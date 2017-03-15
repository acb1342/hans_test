<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroup" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {

		$('#card_detail').click(function(e) {
			openWindow("/customer/member/cardDetail.htm?id=${member.id}", "cardDetail", "width=648,height=448,top=100,left=100");
		});
		$('#card_delete').click(function(e) {
			openWindow("/customer/member/cardDelete.htm?id=${member.id}", "cardDelete", "width=448,height=448,top=100,left=100");
		});
	});

	// 수정 페이지로 이동
	function update() {
		window.location.href = "/customer/member/update.htm?id=${member.id}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/customer/member/delete.json', '${member.id}', function() { 
			search();
		});
	}

	// 검색 페이지로 이동
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			location.href = "/customer/member/search.htm?" + callbackUrl;
		} else {
			location.href = "/customer/member/search.htm";
		}
	}
	
	function setDeviceId() {
		$("#card_delete_td").text("");
	}
</script>
<div class="wrap00">
	<!-- list _ start -->
	<!-- 기본 정보 -->
	<p>* <fmt:message key="label.customer.basic.info"/></p>
	<table style="width:100%">
		<tr class="line-top"><td colspan="4"/></tr>
		<tr>
			<!-- 사용자 ID -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsUser.id"/>
			</td>
			<td class="td02">&nbsp;${member.subsId}</td>
			<!-- 사용자 이름 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsUser.name"/>
			</td>
			<td class="td02">&nbsp;${member.name}</td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
		<tr>
			<!-- 가입일 / 탈퇴 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.udt"/>/<fmt:message key="label.customer.close.udt"/>
			</td>
			<td class="td02">&nbsp;
				<c:if test="${member.fstRgDt != null}">
					<fmt:parseDate var="strDate" value="${member.fstRgDt}" pattern="yyyy-MM-dd" />
					<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
				</c:if>
				<c:if test="${member.fstRgDt == null}">-</c:if>
				&nbsp;/&nbsp;
				<c:if test="${member.close.fstRgDt != null}">
					<fmt:parseDate var="strDate" value="${member.close.fstRgDt}" pattern="yyyy-MM-dd" />
					<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
				</c:if>
				<c:if test="${member.close.fstRgDt == null}">-</c:if>
			</td>
			<!-- 회원 상태 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.status"/>
			</td>
			<td class="td02">&nbsp;
			<c:choose>
				<c:when test="${member.status == '301101'}">준회원</c:when>
				<c:when test="${member.status == '301102'}">정회원</c:when>
				<c:when test="${member.status == '301103'}">중지(탈퇴)</c:when>
				<c:otherwise>-</c:otherwise>
			</c:choose>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
		<tr>
			<!-- 휴대전화 / OS -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.mobile"/>/<fmt:message key="label.customer.os"/>
			</td>
			<td class="td02">&nbsp;
				${fn:length(member.mdn) == 0 ? "-" : member.mdn}
				&nbsp;/&nbsp;
				${fn:length(member.os) == 0 ? "-" : member.os}
			</td>
			<!-- 이메일 주소 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.email"/>
			</td>
			<td class="td02">&nbsp;${member.emailAddr}</td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
	</table>
	<br/>
	<!-- 결제 정보 -->
	<p>* <fmt:message key="label.customer.payment.info"/></p>
	<table style="width:100%">
		<tr class="line-top"><td colspan="4"/></tr>
		<tr>
			<!-- 결제 방식 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.payment.plan"/>
			</td>
			<td class="td02">&nbsp;
			<c:choose>
				<c:when test="${member.paymentPlan == '301201'}">선불</c:when>
				<c:when test="${member.paymentPlan == '301202'}">후불</c:when>
				<c:otherwise>-</c:otherwise>
			</c:choose>
			&nbsp;/&nbsp;
			<c:choose>
				<c:when test="${member.paymentMethod == '301301'}">신용카드</c:when>
				<c:when test="${member.paymentMethod == '301302'}">포인트</c:when>
				<c:when test="${member.paymentMethod == '301302'}">소액결재</c:when>
				<c:otherwise>-</c:otherwise>
			</c:choose>
			</td>
			<!-- 포인트 잔액 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.remain.point"/>
			</td>
			<td class="td02">&nbsp;${member.point.remainPoint}</td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
	</table>
	<br/>
	<!-- 차량 정보 -->
	<p>* <fmt:message key="label.customer.car.info"/></p>
	<table style="width:100%">
		<tr class="line-top"><td colspan="4"/></tr>
		<c:forEach items="${member.carList}" var="car">
		<tr>
			<!-- 차량 번호 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.car.number"/>
			</td>
			<td class="td02">&nbsp;${car.carNo}</td>
			<!-- 차량 모델명 -->
			<%--
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.car.model"/>
			</td>
			<td class="td02">&nbsp;</td>
			--%>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
		</c:forEach>
	</table>
	<br/>
	<!-- 인증 정보 -->
	<p>* <fmt:message key="label.customer.rfid.info"/></p>
	<table style="width:100%">
		<tr class="line-top"><td colspan="4"/></tr>
		<tr>
			<!-- NFC 발급 여부 -->
			<td height="25" class="td01" style="width: 20%">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.rfid.nfc"/>
			</td>
			<td class="td02" style="width: 40%">
			<c:choose>
				<c:when test="${fn:length(nfcList) > 0}">HCE NFC 발급 완료</c:when>
				<c:otherwise>HCE NFC 미발급</c:otherwise>
			</c:choose>
			</td>
			<!-- Device ID  -->
			<td height="25" class="td01" style="width: 10%">
				<span class="bul_dot1">◆</span>Device ID
			</td>
			<td class="td02" id="card_delete_td" style="width: 30%">
				${member.deviceId} &nbsp;&nbsp;&nbsp;
				<c:if test="${fn:length(member.deviceId) > 0}"><input type="button" id="card_delete" value='삭제'/></c:if>
			</td>
		</tr>
		<tr>
			<!-- 발급 이력  -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				HCE NFC 발급 이력
			</td>
			<td class="td02">
				<c:forEach items="${nfcList}" var="nfc">
				${nfc.FST_RG_DT}
				<c:choose>
					<c:when test="${nfc.LOG_TYPE == '313207'}">NFC 신규발급</c:when>
					<c:when test="${nfc.LOG_TYPE == '313208'}">NFC 초기화 (${nfc.ADD_INFO})</c:when>
					<c:otherwise>-</c:otherwise>
				</c:choose>
				<br/>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<!-- 사용중인 회원 카드 수  -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				사용중인 회원 카드 수
			</td>
			<td class="td02">
				&nbsp;${fn:length(member.rfidList)}&nbsp;&nbsp;&nbsp;&nbsp;
				<c:if test="${fn:length(member.rfidList) > 0}">
				<input type="button" id="card_detail" value='<fmt:message key="label.customer.rfid.detail"/>'/>
				</c:if>
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
				<%--
				<td width="50%" align="right">
				<c:if test="${authority.update}">
					<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="javascript:update()"/>
				</c:if>
				<c:if test="${authority.delete}">
					<input type="button" value='<fmt:message key="label.common.delete"/>' onclick="javascript:confirmAndDelete()"/>
				</c:if>
				</td>
				--%>					
			</tr>
		</table>
	</div>
	<!-- button _ end -->
</div>
<%@ include file="/include/footer.jspf" %>