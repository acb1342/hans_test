<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	// 수정
	function update() {
		document.location = "/push/button/update.htm?id=${pushButton.id}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/push/button/delete.json', '${pushButton.id}', function() { 
			search(); 
		});
	}

	// 검색 페이지
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			document.location = "/push/button/search.htm?" + callbackUrl;
		} else {
			document.location = "/push/button/search.htm";
		}
	}
</script>
<spring:hasBindErrors name="pushButton" />
<div class="wrap00">
	<!-- list _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.id"/>
			</td>
			<td colspan="2" class="td02">${pushButton.id}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Pactionition -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushButton.position"/>
			</td>
			<td colspan="2" class="td02">${pushButton.position}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Title -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushButton.title"/>
			</td>
			<td colspan="2" class="td02">${pushButton.title}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Type -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushButton.type"/>
			</td>
			<td colspan="2" class="td02">${pushButton.type}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Action -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushButton.action"/>
			</td>
			<td colspan="2" class="td02">${pushButton.action}</td>
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