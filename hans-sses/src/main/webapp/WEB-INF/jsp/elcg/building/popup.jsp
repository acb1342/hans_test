<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/common.css" media="all">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/popup.css" media="all">
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		$('#bdGroupName').focus();
	});
	
	function search() {
		$('#vForm').submit();
	}
	
	function sendChildValue(paramName, id) {
		opener.setChildValue(paramName, id);
		window.close();
	}
	
	
</script>

<form method="get" id="vForm" name="vForm" action="/elcg/building/popup.htm" >

	<div class="wrap_winpop">
		
		<div class="header_area">
			<h1 class="tit">건물명 검색</h1>
		</div>
		
		<fieldset class="searchBox">
		<table class="searchTbl" style="margin:0px 0px 0px 20px" >
			<tr>
				<td>
					<c:set var="bdGroupName" value='<%=request.getParameter("bdGroupName")%>' />
					<input type="text" id="bdGroupName" name="bdGroupName" value="${bdGroupName}"/>
					<input type="button" id="search_list" value='<fmt:message key="label.common.search"/>' class="btn_search" onclick="search()" />
				</td>
			</tr>
		</table>
		</fieldset>
		
		<div class="content_area">
		<display:table name="bdGroupList" id="bdGroup" class="simple" style="margin:5px 0pt;" requestURI="/elcg/controller/popup.htm" >
			<display:column titleKey="label.elcg.buildingName" media="html" >
				<a href='javascript:sendChildValue("${bdGroup.name}","${bdGroup.bdGroupId}")'>${bdGroup.name}</a>
			</display:column>
			<display:column titleKey="label.elcg.addr" property="addr" />
		</display:table>
		</div>
		
	</div>

</form>