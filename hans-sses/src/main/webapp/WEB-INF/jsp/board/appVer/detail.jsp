<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
//수정 페이지로 이동
function update() {
	location.href = "/board/appVer/update.htm?seq=${appVer.snId}";
}

// 검색 페이지로 이동
function search() {
	var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
	if (callbackUrl != 'null') {
		location.href = "/board/appVer/search.htm?" + callbackUrl;
	} else {
		location.href = "/board/appVer/search.htm";
	}
}
</script>

<div class="wrap00">
	<!-- input _ first -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.os"/>
			</td>
			<td class="td02"><fmt:message key="${appVer.os}"/></td>
			<td height="25" class="td01" nowrap>
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.boad.inputVersion"/>
			</td>
			<td class="td02">${appVer.ver}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.boad.isRequired"/>
			</td>
			<td class="td02"><fmt:message key="${appVer.updateType}"/></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.regDate"/>
			</td>
			<td colspan="5" class="td02"><fmt:formatDate value="${appVer.fstRgDt}" pattern="yyyy.MM.dd"/></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01" nowrap>
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.boad.udtBody"/>
			</td>
			<td colspan="5" class="td02">
				<textarea id="content" name="content" readonly="readonly" >${appVer.content}</textarea>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.boad.dueDateToDeply"/>
			</td>
			<td colspan="5" class="td02">
				<fmt:parseDate var="dateStr" value="${appVer.deployYmd}" pattern="yyyyMMdd" />
				<fmt:parseDate var="timeStr" value="${appVer.deployHhmi}" pattern="HHmm"/>
				<fmt:formatDate value="${dateStr}" pattern="yyyy.MM.dd"/>
				<fmt:formatDate value="${timeStr}" pattern="HH:mm"/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.boad.binaryFile"/>(URL)
			</td>
			<td colspan="5" class="td02"><a href="${appVer.url}" target="_blank">${appVer.url}</a></td>
		</tr>
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
				</td>
			</tr>
		</table>
	</div>
	<!-- button _ end -->
</div>

<%@ include file="/include/footer.jspf" %>