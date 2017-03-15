<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroupAuth" %>
<%@ page import="com.mobilepark.doit5.admin.model.Menu" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 저장
		$('#save').click(function(e) {
			e.preventDefault();
			$("#vForm").submit();
		});
		
		// 유효성 체크
		$("#vForm").validate({
			onfocusout:false,
			onkeyup:false,
			rules:{
				name:"required"
			},
			messages:{
				name:{
					required:'<fmt:message key="validate.required"/>'
				}
			}
		});
	});

	// 검색 페이지로 이동
	function cancel() {
		window.location = "/admin/group/search.htm";
	}
	
	// 모든 메뉴에 대한 일괄적으로 선택한 권한으로 설정
	function allAuthModify() {
		var form = document.vForm;
		var allAuth = form.allAuth;
		var selElems = form.elements;

		for (var i = 0; i < selElems.length; i++) {
			var selElem = selElems[i];
			if (selElem.options) {
				selElem.value = allAuth.value;
			}
		}
	}
</script>
<spring:hasBindErrors name="cmsGroup"/>
<form method="post" id="vForm" name="vForm" action="/admin/group/create.htm">
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- 그룹명 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsGroup.name"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="name" value="${cmsGroup.name}">
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
				<textarea name="description">${cmsGroup.description}</textarea>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 권한리스트 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.auth"/>
			</td>
			<td colspan="2" class="td02">
			<div style="padding:10px 20px 10px 80px;margin-bottom:10px;border:1px solid #b8d4e4;background:#d9eefa;">
				<select name="allAuth" style="width:200px;">
					<option value="N"><fmt:message key="label.cmsGroup.authority.none"/></option>
					<option value="R"><fmt:message key="label.cmsGroup.authority.r"/></option>
					<option value="RA"><fmt:message key="label.cmsGroup.authority.ra"/></option>
					<option value="RC"><fmt:message key="label.cmsGroup.authority.rc"/></option>
					<option value="RUA"><fmt:message key="label.cmsGroup.authority.rua"/></option>
					<option value="RCU"><fmt:message key="label.cmsGroup.authority.rcu"/></option>
					<option value="RCUD"><fmt:message key="label.cmsGroup.authority.rcud"/></option>
					<option value="RCUDA"><fmt:message key="label.cmsGroup.authority.rcuda"/></option>
				</select>&nbsp;&nbsp;
				<input type="button" value='<fmt:message key="label.cmsGroup.authority.updateAll"/>' style="width:80px;" onclick="allAuthModify()">
			</div>
			<table style="width:100%;margin-bottom:10px;">
		<%
			@SuppressWarnings("unchecked")
			Map<Menu, AdminGroupAuth> groupAuthMap = (Map<Menu, AdminGroupAuth>)request.getAttribute("groupAuthMap");
			Set<Menu> Menus = groupAuthMap.keySet();
			for (Menu menu: Menus) {
				Integer menuId = menu.getId();
				String menuName = menu.getTitle();
				AdminGroupAuth actorAuth = groupAuthMap.get(menu);
				String authority = "N";
				if (actorAuth != null && actorAuth.getAuth() != null) authority = actorAuth.getAuth();
		%>
				<tr>
					<td style="width:240px;">
						<div style="padding-left:<%=(menu.getDepth()-1) * 20%>px;">
							<img src="/images/default/icon_list.gif" width="7" height="7">
							<%=menuName%>
						</div>
					</td>
					<td  class="td02" colspan="2">
						<select name="<%=menuId%>" style="width:200px;">
							<option value="N"><fmt:message key="label.cmsGroup.authority.none"/></option>
							<option value="R" <%=authority.equals("R") ? "selected":"" %>><fmt:message key="label.cmsGroup.authority.r"/></option>
							<option value="RA" <%=authority.equals("RA") ? "selected":"" %>><fmt:message key="label.cmsGroup.authority.ra"/></option>
							<option value="RC" <%=authority.equals("RC") ? "selected":"" %>><fmt:message key="label.cmsGroup.authority.rc"/></option>
							<option value="RUA" <%=authority.equals("RUA") ? "selected":"" %>><fmt:message key="label.cmsGroup.authority.rua"/></option>
							<option value="RCU" <%=authority.equals("RCU") ? "selected":"" %>><fmt:message key="label.cmsGroup.authority.rcu"/></option>
							<option value="RCUD" <%=authority.equals("RCUD") ? "selected":"" %>><fmt:message key="label.cmsGroup.authority.rcud"/></option>
							<option value="RCUDA" <%=authority.equals("RCUDA") ? "selected":"" %>><fmt:message key="label.cmsGroup.authority.rcuda"/></option>
						</select>
					</td>
				</tr>
				<tr class="line-dot"><td colspan="3"/></tr>
		<%	}	%>
			</table>
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