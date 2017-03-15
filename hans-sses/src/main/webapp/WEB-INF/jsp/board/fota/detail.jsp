<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroupAuth" %>
<%@ page import="com.mobilepark.doit5.admin.model.Menu" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	// 수정 페이지로 이동
	function update() {
		location.href = "/board/fota/update.htm?snId=${boadFota.snId}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/board/fota/delete.json', '${boardFota.snId}', function() { 
			search();
		});
	}

	// 검색 페이지로 이동
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			location.href = "/board/fota/search.htm?" + callbackUrl;
		} else {
			location.href = "/board/fota/search.htm";
		}
	}
</script>
<div class="wrap00">
	<!-- list _ start -->
	<table style="width:100%">
		<!-- title -->
		<tr class="line-top"><td colspan="2"/></tr>
		<tr class="line-dot"><td colspan="2"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				파일명
			</td>
			<td class="td02">
				${boadFota.fileName}
			</td>
		</tr>
		<tr class="line-dot"><td colspan="2"/></tr>
		<!-- body -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				버전 정보
			</td>
			<td class="td02">
				${boadFota.ver}
			</td>
		</tr>
		<tr class="line-dot"><td colspan="2"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				업데이트 내용
			</td>
			<td class="td02">
				${boadFota.content}
			</td>
		</tr>
		<tr class="line-dot"><td colspan="2"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				FOTA업로드
			</td>
			<td class="td02">
				${boadFota.fileName}
				<input type="button" value='저장' onclick="location.href='/board/fota/down.htm?snId=${boadFota.snId}'"/>	
			</td>
		</tr>
		<tr class="line-dot"><td colspan="2"/></tr>
		
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