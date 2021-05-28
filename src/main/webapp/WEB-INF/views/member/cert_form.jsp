<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<header class="major">
<h2><spring:message code="msg.certForm"/></h2>
</header>
<form action="<c:url value="/sandMail"/>" method="POST">
	<div class="row gtr-uniform">
		<p class="text-info text-center text-bold"><spring:message code="msg.giveMeCode" arguments="${sessionScope.member.mem_email }"/></p>
		<div class="col-12 col-12-xsmall text-center">
			<input type="hidden" name="mem_email" value="${sessionScope.member.mem_email }">
			<input type="text" name="mae_key" required="required" placeholder="<spring:message code="label.mailKey"/>">
		</div>
		<div class="col-12 col-12-xsmall text-center">
			<a href="javascript:void(0);" class="button warning icon solid fa-search sandBtn" onclick="send_mail('resend')"><spring:message code="button.resend"/></a>
			<input class="button primary" type="submit" value="<spring:message code="button.submitCert"/>">
		</div>
	</div>
</form>