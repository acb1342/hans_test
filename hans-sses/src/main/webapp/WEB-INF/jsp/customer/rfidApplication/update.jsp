<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<style type="text/css" media="all">
	@importA url("/js/jquery/ui/jquery-ui-timepicker-addon.css");
</style>
<script type="text/javascript" src="/js/jquery/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript">
	$(function() {
		$('#fromDate').datetimepicker({
			dateFormat: 'yy/mm/dd'
		});
		
		// 배송 요청
		$('#delivery').click(function(e) {
			if(confirm("배송요청 하시겠습니까?") == false) return;
 			$.ajax({
				type:'POST',
				url:'/customer/rfidApplication/delivery.json',
				success:function (data) {
					if(data) {
						$("#odDt").text(data.odDt.substring(0,4) + "-" + data.odDt.substring(4,6) + "-" + data.odDt.substring(6,8));
						$("select[name=status]").val(data.status);
						if (data.status == "308104" || data.status == "308105" || 
							data.status == "308106" || data.status == "308107") {
							$("#statusTxt").text("요청완료");
						}
						alert("배송요청이 완료되었습니다.");
					}
				},
				error: function(e) {
					alert("error!!!");
				}
			});
 		});
		// 저장
		$('#save').click(function(e) {
			if(confirm("변경사항으로 저장하시겠습니까?") == false) return;
 			$.ajax({
				type:'POST',
				url:'/customer/rfidApplication/update.json',
				data: {
					status: $("select[name=status]").val()
				},
				success:function (data) {
					if(data) {
						alert("해당 카드 정보가 수정되었습니다.");
					}
				},
				error: function(e) {
					alert("error!!!");
				}
			});
			
			search();
		});
	});

	// 검색 페이지로 이동
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			location.href = "/customer/rfidApplication/search.htm?" + callbackUrl;
		} else {
			location.href = "/customer/rfidApplication/search.htm";
		}
	}

	function cancel() {
		history.back();
	}
</script>
<spring:hasBindErrors name="member"/>
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
			<td class="td02">&nbsp;
				<select id="status" name="status">
				<c:forEach items="${statusList}" var="status">
					<option value="${status.key}" ${status.key == rfidApplication.status? 'selected':''}>${status.value}</option>
				</c:forEach>
				</select>
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
			<td class="td02" id="statusTxt">&nbsp;
			<c:choose>
				<c:when test="${rfidApplication.status == '308102'}">
				<input type="button" id="delivery" value='<fmt:message key="label.customer.delivery"/>'/>
				</c:when>
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
			<td class="td02" id="odDt">&nbsp;
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
			<td class="td02">&nbsp;${rfidApplication.fstRgDt}</td>
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
				<td width="50%" height="30"></td>
				<td width="50%" align="right">	
				<c:if test="${authority.update}">
					<input type="button" id="save" value='<fmt:message key="label.common.modify"/>'/>
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
<%@ include file="/include/footer.jspf" %>