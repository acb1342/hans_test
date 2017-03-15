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
				type:"required"
			},
			messages:{
				type:{
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
<spring:hasBindErrors name="pushButton"/>
<form method="post" id="vForm" name="vForm" action="/push/button/update.htm">
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
				${pushButton.id}
				<input type="hidden" name="id" value="${pushButton.id}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Position -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushButton.position"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<select id="position" name="position">
					<c:if test="${pushButton.position == 'left'}">
						<option value="left" selected>left</option>
						<option value="right">right</option>
						<option value="center">center</option>
					</c:if>
					<c:if test="${pushButton.position == 'right'}">
						<option value="left">left</option>
						<option value="right" selected>right</option>
					</c:if>
				</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Title -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushButton.title"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="title" name="title" value="${pushButton.title}" style="width:99%;">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Type -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushButton.type"/>
			</td>
			<td colspan="2" class="td02">
				<select id="type" name="type">
					<c:if test="${pushButton.type == 'URL'}">
						<option value="URL" selected>URL</option>
						<option value="INTENT">INTENT</option>
						<option value="API">API</option>
						<option value="NONE">NONE</option>
					</c:if>
					<c:if test="${pushButton.type == 'INTENT'}">
						<option value="URL">URL</option>
						<option value="INTENT" selected>INTENT</option>
						<option value="API">API</option>
						<option value="NONE">NONE</option>
					</c:if>
					<c:if test="${pushButton.type == 'API'}">
						<option value="URL">URL</option>
						<option value="INTENT">INTENT</option>
						<option value="API" selected>API</option>
						<option value="NONE">NONE</option>
					</c:if>
					<c:if test="${pushButton.type == 'NONE'}">
						<option value="URL">URL</option>
						<option value="INTENT">INTENT</option>
						<option value="API">API</option>
						<option value="NONE" selected>NONE</option>
					</c:if>
				</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- ACtion -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushButton.action"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="action" name="action" value="${pushButton.action}" style="width:99%;">
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