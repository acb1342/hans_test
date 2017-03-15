<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroup" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	var passwordUpdated = false;
	
	$(function() {
		// 그룹에 따라 수행 액션에 제한을 설정(버튼 블럭)
		afterUserGroupCheck();

		// 비밀번호 변경 창 open
		$("#passwordUpdate").click(function(e) {
			e.preventDefault();
			$("#changePassword").dialog("open");
		});
		$("#changePassword").dialog({
			autoOpen:false,
			modal:true
		});
		
		// 비밀번호 변경 창 닫기
		$("#cancelPwd").click(function(e) {
			$("#password").val('');
			$("#passwordCfm").val('');
			$("#changePassword").dialog("close");
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
					url:'/admin/user/changePassword.json',
					data:{
						id:$("#id").val(),
						password:$("#password").val()					
					},
					success:function (data) {
						jAlert('<fmt:message key="statement.updatePassword.success"/>');
						$("#password").val('');
						$("#passwordCfm").val('');
						$("#changePassword").dialog("close");
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
			if ($("#cmdGroup option:selected").text() == '<%=AdminGroup.CP_GROUP_NAME%>' && $("#relatedMCPs").find('tr').size() == 0) {
				jAlert("CP must contains more than one MCP or MCP&CP");
				return false;
			}
			$("#vForm").submit();
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
<form method="post" id="vForm" name="vForm" action="/admin/user/update.htm">
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- 사용자 ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsUser.id"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
	 			<input type="text" id="id" name="id" value="${admin.id}" readonly="readonly"/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 사용자 패스워드 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.password"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="button" id="passwordUpdate" value='<fmt:message key="label.common.changePwd"/>'/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 그룹 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsGroup"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
			<input type="hidden" name="adminGroup.id" id="adminGroup" value="${admin.adminGroup.id}"/>${admin.adminGroup.name}
			<input type="hidden" name="adminGroup.name" id="adminGroupName" value="${admin.adminGroup.name}"/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 사용자명 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsUser.name"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="name" value="${admin.name}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 집전화번호 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.homePhone"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="tel" value="${admin.tel}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 핸드폰번호 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.mobilePhone"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="mobile" value="${admin.mobile}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 이메일 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.email"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="email" value="${admin.email}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 주소 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.address"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="area" value="${admin.area}" style="width:400px;">
			</td>
		</tr>
		<tr class="line-bottom"><td colspan="3"/></tr>
	</table>
	<!-- input _ end -->

	<!-- password -->
	<div id="changePassword" title='<fmt:message key="label.common.changePwd"/>'>
		<table>
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
		</table>
		<br/>
		<div>
			<input type="button" id="savePwd" value='<fmt:message key="label.common.save"/>'/>
			<input type="button" id="cancelPwd" value='<fmt:message key="label.common.cancel"/>'/>
		</div>
	</div>

	<div class="line-clear"></div>

	<!-- button _ start -->
	<div class="footer">
		<table style="width:100%">
			<tr>
				<td width="50%" height="30"></td>
				<td width="50%" align="right">	
				<c:if test="${authority.update}">
					<input type="button" id="save" value='<fmt:message key="label.common.save"/>'/>
				</c:if>
				<c:if test="${authority.delete}">
					<input type="button" value='<fmt:message key="label.common.cancel"/>' onclick="javascript:cancel()"/>
				</c:if>	
				</td>
			</tr>
		</table>
	</div>				
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>