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
                    <h2>${boardBean.brd_name} <spring:message code="button.write"/></h2>
                </header>
                <hr>
                <div class="box">
                    <form method="post">
                        <div class="row gtr-uniform">
                            <div class="col-12 col-12-xsmall">
                                <label for="post_title"><spring:message code="label.title"/></label>
                                <input type="text" name="post_title" id="post_title" value="" placeholder="<spring:message code="label.title"/>" required="required">
                                <hr>
                                <script type="text/javascript" src="<c:url value="/resources/plugin/ckeditor/ckeditor.js"/>"></script>
                                <textarea class="form-control" id="p_content" name="post_content"></textarea>
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
                        <script>
                            var filter = "win16|win32|win64|mac|macintel";
                            if ( navigator.platform ) {
                                if ( filter.indexOf( navigator.platform.toLowerCase() ) < 0 ) {
                                    //mobile alert('mobile 접속');
                                    } else {
                                    //pc alert('pc 접속');
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