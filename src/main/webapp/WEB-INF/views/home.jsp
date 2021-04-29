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
								<h1>뮤즐리 커뮤니티에 오신 것을 환영합니다.</h1>
							</header>
							<p>Aenean ornare velit lacus, ac varius enim ullamcorper eu. Proin aliquam facilisis ante interdum congue. Integer mollis, nisl amet convallis, porttitor magna ullamcorper, amet egestas mauris. Ut magna finibus nisi nec lacinia. Nam maximus erat id euismod egestas. Pellentesque sapien ac quam. Lorem ipsum dolor sit nullam.</p>
							<ul class="actions">
								<li><a href="#" class="button big">Learn More</a></li>
							</ul>
						</div>
						<span class="image object">
<!-- 									배경 삽입 공간 -->
						</span>
					</section>

				<!-- Section -->
					<section>
						<header class="major">
							<h2>공지사항</h2>
						</header>
						<div class="table-wrapper">
							<table>
								<thead>
									<tr>
										<th>작성일</th>
										<th>제목</th>
										<th>작성자</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>21-04-05</td>
										<td>Ante turpis integer aliquet porttitor.</td>
										<td>Rateye</td>
									</tr>
									<tr>
										<td>21-04-05</td>
										<td>Vis ac commodo adipiscing arcu aliquet.</td>
										<td>Rateye</td>
									</tr>
									<tr>
										<td>21-04-05</td>
										<td> Morbi faucibus arcu accumsan lorem.</td>
										<td>Rateye</td>
									</tr>
									<tr>
										<td>21-04-05</td>
										<td>Vitae integer tempus condimentum.</td>
										<td>Rateye</td>
									</tr>
									<tr>
										<td>21-04-05</td>
										<td>Ante turpis integer aliquet porttitor.</td>
										<td>Rateye</td>
									</tr>
								</tbody>
							</table>
						</div>
					</section>
					
					<!-- Section -->
					<section>
						<header class="major">
							<h2>자유게시판</h2>
						</header>
						<div class="table-wrapper">
							<table>
								<thead>
									<tr>
										<th>작성일</th>
										<th>제목</th>
										<th>작성자</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>21-04-05</td>
										<td>Ante turpis integer aliquet porttitor.</td>
										<td>Rateye</td>
									</tr>
									<tr>
										<td>21-04-05</td>
										<td>Vis ac commodo adipiscing arcu aliquet.</td>
										<td>Rateye</td>
									</tr>
									<tr>
										<td>21-04-05</td>
										<td> Morbi faucibus arcu accumsan lorem.</td>
										<td>Rateye</td>
									</tr>
									<tr>
										<td>21-04-05</td>
										<td>Vitae integer tempus condimentum.</td>
										<td>Rateye</td>
									</tr>
									<tr>
										<td>21-04-05</td>
										<td>Ante turpis integer aliquet porttitor.</td>
										<td>Rateye</td>
									</tr>
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