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
			//$('button').click(function() {
	        //var $href = $(this).attr('href');
	        layer_popup();
	    });
	});

	
	
    function layer_popup(){

        var $el = $('#layer2');        //레이어의 id를 $el 변수에 저장
        var isDim = $el.prev().hasClass('dimBg');   //dimmed 레이어를 감지하기 위한 boolean 변수

       
        $('.dim-layer').fadeIn();
        
        //isDim ? $('.dim-layer').fadeIn() : $el.fadeIn();
		
        // ~~ : Math.floor() 와 동등하게 쓰이는 연산자(소스아래 자리 없앤다.)
        var $elWidth = ~~($el.outerWidth()),
            $elHeight = ~~($el.outerHeight()),
            docWidth = $(document).width(),
            docHeight = $(document).height();

        alert($elWidth +",\n" + $elHeight +", \n"+ docWidth +", \n"+docHeight );

        // 화면의 중앙에 레이어를 띄운다.
        if ($elHeight < docHeight || $elWidth < docWidth) {
            $el.css({
                marginTop: -$elHeight /2,
                marginLeft: -$elWidth/2
            })
        } else {
            $el.css({top: 0, left: 0});
        }

        $el.find('a.btn-layerClose').click(function(){
            isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); // 닫기 버튼을 클릭하면 레이어가 닫힌다.
            return false;
        });

        $('.dimBg').click(function(){
            $('.dim-layer').fadeOut();
            return false;
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
         .vs {width:200px;}
         
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
		.pop-layer .pop-container {
		  padding: 20px 25px;
		}
		
		p.ctxt {
		  color: #666;
		  line-height: 25px;
		}
		
		.btn-r {
		  width: 100%;
		  margin: 10px 0 20px;
		  padding-top: 10px;
		  border-top: 1px solid #DDD;
		  text-align: right;
		}
		
		.dim-layer {
		  display: none;
		  position: fixed;
		  _position: absolute;
		  top: 0;
		  left: 0;
		  width: 100%;
		  height: 100%;
		  z-index: 100;
		}
		
		.dim-layer .dimBg {
		  position: absolute;
		  top: 0;
		  left: 0;
		  width: 100%;
		  height: 100%;
		  background: #000;
		  opacity: .5;
		  filter: alpha(opacity=50);
		}
		
		.dim-layer {
		  display: block;
		}
		
		a.btn-layerClose {
		  display: inline-block;
		  height: 25px;
		  padding: 0 14px 0;
		  border: 1px solid #304a8a;
		  background-color: #3f5a9d;
		  font-size: 13px;
		  color: #fff;
		  line-height: 25px;
		}
		
		a.btn-layerClose:hover {
		  border: 1px solid #091940;
		  background-color: #1f326a;
		  color: #fff;
		}
</style>
</head>

<body>
    <form id="frm" name="frm" action="/saveGameResult.do" method="POST">
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
                    <input class="member-btn"  type="button" value="찾기">
                </div>
                <div class="flex-item">
                    <label for="ex_select">파트너</label>
                    <input type="hidden" name="teamPlayerId" value="">
                    <input type="text" name="teamPlayerNm" value="">
                    <input class="member-btn" type="button" value="찾기" >
                </div>
            </div>
            <div class="flex-item vs row-flex">
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
                    <input type="button" value="찾기" onclick="findMember(1);">
                </div>
                <div class="flex-item row-flex">
                    <label for="ex_select">Player1</label>
                    <input type="hidden" name="oTeamPlayerId2" value="">
                    <input type="text" name="oTeamPlayerNm2" value="">
                    <input type="button" value="찾기" onclick="findMember(2);">
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
    </form>
    
	<!-- 회원 검색 -->
	<div class="dim-layer">
	    <div class="dimBg"></div>
	    <div id="layer2" class="pop-layer">
	        <div class="pop-container">
	            <div class="pop-conts">
	                <!--content //-->
	                <p class="ctxt">Thank you.<br>
	                    Your registration was submitted successfully.<br>
	                    Selected invitees will be notified by e-mail on JANUARY 24th.<br><br>
	                    Hope to see you soon!
	                </p>
	
	                <div class="btn-r">
	                    <a href="#" class="btn-layerClose">Close</a>
	                </div>
	                <!--// content-->
	            </div>
	        </div>
	    </div>
	</div>
    
    
</body>
</html>