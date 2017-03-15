<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroup" %>
<%@ include file="/include/header.jspf" %>

<script type="text/javascript">
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

	var passwordUpdated = false;
	
	$(function() {
		$("#inputApplyStartDate").datepicker();
		
		$("#inputApplyStartDate").val($.datepicker.formatDate("yy-mm-dd", new Date("${applyStartDate}")));
		$("#applyStartTime").val("${applyStartTime}");
		
		$("#delete").click(function() {
			if(!$("input:checkbox[name='check']").is(":checked")) {
				alert("선택된 요금제가 없습니다.");
 				return;
			}
			
			if(confirm("선택된 요금제를 삭제 하시겠습니까?")) {
				$("input:checkbox[name=check]").each(function(){
					if($(this).is(":checked")){
						//행삭제
						$(this).parent().parent().remove();						
					}
				});
			}
		});
		
		$("#rowadd").click(function() {
			//id값은 초기화 하지 않음...추후 필요시 id값을 리셋하는 로직을 구현해야함
			var idx = $("input:checkbox[name=snId]").length;
			var row = 
				"<tr>" + 
					"<td height='25' class='td02' align='center'>" + 
						"<input type='checkbox' id='check_'" +idx + " name='check' value='0' />" +
						"<input type='hidden' id='snId_'" +idx + " name='snId' value='0' />" +
						"<input type='hidden' id='setId_'" +idx + " name='setId' value='0' />" +
					"</td>" + 
					"<td height='25' class='td02' align='center'>" +
					"</td>" +
					"<td height='25' class='td02' align='center'>" + 
						"<input type='text' id='feeName_'" +idx + " name='feeName' value='' style='width: 100px;'/>" + 
					"</td>" + 
					"<td height='25' class='td02' align='center'>" + 
						"<input type='text' id='amtPrice_'" +idx + " name='amtPrice' value='' style='width: 100px;'/>" + 
					"</td>" + 
					"<td height='25' class='td02' align='center'>" + 
						"<input type='text' id='smApplyPeriod_'" +idx + " name='smApplyPeriod' value='' style='width: 20px;'/>월" + 
						"<input type='text' id='sdApplyPeriod_'" +idx + " name='sdApplyPeriod' value='' style='width: 20px;'/>일" + 
						" ~ " + 
						"<input type='text' id='emApplyPeriod_'" +idx + " name='emApplyPeriod' value='' style='width: 20px;'/>월" + 
						"<input type='text' id='edApplyPeriod_'" +idx + " name='edApplyPeriod' value='' style='width: 20px;'/>일" + 
					"</td>" + 
					"<td height='25' class='td02' align='center'>" + 
						"<input type='text' id='shApplyTime_'" +idx + " name='shApplyTime' value='' style='width: 20px;'/>시" + 
						"<input type='text' id='smApplyTime_'" +idx + " name='smApplyTime' value='' style='width: 20px;'/>분" + 
						" ~ " + 
						"<input type='text' id='ehApplyTime_'" +idx + " name='ehApplyTime' value='' style='width: 20px;'/>시" + 
						"<input type='text' id='empplyTime_'" +idx + " name='empplyTime' value='' style='width: 20px;'/>분" + 
					"</td>" + 
				"</tr>" + 
				"<tr class='line-dot'><td colspan='5'/></tr>";
			
			$('#input_table > tbody:last').append(row);
			
			$("#input_table > tbody > tr").each(function(idx){
				$(this).find("td").eq(1).text(idx+1);
			});
			
		});
		
		$("#update").click(function() {
			var ret = dateCheck();
 			if (ret == 0) {
 				alert("요금제의 적용 시점은 현재 이후이어야 합니다.");
 				return;
 			}
 			
 			if(confirm("변경하신 내용으로 수정 하시겠습니까?")) {
 				$("#vForm").submit();
			}
		});
		
		$("#cancel").click(function() {
			window.location.href = "/adjust/rateSystem.htm";
		});
	});
	
	function dateCheck() {
		var iasDate = $("#inputApplyStartDate").val();
		
		var iasmArray = iasDate.split("-");
		
		var iDate = new Date(iasmArray[0], iasmArray[1]-1, iasmArray[2]);
		var cDate = new Date();
		
		if (cDate >= iDate) return 0;
		
		iasDate = iasmArray[0] + "-" + iasmArray[1] + "-" + iasmArray[2];

		$("#applyStartDate").val(iasDate);
		
		return 2;
	}
</script>

<form method="post" id="vForm" name="vForm" action="/adjust/rateSystemUpdate.htm">
<input type="hidden" id="applyStartDate" name="applyStartDate" value=""/>
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="4"/></tr>
		<tr>
			<td height="25" class="td01">
				<fmt:message key="label.adjust.basePrice"/>
			</td>
			<td class="td02">
	 			<input type="text" id="basePay" name="basePay" value="<fmt:formatNumber value="${basePriceList[0].basePrice}" pattern="####" />" style="text-align: right"/> 원
			</td>
			<td height="25" class="td01">
				<fmt:message key="label.adjust.cardPay"/>
			</td>
			<td class="td02">
	 			<input type="text" id="cardPay" name="cardPay" value="<fmt:formatNumber value="${basePriceList[0].cardPrice}" pattern="####" />" style="text-align: right"/> 원
			</td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
	</table>
	<br/>
	<table id="input_table" style="width:100%">
		<colgroup>
			<col width="5%" />
			<col width="5%" />
			<col width="20%" />
			<col width="20%" />
			<col width="25%" />
			<col width="25%" />
		</colgroup>
		<thead>
			<tr class="line-top"><td colspan="6"/></tr>
			<tr>
				<td height="25" class="td01" align="center" colspan="2">
					<fmt:message key="label.adjust.timeFee"/>
				</td>
				<td height="25" class="td01" align="center">
					<fmt:message key="label.adjust.feeName"/>
				</td>
				<td height="25" class="td01" align="center">
					<fmt:message key="label.adjust.amtPrice"/>
				</td>
				<td height="25" class="td01" align="center">
					<fmt:message key="label.adjust.applyPeriod"/>
				</td>
				<td height="25" class="td01" align="center">
					<fmt:message key="label.adjust.applyTime"/>
				</td>
			</tr>
			<tr class="line-dot"><td colspan="6"/></tr>
		</thead>	
		<tbody>
			<c:choose>
				<c:when test="${rateSystemList != null && not empty rateSystemList}">
					<c:forEach items="${rateSystemList}" varStatus="loop" var="rateSystem">
						<tr>
						<td height="25" class="td02" align="center">
							<input type="checkbox" id="check_${loop.index}" name="check" value="${rateSystem.SN_ID }" />
							<input type="hidden" id="snId_${loop.index}" name="snId" value="${rateSystem.SN_ID }" />
							<input type="hidden" id="setId_${loop.index}" name="setId" value="${rateSystem.SET_ID }" />
						</td>
						<td height="25" class="td02" align="center">${loop.count }
						</td>
						<td height="25" class="td02" align="center">
							<input type="text" id="feeName_${loop.index}" name="feeName" value="${rateSystem.TITLE }" style="width: 100px;"/>
						</td>
						<td height="25" class="td02" align="center">
							<input type="text" id="amtPrice_${loop.index}" name="amtPrice" value="${rateSystem.FEE }" style="width: 100px; text-align: right"/>
						</td>
						<td height="25" class="td02" align="center">
							<input type="text" id="smApplyPeriod_${loop.index}" name="smApplyPeriod" value="${fn:split(rateSystem.START_MMDD, '.')[0]}" style="width: 20px;"/>월
							<input type="text" id="sdApplyPeriod_${loop.index}" name="sdApplyPeriod" value="${fn:split(rateSystem.START_MMDD, '.')[1]}" style="width: 20px;"/>일
							 ~ 
							<input type="text" id="emApplyPeriod_${loop.index}" name="emApplyPeriod" value="${fn:split(rateSystem.END_MMDD, '.')[0]}" style="width: 20px;"/>월
							<input type="text" id="edApplyPeriod_${loop.index}" name="edApplyPeriod" value="${fn:split(rateSystem.END_MMDD, '.')[1]}" style="width: 20px;"/>일
						</td>
						<td height="25" class="td02" align="center">
							<input type="text" id="shApplyTime_${loop.index}" name="shApplyTime" value="${fn:split(rateSystem.START_HHMI, ':')[0]}" style="width: 20px;"/>시
							<input type="text" id="smApplyTime_${loop.index}" name="smApplyTime" value="${fn:split(rateSystem.START_HHMI, ':')[1]}" style="width: 20px;"/>분
							 ~ 
							<input type="text" id="ehApplyTime_${loop.index}" name="ehApplyTime" value="${fn:split(rateSystem.END_HHMI, ':')[0]}" style="width: 20px;"/>시
							<input type="text" id="empplyTime_${loop.index}" name="empplyTime" value="${fn:split(rateSystem.END_HHMI, ':')[1]}" style="width: 20px;"/>분
						</td>
					</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td height="25" class="td02" align="center">
							<input type="checkbox" id="check_0" name="check"/>
							<input type="hidden" id="snId_0" name="snId" value="" />
							<input type="hidden" id="ssetId_0" name="setId" value="" />
						</td>
						<td height="25" class="td02" align="center">1
						</td>
						<td height="25" class="td02" align="center">
							<input type="text" id="feeName_0" name="feeName" value="" style="width: 100px;"/>
						</td>
						<td height="25" class="td02" align="center">
							<input type="text" id="amtPrice_0" name="amtPrice" value="" style="width: 100px; text-align: right"/>
						</td>
						<td height="25" class="td02" align="center">
							<input type="text" id="smApplyPeriod_0" name="smApplyPeriod" value="" style="width: 20px;"/>월
							<input type="text" id="sdApplyPeriod_0" name="sdApplyPeriod" value="" style="width: 20px;"/>일
							 ~ 
							<input type="text" id="emApplyPeriod_0" name="emApplyPeriod" value="" style="width: 20px;"/>월
							<input type="text" id="edApplyPeriod_0" name="edApplyPeriod" value="" style="width: 20px;"/>일
						</td>
						<td height="25" class="td02" align="center">
							<input type="text" id="shApplyTime_0" name="shApplyTime" value="" style="width: 20px;"/>시
							<input type="text" id="smApplyTime_0" name="smApplyTime" value="" style="width: 20px;"/>분
							 ~ 
							<input type="text" id="ehApplyTime_0" name="ehApplyTime" value="" style="width: 20px;"/>시
							<input type="text" id="empplyTime_0" name="empplyTime" value="" style="width: 20px;"/>분
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
		<tfoot>
			<tr class="line-dot"><td colspan="6"/></tr>
		</tfoot>
	</table>
	<table style="width:100%">
		<tr>
			<td height="50" class="td02" colspan="2" align="left">
				<input type="button" id="delete" value='<fmt:message key="label.adjust.delete"/>'/>
			</td>
			<td height="50" class="td02" colspan="3" align="right">
				<input type="button" id="rowadd" value='<fmt:message key="label.adjust.rowAdd"/>'/>
			</td>
		</tr>
	</table>
	<table style="width:100%">
		<tr class="line-top"><td colspan="2"/></tr>
		<tr>
			<td height="25" class="td01">
				<fmt:message key="label.adjust.applyStartDate"/>
			</td>
			<td class="td02">
	 			<input id="inputApplyStartDate" value="" style="width: 65px" readonly/>
	 			<select id="applyStartTime" name="applyStartTime" style="width: 60px">
					<option value='00:00'>00:00</option>
					<option value='01:00'>01:00</option>
					<option value='02:00'>02:00</option>
					<option value='03:00'>03:00</option>
					<option value='04:00'>04:00</option>
					<option value='05:00'>05:00</option>
					<option value='06:00'>06:00</option>
					<option value='07:00'>07:00</option>
					<option value='08:00'>08:00</option>
					<option value='09:00'>09:00</option>
					<option value='10:00'>10:00</option>
					<option value='11:00'>11:00</option>
					<option value='12:00'>12:00</option>
					<option value='13:00'>13:00</option>
					<option value='14:00'>14:00</option>
					<option value='15:00'>15:00</option>
					<option value='16:00'>16:00</option>
					<option value='17:00'>17:00</option>
					<option value='18:00'>18:00</option>
					<option value='19:00'>19:00</option>
					<option value='20:00'>20:00</option>
					<option value='21:00'>21:00</option>
					<option value='22:00'>22:00</option>
					<option value='23:00'>23:00</option>
				</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="2"/></tr>
	</table>
	<!-- input _ end -->

	<div class="line-clear"></div>

	<!-- button _ start -->
	<div class="footer">
		<table style="width:100%">
			<tr>
				<td width="50%" height="30"></td>
				<td width="50%" align="right">	
					<input type="button" id="update" value='<fmt:message key="label.common.update"/>'/>
					<input type="button" id="cancel" value='<fmt:message key="label.common.cancel"/>'/>
				</td>
			</tr>
		</table>
	</div>				
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>