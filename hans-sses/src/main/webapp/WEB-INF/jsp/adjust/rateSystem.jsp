<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/include/header.jspf" %>
<link href="/js/jquery/ui/month/MonthPicker.min.css" rel="stylesheet" type="text/css" />
<link href="/js/jquery/ui/month/examples.css" rel="stylesheet" type="text/css" />
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="/js/jquery/ui/month/MonthPicker.min.js"></script>
<script type="text/javascript" src="/js/jquery/ui/month/jquery.mtz.monthpicker.js"></script>

<c:set var="searchType" value='<%=request.getParameter("searchType")%>'/>
<c:set var="fromDate" value='<%=request.getParameter("fromDate")%>'/>
<c:set var="toDate" value='<%=request.getParameter("toDate")%>'/>
<c:set var="usid" value='<%=request.getParameter("usid")%>'/>
<c:set var="usname" value='<%=request.getParameter("usname")%>'/>

<script type="text/javascript">
	function goUpdate() {
		document.location = "/adjust/rateSystemForm.htm";
	}
</script>

<div class="wrap00">
	<!-- list _ start -->
	<display:table name="basePriceList" id="basePriceList" class="simple" style="margin:5px 0pt;">
		<display:column titleKey="label.adjust.basePrice"><fmt:formatNumber value="${basePriceList.basePrice}" pattern="#,###" />원</display:column>
		<display:column titleKey="label.adjust.cardPay"><fmt:formatNumber value="${basePriceList.cardPrice}" pattern="#,###" />원</display:column>
	</display:table>

	<c:set var="idx" value="0"/>
	<display:table name="rateSystemList" id="rateSystemList" class="simple" style="margin:5px 0pt;"   >
		<c:set var="idx" value="${idx + 1}"/>
		<display:column titleKey="label.adjust.timeFee">${idx}</display:column>
		<display:column titleKey="label.adjust.feeName" property="TITLE"/>
		<display:column titleKey="label.adjust.amtPrice" property="FEE"/>
		<display:column titleKey="label.adjust.applyPeriod">${rateSystemList.START_MMDD}~${rateSystemList.END_MMDD}</display:column>
		<display:column titleKey="label.adjust.applyTime">${rateSystemList.START_HHMI}~${rateSystemList.END_HHMI}</display:column>
	</display:table>
	<!-- list _ end -->
	
	<!-- button _ start -->
	<div class="footer">
		<table style="width:100%">
			<tr>
				<td height="30" align="right">
					<input type="button" value='<fmt:message key="label.common.update"/>' onclick="goUpdate()"/>
				</td>
			</tr>
		</table>
	</div>
	<!-- button _ end -->
</div>
<%@ include file="/include/footer.jspf" %>