<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroupAuth" %>
<%@ page import="com.mobilepark.doit5.admin.model.Menu" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	// 수정 페이지로 이동
	function update() {
		location.href = "/admin/group/update.htm?id=${cmsGroup.id}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/admin/group/delete.json', '${cmsGroup.id}', function() { 
			search();
		});
	}

	// 검색 페이지로 이동
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			location.href = "/admin/group/search.htm?" + callbackUrl;
		} else {
			location.href = "/admin/group/search.htm";
		}
	}
</script>
<div class="wrap00">
	<!-- list _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- 그룹 ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsGroup.id"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${cmsGroup.id}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 그룹명 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsGroup.name"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${cmsGroup.name}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 설명 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.description"/>
			</td>
			<td colspan="2" class="td02">
				<textarea name="description" readonly="readonly">${cmsGroup.description}</textarea>
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
			<table style="width:100%;padding:10px;">
		<%
			@SuppressWarnings("unchecked")
			Map<Menu, AdminGroupAuth> groupAuthMap = (Map<Menu, AdminGroupAuth>)request.getAttribute("groupAuthMap");
			Set<Menu> menus = groupAuthMap.keySet();
			int setSize = menus.size();
			int idx = 0;
			for (Menu menu: menus) {
				String menuName = menu.getTitle();
				AdminGroupAuth actorAuth = groupAuthMap.get(menu);
				request.setAttribute("menuAuthority", actorAuth == null ? "N":actorAuth.getAuth());
				idx++;
		%>
				<tr>
					<td style="width:240px;">
						<div style="padding-left:<%=(menu.getDepth()-1) * 20%>px;">
							<img src="/images/default/icon_list.gif" width="7" height="7">
							<%=menuName%>
						</div>
					</td>
					<td colspan="2" class="td02" height="25">
						<select style="width:200px;">
							<option>
							<c:if test="${menuAuthority == 'N'}"><fmt:message key="label.cmsGroup.authority.none"/></c:if>
							<c:if test="${menuAuthority == 'R'}"><fmt:message key="label.cmsGroup.authority.r"/></c:if>
							<c:if test="${menuAuthority == 'RA'}"><fmt:message key="label.cmsGroup.authority.ra"/></c:if>
							<c:if test="${menuAuthority == 'RC'}"><fmt:message key="label.cmsGroup.authority.rc"/></c:if>
							<c:if test="${menuAuthority == 'RUA'}"><fmt:message key="label.cmsGroup.authority.rua"/></c:if>
							<c:if test="${menuAuthority == 'RCU'}"><fmt:message key="label.cmsGroup.authority.rcu"/></c:if>
							<c:if test="${menuAuthority == 'RCUD'}"><fmt:message key="label.cmsGroup.authority.rcud"/></c:if>
							<c:if test="${menuAuthority == 'RCUDA'}"><fmt:message key="label.cmsGroup.authority.rcuda"/></c:if>
							</option>
						</select>
					</td>
				</tr>
		<% 		if (idx < setSize) { %>
				<tr class="line-dot"><td colspan="3"/></tr>
		<%		}
			}
		%>
			</table>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 생성일 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.createDate"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${cmsGroup.fstRgDt}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 수정일 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.modifyDate"/>
			</td>
			<td colspan="2" class="td02">&nbsp;${cmsGroup.lstChDt}</td>
		</tr>
		<tr class="line-bottom"><td colspan="3"/></tr>
	</table>
	<!-- list _ end -->

	<div class="line-clear"></div>
	
	<!-- button _ start -->
	<div class="footer">	
		<table style="width:100%">
			<tr>
				<td width="50%" height="30">
					<input type="button" value='<fmt:message key="label.common.list"/>' onclick="javascript:search()"/>
				</td>
				<td width="50%" align="right">
				<c:if test="${authority.update}">
					<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="javascript:update()"/>
				</c:if>
				<c:if test="${authority.delete}">
					<input type="button" value='<fmt:message key="label.common.delete"/>' onclick="javascript:confirmAndDelete()"/>
				</c:if>
				</td>
			</tr>
		</table>
	</div>
	<!-- button _ end -->
</div>
<%@ include file="/include/footer.jspf" %>