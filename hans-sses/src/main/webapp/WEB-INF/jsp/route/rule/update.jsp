<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 저장
		$('#save').click(function(e) {
			e.preventDefault();
			$("#vForm").submit();
		});
	});

	// 이전 페이지
	function cancel() {
		history.back();
	}
</script>
<spring:hasBindErrors name="routeRule"/>
<form method="post" id="vForm" name="vForm" action="/route/rule/update.htm">
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.routeRule.routeRuleId"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="id" name="id" readOnly="readonly" value="${routeRule.id}" style="width:500px;">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 1차 루트 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.routeRule.route1"/>
			</td>
			<td colspan="2" class="td02">
				<select name="route1">
					<c:if test="${routeRule.route1 == 'PRI'}">
						<option value="PRI" selected>Private</option>
						<option value="PUB">Public</option>
						<option value="HUB">SMS</option>
					</c:if>
					<c:if test="${routeRule.route1 == 'PUB'}">
						<option value="PRI">Private</option>
						<option value="PUB" selected>Public</option>
						<option value="HUB">SMS</option>
					</c:if>
					<c:if test="${routeRule.route1 == 'HUB'}">
						<option value="PRI">Private</option>
						<option value="PUB">Public</option>
						<option value="HUB" selected>SMS</option>
					</c:if>
				</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 2차 루트 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.routeRule.route2"/>
			</td>
			<td colspan="2" class="td02">
				<select name="route2">
					<c:if test="${routeRule.route2 == 'PRI'}">
						<option value="PRI" selected>Private</option>
						<option value="PUB">Public</option>
						<option value="HUB">SMS</option>
						<option value="X">None</option>
					</c:if>
					<c:if test="${routeRule.route2 == 'PUB'}">
						<option value="PRI">Private</option>
						<option value="PUB" selected>Public</option>
						<option value="HUB">SMS</option>
						<option value="X">None</option>
					</c:if>
					<c:if test="${routeRule.route2 == 'HUB'}">
						<option value="PRI">Private</option>
						<option value="PUB">Public</option>
						<option value="HUB" selected>SMS</option>
						<option value="X">None</option>
					</c:if>
					<c:if test="${routeRule.route2 == 'X'}">
						<option value="PRI">Private</option>
						<option value="PUB">Public</option>
						<option value="HUB">SMS</option>
						<option value="X" selected>None</option>
					</c:if>
				</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 3차 루트 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.routeRule.route3"/>
			</td>
			<td colspan="2" class="td02">
				<select name="route3">
					<c:if test="${routeRule.route3 == 'PRI'}">
						<option value="PRI" selected>Private</option>
						<option value="PUB">Public</option>
						<option value="HUB">SMS</option>
						<option value="X">None</option>
					</c:if>
					<c:if test="${routeRule.route3 == 'PUB'}">
						<option value="PRI">Private</option>
						<option value="PUB" selected>Public</option>
						<option value="HUB">SMS</option>
						<option value="X">None</option>
					</c:if>
					<c:if test="${routeRule.route3 == 'HUB'}">
						<option value="PRI">Private</option>
						<option value="PUB">Public</option>
						<option value="HUB" selected>SMS</option>
						<option value="X">None</option>
					</c:if>
					<c:if test="${routeRule.route3 == 'X'}">
						<option value="PRI">Private</option>
						<option value="PUB">Public</option>
						<option value="HUB">SMS</option>
						<option value="X" selected>None</option>
					</c:if>
				</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 설명 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.description"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="description" name="description" value="${routeRule.description}" style="width:500px;" maxlength="32">
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