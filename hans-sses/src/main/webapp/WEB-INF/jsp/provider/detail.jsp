<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	// 수정
	function update() {
		document.location = "/provider/update.htm?id=${contentProvider.id}";
	}

	// 삭제
	function confirmAndDelete() {
		checkUesd();
		deleteById('/provider/delete.json', '${contentProvider.id}', function() { 
			search(); 
		});
	}

	function checkUesd() {
		var data;
		var id = ${contentProvider.id};

		data = {cpid:id};
		
		$.ajax({
			url:'/provider/checkUsed.json',
			type:"POST",
			data:data,		    
			success:function(result) {
				if (!result.success) {				
					if (result.errors.reason == 'isUsed') {
						jAlert('<fmt:message key="statement.error.delete.appIsUesd"/>', 'alert', function() {});
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
			document.location = "/provider/search.htm?" + callbackUrl;
		} else {
			document.location = "/provider/search.htm";
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
			<td colspan="2" class="td02">${contentProvider.id}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- CP ID-->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.provider.cpId"/>
			</td>
			<td colspan="2" class="td02">${contentProvider.cpId}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- CP명 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.provider.cpName"/>
			</td>
			<td colspan="2" class="td02">${contentProvider.cpName}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- CP Phone-->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.provider.phone"/>
			</td>
			<td colspan="2" class="td02">${contentProvider.phone}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- CP Email -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.provider.email"/>
			</td>
			<td colspan="2" class="td02">${contentProvider.email}</td>
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