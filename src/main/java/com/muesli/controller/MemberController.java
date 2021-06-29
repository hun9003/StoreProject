package com.muesli.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.muesli.domain.MemberLoginLogBean;
import com.muesli.service.BoardService;
import com.muesli.service.CommentService;
import com.muesli.util.StrResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.muesli.domain.MailBean;
import com.muesli.domain.MemberAuthEmailBean;
import com.muesli.domain.MemberBean;
import com.muesli.service.MemberService;
import com.muesli.util.FunctionUtils;
import com.muesli.util.LoginAPI;
import com.muesli.util.ScriptUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 회원 관리를 위한 컨트롤러 클래스
 *
 * @author 개발자 박진훈
 * @version 1.0
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일         수정자           수정내용
 *  ------------   --------    ---------------------------
 *   2021.04.06     박진훈          최초 생성
 *
 * Copyright (C) 2009 by MOPAS  All right reserved.
 * </pre>
 * @since 2021.04.06
 */

@Controller
public class MemberController {

    @Inject
    private MemberService memberService;

    @Inject
    private BoardService boardService;

    @Inject
    private CommentService commentService;

    @Autowired
    private JavaMailSenderImpl mailSender;

    /**
     * 회원가입 페이지
     * @param session 세션
     * @param model 뷰에 전달할 객체
     * @return "/member/join"
     */
    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public String join(HttpSession session, Model model) {
        System.out.println("MemberController - join() :: GET /join");
        /*
        이미 로그인 되어있을 경우 메인 페이지로 이동
         */
        if(StrResources.CHECK_LOGIN(session)){
            model.addAttribute("msg", StrResources.ALREADY_LOGIN);
            model.addAttribute("url", "/home");
            return StrResources.ALERT_MESSAGE_PAGE;
        }
        return StrResources.JOIN_PAGE;
    }

    /**
     * 로그인 페이지
     * @param session 세션
     * @param model 뷰에 전달할 객체
     * @param request 요청
     * @return "member/login"
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpSession session, Model model, HttpServletRequest request) {
        System.out.println("MemberController - login :: GET /login");

        // 로그인 시 이전페이지 기억
        String referrer = request.getHeader("Referer");

        request.getSession().setAttribute("prevPage", referrer);

        model.addAttribute("referrer", referrer);


        if(StrResources.CHECK_LOGIN(session)){
            return StrResources.REDIRECT+"/home";
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
    public String loginPost(@RequestHeader(value = "User-Agent") String userAgent, MemberBean memberBean, Model model, HttpSession session, HttpServletRequest request) {
        System.out.println("MemberController - loginPost");
        if(StrResources.CHECK_LOGIN(session)){
            model.addAttribute("msg", StrResources.ALREADY_LOGIN);
            return StrResources.ALERT_MESSAGE_PAGE;
        }
        String password = request.getParameter("mem_password"); // 유저 비밀번호
        memberBean.setMem_password(SALT(password));
        // 로그인을 위해 담아온 정보로 로그인 유효성 검사
        MemberBean memberCheck = memberService.getMember(memberBean);

        String url = request.getParameter("referrer");
        if(url == null){
            url = "/home";
        }

        if (memberCheck != null) {
            // 로그인에 성공했다면 마지막 로그인, 아이피 기록 업데이트
            Timestamp nowTime = new Timestamp(System.currentTimeMillis());
            String nowIp = ScriptUtils.getIp(request);

            // 레벨업
            System.out.println(nowTime.getTime() - memberCheck.getMem_lastlogin_datetime().getTime());
            if (memberCheck.getMem_lastlogin_datetime() == null || nowTime.getTime() - memberCheck.getMem_lastlogin_datetime().getTime() > 86400000) {
                System.out.println("Login Exp 10 up");
                memberService.setMemberPoint(memberCheck.getMem_id(), 10);
            }

            memberCheck.setMem_lastlogin_datetime(nowTime);
            memberCheck.setMem_lastlogin_ip(nowIp);

            MemberLoginLogBean memberLoginLogBean = new MemberLoginLogBean();
            memberLoginLogBean.setMll_success(1);
            memberLoginLogBean.setMem_id(memberCheck.getMem_id());
            memberLoginLogBean.setMll_userid(memberCheck.getMem_userid());
            memberLoginLogBean.setMll_datetime(nowTime);
            memberLoginLogBean.setMll_ip(nowIp);
            memberLoginLogBean.setMll_reason("success");
            memberLoginLogBean.setMll_useragent(userAgent);
            memberLoginLogBean.setMll_url("/login");
            memberLoginLogBean.setMll_referer(url);

            memberService.insertLog(memberLoginLogBean);

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




        return StrResources.REDIRECT+url;
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
            entity = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
            entity = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<String> sandMail(HttpServletRequest request, final MailBean mailBean, HttpSession session) throws UnsupportedEncodingException {
        System.out.println("MemberController - sendMail() :: GET /sandMail");
        request.setCharacterEncoding("utf-8");
        // 이메일 전송을 위해 회원 정보 저장
        MemberBean member = (MemberBean) session.getAttribute("member");
        // ajax 결과를 저장할 변수
        ResponseEntity<String> entity = null;
        String result = "";

        // 랜덤 문자열로 코드 생성
        FunctionUtils fn = new FunctionUtils();
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
                    mailBean.setContents("커뮤니티 회원 인증 이메일 코드입니다.  <br>"
                                                        + "인증 코드 : " + code);
                    mailBean.setSubject("커뮤니티 회원 인증 이메일 코드입니다.");

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

    // 회원 정보 조회
    @RequestMapping(value = "/member/info", method = RequestMethod.GET)
    public String member_info(HttpSession session, Model model) {
        System.out.println("MemberController - member_info() :: GET /member/info");

        // 페이지 제목
        String subTitle = "memberInfo";

        // 세션으로 멤버 정보 호출
        MemberBean memberBean = (MemberBean)session.getAttribute("member");
        if(memberBean == null) {
            model.addAttribute("msg", StrResources.LOGIN);
            model.addAttribute("url", "/login");
            return StrResources.ALERT_MESSAGE_PAGE;
        }

        MemberBean memberInfo = memberService.getMember(memberBean);

        int postCount = boardService.getMemberPostCount(memberInfo.getMem_id());
        int commentCount = commentService.getMemberCommentCount(memberInfo.getMem_id());

        // 모델에 저장
        model.addAttribute("subTitle", subTitle);
        model.addAttribute("member", memberInfo);
        model.addAttribute("postCount", postCount);
        model.addAttribute("commentCount", commentCount);
        return StrResources.MEMBER_INFO_PAGE;
    }

    // 비밀번호 체크
    @RequestMapping(value = "/member/check_pwd", method = RequestMethod.GET)
    public String check_password(HttpSession session, Model model, HttpServletRequest request) {
        System.out.println("MemberController - check_password() :: GET /member/check_pwd");

        // 페이지 제목
        String subTitle = "checkPwd";
        String type = request.getParameter("type");
        if(type == null) {
            model.addAttribute("msg", StrResources.BAD_REDIRECT);
            return StrResources.ALERT_MESSAGE_PAGE;
        }



        model.addAttribute("subTitle", subTitle);
        model.addAttribute("type", type);
        return StrResources.CHECK_PASSWORD;
    }

    // 프로필 사진 변경
    @RequestMapping(value = "/member/change_photo", method = RequestMethod.GET)
    public String change_photo(HttpSession session, Model model, HttpServletRequest request) {
        System.out.println("MemberController - change_photo() :: GET /member/change_photo");

        // 페이지 제목
        String subTitle = "changePhoto";

        model.addAttribute("subTitle", subTitle);
        return StrResources.CHANGE_PHOTO;
    }

    // 프로필 사진 변경
    @RequestMapping(value = "/member/change_photo", method = RequestMethod.POST)
    public String change_photo_upload(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile mem_photo, HttpSession session, Model model)
        throws Exception {
        System.out.println("MemberController - change_photo_upload() :: POST /member/change_photo");

        String fileUrl = null;
        if(!StrResources.CHECK_LOGIN(session)){
            model.addAttribute("msg", StrResources.LOGIN);
            model.addAttribute("url", "/login");
            return StrResources.ALERT_MESSAGE_PAGE;
        }
        MemberBean memberBean = (MemberBean) session.getAttribute("member");
        // 한글깨짐을 방지하기위해 문자셋 설정
        response.setCharacterEncoding("utf-8");

        // 마찬가지로 파라미터로 전달되는 response 객체의 한글 설정
        response.setContentType("text/html; charset=utf-8");

        // 업로드한 파일 이름
        String fileName = mem_photo.getOriginalFilename();
        System.out.println(fileName);
        if(!fileName.equals("")) {
            // 파일을 바이트 배열로 변환
            byte[] bytes = mem_photo.getBytes();

            // 이미지를 업로드할 디렉토리(배포 디렉토리로 설정)
            String root_path = request.getSession().getServletContext().getRealPath("/");
            //String uploadPath = "resources/upload/";
            String uploadPath = "images/user/"+memberBean.getMem_userid()+"/";
//        프로젝트는 개발 디렉토리에 저장이 되는데 이미지를 업로드할 디렉토리를 개발 디렉토리로 설정하면 일일이 새로고침을 해주어야되서
//        불편하기 때문에 이미지를 업로드할 디렉토리를 배포 디렉토리로 설정한다.
            System.out.println("디렉토리 : " + root_path + uploadPath);
            File Folder = new File(root_path + uploadPath);

            // 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
            if (!Folder.exists()) {
                try{
                    Folder.mkdirs(); //폴더 생성합니다.
                    System.out.println("폴더가 생성되었습니다.");
                }
                catch(Exception e){
                    e.getStackTrace();
                }
            }else {
                System.out.println("이미 폴더가 생성되어 있습니다.");
            }

            OutputStream out = new FileOutputStream(new File(root_path + uploadPath + fileName));

            // 서버로 업로드
            // write메소드의 매개값으로 파일의 총 바이트를 매개값으로 준다.
            // 지정된 바이트를 출력 스트립에 쓴다 (출력하기 위해서)
            out.write(bytes);

            fileUrl ="/"+uploadPath + fileName;
        }

        // 프로필 저장
        memberBean.setMem_photo(fileUrl);
        int result = memberService.updateMemberPhoto(memberBean);
        if(result > 0) {
            model.addAttribute("msg", StrResources.SUCCESS_PHOTO_UPLOAD);
            model.addAttribute("url", "/member/info");
        } else {
            model.addAttribute("msg", StrResources.FAIL_PHOTO_UPLOAD);
        }

        return StrResources.ALERT_MESSAGE_PAGE;
    }
}
