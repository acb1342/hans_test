<#-- <%@ include file="/include/header.jspf" %>-->
<script type="text/javascript">
	$(function() {
		
		// 저장
		$('#save').click(function(e) {
			if ($('#isIdCheckComplete').val() == '' || $('#regId').val() == '') {
				alert("ID 중복확인을 해주세요.");
				return;
			}

			var formData = $("#vForm").serialize();
			var url = "/admin/operator/create.htm";
			
			$.ajax({
				type : "POST",
				url : url,
				data : formData,			
				success : function(response){
					$("#content").html(response);
				},
				error : function(){
					console.log("error!!");
					//err_page();
					return false;
				}
			});
			
			
		});
		
		// 이전 페이지로 이동
		$('#cancel').click(function(e) {
			window.location.href = "/admin/operator/search.htm";
		});
	
		// 중복 확인 버튼
		$('#idCheckBtn').click(function(e) {
			if (!idValCheck()) return;
			else {
				$.ajax({
					type:'POST',
					url:'/admin/operator/checkid.json',
					data:{
						id:$("#regId").val(),					
					},
					success:function (data) {						
						if(!data) {
							alert("사용할 수 없는 ID 입니다.");
							return;
						}
						alert("사용가능한 ID 입니다.");
						document.getElementById("isIdCheckComplete").value = 1;
					},
					error: function(e) {
					}
				});
			}
		});
		
		<#--
		// 유효성 체크
		$.validator.addMethod("passwordConfirm", function(value, element) {
			return $("#passwd").val() == $("#passwdCfm").val();
		},"비밀번호가 일치하지 않습니다.");
	
		$.validator.addMethod("passwdMix", function(value, element) {
			var pattern = /^.*(?=.{8,64})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
			var pwd = $("#passwd").val();
			if (pattern.test(pwd)) return true;
			else return false;
		},"<fmt:message key='validate.passwdMinlength'/>");
		
		$("#vForm").validate({
			onfocusout:false,
			onkeyup:false,
			rules:{
				passwd:{
					required:true,
					passwordConfirm:true,
					passwdMix:true
				},
				passwdCfm:{
					required:true,
					passwordConfirm:true,
				},
				tel:"number",
				mobile:"required number",
				email:"required email"
			},
			messages:{
				email:{
					required:'<fmt:message key="validate.required"/>',
					email:'<fmt:message key="validate.email"/>'
				},
				passwd:{
					required:'<fmt:message key="validate.required"/>'
				},
				passwdCfm:{
					required:'<fmt:message key="validate.required"/>'
				},
				tel:{
					number:'<fmt:message key="validate.number"/>'
				},
				mobile:{
					required:'<fmt:message key="validate.required"/>',
					number:'<fmt:message key="validate.number"/>'
				}
			}
		});
		
		-->
	});
	
	function idValCheck() {
		var strSpace = $('#regId').val().replace(/s/gi,'');
		var blank = /^\s+|\s+$/g;
		var pattern = /^.*(?=.{8,32})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
		
		// ID 공백체크
		if (strSpace == '') {
			alert("ID를 입력해주세요.");
			$('#regId').focus();
			return;
		}
		else if(strSpace.replace(blank,'') == "") {
			alert("공백은 사용이 불가합니다.");
			return;
		}
		else if (!$('#regId').val().match(pattern)) {
			alert("ID는 영문+숫자 8자 이상이어야 합니다.");
			return;
		}
		
		return true;
	}
	
	function onkeydownId() {
		if ($('#isIdCheckComplete').val() == 1) {
			document.getElementById("isIdCheckComplete").value = '';
		}
	}
</script>
<spring:hasBindErrors name="admin"/>
<form method="post" id="vForm" name="vForm" action="/admin/operator/create.htm"  data-parsley-validate class="form-horizontal form-label-left">
<input type="hidden" id="isIdCheckComplete" value=""/>
<div class="wrap00">

	<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="last-name">ID</label>
			<div class="col-md-7 col-sm-6 col-xs-12">
				<input type="text" class="form-control" id="regId" name="id" onkeydown="javascript:onkeydownId()">
			</div>
			<div class="col-md-3 col-sm-6 col-xs-12">	
			<input type="button" class="form-control col-md-3" id="idCheckBtn" value="중복확인" style="background:#ededed;"/>
			</div>
	</div>
	
	<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="last-name">이름</label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<input type="text" class="form-control" id="name" name="name"">
			</div>
	</div>	
	
	<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="last-name">비밀번호</label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<input type="password" class="form-control" id="passwd" name="passwd" maxlength="16" >
			</div>
	</div>	
	<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="last-name">비밀번호 확인</label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<input type="password" class="form-control" id="passwdCfm" name="passwdCfm" maxlength="16">
			</div>
	</div>	
	<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="last-name">사용자 그룹</label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<select class="form-control"  id="adminGroup" name="adminGroup.id" onchange="afterUserGroupCheck()">
					<#-- <#list adminGroupList as group>
					<option value="${group.id}">${group.name}</option>
					</#list> --%>
				</select>			
			</div>
	</div>	
	<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="last-name">연락처</label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<input type="text" class="form-control" name="tel">
			</div>
	</div>
	<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="last-name">휴대전화</label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<input type="text" class="form-control" name="mobile">
			</div>
	</div>
	<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="last-name">이메일</label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<input type="text" class="form-control" name="email">
			</div>
	</div>		
	<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="last-name">회사주소</label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<input type="text" class="form-control" name="area">
			</div>
	</div>		
	
	<div class="ln_solid"></div>
	<div class="form-group">
		<div class="col-md-12 col-sm-6 col-xs-12" align="right">
			<button type="button" class="btn btn-success" id="save">추가</button>
			<button type="button" class="btn btn-danger" onclick="javascript:page_move('/admin/operator/search.htm')">취소</button>
		</div>
	</div>
			
	
	<!-- button _ end -->
</div>
</form>
<#-- <%@ include file="/include/footer.jspf" %> -->