<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(document).ready(function(){
		$("#searchBtn").click(function() {
			$('#vForm').submit();
		});
	});
	
	function sendChildValue(pId, pName) {
		opener.setChildValue(pId, pName);
		window.close();
	}
</script>

<form method="get" id="vForm" name="vForm" action="/customer/histCharge/popup.htm" >

	<div class="wrap_winpop" style="overflow-y:scroll;">
		<div class="header_area">
			<h1 class="tit">카드현황</h1>
		</div>
	
		<div class="content_area">
			<table style="width:100%">
				<tr class="line-top"><td colspan="4"/></tr>
				<tr>
					<!-- 사용자 이름 -->
					<td height="25" class="td01">
						<span class="bul_dot1">◆</span>
						<fmt:message key="label.cmsUser.name"/>
					</td>
					<td class="td02">&nbsp;${member.name}</td>
					<!-- 사용자 ID -->
					<td height="25" class="td01">
						<span class="bul_dot1">◆</span>
						<fmt:message key="label.cmsUser.id"/>
					</td>
					<td class="td02">&nbsp;${member.subsId}</td>
				</tr>
			</table>		
			<display:table name="member.rfidList" id="rfid" class="simple" style="margin:5px 0pt;" requestURI="/customer/histCharge/popup.htm" >
				<display:column titleKey="label.customer.updateDate">
					<fmt:parseDate var="strDate" value="${member.lstChDt}" pattern="yyyy-MM-dd" />
					<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
				</display:column>
				<display:column titleKey="label.customer.user.cardNo" property="cardNo"/>
				<display:column titleKey="label.customer.rfid.uid" property="uid"/>
				<display:column titleKey="label.customer.os">${member.os}</display:column>
				<display:column titleKey="label.common.status">
				<c:choose>
					<c:when test="${rfid.status == '308101'}">미사용</c:when>
					<c:when test="${rfid.status == '308102'}">발급요청</c:when>
					<c:when test="${rfid.status == '308103'}">발급취소</c:when>
					<c:when test="${rfid.status == '308104'}">배송요청</c:when>
					<c:when test="${rfid.status == '308105'}">배송중</c:when>
					<c:when test="${rfid.status == '308106'}">배송완료</c:when>
					<c:when test="${rfid.status == '308107'}">사용중</c:when>
					<c:when test="${rfid.status == '308108'}">사용중지</c:when>
					<c:otherwise>-</c:otherwise>
				</c:choose>
				</display:column>
			</display:table>
		</div>
	</div>
</form>
<%@ include file="/include/footer.jspf" %>