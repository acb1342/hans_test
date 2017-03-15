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
				alert("사용자명 검색 팝업에서 사용자 검색 후 선택하셔야 조회가 가능합니다.");
				return;
			}

			$("#vForm").submit();
		});
		
		$("#usname").click(function() {
			openWindow("/statistics/stat/userPopup.htm", "new", "width=448,height=448,top=100,left=100");
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
<form method="get" id="vForm" name="vForm" action="/history/log/user.htm">
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
					<input type="radio" name="searchType" value="logIn" <c:if test="${searchType == 'logIn'}">checked</c:if>> 로그인 이력 &nbsp;&nbsp;
					<input type="radio" name="searchType" value="pushSend" <c:if test="${searchType == 'pushSend'}">checked</c:if>> PUSH발신 이력 &nbsp;&nbsp;
				</td>
				<!-- 일별 날짜 검색 -->
				<td id="searchDaily" align="left">
					<input id="fromDateByDaily" value="" style="width: 75px"/> ~ <input id="toDateByDaily" value="" style="width: 75px"/> &nbsp;&nbsp;
				</td>
				<td align="left">
					<input type="text" id="usname" name="usname" value="${usname}" placeholder="사용자명" readonly/>
					<input type="button" id="searchBtn" value='<fmt:message key="label.common.search"/>'/>
				</td>
				
			</tr>
		</table>
	</fieldset>
	<br/>
	<div id="explain">* 기간 설정은 31일 이내로만 가능합니다.</div>
	<!-- search _ end -->

	<!-- list _ start -->
	<c:choose>
	<c:when test="${searchType == 'logIn'}">
		<c:set var="row" value="${ rownum-((page-1)*10) }"/>
		<display:table name="logUserList" id="logUserList" class="simple" style="margin:5px 0pt;" requestURI="/history/log/user.htm" pagesize="10" export="false">
			<display:column titleKey="label.history.log.no">
				<c:out value="${row}"/>
				<c:set var="row" value="${row-1}"/>	
			</display:column>
			<display:column titleKey="label.history.log.logDate" property="FST_RG_DT"/>
			<display:column titleKey="label.history.log.userName" property="NAME"/>
			<display:column titleKey="label.history.log.userId" property="SUBS_ID"/>
			<display:column titleKey="label.history.log.logInOut"><fmt:message key="${logUserList.LOG_TYPE}" /></display:column>
			<display:column titleKey="label.history.log.channel"><fmt:message key="${logUserList.ACCESS_DEVICE}" /></display:column>
			<display:column titleKey="label.history.log.result"><fmt:message key="${logUserList.RESULT_CODE}" /></display:column>
		</display:table>
	</c:when>
	<c:when test="${searchType == 'pushSend'}">
		<c:set var="row" value="${ rownum-((page-1)*10) }"/>
		<display:table name="logUserList" id="logUserList" class="simple" style="margin:5px 0pt;" requestURI="/history/log/user.htm" pagesize="10" export="false">
			<display:column titleKey="label.history.log.no" style="width:30px">
				<c:out value="${row}"/>
				<c:set var="row" value="${row-1}"/>	
			</display:column>
			<display:column titleKey="label.history.log.logDate" style="width:110px">
				<fmt:formatDate value="${logUserList.FST_RG_DT}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</display:column>
			<display:column titleKey="label.history.log.userName" property="CUST_NAME" style="width:70px"/>
			<display:column titleKey="label.history.log.userId" property="SUBS_ID" style="width:120px"/>
			<display:column titleKey="label.history.log.kind" style="width:140px">
			<c:choose>
					<c:when test="${!empty logUserList.CATEGORY}">
						<div style="width:140px; text-align:left; margin-left:20px"><fmt:message key="${logUserList.CATEGORY}"/></div>
					</c:when>
					<c:otherwise>
						<div style="width:140px; text-align:left; margin-left:20px">${logUserList.TITLE}</div>
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column titleKey="label.history.log.content" property="MSG" style="width:300px; text-align:left;"/>
			<display:column titleKey="label.history.log.channel" style="width:70px">	<fmt:message key="${logUserList.OS}" /></display:column>
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