<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 저장
		$('#save').click(function(e) {
			e.preventDefault();
			$("#vForm").submit();
		});
	});

	// 이전 페이지
	function cancel() {
		history.back();
	}
</script>
<spring:hasBindErrors name="agent"/>
<form method="post" id="vForm" name="vForm" action="/client/agent/update.htm">
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- ID -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.common.id"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="id" readOnly="readonly" value="${agent.id}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- ContentProvider -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.provider.cpId"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="contentProviderId" readOnly="readonly" value="${agent.contentProvider.cpName}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Agent ID -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.agent.agentId"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="agentId" name="agentId" value="${agent.agentId}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Agent Type -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.agent.agentType"/>
			</td>
			<td colspan="2" class="td02">
				<select name="agentType">
					<c:if test="${agent.agentType == '00'}">
						<option value="00" selected>DBAgent</option>
						<option value="01">HTTP</option>
					</c:if>
					<c:if test="${agent.agentType == '01'}">
						<option value="00">DBAgent</option>
						<option value="01" selected>HTTP</option>
					</c:if>
				</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Used YN -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.agent.usedYn"/>
			</td>
			<td colspan="2" class="td02">
				<select name="usedYn">
				<c:forEach items="${usedYnList}" var="usedYnEnum">
					<option value="${usedYnEnum}" ${agent.usedYn == usedYnEnum ? "selected='selected'":""}>
						${usedYnEnum}
					</option>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- IP Check YN -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.agent.ipCheckYn"/>
			</td>
			<td colspan="2" class="td02">
				<select name="ipCheckYn">
				<c:forEach items="${ipCheckYnList}" var="ipCheckYnEnum">
					<option value="${ipCheckYnEnum}" ${agent.ipCheckYn == ipCheckYnEnum ? "selected='selected'":""}>
						${ipCheckYnEnum}
					</option>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Status Check YN -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.agent.statusCheckYn"/>
			</td>
			<td colspan="2" class="td02">
				<select name="statusCheckYn">
				<c:forEach items="${statusCheckYnList}" var="statusCheckYnEnum">
					<option value="${statusCheckYnEnum}" ${agent.statusCheckYn == statusCheckYnEnum ? "selected='selected'":""}>
						${statusCheckYnEnum}
					</option>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Noti SMS YN -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.agent.notiSmsYn"/>
			</td>
			<td colspan="2" class="td02">
				<select name="notiSmsYn">
				<c:forEach items="${notiSmsYnList}" var="notiSmsYnEnum">
					<option value="${notiSmsYnEnum}" ${agent.notiSmsYn == notiSmsYnEnum ? "selected='selected'":""}>
						${notiSmsYnEnum}
					</option>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Noti SMS Mdn -->
		<tr>
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.agent.notiSmsMdn"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="notiSmsMdn" name="notiSmsMdn" value="${agent.notiSmsMdn}">
			</td>
		</tr>
		<tr class="line-bottom"><td colspan="3"/></tr>
	</table>
	<!-- input _ end -->

	<div class="line-clear"></div>
	
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