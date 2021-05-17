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
<c:set value="/settings/board-create" var="formUrl"/>
<c:set value="생성하기" var="btnValue"/>
<c:if test="${board != null}">
  <c:set value="/settings/board-info/${board.brd_id}" var="formUrl"/>
  <c:set value="수정하기" var="btnValue"/>
</c:if>

<h3 class="text-info">게시판</h3>
<hr>
<form action="<c:url value="${formUrl}"/>" method="POST">
  <div class="row gtr-uniform">
    <div class="col-9 col-12-xsmall">
      <label for="brd_name">이름</label>
      <input type="text" name="brd_name" id="brd_name" value="${board.brd_name}" required>
    </div>
    <div class="col-9 col-12-xsmall">
      <label for="brd_mobile_name">모바일 화면 이름</label>
      <input type="text" name="brd_mobile_name" id="brd_mobile_name" value="${board.brd_mobile_name}" required>
    </div>
    <div class="col-9 col-12-xsmall">
      <label for="brd_key">링크</label>
      <input type="text" name="brd_key" id="brd_key" value="${board.brd_key}" required>
    </div>
    <div class="col-12 col-12-medium">
      <input type="submit" style="width: 100%;" value="${btnValue}" class="primary">
        <hr>
      <c:if test="${board != null}">
        <input type="button" style="width: 100%;" value="삭제하기" class="warning" onclick="boardDelete('${board.brd_id}')">
      </c:if>
    </div>
  </div>

  </form>



