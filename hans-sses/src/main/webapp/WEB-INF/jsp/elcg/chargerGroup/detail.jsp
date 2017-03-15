<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	//수정 페이지로 이동
	function update() {
		location.href = "/elcg/chargerGroup/update.htm?seq=${chargerGroup.chargerGroupId}";
	}
	
	// 삭제
	function confirmAndDelete() {
		deleteById('/elcg/chargerGroup/delete.json', '${chargerGroup.chargerGroupId}', function() { 
			search();
		});
	}
	
	// 검색 페이지로 이동
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			location.href = "/elcg/chargerGroup/search.htm?" + callbackUrl;
		} else {
			location.href = "/elcg/chargerGroup/search.htm";
		}
	}
</script>

<spring:hasBindErrors name="chargerGroup"/>
<div class="wrap00">
	<!-- start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="4"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerGroupName"/>
			</td>
			<td class="td02">${chargerGroup.name}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.max"/>
			</td>
			<td  class="td02">${chargerGroup.capacity}</td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.buildingName"/>
			</td>
			<td class="td02">${chargerGroup.bd.bdGroup.name}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.detailName"/>
			</td>
			<td class="td02">${chargerGroup.bd.name}</td>	
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
		<tr>
			<td style="width:15%" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerGroupDescription"/>
			</td>
			<td style="width:35%" class="td02">
			<textarea readonly="readonly">${chargerGroup.description}</textarea>
			</td>
			<td style="width:15%" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerCnt"/>
			</td>
			<td style="width:35%" class="td02">${chargerCnt}</td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
	</table>
		<br/>
		<c:if test="${ fn:length(chargerGroup.chargerList) > 0 }">
			<table style="width:100%">
			<tr class="line-top"><td colspan="4"/></tr>
			<tr>
				<td height="25" class="td01"><fmt:message key="label.elcg.mgmtNo"/></td>
			</tr>
				<c:forEach items="${chargerGroup.chargerList}" var="charger" varStatus="status">	
					<tr>
						<td style="width:25%" class="td02">${charger.chargerList.mgmtNo}</td>
					</tr>
					<tr class="line-dot"><td colspan="4"/>			
				</c:forEach>
			</table>
		</c:if>
		
	<!-- list end -->

	<div class="line-clear"></div>

	<!-- button _ start -->
	<div class="footer">	
		<table style="width:100%">
			<tr>
				<td width="50%" height="30">
					<input type="button" value='<fmt:message key="label.common.list"/>' onclick="javascript:search()"/>
				</td>
				<td width="50%" align="right">
				<c:if test="${authority.update && loginGroup.id != 3}">
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