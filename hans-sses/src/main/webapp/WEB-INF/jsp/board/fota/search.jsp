<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<style type="text/css" media="all">
	@importA url("/js/jquery/ui/jquery-ui-timepicker-addon.css");
</style>
<script type="text/javascript" src="/js/jquery/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript">
	
	// 생성 페이지로 이동
	function insert() {
		document.location = "/board/fota/create.htm";
	}

	// 검색
	function search() {
		$("#vForm").submit();
	}

	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/board/fota/delete.json', function() { 
			search(); 
		});
	}
</script>
<form method="get" id="vForm" name="vForm" action="/board/fota/search.htm">
	<!-- search _ start -->
	<input type="hidden" name="id" id="id"/>
	<div class="wrap00">
	<!-- search _ start -->
	
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<td align="left">
					<b>파일명</b>&nbsp;<input type="text" name="searchValue" id="searchValue" value="${param.searchValue}" style="margin:0 7px 0 8px;"/>
					<input type="button" value='<fmt:message key="label.common.search"/>' onclick="search()"/>
				</td>
			</tr>
		</table>
	</fieldset><br/>
	<!-- search _ end -->
	
	<!-- list _ start -->
	
	<c:set var="row" value="${ rownum-((page-1)*10) }"/>
	<display:table name="boardList" id="boadFota" class="simple" style="margin:5px 0pt;" requestURI="/board/fota/search.htm" pagesize="10" export="false">
		<c:if test="${authority.delete}">
			<display:column titleKey="label.common.select" style="width:40px;" media="html">
				<input type="checkbox" id="selected" name="selected" style="width:15px;" value="${boadFota.snId}"/>
			</display:column>
		</c:if>
		<display:column title="NO" style="width:40px;">
			<c:out value="${row}"/>
			<c:set var="row" value="${row-1}"/>
		</display:column>
		<display:column title="파일명" style="width:200px;" media="html">
			<a href="/board/fota/detail.htm?&snId=${boadFota.snId}" > ${boadFota.fileName} </a>
		</display:column>
		<display:column title="내용" media="html" style="width:200px;">
			${boadFota.content}
		</display:column>
		<display:column title="버전정보" media="html" style="width:100px;">
			${boadFota.ver}
		</display:column>
		<display:column titleKey="label.common.regDate" media="html" style="width:100px;">
			<fmt:formatDate value="${boadFota.fstRgDt}" pattern="yyyy/MM/dd HH:mm"/>
		</display:column>
		<display:column titleKey="label.common.detail" style="text-align:center; width:140px;" media="html">
			<input type="button" value='<fmt:message key="label.common.detail"/>' onclick="location.href='/board/fota/detail.htm?snId=${boadFota.snId}'"/>
		<c:if test="${authority.update}">
			<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/board/fota/update.htm?snId=${boadFota.snId}'"/>
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
					<input type="button" value='<fmt:message key="label.common.add"/>' onclick="insert()"/>
				</c:if>
				<c:if test="${authority.delete}">
					<input type="button" class="btn_red" value='<fmt:message key="label.common.delete"/>' onclick="javascript:confirmAndDelete()"/>
				</c:if>
				</td>
			</tr>
		</table>
	</div>
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>