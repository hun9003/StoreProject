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
                    <table>
                        <thead>
                        <tr>
                            <th><spring:message code="table.title"/></th>
                            <th><spring:message code="table.writer"/></th>
                            <th><spring:message code="table.dateWrite"/></th>
                            <th><spring:message code="table.likes"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>Ante turpis integer aliquet porttitor.</td>
                            <td>Rateye</td>
                            <td>21-04-05</td>
                            <td>0</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </section>
        </div>
    </div>
    <c:import url="/side"/>
</div>
<c:import url="/bottom"/>