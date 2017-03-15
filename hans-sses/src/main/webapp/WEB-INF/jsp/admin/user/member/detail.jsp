<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	// 수정 페이지로 이동
	function update() {
		window.location.href = "/admin/user/member/update.htm?id=${member.id}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/admin/user/delete.json', '${member.id}', function() { 
			userDetail();
		});
	}

	// 상세 페이지로 이동
	function userDetail() {
		window.location.href = "/admin/user/detail.htm?id=${member.defaultUserId}";
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
			<td colspan="2" class="td02">&nbsp;${member.id}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 사용자 이름 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsUser.name"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${member.name}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 그룹 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsGroup"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${member.cmsGroup.name}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 집전화번호 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.homePhone"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${member.homePhone}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 핸드폰번호 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.mobilePhone"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${member.mobilePhone}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 사무실번호 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.officePhone"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${member.officePhone}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 이메일 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.email"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${member.email}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 주소 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.address"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${member.address}</td>
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
					<input type="button" value='<fmt:message key="label.common.list"/>' onclick="javascript:userDetail()"/>
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