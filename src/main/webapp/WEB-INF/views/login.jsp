<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">

<title>로그인</title>

<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" >
<!-- <link rel="stylesheet" href="/style.css"> -->

<!-- login css -->
<link href="resources/css/customized/login.css" rel="stylesheet">

</head>

<script type="text/javascript">
$(function(){
    var responseMessage = "${msg}";
    if(responseMessage != ""){
        alert(responseMessage);
    }
}) ;

function toggleResetPswd(e){
    e.preventDefault();
    $('#logreg-forms .form-signin').toggle() // display:block or none
    $('#logreg-forms .form-reset').toggle() // display:block or none
}

function toggleSignUp(e){
    e.preventDefault();
/*     $('#logreg-forms .form-signin').toggle(); // display:block or none
    $('#logreg-forms .form-signup').toggle(); // display:block or none */
    location.href="/registerform.do";
}

$(()=>{
    // Login Register Form
    $('#logreg-forms #forgot_pswd').click(toggleResetPswd);
    $('#logreg-forms #cancel_reset').click(toggleResetPswd);
    $('#logreg-forms #btn-signup').click(toggleSignUp);
    $('#logreg-forms #cancel_signup').click(toggleSignUp);
})

///////////////////---------------------------------------------

// 회원가입 폼의 유효성 검사
		function fn_checkForm() {
			var re = /^[a-zA-Z0-9]{4,12}$/ // 아이디와 패스워드가 적합한지 검사할 정규식
			var re2 = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
			// 이메일이 적합한지 검사할 정규식
			var regPhone = /^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/;

			var id = $("#mbrid");
			var pw = $("#mbrpw");
			
			if (!check(re, id, "아이디는 4~12자의 영문 대소문자와 숫자로만 입력")) {
				return false;
			}

			if (!check(re, pw, "패스워드는 4~12자의 영문 대소문자와 숫자로만 입력")) {
				return false;
			}
			
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
		
		///////////////////// sign in with sns ////////////////////
		function signInWithSns(sns_url){
			location.href=sns_url;
			
		} 

</script>

<body>
    <div id="logreg-forms">
        <form class="form-signin" action="/login.do" onSubmit="return fn_checkForm();" method="post">
            <h1 class="h3 mb-3 font-weight-normal" style="text-align: center"> Sign in</h1>
            <div class="social-login">
                <button class="btn facebook-btn social-btn" type="button" onClick="signInWithSns('${naver_url}');"><span><i class="fab fa-facebook-f"></i> Sign in with Naver</span> </button>
                <button class="btn google-btn social-btn" type="button" onClick="signInWithSns('${google_url}');"><span><i class="fab fa-google-plus-g"></i> Sign in with Google</span> </button>
            </div>
            <p style="text-align:center"> OR  </p>
            <input type="text" id="inputId" name="mbrid" class="form-control" placeholder="아이디를 입력하세요!" required="" autofocus="">
            <input type="password" id="inputPassword" name="mbrpw" class="form-control" placeholder="비밀번호를 입력하세요." required="">
            
            <button class="btn btn-success btn-block" type="submit"><i class="fas fa-sign-in-alt"></i> Sign in</button>
            <a href="#" id="forgot_pswd">Forgot password?</a>
            <hr>
            <!-- <p>Don't have an account!</p>  -->
            <button class="btn btn-primary btn-block" type="button" id="btn-signup"><i class="fas fa-user-plus"></i> Sign up New Account</button>
        </form>

        <form action="/reset/password/" class="form-reset">
            <input type="email" id="resetEmail" class="form-control" placeholder="Email address" required="" autofocus="">
            <button class="btn btn-primary btn-block" type="submit">Reset Password</button>
            <a href="#" id="cancel_reset"><i class="fas fa-angle-left"></i> Back</a>
        </form>
        
        <form action="/signup/" class="form-signup">
            <div class="social-login">
                <button class="btn facebook-btn social-btn" type="button"><span><i class="fab fa-facebook-f"></i> Sign up with Facebook</span> </button>
            </div>
            <div class="social-login">
                <button class="btn google-btn social-btn" type="button"><span><i class="fab fa-google-plus-g"></i> Sign up with Google+</span> </button>
            </div>
            
            <p style="text-align:center">OR</p>

            <input type="text" id="user-name" class="form-control" placeholder="Full name" required="" autofocus="">
            <input type="email" id="user-email" class="form-control" placeholder="Email address" required autofocus="">
            <input type="password" id="user-pass" class="form-control" placeholder="Password" required autofocus="">
            <input type="password" id="user-repeatpass" class="form-control" placeholder="Repeat Password" required autofocus="">

            <button class="btn btn-primary btn-block" type="submit"><i class="fas fa-user-plus"></i> Sign Up</button>
            <a href="#" id="cancel_signup"><i class="fas fa-angle-left"></i> Back</a>
        </form>
        <br>
    </div>

</body>
</html>