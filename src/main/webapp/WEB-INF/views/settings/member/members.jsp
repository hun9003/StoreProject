<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:import url="/settings/top"/>
<!-- Wrapper -->
<div id="wrapper">
	<!-- Main -->
		<div id="main">
			<div class="inner">
				<c:import url="/settings/header"/>
				<!-- Banner -->
					<section>
						<header class="main">
							<h2><spring:message code="label.memberSetting"/> > <spring:message code="title.${subTitle}"/></h2>
						</header>
						<div class="content">
							<form action="<c:url value="/settings/members/1"/>" method="get">
								<div class="row">
									<div class="col-3 col-3-xsmall">
										<select name="sch_type">
											<option value="1"><spring:message code="label.id"/></option>
											<option value="2"><spring:message code="label.nickname"/></option>
											<option value="3"><spring:message code="label.email"/></option>
										</select>
									</div>
									<div class="col-3 col-3-xsmall">
										<input type="text" name="sch_content" placeholder="<spring:message code="label.searchContent"/>">
									</div>
									<div class="col-3 col-3-xsmall">
										<input type="submit" value="<spring:message code="button.search"/>">
									</div>
								</div>
							</form>
							<div class="table-wrapper">
								<table class="alt">
									<thead>
									<tr>
										<th><spring:message code="table.index"/></th>
										<th><spring:message code="table.level"/></th>
										<th><spring:message code="table.id"/></th>
										<th><spring:message code="table.permissoin"/></th>
										<th><spring:message code="table.nickname"/></th>
										<th><spring:message code="table.email"/></th>
										<th><spring:message code="table.joinDatetime"/></th>
										<th><spring:message code="table.lastDatetime"/></th>
										<th></th>
									</tr>
									</thead>
									<tbody>
									<c:forEach items="${members}" var="member">
										<tr>
											<td>${member.mem_id}</td>
											<td>${member.mem_level}</td>
											<td>${member.mem_userid}</td>
											<td><c:choose>
												<c:when test="${member.mem_is_admin == 1}"><spring:message code="permission.admin"/></c:when>
												<c:otherwise><spring:message code="permission.public"/></c:otherwise>
											</c:choose></td>
											<td>${member.mem_nickname}</td>
											<td>${member.mem_email}</td>
											<td><fmt:formatDate value="${member.mem_register_datetime }" type="date"/></td>
											<td><fmt:formatDate value="${member.mem_lastlogin_datetime }" type="date"/></td>
											<td><a href="<c:url value="/settings/members/info/${member.mem_id}"/>" class="btn btn-primary">상세정보</a></td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<div id="paging" class="align-center">
							<ul class="pagination">
								<c:set value="/settings/members" var="url"/>
								<c:set value="" var="params"/>
								<c:if test="${param.sch_type != null }">
									<c:set var="params" value="&sch_type=${param.sch_type }&sch_content=${param.sch_content }"/>
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
					</section>
			</div>
		</div>
	<c:import url="/settings/side"/>
</div>
<c:import url="/settings/bottom"/>