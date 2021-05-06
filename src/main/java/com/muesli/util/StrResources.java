package com.muesli.util;

import javax.servlet.http.HttpSession;

public class StrResources {

    // 시스템 관련 =================================================================================
    public static final String ERROR = "예기치 못한 오류가 발생했습니다. 관리자에게 문의하세요."; // 오류가 발생했을 때
    public static final String FAIL = "실패했습니다. 이전 페이지로 돌아갑니다."; // 무언가에 실패 했을 때
    public static final String SUCCESS = "성공적으로 완료했습니다."; // 무언가를 완료 했을 때
    public static final String RESULT_EMPTY = "결과가 존재하지 않습니다"; // 리스트의 결과가 존재하지 않을 때
    public static final String BAD_REDIRECT = "잘못된 접근 방식 입니다."; // 올바른 방식으로 리다이렉트 하지 않은 경우

    // 로그인 관련 =================================================================================
    public static final String LOGIN = "로그인이 필요한 시스템 입니다."; // 로그인이 필요한 시스템일 때
    public static final String LOGIN_FAIL = "아이디 혹은 비밀번호가 틀립니다"; // 로그인에 실패 했을 때
    public static final String ALREADY_LOGIN = "이미 로그인 하셨습니다."; // 비로그인이 이용하는 시스템일 때
    public static final String EMAIL_CERT = "이메일 인증 미완료 회원입니다. 이메일 인증을 진행해주세요."; // 이메일 인증이 필요할 때

    // 메뉴 관련 ===============================================================================
    public static final String FAIL_UPDATE_ORDER = "더이상 메뉴의 순서를 변경할 수 없습니다"; // 메뉴의 순서가 이미 최상단이거나 최하단일 때

    // 리다이렉트 관련 ==========================================================================
    public static final String REDIRECT = "redirect:"; // 리다이렉트

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

    public static final String MAIN_PAGE = "/home"; // 메인 페이지
    public static final String SETTING_PAGE = "/settings/index"; // 관리자 페이지
    public static final String JOIN_PAGE = "/member/join"; // 회원가입 페이지
    public static final String LOGIN_PAGE = "/member/login"; // 로그인 페이지
    public static final String CERT_PAGE = "/member/cert"; // 이메일 인증 페이지
    public static final String CERT_FORM_PAGE = "/member/cert_form"; // 이메일 인증 폼 페이지


    public static final String ALERT_MESSAGE_PAGE = "/common/alertMessage"; // 메세지 출력 페이지

    public static boolean CHECK_LOGIN(HttpSession session) {
        return session.getAttribute("member") != null;
    }

}
