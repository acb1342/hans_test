<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	// 생성
	function insert() {
		document.location = "/admin/securityKey/create.htm";
	}

	// 검색
	function search() {
		$("#vForm").submit();
	}

	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/admin/securityKey/delete.json', function() { 
			search(); 
		});
	}
</script>
<form method="get" id="vForm" name="vForm" action="/admin/securityKey/search.htm">
<div class="wrap00">
	<!-- search _ start -->
	<fieldset class="searchBox">
		<table class="searchTbl">
			<tr>
				<td class="td00"><fmt:message key="label.securityKey.key"/></td>
				<td align="left">
					<input type="text" id="id" name="id" value="${id}"/>
					&nbsp;
					<input type="button" value='<fmt:message key="label.common.search"/>' onclick="javascript:search()"/>	
				</td>
			</tr>
		</table>
	</fieldset>
	<!-- search _ end -->
	
	<!-- list _ start -->
	<display:table name="securityKeys" id="securityKey" class="simple" style="margin:5px 0pt;" requestURI="/admin/securityKey/search.htm" pagesize="10" export="false">
	<c:if test="${authority.delete}">
		<display:column titleKey="label.common.select" style="width:40px;" media="html">
			<input type="checkbox" id="selected" name="selected" style="width:15px;" value="${securityKey.id}"/>
		</display:column>
	</c:if>
		<display:column titleKey="label.securityKey.key" property="id"/>
		<display:column titleKey="label.securityKey.value" property="value"/>
		<display:column titleKey="label.common.detail" style="text-align:center; width:100px;" media="html">
			<input type="button" style="width:46px !important;" value='<fmt:message key="label.common.detail"/>' onclick="location.href='/admin/securityKey/detail.htm?id=${securityKey.id}'"/>
			<c:if test="${authority.update}">
				<input type="button" style="width:46px !important;" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/admin/securityKey/update.htm?id=${securityKey.id}'"/>
			</c:if>	
		</display:column>
	</display:table>
	<!-- list _ end -->
	
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