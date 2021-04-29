package com.muesli.controller;

import java.lang.reflect.Member;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.muesli.domain.MailBean;
import com.muesli.domain.MemberAuthEmailBean;
import com.muesli.domain.MemberBean;
import com.muesli.service.MemberService;
import com.muesli.util.FuncionUtils;
import com.muesli.util.LoginAPI;
import com.muesli.util.ScriptUtils;

@Controller
public class MemberController {
	
	@Inject
	private MemberService memberService;
	
	@Autowired 
	private JavaMailSenderImpl mailSender;
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		System.out.println("MemberController - join() :: GET /join");
		return "member/join";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		System.out.println("MemberController - login :: GET /login");
		return "member/login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		System.out.println("MemberController - logout :: GET /logout");
		session.invalidate();
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String joinpost(HttpServletRequest request, Model model, MemberBean memberBean) {
		System.out.println("MemberController - joinpost() :: POST /join");
		// 현재 시간과 IP를 확인
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		String nowIp = ScriptUtils.getIp(request);
		
		// 회원가입을 위해 담아온 회원정보에 추가 데이터를 저장
		memberBean.setMem_level(1);
		memberBean.setMem_point(0);
		memberBean.setMem_denied(0);
		memberBean.setMem_email_cert(0);
		memberBean.setMem_register_datetime(nowTime);
		memberBean.setMem_register_ip(nowIp);
		memberBean.setMem_lastlogin_datetime(nowTime);
		memberBean.setMem_lastlogin_ip(nowIp);
		memberBean.setMem_is_admin(0);
		memberBean.setMem_profile_content("/resources/images/profiles/no-profile.png");
		
		String password = memberBean.getMem_password(); // 유저 비밀번호 
		String SALT = "mRL0Qmx7lUU4C3NIwc1hXIVhfZrj1KnSxsSkWgyRqWBvxmiV"; // 보안용 SALT
		String hashPass = SALT + password + SALT; // SALT, PASSWORD 조합
		LoginAPI login = new LoginAPI();
		String secretPass = ""; // 해시화된 비밀번호 저장
		
		try {
			byte[] resultHash = login.hasing(hashPass); // 해싱작업
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < resultHash.length; i++) {
				sb.append(Integer.toString((resultHash[i] & 0xFF) + 256, 16).substring(1));
			}
			secretPass = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		memberBean.setMem_password(secretPass);
		// 회원가입 후 그 결과를 저장
		int result = memberService.join(memberBean);
		// 예외 처리
		if(result == 0) {
			model.addAttribute("msg", "예기치 못한 오류가 발생했습니다. 관리자에게 문의하세요"); 
			model.addAttribute("url", "/home");
			return "/common/alertMessage";
		}
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPost(MemberBean memberBean, Model model, HttpSession session, HttpServletRequest request) {
		System.out.println("MemberController - loginPost");
		String password = request.getParameter("mem_password"); // 유저 비밀번호 
		String SALT = "mRL0Qmx7lUU4C3NIwc1hXIVhfZrj1KnSxsSkWgyRqWBvxmiV"; // 보안용 SALT
		String hashPass = SALT + password + SALT; // SALT, PASSWORD 조합
		LoginAPI login = new LoginAPI();
		String secretPass = ""; // 해시화된 비밀번호 저장
		
		try {
			byte[] resultHash = login.hasing(hashPass); // 해싱작업
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < resultHash.length; i++) {
				sb.append(Integer.toString((resultHash[i] & 0xFF) + 256, 16).substring(1));
			}
			secretPass = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		
		memberBean.setMem_password(secretPass);
		
		// 로그인을 위해 담아온 정보로 로그인 유효성 검사
		MemberBean memberCheck = memberService.getMember(memberBean);
		String msg = "";
		if(memberCheck != null) {
			// 로그인에 성공했다면 마지막 로그인, 아이피 기록 업데이트
			Timestamp nowTime = new Timestamp(System.currentTimeMillis());
			String nowIp = ScriptUtils.getIp(request);
			memberCheck.setMem_lastlogin_datetime(nowTime);
			memberCheck.setMem_lastlogin_ip(nowIp);
			
			int result = memberService.updateLoginMember(memberCheck);
			// 업데이트 완료시 세션 저장
			if(result == 1) {
				session.setAttribute("member", memberCheck);
			} else {
				msg = "예기치 못한 오류가 발생했습니다. 관리자에게 문의하세요";
				model.addAttribute("msg", msg); 
				model.addAttribute("url", "/home");
				return "/common/alertMessage";
			}
		} else {
			msg = "예기치 못한 오류가 발생했습니다. 관리자에게 문의하세요";
			model.addAttribute("msg", msg); 
			model.addAttribute("url", "/home");
			return "/common/alertMessage";
		}
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/ckUserid", method = RequestMethod.GET)
	public ResponseEntity<String> useridCheck(HttpServletRequest request) {
		System.out.println("MemberController - useridCheck() :: GET /ckUserid");
		// 아이디 중복검사
		ResponseEntity<String> entity = null;
		String result = "";
		try {
			String mem_userid = request.getParameter("mem_userid");
			MemberBean mb = memberService.getMember_userid(mem_userid);
			if (mb != null) {
				// 중복
				result = "false";
			} else {
				// 사용가능
				result = "true";
			}
			entity = new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@RequestMapping(value = "/ckNickname", method = RequestMethod.GET)
	public ResponseEntity<String> nicknameCheck(HttpServletRequest request) {
		System.out.println("MemberController - nicknameCheck() :: GET /ckNickname");
		// 닉네임 중복검사
		ResponseEntity<String> entity = null;
		String result = "";
		try {
			String mem_nickname = request.getParameter("mem_nickname");
			MemberBean mb = memberService.getMember_nickname(mem_nickname);
			if (mb != null) {
				// 중복
				result = "false";
			} else {
				// 사용가능
				result = "true";
			}
			entity = new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	// 이메일 인증 화면
	@RequestMapping(value = "/cert", method = RequestMethod.GET)
	public String cert(Model model, HttpSession session) {
		System.out.println("MemberController - cert :: GET /cert");
		MemberBean memberBean = (MemberBean)session.getAttribute("member");
		model.addAttribute("mem_email", memberBean.getMem_email());
		return "/member/cert";
	}
	
	// 이메일 인증 폼
		@RequestMapping(value = "/certForm", method = RequestMethod.GET)
		public String certForm() {
			System.out.println("MemberController - certForm :: GET /certForm");
			return "/member/cert_form";
		}
	
	// 이메일 전송 ajax
	@RequestMapping(value = "/sandMail", method = RequestMethod.GET)
	public ResponseEntity<String> sandMail(HttpServletRequest request, final MailBean mailBean, Model model,
										   HttpSession session) {
		System.out.println("MemberController - sandMail() :: GET /sandMail");
		MemberBean member = (MemberBean)session.getAttribute("member");

		ResponseEntity<String> entity = null;
		String result = "";
		FuncionUtils fn = new FuncionUtils();
		String code = fn.getRandStr();
		
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		
		MemberAuthEmailBean memberAuthEmailBean = new MemberAuthEmailBean();
		memberAuthEmailBean.setMem_id(member.getMem_id());

		MemberAuthEmailBean checkEmailCode = memberService.getMemberEmailCode(memberAuthEmailBean);

		int isSuccess = 0;
		if(request.getParameter("reSend") == null && checkEmailCode == null){
			memberAuthEmailBean.setMae_key(code);
			memberAuthEmailBean.setMae_type(1);
			memberAuthEmailBean.setMae_generate_datetime(nowTime);
			memberAuthEmailBean.setMae_expired(0);

			isSuccess = memberService.createMemberEmailCode(memberAuthEmailBean);
		} else {
			isSuccess = memberService.updateMemberEmailCode(memberAuthEmailBean);
		}
		if (isSuccess == 1) {
			try {
				String mem_email = request.getParameter("mem_email");
				if(mem_email != null) {
					mailBean.setTo(mem_email);
					mailBean.setFrom("jinhun3892@gmail.com");
					mailBean.setContents("일단 실험용 이메일");
					mailBean.setSubject("뮤즐리 커뮤니티 회원 인증 이메일 코드입니다. <br>"
							+ "인증 코드 : " + code);
					
					final MimeMessagePreparator preparator = new MimeMessagePreparator() { 
						@Override 
						public void prepare(MimeMessage mimeMessage) throws Exception { 
							final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8"); 
							helper.setFrom(mailBean.getFrom()); 
							helper.setTo(mailBean.getTo()); 
							helper.setSubject(mailBean.getSubject()); 
							helper.setText(mailBean.getContents(), true); 
						}
					}; 
					mailSender.send(preparator); 
					result = "result";
				}
				
				entity = new ResponseEntity<String>(result, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
		}
		
		return entity;
	}
}
