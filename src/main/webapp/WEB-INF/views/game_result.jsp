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
</head>

<body>
    <form action="">
    <div id="wrap">
        <div id="title"><h1>게임 결과 입력</h1></div>
        <div>
            <div id="sdcd_text"><span>단식 복식 구분</span></div>
            <div id="sdcd">
                <select name="sdcd" id="sdcd">
                    <option value="D" selected>복식</option>
                    <option value="S" selected>단식</option>
                </select>
            </div>
        </div>
        <div class="ourteam">
            <div><h2>Our Team</h2></div>
            <div>
                <input type="hidden" name="mbrId" value="${loginInfo.mbrId}">
                <span>${loginInfo.mbrName}</span>
            </div>
            <div>
                <input type="hidden" name="teamPlayerId" value="">
                <input type="text" name="teamPlayerNm" value="">
                <button value="찾기"/>
            </div>
            <div>
                <input type="text" name="ourScore" value="0">
            </div>
        </div>
        <div><h1>VS</h1></div>
        <div class="opponentTeam">
            <div><h2>Opponent Team</h2></div>
            <div>
                <input type="text" name="opponetScore" value="0">
            </div>
            <div>
                <input type="hidden" name="oTeamPlayerId1" value="">
                <input type="text" name="oTeamPlayerNm1" value="">
                <button value="찾기"/>
            </div>
            <div>
                <input type="hidden" name="oTeamPlayerId2" value="">
                <input type="text" name="oTeamPlayerNm2" value="">
                <button value="찾기"/>
            </div>
        </div>
        <div>
            <div><h2>승패(Win or Lose)</h2></div>
            <div>
                <input type="radio" name="winLoseCd" id="winLoseCd">
                <input type="radio" name="winLoseCd" id="winLoseCd">
            </div>
        </div>
        <div>
            <div><h2>코트(Court)</h2></div>
            <div>
                <input type="text" name="courtNo" id="courtNo">
            </div>
        </div>
        <div>
            <input type="button" value="저장" onclick="saveGame();">
            <input type="button" value="취소" onclick="goHome();">
        </div>
    </div>
    </form>
</body>
</html>