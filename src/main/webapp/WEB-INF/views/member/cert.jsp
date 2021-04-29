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
				
				<section id="sandMailBox">
				<header class="major">
					<h2>이메일 인증</h2>
				</header>
				<p class="text-info text-center text-bold">회원님의 이메일 ${mem_email } 로 인증코드를 전송하시겠습니까?</p>
				<div class="col-12 col-12-xsmall text-center">
				<a href="javascript:void(0);" class="button primary icon solid fa-search sandBtn" onclick="send_mail('send')">이메일 코드 전송</a>
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
									alert('이메일에 코드를 전송했습니다.');
									$('#sandMailBox').load('<c:url value="/certForm"/>');
								} else {
									alert('이메일 전송에 실패했습니다. 관리자에게 문의하세요');
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
