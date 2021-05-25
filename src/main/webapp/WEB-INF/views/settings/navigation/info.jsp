<%--
  Created by IntelliJ IDEA.
  User: HOME
  Date: 2021-05-04
  Time: 오후 3:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set value="/settings/menu-create" var="formUrl"/>
<c:set value="create" var="btnValue"/>
<c:if test="${menuTitle == null}">
  <c:set value="/settings/menu-info/${menu.men_id}" var="formUrl"/>
  <c:set value="update" var="btnValue"/>
  <c:choose>
    <c:when test="${menu.men_parent != null}">
      <c:set var="menuTitle" value="settingMenuUpdate"/>
    </c:when>
    <c:otherwise>
      <c:set var="menuTitle" value="settingMenuGroupUpdate"/>
    </c:otherwise>
  </c:choose>
</c:if>

<h3 class="text-info"><spring:message code="title.${menuTitle}"/></h3>
<hr>
<form action="<c:url value="${formUrl}"/>" method="POST">
  <div class="row gtr-uniform">
    <div class="col-9 col-12-xsmall">
      <label for="men_name"><spring:message code="label.name"/></label>
      <input type="text" name="men_name" id="men_name" value="${menu.men_name}" required>
    </div>
    <c:if test="${menu.men_parent != null || menuType eq 'men'}">
      <input type="hidden" name="men_parent" value="${menuParent}">
    <div class="col-9 col-12-xsmall">
      <label for="men_link"><spring:message code="label.link"/></label>
      <input type="text" name="men_link" id="men_link" value="${menu.men_link}" required>
    </div>
    <div class="col-9 col-12-xsmall">
      <input type="checkbox" id="men_target" name="men_target" value="_blank" <c:if test="${menu.men_target eq '_blank'}">checked</c:if>>
      <label for="men_target"><spring:message code="label.menuTarget"/></label>
    </div>
    </c:if>
    <div class="col-9 col-12-xsmall">
      <input type="checkbox" id="men_desktop" name="men_desktop" value="1" <c:if test="${menu.men_desktop == 1}">checked</c:if>>
      <label for="men_desktop"><spring:message code="label.menuDesktop"/></label>
    </div>
    <div class="col-9 col-12-xsmall">
      <input type="checkbox" id="men_mobile" name="men_mobile" value="1" <c:if test="${menu.men_mobile == 1}">checked</c:if>>
      <label for="men_mobile"><spring:message code="label.menuMobile"/></label>
    </div>
    <div class="col-12 col-12-medium">
      <input type="submit" style="width: 100%;" value="<spring:message code="button.${btnValue}"/>" class="primary">
      <c:if test="${menu != null}">
        <hr>
        <input type="button" style="width: 100%;" value="<spring:message code="button.delete"/>" class="warning" onclick="menuDelete('${menu.men_id}')">
      </c:if>
    </div>
  </div>

  </form>



