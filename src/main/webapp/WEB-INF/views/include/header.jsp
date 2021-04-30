<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Header -->
<header id="header">
	<a href="<c:url value="home"/>" class="logo"><strong>뮤즐리</strong> 커뮤니티</a>
	<ul class="icons">
		<c:choose>
			<c:when test="${sessionScope.member != null}">
				<li><span>${sessionScope.member.mem_nickname}님 환영합니다.</span></li>
				<li><a href="<c:url value="/logout"/>" class="fas fa-sign-out-alt"> <span class="label">로그아웃</span></a></li>
			</c:when>
			<c:otherwise>
				<li><a href="<c:url value="/join"/>" class="fas fa-user-plus"> <span class="label">회원가입</span></a></li>
				<li><a href="<c:url value="/login"/>" class="fas fa-sign-in-alt"> <span class="label">로그인</span></a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</header>