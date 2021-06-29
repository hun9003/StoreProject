<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
                    <h2><spring:message code="title.${subTitle}"/></h2>
                </header>
                <div class="box text-center" style="max-width: 500px; margin: 0 auto;">
                    <form method="post" enctype="multipart/form-data">
                        <div class="col-12 col-12-large m-b-20"><img id="profile_photo" class="profile_photo" src="<c:choose>
										<c:when test="${sessionScope.member.mem_photo != null}"><c:url value="${sessionScope.member.mem_photo}"/></c:when>
										<c:otherwise><c:url value="/resources/images/profiles/no-profile.png"/></c:otherwise>
										</c:choose>" alt="profile_photo"></div>
                        <div class="col-12 col-12-large text-info m-b-20">
                            <div class="text-primary text-bold m-b-20">${sessionScope.member.mem_userid}</div>
                            <div class="m-b-20"><spring:message code="msg.changePhoto"/></div>
                            <div class="m-b-40">
                                <a class="text-primary m-r-10" href="javascript:void(0)" onclick="get_photo()"><spring:message code="button.getPhoto"/></a>
                                <a class="text-danger" href="javascript:void(0)" onclick="delete_photo()"><spring:message code="button.deletePhoto"/></a>
                            </div>
                            <input type="file" style="display: none;" id="mem_photo" name="mem_photo">
                        </div>
                        <div class="col-12 col-12-large m-tb-20 txt-right">
                            <a class="m-r-20" href="<c:url value="/member/info"/>"><spring:message code="button.back"/></a>
                            <input class="primary" type="submit" value="<spring:message code="button.change"/>">
                        </div>

                    </form>
                </div>
            </section>

        </div>
    </div>
    <c:import url="/side"/>
</div>
<script>
    $('#mem_photo').on('change', function(e){
        //console.log(e);
        const [file] = $(this)[0].files
        if (file) {
            $('#profile_photo').prop('src', URL.createObjectURL(file));
        }
    });

    function get_photo() {
        if (confirm('<spring:message code="msg.selectPhoto"/>')) {
            $('#mem_photo').click();
        }
    }

    function delete_photo() {
        if (confirm('<spring:message code="msg.deletePhoto"/>')) {
            $('#profile_photo').prop('src', '<c:url value="/resources/images/profiles/no-profile.png"/>');
            $("#mem_photo").val("");
        }
    }

</script>
<c:import url="/bottom"/>