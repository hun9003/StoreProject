<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/settings/top"/>
<!-- Wrapper -->
<div id="wrapper">
	<!-- Main -->
		<div id="main">
			<div class="inner">
				<c:import url="/settings/header"/>
				<!-- Banner -->
					<section>
						<header class="main">
							<h2>회원관리 > ${subTitle} - ${memberInfo.mem_userid}</h2>
						</header>
						<hr>
						<div class="content">
							<section id="profile">
								<div class="box">
									<h3>기본 정보</h3>
									<p>일부 정보는 다른 사람에게 표시 될 수 있습니다.</p>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info">사진</div>
										<div class="col-6 col-6-large text-bold">프로필 사진을 설정하실수 있습니다 <a href="#" class="text-primary">변경하기</a> </div>
										<div class="col-3 col-3-large"><img src="<c:url value="/resources/images/profiles/no-profile.png"/>" alt="" style="width: 40%; border-radius: 50%;"></div>
									</div>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info">아이디</div>
										<div class="col-9 col-9-large text-bold">${memberInfo.mem_userid}</div>
									</div>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info">닉네임</div>
										<div class="col-9 col-9-large text-bold">${memberInfo.mem_nickname}</div>
									</div>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info">성별</div>
										<div class="col-9 col-9-large text-bold"><c:choose>
											<c:when test="${memberInfo.mem_gender == 1}">남성</c:when>
											<c:when test="${memberInfo.mem_gender == 2}">여성</c:when>
											<c:otherwise>알 수 없음</c:otherwise>
										</c:choose></div>
									</div>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info">비밀번호</div>
										<div class="col-9 col-9-large text-bold"><a href="#" class="text-primary">비밀번호 변경하기</a></div>
									</div>
								</div>
							</section>
							<section id="profile_sub">
								<div class="box">
									<h3>연락처 정보</h3>
									<p>다른 사람에게 표시 되지 않습니다.</p>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info">이메일</div>
										<div class="col-9 col-9-large text-bold">${memberInfo.mem_email}</div>
									</div>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info">전화번호</div>
										<div class="col-9 col-9-large text-bold">${memberInfo.mem_phone}</div>
									</div>
								</div>
							</section>
							<section id="profile_sub2">
								<div class="box">
									<h3>회원 정보</h3>
									<p>사이트 내에서 제공되는 정보 입니다.</p>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info">회원등급</div>
										<div class="col-9 col-9-large text-bold"><c:choose>
											<c:when test="${memberInfo.mem_is_admin == 1}">관리자</c:when>
											<c:otherwise>일반회원</c:otherwise>
										</c:choose></div>
									</div>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info">레벨</div>
										<div class="col-9 col-9-large text-bold">${memberInfo.mem_level}</div>
									</div>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info">작성한 게시물</div>
										<div class="col-6 col-6-large text-bold">0 개</div>
										<div class="col-3 col-3-large text-bold"><a href="#" class="text-primary">모두보기</a></div>
									</div>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info">작성한 댓글</div>
										<div class="col-6 col-6-large text-bold">0 개</div>
										<div class="col-3 col-3-large text-bold"><a href="#" class="text-primary">모두보기</a></div>
									</div>
								</div>
							</section>
						</div>

					</section>
			</div>
		</div>
	<c:import url="/settings/side"/>
</div>
<c:import url="/settings/bottom"/>