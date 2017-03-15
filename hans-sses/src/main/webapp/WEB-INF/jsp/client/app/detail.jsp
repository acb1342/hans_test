<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	// 수정
	function update() {
		document.location = "/client/app/update.htm?id=${application.id}";
	}

	// 삭제
	function confirmAndDelete() {
		checkUesd();
		deleteById('/client/app/delete.json', '${application.id}', function() { 
			search(); 
		});
	}

	function checkUesd() {
		var data;
		var id = ${application.id};

		data = {appid:id};
		
		$.ajax({
			url:'/client/app/checkUsed.json',
			type:"POST",
			data:data,		    
			success:function(result) {
				if (!result.success) {				
					if (result.errors.reason == 'isUsed') {
						jAlert('<fmt:message key="statement.error.delete.applistIsUesd"/>', 'alert', function() {});
					} else if (result.errors.reason == 'notExist') {						
						jAlert('<fmt:message key="statement.error.delete.notExist"/>', 'alert', function() {});
					}
				}
			}
		});
	}
	
	// 검색 페이지
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			document.location = "/client/app/search.htm?" + callbackUrl;
		} else {
			document.location = "/client/app/search.htm";
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
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.id"/>
			</td>
			<td colspan="2" class="td02">${application.id}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- CP ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.provider.cpId"/>
			</td>
			<td colspan="2" class="td02">${application.cpId}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- CP명 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.provider.cpName"/>
			</td>
			<td colspan="2" class="td02">${application.contentProvider.cpName}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- App Name -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.application.appName"/>
			</td>
			<td colspan="2" class="td02">${application.appName}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- PKG ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.application.pkgId"/>
			</td>
			<td colspan="2" class="td02">${application.pkgId}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Agent ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.application.agentId"/>
			</td>
			<td colspan="2" class="td02">${application.agentId}</td>
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