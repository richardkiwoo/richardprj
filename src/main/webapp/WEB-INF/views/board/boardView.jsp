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
	.wrap19 {position: relative;}
	.wrap span:last-child { position: absolute; bottom: 5px; right: 5px; }
	#counter { background:rgba(255,0,0,0.5); border-radius: 0.5em; padding: 0 .5em 0 .5em; font-size: 0.75em;}
	.form-group button {margin:10px 0 10px;}
	/* 댓글 버튼*/
	
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
	function attachfile_download(fileseq){
		$("#frm input[name=fileSeq]").val(fileseq);
		$("#frm").attr("action", "/board/attachFileDownload.do");
		$("#frm").submit();
	}
	
	function deleteReply(repNo){
		if (confirm("삭제하겠습니까?") == false) return;
		
		$("#frm input[name=replyNo]").val(repNo);
		
		$.ajax({
			type: "POST",
			url: "/board/removeReply.do",
	        dataType : "json",//서버에서 보내는 데이터타입(브라우저에서 받는)
		    data: $('#frm').serialize(),
		    success: function(msg) {
		       	//alert("삭제되었습니다");
		       	$("#replyNo_"+repNo).remove();
		    },
		    error:function(request,status,error){
                alert("code:  "+request.status+"\n"+"message:  "+request.responseText+"\n"+"error:  "+error);
            }

		});
		
	}
	function updateReply(repNo){
		
		
// 		$("#frm input[name=fileSeq]").val(fileseq);
// 		$("#frm").attr("action", "/board/attachFileDownload.do");
// 		$("#frm").submit();
	}
	function reply(){
		var rep = $("#repCont").val();
		<c:if test="${loginInfo == null}">
		alert("로그인 후 작성해 주세요!"); return;
		</c:if>
		if (rep == ''){alert("댓글을 작성해 주세요!"); return;}
		
		//$('#replyList').prepend('<li class="list-group-item">'+rep+'</li>');
		
		$('#frm input[name=replyNo]').val(0);
		var form_data = $("#frm").serialize();
		
		$.ajax({
			type: "POST",
			url: "/board/addReply.do",
	        dataType : "json", //서버에서 보내는 데이터타입(브라우저에서 받는)
		    data: $('#frm').serialize(),
		    success: function(msg) {
		    	alert(msg.msg);
		    	var no = msg.new_replyNo;
		       	var html = "";
		       	html += "<li class=\"list-group-item\" id=\"replyNo_"+ no +"\">";
		       	html += "<ul class=\"list-group list-group-flush\">";
		       	html += "	<li class=\"list-group-item\" style=\"border-bottom:none;\">${loginInfo.mbrid}<br>방금";
		       	html += "	<button type=\"button\" class=\"btn btn-primary btn-xs\" onClick=\"modifyReply("+no+");\">수정</button>";
		       	html += "	<button type=\"button\" class=\"btn btn-primary btn-xs btn-danger\" onClick=\"deleteReply("+no+");\">삭제</button>";
		       	html += "	</li>";
		       	html += "	<li class=\"list-group-item\" style=\"border-top:none;\">"+$("#repCont").val()+"</li>";
		       	html += "</ul>";
		       	html += "</li>";
		       	
		       	$('#replyList').prepend(html);
		       	
		       	$("#repCont").val("");
		       	
		       	
		    },
		    error:function(request,status,error){
                alert("code:  "+request.status+"\n"+"message:  "+request.responseText+"\n"+"error:  "+error);
            }

		});
		
	}
	
	$(function() {
	      $('#repCont').keyup(function (e){
	          var content = $(this).val();
	          $(this).height(((content.split('\n').length + 1) * 1.5) + 'em');
	          $('#counter').html(content.length + '/300');
	      });
	      $('#repCont').keyup();
	});
	</script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>

<form method="post" id="frm" name="frm" action="">
	<input type="hidden" name="boardId" value="${post.boardId }">
	<input type="hidden" name="postNo" value="${post.postNo }">
	<input type="hidden" name="fileSeq" value="">
	<input type="hidden" name="replyNo" value="">
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
		<div class="well well-lg">${post.contents}</div>
	  	<div class="form-group">
			<ul class="list-group list-group-flush">
			<c:forEach var="af" items="${post.attachFile}">
	  			<li class="list-group-item">첨부${af.fileSeq} : <a href="javascript:attachfile_download(${af.fileSeq });">${af.fileName}</a></li>
	  		</c:forEach>
	  		</ul>
	  	</div>
	  	<hr>
	  	<div class="container">
		  	<c:if test="${loginInfo.mbrid == post.writer}">
		  		<button type="button" class="btn btn-primary" onClick="modifyPost();">수정</button>
		  		<button type="button" class="btn btn-primary" onClick="deletePost();">삭제</button>
		  	</c:if>
		  		<button type="button" class="btn btn-primary" onClick="goList();">목록</button>
	  	</div><br>
	  	<!-- <div class="form-group">
		  <label for="comment">Comment:</label>
		  <textarea class="form-control" rows="3" id="comment" name="repCont"></textarea>
		</div> -->
		<div class="form-group wrap19">
	    	<label for="repCont">댓글:</label>
	    	<textarea id="repCont" name="repCont" class="form-control" placeholder="댓글을 남겨보세요~"></textarea>
	    	<button type="button" class="btn btn-info" onClick="javascript:reply();">댓글달기</button>
	    	<span id="counter">###</span>
	  	</div>
	  	<div class="form-group" style="margin:10px 0 10px 0;">
	  		<ul class="list-group list-group-flush" id="replyList">
		  	<c:forEach var="rep" items="${post.replies}">
	  			<li class="list-group-item" id="replyNo_${rep.replyNo}">
	  				<ul class="list-group list-group-flush">
	  					<li class="list-group-item" style="border-bottom:none;">${rep.repWriter}<br>${rep.modDate}
	  					<c:if test="${loginInfo.mbrid eq rep.repWriter}">
	  					<button type="button" class="btn btn-primary btn-xs" onClick="modifyReply(${rep.replyNo});">수정</button>
		  				<button type="button" class="btn btn-primary btn-xs btn-danger" onClick="deleteReply(${rep.replyNo});">삭제</button>
		  				</c:if>
	  					</li>
	  					<li class="list-group-item" style="border-top:none;">${rep.repCont}</li>
	  				</ul>
	  			</li>
	  		</c:forEach>
			</ul>
		</div>
		
		
		<!-- Modal -->
		  <div class="modal fade" id="myModal" role="dialog">
		    <div class="modal-dialog">
		    
		      <!-- Modal content-->
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal">&times;</button>
		          <h4 class="modal-title">Modal Header</h4>
		        </div>
		        <div class="modal-body">
		          <p>Some text in the modal.</p>
		        </div>
		        <div class="modal-footer">
		          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		        </div>
		      </div>
		      
		    </div>
		  </div>
		  
		</div>
		
	  	<%-- <div class="container">
		  	<c:if test="${loginInfo.mbrid == post.writer}">
		  		<button type="button" class="btn btn-primary" onClick="modifyPost();">수정</button>
		  		<button type="button" class="btn btn-primary" onClick="deletePost();">삭제</button>
		  	</c:if>
		  		<button type="button" class="btn btn-primary" onClick="goList();">목록</button>
	  	</div> --%>
  	</div>
	<input type="text" style="width:0; top:-1000%">
</form>

<%@ include file="/WEB-INF/views/footer.jsp" %>

</body>
</html>
