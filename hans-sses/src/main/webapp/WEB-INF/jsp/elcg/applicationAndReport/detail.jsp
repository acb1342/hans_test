<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
//수정 페이지로 이동
function update() {
	location.href = "/elcg/applicationAndReport/update.htm?seq=${obj.snId}&wkType=${wkType}";
}

// 삭제
function confirmAndDelete() {
	deleteById('/elcg/applicationAndReport/delete.json', '${wkType}_${obj.snId}', function() { 
		search();
	});
}

// 검색 페이지로 이동
function search() {
	var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
	if (callbackUrl != 'null') {
		location.href = "/elcg/applicationAndReport/search.htm?" + callbackUrl;
	} else {
		location.href = "/elcg/applicationAndReport/search.htm";
	}
}

function statusComplete() {
	if(confirm("완료 처리 하시겠습니까?")) {
		location.href = "/elcg/applicationAndReport/updateStatusComplete.htm?seq=${obj.snId}&wkType=${wkType}";
	}
}
</script>

<div class="wrap00">
	<!-- input _ first -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="4"/></tr>
		<tr>
			<td style="width:20%" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.writer"/>
			</td>
			<td style="width:30%" class="td02">${rcName}</td>
			<td style="width:20%" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsMenu.function.type"/>
			</td>
			<td style="width:30%" class="td02"><fmt:message key="${wkType}"/></td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.regDate"/>
			</td>
			<td class="td02"><fmt:formatDate value="${obj.fstRgDt}" pattern="yyyy.MM.dd"/></td>
			<td style="width:20%" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.status"/>
			</td>
			<td class="td02"><fmt:message key="${obj.status}"/></td>	
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.contentEditor.content"/>
			</td>
			<td colspan="5" class="td02">
				<textarea readonly="readonly">${obj.body}</textarea>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
	</table>
	<!-- input _ second -->
	<c:choose>
	 <%-- status : 접수중 --%>
	<c:when test="${obj.status eq '407101' || obj.status eq '409101'}">
		<table style="width:100%; margin:10px 0px 5px 0px">
			<tr class="line-top"><td colspan="4"/></tr>
			<tr>
				<td style="width:20%" height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.elcg.buildingName"/>
				</td>
				<td style="width:30%" class="td02"></td>
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.elcg.ownerName"/>
				</td>
				<td colspan="2" class="td02"></td>
			</tr>
			<tr class="line-dot"><td colspan="4"/></tr>
			<tr>
				<td style="width:20%" height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.elcg.detailName"/>
				</td>
				<td style="width:30%" class="td02"></td>
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.elcg.ownerPhone"/>
				</td>
				<td colspan="2" class="td02" id="tdOwnerPhone"></td>
			</tr>
			<tr class="line-dot"><td colspan="4"/></tr>
			<tr>
				<td style="width:20%" height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.elcg.addr"/>
				</td>
				<td style="width:30%" class="td02"></td>
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.elcg.latitude"/>
				</td>
				<td colspan="2" class="td02" id="tdLatitude"></td>
			</tr>
			<tr class="line-dot"><td colspan="4"/></tr>
			<c:if test="${wkType eq '802102'}">
				<tr>
				<td height="25" class="td01">
				<fmt:message key="label.elcg.chargerGroupName"/>/<fmt:message key="label.elcg.mgmtNo"/>
				</td>
				<td colspan="5" class="td02"></td>
				</tr>
				<tr class="line-dot"><td colspan="4"/></tr>
			</c:if>
		</table>
		<table style="width:100%; margin:10px 0px 5px 0px">
		<tr class="line-top"><td colspan="4"/></tr>
		<tr>
			<td style="width:20%" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.installer"/>
			</td>
			<td style="width:30%" class="td02"></td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.installerPhone"/>
			</td>
			<td colspan="2" class="td02"></td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
	</table>
	</c:when>
	 <%-- status : 처리중 / 완료 --%> 
	<c:otherwise>
		<table style="width:100%; margin:10px 0px 5px 0px">
			<tr class="line-top"><td colspan="4"/></tr>
			<tr>
				<td style="width:20%" height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.elcg.buildingName"/>
				</td>
				<td style="width:30%" class="td02">${obj.bd.bdGroup.name}</td>
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.elcg.ownerName"/>
				</td>
				<td colspan="2" class="td02">${owner.name}</td>
			</tr>
			<tr class="line-dot"><td colspan="4"/></tr>
			<tr>
				<td style="width:20%" height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.elcg.detailName"/>
				</td>
				<td style="width:30%" class="td02">${obj.bd.name}</td>
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.elcg.ownerPhone"/>
				</td>
				<td colspan="2" class="td02" id="tdOwnerPhone">${owner.mobile}</td>
			</tr>
			<tr class="line-dot"><td colspan="4"/></tr>
			<tr>
				<td style="width:20%" height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.elcg.addr"/>
				</td>
				<td style="width:30%" class="td02">${obj.bd.bdGroup.addr}</td>
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.elcg.latitude"/>
				</td>
				<td colspan="2" class="td02" id="tdLatitude">${obj.bd.bdGroup.latitude} / ${obj.bd.bdGroup.longitude}</td>
			</tr>
			<tr class="line-dot"><td colspan="4"/></tr>
			<c:if test="${wkType eq '802102'}">
				<tr>
				<td height="25" class="td01">
				<fmt:message key="label.elcg.chargerGroupName"/>/<fmt:message key="label.elcg.mgmtNo"/>
				</td>
				<td colspan="5" class="td02">
					${charger.chargerGroup.name} / ${charger.chargerList.mgmtNo}
				</td>
				</tr>
				<tr class="line-dot"><td colspan="4"/></tr>
			</c:if>
		</table>
		<table style="width:100%; margin:10px 0px 5px 0px">
			<tr class="line-top"><td colspan="4"/></tr>
			<tr>
				<td style="width:20%" height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.elcg.installer"/>
				</td>
				<td style="width:30%" class="td02">${installer.name}</td>
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.elcg.installerPhone"/>
				</td>
				<td colspan="2" class="td02">${installer.mobile}</td>
			</tr>
			<tr class="line-dot"><td colspan="4"/></tr>
		</table>
	</c:otherwise>
	</c:choose>
	<div class="line-clear"></div>

	<!-- button _ start -->
	<div class="footer">	
		<table style="width:100%">
			<tr>
				<td width="50%" height="30">
					<input type="button" value='<fmt:message key="label.common.list"/>' onclick="javascript:search()"/>
				</td>
				<td width="50%" align="right">
				<c:if test="${authority.update && loginGroup.id != 2}">
					<c:choose>
						<c:when test="${ '407103' == obj.status || '409103' == obj.status}"></c:when>
						<c:otherwise> <input type="button" value='<fmt:message key="label.common.modify"/>' onclick="javascript:update()"/></c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${loginGroup.id == 2 && obj.bd.bdId != null}">
					<c:if test="${ '407102' == obj.status || '409102' == obj.status}">
						<input type="button" value="완료처리" onclick="javascript:statusComplete()"/>
					</c:if>
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