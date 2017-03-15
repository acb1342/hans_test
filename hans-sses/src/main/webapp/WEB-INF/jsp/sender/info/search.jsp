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
		document.location = "/sender/info/create.htm";
	}
	
	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/sender/info/delete.json', function() { 
			search();
		});
	}
	
	// 검색 조건 변경
	function searchChange() {
	}
</script>
<form method="get" id="vForm" name="vForm" action="/sender/info/search.htm">
<div class="wrap00">
	<!-- search _ start -->
	<c:set var="searchOptionKeys">id</c:set>
	<c:set var="searchOptionValues">
		<fmt:message key="label.senderInfo.hostName"/>
	</c:set>
	<%@ include file="/include/common/searchSelectForm.jsp" %>
	<!-- search _ end -->
	
	<!-- list _ start -->
	<display:table name="senderInfos" id="senderInfo" class="simple" style="margin:5px 0pt;" requestURI="/sender/info/search.htm" pagesize="10" export="false">
	<c:if test="${authority.delete}">
		<display:column titleKey="label.common.select" style="width:40px;" media="html">
			<input type="checkbox" name="selected" style="width:15px;" value="${senderInfo.id}"/>
		</display:column>
	</c:if>
		<display:column titleKey="label.senderInfo.hostName" property="id"/>
		<display:column titleKey="label.senderInfo.senderName" property="senderName"/>
		<display:column titleKey="label.senderInfo.senderTid" property="senderTid"/>
		<display:column titleKey="label.senderInfo.ip" property="ip"/>
		<display:column titleKey="label.senderInfo.port" property="port"/>
		<display:column titleKey="label.common.detail" style="text-align:center; width:140px;" media="html">
			<input type="button" value='<fmt:message key="label.common.detail"/>' onclick="location.href='/sender/info/detail.htm?id=${senderInfo.id}'"/>
		<c:if test="${authority.update}">
			<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/sender/info/update.htm?id=${senderInfo.id}'"/>
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
</div>
</form>
<%@ include file="/include/footer.jspf" %>