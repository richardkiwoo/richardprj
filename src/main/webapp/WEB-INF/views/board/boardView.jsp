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
	.list-group-item > ul > li {position:relative;}
	.list-group-item > ul > li > button {margin:0 3px 0 3px; position:absolute; right:45px; top:5px;}
	.list-group-item > ul > li > button:last-child{right:5px;}
	
	
	/* The Modal (background) */
	.modal { display: none; /* Hidden by default */ position: fixed; /* Stay in place */ z-index: 1; /* Sit on top */ left: 0; top: 0; width: 100%; height: 100%;
			overflow: auto; background-color: rgb(0,0,0); background-color: rgba(0,0,0,0.4); }
	
	/* Modal Content/Box */
	.modal-content { background-color: #fefefe; margin: 15% auto; /* 15% from the top and centered */ padding: 20px; border: 1px solid #888; width: 80%; /* Could be more or less, depending on screen size */}
	
	/* The Close Button */
	.close { color: #aaa; float: right; font-size: 28px; font-weight: bold; margin:5px 2px;}
	
	.close:hover,
	.close:focus { color: black;text-decoration: none;cursor: pointer;}
	.modal-content button {margin : 5px 3px;}
	.modal-content .btn-default {float:right}
	
	.list-group-item > span {font-size:10px; font-style:italic}
	/* like */
	.like {float:right;margin:15px 20px 5px 0;}
	.like a{disply:block; margin:5px 5px;}
	.like:before {content:""; display: block; background : url(resources/images/icon/thumbup.png) no-repeat 0 0 / 15px auto;}
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
		if(confirm("게시글을 삭제하시겠습니까?") != true){return;}
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
	function modifyReply(repNo){
		
		modal.style.display = "block";
		
		$("#frm input[name=replyNo]").val(repNo);
		
		$.ajax({
			type: "POST",
			url: "/board/getReply.do",
	        dataType : "json",//서버에서 보내는 데이터타입(브라우저에서 받는)
		    data: $('#frm').serialize(),
		    success: function(result) {
		    	var cont = result.reply.repCont;
		    	$("#repCont_mod").val(cont);
		    	//$("#myModal").show();
		    },
		    error:function(request,status,error){
                alert("code:  "+request.status+"\n"+"message:  "+request.responseText+"\n"+"error:  "+error);
            }

		});
		
	}
	
	function saveReply(){
		//alert($("#repCont_mod").val());
		$("#repCont").val($("#repCont_mod").val());
		var fv = $('#frm').serialize();
		
		$("#repCont").val("");
		var replyNo = $("#frm input[name=replyNo]").val();
		//$("#myModal").css("display","none");
		
		 $.ajax({
			type: "POST",
			url: "/board/saveReply.do",
	        dataType : "json",//서버에서 보내는 데이터타입(브라우저에서 받는)
		    data: fv,
		    success: function(result) {
		    	$("#repCont_li_"+replyNo).text($("#repCont_mod").val().replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;"));
		    	$("#rep_date_"+replyNo).text("방금");
		    	modal.style.display = "none";
		    },
		    error:function(request,status,error){
                alert("code:  "+request.status+"\n"+"message:  "+request.responseText+"\n"+"error:  "+error);
            }

		});
		
	}
	
	function reply(){
		var rep = $("#repCont").val();
		<c:if test="${loginInfo == null}">
		alert("로그인 후 작성해 주세요!"); return;
		</c:if>
		if (rep == ''){alert("댓글을 작성해 주세요!"); return;}
		
		$('#frm input[name=replyNo]').val(0);
		var form_data = $("#frm").serialize();
		
		$.ajax({
			type: "POST",
			url: "/board/addReply.do",
	        dataType : "json", //서버에서 보내는 데이터타입(브라우저에서 받는)
		    data: $('#frm').serialize(),
		    success: function(msg) {
		    	
		    	var no = msg.new_replyNo;
		       	var html = "";
		       	html += "<li class=\"list-group-item\" id=\"replyNo_"+ no +"\">";
		       	html += "<ul class=\"list-group list-group-flush\">";
		       	html += "	<li class=\"list-group-item\" style=\"border-bottom:none;\">${loginInfo.mbrid}<br><span id=\"rep_date_"+no+"\">방금</span>";
		       	html += "	<button type=\"button\" class=\"btn btn-primary btn-xs\" id=\"btn_rep_mod\"  onClick=\"modifyReply("+no+");\">수정</button>";
		       	html += "	<button type=\"button\" class=\"btn btn-primary btn-xs btn-danger\" onClick=\"deleteReply("+no+");\">삭제</button>";
		       	html += "	</li>";
		       	html += "	<li class=\"list-group-item\" style=\"border-top:none;\">"+$("#repCont").val().replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;")+"</li>";
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
		  		<div class="like"><a href="#">좋아요!</a><span>${post.recommendCnt }</span></div>
	  	</div><br>
	  	<!-- <div class="form-group">
		  <label for="comment">Comment:</label>
		  <textarea class="form-control" rows="3" id="comment" name="repCont"></textarea>
		</div> -->
		<div class="form-group wrap19">
	    	<label for="repCont">댓글:</label>
	    	<textarea id="repCont" name="repCont" class="form-control" maxlength="300" placeholder="댓글을 남겨보세요~"></textarea>
	    	<button type="button" class="btn btn-info" onClick="javascript:reply();">댓글달기</button>
	    	<span id="counter">###</span>
	  	</div>
	  	<div class="form-group" style="margin:10px 0 10px 0;">
	  		<ul class="list-group list-group-flush" id="replyList">
		  	<c:forEach var="rep" items="${post.replies}">
	  			<li class="list-group-item" id="replyNo_${rep.replyNo}">
	  				<ul class="list-group list-group-flush">
	  					<li class="list-group-item" style="border-bottom:none;">${rep.repWriter}<br><span id="rep_date_${rep.replyNo}">${rep.modDate}</span>
	  					<c:if test="${loginInfo.mbrid eq rep.repWriter}">
	  					<button type="button" class="btn btn-primary btn-xs" id="btn_rep_mod" onClick="modifyReply(${rep.replyNo});">수정</button>
		  				<button type="button" class="btn btn-primary btn-xs btn-danger" onClick="deleteReply(${rep.replyNo});">삭제</button>
		  				</c:if>
	  					</li>
	  					<li class="list-group-item" id="repCont_li_${rep.replyNo}" style="border-top:none;">${rep.repCont}</li>
	  				</ul>
	  			</li>
	  		</c:forEach>
			</ul>
		</div>
		
		
		<!-- The Modal -->
<div id="myModal" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <span class="close">&times;</span>
    <p>댓글 수정..</p>
    <textarea id="repCont_mod" name="repCont_mod" class="form-control" maxlength="300"></textarea>
	<button type="button" class="btn btn-info" onClick="javascript:saveReply();">댓글달기</button>
	<button type="button" class="btn btn-default btn-xs" onClick="modal.style.display = 'none';">Close</button>
  </div>

</div>

<script>
// Get the modal
var modal = document.getElementById("myModal");
// Get the button that opens the modal
//var btn = document.getElementById("myBtn");
// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];
// When the user clicks on the button, open the modal
//	btn.onclick = function() {
//	  modal.style.display = "block";
//	}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
  modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}

</script>		

		
  	</div>
	<!-- <input type="text" style="width:0; top:-1000%"> -->
</form>

<%@ include file="/WEB-INF/views/footer.jsp" %>

</body>
</html>
