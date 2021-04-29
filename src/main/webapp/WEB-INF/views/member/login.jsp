<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/top"/>
<!-- Wrapper -->
<div id="wrapper">
	<!-- Main -->
		<div id="main">
			<div class="inner">
				<c:import url="/header"/>
				
				<section>
				<header class="major">
					<h2>로그인</h2>
				</header>
				<form method="post">
					<div class="row gtr-uniform">
						<div class="col-12 col-12-xsmall">
							<input type="text" name="mem_userid" id="mem_userid" value="" placeholder="아이디" required="required">
						</div>
						<div class="col-12 col-12-xsmall">
							<input type="password" name="mem_password" id="mem_password" value="" placeholder="비밀번호" required="required">
						</div>
						<!-- Break -->
						<div class="col-12">
							<ul class="actions">
								<li><input type="submit" value="로그인" class="primary"></li>
								<li><a href="#">아이디 혹은 비밀번호를 잊으셨나요?</a></li>
							</ul>
						</div>
					</div>
				</form>
				</section>
				
			</div>
		</div>
	<c:import url="/side"/>
</div>
<c:import url="/bottom"/>