<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<style type="text/css" media="all">
	@importA url("/js/jquery/ui/jquery-ui-timepicker-addon.css");
</style>
<script type="text/javascript" src="/js/jquery/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript">
	$(function () {
		var group = ${loginGroup.id};
		if (group == 2 || group == 3 ) $('#searchDate').hide();
		
		$('#fromDate').datetimepicker({
			dateFormat: 'yy/mm/dd'
		});
		$('#toDate').datetimepicker({
			dateFormat: 'yy/mm/dd',
			hour: 23,
			minute: 59
		});
		
		$("#excelBtn").click(function() {
	        $("#vForm").attr("action","/board/notice/excelDown.json");
	        $("#vForm").submit();
		});
		
	});
	
	// 생성 페이지로 이동
	function insert() {
		document.location = "/board/notice/create.htm";
	}

	// 검색
	function search() {
		$("#vForm").attr("action","/board/notice/search.htm");
		$("#vForm").submit();
	}

	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/board/notice/delete.json', function() { 
			search(); 
		});
	}
</script>
<form method="get" id="vForm" name="vForm" action="/board/notice/search.htm">
	<!-- search _ start -->
	<input type="hidden" name="id" id="id"/>
	<c:set var="searchValue" value='<%=request.getParameter("searchValue")%>'/>
	<div class="wrap00">
	<!-- search _ start -->
	
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<td><b>제목</b>&nbsp;<input type="text" name="searchValue" id="searchValue" value="${searchValue}" style="margin:0 7px 0 8px;"/></td>
				<td id="searchDate">
				&nbsp;&nbsp;<b><fmt:message key="label.common.regDate"/></b>&nbsp;
				<input id="fromDate" name="fromDate" style="width: 170px" value="${fromDate}"/> ~ <input id="toDate" name="toDate" style="width: 170px" value="${toDate}"/>
				</td>
				<td>&nbsp;
					<input type="button" value='<fmt:message key="label.common.search"/>' onclick="javascript:search()"/>
					<input type="button" value="엑셀 다운로드" id="excelBtn"/>
				</td>
			</tr>
		</table>
	</fieldset><br/>
	<!-- search _ end -->
	
	<!-- list _ start -->
	
	<c:set var="row" value="${ rownum-((page-1)*10) }"/>
	<display:table name="boardList" id="boadNotice" class="simple" style="margin:5px 0pt;" requestURI="/board/notice/search.htm" pagesize="10" export="false">
		<c:if test="${authority.delete}">
			<display:column titleKey="label.common.select" style="width:40px;" media="html">
				<input type="checkbox" id="selected" name="selected" style="width:15px;" value="${boadNotice.sn_id}"/>
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
			<fmt:formatDate value="${boadNotice.fstRgDt}" pattern="yyyy/MM/dd HH:mm"/>
		</display:column>
		<c:if test="${loginGroup.id == 1 }">
			<display:column title="노출대상" style="width:100px;">
				<c:choose> 
					<c:when test="${ 'Y' eq boadNotice.installer_yn }">설치자</c:when>
					<c:when test="${ 'Y' eq boadNotice.owner_yn }">건물주</c:when>
					<c:when test="${ 'Y' eq boadNotice.counselor_yn }">상담사</c:when>
					<c:when test="${ 'Y' eq boadNotice.cust_yn }">사용자</c:when>
					<c:otherwise>운영자</c:otherwise>
				</c:choose>
			</display:column>
			<display:column title="노출여부" style="width:60px;">
				<c:if test="${ 'Y' eq boadNotice.display_yn}">노출</c:if>
				<c:if test="${ 'N' eq boadNotice.display_yn}">비노출</c:if>
			</display:column>
		</c:if>
		<display:column titleKey="label.boad.detail" style="text-align:center; width:100px;" media="html">
			<input type="button" value='<fmt:message key="label.boad.detail"/>' onclick="location.href='/board/notice/detail.htm?id=${agent.id}&seq=${boadNotice.sn_id}'"/>
		<c:if test="${authority.update}">
			<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/board/notice/update.htm?id=${agent.id}&seq=${boadNotice.sn_id}'"/>
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