package com.muesli.util;

import javax.servlet.http.HttpSession;

public class StrResources {

    // 시스템 관련 =================================================================================
    public static final String ERROR = "error"; // 예기치 못한 오류가 발생했습니다. 관리자에게 문의하세요.
    public static final String FAIL = "fail"; // 실패했습니다. 이전 페이지로 돌아갑니다.
    public static final String SUCCESS = "success"; // 성공적으로 완료했습니다.
    public static final String RESULT_EMPTY = "resultEmpty"; // 결과가 존재하지 않습니다.
    public static final String BAD_REDIRECT = "noRedirect"; // 잘못된 접근 방식 입니다.
    public static final String BAD_PERMISSION = "badPermission"; // 권한이 없습니다.
    public static final String PAGE_404 = "page404"; // 존재하지 않는 페이지 입니다. 메인 화면으로 이동합니다.

    public static final String PAUSE = "pause";

    // 로그인 관련 =================================================================================
    public static final String LOGIN = "login"; // 로그인이 필요한 시스템 입니다.
    public static final String LOGIN_FAIL = "loginFail"; // 아이디 혹은 비밀번호가 틀립니다.
    public static final String ALREADY_LOGIN = "alreadyLogin"; // 이미 로그인 하셨습니다.
    public static final String EMAIL_CERT = "emailCert"; // 이메일 인증 미완료 회원입니다. 이메일 인증을 진행해주세요.

    // 메뉴 관련 ===============================================================================
    public static final String FAIL_UPDATE_ORDER = "failUpdateOrder"; // 더이상 메뉴의 순서를 변경할 수 없습니다.

    // 리다이렉트 관련 ==========================================================================
    public static final String REDIRECT = "redirect:"; // 리다이렉트

    // 게시판 관련 ==============================================================================
    public static final String SUCCESS_BOARD_WRITE = "successBoardWrite"; // 게시물 작성을 완료했습니다.
    public static final String FAIL_BOARD_WRITE = "failBoardWrite"; // 게시물 작성을 실패했습니다.
    public static final String SUCCESS_BOARD_UPDATE = "successBoardUpdate"; // 게시물 수정을 완료했습니다.
    public static final String FAIL_BOARD_UPDATE = "failBoardUpdate"; // 게시물 수정을 실패했습니다.
    public static final String SUCCESS_BOARD_DELETE = "successBoardDelete"; // 게시물 삭제를 완료했습니다.
    public static final String FAIL_BOARD_DELETE = "failBoardDelete"; // 게시물 삭제를 실패했습니다.

    public static final String SUCCESS_COMMENT_WRITE = "successCommentWrite"; // 댓글 작성을 완료했습니다.
    public static final String FAIL_COMMENT_WRITE = "failCommentWrite"; // 댓글 작성을 실패했습니다.
    public static final String SUCCESS_COMMENT_UPDATE = "successCommentUpdate"; // 댓글 수정을 완료했습니다.
    public static final String FAIL_COMMENT_UPDATE = "failCommentUpdate"; // 댓글 수정을 실패했습니다.
    public static final String SUCCESS_COMMENT_DELETE = "successCommentDelete"; // 댓글 삭제를 완료했습니다.
    public static final String FAIL_COMMENT_DELETE = "failCommentDelete"; // 댓글 삭제를 실패했습니다.

    public static final String INCLUDE_TOP = "/include/top"; // 리팩토링 top 페이지
    public static final String INCLUDE_HEADER = "/include/header"; // 리팩토링 header 페이지
    public static final String INCLUDE_SIDE = "/include/side"; // 리팩토링 side 페이지
    public static final String INCLUDE_BOTTOM = "/include/bottom"; // 리팩토링 bottom 페이지

    public static final String INCLUDE_SETTING_TOP = "/settings/include/top"; // 리팩토링 top 관리자페이지
    public static final String INCLUDE_SETTING_HEADER = "/settings/include/header"; // 리팩토링 header 관리자페이지
    public static final String INCLUDE_SETTING_SIDE = "/settings/include/side"; // 리팩토링 side 관리자페이지
    public static final String INCLUDE_SETTING_BOTTOM = "/settings/include/bottom"; // 리팩토링 bottom 관리자페이지

    public static final String SETTING_MEMBER_PAGE = "/settings/member/members"; // 회원 관리 페이지
    public static final String SETTING_MEMBER_INFO = "/settings/member/info"; // 회원 정보 조회 페이지

    public static final String SETTING_MENU_PAGE = "/settings/navigation/menu"; // 메뉴 관리 페이지
    public static final String SETTING_MENU_INFO = "/settings/navigation/info"; // 메뉴 정보 조회 페이지

    public static final String SETTING_BOARD_PAGE = "/settings/board/board"; // 게시판 관리 페이지
    public static final String SETTING_BOARD_INFO = "/settings/board/info"; // 게시판 정보 페이지

    public static final String MAIN_PAGE = "/home"; // 메인 페이지
    public static final String SETTING_PAGE = "/settings/index"; // 관리자 페이지
    public static final String JOIN_PAGE = "/member/join"; // 회원가입 페이지
    public static final String LOGIN_PAGE = "/member/login"; // 로그인 페이지
    public static final String CERT_PAGE = "/member/cert"; // 이메일 인증 페이지
    public static final String CERT_FORM_PAGE = "/member/cert_form"; // 이메일 인증 폼 페이지
    
    public static final String MEMBER_INFO_PAGE = "/member/info"; // 회원 정보 페이지

    public static final String BOARD_PAGE = "/page/board"; // 게시판 페이지
    public static final String BOARD_FORM_PAGE = "/page/form"; // 게시물 폼 페이지
    public static final String BOARD_INFO_PAGE = "/page/info"; // 게시물 조회 페이지
    public static final String COMMENT_PAGE = "/page/comment"; // 댓글 페이지

    public static final String ALERT_MESSAGE_PAGE = "/common/alertMessage"; // 메세지 출력 페이지
    public static final String PAUSE_PAGE = "/common/pause"; // 시스템 점검 페이지
    
    public static final String PANEL_PAGE = "/page/boardPanel"; // 게시판 판넬 페이지
    

    public static boolean CHECK_LOGIN(HttpSession session) {
        return session.getAttribute("member") != null;
    }

}
