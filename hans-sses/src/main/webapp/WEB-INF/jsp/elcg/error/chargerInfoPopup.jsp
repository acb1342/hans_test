<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/common.css" media="all">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/popup.css" media="all">
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>

<form method="post" id="vForm" name="vForm" action="/elcg/error/chargerInfoPopup.htm">

	<div class="wrap_winpop">
		<div class="header_area">
			<h3 class="tit">충전기 설치 정보</h3>
		</div>
	
		<table style="width:100%">
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.mgmtNo"/>
			</td>
			<td colspan="2" class="td02">${charger.mgmtNo}</td>
		</tr>
		<tr class="line-dot"><td colspan="2"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerGroupName"/>
			</td>
			<td colspan="2" class="td02">${charger.chargerGroup.name}</td>
		</tr>
		<tr class="line-dot"><td colspan="2"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.detailName"/>
			</td>
			<td colspan="2" class="td02">${charger.chargerGroup.bd.name}</td>
		</tr>
		<tr class="line-dot"><td colspan="2"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.buildingName"/>
			</td>
			<td colspan="2" class="td02">${charger.chargerGroup.bd.bdGroup.name}</td>
		</tr>
		<tr class="line-dot"><td colspan="2"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.addr"/>
			</td>
			<td colspan="2" class="td02">${charger.chargerGroup.bd.addr}</td>
		</tr>
		<tr class="line-dot"><td colspan="2"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.ownerName"/>
			</td>
			<td colspan="2" class="td02">${owner.name}</td>
		</tr>
		<tr class="line-dot"><td colspan="2"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.ownerPhone"/>
			</td>
			<td colspan="2" class="td02">${owner.mobile}</td>
		</tr>
		<tr class="line-dot"><td colspan="2"/></tr>
	</table>
</div>
</form>