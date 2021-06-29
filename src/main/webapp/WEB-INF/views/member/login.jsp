<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:import url="/top"/>
<!-- Wrapper -->
<div id="wrapper">
<!-- Main -->
<div id="main">
<div class="inner">
<c:import url="/header"/>

<section>
<header class="major">
    <h2><spring:message code="title.login"/></h2>
</header>
<form method="post">
<div class="row gtr-uniform">
<div class="col-12 col-12-xsmall">
    <input type="text" name="mem_userid" id="mem_userid" value="" placeholder="<spring:message code="label.id"/>"
           required="required">
</div>
<div class="col-12 col-12-xsmall">
    <input type="password" name="mem_password" id="mem_password" value=""
           placeholder="<spring:message code="label.password"/>" required="required">
</div>
<div class="col-12 col-12-large m-b-20 txt-left">
<input type="checkbox" id="pass_show" value="1" onchange="pass_show_toggle()"><label for="pass_show"><spring:message
        code="label.passwordShow"/></label>
    </div>
    <!-- Break -->
    <div class="col-12">
    <ul class="actions">
    <li><input type="submit" value="<spring:message code="button.submitlogin"/>" class="primary"></li>
    <li><a href="#"><spring:message code="msg.forget"/></a></li>
    </ul>
    </div>
    </div>
    <input type="hidden" name="referrer" value="${referrer}">
    </form>
    </section>

    </div>
    </div>
    <c:import url="/side"/>
    </div>
    <c:import url="/bottom"/>