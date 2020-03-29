<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=devide-width, initial-scale=1.0">
<title>Game Result</title>

<!-- web font -->
<link rel="stylesheet" href="http://fonts.googleapis.com/earlyaccess/nanumgothic.css" type="text/css">
<link rel="stylesheet" href="resources/css/customized/reset.css">
<link rel="stylesheet" href="resources/css/customized/style.css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javascript">

	function saveGame() {
		$("#frm").submit();
	}

	$(document).ready(function() { 
		var selectTarget = $('.selectbox select'); 
		selectTarget.change(function(){ 
			var select_name = $(this).children('option:selected').text(); 
			$(this).siblings('label').text(select_name); 
		}); 
		
		//
		$(".dim-layer").css("display" ,"none");
		
		//회원찾기 팝업
		$('.member-btn').click(function(){
			
			
			//openMemberList($(this));
			
			//$("#modal").show();
			
			//var id, nm = "test";
			
			//$(this).prev().val(nm);
			//$(this).prev().prev().val(id);
	    });
		
// 		$('.member1').click(function(){
// 			alert($(this).children('h4'));
// 		});
	});
	
	function selectPlayerf(id, nm, obj){
		console.log(obj);
		
		
	}

	function closeModal() {
		$('.searchModal').hide();
	};

	function openMemberList(obj){
		$.ajax({
			url: "/memberList.do",
		    type: "POST",
		    cache: false,
		    dataType: "json",
		    data: $('#frm').serialize(),
			success : function(result) {
				var cnt = result.members.length;
				var mbrid, mbrName;
				var innerHtml = "";
				
				for(var i=0; i<cnt; i++){
					mbrid = result.members[i].mbrid;
					mbrName = result.members[i].mbrName;
					
					innerHtml += "<div class='member1'>";
					innerHtml += "<div id='mbrid' style='display:none'>"+mbrid + "</div>";
					innerHtml += "<h4>" + mbrName+"</h4>";
					innerHtml += "<div class='mbr-pic'>";
					innerHtml += "    <img src='/resources/images/person.gif' width='100px' height='100px'>";
					innerHtml += "</div>";
					innerHtml += "<input type='button' value='선택' onclick=\"selectPlayer('"+mbrid+"','" + mbrName + "',"+obj+");\">";
					innerHtml += "</div>";
                	
					$("#memberInfo").html(innerHtml);
				}
				
				
			}
		});
	}
   

</script>
<style type="text/css">
	 .main { width : 800px; height:600px; margin: 0 auto;
            background-color: #f7f7f7;
            border-radius: 3px;
            padding: 20px;
            counter-reset: items;
        }
        .item {
        	border-radius: 3px;
            background-color: rgb(255, 255, 255);
            border: 1px solid #4390E1;
            box-sizing: border-box;
            box-shadow: 0 2px 2px rgba(0,90,250,0.05), 0 4px 4px rgba(0,90,250,0.05), 0 8px 8px rgba(0,90,250,0.05), 0 16px 16px rgba(0,90,250,0.05);
            color: rgb(0, 0, 0);
            padding: 10px;
        }
        
         /* .item::before {
	            counter-increment: items;
	            content: counter(items);
	        } */
        
        ./* flex1 {display: flex; flex-direction: column; } */
        
        /* .sub-item{flex-direction: column;} */
        
        /* 세로 item flex */
        .col-flex {display: flex; flex-direction: column; }
        .flex-item {align-items: center; justify-content: center;}
        .flex-item-right {align-items: center; justify-content: flex-end;}
        
        /* 가로 item flex  */
        .row-flex {display: flex; flex-direction: row; }
        
        /* vs div를 넓게 한다. */
         /* .item:nth-child(3) .flex-item:nth-child(2) { with: 200px; } */
         .vs {width:200px; display: block; text-align: center;}
         
		input[type="text"] { 
		 width: 60px; padding: 5px 5px; margin: 5px 0; box-sizing: border-box; 
		 border: solid 1px #D2691E; border-radius: 8px; }
		 
		 .main h2{text-align: center; color: blue; background-color:rgb(134, 248, 239);}
		 .main button{padding:5px;}
		 
		 
		 input[type="button"] { 
		 	border-radius: 5px;
		 	border: 1px solid skyblue; background-color: rgba(0,0,0,0); 
		 	color: skyblue; padding: 5px;
		 } 
		 input[type="button"]:hover{ color:white; background-color: skyblue; }
		 
		 /* selectbox  꾸미기 */
		 .selectbox { 
		 	position: relative; width: 150px; /* 너비설정 */ 
		 	border: 1px solid #999; /* 테두리 설정 */ 
		 	z-index: 1; 
		 } 
		 .selectbox:before { /* 화살표 대체 */ 
		 	content: ""; 
		 	position: absolute; 
		 	top: 50%; 
		 	right: 15px; 
		 	width: 0; height: 0; 
		 	margin-top: -1px; 
		 	border-left: 5px solid transparent; 
		 	border-right: 5px solid transparent; 
		 	border-top: 5px solid #333; } 
		 .selectbox label { 
		 	position: absolute; 
		 	top: 1px; /* 위치정렬 */ 
		 	left: 5px; /* 위치정렬 */ 
		 	padding: .8em .5em; /* select의 여백 크기 만큼 */ 
		 	color: #999; 
		 	z-index: -1;  /* IE8에서 label이 위치한 곳이 클릭되지 않는 것 해결 */ } 
		 .selectbox select { 
		 	width: 100%; heifht:auto;
		 	line-height:normal; font-family:inherit;
		 	 padding: .8em .5em; /* 여백과 높이 결정 */ 
		 	 border: 0; opacity: 0; /* 숨기기 */ 
		 	 filter:alpha(opacity=0); /* IE8 숨기기 */ 
		 	 -webkit-appearance: none;/* 네이티브 외형 감추기 */ 
		 	 -moz-appearance: none; appearance: none; }
		 	 
		 	 
	
	/* 회원검색 화면 */
.searchModal {
		display: none; /*Hidden by default */
		position: fixed; /* Stay in place */
		z-index: 10; /* Sit on top */
		left: 0;
		top: 0;
		width: 100%; /* Full width */
		height: 100%; /* Full height */
		overflow: auto; /* Enable scroll if needed */
		background-color: rgb(0,0,0); /* Fallback color */
		background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
	}
	/* Modal Content/Box */
	.search-modal-content {
		background-color: #fefefe;
		margin: 15% auto; /* 15% from the top and centered */
		padding: 20px;
		border: 1px solid #888;
		width: 50%; /* Could be more or less, depending on screen size */
        height: 70%;
	}
    .row { display: flex; flex-wrap: wrap; }

    .flex-row { display: flex; flex-wrap: wrap; justify-content:flex-start; }
    .flex-col { display: flex; flex-direction: column; }
   
    .member1 { margin: 3px; cursor: pointer; }
    .member1 h4 {text-align: center; font-size: 13px; color: white; background-image: linear-gradient(green,yellow); }

    .close {margin-top:auto;}

</style>
</head>

<body>
    <form id="frm" name="frm" action="/saveGameResult.do" method="POST">
    <input type="hidden" name="WinLoseCd" value="W">
    <div class="main col-flex">
        <div class="item flex-item row-flex">
            <h1 class="flex-item">게임 결과 입력</h1>
        </div>
        <div class="item flex-item row-flex">
            <h3 class="flex-item">게임방식</h3>
            <div class="selectbox">
            	<label for="ex_select">경기방식</label>
	            <select class="flex-item" name="sDCd" id="sDCd" >
	                <option value="MS">남자단식(MS)</option>
	                <option value="WS">여자단식(WS)</option>
	                <option value="MD">남자복식(MD)</option>
	                <option value="WD">여자복식(WD)</option>
	                <option value="XD">혼합복식(XD)</option>
	            </select>
            </div>
            <span class="flex-item" style="width:100px"></span>
            <h3>게임코트(Court)</h3>
            <div class="selectbox">
            	<label for="ex_select">코트선택</label>
	            <select class="flex-item" name="courtNo" id="courtNo" >
	                <option value="1" selected>1코드</option>
	                <option value="2">2코드</option>
	                <option value="3">3코드</option>
	                <option value="4">4코드</option>
	                <option value="기타">기타</option>
	            </select>
            </div>
        </div>
    
        <!-- 팀원 -->
        <div class="item flex-item row-flex">
            <div class="flex-item col-flex">
                <div class="flex-item">
                    <h2>우리팀</h2>
                </div>
                <div class="flex-item">
                    <label for="ex_select">본인</label>
                    <input type="hidden" name="mbrId" value="${loginInfo.mbrid}">
                    <input type="text" name="mbrNm" value="${loginInfo.mbrName}">
                    <input class="member-btn"  type="button" value="찾기" style="display:none;">
                </div>
                <div class="flex-item">
                    <label for="ex_select">파트너</label>
                    <input type="hidden" name="teamPlayerId" value="">
                    <input type="text" name="teamPlayerNm" value="">
                    <input class="member-btn" type="button" value="찾기" >
                </div>
            </div>
            <div class="flex-item vs">
                <h1>VS</h1>
            </div>
            <div class="flex-item col-flex">
                <div class="flex-item">
                    <h2>상태팀</h2>
                </div>
                <div class="flex-item row-flex">
                    <label for="ex_select">Player1</label>
                    <input type="hidden" name="oTeamPlayerId1" value="">
                    <input type="text" name="oTeamPlayerNm1" value="">
                    <input class="member-btn"  type="button" value="찾기" >
                </div>
                <div class="flex-item row-flex">
                    <label for="ex_select">Player1</label>
                    <input type="hidden" name="oTeamPlayerId2" value="">
                    <input type="text" name="oTeamPlayerNm2" value="">
                    <input class="member-btn"  type="button" value="찾기" >
                </div>
            </div>
        </div>
        
        <!-- 점수 -->
        <div class="item flex-item row-flex">
            <div class="flex-item row-flex">
                <img src="resources/images/win.jpg" width="40px" height="40px" alt="">
                <img src="resources/images/lose.jpg" width="40px" height="40px" alt="">
                <input type="text" name="ourScore" value="0">
            </div>
            <div class="flex-item vs row-flex"><h3>점 수</h3></div>
            <div class="flex-item row-flex">
                <input type="text" name="ourScore" value="0">
                <img src="resources/images/win.jpg" width="40px" height="40px" alt="">
                <img src="resources/images/lose.jpg" width="40px" height="40px" alt="">
            </div>
        </div>
        
        <!--  저장 -->
        <div class="item flex-item row-flex">
            <input type="button" value="저장" onclick="saveGame();">
            <input type="button" value="취소" onClick="location.href='/'">
        </div>
    </div>

	<!-- 회원 검색 -->
    <div id="modal" class="searchModal">
        <div class="search-modal-content flex-col">
            <div class="page-header">
                <h3>회원선택</h3>
            </div>
            <div><hr></div>
            <div id="memberInfo" class="flex-row">
                <!-- <div class="member1">
                    <h4>홍길동</h4>
                    <div class="mbr-pic">
                        <img src="/resources/images/person.gif" width="100px" height="100px" alt="">
                    </div>
                </div> -->
            </div>

            <div><hr></div>
            <div class="close" style="cursor:pointer;background-color:#DDDDDD;text-align: center;padding-bottom: 10px;padding-top: 10px;" onClick="closeModal();">
                <span class="pop_bt modalCloseBtn" style="font-size: 13pt;">닫기
                </span>
            </div>
        </div>
    </div>
    </form>
</body>
</html>