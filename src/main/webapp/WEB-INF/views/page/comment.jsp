
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<hr>
<h4 class="text-info"><i class="icon fa-comments solid"></i> <spring:message code="msg.commentInfo" arguments="${pageBean.count}"/></h4>
<hr>
<ul class="alt">
  <c:forEach var="cmt" items="${commentList}">
    <li id="comment_${cmt.cmt_id}" class="<c:if test="${cmt.cmt_reply == 0}">comment-list</c:if>"
            <c:if test="${cmt.cmt_reply > 0}">style="margin-left: 20px;"</c:if>
    >
      <c:if test="${cmt.cmt_reply > 0}"><i class="icon fab fa-replyd"></i> </c:if>
      <span class="<c:choose>
        <c:when test="${cmt.mem_id == postBean.mem_id}">text-primary</c:when>
        <c:otherwise>text-info</c:otherwise>
      </c:choose>">${cmt.mem_nickname}</span>
      <c:if test="${cmt.cmt_device == 2}"><i class="fas fa-mobile-alt"></i> </c:if>
        ${cmt.cmt_content} <c:if test="${cmt.mem_id == sessionScope.member.mem_id}">
      <i class="icon fa-trash-alt solid" style="margin-left: 10px; cursor: pointer;" onclick="commentDelete('${cmt.cmt_id}');"></i></c:if> <span id="cmt_date${cmt.cmt_id}" class="text-mute" style="float: right"></span></li>
    <script>document.getElementById('cmt_date${cmt.cmt_id}').innerHTML = timeForToday('${cmt.cmt_datetime}');</script>

  </c:forEach>
</ul>
<div id="paging" class="align-center">
  <ul class="pagination">
    <c:choose>
      <c:when test="${pageBean.startPage > pageBean.pageBlock}">
        <li><a href="javascript:void(0)" class="button" onclick="commentPaging('<c:url value="/comment/${postBean.post_id}?page=${pageBean.startPage - pageBean.pageBlock}&brd_key=${brd_key}"/>')"><spring:message code="button.prev"/></a></li>
      </c:when>
      <c:otherwise><li><span class="button disabled"><spring:message code="button.prev"/></span></li></c:otherwise>
    </c:choose>

    <c:forEach var="count" begin="${pageBean.startPage}" end="${pageBean.endPage}" step="1">
    <li><a href="javascript:void(0)" class="page <c:if test="${pageBean.currentPage == count}">active</c:if>" onclick="commentPaging('<c:url value="/comment/${postBean.post_id}?page=${count}&brd_key=${brd_key}"/>')">${count}</a></li>
    </c:forEach>

    <c:choose>
      <c:when test="${pageBean.endPage < pageBean.pageCount}">
        <li><a href="javascript:void(0)" class="button" onclick="commentPaging('<c:url value="/comment/${postBean.post_id}?page=${pageBean.startPage + pageBean.pageBlock}&brd_key=${brd_key}"/>')"><spring:message code="button.next"/></a></li>
      </c:when>
      <c:otherwise><li><span class="button disabled"><spring:message code="button.next"/></span></li></c:otherwise>
    </c:choose>
  </ul>
</div>
<div id="comment_write_box">

<form action="<c:url value="/comment/write"/>" method="post">
  <div class="row gtr-uniform">
    <div class="col-12">
      <textarea class="ta-noresize" name="cmt_content" required></textarea>
    </div>
    <div class="col-12">
      <input type="hidden" name="post_id" value="${postBean.post_id}">
      <input type="hidden" id="cmt_device" name="cmt_device">
      <input type="hidden" name="brd_key" value="${brd_key}">
      <button class="primary" <c:if test="${sessionScope.member == null}">title="<spring:message code="msg.login"/>" disabled</c:if>><spring:message code="button.submitWrite"/></button>
    </div>
  </div>
</form>

</div>
<script>
  $(document).ready(function(){
    $('.comment-list').click(function () {
      <c:choose>
        <c:when test="${sessionScope.member != null}">
          let $box = $('#comment_write_box').html();
          let id = $(this).attr('id').replaceAll('comment_', '');
          $('.reply_write_box').remove();
          $(this).after('<li id="reply_box_"'+id+' class="reply_write_box">'+$box+'</li>');
          $('.reply_write_box form').append('<input type="hidden" name="cmt_num" value="'+id+'">');
        </c:when>
        <c:otherwise>
          let login = confirm('<spring:message code="msg.login"/>');
          if (login) {
            location.href = '<c:url value="/login"/>';
          }
        </c:otherwise>
      </c:choose>


    })
  })
  let filter = "win16|win32|win64|mac|macintel";
  if ( navigator.platform ) {
    if ( filter.indexOf( navigator.platform.toLowerCase() ) < 0 ) {
      //mobile alert('mobile 접속');
      document.getElementById('cmt_device').value = 2;
    } else {
      //pc alert('pc 접속');
      document.getElementById('cmt_device').value = 1;
    }
  }

  function commentDelete(cmt_id) {
    let doDelete = confirm('<spring:message code="msg.doDelete"/>');
    if(doDelete) {
      location.href = '<c:url value="/comment/delete"/>?brd_key=${brd_key}&cmt_id='+cmt_id;
    }
  }

</script>


