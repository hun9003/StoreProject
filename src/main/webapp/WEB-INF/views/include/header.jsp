<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- Header -->
<header id="header">
	<a href="<c:url value="/home"/>" class="logo"><strong><spring:message code="title.main"/></strong> <spring:message code="title.tail"/></a>
	<ul class="icons">
		<c:choose>
			<c:when test="${sessionScope.member != null}">
				<li><span><spring:message code="msg.hello" arguments="${sessionScope.member.mem_nickname}"/></span></li>
				<c:if test="${sessionScope.member.mem_is_admin == 1}">
					<li><a href="<c:url value="/settings"/>" class="fas fa-cogs"> <span class="label"><spring:message code="button.setting"/></span></a></li>
				</c:if>
				<li><a href="<c:url value="/logout"/>" class="fas fa-sign-out-alt"> <span class="label"><spring:message code="button.logout"/></span></a></li>
			</c:when>
			<c:otherwise>
				<li><a href="<c:url value="/join"/>" class="fas fa-user-plus"> <span class="label"><spring:message code="button.join"/></span></a></li>
				<li><a href="<c:url value="/login"/>" class="fas fa-sign-in-alt"> <span class="label"><spring:message code="button.login"/></span></a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</header>