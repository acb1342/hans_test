<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroupAuth" %>
<%@ page import="com.mobilepark.doit5.admin.model.Menu" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	// 수정 페이지로 이동
	function update() {
		location.href = "/board/faqCust/update.htm?id=${boadFaq.snId}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/board/faqCust/delete.json', '${boadFaq.snId}', function() { 
			search();
		});
	}

	// 검색 페이지로 이동
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			location.href = "/board/faqCust/search.htm?" + callbackUrl;
		} else {
			location.href = "/board/faqCust/search.htm";
		}
	}
</script>
<div class="wrap00">
	<!-- list _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<!-- 카테고리 -->
		<tr>
			<td height="25" class="td01">카테고리</td>
			<td colspan="5" class="td02">
				<c:choose>	
					<c:when test="${boadFaq.category eq '601100' || boadFaq.category eq '0' || boadFaq.category eq ''}" >전체</c:when>
					<c:otherwise><fmt:message key="${boadFaq.category}"/></c:otherwise>
				</c:choose>
			</td>
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
				<span class="bul_dot1">◆</span>	<fmt:message key="label.common.regDate"/></td>
			<td colspan="5" class="td02"><fmt:formatDate value="${boadFaq.fstRgDt}" pattern="yyyy.MM.dd"/></td>
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
		
		<c:if test="${loginGroup.id == 1 }">
		<tr>
			<td height="25" class="td01">
			<span class="bul_dot1">◆</span>노출여부</td>
			<td colspan="5" class="td02">
				<c:if test="${'Y' eq boadFaq.displayYn }">공개</c:if>
				<c:if test="${'N' eq boadFaq.displayYn }">비공개</c:if>
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
					<input type="button" class="btn_red" value='<fmt:message key="label.common.delete"/>' onclick="javascript:confirmAndDelete()"/>
				</c:if>
				</td>
			</tr>
		</table>
	</div>
	<!-- button _ end -->
</div>
<%@ include file="/include/footer.jspf" %>