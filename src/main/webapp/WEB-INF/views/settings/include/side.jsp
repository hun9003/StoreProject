<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- Sidebar -->
<div id="sidebar">
	<div class="inner">
		<!-- Menu -->
			<nav id="menu">
				<header class="major">
					<h2>Menu</h2>
				</header>
				<ul>
					<li><a href="<c:url value="/home"/>"><spring:message code="button.main"/></a></li>
					<li>
						<span class="opener"><spring:message code="label.memberSetting"/></span>
						<ul>
							<li><a href="<c:url value="/settings/members"/>"><spring:message code="title.settingMemberList"/></a></li>
						</ul>
					</li>
					<li>
						<span class="opener"><spring:message code="title.settingMenu"/></span>
						<ul>
							<li><a href="<c:url value="/settings/menu-group"/>"><spring:message code="title.settingMenuGroup"/></a></li>
							<li><a href="<c:url value="/settings/menu"/>"><spring:message code="title.settingMenu"/></a></li>
						</ul>
					</li>
					<li>
						<span class="opener"><spring:message code="title.settingBoard"/></span>
						<ul>
							<li><a href="<c:url value="/settings/board"/>"><spring:message code="title.settingBoard"/></a></li>
						</ul>
					</li>
				</ul>
			</nav>

		<!-- Footer -->
			<footer id="footer">
				<p class="copyright"><spring:message code="footer.company"/></p>
			</footer>
	</div>
</div>