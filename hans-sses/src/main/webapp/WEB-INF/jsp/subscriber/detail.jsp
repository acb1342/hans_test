<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	// 수정
	function update() {
		document.location = "/subscriber/update.htm?id=${endUser.id}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/subscriber/delete.json', '${endUser.id}', function() { 
			search(); 
		});
	}

	// 검색 페이지
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			document.location = "/subscriber/search.htm?" + callbackUrl;
		} else {
			document.location = "/subscriber/search.htm";
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
			<td colspan="2" class="td02">${endUser.id}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- APP ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.appId"/>
			</td>
			<td colspan="2" class="td02">${endUser.appId}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- APP Version -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.appVer"/>
			</td>
			<td colspan="2" class="td02">${endUser.appVer}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Push Token -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.pushToken"/>
			</td>
			<td colspan="2" class="td02">${endUser.pushToken}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- OS -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.os"/>
			</td>
			<td colspan="2" class="td02">${endUser.os}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- OS Version -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.osVer"/>
			</td>
			<td colspan="2" class="td02">${endUser.osVer}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Device ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.deviceId"/>
			</td>
			<td colspan="2" class="td02">${endUser.deviceId}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Device Brand -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.deviceBrand"/>
			</td>
			<td colspan="2" class="td02">${endUser.deviceBrand}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Device Model -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.deviceModel"/>
			</td>
			<td colspan="2" class="td02">${endUser.deviceModel}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Height -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.height"/>
			</td>
			<td colspan="2" class="td02">${endUser.height}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Width -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.width"/>
			</td>
			<td colspan="2" class="td02">${endUser.width}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Locale -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.locale"/>
			</td>
			<td colspan="2" class="td02">${endUser.locale}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Market -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.market"/>
			</td>
			<td colspan="2" class="td02">${endUser.market}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 휴대폰 번호 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.mdn"/>
			</td>
			<td colspan="2" class="td02">${endUser.mdn}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- User Phone -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.libVer"/>
			</td>
			<td colspan="2" class="td02">${endUser.libVer}</td>
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