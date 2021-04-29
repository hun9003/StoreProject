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
					<h2>회원가입</h2>
				</header>
				<p id="alertMsg" class="text-danger"></p>
				<form method="post" onsubmit="return join()">
					<div class="row gtr-uniform">
						<div class="col-9 col-12-xsmall">
							<input type="text" name="mem_userid" id="mem_userid" value="" placeholder="아이디" maxlength="12" required="required">
						</div>
						<div class="col-3 col-12-xsmall">
							<a href="javascript:void(0);" class="button primary icon solid fa-search" onclick="checkDupId()">중복확인</a>
							<input type="hidden" id="dupid" value="0">
						</div>
						<div class="col-6 col-12-xsmall">
							<input type="password" name="mem_password" id="mem_password" value="" placeholder="비밀번호" maxlength="30" required="required">
						</div>
						<div class="col-6 col-12-xsmall">
							<input type="password" name="mem_password2" id="mem_password2" value="" placeholder="비밀번호확인" maxlength="30" required="required">
						</div>
						<div class="col-9 col-12-xsmall">
							<input type="text" name="mem_nickname" id="mem_nickname" value="" placeholder="닉네임" maxlength="20" required="required">
						</div>
						<div class="col-3 col-12-xsmall">
							<a href="javascript:void(0);" class="button primary icon solid fa-search" onclick="checkDupName()">중복확인</a>
							<input type="hidden" id="dupname" value="0">
						</div>
						<div class="col-9 col-12-xsmall">
							<input type="email" name="mem_email" id="mem_email" value="" placeholder="이메일" maxlength="100" required="required">
						</div>
						<div class="col-3 col-12-small">
							<input type="checkbox" id="mem_receive_email" name="mem_receive_email" value="1">
							<label for="mem_receive_email">이메일 수신 여부</label>
						</div>
						<div class="col-9 col-12-xsmall">
							<input type="text" name="mem_phone" id="mem_phone" value="" placeholder="연락처 '-'포함" required="required">
						</div>
						<div class="col-3 col-12-small">
							<input type="checkbox" id="mem_receive_sms" name="mem_receive_sms" value="1">
							<label for="mem_receive_sms">SMS 수신 여부</label>
						</div>
						<!-- Break -->
						<div class="col-6 col-12-small">
							<input type="radio" id="mem_gender1" name="mem_gender" value="1" checked="checked">
							<label for="mem_gender1">남자</label>
						</div>
						<div class="col-6 col-12-small">
							<input type="radio" id="mem_gender2" name="mem_gender" value="2">
							<label for="mem_gender2">여자</label>
						</div>
						<!-- Break -->
						<div class="col-12">
							<ul class="actions">
								<li><input type="submit" value="가입하기" class="primary"></li>
								<li><input type="reset" value="다시작성"></li>
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
<script type="text/javascript" src="<c:url value="/resources/js/join.js"/>"></script>
