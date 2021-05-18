<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
					<li><a href="<c:url value="home"/>"><spring:message code="menu.home"/></a></li>
					<li><a href="generic.html"><spring:message code="menu.notice"/></a></li>
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
					<h2><spring:message code="footer.title"/></h2>
				</header>
				<p><spring:message code="footer.description"/></p>
				<ul class="contact">
					<li class="icon solid fa-envelope"><a href="#"><spring:message code="footer.email"/></a></li>
					<li class="icon solid fa-phone"><spring:message code="footer.tel"/></li>
					<li class="icon solid fa-home"><spring:message code="footer.city"/></li>
				</ul>
			</section>
		<!-- Footer -->
			<footer id="footer">
				<p class="copyright"><spring:message code="footer.company"/></p>
			</footer>
	</div>
</div>