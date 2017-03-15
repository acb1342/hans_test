<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	// 기능 수정
	function update() {
		location.href = "/admin/menu/function/update.htm?id=${cmsMenuFunction.id}";
	}

	// 기능 삭제
	function confirmAndDelete() {
		deleteById('/admin/menu/function/delete.json', '${cmsMenuFunction.id}', function() { 
			search(); 
		});
	}

	// 이전 페이지로 이동
	function search() {
		location.href = "/admin/menu/detail.htm?id=${cmsMenuFunction.menu.id}";
	}
</script>
<div class="wrap02">
	<!-- list _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- function ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsMenu.function.id"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${cmsMenuFunction.id}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- function명 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsMenu.function.name"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${cmsMenuFunction.name}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- function URL -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsMenu.function.url"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${cmsMenuFunction.url}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- function 타입 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsMenu.function.type"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${cmsMenuFunction.auth}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- function 설명 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.description"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${cmsMenuFunction.description}</td>
		</tr>
		<tr class="line-bottom"><td colspan="3"/></tr>
	</table>
	<!-- list _ start -->

	<div class="line-clear"></div>
	
	<!-- button _ start -->
	<div class="footer">	
		<table style="width:100%">
			<tr>
				<td width="50%" height="30">
					<input type="button" value='<fmt:message key="label.common.list"/>' onclick="javascript:search()"/>
				</td>
				<td width="50%" align="right">
				<c:if test="${authority.update}">
					<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="javascript:update()"/>
				</c:if>
				<c:if test="${authority.delete}">
					<input type="button" value='<fmt:message key="label.common.delete"/>' onclick="javascript:confirmAndDelete()"/>
				</c:if>					
				</td>
			</tr>
		</table>
	</div>
	<!-- button _ end -->
</div>
<%@ include file="/include/footer.jspf" %>