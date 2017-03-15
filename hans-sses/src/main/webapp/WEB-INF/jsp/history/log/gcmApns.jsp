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
<c:set var="os" value='<%=request.getParameter("os")%>'/>
<c:set var="usType" value='<%=request.getParameter("usType")%>'/>
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
 			var ret = dateCheck();
 			if (ret == 0) {
 				alert("시작시점은 종료시점보다 앞서야 합니다. 기간을 다시 설정한 후 조회해 주세요.");
 				return;
 			} else if(ret == 1) {
 				alert("조회는 31일 이내만 가능합니다. 기간을 다시 설정한 후 조회해 주세요.");
 				return;
 			}
 			
 			var os = $('select[name="os"]').val();
			if (os == 'NONE') { 
				alert("OS를 선택 후 조회해 주세요.");
				return;
			}
			
			var usType = $('select[name="usType"]').val();
			if (usType == 'NONE') { 
				alert("USER를 선택 후 조회해 주세요.");
				return;
			}
 			
 			var usname = $('input:text[name="usname"]').val();
			if (usname == "") { 
				alert("USER 선택 및 검색 팝업에서 검색 후 선택하셔야 조회가 가능합니다.");
				return;
			}

			$("#vForm").submit();
		});
		
		$("#usname").click(function() {
			var usType = $('select[name="usType"]').val();
			if (usType == 'NONE') { 
				alert("USER를 선택 후 검색해 주세요.");
				return;
			}

			openWindow("/history/log/popup.htm?searchType=" + usType, "userPopup", "width=448,height=448,top=100,left=100");
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
<form method="get" id="vForm" name="vForm" action="/history/log/gcmApns.htm">
	<div class="wrap00">
	<!-- search _ start -->
	<input type="hidden" id="fromDate" name="fromDate" value="${fromDate}"/>
	<input type="hidden" id="toDate" name="toDate" value="${toDate}"/>
	<input type="hidden" id="usid" name="usid" value="${usid}"/>
	
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<!-- 일별 날짜 검색 -->
				<td id="searchDaily" align="left">
					<input id="fromDateByDaily" value="" style="width: 75px"/> ~ <input id="toDateByDaily" value="" style="width: 75px"/> &nbsp;&nbsp;
				</td>
				<td align="left">
					<select name="os" id="os" style="width:150px;">
						<option value="NONE">OS선택</option>
						<option value="301401" <c:if test="${os == '301401'}">selected</c:if>>Android</option>
						<option value="301402" <c:if test="${os == '301402'}">selected</c:if>>IOS</option>
					</select>
					<select name="usType" id="usType" style="width:150px;">
						<option value="NONE">USER선택</option>
						<option value="101206" <c:if test="${usType == '101206'}">selected</c:if>>사용자</option>
						<option value="101203" <c:if test="${usType == '101203'}">selected</c:if>>건물주</option>
						<option value="101204" <c:if test="${usType == '101204'}">selected</c:if>>설치자</option>
					</select>
					<input type="text" id="usname" name="usname" value="${usname}" placeholder="이름" readonly/>
					<input type="button" id="searchBtn" value='<fmt:message key="label.common.search"/>'/>
				</td>
				
			</tr>
		</table>
	</fieldset>
	<br/>
	<div id="explain">* 기간 설정은 31일 이내로만 가능합니다.</div>
	<!-- search _ end -->

	<!-- list _ start -->
	<c:if test="${usid != null && usid != ''}">
		<c:set var="row" value="${ rownum-((page-1)*10) }"/>
		<display:table name="logGcmApnsList" id="logGcmApns" class="simple" style="margin:5px 0pt;" requestURI="/history/log/gcmApns.htm" pagesize="10" export="false">
			<display:column titleKey="label.history.log.no">
				<c:out value="${row}"/>
				<c:set var="row" value="${row-1}"/>	
			</display:column>
			<display:column titleKey="label.history.log.sendRequestDate">
				<fmt:parseDate var="strDate" value="${logGcmApns.FST_RG_DT}" pattern="yyyy-MM-dd" />
				<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
			</display:column>
			<display:column titleKey="label.history.log.os" media="html">
			<c:choose>
				<c:when test="${logGcmApns.OS == '301401'}">Android</c:when>
				<c:when test="${logGcmApns.OS == '301402'}">IOS</c:when>
				<c:otherwise>-</c:otherwise>
			</c:choose>
			</display:column>
			<display:column titleKey="label.history.log.enUser" media="html">
			<c:choose>
				<c:when test="${logGcmApns.CUST_TYPE == '101206'}">사용자</c:when>
				<c:when test="${logGcmApns.CUST_TYPE == '101203'}">건물주</c:when>
				<c:when test="${logGcmApns.CUST_TYPE == '101204'}">설치자</c:when>
				<c:otherwise>-</c:otherwise>
			</c:choose>
			</display:column>
			<display:column titleKey="label.history.log.enUserName" property="CUST_NAME"/>
			<display:column titleKey="label.history.log.enUserId" property="USID"/>
			<display:column titleKey="label.history.log.result" property="RESULT_CODE"/>
			<display:column titleKey="label.history.log.message" property="MSG" style="width: 500px"/>
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