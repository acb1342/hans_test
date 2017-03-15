<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroup" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {

	});

	// 수정 페이지로 이동
	function update() {
		window.location.href = "/customer/rfidApplication/update.htm?id=${rfidApplication.id}";
	}

	// 삭제
	function confirmAndDelete() {
		if(confirm("해당 카드 정보를 삭제하시겠습니까?") == false) return;
		deleteById('/customer/rfidApplication/delete.json', '${rfidApplication.id}', function() { 
			search();
		});
	}

	// 검색 페이지로 이동
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			location.href = "/customer/rfidApplication/search.htm?" + callbackUrl;
		} else {
			location.href = "/customer/rfidApplication/search.htm";
		}
	}
</script>
<div class="wrap00">
	<!-- list _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="4"/></tr>
		<tr>
			<!-- 회원카드 SN -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.user.cardNo"/>
			</td>
			<td class="td02">&nbsp;${rfidApplication.cardNo}</td>
			<!-- RFID UID -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.rfid.uid"/>
			</td>
			<td class="td02">&nbsp;${rfidApplication.rfidCard.uid}</td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
		<tr>
			<!-- 카드 상태 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.status"/>
			</td>
			<td class="td02">
			<c:choose>
				<c:when test="${rfidApplication.status == '308101'}">미사용</c:when>
				<c:when test="${rfidApplication.status == '308102'}">발급요청</c:when>
				<c:when test="${rfidApplication.status == '308103'}">발급취소</c:when>
				<c:when test="${rfidApplication.status == '308104'}">배송요청</c:when>
				<c:when test="${rfidApplication.status == '308105'}">배송중</c:when>
				<c:when test="${rfidApplication.status == '308106'}">배송완료</c:when>
				<c:when test="${rfidApplication.status == '308107'}">사용중</c:when>
				<c:when test="${rfidApplication.status == '308108'}">사용중지</c:when>
				<c:otherwise>-</c:otherwise>
			</c:choose>
			</td>
			<!-- 신청일 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.app.date"/>
			</td>
			<td class="td02">&nbsp;${rfidApplication.rcDt}</td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
		<tr>
			<!-- 사용자명 / OS -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.user.name"/> / <fmt:message key="label.customer.os"/>
			</td>
			<td class="td02">&nbsp;${rfidApplication.member.name} / ${rfidApplication.member.os}</td>
			<!-- 배송요청 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.delivery"/>
			</td>
			<td class="td02">&nbsp;
			<c:choose>
				<c:when test="${rfidApplication.status == '308104'}">요청 완료</c:when>
				<c:when test="${rfidApplication.status == '308105'}">요청 완료</c:when>
				<c:when test="${rfidApplication.status == '308106'}">요청 완료</c:when>
				<c:when test="${rfidApplication.status == '308107'}">요청 완료</c:when>
			</c:choose>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
		<tr>
			<!-- 배송 요청일 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.delivery.reqDate"/>
			</td>
			<td class="td02">&nbsp;
				<fmt:parseDate var="strDate" value="${rfidApplication.odDt}" pattern="yyyy-MM-dd" />
				<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
			</td>
			<!-- 배송 처리일 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.delivery.processDate"/>
			</td>
			<td class="td02">&nbsp;
				<fmt:parseDate var="strDate" value="${rfidApplication.wkDt}" pattern="yyyy-MM-dd" />
				<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
			</td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
		<tr>
			<!-- 등록일 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.regi.date"/>
			</td>
			<td class="td02">&nbsp;
				<fmt:parseDate var="strDate" value="${rfidApplication.fstRgDt}" pattern="yyyy-MM-dd" />
				<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
			</td>
			<!-- 중지일 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.stop.date"/>
			</td>
			<td class="td02">&nbsp;
				<fmt:parseDate var="strDate" value="${rfidApplication.stDt}" pattern="yyyy-MM-dd" />
				<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
			</td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
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