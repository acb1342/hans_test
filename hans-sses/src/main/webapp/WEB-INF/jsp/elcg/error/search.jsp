<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<link href="/js/jquery/ui/month/MonthPicker.min.css" rel="stylesheet" type="text/css" />
<link href="/js/jquery/ui/month/examples.css" rel="stylesheet" type="text/css" />
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="/js/jquery/ui/month/MonthPicker.min.js"></script>
<script type="text/javascript" src="/js/jquery/ui/month/jquery.mtz.monthpicker.js"></script>

<c:set var="status" value="${type}" />
<c:set var="fromDate" value='<%=request.getParameter("fromDate")%>' />
<c:set var="toDate" value='<%=request.getParameter("toDate")%>' />

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

	$(function () {
		if(!Boolean($('input[name="status"]:checked').val())) {
			$('#CGdiv').hide();
			$('#Errdiv').hide();
			$('#Alldiv').hide();
		}
		searchChange();
		
		$('#bdGroup').click(function(e) {
			window.open("/elcg/building/popup.htm","new","width=448,height=448,top=100,left=100");
		});

		$("#fromDate").val("${fromDate}");
		$("#toDate").val("${toDate}");
		
		if ($("#fromDate").val() == "") {
			$("#fromDate").val($.datepicker.formatDate("yy-mm-dd", new Date()));
		}
		if ($("#toDate").val() == "") {
			$("#toDate").val($.datepicker.formatDate("yy-mm-dd", new Date()));
		}
		
		$('#fromDate').datepicker();
		$('#toDate').datepicker();
		
		searchChange();
	});
	
	function setChildValue(name, id) {
		$('#bdSelect').find("option").remove();
		$('#bdSelect').append("<option value='0'>상세/동명 선택</option>");
		document.getElementById("bdGroup").value = name;
		document.getElementById("bdGroupId").value = id;
		setBdSelect();
	}
	
	// 충전그룹별 검색
	function searchByGroup() {
		var from = $('#fromDate').val().replaceAll('/','').replaceAll(':','').replace(/ /gi,'').substring(0,12);
		var to = $('#toDate').val().replaceAll('/','').replaceAll(':','').replace(/ /gi,'').substring(0,12);
		if (from > to) {
			alert("검색 날짜를 확인해주세요.");
			return;
		}
		
		var selBdId = document.getElementById("bdSelect");
		var selCgId = document.getElementById("chargerGroupSelect");
		var selCharger = document.getElementById("setChargerMgmtNoSelect");
		var selType = $('input:radio[name="status"]:checked').val();
		if (selType == 'CG') {
			if (selBdId.options[selBdId.selectedIndex].value == '0'
					|| selCgId.options[selCgId.selectedIndex].value == '0'
					|| selCharger.options[selCharger.selectedIndex].value == '0') {
				alert("상세/동명과 충전그룹 및 충전기 관리번호를 선택하세요.");
				return;
			}
		}
		
		$('#vForm').submit();
	}
	
	function searchByType() {
		var a = $("#fromDate").val($.datepicker.formatDate("yy-mm-dd", new Date()));
		alert(a);
		$('#vForm').submit();
	}
	
	function searchChange() {	
		if( $('input[name="status"]:checked').val() == "CG" ) {
			$('#Alldiv').hide();
			$('#CGdiv').show();
			$('#Errdiv').hide();
			$('#Alldiv').show();
		} 
		else if( $('input[name="status"]:checked').val() == "ERR") {	
			$('#Alldiv').hide();
			$('#CGdiv').hide();
			$('#Errdiv').show();
			$('#Alldiv').show();
		}
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
	
	
	function setChargerGroupSelect() {
		$('#chargerGroupSelect').find("option").remove();
		$('#chargerGroupSelect').append("<option value='0'>충전그룹 선택</option>");
		$.ajax({
			type:'POST',
			url:'/elcg/building/setChargerGroupSelect.json',
			data:{
				bdSelect:$("#bdSelect").val()					
			},
			success:function (data) {
					if(data.length > 0) {
						for(var i=0; i<data.length; i++) {
							$('#chargerGroupSelect').append("<option value='" + data[i].chargerGroupId + "'>" + data[i].name + "</option>");
						}
					}
				},
			error: function(e) {
				alert("error!!!");
			}
		});
	}
	
	function setMgmtNoSelect() {
		$('#setChargerMgmtNoSelect').find("option").remove();
		$('#setChargerMgmtNoSelect').append("<option value='0'>충전기 관리번호</option>");
		$.ajax({
			type:'POST',
			url:'/elcg/building/setChargerMgmtNoSelect.json',
			data:{
				chargerGroupSelect:$("#chargerGroupSelect").val()					
			},
			success:function (data) {
					if(data.length > 0) {
						for(var i=0; i<data.length; i++) {
							$('#setChargerMgmtNoSelect').append("<option value='" + data[i].mgmtNo + "'>" + data[i].mgmtNo + "</option>");
						}
						
					}
				},
			error: function(e) {
				alert("error!!!");
			}
		});
	}
	
	function chargerInfo(param) {
		window.open("/elcg/error/chargerInfoPopup.htm?paramId="+param,"new","width=448,height=448,top=100,left=100");
	}
	
</script>

<form method="get" id="vForm" name="vForm" action="/elcg/error/search.htm">
	<div class="wrap00">
	<!-- search _ start -->
	<input type="hidden" id="bdGroupId" name="bdGroupId" value="${selBdGroupId}"/>
	<input type="hidden" id="selBdId" name="selBdId" value="${bdSelect}"/>
	<fieldset class="searchBox">
		<table class="searchTbl" style="margin:5px 5px 5px 20px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<td style="width: 150px;">
					<input type="radio" id="status" name="status" value="ERR" <c:if test="${type == 'ERR'}">checked</c:if> onclick="javascript:searchChange()"> 이벤트별&nbsp;&nbsp;
					<input type="radio" id="status" name="status" value="CG" <c:if test="${type == 'CG'}">checked</c:if> onclick="javascript:searchChange()"> 충전기별
				</td>
				<td>
				<span id="CGdiv">
					<c:set var="bdGroup" value='<%=request.getParameter("bdGroup")%>' />
					<input type="text" id="bdGroup" name="bdGroup" style="width: 130px;" value="${bdGroup}" placeholder="<fmt:message key="label.elcg.buildingName"/>" />
					
					<input type="hidden" id="bdText" name="bdText"/>
					<c:set var="bdText" value='<%=request.getParameter("bdText")%>' />
					<c:set var="bdSelect" value='<%=request.getParameter("bdSelect")%>' />
					<select id="bdSelect" name="bdSelect" style="width: 130px" onchange="javascript:setChargerGroupSelect()">
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
					
					<input type="hidden" id="groupText" name="groupText"/>
					<c:set var="groupText" value='<%=request.getParameter("groupText")%>' />
					<c:set var="chargerGroupSelect" value='<%=request.getParameter("chargerGroupSelect")%>' />
					<select id="chargerGroupSelect" name="chargerGroupSelect" style="width: 130px" onchange="javascript:setMgmtNoSelect()">
						<c:choose>
							<c:when test="${bdSelect eq '' || bdSelect eq null}">
								<option value='0'>충전그룹 선택</option>
							</c:when>
							<c:otherwise>
								<c:if test="${ fn:length(selCgList) > 0 }">
									<c:forEach items="${selCgList}" var="selCg" varStatus="status">
										<option <c:if test="${selCg.chargerGroupId == chargerGroupSelect}">selected</c:if> value="${selCg.chargerGroupId}">${selCg.name}</option>
									</c:forEach>
								</c:if>
							</c:otherwise>
						</c:choose>
					</select>
					
					<input type="hidden" id="mgmtNoText" name="mgmtNoText"/>
					<c:set var="mgmtNoText" value='<%=request.getParameter("mgmtNoText")%>' />
					<c:set var="setChargerMgmtNoSelect" value='<%=request.getParameter("setChargerMgmtNoSelect")%>' />
					<select id="setChargerMgmtNoSelect" name="setChargerMgmtNoSelect" style="width: 130px">
						<c:choose>
							<c:when test="${chargerGroupSelect eq '' || chargerGroupSelect eq null}">
								<option value='0'>충전기 관리번호</option>
							</c:when>
							<c:otherwise>
								<c:if test="${ fn:length(selChargerList) > 0 }">
									<c:forEach items="${selChargerList}" var="selCharger" varStatus="status">
										<option <c:if test="${selCharger.chargerId == setChargerMgmtNoSelect}">selected</c:if> value="${selCharger.chargerId}">${selCharger.mgmtNo}</option>
									</c:forEach>
								</c:if>
							</c:otherwise>
						</c:choose>
					</select>
				</span>
				
				<span id="Errdiv">
				<c:set var="errorType" value='<%=request.getParameter("errorType")%>'/>
				<select name="errorType" id="errorType" style="width: 130px">
					<c:choose>
						<c:when test="${loginGroup.id == 1}"><option value="">전체</option></c:when>
						<c:otherwise><option value="C00108,C00109,C0010C,C0010D">전체</option></c:otherwise>
					</c:choose>
					<option value="C00108"${errorType == 'C00108' ? ' selected' : ''}>디바이스고장</option>					
					<option value="C00109"${errorType == 'C00109' ? ' selected' : ''}>기타에러</option>					
					<option value="C0010D"${errorType == 'C0010D' ? ' selected' : ''}>통신이상</option>					
					<option value="C0010C"${errorType == 'C0010C' ? ' selected' : ''}>충전기탈거</option>					
				</select>
				</span>			
				<span id="Alldiv">
					<input id="fromDate" name="fromDate" value="${fromDate}" style="width: 75px"/> ~
					<input id="toDate" name="toDate" value="${toDate}" style="width: 75px"/>
				</span>
				</td>
				<td>
					<input type="button" id="searchGroup" value="조회" onclick="javascript:searchByGroup()" style="margin:0px 40px 0px 0px;"/>
				</td>
			</tr>
		</table>
	</fieldset><br/>
	<!-- search _ end -->
	
	<!-- list start -->
	<table style="width:100%">
		<display:table name="chargerIfList" id="chargerIf" class="simple" style="margin:5px 0pt;" requestURI="/elcg/error/search.htm" pagesize="10" export="false">
		<display:column titleKey="label.common.id" property="SN_ID"/>
		<display:column titleKey="label.elcg.occurDate">
			<fmt:parseDate var="strDate" value="${chargerIf.FST_RG_DT}" pattern="yyyy-MM-dd HH:mm:ss.S" />
			<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd HH:mm:ss" />
		</display:column>
		<display:column titleKey="label.elcg.mgmtNo">
			<a href='javascript:chargerInfo("${chargerIf.CHARGER_ID}")'>${chargerIf.MGMT_NO}</a>
		</display:column>
		<display:column titleKey="label.elcg.errorType">
		<c:choose>
			<c:when test="${chargerIf.CMD == 'C00101'}">상태</c:when> 
			<c:when test="${chargerIf.CMD == 'C00102'}">상태</c:when> 
			<c:when test="${chargerIf.CMD == 'C00103'}">인증</c:when> 
			<c:when test="${chargerIf.CMD == 'C00104'}">상태</c:when> 
			<c:when test="${chargerIf.CMD == 'C00105'}">상태</c:when> 
			<c:when test="${chargerIf.CMD == 'C00106'}">상태</c:when> 
			<c:when test="${chargerIf.CMD == 'C00107'}">상태</c:when> 
			<c:when test="${chargerIf.CMD == 'C00108'}">오류</c:when> 
			<c:when test="${chargerIf.CMD == 'C00109'}">오류</c:when> 
			<c:when test="${chargerIf.CMD == 'C0010A'}">상태</c:when> 
			<c:when test="${chargerIf.CMD == 'C0010B'}">상태</c:when> 
			<c:when test="${chargerIf.CMD == 'C0010C'}">오류</c:when> 
			<c:when test="${chargerIf.CMD == 'C0010D'}">오류</c:when>
			<c:otherwise>-</c:otherwise>
		</c:choose>
		</display:column>
		<display:column titleKey="label.elcg.errorDescription" style="text-align: left;">
		${chargerIf.CHARGER_ID};
		<c:choose>
			<c:when test="${chargerIf.CMD == 'C00100'}">충전기 대기중</c:when> 
			<c:when test="${chargerIf.CMD == 'C00101'}">차량 플러그 장착</c:when> 
			<c:when test="${chargerIf.CMD == 'C00103'}">RFID 인식; ${chargerIf.RFID}</c:when> 
			<c:when test="${chargerIf.CMD == 'C00104'}">충전 시작; ${chargerIf.RFID} ;${chargerIf.WATT}</c:when> 
			<c:when test="${chargerIf.CMD == 'C00105'}">충전 중; ${chargerIf.RFID} ;${chargerIf.WATT}</c:when> 
			<c:when test="${chargerIf.CMD == 'C00106'}">충전 완료; ${chargerIf.RFID} ;${chargerIf.WATT}</c:when> 
			<c:when test="${chargerIf.CMD == 'C00107'}">차량 플러그 탈거; ${chargerIf.RFID}</c:when> 
			<c:when test="${chargerIf.CMD == 'C00108'}">디바이스 고장</c:when> 
			<c:when test="${chargerIf.CMD == 'C00109'}">기타 에러</c:when> 
			<c:when test="${chargerIf.CMD == 'C0010A'}">충전 대기</c:when> 
			<c:when test="${chargerIf.CMD == 'C0010B'}">충전 불가</c:when> 
			<c:when test="${chargerIf.CMD == 'C0010C'}">충전기 강제 탈거</c:when> 
			<c:when test="${chargerIf.CMD == 'C0010D'}">통신이상</c:when>
			<c:otherwise>-</c:otherwise>
		</c:choose>
		</display:column>
		<c:if test="${loginGroup.id == 1}">
		<display:column titleKey="label.elcg.errorReport">
		<c:if test="${chargerIf.CMD == 'C00108' || chargerIf.CMD == 'C00109' || chargerIf.CMD == 'C0010C' || chargerIf.CMD == 'C0010D'}">
			<input type="button" value="접수하기" onclick="location.href='/elcg/applicationAndReport/create.htm?chargerId=${chargerIf.CHARGER_ID}&from=error'"/>
		</c:if>
		</display:column>
		</c:if>
		</display:table>
	</table>
	
	<div class="footer">
		<table style="width:100%">
			<tr>
				<td height="30" align="right">
					<c:if test="${type eq 'C' }">
					<input type="button" value='<fmt:message key="label.elcg.restart"/>' onclick=""/>
					</c:if>
				</td>
			</tr>
		</table>
	</div>
	
	</div>
</form>
<%@ include file="/include/footer.jspf" %>