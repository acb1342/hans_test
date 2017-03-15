<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	// 수정
	function update() {
		document.location = "/client/agent/update.htm?id=${agent.id}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/client/agent/delete.json', '${agent.id}', function() { 
			search(); 
		});
	}

	// 검색 페이지
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			document.location = "/client/agent/search.htm?" + callbackUrl;
		} else {
			document.location = "/client/agent/search.htm";
		}
	}
</script>
<div class="wrap00">
	<!-- list _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- ID -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.common.id"/>
			</td>
			<td colspan="2" class="td02">${agent.id}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- CP명 -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.provider.cpName"/>
			</td>
			<td colspan="2" class="td02">${agent.contentProvider.cpName}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Agent ID -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.agent.agentId"/>
			</td>
			<td colspan="2" class="td02">${agent.agentId}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Agent Type -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.agent.agentType"/>
			</td>
			<td colspan="2" class="td02">
				<c:if test="${agent.agentType == '00'}">DBAgent</c:if>
				<c:if test="${agent.agentType == '01'}">HTTP</c:if>			
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Used YN-->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.agent.usedYn"/>
			</td>
			<td colspan="2" class="td02">${agent.usedYn}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- IP Check YN -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.agent.ipCheckYn"/>
			</td>
			<td colspan="2" class="td02">${agent.ipCheckYn}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Status Check YN -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.agent.statusCheckYn"/>
			</td>
			<td colspan="2" class="td02">${agent.statusCheckYn}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Noti Sms YN -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.agent.notiSmsYn"/>
			</td>
			<td colspan="2" class="td02">${agent.notiSmsYn}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Noti Sms MDN -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.agent.notiSmsMdn"/>
			</td>
			<td colspan="2" class="td02">${agent.notiSmsMdn}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Reg Date -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.agent.regDate"/>
			</td>
			<td colspan="2" class="td02">${agent.regDate}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Update Date -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.agent.updateDate"/>
			</td>
			<td colspan="2" class="td02">${agent.updateDate}</td>
		</tr>
		<tr class="line-bottom"><td colspan="3"/></tr>		
	</table>
	<!-- list _ start -->
	
	<div class="line-clear"></div>
	
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