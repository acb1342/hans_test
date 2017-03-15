<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<link href="/js/jquery/ui/month/MonthPicker.min.css" rel="stylesheet" type="text/css" />
<link href="/js/jquery/ui/month/examples.css" rel="stylesheet" type="text/css" />
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="/js/jquery/ui/month/MonthPicker.min.js"></script>
<script type="text/javascript" src="/js/jquery/ui/month/jquery.mtz.monthpicker.js"></script>

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
			$('#ALLdiv').hide();
			$('#DAYdiv').hide();
			$('#BDdiv').hide();
			$('#footerDiv').hide();
		} else $('#footerDiv').show();
		
		searchChange();
		
		$('#bdGroup').click(function(e) {
			window.open("/elcg/building/popup.htm","new","width=448,height=448,top=100,left=100");
		});
		
		$('#searchAllType').click(function(e) {
			if($('input[name="status"]:checked').val() == "BD") {
				var selBdId = document.getElementById("bdSelect");
				
				if (selBdId.options[selBdId.selectedIndex].value == '0') {
					alert("상세/동명을 선택하세요.");
					return;
				}
				searchByGroup();
			}
			else if($('input[name="status"]:checked').val() == "DAY") {
	 			var ret = dateCheck();
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
			}
 			
 			$('#vForm').submit();	
		});
		
	});

	function setChildValue(name, id) {
		$('#bdSelect').find("option").remove();
		$('#bdSelect').append("<option value='0'>상세/동명 선택</option>");
		document.getElementById("bdGroup").value = name;
		document.getElementById("bdGroupId").value = id;
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
	
	// 건물별 검색
	function searchByGroup() {
		saveSelected();
		$('#vForm').submit();		
	}
	
	// 생성 페이지로 이동
	function insert() {
		document.location = "/elcg/charger/create.htm";
	}

	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/elcg/charger/delete.json', function() { 
			document.location = "/elcg/charger/search.htm";
		});
	}
	
	function searchChange() {
		if( $('input[name="status"]:checked').val() == "DAY" ) {
			
			$("#fromDate").datepicker();
			$("#toDate").datepicker();
			
			if ($("#fromDate").val() == "") {
				$("#fromDate").val($.datepicker.formatDate("yy-mm-dd", new Date()));
			}
			if ($("#toDate").val() == "") {
				$("#toDate").val($.datepicker.formatDate("yy-mm-dd", new Date()));
			}

			$("#bdSelect").val("");
			$("#bdGroupId").val("");
			
			$('#DAYdiv').show();
			$('#BDdiv').hide();
			$('#ALLdiv').show();
		} 
		else if( $('input[name="status"]:checked').val() == "BD" ) {	
			$("#fromDate").val("");
			$("#toDate").val("");
			
			$('#DAYdiv').hide();
			$('#BDdiv').show();
			$('#ALLdiv').show();
		}
		else if( $('input[name="status"]:checked').val() == "ALL" ) {
			
			$("#fromDate").val("");
			$("#toDate").val("");
			
			$("#bdSelect").val("");
			$("#bdGroupId").val("");
			
			$('#DAYdiv').hide();
			$('#BDdiv').hide();
			$('#ALLdiv').show();
		}
	}
	
	// select 선택한 항목 유지
	function saveSelected() {
		var targetBd = document.getElementById("bdSelect");
		document.getElementById("bdText").value = targetBd.options[targetBd.selectedIndex].text;
	}

	function dateCheck() {
		
		var fromDate = "", toDate = "";
		
		fromDate = $("#fromDate").val();
		toDate = $("#toDate").val();
		
		if (!fromDate || fromDate == "") return -1;
		if (!toDate || toDate == "")     return -2;
		
		var fromArray = fromDate.split("-");
		var toArray = toDate.split("-");
		
		var fDate = new Date(fromArray[0], fromArray[1]-1, fromArray[2]);
		var tDate = new Date(toArray[0], toArray[1]-1, toArray[2]);
		
		if (fDate > tDate) return 0;
		var diffDate = ((tDate.getTime() - fDate.getTime()) / 1000 / 60 / 60 / 24);
		if (diffDate > 31) {
			return 1;
		}
		
		fromDate = fromArray[0] + "-" + fromArray[1] + "-" + fromArray[2];
		toDate   = toArray[0] + "-" + toArray[1] + "-" + toArray[2];
		
		$("#fromDate").val(fromDate);
		$("#toDate").val(toDate);
		
		return 2;
	}
	
</script>

<form method="get" id="vForm" name="vForm" action="/elcg/charger/search.htm">
	<!-- search _ start -->
	<input type="hidden" name="id" id="id"/>
	<input type="hidden" id="bdGroupId" name="bdGroupId" value="${selBdGroupId}"/>
	<c:set var="fromDate" value='<%=request.getParameter("fromDate")%>' />
	<c:set var="toDate" value='<%=request.getParameter("toDate")%>' />
	<div class="wrap00">
	<!-- search _ start -->
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<td style="width: 200px">
					<input type="radio" id="status" name="status" value="ALL" <c:if test="${type eq 'ALL'}">checked</c:if> onclick="javascript:searchChange()"> <fmt:message key="label.common.all"/>&nbsp;&nbsp;
					<c:if test="${loginGroup.id != 2 }">
					<input type="radio" id="status" name="status" value="DAY" <c:if test="${type eq 'DAY'}">checked</c:if> onclick="javascript:searchChange()"><fmt:message key="label.elcg.searchByDay"/>&nbsp;&nbsp;
					</c:if>
					<input type="radio" id="status" name="status" value="BD" <c:if test="${type eq 'BD'}">checked</c:if> onclick="javascript:searchChange()"><fmt:message key="label.elcg.searchByBd"/>
				</td>
				<td>
				<div id="DAYdiv">
					<input id="fromDate" name="fromDate" value="${fromDate}" style="width: 75px"/> ~
					<input id="toDate" name="toDate" value="${toDate}" style="width: 75px"/>
				</div>
				<div id="BDdiv">
					<c:set var="bdGroup" value='<%=request.getParameter("bdGroup")%>' />
					<input type="text" id="bdGroup" name="bdGroup" value="${bdGroup}" placeholder="<fmt:message key="label.elcg.buildingName"/>" />
					
					<input type="hidden" id="bdText" name="bdText"/>
					<c:set var="bdText" value='<%=request.getParameter("bdText")%>' />
					<c:set var="bdSelect" value='<%=request.getParameter("bdSelect")%>' />
					<select id="bdSelect" name="bdSelect">
						<c:choose>
							<c:when test="${bdGroup eq '' || bdGroup eq null}">
								<option value="">상세/동명 선택</option>
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
				</div>
			</td>
			<td>
				<div id="ALLdiv">
					<input type="button" id="searchAllType" value="조회"/>
				</div>
			</td>	
			</tr>
		</table>
	</fieldset><br/>
	<!-- search _ end -->
	
	<!-- list _ start -->
	<c:choose>
	<c:when test="${type eq 'default'}">
		<table style="width:100%">
			<tr>
				<td height="25" class="td01"><fmt:message key="label.elcg.installedChargerCount"/></td>
				<td class="td02">${totalCount}</td>
			</tr>
		</table>
	</c:when>
	<c:otherwise>
	<c:set var="row" value="${ rownum-((page-1)*10) }"/>
	<display:table name="chargerList" id="charger" class="simple" style="margin:5px 0pt;" requestURI="/elcg/charger/search.htm" pagesize="10" export="false">
		<c:if test="${authority.delete}">
			<display:column titleKey="label.common.select" style="width:40px;" media="html">
				<input type="checkbox" id="selected" name="selected" style="width:15px;" value="${charger.chargerId}"/>
			</display:column>
		</c:if>
		<display:column titleKey="label.common.seq" >
			<c:out value="${row}"/>
			<c:set var="row" value="${row-1}"/>
		</display:column>
		<display:column titleKey="label.elcg.mgmtNo" property="mgmtNo"/>
		<display:column titleKey="label.elcg.installer" media="html">
			${charger.wkName}
			<c:if test="${ empty charger.wkName}">${charger.fstRgUsid}</c:if>
		</display:column>
		<display:column titleKey="label.elcg.insDate" >
			<fmt:formatDate value="${charger.fstRgDt}" pattern="yyyy/MM/dd HH:mm"/>
		</display:column>
		<display:column titleKey="label.elcg.buildingName" property="chargerGroup.bd.bdGroup.name"/>
		<display:column titleKey="label.elcg.detailName" property="chargerGroup.bd.name"/>
		<display:column titleKey="label.elcg.chargerGroupName" property="chargerGroup.name"/>
		<display:column titleKey="label.elcg.status">
			<c:if test="${ !empty charger.status }"><fmt:message key="${charger.status}"/></c:if>
		</display:column>
		<display:column titleKey="label.common.detail" style="text-align:center; width:140px;" media="html">
			<input type="button" value='<fmt:message key="label.common.detail"/>' onclick="location.href='/elcg/charger/detail.htm?id=${agent.id}&seq=${charger.chargerId}'"/>
		<c:if test="${authority.update}">
			<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/elcg/charger/update.htm?id=${agent.id}&seq=${charger.chargerId}&bdId=${charger.chargerGroup.bd.bdId}'"/>
		</c:if>
		</display:column>
	</display:table>
	</c:otherwise>
	</c:choose>
	<!-- list _ end -->
	
	<!-- button _ start -->
	<div class="footer" id="footerDiv">
		<table style="width:100%">
			<tr>
				<td height="30" align="right">
				<c:if test="${authority.create}">
					<input type="button" value='<fmt:message key="label.common.add"/>' onclick="javascript:insert()"/>
				</c:if>
				<c:if test="${authority.delete}">
					<input type="button" class="btn_red" value='<fmt:message key="label.common.delete"/>' onclick="javascript:confirmAndDelete()"/>
				</c:if>
				</td>
			</tr>
		</table>
	</div>
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>