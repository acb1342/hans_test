<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<link href="/js/jquery/ui/month/MonthPicker.min.css" rel="stylesheet" type="text/css" />
<link href="/js/jquery/ui/month/examples.css" rel="stylesheet" type="text/css" />
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="/js/jquery/ui/month/MonthPicker.min.js"></script>
<script type="text/javascript" src="/js/jquery/ui/month/jquery.mtz.monthpicker.js"></script>

<c:set var="historyDate" value='<%=request.getParameter("historyDate")%>'/>

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

	$(function() {
		$("#historyDateByDaily").datepicker();
		
		if("${historyDate}" == "") {
			$("#historyDateByDaily").val($.datepicker.formatDate("yy-mm-dd", new Date()));
		} else {
			$("#historyDateByDaily").val("${historyDate}");
		}
		
		$("#searchBtn").click(function() {
			$("#historyDate").val($("#historyDateByDaily").val());
			
			$("#vForm").submit();
		});
	});
</script>
<form method="get" id="vForm" name="vForm" action="/history/log/kepco.htm">
	<div class="wrap00">
	<!-- search _ start -->
	<input type="hidden" id="historyDate" name="historyDate" value="${historyDate}"/>
	
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<!-- 일별 날짜 검색 -->
				<td id="searchDaily" align="left">
					<input id="historyDateByDaily" value="" style="width: 75px"/>
				</td>
				<td align="left">
					<input type="button" id="searchBtn" value='<fmt:message key="label.common.search"/>'/>
				</td>
				
			</tr>
		</table>
	</fieldset>
	<!-- search _ end -->

	<!-- list _ start -->
	<c:if test="${historyDate != null && historyDate != ''}">
		<c:set var="row" value="${ rownum-((page-1)*10) }"/>
		<display:table name="logKepcoList" id="logKepco" class="simple" style="margin:5px 0pt;" requestURI="/history/log/kepco.htm" pagesize="10" export="false">
			<display:column titleKey="label.history.log.no">
				<c:out value="${row}"/>
				<c:set var="row" value="${row+1}"/>	
			</display:column>
			<display:column titleKey="label.history.log.sendDate">
				<fmt:parseDate var="strDate" value="${logKepco.TRANS_DT}" pattern="yyyy-MM-dd" />
				<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
			</display:column>
			<display:column titleKey="label.history.log.adjustPeriod">
				<fmt:parseDate var="strDate" value="${logKepco.START_YMD}" pattern="yyyyMMdd" />
				<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" /> ~ 
				<fmt:parseDate var="strDate" value="${logKepco.END_YMD}" pattern="yyyyMMdd" />
				<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
			</display:column>
			<display:column titleKey="label.history.log.readDate">
				<fmt:parseDate var="strDate" value="${logKepco.METER_YMD}" pattern="yyyyMMdd" />
				<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
			</display:column>
			<display:column titleKey="label.history.log.paymentTerm" property="PERIOD_DAY"/>
			<display:column titleKey="label.history.log.buildingName" property="BD_GROUP_NAME"/>
			<display:column titleKey="label.history.log.buildingDetail" property="BD_NAME"/>
			<display:column titleKey="label.history.log.amt" property="CHARGE_AMT"/>
		</display:table>
	</c:if>
	<!-- list _ end -->
	
	<!-- button _ start -->
	<%--
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
	--%>
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>