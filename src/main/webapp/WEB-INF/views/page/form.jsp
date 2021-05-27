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
                <header class="major">
                    <h2>${boardBean.brd_name}
                        <c:choose>
                            <c:when test="${postBean != null}"><spring:message code="button.update"/></c:when>
                            <c:otherwise><spring:message code="button.write"/></c:otherwise>
                        </c:choose>
                    </h2>
                </header>
                <hr>
                <script src="<c:url value="/resources/js/calculateTime.js"/>"></script>
                <div class="box">
                    <form method="post">
                        <div class="row gtr-uniform">
                            <div class="col-12 col-12-xsmall">
                                <input type="hidden" name="post_id" value="${postBean.post_id}">
                                <label for="post_title"><spring:message code="label.title"/></label>
                                <input type="text" name="post_title" id="post_title" value="${postBean.post_title}" placeholder="<spring:message code="label.title"/>" required="required">
                                <hr>
                                <script type="text/javascript" src="<c:url value="/resources/plugin/ckeditor/ckeditor.js"/>"></script>
                                <textarea class="form-control" id="p_content" name="post_content">${postBean.post_content}</textarea>
                                <script type="text/javascript">
                                    CKEDITOR.replace('p_content', {
                                        // height: 500,
                                        filebrowserUploadUrl : '<c:url value="/imageUpload"/>'
                                        });
                                </script>
                                <hr>
                                <div class="btn-container">
                                    <input class="primary" type="submit" value="<spring:message code="button.submitWrite"/>">
                                </div>
                                </div>
                        </div>
                        <input type="hidden" id="post_device" name="post_device">
                        <script>
                            let filter = "win16|win32|win64|mac|macintel";
                            if ( navigator.platform ) {
                                if ( filter.indexOf( navigator.platform.toLowerCase() ) < 0 ) {
                                    //mobile alert('mobile 접속');
                                    document.getElementById('post_device').value = 2;
                                    } else {
                                    //pc alert('pc 접속');
                                    document.getElementById('post_device').value = 1;
                                    }
                                }
                        </script>
                    </form>
                </div>
            </section>
        </div>
    </div>
    <c:import url="/side"/>
</div>
<c:import url="/bottom"/>