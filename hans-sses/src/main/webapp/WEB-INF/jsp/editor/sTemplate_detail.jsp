<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="u" uri="/WEB-INF/tlds/util.tld" %>
<%@ page import="com.uangel.platform.util.Env" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="title" content="uPUSH" />
<meta name="description" content="uPUSH" />
<meta name="keywords" content="" />
<meta name="language" content="ko" />

<title>Admin - SKT EVC</title>

<!-- Le styles -->
<link href="${contextPath}/editor/css/bootstrap-combined.min.css" rel="stylesheet">
<link href="${contextPath}/editor/css/layoutit.css" rel="stylesheet">
<link href="${contextPath}/js/jquery/alert/jquery.alerts.css" rel="stylesheet">

<!-- fav and touch icons -->
<script type="text/javascript" src="${contextPath}/js/jquery/jquery-1.7.2.custom.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery/alert/jquery.alerts.custom.js"></script>
<script type="text/javascript" src="${contextPath}/editor/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/editor/js/jquery-ui.js"></script>
<script type="text/javascript" src="${contextPath}/editor/js/jquery.ui.touch-punch.min.js"></script>


<%@ include file="/include/admin.jspf" %>

<script type="text/javascript">
	
	<%--
	$(function() {
	
		$("body").removeClass("edit");
		$("body").addClass("devpreview");
		clearDemo();
		$(".demo").html('${editData}');
	});
	--%>

	// 수정
	function update() {
		document.location = "/editor/sTemplate_update.htm?id=${contentEditor.id}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/editor/simpleDelete.json', '${contentEditor.id}', function() { 
			search(); 
		});
	}

	// 검색 페이지
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			document.location = "/editor/sTemplate_search.htm?" + callbackUrl;
		} else {
			document.location = "/editor/sTemplate_search.htm";
		}
	}
</script>
</head>

<body class="edit">

	<input type="hidden" id="id" name="id" value="${contentEditor.id}"/>

	<div class="wrap00">
		<div class="section1">
			<table style="width:100%">
				<tr class="line-top"><td colspan="6"/></tr>
				<!-- ID -->
				<tr>
					<td height="25" class="td01">
						<span class="bul_dot1">◆</span>
						<fmt:message key="label.common.id"/><font color="#FF0000">*</font>
					</td>
					<td colspan="5" class="td02" style="height:30px;">
						${contentEditor.id}
					</td>
				</tr>
				<tr class="line-dot"><td colspan="6"/></tr>
				<!-- Title -->
				<tr>
					<td height="25" class="td01">
						<span class="bul_dot1">◆</span>
						<fmt:message key="label.contentEditor.title"/><font color="#FF0000">*</font>
					</td>
					<td colspan="5" class="td02" style="height:30px;">
						${contentEditor.title}
					</td>
				</tr>
				<tr class="line-dot"><td colspan="6"/></tr>
		 		<tr>
<!-- 
					<td height="25" class="td01">
						<span class="bul_dot1">◆</span>
						<fmt:message key="label.contentEditor.content"/><font color="#FF0000">*</font>
					</td>
 -->
					<td colspan="6" class="td02">
						<div>${contentEditor.downloadData}</div>
					</td>
				</tr>
			</table>
		</div>
	</div>

<div class="wrap00">
	<div class="line-clear"></div>
	<div class="line-bottom"></div>
	<div class="line-clear"></div>
	<div class="" style="float:right">
		<ul class="nav" id="menu-layoutit">
			<li class="divider-vertical"></li>
			<li>
			<c:if test="${authority.create}">
				<button class="btn btn-jquery" id="modify" onclick="javascript:update()"><fmt:message key="label.common.modify"/></button>
			</c:if>
			<c:if test="${authority.create}">			
				<button class="btn btn-jquery" id="delete" onclick="javascript:confirmAndDelete()"><fmt:message key="label.common.delete"/></button>
			</c:if>
				<button class="btn btn-jquery" id="search" onclick="javascript:search()"><fmt:message key="label.common.list"/></button>
			</li>
		</ul>
	</div>
</div>
</body>
</html>