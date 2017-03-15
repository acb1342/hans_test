<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
 
<%@ page import="com.mobilepark.doit5.web.UtilTag" %>
<%@ include file="/include/header.jspf" %>

<style type="text/css" media="all">
	label {
		width:80px;
		float:left;
		vertical-align:middle;
	}
	div.scrollTableContainer {
    	height:140px;
		overflow:auto;
		width:100%;
		margin:0px 0px 0px 0px;
		display:block;
		position:relative;
		scrollbar-3dlight-color:#2F4F4F;
		scrollbar-highlight-color:#2F4F4F;
		scrollbar-face-color:#DCDCDC;
		scrollbar-shadow-color:#2F4F4F;
		scrollbar-darkshadow-color:#2F4F4F;
		scrollbar-track-color:#2F4F4F;
		scrollbar-arrow-color:#0000FF;
		scrollbar-base-color:#2F4F4F;
		border:1px dotted #000000; 
		padding:0px 0px 0px 0px;
	}
</style>
<script type="text/javascript">

	$(function() {
		$('#fromDate, #toDate').datepicker({
			beforeShow:customRange,
			dateFormat:'yymmdd'
		});
	});
	
	function customRange(input) {
		return {
			minDate:(input.id == "fromDate" ? new Date(2012, 12 - 1, 1):null),
			minDate:(input.id == "toDate" ? $("#fromDate").datepicker("getDate"):null),
			maxDate:(input.id == "fromDate" ? $("#toDate").datepicker("getDate"):null)
			};
	}	
	
	// 검색
	function search() {
		$("#vForm").submit();
	}

</script>
<form method="get" id="vForm" name="vForm" action="/statistics/push/daily.htm">
	<!-- search _ start -->

	<div class="wrap00">
		<fieldset class="searchBox">
			<table class="searchTbl" align="left" style="margin:5px 0px 5px 26px;">
				<tr>
					<td width="80px"><fmt:message key="label.common.searchPeriod"/></td>
					<td>
						<input id="fromDate" name="fromDate" style="width:327px" value="${fromDate}"/>
					</td>
					<td width="135px" style="text-align:center">~</td>
					<td>
						<input id="toDate" name="toDate" style="width:327px" value="${toDate}"/>
						&nbsp;
						<input type="button" value='<fmt:message key="label.common.search"/>' onclick="javascript:search()"/>
					</td>
				</tr>
			</table>
		</fieldset>
		<!-- search _ end -->

		<!-- list _ start -->
		<display:table name="histories" id="histories" varTotals="totals" class="simple" style="margin:5px 0pt;" requestURI="/statistics/push/daily.htm" pagesize="30" export="true">
 			<display:caption style="text-align:left"><b>Total PUSH Count : <fmt:formatNumber type="number" value="${totalPush}"/></b></display:caption>
			<display:column titleKey="label.statistics.statDate" total="true" property="stat_date"/>
			<display:column titleKey="label.statistics.push.successCnt" total="true" property="successCnt"/>
			<display:column titleKey="label.statistics.push.failCnt" total="true" property="failCnt"/>
			<display:column titleKey="label.statistics.push.total" total="true" property="total"/>

			<display:footer>
			<td>Total</td>
			<td><fmt:formatNumber type="number" value="${totals.column2}"/></td>
			<td><fmt:formatNumber type="number" value="${totals.column3}"/></td>
			<td><fmt:formatNumber type="number" value="${totals.column4}"/></td>
			</display:footer>	

			<display:setProperty name="export.excel.filename" value="PushStat.xls"/>
		</display:table>
		<!-- list _ end -->
		</div>
</form>
<%@ include file="/include/footer.jspf" %>

</body>
</html>