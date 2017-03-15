<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>

<script type="text/javascript">
	$(function () {
		if(!Boolean($('input[name="status"]:checked').val())) {
			$('#CGdiv').hide();
			$('#Cdiv').hide();	
		}
		searchChange();
		
		$('#bdGroup').click(function(e) {
			window.open("/elcg/controller/popup.htm","new","width=448,height=448,top=100,left=100");
		});
		
		
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
		var selBdId = document.getElementById("bdSelect");
		var selCgId = document.getElementById("chargerGroupSelect");
		var selType = $('input:radio[name="status"]:checked').val();
			
		if (selType == 'CG') {
			if (selBdId.options[selBdId.selectedIndex].value == '0'
					|| selCgId.options[selCgId.selectedIndex].value == '0') {
				alert("상세/동명 및 충전그룹을 선택하세요.");
				return;
			}
		}
		
		saveSelected();
		$('#vForm').submit();
		
	}
	
	// 충전기별 검색
	function searchByCharger() {
		if (!$("#mgmtNo").val()) {
			alert("충전기 관리번호를 입력하세요!")
		} else {
			$('#vForm').submit();
		}
	}
	
	function searchChange() {	
		if( $('input[name="status"]:checked').val() == "CG" ) {
			$('#CGdiv').show();
			$('#Cdiv').hide();
		} 
		else if( $('input[name="status"]:checked').val() == "C") {	
			$('#CGdiv').hide();
			$('#Cdiv').show();
		}
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
	
	// select 선택한 항목 유지
	function saveSelected() {
		var targetGroup = document.getElementById("chargerGroupSelect");
		document.getElementById("chargerGroupText").value = targetGroup.options[targetGroup.selectedIndex].text;
		var targetBd = document.getElementById("bdSelect");
		document.getElementById("bdText").value = targetBd.options[targetBd.selectedIndex].text;
	}
	
	function restart() {
		if(confirm("해당 충전기를 재시작하시겠습니까?") == true) {
			$.ajax({
				type:'POST',
				url:'/elcg/controller/restart.json',
				data:{
					chargerId:$("#chargerId").val(),					
				},
				success:function (data) {
					if (data) alert("해당 충전기를 재시작하였습니다.");
					else alert("해당 충전기 재시작을 실패하였습니다.");
				},
				error: function(e) {
					alert("error!!!");
				}
			});
		}
	}
	
</script>

<form method="get" id="vForm" name="vForm" action="/elcg/controller/search.htm">
	<div class="wrap00">
	<!-- search _ start -->
	<c:set var="status" value="${type}" />
	<c:set var="mgmtNo" value='<%=request.getParameter("mgmtNo")%>' />
	<c:set var="bdGroup" value='<%=request.getParameter("bdGroup")%>' />
	<c:set var="chargerGroupSelect" value='<%=request.getParameter("chargerGroupSelect")%>' />
	<input type="hidden" id="bdGroupId" name="bdGroupId" value="${selBdGroupId}"/>
	<input type="hidden" id="selBdId" name="selBdId" value="${bdSelect}"/>
	<input type="hidden" id="chargerId" value="${charger.chargerId}"/>
	
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<td>
					<input type="radio" id="status" name="status" value="CG" <c:if test="${type eq 'CG'}">checked</c:if> onclick="javascript:searchChange()"> 충전그룹별&nbsp;&nbsp;
					<input type="radio" id="status" name="status" value="C" <c:if test="${(type eq 'C' || type eq 'fail' )&& mgmtNo ne null}">checked</c:if> onclick="javascript:searchChange()"> 충전기별
				</td>
				<td>
				<div id="CGdiv">
					<c:set var="bdGroup" value='<%=request.getParameter("bdGroup")%>' />
					<input type="text" id="bdGroup" name="bdGroup" value="${bdGroup}" placeholder="<fmt:message key="label.elcg.buildingName"/>" />
					
					<input type="hidden" id="bdText" name="bdText"/>
					<c:set var="bdText" value='<%=request.getParameter("bdText")%>' />
					<c:set var="bdSelect" value='<%=request.getParameter("bdSelect")%>' />
					<select id="bdSelect" name="bdSelect" onchange="javascript:setChargerGroupSelect()">
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
					
					<input type="hidden" id="chargerGroupText" name="chargerGroupText"/>
					<c:set var="chargerGroupText" value='<%=request.getParameter("chargerGroupText")%>' />
					<c:set var="chargerGroupSelect" value='<%=request.getParameter("chargerGroupSelect")%>' />
					<select id="chargerGroupSelect" name="chargerGroupSelect">
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
					
					<input type="button" id="searchGroup" value="조회" onclick="javascript:searchByGroup()" />
				</div>
				</td>
				<td>
				<div id="Cdiv">
					<input type="text" id="mgmtNo" name="mgmtNo" placeholder="충전기 관리번호" value="${mgmtNo}"/>
					<input type="button" id="searchCharger" value="조회" onclick="javascript:searchByCharger()" />
				</div>
				</td>
			</tr>
		</table>
	</fieldset><br/>
	<!-- search _ end -->
	
	<!-- list start -->
	<table style="width: 100%">
		<tr class="line-top"><td colspan="4"/></tr>
		<c:choose>
			<c:when test="${ type eq 'default' }">
				<div style="text-align:center">
				<br/><h4><fmt:message key="label.elcg.chargerControl.default"/></h4>
				</div>
			</c:when>
			<c:when test="${'C' eq type}">
				<tr>
					<td height="25" class="td01">
						<span class="bul_dot1">◆</span>
						<fmt:message key="label.elcg.mgmtNo"/>
					</td>
					<td colspan="3" class="td02">${chargerList.mgmtNo}</td>
				</tr>
				<tr class="line-dot"><td colspan="4"/></tr>
				<tr>
					<td style="width: 25%" height="25" class="td01">
						<span class="bul_dot1">◆</span>
						<fmt:message key="label.elcg.buildingName"/>
					</td>
					<td style="width: 25%" class="td02">${charger.chargerGroup.bd.bdGroup.name}</td>
					<td style="width: 25%" height="25" class="td01">
						<span class="bul_dot1">◆</span>
						<fmt:message key="label.elcg.detailName"/>
					</td>
					<td style="width: 25%" class="td02">${charger.chargerGroup.bd.name}</td>
				</tr>
				<tr class="line-dot"><td colspan="4"/></tr>
				<tr>
					<td style="width: 25%" height="25" class="td01">
						<span class="bul_dot1">◆</span>
						<fmt:message key="label.elcg.chargerGroupName"/>
					</td>
					<td style="width: 25%" class="td02">${charger.chargerGroup.name}</td>
					<td style="width: 25%" height="25" class="td01">
						<span class="bul_dot1">◆</span>
						현재 그룹 사용 전류량(A)
					</td>
					<td style="width: 25%" class="td02">${charger.chargerGroup.capacity}</td>
				</tr>
				<tr class="line-dot"><td colspan="4"/></tr>
				<tr>
					<td style="width: 25%" height="25" class="td01">
						<span class="bul_dot1">◆</span>
						<fmt:message key="label.elcg.sn"/>
					</td>
					<td style="width: 25%" class="td02">${chargerList.serialNo}</td>
					<td style="width: 25%" height="25" class="td01">
						<span class="bul_dot1">◆</span>
						<fmt:message key="label.elcg.status"/>
					</td>
					<td style="width: 25%" class="td02"><fmt:message key="${charger.status}" /></td>
				</tr>
				<tr class="line-dot"><td colspan="4"/></tr>
		</c:when>
		<c:when test="${ type eq 'CG' }">
				<tr>
					<td style="width:20%" height="25" class="td01">
						<span class="bul_dot1">◆</span>
						<fmt:message key="label.elcg.buildingName"/>
					</td>
					<td style="width:30%" class="td02">${chargerGroup.bd.bdGroup.name}</td>
					<td style="width:20%" height="25" class="td01">
						<span class="bul_dot1">◆</span>
						<fmt:message key="label.elcg.detailName"/>
					</td>
					<td style="width:30%" class="td02">${chargerGroup.bd.name}</td>
				</tr>
				<tr class="line-dot"><td colspan="4"/></tr>
				<tr>
					<td style="width:20%" height="25" class="td01">
						<span class="bul_dot1">◆</span>
						<fmt:message key="label.elcg.chargerGroupName"/>
					</td>
					<td style="width:30%" class="td02">${chargerGroup.name}</td>
					<td style="width:20%" height="25" class="td01">
						<span class="bul_dot1">◆</span>
						현재 그룹 사용 전류량(A)
					</td>
					<td style="width:30%" class="td02">${chargerGroup.capacity}</td>
				</tr>
				<tr class="line-dot"><td colspan="4"/></tr>
				<tr>
					<td height="25" colspan="4"><b>* 충전기 현황 및 제어</b></td>
				</tr>
				<tr class="line-top"><td colspan="4"/></tr>
				
				<c:choose>
					<c:when test="${ fn:length(chargerList) > 0 }">
					<table style="width:100%">
						<c:forEach items="${chargerList}" var="charger" varStatus="status">	
							<c:if test="${status.index%2 == 0}" >
								<tr >
									<td style="width:20%" height="25" class="td01">${charger.chargerId}</td>
									<td style="width:30%" class="td02"><fmt:message key="${charger.status}"/>
										<span style="float:right"><input type="button" onclick="javascript:restart()" value="재시작"/></span>
									</td>
							</c:if>
							<c:if test="${ fn:length(chargerList)-1 eq status.index }">
								<c:if test="${ fn:length(chargerList)%2 == 1}">
									<td style="width:20%" height="25" class="td01"></td>
									<td style="width:30%" class="td02"></td>
									</td>
								</tr>
								</c:if>
							</c:if>
							<c:if test="${status.index%2 == 1}">
									<td style="width:20%" height="25" class="td01">${charger.chargerId}</td>
									<td style="width:30%" class="td02"><fmt:message key="${charger.status}"/>
									<span style="float:right"><input type="button" onclick="javascript:restart()" value="재시작"/></span>
									</td>
								</tr>
								<tr class="line-dot"><td colspan="4"/></tr>
							</c:if>
							
						</c:forEach>
					</table>
					</c:when>
				</c:choose>
		</c:when>
		<c:when test="${ type eq 'fail' }">
		</c:when>
		</c:choose>
	</table>
	
	<div class="footer">
		<table style="width:100%">
			<tr>
				<td height="30" align="right">
					<c:if test="${type eq 'C' }">
					<input type="button" value='<fmt:message key="label.elcg.restart"/>' onclick="javascript:restart()"/>
					</c:if>
				</td>
			</tr>
		</table>
	</div>
	
	</div>
</form>
<%@ include file="/include/footer.jspf" %>