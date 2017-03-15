<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	//수정 페이지로 이동
	function update() {
		location.href = "/elcg/building/update.htm?seq=${bd.bdId}";
	}
	
	// 삭제
	function confirmAndDelete() {
		deleteById('/elcg/building/delete.json', '${bd.bdId}', function() { 
			search();
		});
	}
	
	// 검색 페이지로 이동
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			location.href = "/elcg/building/search.htm?" + callbackUrl;
		} else {
			location.href = "/elcg/building/search.htm";
		}
	}
</script>
<spring:hasBindErrors name="bd"/>

<div class="wrap00">
	<input type="hidden" name="id" value="${bd.bdId}"/>
	<!-- start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.buildingName"/>
			</td>
			<td colspan="2" class="td02"> ${bd.bdGroup.name}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.ownerName"/>
			</td>
			<td colspan="2" class="td02"> ${owner.name}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.detailName"/>
			</td>
			<td colspan="2" class="td02"> ${bd.name}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.ownerPhone"/>
			</td>
			<td colspan="2" class="td02">${owner.mobile}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.addr"/>
			</td>
			<td colspan="2" class="td02">
			<c:if test="${bd.addr == null}">${bd.bdGroup.addr}</c:if>
			<c:if test="${bd.addr != null}">${bd.addr}</c:if>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.latitude"/>
			</td>
			<td colspan="2" class="td02">${bd.latitude}  /  ${bd.longitude}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.buildingRegDate"/>
			</td>
			<td colspan="2" class="td02">
				<fmt:formatDate value="${bd.fstRgDt}" pattern="yyyy.MM.dd"/>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerGroupCnt"/>
			</td>
			<td colspan="2" class="td02">${bd.chargerGroupSize}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerCnt"/>
			</td>
			<td colspan="2" class="td02">${bd.chargerSize}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.lstInsDate"/>
			</td>
			<td colspan="2" class="td02">
				<fmt:parseDate var="strDate" value="${bd.lstInsDate}" pattern="yyyyMMddHHmm" />
				<fmt:formatDate value="${strDate}" pattern="yyyy.MM.dd HH:mm"/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.paymentDate"/>
			</td>
			<td colspan="2" class="td02">
				<c:if test="${'31' ne periodDay }">매월 ${periodDay}일</c:if>
				<c:if test="${'31' eq periodDay }">매월 말일</c:if>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.lstInstaller"/>
			</td>
			<td colspan="2" class="td02">${bd.lstInstaller}</td>
		</tr>	
		<tr class="line-dot"><td colspan="6"/></tr>
	</table>
	
	<c:if test="${bd.chargerGroupSize > 0}"><br/>
		<table style="width:100%">
			<tr class="line-top"><td colspan="6"/></tr>
			<tr>
				<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerGroupName"/>
				</td>
				<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.mgmtNo"/>
				</td>
			</tr>
			<tr class="line-dot"><td colspan="6"/></tr>
			
			<c:forEach items="${bd.chargerGroupList}" var="chargerGroup" varStatus="status">
					<tr>
						<td class="td02">${chargerGroup.name}</td>
						<td class="td02">
							<c:forEach items="${chargerGroup.chargerList}" var="charger" varStatus="cgStatus">
								${charger.chargerList.mgmtNo}
								<br/>
							</c:forEach>
						</td>
					</tr>
					<tr class="line-dot"><td colspan="6"/></tr>
			</c:forEach>
			<tr class="line-dot"><td colspan="6"/></tr>
		</table>
	</c:if>
	<!-- end -->

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