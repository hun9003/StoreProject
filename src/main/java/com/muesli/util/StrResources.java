package com.muesli.util;

import javax.servlet.http.HttpSession;

public class StrResources {

    public static final String ERROR = "예기치 못한 오류가 발생했습니다. 관리자에게 문의하세요.";
    public static final String LOGIN = "로그인이 필요한 시스템 입니다.";
    public static final String ALREADY_LOGIN = "이미 로그인 하셨습니다.";
    public static final String FAIL = "실패했습니다. 이전 페이지로 돌아갑니다.";
    public static final String SUCCESS = "성공적으로 완료했습니다.";
    public static final String EMAIL_CERT = "이메일 인증 미완료 회원입니다. 이메일 인증을 진행해주세요.";


    public static boolean CHECK_LOGIN(HttpSession session) {
        return session.getAttribute("member") != null;
    }

}
