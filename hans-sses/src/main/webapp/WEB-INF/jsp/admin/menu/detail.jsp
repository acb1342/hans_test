<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	// 기능 생성 페이지로 이동
	function createCmsMenuFunction() {
		document.location = "/admin/menu/function/create.htm?menuId=${cmsMenu.id}";
	}

	// 메뉴 업데이트 페이지로 이동
	function update() {
		location.href = "/admin/menu/update.htm?id=${cmsMenu.id}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/admin/menu/delete.json', '${cmsMenu.id}', function() {
			parent.leftFrame.reloadTree();
			location.href = "about:blank";			
		});
	}
</script>
<div class="wrap02">
	<!-- list _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- 메뉴 ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsMenu.id"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${cmsMenu.id}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 메뉴명 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsMenu.name"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${cmsMenu.title}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 메뉴 타입 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsMenu.type"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${cmsMenu.type}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 메뉴 URL -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsMenu.url"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${cmsMenu.url}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 메뉴 설명 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.description"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${cmsMenu.description}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 메뉴 순서 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsMenu.menuOrder"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${cmsMenu.sort}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 생성일 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.createDate"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${cmsMenu.fstRgDt}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 수정일 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.modifyDate"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${cmsMenu.lstChDt}</td>
		</tr>
		<tr class="line-bottom"><td colspan="3"/></tr>
		<!-- 메뉴 function 리스트 -->
		<tr>
			<td colspan="3" style="padding:0;">
				<display:table name="cmsMenu.functions" id="cmsMenuFunction" requestURI="/admin/menu/detail.htm" class="simple" export="false">
					<display:column titleKey="label.cmsMenu.function.name" property="name" maxLength="12"/>
					<display:column titleKey="label.cmsMenu.function.url" property="url" maxLength="50" style="text-align:left;"/>
					<display:column titleKey="label.cmsMenu.function.type" property="auth"/>
					<display:column titleKey="label.common.detail" style="text-align:center; width:140px;" media="html">
						<input type="button" style="width:65px !important;" value='<fmt:message key="label.common.detail"/>' onclick="location.href='/admin/menu/function/detail.htm?id=${cmsMenuFunction.id}'"/>
						<input type="button" style="width:65px !important;" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/admin/menu/function/update.htm?id=${cmsMenuFunction.id}'"/>
					</display:column>
				</display:table>
			</td>
		</tr>
		<tr>
			<td colspan="3" class="list-button-noti">* <fmt:message key="label.cmsMenu.function.ref"/></td>
		</tr>
		<tr class="line-bottom"><td colspan="3"/></tr>
	</table>
	<!-- list _ end -->

	<div class="line-clear"></div>
	
	<!-- button _ start -->
	<div class="footer">
		<table style="width:100%">
			<tr>
				<td width="50%" height="30"></td>
				<td width="50%" align="right">	
				<c:if test="${authority.update}">
					<input type="button" value='<fmt:message key="label.cmsMenu.function"/> <fmt:message key="label.common.add"/>' onclick="createCmsMenuFunction()"/>
				</c:if>					
				<c:if test="${authority.update}">
					<input type="button" style="margin-left:1px;" value='<fmt:message key="label.common.modify"/>' onclick="javascript:update()"/>
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