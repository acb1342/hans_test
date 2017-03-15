<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroup" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		$("#createMember").button().click(createMember);
	});

	// 수정 페이지로 이동
	function update() {
		window.location.href = "/admin/user/update.htm?id=${admin.id}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/admin/user/delete.json', '${admin.id}', function() { 
			search();
		});
	}

	// 검색 페이지로 이동
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			location.href = "/admin/user/search.htm?" + callbackUrl;
		} else {
			location.href = "/admin/user/search.htm";
		}
	}
	
	// 생성 페이지로 이동
	function createMember() {
		document.location = "/admin/user/member/create.htm?defaultUserId=${admin.id}";
	}
</script>
<div class="wrap00">
	<!-- list _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- 사용자 ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsUser.id"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${admin.id}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 사용자 이름 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsUser.name"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${admin.name}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 그룹 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsGroup"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${admin.adminGroup.name}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 전화번호 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.officePhone"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${admin.tel}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 핸드폰번호 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.mobilePhone"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${admin.mobile}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 이메일 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.email"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${admin.email}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 주소 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.address"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${admin.area}</td>
		</tr>
		<tr class="line-bottom"><td colspan="3"/></tr>
	</table>
	<!-- list _ end -->

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