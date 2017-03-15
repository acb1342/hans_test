<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 검색 조건에 따라 검색 UI 리셋
		searchChange();
	});

	// 생성 페이지로 이동
	/* function insert() {
		//document.location = "/admin/operator/create.htm";
		$("#content").load("/admin/operator/create.htm");
	} */

	// 검색
	function search() {
		//$("#vForm").submit();
		var formData = $("#vForm").serialize();
		var url = "/admin/operator/search.htm";
		
		$.ajax({
			type : "POST",
			url : url,
			data : formData,			
			success : function(response){
				$("#content").html(response);
			},
			error : function(){
				console.log("error!!");
				//err_page();
				return false;
			}
		});
		
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
		deleteByChecked('/admin/operator/delete.json', function() { 
			search(); 
		});
	}
/* 	
	function detail(id){
		//$("#content").load("/admin/operator/detail.htm?id="+id);
		
		$.ajax({
			type : "GET",
			url : "/admin/operator/detail.htm",
			data : {"id":id},
			success : function(response){
				$("#search-form").html(response);
			},
			error : function(){
				console.log("error!!");
				//err_page();
				return false;
			}
		});
		
		
	}
	function update(id){
		page_move("/admin/operator/update.htm", id);
	}
	 */
	
	
	
	
</script>
<div id="search-form">
<form method="get" id="vForm" name="vForm" action="/admin/operator/search.htm" onsubmit="return false;">
	<!-- search _ start -->
	<input type="hidden" name="id" id="id"/>
	<div class="wrap00">
	<!-- search _ start -->
	<c:set var="searchOptionKeys">id,name</c:set>
	<c:set var="searchOptionValues">ID,이름,</c:set>
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
					<input type="text" name="searchValue" id="searchValue" value="${searchValue}" style="margin:0 7px 0 8px;" onkeypress="if (event.keyCode == 13) {search();}"/>
					<select name="searchSelect" id="searchSelect" style="width:150px;">
						<c:forEach items="${groupList}" var="groupList">
							<option value="${groupList.id}" ${groupList.id == searchSelect? 'selected':''}>${groupList.name}</option>
						</c:forEach>
					</select>
					<select name="searchValid" id="searchValid" style="width:80px;">
							<option value="">전체</option>
							<option value="Y" ${param.searchValid == 'Y' ? 'selected':''}>사용중</option>
							<option value="N" ${param.searchValid == 'N' ? 'selected':''}>사용중지</option>
					</select>
					<input type="button" value='<fmt:message key="label.common.search"/>' onclick="javascript:search()"/>
				</td>
			</tr>
		</table>
	</fieldset>
	<!-- search _ end -->

	<!-- list _ start -->
	<c:set var="row" value="${ rownum-((page-1)*10) }"/>
	<display:table name="adminList" id="admin" class="simple" style="margin:5px 0pt;" requestURI="/admin/operator/search.htm" pagesize="10" export="false">
	<c:if test="${authority.delete}">
		<display:column titleKey="label.common.select" style="width:40px;" media="html">
			<input type="checkbox" id="selected" name="selected" style="width:15px;" value="${admin.id}"/>
		</display:column>
	</c:if>
		<display:column title="순서" style="width:40px;">
			<c:out value="${row}"/>
			<c:set var="row" value="${row-1}"/>
		</display:column>
		<display:column titleKey="label.cmsUser.landlordId" property="id"/>
		<display:column titleKey="label.cmsUser.landlordName" property="name"/>
		<display:column title="사용자 그룹">${admin.adminGroup.name} 권한</display:column>
		<display:column title="계정상태">
			<c:choose>
				<c:when test="${admin.validYn == 'Y' }">사용중</c:when>
				<c:otherwise>사용중지</c:otherwise>
			</c:choose>
		</display:column>
		<display:column titleKey="label.common.detail" style="text-align:center; width:140px;" media="html">
			<input type="button" style="width:65px !important;" value='<fmt:message key="label.common.detail"/>' onclick="javascript:page_move('/admin/operator/detail.htm','${admin.id}');"/>
		<c:if test="${authority.update}">
			<input type="button" style="width:65px !important;" value='<fmt:message key="label.common.modify"/>' onclick="javascript:page_move('/admin/operator/update.htm','${admin.id}');"/>
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
					<input type="button" value='<fmt:message key="label.common.add"/>' onclick="javascript:page_move('/admin/operator/create.htm','');"/>
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
</div>
