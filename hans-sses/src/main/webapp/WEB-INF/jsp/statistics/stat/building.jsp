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
<c:set var="searchBdGroupId" value='${searchBdgId}'/>
<c:set var="buildingName" value='<%=request.getParameter("buildingName")%>'/>
<c:set var="buildingDetail" value='<%=request.getParameter("buildingDetail")%>'/>

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
		
		if ("daily" == "${searchType}") {
			$("#fromDateByDaily").val("${fromDate}");
			$("#toDateByDaily").val("${toDate}");
		}
		if ("monthly" == "${searchType}") {
			$("#fromDateByMonth").val("${fromDate}");
			$("#toDateByMonth").val("${toDate}");
		}			
		
		// 검색 조건에 따라 검색 UI 리셋
		searchChange();
		
		$("#bdGroup").click(function() {
			window.open("/elcg/controller/popup.htm","new","width=448,height=448,top=100,left=100");
		});
		
		$("#searchBtn").click(function() {
			
			var searchType = $('input:radio[name="searchType"]:checked').val();
			if (!searchType) { 
				alert("일별 또는 월별 선택 후 기간을 입력하여 조회해 주세요.");
				return;
			}
			
 			var ret = dateCheck(searchType);
 			if (ret < 0) {
				alert("일별 또는 월별 선택 후 기간을 입력하여 조회해 주세요.");
				return;
 			} else if (ret == 0) {
 				alert("시작시점은 종료시점보다 앞서야 합니다. 기간을 다시 설정한 후 조회해 주세요.");
 				return;
 			} else if(ret == 1) {
 				alert("조회는 31일 이내만 가능합니다. 기간을 다시 설정한 후 조회해 주세요.");
 				return;
 			}
 			
 			var buildingName = $('input:text[name="buildingName"]').val();
			if (buildingName == "") { 
				alert("조회하고자 하는 건물/아파트명을 팝업에서 먼저 선택 후 조회해 주세요.");
				return;
			}
			
			var buildingDetail = $('select[name="buildingDetail"]').val();
			if (buildingDetail < 0) { 
				alert("조회하고자 하는 동/호수를 선택 후 조회해 주세요.");
				return;
			}
			
			var selBdId = document.getElementById("bdSelect");
			if (selBdId.options[selBdId.selectedIndex].value == '0') {
				alert("상세/동명 및 충전그룹을 선택하세요.");
				return;
			}
			
			saveSelected();
			$("#vForm").submit();
		});
	});

	// 검색 조건에 따라 검색 UI 리셋
	function searchChange() {
		
		var searchType = $('input:radio[name="searchType"]:checked').val();
		
		if (searchType == "daily") {
			$("#fromDateByDaily").datepicker();
			$("#toDateByDaily").datepicker();
			
			$("#searchDaily").show();
			$("#searchMonth").hide();
			
			$("#explain").show();
		} else if (searchType == "monthly") {
			$("#fromDateByMonth").monthpicker(month_options);
			$("#toDateByMonth").monthpicker(month_options);
			
			$("#searchDaily").hide();
			$("#searchMonth").show();
			
			$("#explain").hide();
		} else {
			$("#searchDaily").hide();
			$("#searchMonth").hide();
			
			$("#explain").hide();
		}
	}
	
	function setChildValue(name, id) {
		$('#bdSelect').find("option").remove();
		$('#bdSelect').append("<option value='0'>상세/동명 선택</option>");
		$('#bdGroup').val(name);
		$('#bdGroupId').val(id);
		setBdSelect();
	}
	
	function setBdSelect() {
		$.ajax({
			type:'POST',
			url:'/elcg/controller/setBdSelect.json',
			data:{
				bdGroupId:$("#bdGroupId").val()			
			},
			success:function (data) {					
					 if(data.length > 0) {
						for(var i=0; i<data.length; i++) {
							$('#bdSelect').append("<option value='" + data[i].bdId + "'>" + data[i].name + "</option>");
						}
					}
				},
			error: function(e) {
				alert("error!!!");
			}
		});
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
		if (!toDate || toDate == "")     return -2;
		
		var fromArray = fromDate.split("-");
		var toArray = toDate.split("-");
		
		if (searchType == "monthly") {
			fromArray[2] = "01", toArray[2] = "01";
		}
		
		var fDate = new Date(fromArray[0], fromArray[1]-1, fromArray[2]);
		var tDate = new Date(toArray[0], toArray[1]-1, toArray[2]);
		
		if (fDate > tDate) return 0;
		
		if (searchType == "daily" && ((tDate.getTime() - fDate.getTime()) / 1000 / 60 / 60 / 24) > 31) {
			return 1;
		}
		
		fromDate = fromArray[0] + "-" + fromArray[1];
		toDate   = toArray[0] + "-" + toArray[1];
		
		if (searchType == "daily") {
			fromDate += "-" + fromArray[2];
			toDate += "-" + toArray[2];
		}
		
		$("#fromDate").val(fromDate);
		$("#toDate").val(toDate);
		
		return 2;
	}
	function saveSelected() {
		var targetBd = document.getElementById("bdSelect");
		document.getElementById("bdText").value = targetBd.options[targetBd.selectedIndex].text;
		document.getElementById("bdSelect").value = targetBd.options[targetBd.selectedIndex].value;
	}
</script>
<form method="get" id="vForm" name="vForm" action="/statistics/stat/building.htm">
	<div class="wrap00">
	<!-- search _ start -->
	<input type="hidden" id="fromDate" name="fromDate" value="${fromDate}"/>
	<input type="hidden" id="toDate" name="toDate" value="${toDate}"/>
	<input type="hidden" id="bdGroupId" name="bdGroupId" value="${selBdGroupId}"/>
	<input type="hidden" id="bdText" name="bdText"/>
	<input type="hidden" id="searchBdgId" name="searchBdgId" value="${searchBdGroupId}"/>
 	
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<!-- 일별 / 월별 -->
				<td class="td00">
					<input type="radio" name="searchType" value="daily" onclick="searchChange();"<c:if test="${searchType == 'daily'}">checked</c:if>>     일별 &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="searchType" value="monthly" onclick="searchChange();"<c:if test="${searchType == 'monthly'}">checked</c:if>> 월별
				</td>
				<!-- 일별 날짜 검색 -->
				<td id="searchDaily" align="left">
					<input id="fromDateByDaily" value="" style="width: 75px"/>~
					<input id="toDateByDaily" value="" style="width: 75px"/>
				</td>
				<!-- 월별 날짜 검색 -->
				<td id="searchMonth" align="left">
					<input id="fromDateByMonth" value="" style="width: 75px"/>~
					<input id="toDateByMonth" value="" style="width: 75px"/>
				</td>
				<!-- 사용자 검색 -->
				<td align="left">
					<c:set var="bdGroup" value='<%=request.getParameter("bdGroup")%>' />
					<input type="text" id="bdGroup" name="bdGroup" value="${bdGroup}" placeholder="<fmt:message key="label.elcg.buildingName"/>" readonly/>
					<c:set var="bdText" value='<%=request.getParameter("bdText")%>' />
					<c:set var="bdSelect" value='<%=request.getParameter("bdSelect")%>' />
					<select id="bdSelect" name="bdSelect">
						<c:choose>
							<c:when test="${bdGroup eq '' || bdGroup eq null}">
								<option value='0'>상세/동명 선택</option>
							</c:when>
							<c:otherwise>
								<c:if test="${ fn:length(selBdList) > 0 }">
									<c:forEach items="${selBdList}" var="selBd" varStatus="status">
										<option <c:if test="${selBd.bdId == bdSelect}">selected</c:if> value="${selBd.bdId}">${selBd.name}</option>
									</c:forEach>
								</c:if>
							</c:otherwise>
						</c:choose>
					</select>
					<input type="button" id="searchBtn" value='<fmt:message key="label.common.search"/>'/>
				</td>
			</tr>
		</table>
	</fieldset>
	<br/>
	<div id="explain">* 일별 검색은 31일 이내의 기간만 가능합니다.</div>
	<!-- search _ end -->

	<!-- list _ start -->
	<c:choose>
	<c:when test="${searchType == 'daily'}">
		<c:set var="row" value="${ rownum-((page-1)*10) }"/>
		<display:table name="buildingStat" id="buildingStat" class="simple tbl_type2" style="margin:5px 0pt;" requestURI="/statistics/stat/building.htm" pagesize="10" export="false">
			<display:column titleKey="label.statistics.stat.no">
				<c:out value="${row}"/>
				<c:set var="row" value="${row-1}"/>	
			</display:column>
			<display:column titleKey="label.statistics.stat.statDate" media="html">
				<fmt:parseDate var="strDate" value="${buildingStat.CHARGE_YMD}" pattern="yyyyMMdd" />
				<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
			</display:column>
			<display:column titleKey="label.statistics.stat.buildingName" property="BD_GROUP_NAME"/>
			<display:column titleKey="label.statistics.stat.buildingDetail" property="BD_NAME"/>
			<display:column titleKey="label.statistics.stat.totalChargeTime" property="DIFF"/>
			<display:column titleKey="label.statistics.stat.chargeCount" style="text-align: right;padding-right:10px;">
				<fmt:formatNumber value="${buildingStat.CHARGE_CNT}" pattern="#,###"/>
			</display:column>
			<display:column titleKey="label.statistics.stat.totalAmt" style="text-align: right;padding-right:10px;">
				<fmt:formatNumber value="${buildingStat.CHARGE_AMT/1000}" pattern="#,###.#"/>
			</display:column>
		</display:table>
	</c:when>
	<c:when test="${searchType == 'monthly'}">
		<c:set var="row" value="${ rownum-((page-1)*10) }"/>
		<display:table name="buildingStat" id="buildingStat" class="simple tbl_type2" style="margin:5px 0pt;" requestURI="/statistics/stat/building.htm" pagesize="10" export="false">
			<display:column titleKey="label.statistics.stat.no">
				<c:out value="${row}"/>
				<c:set var="row" value="${row-1}"/>	
			</display:column>
			<display:column titleKey="label.statistics.stat.statDate">
				<fmt:parseDate var="strDate" value="${buildingStat.CHARGE_YM}" pattern="yyyyMM" />
				<fmt:formatDate value="${strDate}" pattern="yyyy-MM" />
			</display:column>
			<display:column titleKey="label.statistics.stat.buildingName" property="BD_GROUP_NAME"/>
			<display:column titleKey="label.statistics.stat.buildingDetail" property="BD_NAME"/>
			<display:column titleKey="label.statistics.stat.totalChargeTime" property="DIFF"/>
			<display:column titleKey="label.statistics.stat.chargeCount" style="text-align: right;padding-right:10px;">
				<fmt:formatNumber value="${buildingStat.CHARGE_CNT}" pattern="#,###"/>
			</display:column>
			<display:column titleKey="label.statistics.stat.totalAmt" style="text-align: right;padding-right:10px;">
				<fmt:formatNumber value="${buildingStat.CHARGE_AMT/1000}" pattern="#,###.#"/>
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