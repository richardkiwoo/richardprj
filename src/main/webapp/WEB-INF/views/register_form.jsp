<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>회원가입</title>
        <!-- Bootstrap -->
        <link href="resources/css/bootstrap.min.css" rel="stylesheet">
        <!-- font awesome -->
        <link href="resources/css/font-awesome.css" rel="stylesheet">
        <!-- Custom style -->
        <!-- <link rel="stylesheet" href="../plugin/bootstrap/css/style.css" media="screen" title="no title" charset="utf-8"> -->

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <!-- <script src="../plugin/bootstrap/js/bootstrap.min.js"></script>
        <script src="../config/js/join.js"></script> -->
    </head>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
	<script>
	    $(function(){
	        var responseMessage = "${message}";
	        if(responseMessage != ""){
	            alert(responseMessage);
	        }
	    }) ;
	    
	//아이디 중복 체크	    
	function checkIDDup() {
		if ($("#mbrid").val() == '') {
			alert("아이디를 입력해 주세요!");
			return false;
		}
		
		$.ajax({
			url: "checkID.do",
		    type: "POST",
		    cache: false,
		    dataType: "json",
		    data: $('#frm').serialize(),
			success : function(result) {
				var memberCnt = "${result.memberCnt}";
				//alert(result.memberCnt);
				if(result.memberCnt != "" && result.memberCnt > 0){
		            alert(memberCnt + " ㅋㅋㅋㅋ 이미 사용중인 ID입니다.");
		        }else {
		        	alert(memberCnt + "ㅇㅇㅇ 사용가능한 ID입니다.");
		        	$('#isIDUnique').val('0');
		        }
			}
		});
	}
	

// 회원가입 폼의 유효성 검사
		function fn_checkForm() {
			var re = /^[a-zA-Z0-9]{4,12}$/ // 아이디와 패스워드가 적합한지 검사할 정규식
			var re2 = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
			// 이메일이 적합한지 검사할 정규식
			var regPhone = /^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/;

			var id = $("#mbrid");
			var name = $("#mbrName");
			var pw = $("#mbrpw");
			var email = $("#mbrEmail");
			var num1 = $("#mbrMobile");

			var isIdUnique = $("#isIDUnique").val();

			// ------------ 이메일 까지 -----------
			if (isIdUnique == '1') {
				alert("아이디 중복확인을 해주세요!");
				return false;
			}

			if ($('#mbrName').val() == "") {
				alert("이름을 입력해 주세요");
				$('#mbrName').focus();
				return false;
			}

			if (!check(re, id, "아이디는 4~12자의 영문 대소문자와 숫자로만 입력")) {
				return false;
			}

			if (!check(re, pw, "패스워드는 4~12자의 영문 대소문자와 숫자로만 입력")) {
				return false;
			}

			if ($('#mbrpw').val() != $('#mbrPwChk').val()) {
				alert("비밀번호가 다릅니다. 다시 확인해 주세요.");
				$('#mbrpw').val('');
				$('#mbrpw').focus();
				return false;
			}

			if (email.val() == "") {
				alert("이메일을 입력해 주세요");
				email.focus();
				return false;
			}

			if (!check(re2, email, "적합하지 않은 이메일 형식입니다.")) {
				return false;
			}

			if (!check(regPhone, num1, "적합하지 않은 휴대폰 형식입니다.")) {
				return false;
			}

		}
		
		//아이디를 바꿨을 때 중복체크를 재 요청해야 한다.
		function fn_resetCheckId() {
			 $("#isIDUnique").val('1');
		}

		function check(re, what, message) {
			if (re.test(what.val())) {
				return true;
			}
			alert(message);
			what.value = "";
			what.focus();
			//return false;
		}
	</script>

    <body>
        <article class="container">
            <div class="page-header">
                <div class="col-md-6 col-md-offset-3">
                <h3>회원가입 Form</h3>
                </div>
            </div>
            <div class="col-sm-6 col-md-offset-3">
                <form role="form" id="frm" action="/register.do" onSubmit="return fn_checkForm();" method="post">
                <input type="hidden" id="isIDUnique" name="isIDUnique" value="1" >
                    <div class="form-group">
                        <label for="inputID">아이디</label>
                        <input type="text" class="form-control" id="mbrid" name="mbrid" onChange="fn_resetCheckId();" placeholder="아이디를 입력해 주세요">
                        <input type="button" class="btn btn-primary" value="중복확인" onClick="checkIDDup()">
                    </div>
                    <div class="form-group">
                        <label for="inputName">성명</label>
                        <input type="text" class="form-control" id="mbrName" name="mbrName" placeholder="이름을 입력해 주세요">
                    </div>
                    <div class="form-group">
                        <label for="InputEmail">이메일 주소</label>
                        <input type="email" class="form-control" id="mbrEmail" name="mbrEmail"  placeholder="이메일 주소를 입력해주세요">
                    </div>
                    <div class="form-group">
                        <label for="inputPassword">비밀번호</label>
                        <input type="password" class="form-control" id="mbrpw" name="mbrpw"  placeholder="비밀번호를 입력해주세요">
                    </div>
                    <div class="form-group">
                        <label for="inputPasswordCheck">비밀번호 확인</label>
                        <input type="password" class="form-control" id="mbrPwChk" placeholder="비밀번호 확인을 위해 다시한번 입력 해 주세요">
                    </div>
                    <div class="form-group">
                        <label for="inputMobile">휴대폰 번호</label>
                        <input type="tel" class="form-control" id="mbrMobile" name="mbrMobile"  placeholder="휴대폰번호를 입력해 주세요">
                    </div>
                    
                    <div class="form-group">
                    <label>약관 동의</label>
                    <div data-toggle="buttons">
                    <label class="btn btn-primary active">
                    <span class="fa fa-check"></span>
                    <input id="agree" type="checkbox" autocomplete="off" checked>
                    </label>
                    <a href="#">이용약관</a>에 동의합니다.
                    </div>
                    </div>

                    <div class="form-group text-center">
                        <button type="submit" id="join-submit" class="btn btn-primary">
                            회원가입<i class="fa fa-check spaceLeft"></i>
                        </button>
                        <!-- <button type="submit" class="btn btn-warning">
                            가입취소<i class="fa fa-times spaceLeft"></i>
                        </button> -->
                        <input type="button" value="가입취소" onClick="location.href='/'" class="btn btn-warning">
                    </div>
                </form>
            </div>

        </article>
    </body>
</html>
