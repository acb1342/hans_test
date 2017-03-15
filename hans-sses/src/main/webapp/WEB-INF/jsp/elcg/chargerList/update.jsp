<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroupAuth" %>
<%@ page import="com.mobilepark.doit5.admin.model.Menu" %>
<%@ page import="java.util.Map" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 저장
		$('#save').click(function(e) {	
			$("#vForm").submit();					
		});
		
	});

	function changeStatus() {
		
	}
	
	function cancel() {
		history.back();
	}

</script>

<spring:hasBindErrors name="chargerGroup"/>
<form method="post" id="vForm" name="vForm" action="/elcg/chargerList/update.htm?seq=${chargerList.chargerId}">

<div class="wrap00">
	<!-- start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td style="width:20%" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.sn"/>
			</td>
			<td style="width:30%" class="td02">${chargerList.serialNo}</td>
			<td style="width:20%" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.modelName"/>
			</td>
			<td style="width:30%" class="td02"><input type="text" name="model.model" value="${chargerList.model.model}"/></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.mgmtNo"/>
			</td>
			<td class="td02">${chargerList.mgmtNo}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargeRate"/>
			</td>
			<td class="td02"><fmt:message key="${chargerList.chargeRate}"/></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.status"/>
			</td>
			<td class="td02">
				<c:set var="searchType" value="${chargerList.status}"/>
				<select name="searchType" id="searchType">
					<option value="402101" ${searchType == '402101' ? 'selected':''}><fmt:message key="label.elcg.noComIns"/></option>
					<option value="402102" ${searchType == '402102' ? 'selected':''}><fmt:message key="label.elcg.comIns"/></option>
				</select>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.max"/>
			</td>
			<td class="td02">${chargerList.model.capacity}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.buildingName"/>
			</td>
			<td class="td02">${charger.chargerGroup.bd.bdGroup.name}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.detailName"/>
			</td>
			<td class="td02">${charger.chargerGroup.bd.name}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerGroupName"/>
			</td>
			<td colspan="5" class="td02">${charger.chargerGroup.name}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
	</table>

	<div class="line-clear"></div>
			
	
	<div class="line-clear"></div>	
	<!-- button _ start -->
	<div class="footer"><br/>
		<table style="width:100%">
		<tr>
			<td width="50%" height="30"></td>
			<td width="50%" align="right">		
				<c:if test="${authority.update}">
					<input type="button" id="save" value='<fmt:message key="label.common.save"/>'/>
				</c:if>
				<c:if test="${authority.delete}">
					<input type="button" value='<fmt:message key="label.common.cancel"/>' onclick="javascript:cancel()"/>
				</c:if>
			</td>
		</tr>
		</table>
	</div>				
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>