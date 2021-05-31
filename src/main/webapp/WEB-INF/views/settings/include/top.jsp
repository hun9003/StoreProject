<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title><spring:message code="title.main"/> <spring:message code="title.tail"/></title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="<c:url value="/resources/css/main.css"/>" />
		<link rel="stylesheet" href="<c:url value="/resources/css/custom.css"/>" />
		<script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
		<c:if test="${sessionScope.member == null}">
			<script>
				location.href = "<c:url value="/no-login"/>";
			</script>
		</c:if>
		<c:if test="${sessionScope.member.mem_is_admin != 1}">
			<script>
				location.href = "<c:url value="/no-permission"/>";
			</script>
		</c:if>

	</head>
	<body class="is-preload">