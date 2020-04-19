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
	<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script> -->
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    
    <!-- include summernote css/js -->
    <script type="text/javascript" src="resources/js/summernote/summernote-lite.js"></script>
    <script type="text/javascript" src="resources/js/summernote/lang/summernote-ko-KR.js"></script>
	<link href="resources/css/summernote/summernote-lite.css" rel="stylesheet">
	
		
	<style type="text/css">
	
	</style>
	<script type="text/javascript">
	
	$(document).ready(function() {
	     
	});
	
	function modifyPost(){
		$("#frm").attr("action", "/postWrite.do");
		$("#frm").submit();
	}
	
	function goList(){
		$("#frm").attr("action", "/postList.do");
		$("#frm").submit();
	}
	
	function deletePost(){
		$("#frm").attr("action", "/postDelete.do");
		$("#frm").submit();
	}
	</script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>

<form method="post" id="frm" name="frm" action="">
	<input type="hidden" name="boardId" value="${post.boardId }">
	<input type="hidden" name="postNo" value="${post.postNo }">
	<input type="hidden" name="page" value="${pagination.page}">
	<div class="container">
		<div class="form-group">
		  <h5 class="blog-post-title">${board.boardName}</h5>
		</div>
		<div class="form-group">
		  <h3 class="blog-post-title">${post.postTitle }</h3>
		</div>
		<div class="form-group">
		  <p>${post.modDate} &nbsp; &nbsp; 조회 : ${post.readCnt} , 추천 : ${post.recommendCnt}</p>
		</div>
		<div class="form-group">${post.contents}</div>
	  	<div class="form-group">
			<ul class="list-group list-group-flush">
			<c:forEach var="af" items="${post.attachFile}">
	  			<li class="list-group-item">${af.fileSeq} : <a href="${af.savedFileName }">${af.fileName}</a></li>
	  		</c:forEach>
	  		</ul>
	  	</div>
	  	<div class="container">
		  	<c:if test="${loginInfo.mbrid == post.writer}">
		  		<button type="button" class="btn btn-primary" onClick="modifyPost();">수정</button>
		  		<button type="button" class="btn btn-primary" onClick="deletePost();">삭제</button>
		  	</c:if>
		  		<button type="button" class="btn btn-primary" onClick="goList();">목록</button>
	  	</div>
  	</div>

</form>

<%@ include file="/WEB-INF/views/footer.jsp" %>

</body>
</html>
