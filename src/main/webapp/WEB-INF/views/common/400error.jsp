
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:import url="/top"/>
<!-- Wrapper -->
<div id="wrapper">
  <!-- Main -->
  <div id="main">
    <div class="inner">
      <c:import url="/header"/>
      <!-- Banner -->
      <section id="banner">
        <div class="content">
          <header>
            <h1><spring:message code="msg.badRequest"/> </h1>
            <a class="primary" href="<c:url value="/"/>"><spring:message code="button.main"/></a> <a class="primary" href="javascript:void(0)" onclick="history.back();"><spring:message code="button.back"/></a>
          </header>
        </div>
      </section>
    </div>
  </div>
</div>
<c:import url="/bottom"/>


