<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function () {
		
	});
	
	// 생성 페이지로 이동
	function insert() {
		document.location = "/board/adminAppVer/create.htm";
	}
</script>
<form method="get" id="vForm" name="vForm" action="/board/adminAppVer/search.htm">
<div class="wrap00">
	<!-- list _ start -->
	<table style="width:100%">
	<c:set var="row" value="${ rownum-((page-1)*10) }"/>
	<display:table name="appVerList" id="appVer" class="simple" style="margin:5px 0pt;" requestURI="/board/adminAppVer/search.htm" pagesize="10" export="false">
		<display:column title="No.">
			<c:out value="${row}"/>
			<c:set var="row" value="${row-1}"/>
		</display:column>
		<display:column titleKey="label.common.regDate" media="html">
			<fmt:formatDate value="${appVer.fstRgDt}" pattern="yyyy.MM.dd"/>
		</display:column>
		<display:column titleKey="label.boad.target"><fmt:message key="${appVer.targetType}"/></display:column>
		<display:column titleKey="label.applicationList.os"><fmt:message key="${appVer.os}"/></display:column>
		<display:column titleKey="label.boad.version" property="ver"/>
		<display:column titleKey="label.boad.isRequired"><fmt:message key="${appVer.updateType}"/></display:column>
		<display:column titleKey="label.boad.udtBody" property="content"/>
		<display:column titleKey="label.boad.dueDateToDeply">
			<fmt:parseDate var="dateStr" value="${appVer.deployYmd}" pattern="yyyyMMdd" />
			<fmt:parseDate var="timeStr" value="${appVer.deployHhmi}" pattern="HHmm"/>
			<fmt:formatDate value="${dateStr}" pattern="yyyy.MM.dd"/>
			<fmt:formatDate value="${timeStr}" pattern="HH:mm"/>
		</display:column>
		<display:column titleKey="label.common.detail" media="html">
			<input type="button" value='<fmt:message key="label.boad.detail"/>' onclick="location.href='/board/adminAppVer/detail.htm?id=${agent.id}&seq=${appVer.snId}'"/>
			<c:if test="${authority.update}">
				<c:set var="dateDeploy" value="${appVer.deployYmd}"/>
				<fmt:parseDate var="dateNow" value="${date}" pattern="yyyyMMdd" />
				<fmt:parseDate var="dateStr2" value="${dateDeploy}" pattern="yyyyMMdd" />

				<fmt:formatDate var="dateFom1" value="${dateNow}" pattern="yyyyMMdd"/>
				<fmt:formatDate var="dateFom2" value="${dateStr2}" pattern="yyyyMMdd"/>
				<c:if test="${dateFom1<dateFom2 }" >
					<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/board/adminAppVer/update.htm?id=${agent.id}&seq=${appVer.snId}'"/>
				</c:if>
			</c:if>
		</display:column>
	</display:table>
	</table>
	<!-- list _ end -->
	
	<!-- button _ start -->
	<div class="footer">
		<table style="width:100%">
			<tr>
				<td height="30" align="right">
				<c:if test="${authority.create}">
					<input type="button" value='<fmt:message key="label.common.add"/>' onclick="javascript:insert()"/>
				</c:if>
				</td>
			</tr>
		</table>
	</div>
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>