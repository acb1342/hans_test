<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroup" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	var passwordUpdated = false;
	
	$(function() {
		// 그룹에 따라 수행 액션에 제한을 설정(버튼 블럭)
		afterUserGroupCheck();
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

			//$("#vForm").submit();
			
			console.log("formData = "+ formData);
			
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
		$("#vForm").validate({
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
		});
	});
	
	// button disable
	function afterUserGroupCheck() {
		if ($("#adminGroupName").val() == '<%=AdminGroup.CP_GROUP_NAME%>') {
			$("#addMcp").attr("disabled", false);
			$("#removeMcp").attr("disabled", false);
			$("#addAcctInfo").attr("disabled", false);
			$("#addSettleInfo").attr("disabled", false);
			$("#removeSettleInfo").attr("disabled", false);
		} else if ($("#adminGroupName").val() == '<%=AdminGroup.MCP_GROUP_NAME%>' || $("#adminGroupName").val() == '<%=AdminGroup.CP_MCP_GROUP_NAME%>') {
			$("#addMcp").attr("disabled", true);
			$("#removeMcp").attr("disabled", true);
			$("#addAcctInfo").attr("disabled", false);
			$("#addSettleInfo").attr("disabled", false);
			$("#removeSettleInfo").attr("disabled", true);
		} else if ($("#adminGroupName").val() == '<%=AdminGroup.SELLER_GROUP_NAME%>') {
			$("#addMcp").attr("disabled", true);
			$("#addAcctInfo").attr("disabled", false);
			$("#addSettleInfo").attr("disabled", false);
			removeAll('relatedMCPs');			
		} else {
			$("#addMcp").attr("disabled", true);
			$("#removeMcp").attr("disabled", true);
			$("#addAcctInfo").attr("disabled", true);
			$("#addSettleInfo").attr("disabled", true);
			$("#removeSettleInfo").attr("disabled", true);
			removeAll('relatedMCPs');
		}
	}
	
	function removeAll(target) {
		$("#"+target).find('tr').fadeOut("fast", function() {
			$("#"+target).find('tr').remove();
		});
	}
	
	function cancel() {
		history.back();
	}
</script>
<spring:hasBindErrors name="admin"/>
<form method="post" id="vForm" name="vForm" action="/admin/operator/update.htm" data-parsley-validate class="form-horizontal form-label-left">
<div class="wrap00">
	<!-- input _ start -->

		<div class="form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12"
				for="first-name">운영자 ID <span class="required">*</span>
			</label>
			<div class="col-md-6 col-sm-6 col-xs-12">
				<input type="text" id="id" name="id" required="required"
					class="form-control col-md-7 col-xs-12" value="${admin.id}" readonly="readonly">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12"
				for="last-name">운영자 이름
			</label>
			<div class="col-md-6 col-sm-6 col-xs-12">
				<input type="text" id="name" name="name"
					class="form-control col-md-7 col-xs-12" value="${admin.name}" >
			</div>
		</div>
		
		<div class="form-group">
			<label class="control-label col-md-1 col-sm-3 col-xs-12"
				for="last-name">비밀번호<span class="required">*</span>
			</label>
			<div class="col-md-11 col-xs-12">
				<button class="btn btn-primary" id="passwordUpdate" type="button">비밀번호 변경</button>
				<button class="btn btn-success" id="passwordReset" type="button">비밀번호 초기화</button>
			</div>
		</div>
		<!-- 패스워드 변경 창 -->
		<div id="changePassword">
		
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12"
					for="last-name"><fmt:message key="label.common.password"/><span class="required">*</span>
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
					for="last-name"><fmt:message key="label.common.passwordCfm"/><span class="required">*</span>
				</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<label class="control-label"
						for="last-name">
						<input type="password" name="passwordCfm" id="passwordCfm" style="width:160px;" maxlength="16"/>
					</label>
				</div>
				<input type="button" id="savePwd" value='<fmt:message key="label.common.save"/>'/>
				<input type="button" id="cancelPwd" value='<fmt:message key="label.common.cancel"/>'/>
			</div>
		
		<%-- <table>
			<tr>
				<td scope="row"><label for="pw_new"><fmt:message key="label.common.password"/><font color="#FF0000">*</font></label></td>
				<td>
					<input type="password" name="password" id="password" style="width:160px;" maxlength="16"/>
					&nbsp;<label class="error" for="password" id="passwordError" generated="true" style="display:none;"></label>
				</td>
			</tr>
			<tr class="line-dot"><td colspan="2"/></tr>
			<tr>
				<td scope="row"><label for="pw_check"><fmt:message key="label.common.passwordCfm"/><font color="#FF0000">*</font></label></td>
				<td><input type="password" name="passwordCfm" id="passwordCfm" style="width:160px;" maxlength="16"/></td>
			</tr>
			<tr class="line-bottom"><td colspan="2"/></tr>
		</table> --%>
		
			<br/>
		</div>
		
		<div class="form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12"
				for="last-name">사용자 그룹<span class="required">*</span>
			</label>
			<div class="col-md-6 col-sm-6 col-xs-12">
				<label class="control-label"
					for="last-name">
					<input type="hidden" name="adminGroup.id" id="adminGroup" value="${admin.adminGroup.id}"/>${admin.adminGroup.name}
					<input type="hidden" name="adminGroup.name" id="adminGroupName" value="${admin.adminGroup.name}"/>
				</label>
			</div>
		</div>
		<div class="form-group">
			<label for="middle-name"
				class="control-label col-md-3 col-sm-3 col-xs-12">연락처</label>
			<div class="col-md-6 col-sm-6 col-xs-12">
				<input id="tel" class="form-control col-md-7 col-xs-12"
					required="required"type="text" name="tel" value="${admin.tel}">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12"
				for="last-name">휴대전화<span class="required">*</span></label>
			<div class="col-md-6 col-sm-6 col-xs-12">
				<input type="text" id="mobile" name="mobile" value="${admin.mobile}"
					required="required" class="form-control col-md-7 col-xs-12">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12"
				for="last-name">이메일<span class="required">*</span></label>
			<div class="col-md-6 col-sm-6 col-xs-12">
				<input type="text" id="email" name="email" value="${admin.email}"
					required="required" class="form-control col-md-7 col-xs-12">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12"
				for="last-name">회사 주소</label>
			<div class="col-md-6 col-sm-6 col-xs-12">
				<input type="text" id="area" name="area" value="${admin.area}"
					class="form-control col-md-7 col-xs-12">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12"
				for="last-name">등록일<span class="required">*</span>
			</label>
			<div class="col-md-6 col-sm-6 col-xs-12">
				<label class="control-label"
					for="last-name"><fmt:formatDate value="${admin.fstRgDt}" pattern="yyyy-MM-dd HH:mm"/>
				</label>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12"
				for="last-name">계정 상태</label>
			<div class="col-md-6 col-sm-6 col-xs-12">
			<label class="control-label" for="last-name">
             <input type="radio" class="flat" id="validY" name="validYn" ${admin.validYn == 'Y' ? 'checked' : ''} value="Y"/>사용중
             <input type="radio" class="flat" id="validN" name="validYn" ${admin.validYn == 'N' ? 'checked' : ''} value="N"/>사용중지
             
			</label>
			</div>
		</div>
	
		
		<div class="ln_solid"></div>
		<div class="form-group">
			<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
				<button type="button" class="btn btn-success" id="save">저장</button>
				<button type="button" class="btn btn-danger" onclick="javascript:page_move('/admin/operator/search.htm')">취소</button>
			</div>
		</div>
		
		
	
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>