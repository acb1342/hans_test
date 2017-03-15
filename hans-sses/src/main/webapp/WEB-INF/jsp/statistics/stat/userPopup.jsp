<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(document).ready(function(){
		$('#searchValue').focus();
		$("#searchBtn").click(function() {
			$('#vForm').submit();
		});
	});
	
	function sendChildValue(pId, pName) {
		opener.setChildValue(pId, pName);
		window.close();
	}
</script>

<form method="get" id="vForm" name="vForm" action="/statistics/stat/userPopup.htm" >

	<div class="wrap_winpop">
		<div class="header_area">
			<h1 class="tit"><fmt:message key="label.customer.search"/></h1>
		</div>
	
		<fieldset class="searchBox">
		<table class="searchTbl" style="margin:0px 0px 0px 20px" >
			<tr>
				<td>
					<c:set var="searchValue" value='<%=request.getParameter("searchValue")%>' />
					<input type="text" id="searchValue" name="searchValue" class="inp_text" value="${searchValue}"/>
					<Button id="searchBtn" class="btn_search" onclick="search()"><fmt:message key="label.common.search"/></Button>
				</td>
			</tr>
		</table>
		</fieldset>
		
		<div class="content_area">
			<display:table name="memberList" id="member" class="simple" style="margin:5px 0pt;" requestURI="/customer/histCharge/popup.htm" >
				<display:column titleKey="label.cmsUser.id">
					<a href='javascript:sendChildValue("${member.id}", "${member.name}")'>${member.subsId}</a>
				</display:column>
				<display:column titleKey="label.customer.user.name" property="name"/>
			</display:table>
		</div>
	</div>
</form>
<%@ include file="/include/footer.jspf" %>