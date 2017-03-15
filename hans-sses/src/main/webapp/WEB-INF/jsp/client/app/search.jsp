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
		document.location = "/client/app/create.htm";
	}
	
	// 삭제
	function confirmAndDelete() {
		checkUesd();
		deleteByChecked('/client/app/delete.json', function() { 
			search();
		});
	}

	function checkUesd() {
		var data;
		var id = $("#selected").val();

		data = {appid:id};
		
		$.ajax({
			url:'/client/app/checkUsed.json',
			type:"POST",
			data:data,		    
			success:function(result) {
				if (!result.success) {				
					if (result.errors.reason == 'isUsed') {
						jAlert('<fmt:message key="statement.error.delete.applistIsUesd"/>', 'alert', function() {});
					} else if (result.errors.reason == 'notExist') {						
						jAlert('<fmt:message key="statement.error.delete.notExist"/>', 'alert', function() {});
					}
				}
			}
		});
	}
	
	// 검색 조건 변경
	function searchChange() {
	}
</script>
<form method="get" id="vForm" name="vForm" action="/client/app/search.htm">
<div class="wrap00">
	<!-- search _ start -->
	<c:if test="${loginGroup.name eq 'ADMIN'}">
		<c:set var="searchOptionKeys">cpId,appName</c:set>
		<c:set var="searchOptionValues">
			<fmt:message key="label.provider.cpId"/>
			,<fmt:message key="label.application.appName"/>
		</c:set>
	</c:if>
	<c:if test="${loginGroup.name != 'ADMIN'}">
		<c:set var="searchOptionKeys">appName</c:set>
		<c:set var="searchOptionValues">
			<fmt:message key="label.application.appName"/>
		</c:set>
	</c:if>
	<%@ include file="/include/common/searchSelectForm.jsp" %>
	<!-- search _ end -->

	<!-- list _ start -->
	<display:table name="applications" id="app" class="simple" style="margin:5px 0pt;" requestURI="/client/app/search.htm" pagesize="10" export="false">
	<c:if test="${authority.delete}">
		<display:column titleKey="label.common.select" style="width:40px;" media="html">
			<input type="checkbox" id="selected" name="selected" style="width:15px;" value="${app.id}"/>
		</display:column>
	</c:if>
		<display:column titleKey="label.common.id" property="id"/>
		<display:column titleKey="label.provider.cpId" property="cpId"/>
		<display:column titleKey="label.provider.cpName" media="html">
			${app.contentProvider.cpName}
		</display:column>
		<display:column titleKey="label.application.appName" property="appName"/>
		<display:column titleKey="label.application.pkgId" property="pkgId"/>
		<display:column titleKey="label.common.detail" style="text-align:center; width:140px;" media="html">
			<input type="button" value='<fmt:message key="label.common.detail"/>' onclick="location.href='/client/app/detail.htm?id=${app.id}'"/>
		<c:if test="${authority.update}">
			<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/client/app/update.htm?id=${app.id}'"/>
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