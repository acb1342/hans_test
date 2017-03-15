<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		
		$('#trReport').hide();
		
		$('#getBdInfo').click(function(e) {
			window.open("/elcg/building/popup.htm","new","width=448,height=448,top=100,left=100");
		});
		
		$('#getInstallerInfo').click(function(e) {
			window.open("/elcg/applicationAndReport/installerInfoPopup.htm","new","width=448,height=448,top=100,left=100");
		});
		
		// 저장
		$('#save').click(function(e) {
			var groupType = ${loginGroup.id};
			if(!validationCheck()) return;
			if(confirm("등록하시겠습니까?")) {
				if (groupType == 1) $("#vForm").submit();
				else {
					var content = document.getElementById('content').value;
					var type = $('#searchType option').index($('#searchType option:selected'));
					var appendUrl = "?content=" + content + "&type=" + type;
					
					location.href = "/elcg/applicationAndReport/createCust.htm" + appendUrl;
				}
				
				alert("등록되었습니다.");
			}
			
		});
		
		// 이전 페이지로 이동
		$('#cancel').click(function(e) {
			history.go(-1);
		});
	});
	
	function setChildValue(name, id) {
		$('#bdSelect').find("option").remove();
		$('#bdSelect').append("<option>상세/동명 선택</option>");
		$('#tdOwnerName').empty();
		$('#tdOwnerPhone').empty();
		$('#tdLatitude').empty();
		$('#tdAddr').empty();
		document.getElementById("bdGroupId").value = id;
		$('#tdBdGroup').empty();
		$('#tdBdGroup').append(name);
		setBdSelect();
	}
	
	function setChildValueInstaller(id, name, mobile) {
		document.getElementById("installerId").value = id;
		$('#tdInstaller').empty();
		$('#tdInstallerPhone').empty();
		$('#tdInstaller').append(name);
		$('#tdInstallerPhone').append(mobile);
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
						$('#bdSelect').append("<option>상세/동명 선택</option>");
						for(var i=0; i<data.length; i++) {
							$('#bdSelect').append("<option value='" + data[i].bdId + "'>" + data[i].name + "</option>");
						}
					}
				},
			error: function(e) {
			}
		});
	}
	
	function loadBdInfo() {
		$.ajax({
			type:'POST',
			url:'/elcg/applicationAndReport/loadBdInfo.json',
			data:{
				bdId:$("#bdSelect").val()			
			},
			success:function (data) {
					 if(data != null) {
						 $('#tdOwnerName').empty();
						 $('#tdOwnerPhone').empty();
						 $('#tdLatitude').empty();
						 $('#tdAddr').empty();
						 $('#tdOwnerName').append(data.ownerName);
						 $('#tdOwnerPhone').append(data.ownerPhone);
						 $('#tdLatitude').append(data.latitude);
						 $('#tdAddr').append(data.addr);
						 setChargerGroupSelect();
					 }
				},
			error: function(e) {
			}
		});
	}
	
	function setChargerGroupSelect() {
		$('#setChargerMgmtNoSelect').find("option").remove();
		$('#setChargerMgmtNoSelect').append("<option value=''>충전기 관리번호</option>");
		$('#chargerGroupSelect').find("option").remove();
		$('#chargerGroupSelect').append("<option value=''>충전그룹 선택</option>");
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
		$('#setChargerMgmtNoSelect').append("<option value=''>충전기 관리번호</option>");
		$.ajax({
			type:'POST',
			url:'/elcg/building/setChargerMgmtNoSelect.json',
			data:{
				chargerGroupSelect:$("#chargerGroupSelect").val()					
			},
			success:function (data) {
					if(data.length > 0) {
						for(var i=0; i<data.length; i++) {
							$('#setChargerMgmtNoSelect').append("<option value='" + data[i].chargerId + "'>" + data[i].chargerList.mgmtNo + "</option>");
						}
						
					}
				},
			error: function(e) {
				alert("error!!!");
			}
		});
	}
	
	function validationCheck() {
		var groupType = ${loginGroup.id};
		
		if( $('#searchType option').index($('#searchType option:selected')) == 0 ) {
			alert("분류를 선택하세요!");
			return;
		}
		var content = document.vForm.content.value;
		if(getTextLength(content)>2400) {
			alert("내용은 한글 1200자까지만");
			return;
		}
		
		if (groupType != 1) return true;
		
		if( !$("#content").val() ) {
			alert("내용을 입력하세요!");
			return;
		}
		if( $('#searchType option').index($('#searchType option:selected')) == 2 ) {
			if( $('#setChargerMgmtNoSelect option').index($('#setChargerMgmtNoSelect option:selected')) == '' ) {
				alert("건물정보를 불러온후에 충전그룹/관리번호를 선택하세요!");
				return;
			}
		}
		if( $('#bdSelect option').index($('#bdSelect option:selected')) == 0 ) {
			alert("건물정보를 불러온후에 상세/동명을 선택하세요!");
			return;
		}
		if( !$("#installerId").val() ) {
			alert("설치자를 배정하세요.");
			return;
		}
		
		return true;
	}
	
	function searchTypeChange() {
		var index = $('#searchType option').index($('#searchType option:selected')); 
		if( index == 2 ) {
			$('#trReport').show();
		}
		else {
			$('#trReport').hide();
		}
	}
	
	function getTextLength(str) {
		var len = 0;
		for(var i = 0; i < str.length; i++) {
			if(escape(str.charAt(i)).length == 6) {
				len++;
			}
			len++;
		}
		return len;
	}
</script>

<form method="post" id="vForm" name="vForm" action="/elcg/applicationAndReport/create.htm" >
<div class="wrap00">

	<!-- input _ first -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td style="width:20%" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.writer"/>
			</td>
			<td style="width:30%" class="td02">${userName}</td>
			<td style="width:20%" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsMenu.function.type"/>
			</td>
			<td style="width:30%" class="td02">
			<c:if test="${ empty from }">
				<c:set var="searchTypeKeys">0,1,2</c:set>
				<c:set var="searchTypeValues">선택,설치신청,고장신고</c:set>
				<c:set var="searchType" value='<%=request.getParameter("searchType")%>'/>
				<select name="searchType" id="searchType" onchange="javascript:searchTypeChange()">
					<c:forTokens var="key" items="${searchTypeKeys}" delims="," varStatus="statKey">
						<c:forTokens var="val" items="${searchTypeValues}" delims="," varStatus="statVal">
							<c:if test="${statKey.index == statVal.index}">
								<option value="${key}" ${key == searchType ? 'selected':''}>${val}</option>
							</c:if>
						</c:forTokens>
					</c:forTokens>
				</select>
			</c:if>
			<c:if test="${ 'error' eq from}">고장신고</c:if>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.regDate"/>
			</td>
			<td class="td02"><fmt:formatDate value="${date}" pattern="yyyy.MM.dd"/></td>
			<td style="width:20%" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.status"/>
			</td>
			<td class="td02">접수중</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.contentEditor.content"/>
			</td>
			<td colspan="5" class="td02">
				<textarea id="content" name="content" ></textarea>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
	</table>
	
	<c:if test="${loginGroup.id == 1 }">
	<!-- input _ second : 기본 -->
	<c:if test="${ empty from }" >
	<input type="hidden" id="bdGroupId" name="bdGroupId"/>
	<table style="width:100%; margin:10px 0px 5px 0px">
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td style="width:20%" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.buildingName"/>
			</td>
			<td style="width:30%" class="td02" id="tdBdGroup"></td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.ownerName"/>
			</td>
			<td colspan="2" class="td02" id="tdOwnerName"></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td style="width:20%" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.detailName"/>
			</td>
			<td style="width:30%" class="td02">
				<select id="bdSelect" name="bdSelect" onchange="javascript:loadBdInfo()">
					<c:choose>
						<c:when test="${bdGroup eq '' || bdGroup eq null}">
							<option>상세/동명 선택</option>
						</c:when>
						<c:otherwise>
							<option value="${bdSelect}">${bdText}</option>
						</c:otherwise>
					</c:choose>
				</select>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.ownerPhone"/>
			</td>
			<td colspan="2" class="td02" id="tdOwnerPhone"></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td style="width:20%" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.addr"/>
			</td>
			<td style="width:30%" class="td02" id="tdAddr"></td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.latitude"/>
			</td>
			<td colspan="2" class="td02" id="tdLatitude"></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr id="trReport">
			<td height="25" class="td01">
			<fmt:message key="label.elcg.chargerGroupName"/>/<fmt:message key="label.elcg.mgmtNo"/>
			</td>
			<td colspan="5" class="td02">
					<select id="chargerGroupSelect" name="chargerGroupSelect" onchange="javascript:setMgmtNoSelect()">
						<c:choose>
							<c:when test="${bdSelect eq '' || bdSelect eq null}">
								<option value=''>충전그룹 선택</option>
							</c:when>
							<c:otherwise>
								<option value="${chargerGroupSelect}"></option>
							</c:otherwise>
						</c:choose>
					</select>
					
					<select id="setChargerMgmtNoSelect" name="setChargerMgmtNoSelect">
						<c:choose>
							<c:when test="${setChargerMgmtNoSelect eq '' || setChargerMgmtNoSelect eq null}">
								<option value=''>충전기 관리번호</option>
							</c:when>
							<c:otherwise>
								<option value="${setChargerMgmtNoSelect}"></option>
							</c:otherwise>
						</c:choose>
					</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
	</table>
	<div style="float:right; margin:0px 0px 5px 0px">
		<input type="button" id="getBdInfo" value="건물정보 불러오기"/>
	</div>
	</c:if>
	</c:if>
	<!-- input _ second : from error.jsp -->
	<c:if test="${ 'error' eq from }">
		<input type="hidden" id="bdGroupId" name="bdGroupId" value="${charger.chargerGroup.bd.bdGroup.bdGroupId}"/>
		<input type="hidden" name="searchType" value="2"/>
	<table style="width:100%; margin:10px 0px 5px 0px">
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td style="width:20%" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.buildingName"/>
			</td>
			<td style="width:30%" class="td02">${charger.chargerGroup.bd.bdGroup.name}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.ownerName"/>
			</td>
			<td colspan="2" class="td02">${owner.name}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td style="width:20%" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.detailName"/>
			</td>
			<td style="width:30%" class="td02">${charger.chargerGroup.bd.name}
			<input type="hidden" name="bdSelect" value="${charger.chargerGroup.bd.bdId}"/>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.ownerPhone"/>
			</td>
			<td colspan="2" class="td02">${owner.mobile}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td style="width:20%" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.addr"/>
			</td>
			<td style="width:30%" class="td02">${charger.chargerGroup.bd.bdGroup.addr}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.latitude"/>
			</td>
			<td colspan="2" class="td02" id="tdLatitude">
				${charger.chargerGroup.bd.bdGroup.latitude} / ${charger.chargerGroup.bd.bdGroup.longitude}
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td style="width:20%" height="25" class="td01">
			<fmt:message key="label.elcg.chargerGroupName"/>
			</td>
			<td style="width:30%" class="td02">${charger.chargerGroup.name }</td>
			<td style="width:20%" height="25" class="td01">
			<fmt:message key="label.elcg.mgmtNo"/>
			</td>
			<td style="width:30%" class="td02">
			<input type="hidden" name="setChargerMgmtNoSelect" value="${charger.chargerId}"/>
				${charger.mgmtNo}
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
	</table>
	</c:if>
	<c:if test="${loginGroup.id == 1 }">
	<!-- input _ third -->
	<input type="hidden" id="installerId" name="installerId" />
	<table style="width:100%; margin:10px 0px 5px 0px">
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td style="width:20%" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.installer"/>
			</td>
			<td style="width:30%" class="td02" id="tdInstaller"></td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.installerPhone"/>
			</td>
			<td colspan="2" class="td02" id="tdInstallerPhone"></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
	</table>
	<div style="float:right; margin:0px 0px 15px 0px">
		<input type="button" id="getInstallerInfo" value="설치자 배정"/>
	</div>
	</c:if>
	<!-- input _ end -->

	<div class="line-clear"></div>

	<!-- button _ start -->
	<div class="footer">
		<table style="width:100%">
			<tr>
				<td width="100%" align="right">	
				<c:if test="${authority.update}">
					<input type="button" id="save" value='<fmt:message key="label.common.save"/>'/>
				</c:if>
					<input type="button" id="cancel" value='<fmt:message key="label.common.cancel"/>'/>	
				</td>
			</tr>
		</table>
	</div>				
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>