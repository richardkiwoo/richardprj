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
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
    
	
	<style type="text/css">
	
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

<%@ include file="/WEB-INF/views/footer.jsp" %>

</body>
</html>
