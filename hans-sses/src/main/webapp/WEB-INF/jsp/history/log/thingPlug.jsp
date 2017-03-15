<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<link href="/js/jquery/ui/month/MonthPicker.min.css" rel="stylesheet" type="text/css" />
<link href="/js/jquery/ui/month/examples.css" rel="stylesheet" type="text/css" />
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="/js/jquery/ui/month/MonthPicker.min.js"></script>
<script type="text/javascript" src="/js/jquery/ui/month/jquery.mtz.monthpicker.js"></script>

<c:set var="fromDate" value='<%=request.getParameter("fromDate")%>'/>
<c:set var="toDate" value='<%=request.getParameter("toDate")%>'/>
<c:set var="chargerNumber" value='<%=request.getParameter("chargerNumber")%>'/>

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
		
		$("#fromDateByDaily").datepicker();
		$("#toDateByDaily").datepicker();
		
		if("${fromDate}" == "") {
			$("#fromDateByDaily").val($.datepicker.formatDate("yy-mm-dd", new Date()));
		} else {
			$("#fromDateByDaily").val("${fromDate}");
		}
		
		if("${toDate}" == "") {
			$("#toDateByDaily").val($.datepicker.formatDate("yy-mm-dd", new Date()));
		} else {
			$("#toDateByDaily").val("${toDate}");
		}
		
		$("#searchBtn").click(function() {
 			var ret = dateCheck();
 			if (ret == 0) {
 				alert("시작시점은 종료시점보다 앞서야 합니다. 기간을 다시 설정한 후 조회해 주세요.");
 				return;
 			} else if(ret == 1) {
 				alert("조회는 31일 이내만 가능합니다. 기간을 다시 설정한 후 조회해 주세요.");
 				return;
 			}
 			
 			var usname = $('input:text[name="chargerNumber"]').val();
			if (usname == "") {
				alert("충전기 관리번호 입력 후 조회가 가능합니다.");
				return;
			}

			$("#vForm").submit();
		});
	});
	
	function dateCheck() {
		var fromDate = $("#fromDateByDaily").val();
		var toDate = $("#toDateByDaily").val();
		
		if (!fromDate || fromDate == "") return -1;
		if (!toDate || toDate == "")     return -2;
		
		var fromArray = fromDate.split("-");
		var toArray = toDate.split("-");
		
		var fDate = new Date(fromArray[0], fromArray[1]-1, fromArray[2]);
		var tDate = new Date(toArray[0], toArray[1]-1, toArray[2]);
		
		if (fDate > tDate) return 0;
		
		if (((tDate.getTime() - fDate.getTime()) / 1000 / 60 / 60 / 24) > 31) {
			return 1;
		}
		
		fromDate = fromArray[0] + "-" + fromArray[1] + "-" + fromArray[2];
		toDate   = toArray[0] + "-" + toArray[1] + "-" + toArray[2];
		
		$("#fromDate").val(fromDate);
		$("#toDate").val(toDate);
		
		return 2;
	}
</script>
<form method="get" id="vForm" name="vForm" action="/history/log/thingPlug.htm">
	<div class="wrap00">
	<!-- search _ start -->
	<input type="hidden" id="fromDate" name="fromDate" value="${fromDate}"/>
	<input type="hidden" id="toDate" name="toDate" value="${toDate}"/>
	
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<!-- 일별 날짜 검색 -->
				<td id="searchDaily" align="left">
					<input id="fromDateByDaily" value="" style="width: 75px"/> ~ <input id="toDateByDaily" value="" style="width: 75px"/> &nbsp;&nbsp;
				</td>
				<td align="left">
					<input type="text" id="chargerNumber" name="chargerNumber" value="${chargerNumber}" placeholder="충전기 관리번호"/>
					<input type="button" id="searchBtn" value='<fmt:message key="label.common.search"/>'/>
				</td>
				
			</tr>
		</table>
	</fieldset>
	<br/>
	<div id="explain">* 송신:SKT EVC 플래폼 -> ThingPlug / 수신:ThingPlug -> SKT EVC 플래폼, 기간 설정은 31일 이내로만 가능합니다.</div>
	<!-- search _ end -->

	<!-- list _ start -->
	<c:if test="${chargerNumber != null && chargerNumber != ''}">
		<c:set var="row" value="${ rownum-((page-1)*10) }"/>
		<display:table name="logThingPlugList" id="logThingPlug" class="simple" style="margin:5px 0pt;" requestURI="/history/log/thingPlug.htm" pagesize="10" export="false">
			<display:column titleKey="label.history.log.no">
				<c:out value="${row}"/>
				<c:set var="row" value="${row-1}"/>	
			</display:column>
			<display:column titleKey="label.history.log.logDate">
				<fmt:parseDate var="strDate" value="${logThingPlug.FST_RG_DT}" pattern="yyyy-MM-dd HH:mm" />
				<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd HH:mm" />
			</display:column>
			<display:column titleKey="label.history.log.communication">
			<c:choose>
				<c:when test="${logThingPlug.TRANS_TYPE == '408101'}">송신</c:when>
				<c:when test="${logThingPlug.TRANS_TYPE == '408102'}">수신</c:when>
				<c:otherwise>-</c:otherwise>
			</c:choose>
			</display:column>
			<display:column titleKey="label.history.log.chargerNumber" property="MGMT_NO"/>
			<display:column titleKey="label.history.log.order">
			<c:choose>
				<c:when test="${logThingPlug.CMD == 'C00100'}">충전기 대기중</c:when> 
				<c:when test="${logThingPlug.CMD == 'C00101'}">차량 플러그 장착</c:when> 
				<c:when test="${logThingPlug.CMD == 'C00103'}">RFID 인식</c:when> 
				<c:when test="${logThingPlug.CMD == 'C00104'}">충전 시작</c:when> 
				<c:when test="${logThingPlug.CMD == 'C00105'}">충전 중</c:when> 
				<c:when test="${logThingPlug.CMD == 'C00106'}">충전 완료</c:when> 
				<c:when test="${logThingPlug.CMD == 'C00107'}">차량 플러그 탈거</c:when> 
				<c:when test="${logThingPlug.CMD == 'C00108'}">디바이스 고장</c:when> 
				<c:when test="${logThingPlug.CMD == 'C00109'}">기타 에러</c:when> 
				<c:when test="${logThingPlug.CMD == 'C0010A'}">충전 대기</c:when> 
				<c:when test="${logThingPlug.CMD == 'C0010B'}">충전 불가</c:when> 
				<c:when test="${logThingPlug.CMD == 'C0010C'}">충전기 강제 탈거</c:when> 
				<c:when test="${logThingPlug.CMD == 'C0010D'}">통신이상</c:when>
				<c:otherwise>-</c:otherwise>
			</c:choose>
			</display:column>
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