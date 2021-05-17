
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <h1>잘못된 URL 입니다. </h1>
            <a class="primary" href="<c:url value="/"/>">메인으로</a>
          </header>
        </div>
      </section>
    </div>
  </div>
</div>
<c:import url="/bottom"/>


