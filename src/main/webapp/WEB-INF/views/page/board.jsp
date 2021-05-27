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
                        <a href="<c:url value="/board/${boardBean.brd_key}/write"/> " class="button primary">글쓰기</a>
                    </div>
                    <hr>
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
                            <td><a href="<c:url value="/board/${boardBean.brd_key}/info/${post.post_id}?page=${pageBean.currentPage}"/>">${post.post_title}</a>
                                <c:if test="${post.post_device == 2}"><i class="fas fa-mobile-alt"></i> </c:if>
                                <a class="text-primary" href="<c:url value="/board/${boardBean.brd_key}/info/${post.post_id}?page=${pageBean.currentPage}#comment-box"/>">[${post.post_comment_count}]</a>
                            </td>
                            <td>${post.post_nickname}</td>
                            <td>${post.post_like}</td>
                            <td><script>document.write(timeForToday('${post.post_datetime}'));</script></td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div id="paging" class="align-center">
                    <ul class="pagination">
                        <c:set value="/board/${boardBean.brd_key}" var="url"/>
                        <c:set value="" var="params"/>
                        <c:if test="${param.search_type != null }">
                            <c:set var="params" value="&search_type=${param.search_type }&search_content=${param.search_content }"/>
                        </c:if>
                        <c:choose>
                            <c:when test="${pageBean.startPage > pageBean.pageBlock}">
                                <li><a href="javascript:void(0)" class="button" onclick="paging('<c:url value="${url}?page=${pageBean.startPage - pageBean.pageBlock}${params}"/>')"><spring:message code="button.prev"/></a></li>
                            </c:when>
                            <c:otherwise><li><span class="button disabled"><spring:message code="button.prev"/></span></li></c:otherwise>
                        </c:choose>

                        <c:forEach var="count" begin="${pageBean.startPage}" end="${pageBean.endPage}" step="1">
                            <li><a href="javascript:void(0)" class="page <c:if test="${pageBean.currentPage == count}">active</c:if>" onclick="paging('<c:url value="${url}?page=${count}${params}"/>')">${count}</a></li>
                        </c:forEach>

                        <c:choose>
                            <c:when test="${pageBean.endPage < pageBean.pageCount}">
                                <li><a href="javascript:void(0)" class="button" onclick="paging('<c:url value="${url}?page=${pageBean.startPage - pageBean.pageBlock}${params}"/>')"><spring:message code="button.next"/></a></li>
                            </c:when>
                            <c:otherwise><li><span class="button disabled"><spring:message code="button.next"/></span></li></c:otherwise>
                        </c:choose>
                    </ul>
                </div>

                <div id="search-box" class="align-center">
                    <form action="<c:url value="${url}"/>" method="GET">
                        <div class="row">
                            <div class="col-3">
                                <select name="search_type">
                                    <option value="title_content"><spring:message code="search.titleContent"/></option>
                                    <option value="title"><spring:message code="search.title"/></option>
                                    <option value="content"><spring:message code="search.content"/></option>
                                    <option value="nickname"><spring:message code="search.nickname"/></option>
                                </select>
                            </div>
                            <div class="col-6"><input type="search" name="search_content" value="${param.search_content}" placeholder="<spring:message code="label.searchContent"/>"></div>
                            <div class="col-3"><input type="submit" value="검색"></div>
                        </div>

                    </form>

                </div>
            </section>
        </div>
    </div>
    <c:import url="/side"/>
</div>
<c:import url="/bottom"/>