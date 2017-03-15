<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 저장
		$('#save').click(function(e) {
			e.preventDefault();
			$("#vForm").submit();
		});
		
		// 유효성 검사
		$("#vForm").validate({
			onfocusout:false,
			onkeyup:false,
			rules:{
				contentProviderId:"required",
				cpId:"required",
				appName:"required",
				pkgId:"required"
			},
			messages:{
				contentProviderId:{
					required:'<fmt:message key="validate.required"/>'
				},
				cpId:{
					required:'<fmt:message key="validate.required"/>'
				},
				appName:{
					required:'<fmt:message key="validate.required"/>'
				},
				pkgId:{
					required:'<fmt:message key="validate.required"/>'
				}
			}
		});
	});

	// 이전 페이지
	function cancel() {
		history.back();
	}
</script>
<spring:hasBindErrors name="application"/>
<form method="post" id="vForm" name="vForm" action="/client/app/update.htm">
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.id"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="id" readOnly="readonly" value="${application.id}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- ContentProvider -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.provider.cpId"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="contentProviderId" readOnly="readonly" value="${application.contentProvider.cpName}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- App Name -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.application.appName"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="appName" name="appName" value="${application.appName}" style="width:99%;" maxlength="256">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- PKG ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.application.pkgId"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="pkgId" name="pkgId" value="${application.pkgId}" style="width:99%;" maxlength="256">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>		
		<!-- Agent ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.application.agentId"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="agentId" name="agentId" value="${application.agentId}" style="width:99%;" maxlength="256">
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