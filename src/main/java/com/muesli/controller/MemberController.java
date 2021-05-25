package com.muesli.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.muesli.util.StrResources;
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
import com.muesli.util.FuntcionUtils;
import com.muesli.util.LoginAPI;
import com.muesli.util.ScriptUtils;

@Controller
public class MemberController {

    @Inject
    private MemberService memberService;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public String join(HttpSession session, Model model) {
        System.out.println("MemberController - join() :: GET /join");
        if(StrResources.CHECK_LOGIN(session)){
            model.addAttribute("msg", StrResources.ALREADY_LOGIN);
            model.addAttribute("url", "/home");
            return StrResources.ALERT_MESSAGE_PAGE;
        }
        return StrResources.JOIN_PAGE;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpSession session, Model model) {
        System.out.println("MemberController - login :: GET /login");
        if(StrResources.CHECK_LOGIN(session)){
            model.addAttribute("msg", StrResources.ALREADY_LOGIN);
            model.addAttribute("url", "/home");
            return StrResources.ALERT_MESSAGE_PAGE;
        }
        return StrResources.LOGIN_PAGE;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        System.out.println("MemberController - logout :: GET /logout");
        session.invalidate();
        return StrResources.REDIRECT+"/login";
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String joinpost(HttpServletRequest request, Model model, MemberBean memberBean, HttpSession session) {
        System.out.println("MemberController - joinpost() :: POST /join");
        if(StrResources.CHECK_LOGIN(session)){
            model.addAttribute("msg", StrResources.ALREADY_LOGIN);
            model.addAttribute("url", "/home");
            return StrResources.ALERT_MESSAGE_PAGE;
        }
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

        memberBean.setMem_password(SALT(password));
        // 회원가입 후 그 결과를 저장
        int result = memberService.join(memberBean);
        // 예외 처리
        if (result == 0) {
            model.addAttribute("msg", StrResources.ERROR);
            return StrResources.ALERT_MESSAGE_PAGE;
        }
        return StrResources.REDIRECT+"/login";
    }
    // 비밀번호 해시화
    private String SALT(String password) {
        String SALT = "mRL0Qmx7lUU4C3NIwc1hXIVhfZrj1KnSxsSkWgyRqWBvxmiV"; // 보안용 SALT
        String hashPass = SALT + password + SALT; // SALT, PASSWORD 조합
        LoginAPI login = new LoginAPI();
        String secretPass = ""; // 해시화된 비밀번호 저장

        try {
            byte[] resultHash = login.hasing(hashPass); // 해싱작업
            StringBuilder sb = new StringBuilder();
            for (byte hash : resultHash) {
                sb.append(Integer.toString((hash & 0xFF) + 256, 16).substring(1));
            }
            secretPass = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return secretPass;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(MemberBean memberBean, Model model, HttpSession session, HttpServletRequest request) {
        System.out.println("MemberController - loginPost");
        if(StrResources.CHECK_LOGIN(session)){
            model.addAttribute("msg", StrResources.ALREADY_LOGIN);
            return StrResources.ALERT_MESSAGE_PAGE;
        }
        String password = request.getParameter("mem_password"); // 유저 비밀번호
        memberBean.setMem_password(SALT(password));
        // 로그인을 위해 담아온 정보로 로그인 유효성 검사
        MemberBean memberCheck = memberService.getMember(memberBean);
        if (memberCheck != null) {
            // 로그인에 성공했다면 마지막 로그인, 아이피 기록 업데이트
            Timestamp nowTime = new Timestamp(System.currentTimeMillis());
            String nowIp = ScriptUtils.getIp(request);
            memberCheck.setMem_lastlogin_datetime(nowTime);
            memberCheck.setMem_lastlogin_ip(nowIp);

            int result = memberService.updateLoginMember(memberCheck);
            // 업데이트 완료시 세션 저장
            if (result == 1) {
                session.setAttribute("member", memberCheck);
            } else {
                model.addAttribute("msg", StrResources.ERROR);
                return StrResources.ALERT_MESSAGE_PAGE;
            }
        } else {
            model.addAttribute("msg", StrResources.LOGIN_FAIL);
            return StrResources.ALERT_MESSAGE_PAGE;
        }
        return StrResources.REDIRECT+"/home";
    }

    @RequestMapping(value = "/ckUserid", method = RequestMethod.GET)
    public ResponseEntity<String> useridCheck(HttpServletRequest request) {
        System.out.println("MemberController - useridCheck() :: GET /ckUserid");
        // 아이디 중복검사
        ResponseEntity<String> entity;
        String result;
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
        ResponseEntity<String> entity;
        String result;
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
        if(!StrResources.CHECK_LOGIN(session)){
            model.addAttribute("msg", StrResources.LOGIN);
            model.addAttribute("url", "/home");
            return "/common/alertMessage";
        }
        MemberBean memberBean = (MemberBean) session.getAttribute("member");
        model.addAttribute("mem_email", memberBean.getMem_email());
        return StrResources.CERT_PAGE;
    }

    // 이메일 인증 폼
    @RequestMapping(value = "/certForm", method = RequestMethod.GET)
    public String certForm() {
        System.out.println("MemberController - certForm :: GET /certForm");
        return StrResources.CERT_FORM_PAGE;
    }

    // 이메일 전송 ajax
    @RequestMapping(value = "/sendMail", method = RequestMethod.GET)
    public ResponseEntity<String> sandMail(HttpServletRequest request, final MailBean mailBean, HttpSession session) {
        System.out.println("MemberController - sendMail() :: GET /sandMail");

        // 이메일 전송을 위해 회원 정보 저장
        MemberBean member = (MemberBean) session.getAttribute("member");
        // ajax 결과를 저장할 변수
        ResponseEntity<String> entity = null;
        String result = "";

        // 랜덤 문자열로 코드 생성
        FuntcionUtils fn = new FuntcionUtils();
        String code = fn.getRandStr();

        Timestamp nowTime = new Timestamp(System.currentTimeMillis());

        // 이메일코드 가져오기 위해 정보 저장
        MemberAuthEmailBean memberAuthEmailBean = new MemberAuthEmailBean();
        memberAuthEmailBean.setMem_id(member.getMem_id());
        memberAuthEmailBean.setMae_key(code);
        memberAuthEmailBean.setMae_generate_datetime(nowTime);

        // 이미 지급된 코드가 있는지 확인
        MemberAuthEmailBean checkEmailCode = memberService.getMemberEmailCode(memberAuthEmailBean);

        // 재전송을 하거나 이미 지급된 코드가 있으면 update로 넘기기
        int isSuccess;
        if (request.getParameter("reSend") == null && checkEmailCode == null) {
            memberAuthEmailBean.setMae_type(1);
            memberAuthEmailBean.setMae_expired(0);

            isSuccess = memberService.createMemberEmailCode(memberAuthEmailBean);
        } else {
            isSuccess = memberService.updateMemberEmailCode(memberAuthEmailBean);
        }

        // 만약 코드 생성 또는 수정을 성공했다면 실제 메일 전송
        if (isSuccess == 1) {
            try {
                String mem_email = request.getParameter("mem_email");
                if (mem_email != null) {
                    mailBean.setTo(mem_email);
                    mailBean.setFrom("jinhun3892@gmail.com");
                    mailBean.setContents("뮤즐리 커뮤니티 회원 인증 이메일 코드입니다.  <br>"
                                                        + "인증 코드 : " + code);
                    mailBean.setSubject("뮤즐리 커뮤니티 회원 인증 이메일 코드입니다.");

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

	// 이메일 코드 인증
	@RequestMapping(value = "/sandMail", method = RequestMethod.POST)
	public String sandMail(MemberAuthEmailBean memberAuthEmailBean, Model model, HttpSession session) {
		System.out.println("MemberController - sandMail :: POST /sandMail");
        if(!StrResources.CHECK_LOGIN(session)){
            model.addAttribute("msg", StrResources.LOGIN);
            model.addAttribute("url", "/login");
            return StrResources.ALERT_MESSAGE_PAGE;
        }
        MemberBean memberBean = (MemberBean)session.getAttribute("member");
		// 코드가 일치하는지 확인을 위해 서비스 호출
        memberAuthEmailBean.setMem_id(memberBean.getMem_id());
        MemberAuthEmailBean checkMemberEmailCode = memberService.checkMemberEmailCode(memberAuthEmailBean);
        if(checkMemberEmailCode != null){
            int isSuccess = memberService.updateMemberEmailCert(memberBean);

            memberAuthEmailBean.setMae_expired(1);
            memberAuthEmailBean.setMae_use_datetime(new Timestamp(System.currentTimeMillis()));

            isSuccess += memberService.useMemberEmailCode(memberAuthEmailBean);
            if(isSuccess == 2){
                model.addAttribute("msg", StrResources.SUCCESS);
            } else {
                model.addAttribute("msg", StrResources.ERROR);
            }
            model.addAttribute("url", "/home");
        } else {
            model.addAttribute("msg", StrResources.FAIL);
        }
        return StrResources.ALERT_MESSAGE_PAGE;

	}
}
