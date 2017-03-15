<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
		if ("monthly" == "${searchType}") {
			$("#fromDateByMonth").val("${fromDate}");
			$("#toDateByMonth").val("${toDate}");
		}			
		
		// 검색 조건에 따라 검색 UI 리셋
		searchChange();
		
		$("#usname").click(function() {
			openWindow("/statistics/stat/userPopup.htm", "new", "width=448,height=448,top=100,left=100");
		});
		
		$("#searchBtn").click(function() {
			
			var searchType = $('input:radio[name="searchType"]:checked').val();
			if (!searchType) { 
				alert("월별 또는 사용자별 선택 후 기간 또는 사용자명을 입력하여 조회해 주세요.");
				return;
			}
			
			if (searchType == "monthly") {
	 			var ret = dateCheck(searchType);
	 			if (ret < 0) {
					alert("월별 선택 후 기간을 입력하여 조회해 주세요.");
					return;
	 			} else if (ret == 0) {
	 				alert("시작시점은 종료시점보다 앞서야 합니다. 기간을 다시 설정한 후 조회해 주세요.");
	 				return;
	 			}
			}

			if (searchType == "user") {
	 			var usname = $('input:text[name="usname"]').val();
	 			
				if (usname == "") { 
					alert("사용자명을 먼저 입력한 후 조회해 주세요.");
					return;
				}
			}

			$("#vForm").submit();
		});
	});

	// 검색 조건에 따라 검색 UI 리셋
	function searchChange() {
		
		var searchType = $('input:radio[name="searchType"]:checked').val();
		
		if (searchType == "monthly") {
			$("#fromDateByMonth").monthpicker(month_options);
			$("#toDateByMonth").monthpicker(month_options);
			
			$("#searchMonth").show();
			$("#usname").hide();
		} else if (searchType == "user") {
			$("#searchMonth").hide();
			$("#usname").show();
		} else {
			$("#searchMonth").hide();
			$("#usname").hide();
		}
	}
	
	function setChildValue(usid, usname) {
		$("#usid").val(usid);
		$("#usname").val(usname);
	}
	
	function dateCheck(searchType) {
		
		var fromDate = "", toDate = "";
		
		if (searchType == "daily") {
			fromDate = $("#fromDateByDaily").val();
			toDate = $("#toDateByDaily").val();
		}
		else if (searchType == "monthly") {
			fromDate = $("#fromDateByMonth").val();
			toDate = $("#toDateByMonth").val();
		}
		
		if (!fromDate || fromDate == "") return -1;
		//if (!toDate || toDate == "")     return -2;
		
		var fromArray = fromDate.split("-");
		//var toArray = toDate.split("-");
		
		if (searchType == "monthly") {
			fromArray[2] = "01";//, toArray[2] = "01";
		}
		
		var fDate = new Date(fromArray[0], fromArray[1]-1, fromArray[2]);
		//var tDate = new Date(toArray[0], toArray[1]-1, toArray[2]);
		
		//if (fDate > tDate) return 0;
		
		/* if (searchType == "daily" && ((tDate.getTime() - fDate.getTime()) / 1000 / 60 / 60 / 24) > 31) {
			return 1;
		}
		 */
		fromDate = fromArray[0] + "-" + fromArray[1];
		//toDate   = toArray[0] + "-" + toArray[1];
		
		if (searchType == "daily") {
			fromDate += "-" + fromArray[2];
			//toDate += "-" + toArray[2];
		}
		
		$("#fromDate").val(fromDate);
		$("#toDate").val(toDate);
		
		return 2;
	}
	
	function sendUserSearch(usid, usname) {
		$("#usid").val(usid);
		$("#usname").val(usname);
		$('input:radio[name="searchType"]').val("user");
		
		$("#vForm").submit();
	}
</script>
<form method="get" id="vForm" name="vForm" action="/statistics/stat/payment.htm">
	<div class="wrap00">
	<!-- search _ start -->
	<input type="hidden" id="fromDate" name="fromDate" value="${fromDate}"/>
	<input type="hidden" id="toDate" name="toDate" value="${toDate}"/>
	<input type="hidden" id="usid" name="usid" value="${usid}"/>
	
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<!-- 일별 / 월별 -->
				<td align="left">
					<input type="radio" name="searchType" value="monthly" onclick="searchChange();"<c:if test="${searchType == 'monthly'}">checked</c:if>> 월별 &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="searchType" value="user" onclick="searchChange();"<c:if test="${searchType == 'user'}">checked</c:if>>       사용자별
				</td>
				<!-- 월별 날짜 검색 -->
				<td id="searchMonth" align="left">
					<input id="fromDateByMonth" value="" style="width: 75px"/>
					<%--
					<input id="toDateByMonth" value="" style="width: 75px"/>
					--%>
				</td>
				<!-- 사용자 검색 -->
				<td id="searchUser" align="left">
					<input type="text" id="usname" name="usname" value="${usname}" placeholder="사용자명" readonly/>
				</td>
				<!-- 사용자 검색 -->
				<td align="left">
					<input type="button" id="searchBtn" value='<fmt:message key="label.common.search"/>'/>
				</td>
			</tr>
		</table>
	</fieldset>
	<br/>
	<!-- search _ end -->

	<!-- list _ start -->
	<c:if test="${searchType != null && searchType != ''}">
		<table style="width:100%;">
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01"><fmt:message key="label.statistics.stat.basePrice"/></td>
			<td height="25" class="td01"><fmt:message key="label.statistics.stat.cardCnt"/></td>
			<td height="25" class="td01"><fmt:message key="label.statistics.stat.cardPay"/></td>
			<td height="25" class="td01"><fmt:message key="label.statistics.stat.totalAmt"/></td>
			<td height="25" class="td01"><fmt:message key="label.statistics.stat.usePay"/></td>
			<td height="25" class="td01"><fmt:message key="label.statistics.stat.payment"/></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td class="td02"><p style="margin-left:5px"><fmt:formatNumber value="${totalPaymentStat.BASIC_FEE}" pattern="#,###.#"/></p></td>
			<td class="td02"><p style="text-align:center">${totalPaymentStat.CARD_ISSUING_CNT}</p></td>
			<td class="td02"><p style="text-align:center"><fmt:formatNumber value="${totalPaymentStat.CARD_ISSUING_COST}" pattern="#,###.#"/></p></td>
			<td class="td02"><p style="text-align:center; margin-left:50px"><fmt:formatNumber value="${totalPaymentStat.CHARGE_AMT/1000}" pattern="#,###.#"/></p></td>
			<td class="td02"><p style="text-align:center"><fmt:formatNumber value="${totalPaymentStat.CHARGE_PRICE}" pattern="#,###.#"/></p></td>
			<td class="td02"><p style="text-align:center; margin-right:90px"><fmt:formatNumber value="${totalPaymentStat.PAYMENT_FEE}" pattern="#,###.#"/></p></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
	</table>
	</c:if>
		
	<c:choose>
	<c:when test="${searchType == 'monthly'}">
		<c:set var="row" value="${ rownum-((page-1)*10) }"/>
		<display:table name="paymentStat" id="paymentStat" class="simple" style="margin:5px 0pt; text-align:right;" requestURI="/statistics/stat/payment.htm" pagesize="10" export="false">
			<display:column titleKey="label.statistics.stat.no">
				<c:out value="${row}"/>
				<c:set var="row" value="${row-1}"/>	
			</display:column>
			<display:column titleKey="label.statistics.stat.paymentDate">
				<fmt:parseDate var="strDate" value="${paymentStat.PAYMENT_YMD}" pattern="yyyyMMdd" />
				<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
			</display:column>
			<display:column titleKey="label.statistics.stat.userName" media="html" >
				<a href='javascript:sendUserSearch("${paymentStat.USID}", "${paymentStat.USID}")'>${paymentStat.CUST_NAME}</a>
			</display:column>
			<display:column titleKey="label.statistics.stat.basePrice">
				<fmt:formatNumber value="${paymentStat.BASIC_FEE}" pattern="#,###.#"/>
			</display:column>
			<display:column titleKey="label.statistics.stat.cardCnt">
				<p style="text-align:right; margin-right:30px">${paymentStat.CARD_ISSUING_CNT}</p>
			</display:column>
			<display:column titleKey="label.statistics.stat.cardPay">
				<p style="text-align:right; margin-right:30px"><fmt:formatNumber value="${paymentStat.CARD_ISSUING_COST}" pattern="#,###.#"/></p>
			</display:column>
			<display:column titleKey="label.statistics.stat.totalAmt">
				<p style="text-align:right; margin-right:40px"><fmt:formatNumber value="${paymentStat.CHARGE_AMT/1000}" pattern="#,###.#"/></p>
			</display:column>
			<display:column titleKey="label.statistics.stat.usePay">
				<p style="text-align:right; margin-right:30px"><fmt:formatNumber value="${paymentStat.CHARGE_PRICE}" pattern="#,###.#"/></p>
			</display:column>
			<display:column titleKey="label.statistics.stat.payment">
				<p style="text-align:right; margin-right:30px"><fmt:formatNumber value="${paymentStat.PAYMENT_FEE}" pattern="#,###.#"/></p>
			</display:column>
		</display:table>
	</c:when>
	<c:when test="${searchType == 'user'}">
		<c:set var="row" value="${ rownum-((page-1)*10) }"/>
		<display:table name="paymentStat" id="paymentStat" class="simple" style="margin:5px 0pt; text-align:right;" requestURI="/statistics/stat/payment.htm" pagesize="10" export="false">
			<display:column titleKey="label.statistics.stat.no">
				<c:out value="${row}"/>
				<c:set var="row" value="${row-1}"/>	
			</display:column>
			<display:column titleKey="label.statistics.stat.paymentDate" property="PAYMENT_YMD"/>
			<display:column titleKey="label.statistics.stat.userName" media="html" >
				<a href='javascript:sendUserSearch("${paymentStat.USID}", "${paymentStat.USID}")'>${paymentStat.CUST_NAME}</a>
			</display:column>
			<display:column titleKey="label.statistics.stat.basePrice">
					<p style="text-align:right; margin-right:20px"><fmt:formatNumber value="${paymentStat.BASIC_FEE}" pattern="#,###.#"/></p>
			</display:column>
			<display:column titleKey="label.statistics.stat.cardCnt">
				<p style="text-align:right; margin-right:35px">${paymentStat.CARD_ISSUING_CNT}</p>
			</display:column>
			<display:column titleKey="label.statistics.stat.cardPay">
				<p style="text-align:right; margin-right:40px"><fmt:formatNumber value="${paymentStat.CARD_ISSUING_COST}" pattern="#,###.#"/></p>
			</display:column>
			<display:column titleKey="label.statistics.stat.totalAmt">
				<p style="text-align:right; margin-right:40px"><fmt:formatNumber value="${paymentStat.CHARGE_AMT}" pattern="#,###.####"/></p>
			</display:column>
			<display:column titleKey="label.statistics.stat.usePay">
				<p style="text-align:right; margin-right:30px"><fmt:formatNumber value="${paymentStat.CHARGE_PRICE}" pattern="#,###.#"/></p>
			</display:column>
			<display:column titleKey="label.statistics.stat.payment">
				<p style="text-align:right; margin-right:30px"><fmt:formatNumber value="${paymentStat.PAYMENT_FEE}" pattern="#,###.#"/></p>
			</display:column>
		</display:table>
	</c:when>
	<c:otherwise></c:otherwise>
	</c:choose>
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