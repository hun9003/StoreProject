<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
								<h1>권해적단 커뮤니티에 오신 것을 환영합니다.</h1>
							</header>
							<p>뭐라 설명해야 할지도 모르겠는 그냥 그런 사이트</p>
							<ul class="actions">
								<li><a href="#" class="button big disabled">Learn More</a></li>
							</ul>
						</div>
						<span class="image object">
<!-- 									배경 삽입 공간 -->
						</span>
					</section>

				<!-- Section -->
					<section>
						<header class="major">
							<h2><a href="<c:url value="/board/notice"/>">공지사항</a></h2>
						</header>
						<div class="table-wrapper">
							<table>
								<thead>
									<tr>
										<th>제목</th>
										<th>작성자</th>
										<th>조회</th>
										<th>작성일</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${notices}" var="post">
									<tr>
										<td><a href="<c:url value="/board/notice/info/${post.post_id}?page=${pageBean.currentPage}"/>">${post.post_title}</a>
											<c:if test="${post.post_device == 2}"><i class="fas fa-mobile-alt"></i> </c:if>
											<a class="text-primary" href="<c:url value="/board/notice/info/${post.post_id}?page=${pageBean.currentPage}#comment-box"/>">[${post.post_comment_count}]</a>
										</td>
										<td>${post.post_nickname}</td>
										<td>${post.post_hit}</td>
										<td><script>document.write(timeForToday('${post.post_datetime}'));</script></td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</section>
					
					<!-- Section -->
					<section>
						<header class="major">
							<h2><a href="<c:url value="/board/freeboard"/>">자유게시판</a></h2>
						</header>
						<div class="table-wrapper">
							<table>
								<thead>
									<tr>
										<th>제목</th>
										<th>작성자</th>
										<th>추천</th>
										<th>작성일</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${freeboards}" var="post">
									<tr>
										<td><a href="<c:url value="/board/freeboard/info/${post.post_id}?page=${pageBean.currentPage}"/>">${post.post_title}</a>
											<c:if test="${post.post_device == 2}"><i class="fas fa-mobile-alt"></i> </c:if>
											<a class="text-primary" href="<c:url value="/board/freeboard/info/${post.post_id}?page=${pageBean.currentPage}#comment-box"/>">[${post.post_comment_count}]</a>
										</td>
										<td>${post.post_nickname}</td>
										<td>${post.post_like}</td>
										<td><script>document.write(timeForToday('${post.post_datetime}'));</script></td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</section>

				<!-- Section -->
					<section>
						<header class="major">
							<h2>이상형 월드컵</h2>
						</header>
						<div class="posts">
							<article>
								<a href="#" class="image"><img src="<c:url value="/resources/images/pic01.jpg"/>" alt="" /></a>
								<h3>Interdum aenean</h3>
								<p>Aenean ornare velit lacus, ac varius enim lorem ullamcorper dolore. Proin aliquam facilisis ante interdum. Sed nulla amet lorem feugiat tempus aliquam.</p>
								<ul class="actions">
									<li><a href="#" class="button">More</a></li>
								</ul>
							</article>
							<article>
								<a href="#" class="image"><img src="<c:url value="/resources/images/pic02.jpg"/>" alt="" /></a>
								<h3>Nulla amet dolore</h3>
								<p>Aenean ornare velit lacus, ac varius enim lorem ullamcorper dolore. Proin aliquam facilisis ante interdum. Sed nulla amet lorem feugiat tempus aliquam.</p>
								<ul class="actions">
									<li><a href="#" class="button">More</a></li>
								</ul>
							</article>
							<article>
								<a href="#" class="image"><img src="<c:url value="/resources/images/pic03.jpg"/>" alt="" /></a>
								<h3>Tempus ullamcorper</h3>
								<p>Aenean ornare velit lacus, ac varius enim lorem ullamcorper dolore. Proin aliquam facilisis ante interdum. Sed nulla amet lorem feugiat tempus aliquam.</p>
								<ul class="actions">
									<li><a href="#" class="button">More</a></li>
								</ul>
							</article>
							<article>
								<a href="#" class="image"><img src="<c:url value="/resources/images/pic04.jpg"/>" alt="" /></a>
								<h3>Sed etiam facilis</h3>
								<p>Aenean ornare velit lacus, ac varius enim lorem ullamcorper dolore. Proin aliquam facilisis ante interdum. Sed nulla amet lorem feugiat tempus aliquam.</p>
								<ul class="actions">
									<li><a href="#" class="button">More</a></li>
								</ul>
							</article>
						</div>
					</section>

			</div>
		</div>
	<c:import url="/side"/>
</div>
<c:import url="/bottom"/>