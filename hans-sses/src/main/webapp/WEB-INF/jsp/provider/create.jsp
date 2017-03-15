<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 저장
		$('#save').click(function(e) {
			e.preventDefault();
			$("#vForm").submit();
		});

		// 유효성 체크
		$.validator.addMethod("passwordConfirm", function(value, element) {
			return $("#cpPasswd").val() == $("#cpPasswdCfm").val();
		},"비밀번호가 일치하지 않습니다.");
		
		// 유효성 검사
		$("#vForm").validate({
			onfocusout:false,
			onkeyup:false,
			rules:{
				cpId: {
					required:true,
					remote:{
						type:"POST",
						url:"checkCpId.json"
					}
				},
				cpPasswd:{
					required:true,
					passwordConfirm:true
				},
				cpPasswdCfm:{
					required:true,
					passwordConfirm:true
				},
				cpName:"required",
				phone:"required number"
			},
			messages:{
				cpId:{
					required:'<fmt:message key="validate.required"/>',
					remote:'<fmt:message key="validate.duplicated.id"/>'
				},
				cpPasswd:{
					required:'<fmt:message key="validate.required"/>'
				},
				cpName:{
					required:'<fmt:message key="validate.required"/>'
				},
				phone:{
					required:'<fmt:message key="validate.required"/>',
					number:'<fmt:message key="validate.number"/>'
				}
			}
		});
	});

	// 검색 페이지
	function cancel() {
		document.location = "/provider/search.htm";
	}
</script>
<spring:hasBindErrors name="contentProvider"/>
<form method="post" id="vForm" name="vForm" action="/provider/create.htm">
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- CP ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<!-- img src="/images/default/icon_list.gif" width="7" height="7" -->
				<fmt:message key="label.provider.cpId"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="cpId" name="cpId" value="${contentProvider.cpId}" maxlength="32">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- CP 패스워드 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.password"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="password" id="cpPasswd" name="cpPasswd" maxlength="32" >
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- CP 패스워드 확인-->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.passwordCfm"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="password" id="cpPasswdCfm" name="cpPasswdCfm" maxlength="32">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- CP Name -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.provider.cpName"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="cpName" name="cpName" value="${contentProvider.cpName}" maxlength="256">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- CP Phone -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.provider.phone"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="phone" name="phone" value="${contentProvider.phone}" maxlength="32">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- CP Email -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.provider.email"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="email" name="email" value="${contentProvider.email}" maxlength="128">
			</td>
		</tr>		
		<tr class="line-bottom"><td colspan="3"/></tr>
	</table>
	<!-- input _ end -->

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