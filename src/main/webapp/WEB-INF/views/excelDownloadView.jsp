<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
    <title>Excel Download View</title>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    
    <style>
    
    .container{
    	
    }
    
    </style>
</head>

<body>
<script type="text/javascript">
    function doExcelUploadProcess(){
        var f = new FormData(document.getElementById('form1'));
        $.ajax({
            url: "/uploadExcelFile.do",
            data: f,
            processData: false,
            contentType: false,
            type: "POST",
            success: function(data){
                if(data !=null)
                	alert("등록완료!");
            }
        })
    }
    
    function doExcelDownloadProcess(){
        var f = document.form1;
        f.action = "/downloadExcelFile.do";
        f.submit();
    }
</script>
<form id="form1" name="form1" method="post" enctype="multipart/form-data">
	<div class="container">
	    <input type="file" id="fileInput" name="fileInput">
	    <button type="button" onclick="doExcelUploadProcess()">엑셀업로드 작업</button>
	    <button type="button" onclick="doExcelDownloadProcess()">엑셀다운로드 작업</button>
    </div>
</form>
<div id="result">
</div>
</body>
</html>