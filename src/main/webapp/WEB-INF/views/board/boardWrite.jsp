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
	<!-- <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script> -->
	<script src="https://code.jquery.com/jquery-3.1.1.js"></script>
    
    <!-- include summernote css/js -->
    <script type="text/javascript" src="resources/js/summernote/summernote-lite.js"></script>
    <script type="text/javascript" src="resources/js/summernote/lang/summernote-ko-KR.js"></script>
	<link href="resources/css/summernote/summernote-lite.css" rel="stylesheet">
	
		
	<style type="text/css">
	
	</style>
	
	<script type="text/javascript">
	
	$(document).ready(function() {
	      $('#summernote').summernote({
	        height: 300,
	        minHeight: null,
	        maxHeight: null,
	        focus: true,
	        lang: "ko-KR",					// 한글 설정
			placeholder: '최대 2048자까지 쓸 수 있습니다',	//placeholder 설정
	        callbacks: {
	          onImageUpload: function(files, editor, welEditable) {
	            for (var i = files.length - 1; i >= 0; i--) {
	              sendFile(files[i], this);
	            }
	          }
	        }
	      });
	      
	      
	     /*  $("input[type=file]").change(function () {
	            
	            var fileInput = document.getElementById("post_files");
	            //var fileInput = $("#post_files");
	            
	            var files = fileInput.files;
	            var file;
	            
	            for (var i = 0; i < files.length; i++) {
	                file = files[i];
	            }
	        });
	       */
	      
	      
	    });
	    
	    function sendFile(file, el) {
	      var form_data = new FormData();
	      form_data.append('file', file);
	      $.ajax({
	        data: form_data,
	        type: "POST",
	        url: '/boardImage.do',
	        cache: false,
	        contentType: false,
	        enctype: 'multipart/form-data',
	        processData: false,
	        success: function(url) {
	        	alert(url);
	          $(el).summernote('editor.insertImage', url);
	          //$('#imageBoard > ul').append('<li><img src="'+url+'" width="480" height="auto"/></li>');
	        }
	      });
	    }
	    
	    function savePost(){
	    	if($("#postTitle").val() == ''){
	    		alert('제목을 입력하세요.');
	    		$("#postTitle").focus();
	    		return;
	    	}
	    	if($("#summernote").val() == ''){
	    		alert('내용을 입력하세요.');
	    		return;
	    	}
	    	$("#frm").attr("action", "/savePost2.do");
	    	//alert($("input[name=dispYn]").val() + " \n" +$("input[name=replyYn]").val() );
	    	$("#frm").submit();
	    }
	    function goList(){
	    	$("#frm").attr("action", "/postList.do");
	    	$("#frm").submit();
	    }
	    
/*
  //drag and drop 안될 경우 아래 소스를 추가한다.
	  $("div.note-editable").on('drop',function(e){
	         for(i=0; i< e.originalEvent.dataTransfer.files.length; i++){
	         	uploadSummernoteImageFile(e.originalEvent.dataTransfer.files[i],$("#summernote")[0]);
	         }
	        e.preventDefault();
	   }) 
*/

	</script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>

<form id="frm" name="frm" action="/savePost.do" method="post" enctype="multipart/form-data">
	<input type="hidden" name="boardId" value="${article.boardId }">
	<input type="hidden" name="postNo" value="${article.postNo }">
	<div class="container">
		<div class="form-group">
		  <label for="usr">제목:</label>
		  <input type="text" class="form-control" id="postTitle" name="postTitle" value="${article.postTitle}" placeholder="제목을 입력하세요!">
		</div>
		<div>
	  		<textarea id="summernote" name="contents">${article.contents}</textarea>
	  	</div>
	  	<div class="input-group mb-3">
		    <div class="input-group-prepend">
		      <span class="input-group-text">첨부파일</span>
		    </div>
		    <input class="form-control" type="file" id="post_files" name="post_files" placeholder="파일 선택" multiple/><br/>
		</div>
		<div>
		<p class="desc">게시글 파일 첨부는 최대 3개까지 저장할 수 있습니다.<br/>용량은 50kbyte이하로 업로드 해주십시오.</p></div>
		<div>
			<div class="custom-control custom-checkbox">
			    <input type="checkbox" class="custom-control-input" id="dispYn" name="dispYn" 
			    <c:if test="${article.dispYn eq 'Y'}"> checked </c:if> value="Y">
				<label class="custom-control-label" for="dispYn">비공개</label>
				<input type="checkbox" class="custom-control-input" id="replyYn" name="replyYn" 
				<c:if test="${article.replyYn eq 'Y'}"> checked </c:if> value="Y">
				<label class="custom-control-label" for="replyYn">비댓글</label>
			</div>
		</div>
		<!-- The global progress bar -->
		<!-- <div id="progress" class="progress">
		<div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: 75%"></div> -->
		<hr>

</div>
  	</div>
  	<div class="container">
		<button type="button" class="btn btn-primary" onClick="savePost();">저장</button>
		<button type="button" class="btn btn-primary" onClick="goList();">목록</button>
	</div>

</form>

<%@ include file="/WEB-INF/views/footer.jsp" %>

</body>
</html>
