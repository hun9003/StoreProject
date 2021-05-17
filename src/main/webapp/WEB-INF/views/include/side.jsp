<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Sidebar -->
<div id="sidebar">
	<div class="inner">
		<!-- Search -->
			<section id="search" class="alt">
				<form method="post" action="#">
					<input type="text" name="query" id="query" placeholder="Search" />
				</form>
			</section>
		<!-- Menu -->
			<nav id="menu">
				<header class="major">
					<h2>Menu</h2>
				</header>
				<ul>
					<li><a href="<c:url value="home"/>">Home</a></li>
					<li><a href="generic.html">공지사항</a></li>
					<c:forEach items="${menus}" var="meg">
							<c:if test="${meg.men_parent == null}">
							<li>
								<span class="opener">${meg.men_name}</span>
								<ul>
									<c:forEach items="${menus}" var="men">
										<c:if test="${meg.men_id == men.men_parent}">
											<li><a href="<c:url value="/board/${men.men_link}/1"/>" target="${men.men_target}">${men.men_name}</a></li>
										</c:if>
									</c:forEach>
								</ul>
							</li>
							</c:if>
					</c:forEach>
				</ul>
			</nav>
		<!-- Section -->
			<section>
				<header class="major">
					<h2>Readme</h2>
				</header>
				<p>본 사이트는 비상업적인 용도로 개발되었음을 알립니다.</p>
				<ul class="contact">
					<li class="icon solid fa-envelope"><a href="#">jinhun3892@gmail.com</a></li>
					<li class="icon solid fa-phone">(000) 000-0000</li>
					<li class="icon solid fa-home">경상남도 김해시</li>
				</ul>
			</section>
		<!-- Footer -->
			<footer id="footer">
				<p class="copyright">&copy; 뮤즐리 주식회사</p>
			</footer>
	</div>
</div>