<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 저장
		$('#save').click(function(e) {
			
			if(!(createCheck())) return;
		
			if(confirm("입력하신 내용으로 등록하시겠습니까?")) {
				$("#vForm").submit();
				alert("입력하신 내용으로 등록되었습니다.")
			}
			else {
				return;
			}
			
		});
		
		$('#bdGroup').click(function(e) {
			window.open("/elcg/building/popup.htm","new","width=448,height=448,top=100,left=100");
		});
		
		// 이전 페이지로 이동
		$('#cancel').click(function(e) {
			//if(confirm("정말 취소하시겠습니까?")) window.location.href = "/elcg/chargerGroup/search.htm";
			history.go(-1);
		});
		
		
		$('#validationCheck').click(function(e) {
			if (!Boolean($("#serial").val())) {
				alert("S/N을 입력하세요.");
				return;
			}
			else {
				$.ajax({
					type:'POST',
					url:'/elcg/charger/serialCheck.json',
					data:{
						serial:$("#serial").val(),					
					},
					success:function (data) {						
						if(data.result=="0") {
							alert("존재하지 않는 충전기입니다.");
							return;
						}
						else if(data.result=="1") {
							alert("이미 설치된 충전기입니다.");
							return;
						}
						
						document.getElementById("isSerialCheckComplete").value = 1;
							
						var chargerId = data.chargerId;
						var serialNo  = data.serialNo;
						var capacity  = Number(data.capacity);
						var mgmtNo = data.mgmtNo;
						
						$('#viewMgmtNo').empty();
						$('#viewCapacity').empty();
						
						$('#viewMgmtNo').append(mgmtNo);
						$('#viewCapacity').append(capacity);
						
						$('#mgmtNo').val(mgmtNo);
						$('#capa').val(capacity);
						$('#chargerId').val(chargerId);							
					},
					error: function(e) {
						alert("error!!!");
					}
				});
			}
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
						$('#bdSelect').find("option").remove();
						$('#bdSelect').append("<option value='0'>상세/동명 선택</option>");
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
		getOwner();
		$('#chargerGroupSelect').find("option").remove();
		$('#chargerGroupSelect').append("<option value='0'>충전그룹 선택</option>");
		if ($("#bdSelect").val() == 0) return;
		
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
	
	function getOwner() {
		$.ajax({
			type:'POST',
			url:'/elcg/charger/getOwner.json',
			data:{
				bdSelect:$("#bdSelect").val()					
			},
			success:function (data) {
					var ownerName = data.ownerName
					
					$('#tdOwnerName').empty();
					$('#tdOwnerName').append(ownerName);
				},
			error: function(e) {
				alert("error!!!");
			}
		});
	}
	
	function createCheck() {
		if( $('#isSerialCheckComplete').val() == '0' ) {
			alert("충전기S/N 체크하세요!");
			return;
		}		
		if( !$("#chargerName").val() ) {
			alert("충전기 위치를 입력하세요!");
			return;
		}
		if( $('#selInstaller option').index($('#selInstaller option:selected')) == 0  ) {
			alert("설치자를 선택하세요!");
			return;
		}
		var selBdId = document.getElementById("bdSelect");
		if (selBdId.options[selBdId.selectedIndex].value == '0') {
			alert("건물정보를 불러온후에 상세/동명을 선택하세요!");
			return;
		}
		var selCg = document.getElementById("chargerGroupSelect");
		if (selCg.options[selCg.selectedIndex].value == '0') {
			alert("상세/동명을 선택한후에 충전그룹을 선택하세요!");
			return;
		}
		
		return true;
	}
</script>
<spring:hasBindErrors name="charger"/>
<form method="post" id="vForm" name="vForm" action="/elcg/charger/create.htm" >
<div class="wrap00">
<input type="hidden" id="isSerialCheckComplete" value="0"/>
<input type="hidden" id="bdGroupId" name="bdGroupId"/>
<input type="hidden" id="chargerId" name="chargerId" />
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.mgmtNo"/>
			</td>
			<td colspan="2" class="td02" id="viewMgmtNo">
				<input type="hidden" id="mgmtNo" name="mgmtNo" value="${charger.mgmtNo}"/>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.sn"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="serial" name="serialNo"/>
				<input type="button" id="validationCheck" value="유효성 확인" />
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerLocation"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="chargerName" name="name" value="${charger.name}"/>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.max"/>
			</td>
			<td colspan="2" class="td02" id="viewCapacity">
				<input type="hidden" id="capa" name="capacity" value="${charger.capacity}"/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.installer"/>
			</td>
			<td colspan="2" class="td02">
			<c:if test="${loginGroup.id == 2 }">
				${loginUser.name}
				<input type="hidden" name="installerName" value="${loginUser.name}"/>
			</c:if>
			<c:if test="${loginGroup.id == 1 }">
				<select id="selInstaller" name="selInstaller">
					<option value="0">=== 설치자 선택 ===</option>
					<c:forEach items="${installer}" var="inst">
						<option value="${inst.id}">${inst.name}&nbsp;(처리중: ${inst.noCompleWkCnt})</option>
					</c:forEach>
				</select>
			</c:if>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.insDate"/>
			</td>
			<td colspan="2" class="td02">
				<fmt:formatDate value="${date}" pattern="yyyy.MM.dd"/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.buildingName"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="bdGroup" name="bdGroup" value="${bdGroup}" placeholder="<fmt:message key="label.elcg.buildingName"/>" />
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.detailName"/>
			</td>
			<td colspan="2" class="td02">
				<select id="bdSelect" name="bdSelect" onchange="javascript:setChargerGroupSelect()">
						<c:choose>
							<c:when test="${bdGroup eq '' || bdGroup eq null}">
								<option value='0'>상세/동명 선택</option>
							</c:when>
							<c:otherwise>
								<option value="${bdSelect}">${bdSelect}</option>
							</c:otherwise>
						</c:choose>
					</select>
			</td>	
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.ownerName"/>
			</td>
			<td colspan="2" class="td02" id="tdOwnerName"></td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerGroupName"/>
			</td>
			<td colspan="2" class="td02">
				<select id="chargerGroupSelect" name="chargerGroupSelect">
						<c:choose>
							<c:when test="${bdSelect eq '' || bdSelect eq null}">
								<option value='0'>충전그룹 선택</option>
							</c:when>
							<c:otherwise>
								<option value="${chargerGroupSelect}">${chargerGroupSelect}</option>
							</c:otherwise>
						</c:choose>
					</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.status"/>
			</td>
			<td colspan="2" class="td02"></td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.record"/>
			</td>
			<td colspan="2" class="td02"></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
	</table>
	
	<!-- input _ end -->

	<div class="line-clear"></div>

	<!-- button _ start -->
	<div class="footer">
		<table style="width:100%">
			<tr>
				<td width="50%" height="30"></td>
				<td width="50%" align="right">	
				<c:if test="${authority.update}">
					<input type="button" id="save" value='<fmt:message key="label.common.save"/>'/>
				</c:if>
				<c:if test="${authority.delete}">
					<input type="button" id="cancel" value='<fmt:message key="label.common.cancel"/>'/>
				</c:if>	
				</td>
			</tr>
		</table>
	</div>				
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>