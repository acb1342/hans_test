<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 검색 조건에 따라 검색 UI 리셋
		searchChange();
	});

	// 생성 페이지로 이동
	function insert() {
		document.location = "/admin/user/create.htm";
	}

	// 검색
	function search() {
		$("#vForm").submit();
	}

	// 검색 조건에 따라 검색 UI 리셋
	function searchChange() {
		if ($("#searchType").val() == 'group') {
			$("#searchValue").hide();
			$("#searchSelect").show();
		} else {
			$("#searchValue").show();
			$("#searchSelect").hide();
		}
	}

	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/admin/user/delete.json', function() { 
			search(); 
		});
	}
</script>
<form method="get" id="vForm" name="vForm" action="/admin/user/search.htm">
	<!-- search _ start -->
	<input type="hidden" name="id" id="id"/>
	<div class="wrap00">
	<!-- search _ start -->
	<c:set var="searchOptionKeys">id,name,group</c:set>
	<c:set var="searchOptionValues">
		<fmt:message key="label.cmsUser.id"/>,
		<fmt:message key="label.cmsUser.name"/>,
		<fmt:message key="label.cmsGroup"/>
	</c:set>
	<c:set var="searchType" value='<%=request.getParameter("searchType")%>'/>
	<c:set var="searchValue" value='<%=request.getParameter("searchValue")%>'/>
	<c:set var="searchSelect" value='<%=request.getParameter("searchSelect")%>'/>
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<td class="td00">
					<select name="searchType" id="searchType" onchange="searchChange()">
					<c:forTokens var="key" items="${searchOptionKeys}" delims="," varStatus="statKey">
						<c:forTokens var="val" items="${searchOptionValues}" delims="," varStatus="statVal">
							<c:if test="${statKey.index == statVal.index}">
								<option value="${key}" ${key == searchType ? 'selected':''}>${val}</option>
							</c:if>
						</c:forTokens>
					</c:forTokens>
					</select>
				</td>
				<td align="left">
					<input type="text" name="searchValue" id="searchValue" value="${searchValue}" style="margin:0 7px 0 8px;"/>
					<select name="searchSelect" id="searchSelect" style="width:150px;">
						<c:forEach items="${groupList}" var="group">
							<option value="${group.id}" ${group.id == searchSelect? 'selected':''}>${groupList.name}</option>
						</c:forEach>
					</select>
					<input type="button" value='<fmt:message key="label.common.search"/>' onclick="javascript:search()"/>
				</td>
			</tr>
		</table>
	</fieldset>
	<!-- search _ end -->

	<!-- list _ start -->
	<display:table name="adminList" id="admin" class="simple" style="margin:5px 0pt;" requestURI="/admin/user/search.htm" pagesize="10" export="false">
	<c:if test="${authority.delete}">
		<display:column titleKey="label.common.select" style="width:40px;" media="html">
			<input type="checkbox" id="selected" name="selected" style="width:15px;" value="${admin.id}"/>
		</display:column>
	</c:if>
		<display:column titleKey="label.cmsUser.id" property="id"/>
		<display:column titleKey="label.cmsUser.name" property="name"/>
		<display:column titleKey="label.cmsGroup" property="adminGroup.name"/>
		<display:column titleKey="label.common.mobilePhone" property="mobile"/>
		<display:column titleKey="label.common.detail" style="text-align:center; width:140px;" media="html">
			<input type="button" style="width:65px !important;" value='<fmt:message key="label.common.detail"/>' onclick="location.href='/admin/user/detail.htm?id=${admin.id}'"/>
		<c:if test="${authority.update}">
			<input type="button" style="width:65px !important;" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/admin/user/update.htm?id=${admin.id}'"/>
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