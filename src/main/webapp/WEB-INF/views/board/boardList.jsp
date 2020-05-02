<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Home</title>
	<link rel="stylesheet" href="resources/css/bootstrap.css">
	<link rel="stylesheet" href="resources/css/style.css">
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <!--<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script> -->
    
	
	<style type="text/css">
	
	</style>
	<script type="text/javascript">
	//이전 버튼 이벤트

	function fn_prev(page, range, rangeSize) {

			var page = ((range - 2) * rangeSize) + 1;
			var range = range - 1;
			var url = "${pageContext.request.contextPath}/postList.do";
			url = url + "?page=" + page;
			url = url + "&range=" + range;
			url = url + "&boardId=${board.boardId}";
			location.href = url;

		}

	  //페이지 번호 클릭

		function fn_pagination(page, range, rangeSize, searchType, keyword) {

			var url = "${pageContext.request.contextPath}/postList.do";
			url = url + "?page=" + page;
			url = url + "&range=" + range;
			url = url + "&boardId=${board.boardId}";
			location.href = url;	

		}

		//다음 버튼 이벤트

		function fn_next(page, range, rangeSize) {

			var page = parseInt((range * rangeSize)) + 1;

			var range = parseInt(range) + 1;
			var url = "${pageContext.request.contextPath}/postList.do";
			url = url + "?page=" + page;
			url = url + "&range=" + range;
			url = url + "&boardId=${board.boardId}";
			location.href = url;
		}
		
		
		function goView(postno){
			$("#frm").attr("action", "/article.do");
			$("#frm").method = "post";
			$("input[type=hidden][name=postNo]").val(postno);
			$("#frm").submit();
		}
		function goWrite(){
// 			$("#frm").attr("action", "/postWrite.do");
// 			$("#frm").method = "post";
// 			$("#frm").submit();
			
			location.href="/postWrite.do?boardId=${board.boardId}";
		}

	</script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>


<div class="container">
  <h2>[${board.boardName }]</h2>
  <p>${board.boardType }</p>
  <form name="frm" id="frm" method="post" action="">
  	<input type="hidden" name="boardId" value="${board.boardId }">
  	<input type="hidden" name="postNo" value="-1">
  	<input type="hidden" name="page" value="${pagination.page }">
  </form>             
  <table class="table">
    <thead>
      <tr>
        <th></th>
        <th>제목</th>
        <th>글쓴이</th>
        <th>작성일</th>
        <th>추천</th>
        <th>조회</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach var="postVO" items="${postList}" >
      <tr>
        <td><c:out value="${postVO.postNo}" /></td>
        <td><a href="#" onclick="javascript:goView(${postVO.postNo})"><c:out value="${postVO.postTitle}" /></a>
        &nbsp;<span class="badge badge-secondary">${postVO.replyCnt}</span></td>
        <td><c:out value="${postVO.writer}" /></td>
        <td><c:out value="${postVO.modDate}" /></td>
        <td><c:out value="${postVO.recommendCnt}" /></td>
        <td><c:out value="${postVO.readCnt}" /></td>
      </tr>
      </c:forEach>
    </tbody>
  </table>
</div>

	<!-- pagination{s} -->
<div class="row">
	<div class="container col-sm-2">
		<ul  class="pagination navbar-right">
		<li>
		<a href="#" onClick="javascript:goWrite();" class="btn btn-info" role="button">글쓰기</a>
		</li>
		</ul>
	</div>
	<div  class="container col-sm-4">

		<ul class="pagination">
			<c:if test="${pagination.prev}">
				<li class="page-item"><a class="page-link" href="#" onClick="fn_prev('${pagination.page}', '${pagination.range}', '${pagination.rangeSize}')">Previous</a></li>
			</c:if>
			<c:forEach begin="${pagination.startPage}" end="${pagination.endPage}" var="idx">
				<li class="page-item <c:out value="${pagination.page == idx ? 'active' : ''}"/> "><a class="page-link" href="#" onClick="fn_pagination('${idx}', '${pagination.range}', '${pagination.rangeSize}')"> ${idx} </a></li>
			</c:forEach>
			<c:if test="${pagination.next}">
				<li class="page-item"><a class="page-link" href="#" onClick="fn_next('${pagination.range}', '${pagination.range}', '${pagination.rangeSize}')" >Next</a></li>
			</c:if>
		</ul>

	</div>
</div>	
		<!-- pagination{e} -->
		

<%@ include file="/WEB-INF/views/footer.jsp" %>

</body>
</html>
