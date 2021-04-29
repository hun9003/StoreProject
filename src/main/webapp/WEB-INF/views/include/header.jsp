<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Header -->
<header id="header">
	<a href="<c:url value="home"/>" class="logo"><strong>뮤즐리</strong> 커뮤니티</a>
	<ul class="icons">
		<li><a href="<c:url value="join"/>" class="fas fa-user-plus"> <span class="label">회원가입</span></a></li>
		<li><a href="<c:url value="login"/>" class="fas fa-sign-in-alt"> <span class="label">로그인</span></a></li>
	</ul>
</header>