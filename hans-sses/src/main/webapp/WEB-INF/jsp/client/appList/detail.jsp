<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobilepark.doit5.client.model.ApplicationList" %>
<%@ page import="java.util.HashMap" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		applyVisibility();
	});

	// 수정
	function update() {
		document.location = "/client/appList/update.htm?id=${applicationList.id}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/client/appList/delete.json', '${applicationList.id}', function() { 
			search(); 
		});
	}

	// 검색 페이지
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			document.location = "/client/appList/search.htm?" + callbackUrl;
		} else {
			document.location = "/client/appList/search.htm";
		}
	}

	function applyVisibility() {
		var os = '${applicationList.os}';
		if( os == 'android') {
			$( ".hidableTr").css( "display", "none" );
			$( ".ioshidableTr").css( "display", "table-row" );
		} else {
			$( ".ioshidableTr").css( "display", "none" );
			$( ".hidableTr").css( "display", "table-row" );
		}
	}
</script>
<div class="wrap00">
<%
	HashMap<String, String> appNameMap = (HashMap<String, String>)request.getAttribute("appNameMap");
%>
	<!-- list _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.id"/>
			</td>
			<td colspan="2" class="td02">${applicationList.id}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- APP명 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.application.appName"/>
			</td>
			<td colspan="2" class="td02">
				<c:if test="${appNameMap != null}">
				<c:set var="appNameMap" value="${appNameMap}" scope="request"/>
				<c:set var="applicationList" value="${applicationList}" scope="request"/>
				<%
					HashMap<String, String> appName = (HashMap<String, String>)request.getAttribute("appNameMap");
					ApplicationList applist = (ApplicationList)request.getAttribute("applicationList");
					if (applist != null) {
						if (appName.get(applist.getAppId()) != null) {
							out.println(appName.get(applist.getAppId()));
						} else {
							out.println("-");
						}
					} else {
						out.println("-");
					}
				%>
				</c:if>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- OS -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.os"/>
			</td>
			<td colspan="2" class="td02">${applicationList.os}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- APP ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.appId"/>
			</td>
			<td colspan="2" class="td02">${applicationList.appId}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- API Key -->
		<tr class="ioshidableTr">
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.appKey"/>
			</td>
			<td colspan="2" class="td02">${applicationList.appKey}</td>
		</tr>
		<tr class="line-dot ioshidableTr"><td colspan="3"/></tr>
		<!-- APP Version -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.appVer"/>
			</td>
			<td colspan="2" class="td02">${applicationList.appVer}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Cert Path -->
		<c:if test="${applicationList.os != 'android'}">
		<tr class="hidableTr">
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.certPath"/>
			</td>
			<td colspan="2" class="td02">${applicationList.certPath}</td>
		</tr>
		<tr class="line-dot hidableTr"><td colspan="3"/></tr>
		
		<!-- Key Path -->
		<tr class="hidableTr">
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.keyPath"/>
			</td>
			<td colspan="2" class="td02">${applicationList.keyPath}</td>
		</tr>
		<tr class="line-dot hidableTr"><td colspan="3"/></tr>
		</c:if>
		<!-- Route Seq -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.routeSeq"/>
			</td>
			<td colspan="2" class="td02"> ${routeRule == null ? "Unknown Route Rule":routeRule.description}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- User Phone -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.senderIndex"/>
			</td>
			<td colspan="2" class="td02">${applicationList.senderIndex}</td>
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