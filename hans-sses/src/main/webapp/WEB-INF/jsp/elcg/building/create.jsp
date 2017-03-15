<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 건물주 정보
		$('#ownerName').click(function(e) {
			window.open("/elcg/building/ownerInfoPopup.htm","new","width=448,height=448,top=100,left=100");
		});
		
		// 저장
		$('#save').click(function(e) {
			if(!validationCheck()) return;
			if(!confirm("입력하신 내용으로 등록하시겠습니까?")) return;
				
			$("#vForm").submit();
			alert("입력하신 내용으로 등록되었습니다.");
		});
		
		// 이전 페이지로 이동
		$('#cancel').click(function(e) {
			if(confirm("정말 취소하시겠습니까?")) window.location.href = "/elcg/building/search.htm";
		});
	});
	
	function validationCheck() {
		if( !$("#bdGroupName").val() ) {
			alert("건물/아파트명을 입력해주세요.");
			return;
		}
		if( !$("#bdName").val() ) {
			alert("상세/동명을 입력해주세요.");
			return;
		}
		if( !$("#ownerName").val() ) {
			alert("건물주명을 입력하세요!");
			return;
		}
		if( !$("#ownerPhone").val() ) {
			alert("건물주 연락처를 입력하세요!");
			return;
		}
		if( !$("#addr").val() ) {
			alert("주소를 입력해주세요.");
			return;
		}
		if( !$("#latitude").val() || !$("#longitude").val()) {
			alert("위도/경도를 입력해주세요.");
			return;
		}

		return true;
	}
	
	function setChildValueOwner(id, name, mobile) {
		document.getElementById("ownerId").value = id;
		document.getElementById("ownerName").value = name;
		document.getElementById("ownerPhone").value = mobile;
	}
</script>
<spring:hasBindErrors name="bd"/>
<form method="post" id="vForm" name="vForm" action="/elcg/building/create.htm" >
<div class="wrap00">
	<input type="hidden" id="ownerId" name="ownerId"/>
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.buildingName"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="bdGroupName" name="bdGroupName"/>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.ownerName"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="ownerName" name="ownerName" placeholder="건물주 검색" />
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.detailName"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="bdName" name="name" value="${bd.name}" />
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.ownerPhone"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="ownerPhone" name="ownerPhone" readOnly/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.addr"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="addr" name="addr" value="${bd.addr}"/>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.latitude"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="latitude" name="latitude" style="width: 150px;" value="${bd.latitude}"/> /
				<input type="text" id="longitude" name="longitude" style="width: 150px;" value="${bd.longitude}"/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.buildingRegDate"/>
			</td>
			<td colspan="2" class="td02">
				<fmt:formatDate value="${date}" pattern="yyyy.MM.dd"/>
			</td>
			
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerGroupCnt"/>
			</td>
			<td colspan="2" class="td02"></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerCnt"/>
			</td>
			<td colspan="2" class="td02"></td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.lstInsDate"/>
			</td>
			<td colspan="2" class="td02">
			
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>전기요금 납기일
			</td>
			<td colspan="2" class="td02">
				<c:set var="paymentKeys">5,10,15,18,20,25,31</c:set>
				<c:set var="paymentValues">매월 5일,매월 10일,매월 15일,매월 18일,매월 20일,매월 25일,매월 말일</c:set>
				<c:set var="payment" value='<%=request.getParameter("payment")%>'/>
				<select name="payment" id="payment">
					<c:forTokens var="key" items="${paymentKeys}" delims="," varStatus="statKey">
						<c:forTokens var="val" items="${paymentValues}" delims="," varStatus="statVal">
							<c:if test="${statKey.index == statVal.index}">
								<option value="${key}" ${key == payment ? 'selected':''}>${val}</option>
							</c:if>
						</c:forTokens>
					</c:forTokens>
				</select>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.lstInstaller"/>
			</td>
			<td colspan="2" class="td02">
				
			</td>
		</tr>	
		<tr class="line-dot"><td colspan="6"/></tr>
	</table>
	
	<!-- input _ end -->

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
					<input type="button" id="cancel" value='<fmt:message key="label.common.cancel"/>'/>
				</c:if>	
				</td>
			</tr>
		</table>
	</div>				
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>