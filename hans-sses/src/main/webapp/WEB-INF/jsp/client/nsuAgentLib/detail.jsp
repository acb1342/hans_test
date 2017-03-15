<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.uangel.platform.util.Env" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	function update() {
		location.href = "/client/nsuAgentLib/update.htm?id=${nsuAgentLib.id}";
	}

	function confirmAndDelete() {
		jConfirm('<fmt:message key="statement.confirm.delete"/>', 'Confirm', function(r) {
			if (r) {
				$.ajax({
					url:'/client/nsuAgentLib/delete.json',
					type:"POST",
					data:{
						id:'${nsuAgentLib.id}'
					},
					dataType:'json',
					success:function(isDelete) {
						if (isDelete) {
							jAlert('<fmt:message key="statement.delete.success"/>','Alert', function() {
								search();
							});
						} else {
							jAlert('<fmt:message key="statement.delete.fail"/>');
						}
					}
				});
			}
		});
	}

	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			location.href = "/client/nsuAgentLib/search.htm?" + callbackUrl;
		} else {
			location.href = "/client/nsuAgentLib/search.htm";
		}
	}	
</script>
<div class="wrap00">
	<!-- list _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.id"/>
			</td>
			<td colspan="2" class="td02">${nsuAgentLib.id}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.nsuAgentLib.version"/>
			</td>
			<td colspan="2" class="td02">${nsuAgentLib.version}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.nsuAgentLib.os"/>
			</td>
			<td colspan="2" class="td02">${nsuAgentLib.os}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.nsuAgentLib.langCode"/>
			</td>
			<td colspan="2" class="td02">${nsuAgentLib.langCode}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.nsuAgentLib.ynMandatory"/>
			</td>
			<td colspan="2" class="td02">${nsuAgentLib.ynMandatory}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.nsuAgentLib.agentFile"/>
			</td>
			<td colspan="2" class="td02">${nsuAgentLib.filePath}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.nsuAgentLib.applyStartYmd"/>
			</td>
			<td colspan="2" class="td02">${nsuAgentLib.applyStartYmd}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.status"/>
			</td>
			<td colspan="2" class="td02">${nsuAgentLib.status}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.createDate"/>
			</td>
			<td colspan="2" class="td02">${nsuAgentLib.createDate}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.modifyDate"/>
			</td>
			<td colspan="2" class="td02">${nsuAgentLib.modifyDate}</td>
		</tr>
		<tr class="line-bottom"><td colspan="3"/></tr>
	</table>

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

				<!-- 
				<c:if test="${authority.update}">
					<td class="btn02">
						<a class="link01" href="javascript:update()"><fmt:message key="label.common.modify"/></a>
					</td>
				</c:if>
				<c:if test="${authority.delete}">
					<td class="btn02">
						<a class="link01" href="javascript:confirmAndDelete()"><fmt:message key="label.common.delete"/></a>
					</td>
				</c:if>
				-->
				<!-- td class="btn02">
					<a class="link01" href="javascript:search()"><fmt:message key="label.common.list"/></a>
				</td -->
			</tr>
		</table>
	</div>
</div>
<%@ include file="/include/footer.jspf" %>