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
			onfocusout:false,
			onkeyup:false,
			rules:{
				senderIndex:"number"
			},
			messages:{
				senderIndex:{
					number:'<fmt:message key="validate.number"/>'
				}
			}
		});
		
		applyVisibility();
	});

	// 이전 페이지
	function cancel() {
		history.back();
	}

	function applyVisibility() {
		if( $("#os").val() == 'android') {
			$( ".hidableTr").css( "display", "none" );
			$( ".ioshidableTr").css( "display", "table-row" );
		} else {
			$( ".ioshidableTr").css( "display", "none" );
			$( ".hidableTr").css( "display", "table-row" );
		}
	}
	
</script>
<spring:hasBindErrors name="applicationList"/>
<form method="post" ENCTYPE="MULTIPART/FORM-DATA" id="vForm" name="vForm" action="/client/appList/update.htm">
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.id"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="id" readOnly="readonly" value="${applicationList.id}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- APP -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.app"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
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
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- OS -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.os"/>
			</td>
			<td colspan="2" class="td02">
				<select id="os" name="os" onchange="applyVisibility()">
					<c:if test="${applicationList.os == 'ios'}">
						<option value="ios" selected>ios</option>
						<option value="android">android</option>
					</c:if>
					<c:if test="${applicationList.os == 'android'}">
						<option value="ios">ios</option>
						<option value="android" selected>android</option>
					</c:if>
				</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- API Key -->
		<tr class="ioshidableTr">
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.appKey"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="appKey" name="appKey" value="${applicationList.appKey}" style="width:99%;" maxlength="128">
			</td>
		</tr>
		<tr class="line-dot ioshidableTr"><td colspan="3"/></tr>
		<!-- App Version -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.appVer"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="appVer" name="appVer" value="${applicationList.appVer}" maxlength="16">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<%--
		<!-- Cert Path -->
		<tr class="hidableTr">
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.applicationList.certPath"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="certPath" name="certPath" value="${applicationList.certPath}" style="width:99%;" maxlength="128">
			</td>
		</tr>
		<tr class="line-dot hidableTr"><td colspan="3"/></tr>
		--%>
		<!-- Cert File -->
		<tr class="hidableTr">
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.certFile"/>
			</td>
			<td colspan="2" class="td02">
				<input type="file" id="certFile" name="certFile" style="width:99%;"> ${applicationList.certPath}
			</td>
		</tr>
		<tr class="line-dot hidableTr"><td colspan="3"/></tr>
		<%--
		<!-- Key Path -->
		<tr class="hidableTr">
			<td height="25" class="td01">
				<img src="/images/default/icon_list.gif" width="7" height="7">
				<fmt:message key="label.applicationList.keyPath"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="keyPath" name="keyPath" value="${applicationList.keyPath}" style="width:99%;" maxlength="128">
			</td>
		</tr>
		<tr class="line-dot hidableTr"><td colspan="3"/></tr>
		--%>
		<!-- Key File -->
		<tr class="hidableTr">
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.keyFile"/>
			</td>
			<td colspan="2" class="td02">
				<input type="file" id="keyFile" name="keyFile" style="width:99%;"> ${applicationList.keyPath}
			</td>
		</tr>
		<tr class="line-dot hidableTr"><td colspan="3"/></tr>
		
		<!-- Route Sequence -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.routeSeq"/>
			</td>
			<td colspan="2" class="td02">
				<select name="routeSeq">
				<c:forEach items="${routeRuleList}" var="routeRule">
					<option value="${routeRule.id}" ${routeRule.id == applicationList.routeSeq ? "selected='selected'":""}> ${routeRule.description} </option>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Sender Index -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.senderIndex"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="senderIndex" name="senderIndex" value="${applicationList.senderIndex}" disabled>
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