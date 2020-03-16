<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<c:forEach items="${files}" var="file">
	<a href="/fileDownload.do?idx=${file.idx }" >${file.originalFileName}</a><br>
	
</c:forEach>

<p><a href="/" >홈으로...</a></p>

</body>
</html>