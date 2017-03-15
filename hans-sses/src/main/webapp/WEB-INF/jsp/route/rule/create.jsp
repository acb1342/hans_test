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
				'id':{
					required:'true',
					remote:{
						type:"POST",
						url:"checkRuleId.json",
						data:{
							id:function() { return document.vForm['id'].value; }
						}
					}
				}
			},
			messages:{
				'id':{
					required:'<fmt:message key="validate.required"/>',
					remote:'<fmt:message key="validate.duplicated.id"/>'
				}
			}
		});
	});

	// 검색 페이지
	function cancel() {
		document.location = "/route/rule/search.htm";
	}
</script>
<spring:hasBindErrors name="routeRule"/>
<form method="post" id="vForm" name="vForm" action="/route/rule/create.htm">
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
				<input type="text" id="id" name="id" value="${routeRule.id}" style="width:500px;" maxlength="32">
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
					<option value="PRI" ${routeRule.route1 == 'RRI' ? "selected='selected'":""}>Private</option>
					<option value="PUB" ${routeRule.route1 == 'PUB' ? "selected='selected'":""}>Public</option>
					<option value="HUB" ${routeRule.route1 == 'HUB' ? "selected='selected'":""}>SMS</option>
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
					<option value="PRI" ${routeRule.route2 == 'RRI' ? "selected='selected'":""}>Private</option>
					<option value="PUB" ${routeRule.route2 == 'PUB' ? "selected='selected'":""}>Public</option>
					<option value="HUB" ${routeRule.route2 == 'HUB' ? "selected='selected'":""}>SMS</option>
					<option value="X" ${routeRule.route2 == 'X' ? "selected='selected'":""}>None</option>
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
					<option value="PRI" ${routeRule.route3 == 'RRI' ? "selected='selected'":""}>Private</option>
					<option value="PUB" ${routeRule.route3 == 'PUB' ? "selected='selected'":""}>Public</option>
					<option value="HUB" ${routeRule.route3 == 'HUB' ? "selected='selected'":""}>SMS</option>
					<option value="X" ${routeRule.route3 == 'X' ? "selected='selected'":""}>None</option>
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