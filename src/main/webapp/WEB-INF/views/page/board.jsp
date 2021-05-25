<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:import url="/top"/>
<!-- Wrapper -->
<div id="wrapper">
    <!-- Main -->
    <div id="main">
        <div class="inner">
            <c:import url="/header"/>
            <!-- Section -->
            <section>
                <header class="major">
                    <h2>${boardBean.brd_name}</h2>
                </header>
                <div class="table-wrapper">
                    <div class="btn-container">
                        <a href="<c:url value="/board/${boardBean.brd_key}/write"/> " class="button">글쓰기</a>
                    </div>
                    <hr>
                    <script src="<c:url value="/resources/js/calculateTime.js"/>"></script>
                    <table>
                        <thead>
                        <tr>
                            <th><spring:message code="table.title"/></th>
                            <th><spring:message code="table.writer"/></th>
                            <th><spring:message code="table.likes"/></th>
                            <th><spring:message code="table.dateWrite"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="post" items="${posts}">
                        <tr>
                            <td><a href="<c:url value="/board/${boardBean.brd_key}/info/${post.post_id}?page=${pageBean.currentPage}"/>">${post.post_title}</a></td>
                            <td>${post.post_nickname}</td>
                            <td>${post.post_like}</td>
                            <td><script>document.write(timeForToday('${post.post_datetime}'));</script></td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </section>
        </div>
    </div>
    <c:import url="/side"/>
</div>
<c:import url="/bottom"/>