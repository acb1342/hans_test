<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 저장
		$('#save').click(function(e) {
			e.preventDefault();
			$("#vForm").submit();
		});

		// 유효성 검사
	    $("#vForm").validate({
			rules:{
				senderIndex:"number",
				senderTid:"number",
				port:"number",
				tps:"number"
			},
			messages:{
				senderIndex:{
					number:'<fmt:message key="validate.number"/>'
				},
				senderTid:{
					number:'<fmt:message key="validate.number"/>'
				},
				port:{
					number:'<fmt:message key="validate.number"/>'
				},
				tps:{
					number:'<fmt:message key="validate.number"/>'
				}
			}
		});
	});

	// 이전 페이지
	function cancel() {
		history.back();
	}
</script>
<spring:hasBindErrors name="senderInfo"/>
<form method="post" id="vForm" name="vForm" action="/sender/info/update.htm">
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<!-- ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.senderInfo.hostName"/><font color="#FF0000">*</font>
			</td>
			<td colspan="6" class="td02">
				<input type="text" id="id" name="id" readOnly="readonly" value="${senderInfo.id}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- Sender Name / Index-->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.senderInfo.senderName"/>
			</td>
			<td colspan="3" class="td02">
				<input type="text" id="senderName" name="senderName" value="${senderInfo.senderName}">
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.senderInfo.senderIndex"/>
			</td>
			<td width="290px" class="td02">
				<input type="text" id="senderIndex" name="senderIndex" value="${senderInfo.senderIndex}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- Sender Tid / ID-->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.senderInfo.senderTid"/>
			</td>
			<td colspan="3" class="td02">
				<input type="text" id="senderTid" name="senderTid" value="${senderInfo.senderTid}">
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.senderInfo.senderId"/>
			</td>
			<td width="290px" class="td02">
				<input type="text" id="senderId" name="senderId" value="${senderInfo.senderId}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- APP ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.app"/>
			</td>
			<td colspan="6" class="td02">
				<select id="appId" name="appId">
				<option value=""><fmt:message key="label.common.option"/></option>
				<c:forEach items="${applications}" var="apps">
					<option value="${apps.id}" ${apps.appName == appName ? "selected='selected'":""}>
						${apps.appName}
					</option>
				</c:forEach>
				</select>			
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- IP / Port -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.senderInfo.ip"/>
			</td>
			<td colspan="3" class="td02">
				<input type="text" id="ip" name="ip" value="${senderInfo.ip}" maxlength="32">
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.senderInfo.port"/>
			</td>
			<td width="290px" class="td02">
				<input type="text" id="port" name="port" value="${senderInfo.port}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- URL -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.senderInfo.url"/>
			</td>
			<td colspan="6" class="td02">
				<input type="text" id="url" name="url" value="${senderInfo.url}" style="width:500px;" maxlength="64">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- TPS -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.senderInfo.tps"/>
			</td>
			<td colspan="6" class="td02">
				<input type="text" id="tps" name="tps" value="${senderInfo.tps}" style="width:500px;">
			</td>
		</tr>
		<tr class="line-bottom"><td colspan="6"/></tr>
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