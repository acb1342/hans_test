<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<link href="/js/jquery/ui/month/MonthPicker.min.css" rel="stylesheet" type="text/css" />
<link href="/js/jquery/ui/month/examples.css" rel="stylesheet" type="text/css" />
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="/js/jquery/ui/month/MonthPicker.min.js"></script>
<script type="text/javascript" src="/js/jquery/ui/month/jquery.mtz.monthpicker.js"></script>
<script type="text/javascript">
month_options = {
		pattern: 'yyyy-mm',
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']		
};
$.datepicker.regional['ko'] = {
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		dayNames: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
		dateFormat: 'yy-mm-dd',
		yearSuffix: '년',
		showMonthAfterYear: true
};
$.datepicker.setDefaults($.datepicker.regional['ko']);

	$(function () {
		var group = ${loginGroup.id};
		if (group == 2 || group == 3) $('#searchDate').hide();
		
		$("#fromDate").datepicker();
		$("#toDate").datepicker();
	});
	
	// 생성 페이지로 이동
	function insert() {
		document.location = "/board/noticeAdmin/create.htm";
	}

	// 검색
	function search() {
		var from = $('#fromDate').val().replaceAll('/','').replaceAll(':','').replace(/ /gi,'').substring(0,12);
		var to = $('#toDate').val().replaceAll('/','').replaceAll(':','').replace(/ /gi,'').substring(0,12);
		if (from > to) {
			alert("검색 날짜를 확인해주세요.");
			return;
		}
		
		$("#vForm").submit();
	}

	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/board/noticeAdmin/delete.json', function() { 
			search(); 
		});
	}
</script>
<form method="get" id="vForm" name="vForm" action="/board/noticeAdmin/search.htm">
	<input type="hidden" name="id" id="id"/>
	<c:set var="searchValue" value='<%=request.getParameter("searchValue")%>'/>
	<c:set var="fromDate" value='<%=request.getParameter("fromDate")%>'/>
	<c:set var="toDate" value='<%=request.getParameter("toDate")%>'/>
	<div class="wrap00">
	
	<!-- search _ start -->
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<td><b>제목</b>&nbsp;<input type="text" name="searchValue" id="searchValue" value="${searchValue}" style="margin:0 7px 0 8px;"/></td>
				<td id="searchDate">
					<b><fmt:message key="label.common.regDate"/></b>&nbsp;
					<input type="text" id="fromDate" name="fromDate" value="${fromDate}" style="width: 150px"/> ~ <input type="text" id="toDate" name="toDate" value="${toDate}" style="width: 150px"/>
				</td>
				<td><input style="margin:0 0 0 5px" type="button" value='<fmt:message key="label.common.search"/>' onclick="javascript:search()"/></td>
			</tr>
		</table>
	</fieldset><br/>
	<!-- search _ end -->
	
	<!-- list _ start -->
	<c:set var="row" value="${ rownum-((page-1)*10) }"/>
	<display:table name="boardList" id="boadNotice" class="simple" style="margin:5px 0pt;" requestURI="/board/noticeAdmin/search.htm" pagesize="10" export="false">
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
			<input type="button" value='<fmt:message key="label.boad.detail"/>' onclick="location.href='/board/noticeAdmin/detail.htm?id=${boadNotice.snId}'"/>
		<c:if test="${authority.update}">
			<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/board/noticeAdmin/update.htm?id=${boadNotice.snId}'"/>
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