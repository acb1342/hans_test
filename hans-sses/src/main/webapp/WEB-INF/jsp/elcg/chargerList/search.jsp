<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>

<script type="text/javascript">

	// 생성 페이지로 이동
	function insert() {
		document.location = "/elcg/chargerList/create.htm";
	}

	// 검색
	function search() {
		$('#vForm').submit();
	}

	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/elcg/chargerList/delete.json', function() { 
			search(); 
		});
	}
	
</script>

<form method="get" id="vForm" name="vForm" action="/elcg/chargerList/search.htm">
	<!-- search _ start -->
	<input type="hidden" name="id" id="id"/>
	<div class="wrap00">
	
	<!-- search _ start -->
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<c:set var="searchOptionKeys">0,402101,402102</c:set>
				<c:set var="searchOptionValues">
				<fmt:message key="label.all"/>,
				<fmt:message key="label.elcg.noComIns"/>,
				<fmt:message key="label.elcg.comIns"/>
				</c:set>
				<c:set var="searchType" value='<%=request.getParameter("searchType")%>'/>
				<c:set var="searchValue" value='<%=request.getParameter("searchValue")%>'/>
				<td class="td00">
					<select name="searchType" id="searchType" >
					<c:forTokens var="key" items="${searchOptionKeys}" delims="," varStatus="statKey">
						<c:forTokens var="val" items="${searchOptionValues}" delims="," varStatus="statVal">
							<c:if test="${statKey.index == statVal.index}">
								<option value="${key}" ${key == searchType ? 'selected':''}>${val}</option>
							</c:if>
						</c:forTokens>
					</c:forTokens>
					</select>
				</td>
				<td align="left">
					<input type="text" name="searchValue" id="searchValue" value="${searchValue}" style="margin:0 7px 0 8px;" placeholder='<fmt:message key="label.elcg.sn"/>'/>
					<input type="button" value='<fmt:message key="label.common.search"/>' onclick="javascript:search()"/>	
					
				</td>
			</tr>
		</table>
	</fieldset><br/>
	<!-- search _ end -->
	
	<!-- list _ start -->
	<tr class="line-top"><td colspan="6"/></tr>
	<c:choose>
		<c:when test="${ type eq 'default' }">
			<table style="width:100%">
			<tr>
				<td height="25" class="td01"><fmt:message key="label.elcg.totalChargerCount"/></td>
				<td class="td02">${total}</td>
				<td height="25" class="td01"><fmt:message key="label.elcg.installedChargerCount"/></td>
				<td class="td02">${installed}</td>
				<td height="25" class="td01"><fmt:message key="label.elcg.noInstalledChargerCount"/></td>
				<td class="td02">${noInstalled}</td>
			</tr>
			</table>
		</c:when>
		<c:otherwise>
			<c:set var="row" value="${ rownum-((page-1)*10) }"/>
			<display:table name="chargerLists" id="chargerList" class="simple" style="margin:5px 0pt;" requestURI="/elcg/chargerList/search.htm" pagesize="10" export="false">
				<c:if test="${authority.delete}">
					<display:column titleKey="label.common.select" style="width:40px;" media="html">
						<input type="checkbox" id="selected" name="selected" style="width:15px;" value="${chargerList.chargerId}"/>
					</display:column>
				</c:if>
				<display:column titleKey="label.common.seq" >
					<c:out value="${row}"/>
					<c:set var="row" value="${row-1}"/>
				</display:column>
				<display:column titleKey="label.elcg.sn" property="serialNo"/>
				<display:column titleKey="label.elcg.mgmtNo" property="mgmtNo"/>
				<display:column titleKey="label.elcg.modelName" property="model.model"/>
				<display:column titleKey="label.elcg.chargeRate"><fmt:message key="${chargerList.chargeRate}"/></display:column>
				<display:column titleKey="label.elcg.max" property="capacity" />
				<display:column titleKey="label.elcg.status"><fmt:message key="${chargerList.status}"/></display:column>
				<display:column titleKey="label.common.detail" style="text-align:center; width:140px;" media="html">
					<input type="button" value='<fmt:message key="label.common.detail"/>' onclick="location.href='/elcg/chargerList/detail.htm?id=${agent.id}&seq=${chargerList.chargerId}'"/>
				<c:if test="${authority.update}">
					<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/elcg/chargerList/update.htm?id=${agent.id}&seq=${chargerList.chargerId}'"/>
				</c:if>
				</display:column>
			</display:table>
		</c:otherwise>
	</c:choose>
	<!-- list _ end -->
	
	<!-- button _ start -->
	<div class="footer">
		<table style="width:100%">
			<tr>
				<td height="30" align="right">
				<c:if test="${authority.delete}">
					<input type="button" class="btn_red" value='<fmt:message key="label.common.delete"/>' onclick="javascript:confirmAndDelete()"/>
				</c:if>
				</td>
			</tr>
		</table>
	</div>
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>