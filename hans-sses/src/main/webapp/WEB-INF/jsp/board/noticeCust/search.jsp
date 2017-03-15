<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function () {
		
	});
	
	// 생성 페이지로 이동
	function insert() {
		document.location = "/board/noticeCust/create.htm";
	}

	// 검색
	function search() {
		$("#vForm").submit();
	}

	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/board/noticeCust/delete.json', function() { 
			search(); 
		});
	}
</script>
<form method="get" id="vForm" name="vForm" action="/board/noticeCust/search.htm">
	<input type="hidden" name="id" id="id"/>
	<c:set var="searchValue" value='<%=request.getParameter("searchValue")%>'/>
	<div class="wrap00">
	
	<!-- search _ start -->
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<td><b>제목</b>&nbsp;<input type="text" name="searchValue" id="searchValue" value="${searchValue}" style="margin:0 7px 0 8px;"/></td>
				<td>&nbsp;<input type="button" value='<fmt:message key="label.common.search"/>' onclick="javascript:search()"/></td>
			</tr>
		</table>
	</fieldset><br/>
	<!-- search _ end -->
	
	<!-- list _ start -->
	<c:set var="row" value="${ rownum-((page-1)*10) }"/>
	<display:table name="boardList" id="boadNotice" class="simple" style="margin:5px 0pt;" requestURI="/board/noticeCust/search.htm" pagesize="10" export="false">
		<c:if test="${authority.delete}">
			<display:column titleKey="label.common.select" style="width:40px;" media="html">
				<input type="checkbox" id="selected" name="selected" style="width:15px;" value="${boadNotice.snId}"/>
			</display:column>
		</c:if>
		<display:column title="순서" style="width:40px;">
			<c:out value="${row}"/>
			<c:set var="row" value="${row-1}"/>
		</display:column>
		<display:column titleKey="label.contentEditor.title" style="width:400px;" media="html">
			<span style="display:inline-block; width:400px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis; text-align:left;">${boadNotice.title}</span>
		</display:column>
		<display:column titleKey="label.common.regDate" media="html" style="width:100px;">
			<fmt:formatDate value="${boadNotice.fstRgDt}" pattern="yyyy.MM.dd"/>
		</display:column>
		<display:column titleKey="label.boad.detail" style="text-align:center; width:100px;" media="html">
			<input type="button" value='<fmt:message key="label.boad.detail"/>' onclick="location.href='/board/noticeCust/detail.htm?id=${boadNotice.snId}'"/>
		<c:if test="${authority.update}">
			<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/board/noticeCust/update.htm?id=${boadNotice.snId}'"/>
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