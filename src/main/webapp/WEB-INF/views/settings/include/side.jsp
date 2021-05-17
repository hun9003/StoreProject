<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Sidebar -->
<div id="sidebar">
	<div class="inner">
		<!-- Menu -->
			<nav id="menu">
				<header class="major">
					<h2>Menu</h2>
				</header>
				<ul>
					<li><a href="<c:url value="home"/>">메인</a></li>
					<li>
						<span class="opener">회원관리</span>
						<ul>
							<li><a href="<c:url value="/settings/members"/>">회원목록</a></li>
						</ul>
					</li>
					<li>
						<span class="opener">메뉴관리</span>
						<ul>
							<li><a href="<c:url value="/settings/menu-group"/>">메뉴그룹 목록</a></li>
							<li><a href="<c:url value="/settings/menu"/>">메뉴 목록</a></li>
						</ul>
					</li>
					<li>
						<span class="opener">게시판 관리</span>
						<ul>
							<li><a href="<c:url value="/settings/board"/>">게시판 목록</a></li>
						</ul>
					</li>
				</ul>
			</nav>

		<!-- Footer -->
			<footer id="footer">
				<p class="copyright">&copy; 뮤즐리 주식회사</p>
			</footer>
	</div>
</div>