package com.rateye.util;

import javax.servlet.http.HttpSession;

public class StrResources {

    // 메인 페이지
    public static String MAIN_PAGE = "index";

    public static boolean CHECK_LOGIN(HttpSession session) {
        return session.getAttribute("user") != null;
    }

}
