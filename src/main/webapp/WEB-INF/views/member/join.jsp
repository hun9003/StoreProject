<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:import url="/top"/>
<!-- Wrapper -->
<div id="wrapper">
	<!-- Main -->
		<div id="main">
			<div class="inner">
				<c:import url="/header"/>
				
				<section>
				<header class="major">
					<h2><spring:message code="title.join"/></h2>
				</header>
				<p id="alertMsg" class="text-danger"></p>
				<form method="post" onsubmit="return join()">
					<div class="row gtr-uniform">
						<div class="col-9 col-12-xsmall">
							<input type="text" name="mem_userid" id="mem_userid" value="" placeholder="<spring:message code="label.id"/>" maxlength="12" required="required">
						</div>
						<div class="col-3 col-12-xsmall">
							<a href="javascript:void(0);" class="button primary icon solid fa-search" onclick="checkDupId()"><spring:message code="button.duplicationCheck"/></a>
							<input type="hidden" id="dupid" value="0">
						</div>
						<div class="col-6 col-12-xsmall">
							<input type="password" name="mem_password" id="mem_password" value="" placeholder="<spring:message code="label.password"/>" maxlength="30" required="required">
						</div>
						<div class="col-6 col-12-xsmall">
							<input type="password" name="mem_password2" id="mem_password2" value="" placeholder="<spring:message code="label.passwordCheck"/>" maxlength="30" required="required">
						</div>
						<div class="col-12 col-12-large m-b-20 txt-left">
							<input type="checkbox" id="pass_show" value="1" onchange="pass_show_toggle()"><label for="pass_show"><spring:message code="label.passwordShow"/></label>
						</div>
						<div class="col-9 col-12-xsmall">
							<input type="text" name="mem_nickname" id="mem_nickname" value="" placeholder="<spring:message code="label.nickname"/>" maxlength="20" required="required">
						</div>
						<div class="col-3 col-12-xsmall">
							<a href="javascript:void(0);" class="button primary icon solid fa-search" onclick="checkDupName()"><spring:message code="button.duplicationCheck"/></a>
							<input type="hidden" id="dupname" value="0">
						</div>
						<div class="col-9 col-12-xsmall">
							<input type="email" name="mem_email" id="mem_email" value="" placeholder="<spring:message code="label.email"/>" maxlength="100" required="required">
						</div>
						<div class="col-3 col-12-small">
							<input type="checkbox" id="mem_receive_email" name="mem_receive_email" value="1">
							<label for="mem_receive_email"><spring:message code="label.receiveEmail"/></label>
						</div>
						<div class="col-9 col-12-xsmall">
							<input type="text" name="mem_phone" id="mem_phone" value="" placeholder="<spring:message code="label.phonePh"/>" required="required">
						</div>
						<div class="col-3 col-12-small">
							<input type="checkbox" id="mem_receive_sms" name="mem_receive_sms" value="1">
							<label for="mem_receive_sms"><spring:message code="label.receiveSms"/></label>
						</div>
						<!-- Break -->
						<div class="col-6 col-12-small">
							<input type="radio" id="mem_gender1" name="mem_gender" value="1" checked="checked">
							<label for="mem_gender1"><spring:message code="label.genderMen"/></label>
						</div>
						<div class="col-6 col-12-small">
							<input type="radio" id="mem_gender2" name="mem_gender" value="2">
							<label for="mem_gender2"><spring:message code="label.genderWomen"/></label>
						</div>
						<!-- Break -->
						<div class="col-12">
							<ul class="actions">
								<li><input type="submit" value="<spring:message code="button.submitJoin"/>" class="primary"></li>
								<li><input type="reset" value="<spring:message code="button.reset"/>"></li>
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
