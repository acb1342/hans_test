<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroupAuth" %>
<%@ page import="com.mobilepark.doit5.admin.model.Menu" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	// 수정 페이지로 이동
	function update() {
		location.href = "/board/notice/update.htm?seq=${boadNotice.sn_id}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/board/notice/delete.json', '${boadNotice.sn_id}', function() { 
			search();
		});
	}

	// 검색 페이지로 이동
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			location.href = "/board/notice/search.htm?" + callbackUrl;
		} else {
			location.href = "/board/notice/search.htm";
		}
	}
</script>
<div class="wrap00">
	<!-- list _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<c:if test="${loginGroup.id == 1 }">
			<tr>
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>노출대상</td>
				<td colspan="5" class="td02">
					<c:if test="${'Y' eq boadNotice.cust_yn }">사용자</c:if>
					<c:if test="${'Y' eq boadNotice.owner_yn }">건물주</c:if>
					<c:if test="${'Y' eq boadNotice.installer_yn }">설치자</c:if>
					<c:if test="${'Y' eq boadNotice.counselor_yn }">상담사</c:if>
				</td>
			</tr>
			<tr class="line-dot"><td colspan="6"/></tr>
		</c:if>
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
			<td colspan="2" class="td02"><fmt:formatDate value="${boadNotice.fstRgDt}" pattern="yyyy/MM/dd HH:mm"/></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- 내용 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.contentEditor.content"/>
			</td>
			<td colspan="5" class="td02" height="350px" style="overflow:auto;">
				<div style="display:inline-block; height:330px; margin-top:10px;">${boadNotice.body}</div>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- status y/n -->
		<c:if test="${loginGroup.id == 1 }">
			<tr>
				<td height="25" class="td01">
				<span class="bul_dot1">◆</span> 노출여부</td>
				<td colspan="5" class="td02">
					 <c:if test="${ 'Y' eq boadNotice.display_yn }" >노출</c:if>
					 <c:if test="${ 'N' eq boadNotice.display_yn }" >비노출</c:if>
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