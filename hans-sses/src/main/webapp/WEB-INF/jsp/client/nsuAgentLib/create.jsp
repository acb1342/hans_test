<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	var osType;

	$(function () {
		$("#applyStartYmd").datepicker({
			dateFormat:'yy/mm/dd'
		});

		$('#save').click(function(e) {
			e.preventDefault();
			var checkVersion = $("#checkVersion").val();

			if (checkVersion == null || checkVersion == '' || checkVersion == 'N'){
				jAlert('<fmt:message key="statement.duplication.check"/>');
				return;
			}

			$("#vForm").submit();
		});

	    $("#vForm").validate({
			rules:{
				version:"required",
				os:"required",
				agentFile:"required",
				langCode:"required",
				applyStartYmd:"required"
			},
			messages:{
				version:{
					required:'<fmt:message key="validate.required"/>'
				},
				os:{
					required:'<fmt:message key="validate.required"/>'
				},
				langCode:{
					required:'<fmt:message key="validate.required"/>'
				},
				agentFile:{
					required:'<fmt:message key="validate.required"/>'
				},
				applyStartYmd:{
					required:'<fmt:message key="validate.required"/>'
				}
			}
		});

	    changeOsType();
	});

	function onSubmit() {
		document.vForm.submit();
	}

	function cancel() {
		window.location = "/client/nsuAgentLib/search.htm";
	}

	function checkVersionFunc() {
		var data;
		var version = $("#version").val();
		var os = $("#os").val();
		var langCode = $("#langCode").val();

		data = {version:version, os:os, langCode:langCode};
		if(version){
			$.ajax({
				  	url: '/client/nsuAgentLib/checkVersion.json',
				  	dataType : "json",
				  	data: data,
				  	success:function(isSuccess){
			        	if(isSuccess){
			        		$("#msgVersion").html('<fmt:message key="validate.duplication.ok"/>');
			        		$("#checkVersion").val("Y");
			        	} else {
				        	$("#msgVersion").html('<fmt:message key="validate.duplication.nok"/>');
			        		$("#checkVersion").val("N");
			        	}
			      	},
			      	error:function(e){}
			}).done(function() {});
		}else{
			$("#msgVersion").html('<fmt:message key="validate.required.version"/>');
			$("#checkVersion").val("N");
		}
	}
	
	function changeOsType() {
		$("#checkVersion").val("N");
		$("#msgVersion").html('');
		if($("#os").val() == 'android') {
			osType = $("#os").val();
		} else {
			osType = $("#os").val()
		}
	}
</script>
<spring:hasBindErrors name="nsuAgentLib" />
<form method="post" ENCTYPE="MULTIPART/FORM-DATA" id="vForm" name="vForm" action="/client/nsuAgentLib/create.htm">
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>

		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.nsuAgentLib.version"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="version" name="version" value="${nsuAgentLib.version}">
				<input type="hidden" id="checkVersion" name="checkVersion" value="Y">
				<button type="button" class="btn_overlap" onClick="javascript:checkVersionFunc();"><fmt:message key="label.nsuAgentLib.checkDuplication"/></button>
				<strong class="point_color" id="msgVersion"></strong> <!-- 중복체크 결과 메세지 -->
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- OS -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.nsuAgentLib.os"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<select id="os" name="os" onchange="changeOsType()">
				<option value="android">android</option>
				<option value="ios">ios</option>
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
				<fmt:message key="label.nsuAgentLib.agentFile"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="file" name="agentFile"/>
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
	                    	<!-- td class="btn02">
	                    		<a class="link01" href="#" id="save"><fmt:message key="label.common.save"/></a>
	                    	</td -->
				<!-- td class="btn02">
					<a class="link01" href="javascript:cancel();"><fmt:message key="label.common.cancel"/></a>
				</td -->
				</td>
			</tr>
		</table>
	</div>
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>