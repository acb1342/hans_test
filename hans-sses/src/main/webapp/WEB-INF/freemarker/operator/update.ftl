
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
				url:'/admin/operator/resetPassword.json?tid=' + $.now(),
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
					url:'/admin/operator/changePassword.json',
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
			var url = "/admin/operator/update.htm";
			
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
<form method="post" id="vForm" name="vForm" action="/admin/operator/update.htm" data-parsley-validate class="form-horizontal form-label-left">
<div class="wrap00">
	<!-- input _ start -->

		<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="first-name">ID <span class="required">*</span>
			</label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<input type="text" id="id" name="id" required="required"
					class="form-control" value="${admin.id}" readonly="readonly">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="last-name">이름
			</label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<input type="text" id="name" name="name"
					class="form-control" value="${admin.name}" >
			</div>
		</div>
		
		<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="last-name">비밀번호<span class="required">*</span>
			</label>
			<div class="col-md-10 col-xs-12">
				<button class="btn btn-primary" id="passwordUpdate" type="button">비밀번호 변경</button>
				<button class="btn btn-success" id="passwordReset" type="button">비밀번호 초기화</button>
			</div>
		</div>
		<!-- 패스워드 변경 창 -->
		<#--<div id="changePassword">
		
			<div class="form-group">
				<label class="control-label col-md-2 col-sm-3 col-xs-12"
					for="last-name">패스워드<span class="required">*</span>
				</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<label class="control-label"
						for="last-name">
						<input type="password" name="password" id="password" style="width:160px;" maxlength="16"/>
						<label class="error" for="password" id="passwordError" generated="true" style="display:none;"></label>
					</label>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12"
					for="last-name">패스워드 확인<span class="required">*</span>
				</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<label class="control-label"
						for="last-name">
						<input type="password" name="passwordCfm" id="passwordCfm" style="width:160px;" maxlength="16"/>
					</label>
				</div>
				<input type="button" id="savePwd" value='저장'/>
				<input type="button" id="cancelPwd" value='취소'/>
			</div>
		
			<br/>
		</div>-->
		
		<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="last-name">사용자 그룹<span class="required">*</span>
			</label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<label class="control-label"
					for="last-name">
					<input type="hidden" name="adminGroup.id" id="adminGroup" value="${admin.groupId}"/>${admin.groupName}
					<input type="hidden" name="adminGroup.name" id="adminGroupName" value="${admin.groupName}"/>
				</label>
			</div>
		</div>
		<div class="form-group">
			<label for="middle-name"
				class="control-label col-md-2 col-sm-3 col-xs-12">연락처</label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<input id="tel" class="form-control"
					required="required"type="text" name="tel" value="${admin.tel}">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="last-name">휴대전화<span class="required">*</span></label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<input type="text" id="mobile" name="mobile" value="${admin.mobile}"
					required="required" class="form-control">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="last-name">이메일<span class="required">*</span></label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<input type="text" id="email" name="email" value="${admin.email}"
					required="required" class="form-control">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="last-name">회사 주소</label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<input type="text" id="area" name="area" value="${admin.area}"
					class="form-control">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="last-name">등록일<span class="required">*</span>
			</label>
			<div class="col-md-10 col-sm-6 col-xs-12">
				<label class="control-label"
					for="last-name">${admin.fstRgDt?string("yyyy-MM-dd HH:mm")}
				</label>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="last-name">계정 상태</label>
			<div class="col-md-10 col-sm-6 col-xs-12">
			<label class="control-label" for="last-name">
             <input type="radio" class="flat" id="validY" name="validYn" value="Y" <#if admin.validYn == 'Y'>checked</#if>/>사용중
             <input type="radio" class="flat" id="validN" name="validYn" value="N" <#if admin.validYn == 'N'>checked</#if>/>사용중지
             
			</label>
			</div>
		</div>
		
		<div class="ln_solid"></div>
		<div class="form-group">
			<div class="col-md-12 col-sm-6 col-xs-12" align="right">
				<button type="button" class="btn btn-success" id="save">저장</button>
				<button type="button" class="btn btn-danger" onclick="javascript:page_move('/admin/operator/detail.htm','${admin.id}')">취소</button>
			</div>
		</div>
		
	<!-- button _ end -->
</div>
</form>