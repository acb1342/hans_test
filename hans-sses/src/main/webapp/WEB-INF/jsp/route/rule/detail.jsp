<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobilepark.doit5.route.model.RouteRule" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	// 수정
	function update() {
		document.location = "/route/rule/update.htm?id=${routeRule.id}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/route/rule/delete.json', '${routeRule.id}', function() { 
			search(); 
		});
	}

	// 검색 페이지
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			document.location = "/route/rule/search.htm?" + callbackUrl;
		} else {
			document.location = "/route/rule/search.htm";
		}
	}
</script>
<div class="wrap00">
	<!-- list _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.routeRule.routeRuleId"/>
			</td>
			<td colspan="2" class="td02">${routeRule.id}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 1차 루트 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.routeRule.route1"/>
			</td>
			<td colspan="2" class="td02"><c:choose>
		        <c:when test="${routeRule.route1 == 'PUB'}">Public</c:when>
    		    <c:when test="${routeRule.route1 == 'PRI'}">Private</c:when>
        		<c:when test="${routeRule.route1 == 'HUB'}">SMS</c:when>
		        <c:otherwise>None</c:otherwise>
     		</c:choose></td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 2차 루트 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.routeRule.route2"/>
			</td>
			<td colspan="2" class="td02"><c:choose>
		        <c:when test="${routeRule.route2 == 'PUB'}">Public</c:when>
    		    <c:when test="${routeRule.route2 == 'PRI'}">Private</c:when>
        		<c:when test="${routeRule.route2 == 'HUB'}">SMS</c:when>
		        <c:otherwise>None</c:otherwise>
     		</c:choose></td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 3차 루트 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.routeRule.route3"/>
			</td>
			<td colspan="2" class="td02"><c:choose>
		        <c:when test="${routeRule.route3 == 'PUB'}">Public</c:when>
    		    <c:when test="${routeRule.route3 == 'PRI'}">Private</c:when>
        		<c:when test="${routeRule.route3 == 'HUB'}">SMS</c:when>
		        <c:otherwise>None</c:otherwise>
     		</c:choose></td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 변경일 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.routeRule.changeDate"/>
			</td>
			<td colspan="2" class="td02">
			<c:set value="${routeRule}" var="routeRule" scope="request"></c:set>
			<%
				RouteRule rule = (RouteRule)request.getAttribute("routeRule");
				SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
				SimpleDateFormat sf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
				if (rule != null && StringUtils.isNotEmpty(rule.getChangeDate())) {
					String changeDate = sf2.format(sf.parse(rule.getChangeDate()));
					out.println(changeDate);
				}
			%>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 설명 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.description"/>
			</td>
			<td colspan="2" class="td02">${routeRule.description}</td>
		</tr>
		<tr class="line-bottom"><td colspan="3"/></tr>		
	</table>
	<!-- list _ start -->
	
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