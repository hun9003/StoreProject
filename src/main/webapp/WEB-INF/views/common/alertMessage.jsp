<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>

<!-- controller를 통해 경고창 띄워주는 뷰
	model.addAttribute("msg", "메세지"); 
	model.addAttribute("url", "링크"); 
 -->
<script type="text/javascript"> 
	alert('<spring:message code="msg.${msg}"/>');
	<c:choose>
        <c:when test="${url != null}">
	        document.location.href = '<c:url value="${url}"/>';
        </c:when>
        <c:otherwise>
            history.back();
        </c:otherwise>
    </c:choose>
</script>
</body>
</html>