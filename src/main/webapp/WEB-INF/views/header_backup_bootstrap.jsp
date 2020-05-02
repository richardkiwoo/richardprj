<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
				<a class="navbar-brand" href="/">RICHARD</a>
			</div>
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">소개<span class="sr-only"></span></a></li>
					<li><a href="#">강사진</a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
						aria-haspopup="true" aria-expanded="false">게시판<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/postList.do?boardId=1">1번게시판</a></li>
							<li><a href="/postList.do?boardId=2">2번게시판</a></li>
							<li><a href="/postList.do?boardId=3">3번게시판</a></li>
						</ul>
					</li>
				</ul>
				<form action="" name="topfrm" class="navbar-form navbar-left">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="내용을 입력하세요">
					</div>
					<button type="submit" class="btn btn-default">검색</button>
				</form>
				
				<c:if test="${not empty loginInfo}">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
						aria-haspopup="true" aria-expanded="false">${loginInfo.mbrName}님<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/logout.do">로그인아웃</a></li>
							<li><a href="#">회원정보변경</a></li>
							<li><a href="#">Password변경</a></li>
						</ul>
					</li>
					
				</ul>
				</c:if>
				<c:if test="${empty loginInfo}">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
						aria-haspopup="true" aria-expanded="false">접속하기<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/loginPage.do">로그인</a></li>
							<li><a href="/registerform.do">회원가입</a></li>
						</ul>
					</li>
				</ul>				
				</c:if>
				
			</div>
		</div>
	</nav>
	 <!-- <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script> -->
	<!--<script src="/resources/js/bootstrap.js"></script> -->
	