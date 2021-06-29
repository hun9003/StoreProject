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
                    <h2><spring:message code="title.${subTitle}"/></h2>
                </header>
                <div class="box text-center" style="max-width: 500px; margin: 0 auto;">
                    <form method="post">
                        <div class="col-12 col-12-large m-b-20"><img src="<c:url value="/resources/images/profiles/no-profile.png"/>" alt="" style="width: 40%; border-radius: 50%;"></div>
                        <div class="col-12 col-12-large text-info m-b-20">
                            <div class="text-primary text-bold m-b-20">${sessionScope.member.mem_userid}</div>
                            <div class="m-b-20">본인임을 인증하기 위해 비밀번호를 확인합니다.</div>
                            <input type="password" name="mem_password" id="mem_password" value="" placeholder="<spring:message code="label.password"/>" required="required">
                        </div>
                        <div class="col-12 col-12-large m-b-20 txt-left">
                            <input type="checkbox" id="pass_show" value="1" onchange="pass_show_toggle()"><label for="pass_show"><spring:message code="label.passwordShow"/></label>
                        </div>
                        <div class="col-12 col-12-large m-tb-20 txt-right">
                            <a class="m-r-20" href="#">비밀번호 찾기</a>
                            <input class="primary" type="button" value="<spring:message code="button.next"/>">
                        </div>

                    </form>
                </div>
            </section>

        </div>
    </div>
    <c:import url="/side"/>
</div>
<c:import url="/bottom"/>