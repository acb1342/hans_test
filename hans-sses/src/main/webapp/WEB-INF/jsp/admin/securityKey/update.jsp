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
			rules:{
				'id':'required',
				'value':'required'
			},
			messages:{
				'id':'<fmt:message key="validate.required"/>',
				'value':'<fmt:message key="validate.required"/>'
			}
		});
	});

	// 취소
	function cancel() {
		window.location = "/admin/securityKey/search.htm";
	}
</script>
<spring:hasBindErrors name="securityKey"/>
<form method="post" id="vForm" name="vForm" action="/admin/securityKey/update.htm">
<input type="hidden" id="id" name="id" value="${securityKey.id}"/>
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- key -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.securityKey.key"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">${securityKey.id}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- value -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.securityKey.value"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="value" id="value" value="${securityKey.value}" style="width:500px;">
			</td>
		</tr>
		<tr class="line-bottom"><td colspan="3"/></tr>
	</table>
	<!-- input _ end -->

	<!-- space _ start -->
	<table style="width:100%">
		<tr>
			<td height="3"></td>
		</tr>
	</table>
	<!-- space _ end -->

	<!-- button _ start -->
	<c:if test="${authority.update}">
		<input type="button" id="save" value='<fmt:message key="label.common.save"/>'/>
	</c:if>
	<c:if test="${authority.delete}">
		<input type="button" value='<fmt:message key="label.common.cancel"/>' onclick="javascript:cancel()"/>
	</c:if>
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>