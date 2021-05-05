<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
							<h2>회원관리 > ${subTitle}</h2>
						</header>
						<div class="content">
							<form action="<c:url value="/settings/members/1"/>" method="get">
								<div class="row">
									<div class="col-3 col-3-xsmall">
										<select name="sch_type">
											<option value="1">아이디</option>
											<option value="2">닉네임</option>
											<option value="3">이메일</option>
										</select>
									</div>
									<div class="col-3 col-3-xsmall">
										<input type="text" name="sch_content" placeholder="검색내용">
									</div>
									<div class="col-3 col-3-xsmall">
										<input type="submit" value="검색">
									</div>
								</div>
							</form>
							<div class="table-wrapper">
								<table class="alt">
									<thead>
									<tr>
										<th>No</th>
										<th>레벨</th>
										<th>아이디</th>
										<th>등급</th>
										<th>닉네임</th>
										<th>이메일</th>
										<th>가입날짜</th>
										<th>마지막접속날짜</th>
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
												<c:when test="${member.mem_is_admin == 1}">관리자</c:when>
												<c:otherwise>일반회원</c:otherwise>
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
						<div id="paging">
							<ul class="pagination">
								<li><span class="button disabled">Prev</span></li>
								<li><a href="#" class="page active">1</a></li>
								<li><a href="#" class="page">2</a></li>
								<li><a href="#" class="page">3</a></li>
								<li><a href="#" class="page">4</a></li>
								<li><a href="#" class="page">5</a></li>
								<li><a href="#" class="page">6</a></li>
								<li><a href="#" class="page">7</a></li>
<%--								<li><span>…</span></li>--%>
								<li><a href="#" class="page">8</a></li>
								<li><a href="#" class="page">9</a></li>
								<li><a href="#" class="page">10</a></li>
								<li><a href="#" class="button">Next</a></li>
							</ul>
						</div>
					</section>
			</div>
		</div>
	<c:import url="/settings/side"/>
</div>
<c:import url="/settings/bottom"/>