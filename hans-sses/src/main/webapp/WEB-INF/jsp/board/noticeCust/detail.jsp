<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroupAuth" %>
<%@ page import="com.mobilepark.doit5.admin.model.Menu" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	// 수정 페이지로 이동
	function update() {
		location.href = "/board/noticeCust/update.htm?id=${boadNotice.snId}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/board/noticeCust/delete.json', '${boadNotice.snId}', function() { 
			search();
		});
	}

	// 검색 페이지로 이동
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			location.href = "/board/noticeCust/search.htm?" + callbackUrl;
		} else {
			location.href = "/board/noticeCust/search.htm";
		}
	}
</script>
<div class="wrap00">
	<!-- list _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<!-- 제목 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.contentEditor.title"/>
			</td>
			<td colspan="2" class="td02">${boadNotice.title}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.regDate"/>
			</td>
			<td colspan="2" class="td02"><fmt:formatDate value="${boadNotice.fstRgDt}" pattern="yyyy.MM.dd"/></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- 내용 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.contentEditor.content"/>
			</td>
			<td colspan="5" class="td02">
				<textarea name="body" readonly="readonly">${boadNotice.body}</textarea>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- status y/n -->
		<c:if test="${loginGroup.id == 1 }">
			<tr>
				<td height="25" class="td01">
				<span class="bul_dot1">◆</span> 노출여부</td>
				<td colspan="5" class="td02">
					 <c:if test="${ 'Y' eq boadNotice.displayYn }" >노출</c:if>
					 <c:if test="${ 'N' eq boadNotice.displayYn }" >비노출</c:if>
				</td>
			</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		</c:if>
	</table>
	<!-- list _ end -->

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
					<input type="button" value='<fmt:message key="label.common.delete"/>' onclick="javascript:confirmAndDelete()"/>
				</c:if>
				</td>
			</tr>
		</table>
	</div>
	<!-- button _ end -->
</div>
<%@ include file="/include/footer.jspf" %>