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
				name:"required",
				url:"required"
			},
			messages:{
				name:{
					required:'<fmt:message key="validate.required"/>'
				},
				url:{
					required:'<fmt:message key="validate.required"/>'
				}
			}
		});
	});

	// 이전 페이지로 이동
	function cancel() {
		window.location = "/admin/menu/detail.htm?id=" + ${cmsMenuFunction.menuId};
	}
</script>
<spring:hasBindErrors name="cmsMenuFunction"/>
<form method="post" id="vForm" name="vForm" action="/admin/menu/function/create.htm">
<input type="hidden" name="menuId" value="${cmsMenuFunction.menuId}"/>
<div class="wrap02">
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- function명 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsMenu.function.name"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="name" value="${cmsMenuFunction.name}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- function URL -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsMenu.function.url"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="url" value="${cmsMenuFunction.url}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- function 타입 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsMenu.function.type"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<select name="auth">
				<c:forEach items="${types}" var="type">
					<option value="${type}">${type}</option>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- function 설명 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.description"/>
			</td>
			<td colspan="2" class="td02">
				<textarea style="width:300px;" rows="8" name="description">${cmsMenuFunction.description}</textarea>
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