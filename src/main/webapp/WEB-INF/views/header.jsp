<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
	<nav>
		<div class="navbar-header">
			<a href="/">백양배드민턴클럽</a>
		</div>
		<div class="top-menu">
			<ul class="menu">
				<li><a href="#">동호회소개<span></span></a></li>
				<li><a href="#">경기결과</a></li>
				<li class="dropdown">
					<a href="#" >게시판<span></span></a>
					<ul class="drop-menu">
						<li><a href="/postList.do?boardId=1">1번게시판</a></li>
						<li><a href="/postList.do?boardId=2">2번게시판</a></li>
						<li><a href="/postList.do?boardId=3">3번게시판</a></li>
					</ul>
				</li>
			</ul>
			<form action="" name="topfrm" class="topfrm">
				<div class="form-group">
					<input type="text" placeholder="내용을 입력하세요">
				</div>
				<button type="submit" class="btn-submit">검색</button>
			</form>
		</div>
		<div class="login-info">
		<c:if test="${not empty loginInfo}">
			<ul class="">
				<li class="dropdown">
					<a href="#" role="button" aria-haspopup="true" aria-expanded="false">${loginInfo.mbrName}님<span></span></a>
					<ul class="drop-menu">
						<li><a href="/logout.do">로그인아웃</a></li>
						<li><a href="#">회원정보변경</a></li>
						<li><a href="#">Password변경</a></li>
					</ul>
				</li>
			</ul>
			</c:if>
			<c:if test="${empty loginInfo}">
			<ul class="login-info">
				<li class="dropdown">
					<a href="#">접속하기<span></span></a>
					<ul class="drop-menu">
						<li><a href="/loginPage.do">로그인</a></li>
						<li><a href="/registerform.do">회원가입</a></li>
					</ul>
				</li>
			</ul>				
		</c:if>
		</div>
	</nav>
