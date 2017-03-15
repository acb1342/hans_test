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
	function search() {
		$('#vForm').submit();
	}
	
	function sendChildValue(id, name, mobile) {
		opener.setChildValueInstaller(id, name, mobile);
		window.close();
	}
</script>

<form method="get" id="vForm" name="vForm" action="/elcg/applicationAndReport/installerInfoPopup.htm">

	<div class="wrap_winpop">
	
		<div class="header_area">
			<h1 class="tit">설치자명 검색</h1>
		</div>
		
		<fieldset class="searchBox">
		<table class="searchTbl" style="margin:0px 0px 0px 20px" >
			<tr>
				<td>
					<c:set var="installerName" value='<%=request.getParameter("installerName")%>' />
					<input type="text" id="installerName" name="installerName" class="inp_text" value="${installerName}"/>
					<Button id="search_list" class="btn_search" onclick="search()">검색</Button>
				</td>
			</tr>
		</table>
		</fieldset>
			
		<div class="content_area">
		<display:table name="list" id="list" class="simple" style="margin:5px 0pt; width:100%" requestURI="/elcg/applicationAndReport/installerInfoPopup.htm" >
			<display:column titleKey="label.common.select" style="width:25%">
				<input type="button" value="배정" onclick='javascript:sendChildValue("${list.id}" , "${list.name}" , "${list.mobile}")'/>
			</display:column>
			<display:column titleKey="label.elcg.installer" property="name" style="width:25%"/>
			<display:column titleKey="label.elcg.installerPhone" property="mobile" style="width:25%"/>
			<display:column titleKey="label.elcg.gun" style="width:25%">${list.allWkCnt}건 / ${list.noCompleWkCnt}건</display:column>
		</display:table>
		</div>
		
	</div>

</form>