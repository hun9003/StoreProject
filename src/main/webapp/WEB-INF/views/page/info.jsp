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
            <!-- Section -->
            <section>
                <c:set var="member" value="${sessionScope.member}"/>
                <header class="major">
                    <h2><a href="<c:url value="/board/${boardBean.brd_key}?page=${param.page != null ? param.page : 1}"/>">${boardBean.brd_name}</a></h2>
                </header>
                <hr>
                <script src="<c:url value="/resources/js/calculateTime.js"/>"></script>
                <h3>${postBean.post_title}</h3>
                <div class="row">
                    <div class="col-3 col-3-medium">
                        <p class="text-info">${postBean.post_nickname}</p>
                    </div>
                    <div class="col-3 col-3-medium">
                        <p class="text-mute"><spring:message code="table.likes"/> ${postBean.post_like - postBean.post_dislike}</p>
                    </div>
                    <div class="col-3 col-3-medium">
                        <p class="text-mute"><spring:message code="table.hit"/> ${postBean.post_hit}</p>
                    </div>
                    <div class="col-3 col-3-medium">
                       <p class="text-mute"><spring:message code="table.dateWrite"/> <fmt:formatDate value="${postBean.post_datetime}" pattern="yy-MM-dd HH:mm"/></p>
                    </div>
                </div>
                <div class="box" style="min-height: 500px; position: relative;">
                    <div id="content-box">
                        ${postBean.post_content}
                    </div>
                </div>
                <div class="box">
                    <div id="like-box">
                        <div class="align-center">
                            <i id="like" class="icon fa-thumbs-up fa-3x solid <c:choose>
                                <c:when test="${likedBean.lik_type == 1}">active-btn</c:when>
                                <c:otherwise>hover-btn</c:otherwise>
                            </c:choose>" style="margin: 30px;"></i>
                            <i id="hate" class="icon fa-thumbs-down fa-3x solid <c:choose>
                                <c:when test="${likedBean.lik_type == 2}">active-btn</c:when>
                                <c:otherwise>hover-btn</c:otherwise>
                            </c:choose>" style="margin: 30px"></i>
                        </div>
                    </div>
                </div>
                <div id="comment-box"></div>
                <div class="btn-container">
                    <c:if test="${member.mem_id == postBean.mem_id}">
                        <button onclick="location.href = '<c:url value="/board/${boardBean.brd_key}/update?post_id=${postBean.post_id}"/>'"><spring:message code="button.update"/></button>
                        <button onclick="location.href = '<c:url value="/board/${boardBean.brd_key}/delete?post_id=${postBean.post_id}"/>'"><spring:message code="button.delete"/></button>
                    </c:if>
                    <button class="primary" onclick="location.href = '<c:url value="/board/${boardBean.brd_key}?page=${pageBean.currentPage}"/>'"><spring:message code="button.list"/></button>
                </div>
            </section>
        </div>
    </div>
    <c:import url="/side"/>
</div>
<script>
    $(document).ready(function (){
        $("#comment-box").load('<c:url value="/comment/${post_id}?page=1&brd_key=${brd_key}"/>');

        $("#like").click(function(){
            let post_id = '${postBean.post_id}';
            let like = $("#like");
            let hate = $("#hate");

            $.ajax('<c:url value="/like"/>', {
                data: {post_id : post_id},
                success: function (data) {
                    switch (data) {
                        case 'likeOn':
                            like.removeClass('hover-btn');
                            like.addClass('active-btn');
                            break;
                        case 'likeOff':
                            like.removeClass('active-btn');
                            like.addClass('hover-btn');
                            break;
                        case 'likeToggle':
                            hate.removeClass('active-btn');
                            hate.addClass('hover-btn');
                            like.removeClass('hover-btn');
                            like.addClass('active-btn');
                            break;
                        case 'nologin':
                            let isLogin = confirm("로그인이 필요한 시스템입니다. 로그인 하시겠습니까?");
                            if(isLogin) {
                                location.href = '<c:url value="/login"/>';
                            }
                            break;
                    }
                }
            })
        })
        $("#hate").click(function(){
            let post_id = '${postBean.post_id}';
            let like = $("#like");
            let hate = $("#hate");
            $.ajax('<c:url value="/hate"/>', {
                data: {post_id : post_id},
                success: function (data) {
                    switch (data) {
                        case 'hateOn':
                            hate.removeClass('hover-btn');
                            hate.addClass('active-btn');
                            break;
                        case 'hateOff':
                            hate.removeClass('active-btn');
                            hate.addClass('hover-btn');
                            break;
                        case 'hateToggle':
                            like.removeClass('active-btn');
                            like.addClass('hover-btn');
                            hate.removeClass('hover-btn');
                            hate.addClass('active-btn');
                            break;
                        case 'nologin':
                            let isLogin = confirm("로그인이 필요한 시스템입니다. 로그인 하시겠습니까?");
                            if(isLogin) {
                                location.href = '<c:url value="/login"/>';
                            }
                            break;
                    }
                }
            })
        })
    })

    function commentPaging(url) {
        $("#comment-box").load(url);
    }
</script>
<c:import url="/bottom"/>