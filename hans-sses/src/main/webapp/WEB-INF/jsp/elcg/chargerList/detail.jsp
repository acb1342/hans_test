<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	//수정 페이지로 이동
	function update() {
		location.href = "/elcg/chargerList/update.htm?seq=${chargerList.chargerId}";
	}
	
	// 삭제
	function confirmAndDelete() {
		deleteById('/elcg/chargerList/delete.json', '${chargerList.chargerId}', function() { 
			search();
		});
	}
	
	// 검색 페이지로 이동
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			location.href = "/elcg/chargerList/search.htm?" + callbackUrl;
		} else {
			location.href = "/elcg/chargerList/search.htm";
		}
	}
</script>

<spring:hasBindErrors name="chargerList"/>
<div class="wrap00">
	<!-- start -->
	<table style="width:100%">
	<tr class="line-top"><td colspan="4"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.sn"/>
			</td>
			<td  class="td02">${chargerList.serialNo}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.modelName"/>
			</td>
			<td  class="td02">${chargerList.model.model}</td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.mgmtNo"/>
			</td>
			<td  class="td02">${chargerList.mgmtNo}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargeRate"/>
			</td>
			<td  class="td02"><fmt:message key="${chargerList.chargeRate}"/></td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.status"/>
			</td>
			<td  class="td02"><fmt:message key="${chargerList.status}"/></td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.max"/>
			</td>
			<td  class="td02">${chargerList.model.capacity}</td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.buildingName"/>
			</td>
			<td  class="td02"><c:if test="${'402102' eq chargerList.status}">${charger.chargerGroup.bd.bdGroup.name}</c:if></td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.detailName"/>
			</td>
			<td  class="td02"><c:if test="${'402102' eq chargerList.status}">${charger.chargerGroup.bd.name}</c:if></td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerGroupName"/>
			</td>
			<td colspan="5" class="td02"><c:if test="${'402102' eq chargerList.status}">${charger.chargerGroup.name}</c:if></td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
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