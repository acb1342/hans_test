<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroupAuth" %>
<%@ page import="com.mobilepark.doit5.admin.model.Menu" %>
<%@ page import="java.util.Map" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		
		// 건물주 정보
		$('#ownerName').click(function(e) {
			window.open("/elcg/building/ownerInfoPopup.htm","new","width=448,height=448,top=100,left=100");
		});
		
		// 저장
		$('#save').click(function(e) {	
			if( !(validationCheck()) ) return;
			if(!confirm("해당 건물정보를 수정하시겠습니까?")) return;
			
			$("#vForm").submit();
		});
		
		
		
	});

	function setChildValueOwner(id, name, mobile) {
		document.getElementById("ownerId").value = id;
		document.getElementById("ownerName").value = name;
		document.getElementById("ownerPhone").value = mobile;
	}
	
	function cancel() {
		if(confirm("정말 취소하시겠습니까?")) history.back();
	}

	function validationCheck() {
	<c:if test="${logingGroup.id == 1}">
		if( document.vForm.bdGroupName.value == "" ) {
			alert("건물/아파트명을 입력해주세요.");
			return;
		}
		if( document.vForm.name.value == "" ) {
			alert("상세/동명을 입력해주세요.");
			return;
		}
		if( document.vForm.ownerName.value == "" ) {
			alert("건물주명을 검색후 선택하세요!");
			return;
		}
		if( document.vForm.addr.value == "" ) {
			alert("주소를 입력해주세요.");
			return;
		}
		if( document.vForm.latitude.value == "" || document.vForm.longitude.value == "" ) {
			alert("위도/경도를 입력해주세요.");
			return;
		}
	</c:if>
		var selPayment = document.getElementById("payment");
		if( selPayment.options[selPayment.selectedIndex].value == 0 ) {
			alert("납기일을 선택해주세요!");
			return;
		}
		return true;
	}
	
</script>

<spring:hasBindErrors name="bd"/>
<form method="post" id="vForm" name="vForm" action="/elcg/building/update.htm?seq=${bd.bdId}">
<c:set var="ownerId" value="${owner.id}" />
<input type="hidden" id="ownerId" name="ownerId" value="${ownerId}"/>
<div class="wrap00">
	<!-- input _ start -->
	<!-- 운영자 -->
	<c:choose>
	<c:when test="${loginGroup.id == 1 }">
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.buildingName"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="bdGroupName" value="${bd.bdGroup.name}"/>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.ownerName"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="ownerName" name="ownerName" placeholder="건물주 검색" value="${owner.name}" readonly/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.detailName"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="name" value="${bd.name}" />
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.ownerPhone"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="ownerPhone" name="ownerPhone" value="${owner.mobile}" readonly/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.addr"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="addr" value="${bd.bdGroup.addr}"/>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.latitude"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="latitude" style="width: 150px;" value="${bd.latitude}"/> /
				<input type="text" name="longitude" style="width: 150px;" value="${bd.longitude}"/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.regDate"/>
			</td>
			<td colspan="2" class="td02">
				<fmt:formatDate value="${bd.fstRgDt}" pattern="yyyy.MM.dd"/>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerGroupCnt"/>
			</td>
			<td colspan="2" class="td02">${bd.chargerGroupSize}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerCnt"/>
			</td>
			<td colspan="2" class="td02">${bd.chargerSize}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.lstInsDate"/>
			</td>
			<td colspan="2" class="td02">
				<fmt:parseDate var="strDate" value="${bd.lstInsDate}" pattern="yyyyMMddHHmm" />
				<fmt:formatDate value="${strDate}" pattern="yyyy.MM.dd HH:mm"/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>전기요금 납기일
			</td>
			<td colspan="2" class="td02">
			<c:if test="${'9999' eq periodDay }">
				<c:set var="paymentKeys">0,5,10,15,18,20,25,31</c:set>
				<c:set var="paymentValues">납기일 미등록 상태,매월 5일,매월 10일,매월 15일,매월 18일,매월 20일,매월 25일,매월 말일</c:set>
				<c:set var="payment" value='<%=request.getParameter("payment")%>'/>
				<select name="payment" id="payment">
					<c:forTokens var="key" items="${paymentKeys}" delims="," varStatus="statKey">
						<c:forTokens var="val" items="${paymentValues}" delims="," varStatus="statVal">
							<c:if test="${statKey.index == statVal.index}">
								<option <c:if test="${key eq periodDay }">selected</c:if> value="${key}" ${key == payment ? 'selected':''}>${val}</option>
							</c:if>
						</c:forTokens>
					</c:forTokens>
				</select>
			</c:if>
			<c:if test="${'9999' ne periodDay }">
				<c:set var="paymentKeys">5,10,15,18,20,25,31</c:set>
				<c:set var="paymentValues">매월 5일,매월 10일,매월 15일,매월 18일,매월 20일,매월 25일,매월 말일</c:set>
				<c:set var="payment" value='<%=request.getParameter("payment")%>'/>
				<select name="payment" id="payment">
					<c:forTokens var="key" items="${paymentKeys}" delims="," varStatus="statKey">
						<c:forTokens var="val" items="${paymentValues}" delims="," varStatus="statVal">
							<c:if test="${statKey.index == statVal.index}">
								<option <c:if test="${key eq periodDay }">selected</c:if> value="${key}" ${key == payment ? 'selected':''}>${val}</option>
							</c:if>
						</c:forTokens>
					</c:forTokens>
				</select>
			</c:if>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.lstInstaller"/>
			</td>
			<td colspan="2" class="td02">${bd.lstInstaller}</td>
		</tr>	
		<tr class="line-dot"><td colspan="6"/></tr>
		</table>
		</c:when>
		
		<%--  설치자  --%>
		<c:when test="${loginGroup.id == 2 }">
		<input type="hidden" name="name" value="${bd.name}"/>
		<input type="hidden" name="addr" value="${bd.addr}"/>
		<input type="hidden" name="latitude" value="${bd.latitude}"/>
		<input type="hidden" name="longitude" value="${bd.longitude}"/>
		 
		<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.buildingName"/>
			</td>
			<td colspan="2" class="td02"> ${bd.bdGroup.name}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.ownerName"/>
			</td>
			<td colspan="2" class="td02"> ${owner.name}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.detailName"/>
			</td>
			<td colspan="2" class="td02"> ${bd.name}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.ownerPhone"/>
			</td>
			<td colspan="2" class="td02">${owner.mobile}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.addr"/>
			</td>
			<td colspan="2" class="td02">
			<c:if test="${bd.addr == null}">${bd.bdGroup.addr}</c:if>
			<c:if test="${bd.addr != null}">${bd.addr}</c:if>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.latitude"/>
			</td>
			<td colspan="2" class="td02">${bd.latitude}  /  ${bd.longitude}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.buildingRegDate"/>
			</td>
			<td colspan="2" class="td02">
				<fmt:formatDate value="${bd.fstRgDt}" pattern="yyyy.MM.dd"/>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerGroupCnt"/>
			</td>
			<td colspan="2" class="td02">${bd.chargerGroupSize}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerCnt"/>
			</td>
			<td colspan="2" class="td02">${bd.chargerSize}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.lstInsDate"/>
			</td>
			<td colspan="2" class="td02">
				<fmt:parseDate var="strDate" value="${bd.lstInsDate}" pattern="yyyyMMddHHmm" />
				<fmt:formatDate value="${strDate}" pattern="yyyy.MM.dd HH:mm"/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.paymentDate"/>
			</td>
			<td colspan="2" class="td02">
			<c:if test="${'9999' eq periodDay }">
				<c:set var="paymentKeys">0,5,10,15,18,20,25,31</c:set>
				<c:set var="paymentValues">납기일 미등록 상태,매월 5일,매월 10일,매월 15일,매월 18일,매월 20일,매월 25일,매월 말일</c:set>
				<c:set var="payment" value='<%=request.getParameter("payment")%>'/>
				<select name="payment" id="payment">
					<c:forTokens var="key" items="${paymentKeys}" delims="," varStatus="statKey">
						<c:forTokens var="val" items="${paymentValues}" delims="," varStatus="statVal">
							<c:if test="${statKey.index == statVal.index}">
								<option <c:if test="${key eq periodDay }">selected</c:if> value="${key}" ${key == payment ? 'selected':''}>${val}</option>
							</c:if>
						</c:forTokens>
					</c:forTokens>
				</select>
			</c:if>
			<c:if test="${'9999' ne periodDay }">
				<c:set var="paymentKeys">5,10,15,18,20,25,31</c:set>
				<c:set var="paymentValues">매월 5일,매월 10일,매월 15일,매월 18일,매월 20일,매월 25일,매월 말일</c:set>
				<c:set var="payment" value='<%=request.getParameter("payment")%>'/>
				<select name="payment" id="payment">
					<c:forTokens var="key" items="${paymentKeys}" delims="," varStatus="statKey">
						<c:forTokens var="val" items="${paymentValues}" delims="," varStatus="statVal">
							<c:if test="${statKey.index == statVal.index}">
								<option <c:if test="${key eq periodDay }">selected</c:if> value="${key}" ${key == payment ? 'selected':''}>${val}</option>
							</c:if>
						</c:forTokens>
					</c:forTokens>
				</select>
			</c:if>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.lstInstaller"/>
			</td>
			<td colspan="2" class="td02">${bd.lstInstaller}</td>
		</tr>	
		<tr class="line-dot"><td colspan="6"/></tr>
	</table>
		</c:when>
	</c:choose>
		
		
		<!--  second table  -->
		<c:if test="${bd.chargerGroupSize > 0}"><br/>
		<table style="width:100%">
			<tr class="line-top"><td colspan="6"/></tr>
			<tr>
				<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerGroupName"/>
				</td>			
				<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.mgmtNo"/>
				</td>
			</tr>
			<tr class="line-dot"><td colspan="6"/></tr>
			
			<c:forEach items="${bd.chargerGroupList}" var="chargerGroup" varStatus="status">
					<tr>
						<td class="td02">${chargerGroup.name}</td>
						<td class="td02">
							<c:forEach items="${chargerGroup.chargerList}" var="charger" varStatus="cgStatus">
								${charger.chargerList.mgmtNo}
								<br/>
							</c:forEach>
						</td>
					</tr>
					<tr class="line-dot"><td colspan="6"/></tr>
			</c:forEach>
			<tr class="line-dot"><td colspan="6"/></tr>
		</table>
	</c:if>
		
	<!--  input end -->	
			
	<div class="line-clear"></div>
		
	<!-- button _ start -->
	<div class="footer"><br/>
		<table style="width:100%">
		<tr>
			<td width="50%" height="30"></td>
			<td width="50%" align="right">		
				<c:if test="${authority.update}">
					<input type="button" id="save" value='<fmt:message key="label.common.save"/>'/>
				</c:if>
					<input type="button" value='<fmt:message key="label.common.cancel"/>' onclick="javascript:cancel()"/>
			</td>
		</tr>
		</table>
	</div>				
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>