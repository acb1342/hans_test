
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
			var searchType = $('input:radio[name="searchType"]:checked').val();
			if (!searchType) { 
				alert("검색 조건을 먼저 선택해 주세요.");
				return;
			}
			
 			var ret = dateCheck();
 			if (ret == 0) {
 				alert("시작시점은 종료시점보다 앞서야 합니다. 기간을 다시 설정한 후 조회해 주세요.");
 				return;
 			} else if(ret == 1) {
 				alert("조회는 31일 이내만 가능합니다. 기간을 다시 설정한 후 조회해 주세요.");
 				return;
 			}
 			
 			var usname = $('input:text[name="usname"]').val();
			if (usname == "") {
				alert("건물주명 검색 팝업에서 건물주 검색 후 선택하셔야 조회가 가능합니다.");
				return;
			}

			$("#vForm").submit();
		});
		
		$("#usname").click(function() {
			openWindow("/history/log/popup.htm?searchType=101203", "userPopup", "width=448,height=448,top=100,left=100");
		});
	});
	
	function setChildValue(usid, usname) {
		$("#usid").val(usid);
		$("#usname").val(usname);
	}
	
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
<form method="get" id="vForm" name="vForm" action="/history/log/buildingOwner.htm">
	<div class="wrap00">
	<!-- search _ start -->
	<input type="hidden" id="fromDate" name="fromDate" value="${fromDate}"/>
	<input type="hidden" id="toDate" name="toDate" value="${toDate}"/>
	<input type="hidden" id="usid" name="usid" value="${usid}"/>
	
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<!-- 일별 날짜 검색 -->
				<td align="left">
					<input type="radio" name="searchType" value="report" <c:if test="${searchType == 'report'}">checked</c:if>> 신청/신고 이력 &nbsp;&nbsp;
					<input type="radio" name="searchType" value="pushSend" <c:if test="${searchType == 'pushSend'}">checked</c:if>> PUSH발신 이력 &nbsp;&nbsp;
				</td>
				<!-- 일별 날짜 검색 -->
				<td id="searchDaily" align="left">
					<input id="fromDateByDaily" value="" style="width: 75px"/> ~ <input id="toDateByDaily" value="" style="width: 75px"/> &nbsp;&nbsp;
				</td>
				<td align="left">
					<input type="text" id="usname" name="usname" value="${usname}" placeholder="건물주명" readonly/>
					<input type="button" id="searchBtn" value='<fmt:message key="label.common.search"/>'/>
				</td>
				
			</tr>
		</table>
	</fieldset>
	<br/>
	<div id="explain">* 기간 설정은 31일 이내로만 가능합니다.</div>
	<!-- search _ end -->

	<!-- list _ start -->
	<c:if test="${searchType != null && searchType != '' && viewType == 'report'}">
		<c:set var="row" value="${ rownum-((page-1)*10) }"/>
		<display:table name="logBuildingOwnerList" id="logBuildingOwner" class="simple" style="margin:5px 0pt;" requestURI="/history/log/buildingOwner.htm" pagesize="10" export="false">
			<display:column titleKey="label.history.log.no">
				<c:out value="${row}"/>
				<c:set var="row" value="${row-1}"/>	
			</display:column>
			<display:column titleKey="label.history.log.logDate">
				<fmt:parseDate var="strDate" value="${logBuildingOwner.RC_DT}" pattern="yyyy-MM-dd" />
				<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
			</display:column>
			<display:column titleKey="label.history.log.buildingOwnerName" property="RC_NAME"/>
			<display:column titleKey="label.history.log.kind">
				<c:choose>
					<c:when test="${logBuildingOwner.STATUS == '407101' || logBuildingOwner.STATUS == '409101'}">접수중</c:when>
					<c:when test="${logBuildingOwner.STATUS == '407102' || logBuildingOwner.STATUS == '409102'}">처리중</c:when>
					<c:when test="${logBuildingOwner.STATUS == '407103' || logBuildingOwner.STATUS == '409103'}">완료</c:when>
				</c:choose>
			</display:column>
			<display:column titleKey="label.history.log.content" property="BODY" style="text-align: left;"/>
			<display:column titleKey="label.history.log.channel">
			<c:choose>
				<c:when test="${logBuildingOwner.CHAN == '313101'}">WEB</c:when>
				<c:when test="${logBuildingOwner.CHAN == '313104'}">ANDROID</c:when>
				<c:when test="${logBuildingOwner.CHAN == '313105'}">IOS</c:when>
				<c:when test="${logBuildingOwner.CHAN == '313106'}">ADMIN</c:when>
			</c:choose>
			</display:column>
		</display:table>
	</c:if>
	<!-- list _ end -->
	
	<!-- list _ start -->
	<c:if test="${searchType != null && searchType != '' && viewType == 'pushSend'}">
		<c:set var="row" value="${ rownum-((page-1)*10) }"/>
		<display:table name="logBuildingOwnerList" id="logBuildingOwner" class="simple" style="margin:5px 0pt;" requestURI="/history/log/buildingOwner.htm" pagesize="10" export="false">
			<display:column titleKey="label.history.log.no" style="width:30px">
				<c:out value="${row}"/>
				<c:set var="row" value="${row-1}"/>	
			</display:column>
			<display:column titleKey="label.history.log.logDate" style="width:110px">
				<fmt:formatDate value="${logBuildingOwner.FST_RG_DT}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</display:column>
			<display:column titleKey="label.history.log.buildingOwnerName" property="NAME" style="width:100px"/>
			<display:column titleKey="label.history.log.kind" style="width:130px">
				<c:choose>
					<c:when test="${!empty logBuildingOwner.CATEGORY}">
						<div style="width:130px; text-align:left; margin-left:20px"><fmt:message key="${logBuildingOwner.CATEGORY}"/></div>
					</c:when>
					<c:otherwise>
						<div style="width:130px; text-align:left; margin-left:20px">${logBuildingOwner.TITLE}</div>
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column titleKey="label.history.log.content" style="width:400px;">
				<div style="width:400px; text-align:left; margin-left:30px">${logBuildingOwner.MSG}</div>
			</display:column>
			<display:column titleKey="label.history.log.channel" style="width:70px">
				<fmt:message key="${logBuildingOwner.OS}"/>
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