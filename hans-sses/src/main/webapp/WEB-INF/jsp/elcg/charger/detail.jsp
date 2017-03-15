<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	//수정 페이지로 이동
	function update() {
		location.href = "/elcg/charger/update.htm?seq=${charger.chargerId}&bdId=${charger.chargerGroup.bd.bdId}";
	}
	
	// 삭제
	function confirmAndDelete() {
		deleteById('/elcg/charger/delete.json', '${charger.chargerId}', function() { 
			search();
		});
	}
	
	// 검색 페이지로 이동
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			location.href = "/elcg/charger/search.htm?" + callbackUrl;
		} else {
			location.href = "/elcg/charger/search.htm";
		}
	}
	
	function infoSN(chargerId) {
		window.open("/elcg/charger/popup/infoSnPopup.htm?chargerId="+chargerId,"new","width=448,height=448,top=100,left=100");
	}
	
	function infoOwner(ownerId) {
		window.open("/elcg/charger/popup/infoOwnerPopup.htm?ownerId="+ownerId,"new","width=448,height=448,top=100,left=100");
	}
	
	function infoGroup(chargerGroupId) {
		window.open("/elcg/charger/popup/infoGroupPopup.htm?chargerGroupId="+chargerGroupId,"new","width=448,height=448,top=100,left=100");
	}
</script>

<spring:hasBindErrors name="charger"/>
<div class="wrap00">
	<!-- start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.mgmtNo"/>
			</td>
			<td colspan="2" class="td02">${charger.mgmtNo}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.sn"/>
			</td>
			<td colspan="2" class="td02"><a href='javascript:infoSN("${charger.chargerId}")'>${charger.chargerList.serialNo}</a></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerLocation"/>
			</td>
			<td colspan="2" class="td02">${charger.name}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.max"/>
			</td>
			<td colspan="2" class="td02">${charger.capacity}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.installer"/>
			</td>
			<td colspan="2" class="td02">${charger.wkName}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.insDate"/>
			</td>
			<td colspan="2" class="td02">
				<fmt:formatDate value="${charger.fstRgDt}" pattern="yyyy.MM.dd"/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.buildingName"/>
			</td>
			<td colspan="2" class="td02">${charger.chargerGroup.bd.bdGroup.name}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.detailName"/>
			</td>
			<td colspan="2" class="td02">${charger.chargerGroup.bd.name}</td>	
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.ownerName"/>
			</td>
			<td colspan="2" class="td02"><a href='javascript:infoOwner("${owner.id}")'>${owner.name}</a></td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerGroupName"/>
			</td>
			<td colspan="2" class="td02"><a href='javascript:infoGroup("${charger.chargerGroup.chargerGroupId}")'>${charger.chargerGroup.name}</a></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		
		<%-- 운영자 --%>
		<c:if test="${loginGroup.id == 1}">
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.status"/>
			</td>
			<td colspan="2" class="td02">
				<c:if test="${ !empty charger.status }"><fmt:message key="${charger.status}"/></c:if>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.record"/>
			</td>
			<td colspan="2" class="td02"><input type="button" onclick="location.href='/statistics/stat/charger.htm?fromDate=${fromDate}&toDate=${toDate}&chargerNumber=${charger.mgmtNo}&searchType=daily'" value="충전이력 보기"/></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		</c:if>
		
		<%-- 건물주/설치자 --%>
		<c:if test="${loginGroup.id == 2 || loginGroup.id == 3}">
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.status"/>
			</td>
			<td colspan="5" class="td02">
				<c:if test="${ !empty charger.status }"><fmt:message key="${charger.status}"/></c:if>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		</c:if>
		
	</table>
	<div class="line-clear"></div>

	<!-- button _ start -->
	<div class="footer">	
		<table style="width:100%">
			<tr>
				<td width="50%" height="30">
					<input type="button" value='<fmt:message key="label.common.list"/>' onclick="javascript:search()"/>
				</td>
				<td width="50%" align="right">
				<c:if test="${authority.update}">
					<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="javascript:update()"/>
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

<%@ include file="/include/footer.jspf" %>