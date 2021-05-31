
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- Section -->
<section>
  <header class="major">
    <h2><a href="<c:url value="/board/notice"/>">${brd_name}</a></h2>
  </header>
  <div class="table-wrapper">
    <table>
      <thead>
      <tr>
        <th><spring:message code="table.title"/> </th>
        <th><spring:message code="table.writer"/></th>
        <th><spring:message code="table.hit"/></th>
        <th><spring:message code="table.dateWrite"/></th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${posts}" var="post">
        <tr>
          <td><a href="<c:url value="/board/${brd_key}/info/${post.post_id}?page=1"/>">${post.post_title}</a>
            <c:if test="${post.post_device == 2}"><i class="fas fa-mobile-alt"></i> </c:if>
            <a class="text-primary" href="<c:url value="/board/${brd_key}/info/${post.post_id}?page=1#comment-box"/>">[${post.post_comment_count}]</a>
          </td>
          <td>${post.post_nickname}</td>
          <td>${post.post_hit}</td>
          <td><span id="post_date${post.post_id}" class="text-mute" style="float: right"></span></li>
            <script>document.getElementById('post_date${post.post_id}').innerHTML = timeForToday('${post.post_datetime}');</script></td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</section>
<hr>
