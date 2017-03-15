<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobilepark.doit5.client.model.ApplicationList" %>
<%@ page import="java.util.HashMap" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	// 초기화
	$(function () {
		searchChange();
	});
	
	// 검색
	function search() {
		$("#vForm").submit();
	}
	
	// 등록
	function insert() {
		document.location = "/client/appList/create.htm";
	}
	
	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/client/appList/delete.json', function() { 
			search();
		});
	}
	
	// 검색 조건 변경
	function searchChange() {
	}
</script>
<form method="get" id="vForm" name="vForm" action="/client/appList/search.htm">
<div class="wrap00">
	<!-- search _ start -->
	<c:set var="searchOptionKeys">os,appId,appVer</c:set>
	<c:set var="searchOptionValues">
		<fmt:message key="label.applicationList.os"/>
		,<fmt:message key="label.applicationList.appId"/>
		,<fmt:message key="label.applicationList.appVer"/>
	</c:set>
	<%@ include file="/include/common/searchSelectForm.jsp" %>
	<!-- search _ end -->
	
	<!-- list _ start -->
	<display:table name="applicationLists" id="applicationList" class="simple" style="margin:5px 0pt;" requestURI="/client/appList/search.htm" pagesize="10" export="false">
<%
	HashMap<String, String> appNameMap = (HashMap<String, String>)request.getAttribute("appNameMap");
%>
	<c:if test="${authority.delete}">
		<display:column titleKey="label.common.select" style="width:40px;" media="html">
			<input type="checkbox" name="selected" style="width:15px;" value="${applicationList.id}"/>
		</display:column>
	</c:if>
		<display:column titleKey="label.common.id" property="id"/>
		<display:column titleKey="label.applicationList.os" property="os"/>
		<display:column titleKey="label.application.appName" media="html">
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
		</display:column>
		<display:column titleKey="label.applicationList.appId" property="appId"/>
		<display:column titleKey="label.applicationList.appVer" property="appVer"/>
		<display:column titleKey="label.common.detail" style="text-align:center; width:140px;" media="html">
			<input type="button" value='<fmt:message key="label.common.detail"/>' onclick="location.href='/client/appList/detail.htm?id=${applicationList.id}'"/>
		<c:if test="${authority.update}">
			<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/client/appList/update.htm?id=${applicationList.id}'"/>
		</c:if>
		</display:column>
	</display:table>
	<!-- list _ end -->
	
	<div class="line-clear"></div>
	
	<!-- button _ start -->
	<div class="footer">
		<table style="width:100%">
			<tr>
				<td height="30" align="right">
				<c:if test="${authority.create}">
					<input type="button" value='<fmt:message key="label.common.add"/>' onclick="javascript:insert()"/>
				</c:if>
				<c:if test="${authority.delete}">
					<input type="button" value='<fmt:message key="label.common.delete"/>' onclick="javascript:confirmAndDelete()"/>
				</c:if>
				</td>
			</tr>
		</table>
	</div>
	<!-- button _ end -->
	
<script type="text/javascript">
	$('#applicationList tr').each(function() {
		$(this).find("td").each(function() {
			if ($(this).html().trim() == '') { $(this).html('NONE'); }
		});
	});
</script>
</div>
</form>
<%@ include file="/include/footer.jspf" %>