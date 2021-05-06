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
					<section id="banner">
						<div class="content">
							<c:choose>
								<c:when test="${isMenuGroup == true}">
									<c:set value="meg" var="menuType"/>
								</c:when>
								<c:otherwise>
									<c:set value="men" var="menuType"/>
								</c:otherwise>
							</c:choose>
							<header>
								<h2>메뉴관리 - ${subTitle}</h2>
							</header>
							<div class="content">
								<section>
									<div class="box">

										<h3>${subTitle}</h3>
										<p>${subTitle}을 생성하거나 수정&amp;삭제 하실 수 있습니다.</p>

										<c:choose>
											<c:when test="${isMenuGroup == true}">
												<a href="javascript:void(0)" class="text-primary" onclick="menuGroupCreate('${menuType}')">생성하기</a>
											</c:when>
											<c:when test="${menuParent != null}">
												<a href="javascript:void(0)" class="text-primary" onclick="menuCreate('${menuType}', '${menuParent}')">생성하기</a>
											</c:when>
										</c:choose>
										<hr>

										<div class="row">
											<div class="col-6 col-6-xlarge">

												<ul class="alt">
													<c:choose>
														<c:when test="${isMenuGroup == true || menuParent != null}">
															<c:if test="${menuParent != null}">
																<select name="menuParent" id="menuParent" onchange="meg_change(this.value)">
																	<option value="">메뉴그룹선택</option>
																	<c:forEach var="meg" items="${menus}">
																		<c:if test="${meg.men_parent == null}">
																			<option value="${meg.men_id}" <c:if test="${menuParent == meg.men_id}">selected</c:if>>${meg.men_name}</option>
																		</c:if>
																	</c:forEach>
																</select>
																<br>
															</c:if>
															<c:forEach items="${menus}" var="menu">
																<c:choose>
																	<c:when test="${isMenuGroup == true}">
																		<c:if test="${menu.men_parent == null}">
																			<li><span id="menu${menu.men_id}" class="hov menu-item">${menu.men_name}</span>
																				<span style="float: right;">
																	<i id="menu-up${menu.men_id}" class="far fa-arrow-alt-circle-up menu-controller"></i>
																	<i id="menu-down${menu.men_id}" class="far fa-arrow-alt-circle-down menu-controller"></i>
																</span></li>
																		</c:if>
																	</c:when>
																	<c:when test="${isMenuGroup == false && menuParent != null}">
																		<c:if test="${menu.men_parent == menuParent}">
																			<li><span id="menu${menu.men_id}" class="hov menu-item">${menu.men_name}</span>
																				<span style="float: right;">
																	<i id="menu-up${menu.men_id}" class="far fa-arrow-alt-circle-up menu-controller"></i>
																	<i id="menu-down${menu.men_id}" class="far fa-arrow-alt-circle-down menu-controller"></i>
																</span></li>
																		</c:if>
																	</c:when>
																</c:choose>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<label for="menuParent" class="text-info">조회할 메뉴의 메뉴 그룹을 선택해주세요</label>
															<select name="menuParent" id="menuParent" onchange="meg_change(this.value)">
																<option value="">메뉴그룹선택</option>
																<c:forEach var="meg" items="${menus}">
																	<c:if test="${meg.men_parent == null}">
																		<option value="${meg.men_id}">${meg.men_name}</option>
																	</c:if>
																</c:forEach>
															</select>
														</c:otherwise>
													</c:choose>
												</ul>

											</div>
											<div class="col-6 col-6-xlarge">
												<section id="menu_container">
												<p class="text-info">메뉴 정보를 확인 하실 수 있습니다 메뉴를 클릭하세요.</p>
												</section>
											</div>
										</div>
									</div>
								</section>
							</div>
						</div>
					</section>
			</div>
		</div>
	<c:import url="/settings/side"/>
</div>
<c:import url="/settings/bottom"/>
<script>
// 메뉴 클릭시 정보 호출
	$(document).on('click', '.menu-item', function (){
		let men_id = $(this).attr('id').replaceAll('menu', '');
		$('#menu_container').load('<c:url value="/settings/menu-info/'+men_id+'"/> ');
	});

	// 메뉴 순서 변경 관련
	$(document).on('mouseover', '.menu-controller', function (){
		$(this).removeClass('far');
		$(this).addClass('fas');
	});
	$(document).on('mouseout', '.menu-controller', function (){
		$(this).removeClass('fas');
		$(this).addClass('far');
	});

	$(document).on('click', '.menu-controller', function (){
		let url = '<c:url value="/settings/menu-order/"/>';
		let getId = $(this).attr('id');
		let isUp = false;
		let orderType = 'down';
		if(getId.indexOf('menu-up') !== -1) {
			isUp = true;
			orderType = 'up';
		}
		let getIndex;
		if(isUp) {
			getIndex = getId.replaceAll('menu-up', '');
		} else {
			getIndex = getId.replaceAll('menu-down', '');
		}
		location.href = url+getIndex+'?orderType='+orderType;

	});

	function menuGroupCreate(type){
		$('#menu_container').load('<c:url value="/settings/menu-create"/>?menuType='+type);
	}

	function menuCreate(type, parent){
		$('#menu_container').load('<c:url value="/settings/menu-create"/>?menuType='+type+'&menuParent='+parent);
	}

	function menuDelete(men_id){
		let cf = confirm("정말로 메뉴를 삭제하시겠습니까?");
		if(cf){
			location.href = '<c:url value="/settings/menu-delete"/>?men_id='+men_id;
		}
	}

	function meg_change(men_id) {
		if(men_id !== ''){
			location.href = '<c:url value="/settings/menu"/>?menuParent='+men_id;
		} else {
			location.href = '<c:url value="/settings/menu"/>';
		}
	}
</script>