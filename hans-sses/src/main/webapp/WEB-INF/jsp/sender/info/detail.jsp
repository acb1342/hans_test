<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	// 수정
	function update() {
		document.location = "/sender/info/update.htm?id=${senderInfo.id}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/sender/info/delete.json', '${senderInfo.id}', function() { 
			search(); 
		});
	}

	// 검색 페이지
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			document.location = "/sender/info/search.htm?" + callbackUrl;
		} else {
			document.location = "/sender/info/search.htm";
		}
	}
</script>
<div class="wrap00">
	<!-- list _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<!-- ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.senderInfo.hostName"/>
			</td>
			<td colspan="6" class="td02">${senderInfo.id}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- Sender Name / Index -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.senderInfo.senderName"/>
			</td>
			<td colspan="3" class="td02">${senderInfo.senderName}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.senderInfo.senderIndex"/>
			</td>
			<td width="290px" class="td02">${senderInfo.senderIndex}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- Sender Tid / ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.senderInfo.senderTid"/>
			</td>
			<td colspan="3" class="td02">${senderInfo.senderTid}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.senderInfo.senderId"/>
			</td>
			<td width="290px" class="td02">${senderInfo.senderId}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- APP ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.senderInfo.appId"/>
			</td>
			<td colspan="6" class="td02">${senderInfo.appId}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- IP / Port -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.senderInfo.ip"/>
			</td>
			<td colspan="3" class="td02">${senderInfo.ip}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.senderInfo.port"/>
			</td>
			<td width="290px" class="td02">${senderInfo.port}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- URL -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.senderInfo.url"/>
			</td>
			<td colspan="6" class="td02">${senderInfo.url}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- TPS -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.senderInfo.tps"/>
			</td>
			<td colspan="6" class="td02">${senderInfo.tps}</td>
		</tr>
		<tr class="line-bottom"><td colspan="6"/></tr>		
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