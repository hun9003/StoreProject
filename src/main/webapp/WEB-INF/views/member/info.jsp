<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:import url="/top"/>
<!-- Wrapper -->
<div id="wrapper">
	<!-- Main -->
		<div id="main">
			<div class="inner">
				<c:import url="/header"/>
				<!-- Banner -->
					<section>
						<header class="main">
							<h2><spring:message code="label.memberSetting"/> &gt; <spring:message code="title.${subTitle}"/> - ${member.mem_userid}</h2>
						</header>
						<hr>
						<div class="content">
							<section id="profile">
								<div class="box">
									<h3><spring:message code="label.basicInfo"/></h3>
									<p><spring:message code="msg.settingMemberBasic"/></p>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info"><spring:message code="label.photo"/></div>
										<div class="col-6 col-6-large text-bold"><spring:message code="msg.settingMemberPhoto"/> <a href="#" class="text-primary">변경하기</a> </div>
										<div class="col-3 col-3-large"><img src="<c:url value="/resources/images/profiles/no-profile.png"/>" alt="" style="width: 40%; border-radius: 50%;"></div>
									</div>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info"><spring:message code="label.id"/></div>
										<div class="col-9 col-9-large text-bold">${member.mem_userid}</div>
									</div>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info"><spring:message code="label.nickname"/></div>
										<div class="col-9 col-9-large text-bold">${member.mem_nickname}</div>
									</div>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info"><spring:message code="label.gender"/></div>
										<div class="col-9 col-9-large text-bold"><c:choose>
											<c:when test="${member.mem_gender == 1}"><spring:message code="label.genderMen"/></c:when>
											<c:when test="${member.mem_gender == 2}"><spring:message code="label.genderWomen"/></c:when>
											<c:otherwise><spring:message code="label.unknown"/></c:otherwise>
										</c:choose></div>
									</div>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info"><spring:message code="label.password"/></div>
										<div class="col-9 col-9-large text-bold"><a href="#" class="text-primary"><spring:message code="button.passwordChange"/></a></div>
									</div>
								</div>
							</section>
							<section id="profile_sub">
								<div class="box">
									<h3><spring:message code="label.telInfo"/></h3>
									<p><spring:message code="msg.settingMemberTelInfo"/> </p>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info"><spring:message code="label.email"/></div>
										<div class="col-9 col-9-large text-bold">${member.mem_email}</div>
									</div>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info"><spring:message code="label.phone"/></div>
										<div class="col-9 col-9-large text-bold">${member.mem_phone}</div>
									</div>
								</div>
							</section>
							<section id="profile_sub2">
								<div class="box">
									<h3><spring:message code="label.memberInfo"/></h3>
									<p><spring:message code="msg.settingMemberSiteInfo"/></p>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info"><spring:message code="label.memberPermission"/></div>
										<div class="col-9 col-9-large text-bold"><c:choose>
											<c:when test="${member.mem_is_admin == 1}"><spring:message code="permission.admin"/></c:when>
											<c:otherwise><spring:message code="permission.public"/></c:otherwise>
										</c:choose></div>
									</div>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info"><spring:message code="label.level"/></div>
										<div class="col-9 col-9-large text-bold">
											<div class="row">
												<div class="col-6">
													${member.mem_level}
												</div>
												<div class="col-6">
													<spring:message code="label.exp" arguments="${member.mem_point}"/>
													<div class="graph-box"><div class="graph" style="width: ${member.mem_point}%;"></div></div></div>
												</div>
											</div>
									</div>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info"><spring:message code="label.writePost"/></div>
										<div class="col-6 col-6-large text-bold"><spring:message code="label.count" arguments="0"/></div>
										<div class="col-3 col-3-large text-bold"><a href="#" class="text-primary"><spring:message code="label.allView"/></a></div>
									</div>
									<hr>
									<div class="row">
										<div class="col-3 col-3-large text-info"><spring:message code="label.writeComment"/></div>
										<div class="col-6 col-6-large text-bold"><spring:message code="label.count" arguments="0"/></div>
										<div class="col-3 col-3-large text-bold"><a href="#" class="text-primary"><spring:message code="label.allView"/></a></div>
									</div>
								</div>
							</section>
						</div>

					</section>
			</div>
		</div>
	<c:import url="/side"/>
</div>
<c:import url="/bottom"/>