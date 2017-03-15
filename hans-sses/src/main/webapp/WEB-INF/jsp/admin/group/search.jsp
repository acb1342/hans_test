<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	// 추가 페이지로 이동
	function insert() {
		document.location = "/admin/group/create.htm";
	}
	
	// 검색 페이지로 이동
	function search() {
		$("#vForm").submit();
	}
	
	// 그룹 삭제
	function confirmAndDelete() {
		deleteByChecked('/admin/group/delete.json', function() { 
			search(); 
		});
	}
</script>
<form method="get" name="vForm" action="/admin/group/search.htm">
<div class="wrap00">
	<!-- search _ start -->
	<c:set var="searchOptionKeys">name</c:set>
	<c:set var="searchOptionValues">
		<fmt:message key="label.cmsGroup.name"/>
	</c:set>
	<%@ include file="/include/common/searchSelectForm.jsp" %>
	<!-- search _ end -->
	
	<!-- list _ start -->
	<display:table name="cmsGroups" id="cmsGroup" class="simple" style="margin:5px 0pt;" requestURI="/admin/group/search.htm" pagesize="10" export="false">
	<c:if test="${authority.delete}">
		<display:column titleKey="label.common.select" style="width:40px;" media="html">
			<input type="checkbox" id="selected" name="selected" style="width:15px;" value="${cmsGroup.id}"/>
		</display:column>
	</c:if>
		<display:column titleKey="label.cmsGroup.id" property="id"/>
		<display:column titleKey="label.cmsGroup.name" property="name"/>
		<display:column titleKey="label.common.createDate" media="html">
			<fmt:formatDate value="${cmsGroup.fstRgDt}" pattern="yyyy/MM/dd"/>
		</display:column>
		<display:column titleKey="label.common.detail" style="text-align:center; width:140px;" media="html">
			<input type="button" style="width:65px !important;" value='<fmt:message key="label.common.detail"/>' onclick="location.href='/admin/group/detail.htm?id=${cmsGroup.id}'"/>
			<input type="button" style="width:65px !important;" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/admin/group/update.htm?id=${cmsGroup.id}'"/>	
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