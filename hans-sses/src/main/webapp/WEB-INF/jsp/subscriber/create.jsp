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
				appListId:"required",
				deviceId:"required",
				height:"number",
				width:"number"
			},
			messages:{
				appListId:{
					required:'<fmt:message key="validate.required"/>'
				},
				deviceId:{
					required:'<fmt:message key="validate.required"/>'
				},
				height:{
					number:'<fmt:message key="validate.number"/>'
				},
				width:{
					number:'<fmt:message key="validate.number"/>'
				}
			}
		});
	});

	// 검색 페이지
	function cancel() {
		document.location = "/subscriber/search.htm";
	}
</script>
<spring:hasBindErrors name="endUser"/>
<form method="post" id="vForm" name="vForm" action="/subscriber/create.htm">
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- APP -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.appId"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<select id="appListId" name="appListId">
				<option value=""><fmt:message key="label.common.option"/></option>
				<c:forEach items="${applicationLists}" var="appLists">
					<option value="${appLists.id}">${appLists.appId}</option>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Push Token -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.pushToken"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="pushToken" name="pushToken" value="${endUser.pushToken}" style="width:99%;">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- OS -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.os"/>
			</td>
			<td colspan="2" class="td02">
				<select name="os">
				<option value="android">android</option>
				<option value="ios">ios</option>
				</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- OS Version -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.osVer"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="osVer" name="osVer" value="${endUser.osVer}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- LIB Version -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.libVer"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="libVer" name="libVer" value="${endUser.libVer}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Device ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.deviceId"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="deviceId" name="deviceId" value="${endUser.deviceId}" style="width:99%;">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Device Brand -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.deviceBrand"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="deviceBrand" name="deviceBrand" value="${endUser.deviceBrand}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Device Model -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.deviceModel"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="deviceModel" name="deviceModel" value="${endUser.deviceModel}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Height -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.height"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="height" name="height" value="${endUser.height}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Width -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.width"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="width" name="width" value="${endUser.width}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Locale -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.locale"/>
			</td>
			<td class="td02">
				<d:countryCombobox name="locale"/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- Market -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.market"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="market" name="market" value="${endUser.market}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- MDN -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.endUser.mdn"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="mdn" name="mdn" value="${endUser.mdn}">
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