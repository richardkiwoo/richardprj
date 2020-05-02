<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Home</title>
	<!-- <link rel="stylesheet" href="resources/css/bootstrap.css"> -->
	<link rel="stylesheet" href="resources/css/reset.css">
	<link rel="stylesheet" href="resources/css/default.css">
	<link rel="stylesheet" href="resources/css/style.css">
	
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    
	
	<style type="text/css">
	.jumbotron { background-image: url('/resources/images/jumbotronBackground.jpg'); background-size: cover; text-shadow: black 0.2em 0.2em 0.2em; color: white; }
	.dropdown:hover .dropdown-menu {display: block;}
	</style>

	<script type="text/javascript">
	function logout(){
		location.href="/logout.do";
	}
	
    $(function(){
        var responseMessage = "${result}";
        if(responseMessage != ""){
            alert(responseMessage);
        }
    }) ;
    
    function checkFile() {
    	
    	if ($('#uploadfile').val()=='') { 
    		alert("file을 선택하세요!");
    		return false;
    	}else { 
    		return true;
    	}
    	
    }
	</script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>

<div class="container">
	<div class="jumbotron">
		<h1>Richard를 소개합니다.</h1>
		<p>Richard IT교육 사이트입니다. 다양한 환경...</p>
		<p><a href="#">강의 들으러 가기</a></p>
	</div>
</div>


<!-- 
	
	<h1>파일 업로드</h1>
<form action="/fileupload.do"  method="post" enctype="multipart/form-data" onSubmit="return checkFile();">
    <input type="file"  id="uploadfile" name="uploadfile" placeholder="파일 선택" /><br/>
    <input type="submit" value="업로드">
</form>
<br>
<h2>다중 파일 업로드</h2>
<form action="/multfileupload.do"  method="post" enctype="multipart/form-data">
    <input type="file"  name="uploadfiles" placeholder="파일 선택" multiple/><br/>
    <input type="submit" value="업로드">
</form>

	<h2><a href="/downloadFileList.do">다운로드 파일목록</a></h2>
	
	<h2><a href="/game.do">게임 결과 입력</a></h2>
	
	<h2><a href="/excelDownloadUpload.do">게임결과 엑셀 다운로드</a></h2>

 -->
<%@ include file="/WEB-INF/views/footer.jsp" %>

</body>
</html>
