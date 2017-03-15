<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>

<c:set var="searchValue" value='<%=request.getParameter("searchValue")%>' />

<script type="text/javascript">
	$(document).ready(function(){
		$("#searchBtn").click(function() {
			$('#vForm').submit();
		});
	});
	
	function sendChildValue(buildingCode, buildingName) {
		opener.setChildValue(buildingCode, buildingName);
		window.close();
	}
	
	
</script>

<form method="get" id="vForm" name="vForm" action="/adjust/buildingPopup.htm" >

	<div class="wrap_winpop">
		<div class="header_area">
			<h1 class="tit"><fmt:message key="label.adjust.buildingPopup"/></h1>
		</div>
	
		<div class="content_area">
			<div class="search_area">
				<c:set var="searchValue" value='<%=request.getParameter("searchValue")%>' />
				<input type="text" id="searchValue" name="searchValue" class="inp_text" value="${searchValue}"/>
				<Button id="searchBtn" class="btn_search" onclick="search()"><fmt:message key="label.common.search"/></Button>
			</div>
		
			<c:if test="${searchValue != null && searchValue != ''}">
				<display:table name="buildingList" id="building" class="simple" style="margin:5px 0pt;" requestURI="/adjust/buildingPopup.htm" >
					<display:column titleKey="label.adjust.buildingName" media="html" >
						<a href='javascript:sendChildValue("${building.chargerName}", "${building.chargerName}")'>${building.chargerName}</a>
					</display:column>
					<display:column titleKey="label.adjust.buildingAddress" property="chargerName"/>
				</display:table>
			</c:if>
		</div>
	</div>
</form>
<%@ include file="/include/footer.jspf" %>