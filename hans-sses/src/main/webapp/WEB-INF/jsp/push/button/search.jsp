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
		document.location = "/push/button/create.htm";
	}
	
	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/push/button/delete.json', function() { 
			search();
		});
	}
	
	// 검색 조건 변경
	function searchChange() {
	}
</script>
<form method="get" id="vForm" name="vForm" action="/push/button/search.htm">
<div class="wrap00">
	<!-- search _ start -->
	<c:set var="searchOptionKeys">title</c:set>
	<c:set var="searchOptionValues">
	    <fmt:message key="label.pushButton.title"/>
		
	</c:set>
	<%@ include file="/include/common/searchSelectForm.jsp" %>
	<!-- search _ end -->
	
	<!-- list _ start -->
	<display:table name="pushButtons" id="pushButton" class="simple" style="margin:5px 0pt;" requestURI="/push/button/search.htm" pagesize="10" export="false">
	<c:if test="${authority.delete}">
		<display:column titleKey="label.common.select" style="width:40px;" media="html">
			<input type="checkbox" name="selected" style="width:15px;" value="${pushButton.id}"/>
		</display:column>
	</c:if>
		<display:column titleKey="label.common.id" property="id"/>
		<display:column titleKey="label.pushButton.position" property="position"/>
		<display:column titleKey="label.pushButton.title" property="title"/>
		<display:column titleKey="label.pushButton.type" property="type"/>
		<display:column titleKey="label.common.detail" style="text-align:center; width:140px;" media="html">
			<input type="button" value='<fmt:message key="label.common.detail"/>' onclick="location.href='/push/button/detail.htm?id=${pushButton.id}'"/>
		<c:if test="${authority.update}">
			<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/push/button/update.htm?id=${pushButton.id}'"/>
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
	$('#pushButton tr').each(function() {
		$(this).find("td").each(function() {
			if ($(this).html().trim() == '') { $(this).html('NONE'); }
		});
	});
</script>
</div>
</form>
<%@ include file="/include/footer.jspf" %>