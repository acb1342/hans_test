<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 저장
		$('#save').click(function(e) {
			$("#vForm").submit();
		});
	});

	// 검색 페이지로 이동
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			location.href = "/customer/member/search.htm?" + callbackUrl;
		} else {
			location.href = "/customer/member/search.htm";
		}
	}

	function cancel() {
		history.back();
	}
</script>
<spring:hasBindErrors name="member"/>
<form method="post" id="vForm" name="vForm" action="/customer/member/update.htm">
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
				<fmt:message key="label.customer.udt"/> / <fmt:message key="label.customer.close.udt"/>
			</td>
			<td class="td02">&nbsp;${member.fstRgDt} / ${member.close.fstRgDt}</td>
			<!-- 회원 상태 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.status"/>
			</td>
			<td class="td02">&nbsp;
				<select id="status" name="status">
				<c:forEach items="${statusList}" var="status">
					<option value="${status.key}" ${status.key == member.status? 'selected':''}>${status.value}</option>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
		<tr>
			<!-- 휴대전화 / OS -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.mobilePhone"/> / <fmt:message key="label.customer.os"/>
			</td>
			<td class="td02">&nbsp;${member.mdn} / ${member.os}</td>
			<!-- 이메일 주소 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.email"/>
			</td>
			<td class="td02">&nbsp;${member.emailAddr}</td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
	</table>
	<br/>
	<!-- 결재 정보 -->
	<p>* <fmt:message key="label.customer.payment.info"/></p>
	<table style="width:100%">
		<tr class="line-top"><td colspan="4"/></tr>
		<tr>
			<!-- 결재 방식 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.payment.plan"/>
			</td>
			<td class="td02">&nbsp;${member.paymentPlan} / ${member.paymentMethod}</td>
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
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.car.model"/>
			</td>
			<td class="td02">&nbsp;</td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
		</c:forEach>
	</table>
	<br/>
	<!-- 인증 정보 -->
	<p>* <fmt:message key="label.customer.rfid.info"/></p>
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<!-- NFC 발급 여부 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.rfid.nfc"/>
			</td>
			<td class="td02">&nbsp;
				${member.paymentPlan} / ${member.paymentMethod}
			</td>
			<td class="td02">
				<input type="button" id="nfc_init" value='<fmt:message key="label.customer.rfid.init"/>'/>
			</td>
			<!-- 보유 회원카드수  -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.customer.rfid.count"/>
			</td>
			<td class="td02">&nbsp;${fn:length(member.rfidList)}</td>
			<td class="td02">
				<input type="button" id="card_detail" value='<fmt:message key="label.customer.rfid.detail"/>'/>
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
				<td width="50%" height="30"></td>
				<td width="50%" align="right">	
				<c:if test="${authority.update}">
					<input type="button" id="save" value='<fmt:message key="label.common.save"/>'/>
				</c:if>
				<c:if test="${authority.delete}">
					<input type="button" value='<fmt:message key="label.common.cancel"/>' onclick="javascript:cancel()"/>
				</c:if>	
				</td>
			</tr>
		</table>
	</div>				
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>