<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
		document.location = "/subscriber/create.htm";
	}
	
	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/subscriber/delete.json', function() { 
			search();
		});
	}
	
	// 검색 조건 변경
	function searchChange() {
	}
</script>
<form method="get" id="vForm" name="vForm" action="/subscriber/search.htm">
<div class="wrap00">
	<!-- search _ start -->
	<c:set var="searchOptionKeys">mdn,appId,pushToken</c:set>
	<c:set var="searchOptionValues">
	    <fmt:message key="label.endUser.mdn"/>
		,<fmt:message key="label.endUser.appId"/>
		,<fmt:message key="label.endUser.pushToken"/>
		
	</c:set>
	<%@ include file="/include/common/searchSelectForm.jsp" %>
	<!-- search _ end -->
	
	<!-- list _ start -->
	<display:table name="endUsers" id="endUser" class="simple" style="margin:5px 0pt;" requestURI="/subscriber/search.htm" pagesize="10" export="false">
	<c:if test="${authority.delete}">
		<display:column titleKey="label.common.select" style="width:40px;" media="html">
			<input type="checkbox" name="selected" style="width:15px;" value="${endUser.id}"/>
		</display:column>
	</c:if>
		<display:column titleKey="label.endUser.mdn" property="mdn"/>
		<display:column titleKey="label.endUser.appId" property="appId" maxLength="20"/>
		<display:column titleKey="label.endUser.appVer" property="appVer"/>
		<display:column titleKey="label.endUser.pushToken" property="pushToken" maxLength="11"/>
		<display:column titleKey="label.endUser.os" property="os"/>
		<display:column titleKey="label.endUser.osVer" property="osVer"/>
		<display:column titleKey="label.endUser.deviceId" property="deviceId" maxLength="11"/>
		<display:column titleKey="label.common.detail" style="text-align:center; width:140px;" media="html">
			<input type="button" value='<fmt:message key="label.common.detail"/>' onclick="location.href='/subscriber/detail.htm?id=${endUser.id}'"/>
		<c:if test="${authority.update}">
			<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/subscriber/update.htm?id=${endUser.id}'"/>
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
	$('#endUser tr').each(function() {
		$(this).find("td").each(function() {
			if ($(this).html().trim() == '') { $(this).html('NONE'); }
		});
	});
</script>
</div>
</form>
<%@ include file="/include/footer.jspf" %>