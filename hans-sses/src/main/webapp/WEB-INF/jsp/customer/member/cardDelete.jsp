<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(document).ready(function(){
		
		$("#deleteBtn").click(function() {
			if ($('#addInfo').val() == '') {
				alert("삭제 사유를 입력하세요.");
				return;
			}
			
			if(confirm("Device ID를 삭제 하시겠습니까?") == false) return;
 			$.ajax({
				type:'POST',
				url:'/customer/member/cardDelete.json',
				data:{
					id: $("#id").val(),
					addInfo: $("#addInfo").val()
				},
				success:function (data) {
					opener.setDeviceId()
					window.close();
				}
			});
		});
	});
	
	function sendChildValue(pId, pName) {
		opener.setChildValue(pId, pName);
		window.close();
	}
</script>
<form id="vForm">
	<input type="hidden" id="id" name="id" value="${member.id}">
	<div class="wrap_winpop">
		<div class="header_area">
			<h1 class="tit">Device ID 삭제</h1>
		</div>
	
		<div class="content_area">
			<table style="width:100%">
				<!-- 사용자 이름 -->
				<tr class="line-top"><td colspan="2"/></tr>
				<tr>
					<td height="25" class="td01">
						<span class="bul_dot1">◆</span>
						<fmt:message key="label.cmsUser.name"/>
					</td>
					<td class="td02">&nbsp;${member.name}</td>
				</tr>
				<!-- 사용자 ID -->
				<tr>
					<td height="25" class="td01">
						<span class="bul_dot1">◆</span>
						<fmt:message key="label.cmsUser.id"/>
					</td>
					<td class="td02">&nbsp;${member.subsId}</td>
				</tr>
				<!-- Device ID -->
				<tr>
					<td height="25" class="td01">
						<span class="bul_dot1">◆</span>
						Device ID
					</td>
					<td class="td02">&nbsp;${member.deviceId}</td>
				</tr>
				<!-- HCE NFC 발급일 -->
				<tr>
					<td height="25" class="td01">
						<span class="bul_dot1">◆</span>
						HCE NFC 발급일
					</td>
					<td class="td02">&nbsp;${member.fstRgDt}</td>
				</tr>
				<!-- Device ID 삭제 사유 -->
				<tr>
					<td height="25" class="td01">
						<span class="bul_dot1">◆</span>
						Device ID 삭제 사유
					</td>
					<td class="td02">&nbsp;
						<textarea id="addInfo" name="addInfo" cols="1"></textarea>
					</td>
				</tr>
			</table>		
		</div>
		<div class="footer">
			<table style="width:100%">
				<tr>
					<%--
					<td width="50%" height="30">
						<input type="button" value='<fmt:message key="label.common.list"/>' onclick="javascript:search()"/>
					</td>
					<td width="50%" align="right">
					<c:if test="${authority.update}">
						<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="javascript:update()"/>
					</c:if>
					--%>					
					<td width="100%" align="center">
					<c:if test="${authority.delete}">
						<input type="button" class="btn_red" id="deleteBtn" value='<fmt:message key="label.common.delete"/>' onclick="javascript:confirmAndDelete()"/>
					</c:if>
					</td>
				</tr>
			</table>
		</div>
	</div>
</form>

<%@ include file="/include/footer.jspf" %>