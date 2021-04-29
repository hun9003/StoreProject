<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<header class="major">
<h2>이메일 인증 코드를 입력하세요</h2>
</header>
<form action="<c:url value="/sandMail"/>" method="POST">
	<div class="row gtr-uniform">
		<p class="text-info text-center text-bold">회원님의 이메일 ${mem_email } 로 전송된 인증코드를 입력하세요</p>
		<div class="col-12 col-12-xsmall text-center">
			<input type="hidden" name="mem_email" value="${mem_email }">
			<input type="text" name="mae_key" required="required" placeholder="인증코드 입력">
		</div>
		<div class="col-12 col-12-xsmall text-center">
			<a href="javascript:void(0);" class="button warning icon solid fa-search sandBtn" onclick="send_mail('resend')">코드 재전송</a>
			<input class="button primary" type="submit" value="인증완료">
		</div>
	</div>
</form>