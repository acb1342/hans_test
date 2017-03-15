<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<link href="/js/jquery/ui/month/MonthPicker.min.css" rel="stylesheet" type="text/css" />
<link href="/js/jquery/ui/month/examples.css" rel="stylesheet" type="text/css" />
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="/js/jquery/ui/month/MonthPicker.min.js"></script>
<script type="text/javascript" src="/js/jquery/ui/month/jquery.mtz.monthpicker.js"></script>

<c:set var="historyDate" value='<%=request.getParameter("historyDate")%>'/>
<c:set var="usid" value='<%=request.getParameter("usid")%>'/>
<c:set var="usname" value='<%=request.getParameter("usname")%>'/>

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
		$("#historyDateByMonth").monthpicker(month_options);
		
		if("${historyDate}" == "") {
			$("#historyDateByMonth").val($.datepicker.formatDate("yy-mm", new Date()));
		} else {
			$("#historyDateByMonth").val("${historyDate}");
		}
		
		$("#searchBtn").click(function() {
			$("#historyDate").val($("#historyDateByMonth").val());
			
			var usname = $('input:text[name="usname"]').val();
			if (usname == "") {
				alert("사용자명 검색 팝업에서 사용자 검색 후 선택하셔야 조회가 가능합니다.");
				return;
			}
			
			$("#vForm").submit();
		});
		
		$("#usname").click(function() {
			openWindow("/statistics/stat/userPopup.htm", "userPopup", "width=448,height=448,top=100,left=100");
		});
	});
	
	function setChildValue(usid, usname) {
		$("#usid").val(usid);
		$("#usname").val(usname);
	}
</script>
<form method="get" id="vForm" name="vForm" action="/history/log/payment.htm">
	<div class="wrap00">
	<!-- search _ start -->
	<input type="hidden" id="historyDate" name="historyDate" value="${historyDate}"/>
	<input type="hidden" id="usid" name="usid" value="${historyDate}"/>
	
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<!-- 일별 날짜 검색 -->
				<td id="searchMonth" align="left">
					<input id="historyDateByMonth" value="" style="width: 75px"/>
				</td>
				<td align="left">
					<input type="text" id="usname" name="usname" value="${usname}" placeholder="사용자명" readonly/>
					<input type="button" id="searchBtn" value='<fmt:message key="label.common.search"/>'/>
				</td>
				
			</tr>
		</table>
	</fieldset>
	<!-- search _ end -->

	<!-- list _ start -->
	<c:if test="${usid != null && usid != ''}">
		<c:set var="row" value="${ rownum-((page-1)*10) }"/>
		<display:table name="logPaymentList" id="logPayment" class="simple" style="margin:5px 0pt;" requestURI="/history/log/payment.htm" pagesize="10" export="false">
			<display:column titleKey="label.history.log.no">
				<c:out value="${row}"/>
				<c:set var="row" value="${row-1}"/>	
			</display:column>
			<display:column titleKey="label.history.log.sendDate">
				<fmt:parseDate var="strDate" value="${logPayment.FST_RG_DT}" pattern="yyyy-MM-dd" />
				<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
			</display:column>
			<display:column titleKey="label.history.log.accountDate" property="FST_RG_DT">
				<fmt:parseDate var="strDate" value="${logPayment.FST_RG_DT}" pattern="yyyy-MM-dd" />
				<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
			</display:column>
			<display:column titleKey="label.history.log.userName" property="NAME"/>
			<display:column titleKey="label.history.log.userId" property="SUBS_ID"/>
			<display:column titleKey="label.history.log.account" property="PAYMENT_FEE"/>
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