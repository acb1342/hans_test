<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.uangel.platform.util.Env" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	// 수정
	function update() {
		location.href = "/admin/securityKey/update.htm?id=${securityKey.id}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/admin/securityKey/delete.json', '${securityKey.id}', function() { 
			search();
		});
	}

	// 검색
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			location.href = "/admin/securityKey/search.htm?" + callbackUrl;
		} else {
			location.href = "/admin/securityKey/search.htm";
		}
	}	
</script>
<div class="wrap00">
	<!-- list _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- key -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.securityKey.key"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${securityKey.id}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- value -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.securityKey.value"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${securityKey.value}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 생성일 -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.common.createDate"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${securityKey.createDate}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 수정일 -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.common.modifyDate"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${securityKey.modifyDate}</td>
		</tr>
		<tr class="line-bottom"><td colspan="3"/></tr>
	</table>

	<!-- button _ start -->
	<table style="width:100%">
		<tr>
			<td width="50%" height="30">
			<c:if test="${authority.update}">
				<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="javascript:update()"/>
			</c:if>
			<c:if test="${authority.delete}">
				<input type="button" value='<fmt:message key="label.common.delete"/>' onclick="javascript:confirmAndDelete()"/>
			</c:if>
			</td>
			<td width="50%" align="right">
				<input type="button" value='<fmt:message key="label.common.list"/>' onclick="javascript:search()"/>
			</td>
		</tr>
	</table>
	<!-- button _ end -->
</div>
<%@ include file="/include/footer.jspf" %>