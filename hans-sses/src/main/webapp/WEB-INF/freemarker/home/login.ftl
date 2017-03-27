<!DOCTYPE html>
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
<title>MOBILEPARK!</title>

	<!-- Bootstrap -->
    <link href="/css/gentelella-master/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="/css/gentelella-master/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="/css/gentelella-master/vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- Animate.css -->
    <link href="/css/gentelella-master/vendors/animate.css/animate.min.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="/css/gentelella-master/build/css/custom.min.css" rel="stylesheet">
    <link href="/js/jquery/alert/jquery.alerts.css" rel="stylesheet">

<script type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript" src="/js/jquery/alert/jquery.alerts.custom.js"></script>
<script type="text/javascript" src="/js/jquery/alert/jquery.alerts.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript">
	$(function() {
		// ID 저장 속성
		var checkId = getCookie("chk_id");
		if (checkId != 'undefined' && checkId != '') {
			$('#idSave').attr('checked', true);
		}
		
		// ID 불러오기
		var id = getCookie("idIpt");
		if (id.length > 0) {
			$('#idIpt').val(id);
		}		
		
		$("#idSave").click(function () {
			setCookieId();
		});
	});
	
	// 쿠키에 ID 저장
	function setCookieId() {
		var today = new Date();
		var expires = new Date();
		expires.setTime(today.getTime() + 1000*60*60*24*365);
		var checkId = $('#idSave').attr('checked');		
		setCookie("chk_id", checkId, expires, "/");
		
		if (checkId) {
			if ($('#idIpt').val() != '') {
				setCookie("idIpt", $('#idIpt').val(), expires, "/");
			}
		} else {
			setCookie("idIpt", '', expires, "/");
		}
	}	
	
	function login(forceFlag) {
		setCookieId();
		
		var data;
		
		var id = $("#idIpt").val();
		var pw = $("#pwIpt").val();
		
		if (forceFlag) {
			data = {userId:id, password:pw, forceFlag:'Y'};	
		} else {
			data = {userId:id, password:pw, forceFlag:''};
		}		
		$.ajax({
			url:'/home/login.json',
			type:"POST",
			data:data,		    
			success:function(result) {
				if (result.success) {				
					document.location.href = '/home/home.htm';
				} else {								
					if (result.errors.reason == 'loginDuple') {
						jConfirm('동일한 계정이 접속 중 입니다. 연결을 끊으시겠습니까? ', 'confirm', function(r) {
							if (r) {
								login(true);
							} else {
								$("#idIpt").val('');
								$("#pwIpt").val('');
							}
						});
					} else if (result.errors.reason == 'loginCount') {
						jAlert('해당 계정은 비밀번호 5회 이상 오류로 인해\n운영자 문의 후 비밀번호 재설정을 해야 합니다.', 'alert', function() {});
						$("#idIpt").val('');
						$("#pwIpt").val('');
					} else {						
						jAlert('로그인 실패, 다시 시도 해주세요.', 'alert', function() {});
						$("#idIpt").val('');
						$("#pwIpt").val('');
					}
				}
			}
		});
	}
</script>
</head>


<body class="login">
    <div>
      <a class="hiddenanchor" id="signup"></a>
      <a class="hiddenanchor" id="signin"></a>

      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
            <form>
            
            <img src="/images/login/txt_login.png" style="max-width: 100%; height: auto;">
              <p>
              
              <div>
                <input type="text" class="form-control" name="idIpt" id="idIpt" placeholder="Username" required="" onkeypress="if (event.keyCode == 13) {login('');}"/>
              </div>
              <div>
                <input type="password" class="form-control" name="pwIpt" id ="pwIpt" placeholder="Password" required="" onkeypress="if (event.keyCode == 13) {login('');}"/>
              </div>
              <div class="idSave" align="right">
					<label for="idSave">Save ID </label>
					<input type="checkbox" class="inputCheck" name="idSave" id="idSave">
				</div>
              
              <div>
                <a href="javascript:login('')" class="btn btn-default submit"  style="width:100%">Login</a>
              </div>
              <div class="separator"></div>

            </form>
          </section>
        </div>

        
      </div>
    </div>
  </body>


</html>