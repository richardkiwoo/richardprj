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


</script>
<style type="text/css">
	#wrap {width : 800px; height:600px; background:#888;}
	.title {width:100%;}
	.sdcd {width: 100%; }
	.sdcd_t {width:30%; float:left;}
	.sdcd_v{width:70%; float:left;}
	.ourteam{width:45%; float:left; background:#777;}
	  .ourteam .tit{width:100%;}
	  .plyer1 {width:50%;}
	  .plyer2 {width:50%;}
	  .ourScore {width:30%; float:left;}
	.vs{width:10%; float:left;}
	.opponentTeam{width:45%; float:left;}
	
	
</style>
</head>

<body>
    <form id="frm" name="frm" action="/saveGameResult.do" method="POST">
    <div id="wrap">
        <div class="title"><h1>게임 결과 입력</h1></div>
        <div class="sdcd">
            <div class="sdcd_t"><span>단식 복식 구분</span></div>
            <div class="sdcd_v">
                <select name="sDCd" id="sDCd">
                    <option value="D" selected>복식</option>
                    <option value="S" selected>단식</option>
                </select>
            </div>
        </div>
        <div class="ourteam">
            <div class="tit"><h2>Our Team</h2></div>
            <div class="plyer1">
                <input type="hidden" name="mbrId" value="${loginInfo.mbrid}">
                 <input type="text" name="mbrNm" value="${loginInfo.mbrName}">
                <span></span>
            </div>
            <div class="plyer2">
                <input type="hidden" name="teamPlayerId" value="">
                <input type="text" name="teamPlayerNm" value="">
                <input type="button" value="찾기" onclick="findMember(0);">
            </div>
            <div class="ourScore">
                <input type="text" name="ourScore" value="0">
            </div>
        </div>
        <div class="vs"><h1>VS</h1></div>
        <div class="opponentTeam">
            <div class="tit"><h2>Opponent Team</h2></div>
            <div>
                <input type="text" name="opponentScore" value="0">
            </div>
            <div>
                <input type="hidden" name="oTeamPlayerId1" value="">
                <input type="text" name="oTeamPlayerNm1" value="">
                <input type="button" value="찾기" onclick="findMember(1);">
            </div>
            <div>
                <input type="hidden" name="oTeamPlayerId2" value="">
                <input type="text" name="oTeamPlayerNm2" value="">
                <input type="button" value="찾기" onclick="findMember(2);">
            </div>
        </div>
        <div>
            <div><h2>승패(Win or Lose)</h2></div>
            <div>
            	<label><input type="radio" name="winLoseCd" value="W" checked> Win</label>
            	<label><input type="radio" name="winLoseCd" value="L"> Lose</label>
            </div>
        </div>
        <div>
            <div><h2>코트(Court)</h2></div>
            <div>
                <input type="text" name="courtNo" id="courtNo">
            </div>
        </div>
        <div class="save">
            <input type="button" value="저장" onclick="saveGame();">
            <input type="button" value="취소" onClick="location.href='/'">
        </div>
    </div>
    </form>
</body>
</html>