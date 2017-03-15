<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<link href="/js/jquery/ui/month/MonthPicker.min.css" rel="stylesheet" type="text/css" />
<link href="/js/jquery/ui/month/examples.css" rel="stylesheet" type="text/css" />
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="/js/jquery/ui/month/MonthPicker.min.js"></script>
<script type="text/javascript" src="/js/jquery/ui/month/jquery.mtz.monthpicker.js"></script>

<c:set var="searchType" value='<%=request.getParameter("searchType")%>'/>
<c:set var="buildingCode" value='<%=request.getParameter("buildingCode")%>'/>
<c:set var="buildingName" value='<%=request.getParameter("buildingName")%>'/>
<c:set var="buildingDetail" value='<%=request.getParameter("buildingDetail")%>'/>
<c:set var="paymentPeriod" value='<%=request.getParameter("paymentPeriod")%>'/>
<c:set var="fromDate" value='<%=request.getParameter("fromDate")%>'/>
<c:set var="toDate" value='<%=request.getParameter("toDate")%>'/>

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
		if ("paymentPeriod" == "${searchType}") {
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
				alert("건물별 또는 납기일별 선택 후 조회해 주세요.");
				return;
			}
			
			if (searchType == "building") {
				var buildingName = $('input:text[name="buildingName"]').val();
				var buildingDetail = $('select[name="buildingDetail"]').val();
				
				if (buildingName == "" || buildingDetail < 0) { 
					alert("건물/아파트명, 상세/동명을 모두 선택 후 조회해 주세요.");
					return;
				}
			}
			
			if (searchType == "paymentPeriod") {
				var paymentPeriod = $('select[name="paymentPeriod"]').val();
				if(paymentPeriod < 0) {
					alert("납기일 선택 후 조회해 주세요.");
					return;
				}
				
				var ret = dateCheck();
	 			if (ret < 0) {
					alert("기간을 입력하여 조회해 주세요.");
					return;
	 			} else if (ret == 0) {
	 				alert("시작시점은 종료시점보다 앞서야 합니다. 기간을 다시 설정한 후 조회해 주세요.");
	 				return;
	 			}
			}
			
			var selBdId = document.getElementById("bdSelect");
			var selType = $('input:radio[name="searchType"]:checked').val();
			if (selType == 'building') {
				if (selBdId.options[selBdId.selectedIndex].value == '0') {
					alert("상세/동명 및 충전그룹을 선택하세요.");
					return;
				}
			}
			
			if(!saveSelected()) return ;
	        $("#vForm").attr("action","/adjust/kepco.htm");
			$("#vForm").submit();
		});
		
		$("#excelBtn").click(function() {
	        $("#vForm").attr("action","/adjust/kepcoExcel.json");
	        $("#vForm").submit();
		});
	});

	// 검색 조건에 따라 검색 UI 리셋
	function searchChange() {
		
		var searchType = $('input:radio[name="searchType"]:checked').val();
		
		if (searchType == "building") {
			$("#fromDateByDaily").datepicker();
			$("#toDateByDaily").datepicker();
			
			$("#searchBuilding").show();
			$("#searchPaymentPeriod").hide();
		} else if (searchType == "paymentPeriod") {
			$("#fromDateByMonth").monthpicker(month_options);
			$("#toDateByMonth").monthpicker(month_options);
			
			$("#searchBuilding").hide();
			$("#searchPaymentPeriod").show();
		} else {
			$("#searchBuilding").hide();
			$("#searchPaymentPeriod").hide();
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
			url:'/elcg/building/setBdSelect.json',
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
	
	// 검색 값 유지
	function saveSelected() {
		var targetBd = document.getElementById("bdSelect");
		document.getElementById("bdText").value = targetBd.options[targetBd.selectedIndex].text;
		document.getElementById("bdSelect").value = targetBd.options[targetBd.selectedIndex].value;
		
		return true;
	}
	
	function dateCheck() {
		
		var fromDate = "", toDate = "";
		
		fromDate = $("#fromDateByMonth").val();
		toDate = $("#toDateByMonth").val();
		
		if (!fromDate || fromDate == "") return -1;
		if (!toDate || toDate == "")     return -2;
		
		var fromArray = fromDate.split("-");
		var toArray = toDate.split("-");
		
		fromArray[2] = "01", toArray[2] = "01";
		
		var fDate = new Date(fromArray[0], fromArray[1]-1, fromArray[2]);
		var tDate = new Date(toArray[0], toArray[1]-1, toArray[2]);
		
		if (fDate > tDate) return 0;
		
		fromDate = fromArray[0] + "-" + fromArray[1];
		toDate   = toArray[0] + "-" + toArray[1];
		
		$("#fromDate").val(fromDate);
		$("#toDate").val(toDate);
		
		return 2;
	}
</script>
<form method="get" id="vForm" name="vForm" action="/adjust/kepco.htm">
	<div class="wrap00">
	<!-- search _ start -->
	<input type="hidden" id="fromDate" name="fromDate" value="${fromDate}"/>
	<input type="hidden" id="toDate" name="toDate" value="${toDate}"/>
	<input type="hidden" id="bdGroupId" name="bdGroupId" value="${selBdGroupId}"/>
	<input type="hidden" id="bdText" name="bdText"/>
	
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<!-- 건물별 / 납기일별 -->
				<td align="left">
					<input type="radio" name="searchType" value="building" onclick="searchChange();"<c:if test="${searchType == 'building'}">checked</c:if>>     건물별 &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="searchType" value="paymentPeriod" onclick="searchChange();"<c:if test="${searchType == 'paymentPeriod'}">checked</c:if>> 납기일별
				</td>
				<!-- 건물별 검색 -->
				<td id="searchBuilding" align="left">
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
				</td>
				<!-- 납기일별 검색 -->
				<td id="searchPaymentPeriod" align="left">
					<select name="paymentPeriod" id="paymentPeriod" style="width:150px;">
						<option value="-1" <c:if test="${paymentPeriod == '-1'}">selected</c:if>>선택</option>
						<option value="5"  <c:if test="${paymentPeriod == '5'}">selected</c:if>>5일</option>
						<option value="10" <c:if test="${paymentPeriod == '10'}">selected</c:if>>10일</option>
						<option value="15" <c:if test="${paymentPeriod == '15'}">selected</c:if>>15일</option>
						<option value="18" <c:if test="${paymentPeriod == '18'}">selected</c:if>>18일</option>
						<option value="20" <c:if test="${paymentPeriod == '20'}">selected</c:if>>20일</option>
						<option value="25" <c:if test="${paymentPeriod == '25'}">selected</c:if>>25일</option>
						<option value="31" <c:if test="${paymentPeriod == '31'}">selected</c:if>>말일</option>
					</select>
					<input id="fromDateByMonth" value="" style="width: 75px"/> ~ <input id="toDateByMonth" value="" style="width: 75px"/>
				</td>
				</td>
				<!-- 사용자 검색 -->
				<td align="left">
					<input type="button" id="searchBtn" value='<fmt:message key="label.common.search"/>'/>
					<c:if test="${fn:length(kepcoList.list) > 0}">
					<input type="button" id="excelBtn" value='엑셀다운로드'/>
					</c:if>
				</td>
			</tr>
		</table>
	</fieldset>
	<!-- search _ end -->

	<!-- list _ start -->
	<c:choose>
	<c:when test="${searchType == 'building'}">
		<c:set var="row" value="${ rownum-((page-1)*10) }"/>
		<display:table name="kepcoList" id="kepcoList" class="simple" style="margin:5px 0pt;" requestURI="/adjust/kepco.htm" pagesize="10" export="false">
			<display:column titleKey="label.adjust.no">
				<c:out value="${row}"/>
				<c:set var="row" value="${row-1}"/>	
			</display:column>
			<display:column titleKey="label.adjust.period">
				${kepcoList.START_YMD} ~ ${kepcoList.END_YMD}
			</display:column>
			<display:column titleKey="label.adjust.buildingName" property="BD_GROUP_NAME"/>
			<display:column titleKey="label.adjust.buildingDetail" property="BD_NAME"/>
			<display:column titleKey="label.adjust.paymentTerm" property="PERIOD_DAY"/>
			<display:column titleKey="label.adjust.chargerAmt" property="CHARGE_AMT"/>
			<display:column titleKey="label.adjust.usePay" property="CHARGE_FEE"/>
		</display:table>
	</c:when>
	<c:when test="${searchType == 'paymentPeriod'}">
		<c:set var="row" value="${ rownum-((page-1)*10) }"/>
		<display:table name="kepcoList" id="kepcoList" class="simple" style="margin:5px 0pt;" requestURI="/adjust/kepco.htm" pagesize="10" export="false">
			<display:column titleKey="label.adjust.no">
				<c:out value="${row}"/>
				<c:set var="row" value="${row-1}"/>	
			</display:column>
			<display:column titleKey="label.adjust.period">
				${kepcoList.START_YMD} ~ ${kepcoList.END_YMD}
			</display:column>
			<display:column titleKey="label.adjust.buildingName" property="BD_GROUP_NAME"/>
			<display:column titleKey="label.adjust.buildingDetail" property="BD_NAME"/>
			<display:column titleKey="label.adjust.address" property="ADDR"/>
			<display:column titleKey="label.adjust.paymentTerm" property="PERIOD_DAY"/>
			<display:column titleKey="label.adjust.chargerAmt" property="CHARGE_AMT"/>
			<display:column titleKey="label.adjust.usePay" property="CHARGE_FEE"/>
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