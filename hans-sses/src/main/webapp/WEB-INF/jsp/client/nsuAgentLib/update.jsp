<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function () {
		$("#applyStartYmd").datepicker({
			dateFormat:'yy/mm/dd'
		});

		$('#save').click(function(e) {
			e.preventDefault();
			$("#vForm").submit();
		});

	    $("#vForm").validate({
			rules:{
				version:{
					required:true
				},
				langCode:"required",
				applyStartYmd:"required"
			},
			messages:{
				version:{
					required:'<fmt:message key="validate.required"/>'
				},
				langCode:{
					required:'<fmt:message key="validate.required"/>'
				},
				applyStartYmd:{
					required:'<fmt:message key="validate.required"/>'
				}
			}
		});
	});

	function onSubmit() {
		document.vForm.submit();
	}

	function cancel() {
		window.location = "/client/nsuAgentLib/search.htm";
	}
</script>
<spring:hasBindErrors name="nsuAgentLib" />
<form method="post" ENCTYPE="MULTIPART/FORM-DATA" id="vForm" name="vForm" action="/client/nsuAgentLib/update.htm">
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.id"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="id" id="id" readonly="readonly" value="${nsuAgentLib.id}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.nsuAgentLib.version"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="version" id="version" readonly="readonly" value="${nsuAgentLib.version}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.nsuAgentLib.os"/>
			</td>
			<td colspan="2" class="td02">
				<select id="os" name="os">
					<c:if test="${nsuAgentLib.os == 'ios'}">
						<option value="ios" selected>ios</option>
						<option value="android">android</option>
					</c:if>
					<c:if test="${nsuAgentLib.os == 'android'}">
						<option value="ios">ios</option>
						<option value="android" selected>android</option>
					</c:if>
				</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.nsuAgentLib.langCode"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<upush:langCombobox name="langCode" id="langCode" value="${nsuAgentLib.langCode}"/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.nsuAgentLib.ynMandatory"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<select name="ynMandatory">
				<c:forEach items="${ynMandatorys}" var="ynMandatory">
					<option value="${ynMandatory}" ${nsuAgentLib.ynMandatory == ynMandatory ? "selected='selected'":""}>
						${ynMandatory}
					</option>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.nsuAgentLib.agentFile"/>
			</td>
			<td colspan="2" class="td02">
				<input type="file" name="agentFile"/><br>
				${nsuAgentLib.filePath}
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.nsuAgentLib.applyStartYmd"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="applyStartYmd" id="applyStartYmd" value="<fmt:formatDate value="${nsuAgentLib.applyStartYmd}" pattern="yyyy/MM/dd" />">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.status"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<select name="status">
				<c:forEach items="${releaseStatusList}" var="releaseStatus">
					<option value="${releaseStatus}" ${nsuAgentLib.status == releaseStatus ? "selected='selected'":""}>
						${releaseStatus}
					</option>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="line-bottom"><td colspan="3"/></tr>
	</table>
	<!-- input _ end -->

	<div class="line-clear"></div>

	<!-- button _ start -->
	<div class="footer">	
		<table style="width:100%">
			<tr>
				<td width="50%" height="30"></td>
				<td width="50%" align="right">
					<input type="button" id="save" value='<fmt:message key="label.common.save"/>'/>
					<input type="button" value='<fmt:message key="label.common.cancel"/>' onclick="javascript:cancel()"/>
				</td>
	                    	<!-- td class="btn02">
	                    		<a class="link01" href="#" id="save"><fmt:message key="label.common.save"/></a>
	                    	</td-->

				<!-- td class="btn02">
					<a class="link01" href="javascript:cancel();"><fmt:message key="label.common.cancel"/></a>
				</td -->
			</tr>
		</table>
	</div>
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>