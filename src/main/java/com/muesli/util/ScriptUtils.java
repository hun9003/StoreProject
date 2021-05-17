package com.muesli.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//=> 컨트롤에서 경고창 전달시 사용 
public class ScriptUtils {

	 
	   public static void init(HttpServletResponse response) {		   	   
	        response.setContentType("text/html; charset=utf-8");
	        response.setCharacterEncoding("utf-8");
	    }
	 
	    public static void alert(HttpServletResponse response, String alertText) throws Exception {
	    	// 사용법 : ScriptUtils.alert(response,"인증 번호가 틀립니다");
	        init(response);
	        PrintWriter out = response.getWriter();
	        out.println("<script>alert('" + alertText + "');</script> ");
	        out.flush();
	    }
	 
	    public static void alertAndMovePage(HttpServletResponse response, String alertText, String nextPage) throws Exception {
	    	// 사용법 : ScriptUtils.alertAndMovePage(response,"인증 번호가 틀립니다","/login.do");
	    	System.out.println("알럿알");
	        System.out.println(response + "11111");
	        init(response);
	        System.out.println(response + "11111");
	        PrintWriter out = response.getWriter();
	        out.println("<script>alert('" + alertText + "'); location.href='" + nextPage + "';</script> ");
	        out.flush();
	    }
	 
	    public static void alertAndBackPage(HttpServletResponse response, String alertText) throws Exception {
	    	// 사용법 : ScriptUtils.alertAndBackPage(response,"인증 번호가 틀립니다");
	        init(response);
	        PrintWriter out = response.getWriter();
	        out.println("<script>alert('" + alertText + "'); history.go(-1);</script>");
	        out.flush();
	    }
	    
	    public static String getIp(HttpServletRequest request) {
	    	 
	        String ip = request.getHeader("X-Forwarded-For");
	 
	        if (ip == null) {
	            ip = request.getHeader("Proxy-Client-IP");
	        }
	        if (ip == null) {
	            ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
	        }
	        if (ip == null) {
	            ip = request.getHeader("HTTP_CLIENT_IP");
	        }
	        if (ip == null) {
	            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	        }
	        if (ip == null) {
	            ip = request.getRemoteAddr();
	        }
	 
	        return ip;
	 
	    }


}
