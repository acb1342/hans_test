<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroupAuth" %>
<%@ page import="com.mobilepark.doit5.admin.model.Menu" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	// 수정 페이지로 이동
	function update() {
		location.href = "/board/FAQ/update.htm?seq=${boadFaq.sn_id}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/board/FAQ/delete.json', '${boadFaq.sn_id}', function() { 
			search();
		});
	}

	// 검색 페이지로 이동
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			location.href = "/board/FAQ/search.htm?" + callbackUrl;
		} else {
			location.href = "/board/FAQ/search.htm";
		}
	}
</script>
<div class="wrap00">
	<!-- list _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<!-- 노출대상 -->
		<tr>
			<td height="25" class="td01">
				<c:set var="displayWho" value='<%=request.getParameter("displayWho")%>'/>
				<b>노출대상</b>&nbsp;
			</td>
			<td colspan="2" height="25" class="td02">
			<c:choose>
				<c:when test="${'Y' eq boadFaq.cust_yn && 'Y' eq boadFaq.owner_yn && 'Y' eq boadFaq.installer_yn }">전체
			</c:when>
			<c:otherwise>
				<c:if test="${'Y' eq boadFaq.cust_yn }">사용자</c:if>
				<c:if test="${'Y' eq boadFaq.owner_yn }">건물주</c:if>
				<c:if test="${'Y' eq boadFaq.installer_yn }">설치자</c:if>
				<c:if test="${'Y' eq boadFaq.counselor_yn }">상담사</c:if>
			</c:otherwise>
			</c:choose>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>카테고리			
			</td>
			<td colspan="2" class="td02"><fmt:message key="${boadFaq.category}"/></td>	
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
	
		<!-- 제목 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.contentEditor.title"/>
			</td>
			<td colspan="5" class="td02">${boadFaq.question}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- 생성일 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>	<fmt:message key="label.common.createDate"/></td>
			<td colspan="5" class="td02"><fmt:formatDate value="${boadFaq.fstRgDt}" pattern="yyyy/MM/dd"/></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- 내용 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.contentEditor.content"/>
			</td>
			<td colspan="5" class="td02">
				<textarea name="description" readonly="readonly">${boadFaq.answer}</textarea>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		
		<tr>
			<td height="25" class="td01">
			<span class="bul_dot1">◆</span>노출여부</td>
			<td colspan="5" class="td02">
				<c:if test="${'Y' eq boadFaq.disply_yn }">공개</c:if>
				<c:if test="${'N' eq boadFaq.disply_yn }">비공개</c:if>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
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
					<input type="button" class="btn_red" value='<fmt:message key="label.common.delete"/>' onclick="javascript:confirmAndDelete()"/>
				</c:if>
				</td>
			</tr>
		</table>
	</div>
	<!-- button _ end -->
</div>
<%@ include file="/include/footer.jspf" %>