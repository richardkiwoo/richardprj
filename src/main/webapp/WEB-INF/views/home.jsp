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
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
    
	
	<style type="text/css">
	.jumbotron {
		background-image: url('/resources/images/jumbotronBackground.jpg');
		background-size: cover;
		text-shadow: black 0.2em 0.2em 0.2em;
		color: white;
	}
	</style>
	<script type="text/javascript">
	function logout(){
		location.href="/logout.do";
	}
	
	// file upload 를 위한 메소드
//     $(document).ready(function () {
//         if(${not empty result}){
//             alert('${result} 파일 저장 성공');
//         } else {
//             alert('파일 저장 실패');
//         }
//     });
	
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

	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
					<span class="sr-only"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">코딩 부스트</a>
			</div>
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">소개<span class="sr-only"></span></a></li>
					<li><a href="#">강사진</a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
						aria-haspopup="true" aria-expanded="false">강의<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="#">C언어</a></li>
							<li><a href="#">Java</a></li>
							<li><a href="#">안드로이드</a></li>
						</ul>
					</li>
				</ul>
				<form action="" class="navbar-form navbar-left">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="내용을 입력하세요">
					</div>
					<button type="submit" class="btn btn-default">검색</button>
				</form>
				<ul class="nav navbar-nav navbar-right">
					<c:if test="${not empty loginInfo}">
					<li>
						${loginInfo.mbrName} 님 로그인 중입니다~~
					</li>
					<li>
					<input type="button" id="btnLogout" onclick="logout();" value="Logout" />
					</li>
					</c:if>
					<c:if test="${empty loginInfo}">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
						aria-haspopup="true" aria-expanded="false">접속하기<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/loginPage.do">로그인</a></li>
							<li><a href="/registerform.do">회원가입</a></li>
						</ul>
					</li>
					</c:if>
				</ul>
			</div>
		</div>
	</nav>
	<div class="container">
		<div class="jumbotron">
			<h1 class="text-center">Richard를 소개합니다.</h1>
			<p class="text-center">코딩부스터는 IT교육 사이트입니다. 다양한 환경...</p>
			<p class="text-center"><a class="btn btn-primary btn-lg" href="#">강의 들으러 가기</a></p>
		</div>
	</div>
<!-- 	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="/resources/js/bootstrap.js"></script>
 -->	
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

<%-- 	<table>
		<thead>
			<tr>
				<th>아이디</th>
				<th>비밀번호</th>
				<th>이름</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${memberList}" var="member">
				<tr>
					<td>${member.id}</td>
					<td>${member.pw}</td>
					<td>${member.name}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
 --%>

</body>
</html>
