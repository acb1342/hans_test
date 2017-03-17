
<script type="text/javascript">
	var passwordUpdated = false;
	
	$(function() {
		// 그룹에 따라 수행 액션에 제한을 설정(버튼 블럭)
		$("#changePassword").hide();
		
		// 비밀번호 변경 창 open
		$("#passwordUpdate").click(function(e) {
			e.preventDefault();
			//$("#changePassword").dialog("open");
			$("#changePassword").show();
			
		});
		/* $("#changePassword").dialog({
			autoOpen:false,
			modal:true
		}); */
		
		// 비밀번호 변경 창 닫기
		$("#cancelPwd").click(function(e) {
			$("#password").val('');
			$("#passwordCfm").val('');
			
			$("#changePassword").hide();
			//$("#changePassword").dialog("close");
		});

		// 비밀번호 초기화
		$("#passwordReset").click(function(e) {
			if(confirm("비밀번호를 초기화 하시겠습니까?") == false) return;
 			$.ajax({
				type:'POST',
				url:'/admin/code/resetPassword.json?tid=' + $.now(),
				data:{
					id:$("#id").val(),
					password: '0000'				
				},
				success:function (data) {
					jAlert('비밀번호를 초기화하였습니다.');
					$("#password").val('');
					$("#passwordCfm").val('');
				}
			});
		});
		
		$('#savePwd').click(function(e) {
			if (!Boolean($("#password").val())) {
				$("#passwordError").show().text("비밀번호를 입력해주세요.");
				$("#password").addClass('error');
				return;
			}
			if ($("#password").val() == $("#passwordCfm").val()) {
				$.ajax({
					type:'POST',
					url:'/admin/code/changePassword.json',
					data:{
						id:$("#id").val(),
						password:$("#password").val()					
					},
					success:function (data) {
						jAlert('<fmt:message key="statement.updatePassword.success"/>');
						$("#password").val('');
						$("#passwordCfm").val('');
						//$("#changePassword").dialog("close");
						$("#changePassword").hide();
					}
				});
			} else {
				$("#passwordError").show().text("비밀번호가 일치하지 않습니다.");
				$("#password").addClass('error');
				return;
			}			
		});
		
		// 저장
		$('#save').click(function(e) {
			
			e.preventDefault();
			
			var formData = $("#vForm").serialize();
			console.log(formData);
			var url = "/admin/code/update.htm";
			
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
		
				
	
		// 유효성 체크
		<#--$("#vForm").validate({
			onfocusout:false,
			onkeyup:false,
			rules:{
				homePhone:"number",
				mobilePhone:"required number",
				officePhone:"number",
				email:"required email"
			},
			messages:{
				email:{
					required:'<fmt:message key="validate.required"/>',
					email:'<fmt:message key="validate.email"/>'
				},
				id:{
					required:'<fmt:message key="validate.required"/>'
				},
				password:{
					required:'<fmt:message key="validate.required"/>'
				},
				homePhone:{
					number:'<fmt:message key="validate.number"/>'
				},
				mobilePhone:{
					required:'<fmt:message key="validate.required"/>',
					number:'<fmt:message key="validate.number"/>'
				},
				officePhone:{
					number:'<fmt:message key="validate.number"/>'
				}
			}
		});-->
	});
	
	
	
	function removeAll(target) {
		$("#"+target).find('tr').fadeOut("fast", function() {
			$("#"+target).find('tr').remove();
		});
	}
	
	function cancel() {
		history.back();
	}
</script>
<form method="post" id="vForm" name="vForm" action="/admin/code/update.htm" data-parsley-validate class="form-horizontal form-label-left">
<div class="wrap00">
	<!-- input _ start -->

		<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="first-name">코드 <span class="required">*</span>
			</label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<input type="text" id="id" name="id" required="required"
					class="form-control" value="${admin.code}" readonly="readonly">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="first-name">부모 코드 <span class="required">*</span>
			</label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<input type="text" id="parentsCode" name="parentsCode" required="required"
					class="form-control" value="${admin.parentsCode}" readonly="readonly">
			</div>
		</div>
		
		<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="last-name">코드명<span class="required">*</span></label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<input type="text" id="codeName" name="codeName" value="${admin.codeName}"
					required="required" class="form-control">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="last-name">코드 설명</label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<input type="text" id="codeDesc" name="codeDesc" value="${admin.codeDesc}"
					class="form-control">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="last-name">등록일<span class="required">*</span>
			</label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<label class="control-label"
					for="last-name">${admin.regDate?string("yyyy-MM-dd HH:mm")}
				</label>
			</div>
		</div>
		
		<div class="ln_solid"></div>
		<div class="form-group">
			<div class="col-md-12 col-sm-6 col-xs-12" align="right">
				<button type="button" class="btn btn-success" id="save">저장</button>
				<button type="button" class="btn btn-danger" onclick="javascript:page_move('/admin/code/detail.htm','${admin.code}')">취소</button>
			</div>
		</div>
		
	<!-- button _ end -->
</div>
</form>