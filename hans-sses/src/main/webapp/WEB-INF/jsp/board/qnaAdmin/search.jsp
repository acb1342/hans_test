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
		document.location = "/board/qnaAdmin/create.htm";
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
		deleteByChecked('/board/qnaAdmin/delete.json', function() { 
			search(); 
		});
	}
</script>
<form method="get" id="vForm" name="vForm" action="/board/qnaAdmin/search.htm">
	<!-- search _ start -->
	<input type="hidden" name="id" id="id"/>
	<input type="hidden" id="usedDatepicker"/>
	<c:set var="fromDate" value='<%=request.getParameter("fromDate")%>'/>
	<c:set var="toDate" value='<%=request.getParameter("toDate")%>'/>
	<div class="wrap00">
	<!-- search _ start -->
	<c:set var="searchOptionKeys">all,name,title</c:set>
	<c:set var="searchOptionValues">
		<fmt:message key="label.common.all"/>,
		<fmt:message key="label.common.writer"/>,
		<fmt:message key="label.contentEditor.title"/>
	</c:set>
	<c:set var="searchType" value='<%=request.getParameter("searchType")%>'/>
	<c:set var="searchValue" value='<%=request.getParameter("searchValue")%>'/>
	
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<td><b><fmt:message key="label.common.search"/></b>&nbsp;
					<select name="searchType" id="searchType" onchange="searchChange()" style="width:100px">
					<c:forTokens var="key" items="${searchOptionKeys}" delims="," varStatus="statKey">
						<c:forTokens var="val" items="${searchOptionValues}" delims="," varStatus="statVal">
							<c:if test="${statKey.index == statVal.index}">
								<option value="${key}" ${key == searchType ? 'selected':''}>${val}</option>
							</c:if>
						</c:forTokens>
					</c:forTokens>
					</select>
					<input type="text" name="searchValue" id="searchValue" value="${searchValue}" style="margin:0 7px 0 8px;"/>
				</td>
				<td id="searchDate">
					<b><fmt:message key="label.common.regDate"/></b>&nbsp;
					<input type="text" id="fromDate" name="fromDate" value="${fromDate}" style="width: 150px"/> ~ <input type="text" id="toDate" name="toDate" value="${toDate}" style="width: 150px"/>
				</td>
				<td><input style="margin:0 0 0 5px" type="button" value='<fmt:message key="label.common.search"/>' onclick="javascript:search()"/></td>
			</tr>
		</table>	
	</fieldset>
	
	<!-- search _ end -->
	<!-- admin _ list _ start -->
	<c:if test="${loginGroup.id != 2 && loginGroup.id != 3 }">
	<c:set var="row" value="${ rownum-((page-1)*10) }"/>
	<display:table name="boardList" id="boadQna" class="simple" style="margin:5px 0pt;" requestURI="/board/qnaAdmin/search.htm" pagesize="10" export="false">
		<c:if test="${authority.delete}">
			<display:column titleKey="label.common.select" media="html">
				<input type="checkbox" id="selected" name="selected" style="width:15px;" value="${boadQna.snId}"/>
			</display:column>
		</c:if>
		<display:column title="순서">
			<c:out value="${row}"/>
			<c:set var="row" value="${row-1}"/>
		</display:column>
		<display:column titleKey="label.contentEditor.title" style="width:300px; text-align:left;" media="html">
			 <span style="display:inline-block; width:300px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis; text-align:left;">${boadQna.title}</span>
		</display:column>
		<display:column titleKey="label.common.writer" property="penName"/>
		<display:column titleKey="label.common.regDate" media="html">
			<fmt:formatDate value="${boadQna.fstRgDt}" pattern="yyyy.MM.dd"/>
		</display:column>
		<display:column titleKey="label.boad.answer">	
			<c:if test="${'Y' eq boadQna.answerYn }">완료</c:if>
			<c:if test="${'N' eq boadQna.answerYn }">대기중</c:if>
		</display:column>
		<display:column titleKey="label.boad.regUserType" style="width:100px;">
				<c:if test="${ '0' eq boadQna.writerType }">사용자</c:if>
				<c:if test="${ '1' eq boadQna.writerType }">운영자</c:if>
				<c:if test="${ '2' eq boadQna.writerType }">설치자</c:if>
				<c:if test="${ '3' eq boadQna.writerType }">건물주</c:if>
				<c:if test="${ '4' eq boadQna.writerType }">상담사</c:if>
				<c:if test="${ '99' eq boadQna.writerType }">미등록 사용자</c:if>
		</display:column>
		<display:column title="공개여부" style="width:60px;">
			<c:if test="${'Y' eq boadQna.openYn}">공개</c:if>
			<c:if test="${'N' eq boadQna.openYn}">비공개</c:if>
		</display:column>
		<display:column titleKey="label.common.detail" style="text-align:center; width:140px;" media="html">
			<c:if test="${'Y' ne boadQna.answerYn}">
				<input type="button" value='<fmt:message key="label.boad.detail"/>' onclick="location.href='/board/qnaAdmin/update.htm?wk=create&id=${boadQna.snId}&page=${page}'"/>
			</c:if>
			<c:if test="${'Y' eq boadQna.answerYn}">
				<input type="button" value='<fmt:message key="label.boad.detail"/>' onclick="location.href='/board/qnaAdmin/detail.htm?id=${boadQna.snId}&page=${page}'"/>
			</c:if>
				
			<c:if test="${authority.update}">
				<c:if test="${'Y' eq boadQna.answerYn}">
					<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/board/qnaAdmin/update.htm?wk=update&id=${boadQna.snId}&page=${page}'"/>
				</c:if>
				<c:if test="${'Y' ne boadQna.answerYn}">
					<input type="button" disabled value='<fmt:message key="label.common.modify"/>'/>
				</c:if>
			</c:if>
		</display:column>
	</display:table>
	</c:if>
	<!-- list _ end -->
	
	<!-- owner/installer _ list _ start -->
	<c:if test="${loginGroup.id == 2 || loginGroup.id == 3 }">
	<c:set var="row" value="${ rownum-((page-1)*10) }"/>
	<display:table name="boardList" id="boadQna" class="simple" style="margin:5px 0pt;" requestURI="/board/qnaAdmin/search.htm" pagesize="10" export="false">
		<display:column title="순서">
			<c:out value="${row}"/>
			<c:set var="row" value="${row-1}"/>
		</display:column>
		<display:column titleKey="label.contentEditor.title" style="width:300px;" media="html">
			 <span style="display:inline-block; width:300px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis; text-align:left;">${boadQna.title}</span>
		</display:column>
		<display:column titleKey="label.common.writer" property="penName"/>
		<display:column titleKey="label.common.regDate" media="html">
			<fmt:formatDate value="${boadQna.fstRgDt}" pattern="yyyy.MM.dd"/>
		</display:column>
		<display:column titleKey="label.boad.answer">	
			<c:if test="${'Y' eq boadQna.answerYn }">완료</c:if>
			<c:if test="${'N' eq boadQna.answerYn }">대기중</c:if>
		</display:column>
		<display:column titleKey="label.boad.regUserType" style="width:100px;">
				<c:if test="${ '0' eq boadQna.writerType }">사용자</c:if>
				<c:if test="${ '1' eq boadQna.writerType }">운영자</c:if>
				<c:if test="${ '2' eq boadQna.writerType }">설치자</c:if>
				<c:if test="${ '3' eq boadQna.writerType }">건물주</c:if>
				<c:if test="${ '4' eq boadQna.writerType }">상담사</c:if>
				<c:if test="${ '99' eq boadQna.writerType }">미등록 사용자</c:if>
		</display:column>
		<display:column title="공개여부" style="width:60px;">
			<c:if test="${'Y' eq boadQna.openYn}">공개</c:if>
			<c:if test="${'N' eq boadQna.openYn}">비공개</c:if>
		</display:column>
		<display:column titleKey="label.common.detail" style="text-align:center; width:140px;" media="html">
		<c:if test="${loginUser.id eq boadQna.fstRgUsid }">
			<input type="button" value='<fmt:message key="label.boad.detail"/>' onclick="location.href='/board/qnaAdmin/detail.htm?id=${boadQna.snId}&page=${page}'"/>
		</c:if>	
		<c:if test="${loginUser.id ne boadQna.fstRgUsid }">
			<input <c:if test="${'N' eq boadQna.openYn }">disabled</c:if> type="button" value='<fmt:message key="label.boad.detail"/>' onclick="location.href='/board/qnaAdmin/detail.htm?id=${boadQna.snId}&page=${page}'"/>
		</c:if>
		<c:if test="${authority.update}">
			<c:if test="${'N' eq boadQna.answerYn}">
				<c:if test="${loginUser.id eq boadQna.fstRgUsid }">
					<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/board/qnaAdmin/updateQuestion.htm?id=${boadQna.snId}&page=${page}'"/>
				</c:if>
				<c:if test="${loginUser.id ne boadQna.fstRgUsid }">
					<input type="button" disabled value='<fmt:message key="label.common.modify"/>'/>
				</c:if>
			</c:if>
			<c:if test="${'Y' eq boadQna.answerYn || empty boadQna.answerYn}">
				<input type="button" disabled value='<fmt:message key="label.common.modify"/>'/>
			</c:if>
		</c:if>
		</display:column>
	</display:table>
	</c:if>
	<!-- list _ end -->
	
	<!-- button _ start -->
	<div class="footer">
		<table class="searchTbl" style="width:100%">
			<tr>
				<td height="30" align="right">
				<c:if test="${authority.create}">
					<input type="button" value='<fmt:message key="label.common.add"/>' onclick="javascript:insert()"/>
				</c:if>
				<c:if test="${authority.delete && loginGroup.id == 1}">
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