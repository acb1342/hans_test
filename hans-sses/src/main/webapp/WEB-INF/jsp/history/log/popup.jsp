<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>

<c:set var="searchType" value='<%=request.getParameter("searchType")%>'/>
<c:set var="searchValue" value='<%=request.getParameter("searchValue")%>' />

<script type="text/javascript">
	$(document).ready(function(){
		$('#searchValue').focus();
		$("#searchBtn").click(function() {
			$('#vForm').submit();
		});
	});
	
	function sendChildValue(userId, userName) {
		opener.setChildValue(userId, userName);
		window.close();
	}
	
	
</script>

<form method="get" id="vForm" name="vForm" action="/history/log/popup.htm" >

	<div class="wrap_winpop">
		<div class="header_area">
			<c:choose>
			<c:when test="${searchType == '101206'}">
				<h1 class="tit"><fmt:message key="label.history.log.userPopup"/></h1>
			</c:when>
			<c:when test="${searchType == '101203'}">
				<h1 class="tit"><fmt:message key="label.history.log.buildingOwnerPopup"/></h1>
			</c:when>
			<c:when test="${searchType == '101204'}">
				<h1 class="tit"><fmt:message key="label.history.log.installerPopup"/></h1>
			</c:when>
			<c:otherwise></c:otherwise>
			</c:choose>
		</div>
	
		<fieldset class="searchBox">
		<table class="searchTbl" style="margin:0px 0px 0px 20px" >
			<tr>
				<td>
					<input type="hidden" id="searchType" name="searchType" value="${searchType}"/>
					<input type="text" id="searchValue" name="searchValue" class="inp_text" value="${searchValue}"/>
					<Button id="searchBtn" class="btn_search" onclick="search()"><fmt:message key="label.common.search"/></Button>
				</td>
			</tr>
		</table>
		</fieldset>
			
		<div class="content_area">
			<c:if test="${searchValue != null && searchValue != ''}">
				<c:choose>
				<c:when test="${searchType == '101206'}">
				<display:table name="memberList" id="member" class="simple" style="margin:5px 0pt;" requestURI="/history/log/popup.htm" >
					<display:column titleKey="label.cmsUser.id">
						<a href='javascript:sendChildValue("${member.id}", "${member.name}")'>${member.subsId}</a>
					</display:column>
					<display:column titleKey="label.customer.user.name" property="name"/>
				</display:table>
				</c:when>
				<c:otherwise>
					<display:table name="adminList" id="admin" class="simple" style="margin:5px 0pt;" requestURI="/history/log/popup.htm" >
					<display:column title="설치자 ID" media="html" >
						<a href='javascript:sendChildValue("${admin.id}", "${admin.name}")'>${admin.id}</a>
					</display:column>
					<display:column title="설치자명" property="name"/>
				</display:table>
				</c:otherwise>
				</c:choose>
			</c:if>
		</div>
	</div>
</form>
<%@ include file="/include/footer.jspf" %>