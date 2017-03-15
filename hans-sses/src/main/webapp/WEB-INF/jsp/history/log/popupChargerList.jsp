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
		<div class="header_area"><h1 class="tit">충전기</h1></div>
		<div class="content_area">
		<display:table name="list" id="map" class="simple" style="margin:5px 0pt;" requestURI="/history/log/popup.htm" >
			<display:column title="충전그룹" property="CHARGER_GROUP_NAME"/>
			<display:column title="충전기관리번호" property="MGMT_NO"/>
		</display:table>
		</div>
	</div>
</form>
<%@ include file="/include/footer.jspf" %>