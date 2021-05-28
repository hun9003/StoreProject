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
				
				<section id="sandMailBox">
				<header class="major">
					<h2><spring:message code="title.cert"/></h2>
				</header>
				<p class="text-info text-center text-bold"><spring:message code="msg.cert" arguments="${mem_email}"/></p>
				<div class="col-12 col-12-xsmall text-center">
				<a href="javascript:void(0);" class="button primary icon solid fa-search sandBtn" onclick="send_mail('send')"><spring:message code="button.send"/></a>
				</div>
				
				
				
				</section>
				<script type="text/javascript">
				function send_mail(type){
						var url = '';
						if(type == 'resend'){
							url = 'sendMail?resand=true'
						} else {
							url = 'sendMail'
						}
						var mem_email = '${mem_email}';
						$('.sandBtn').addClass('disabled');
						$.ajax(url,{
							data:{mem_email:mem_email},
							success:function(data) {
								if(data=='result') {
									alert('<spring:message code="msg.completeSend"/>');
									$('#sandMailBox').load('<c:url value="/certForm"/>');
								} else {
									alert('<spring:message code="msg.faleSend"/>');
									location.href = '<c:url value="/home"/>';
								}
							}
						})
				}
				</script>
			</div>
		</div>
	<c:import url="/side"/>
</div>
<c:import url="/bottom"/>
