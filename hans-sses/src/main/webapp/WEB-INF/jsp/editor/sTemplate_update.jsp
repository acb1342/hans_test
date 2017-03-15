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
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
<script type="text/javascript" src="${contextPath}/editor/js/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="${contextPath}/editor/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/editor/js/jquery-ui.js"></script>
<script type="text/javascript" src="${contextPath}/editor/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="${contextPath}/editor/js/jquery.htmlClean.js"></script>
<script type="text/javascript" src="${contextPath}/editor/ckeditor453/ckeditor.js"></script>
<script type="text/javascript" src="${contextPath}/editor/ckeditor453/config.js"></script>

<script type="text/javascript" src="${contextPath}/js/jquery/form/jquery.form.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery/validator/jquery.validate.custom.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery/validator/jquery.validate.ext.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery/alert/jquery.alerts.custom.js"></script>

<!-- 
<script type="text/javascript" src="${contextPath}/editor/js/scripts.js"></script>

<script type="text/javascript" src="${contextPath}/js/common.js"></script>
<script type="text/javascript" src="${contextPath}/js/design.js"></script>
-->

<script type="text/javascript">
	$(function() {

		var editor = CKEDITOR.replace('content',{
	    	enterMode:'2',
	    	shiftEnterMode:'3',
	    	height : '417px'
	    });
		
		CKEDITOR.on( 'dialogDefinition', function( e ) {
	    	var dialogName = e.data.name;
	    	var dialog     = e.data.definition.dialog;

	        if (dialogName == 'image') {
	        	dialog.on('show', function () {
	        		dialog.hidePage('Link');
	        		dialog.selectPage('Upload');
	            });
	        } else if (dialogName == 'link') {
	        	dialog.on('show', function () {
	        		dialog.hidePage('target');
	        		dialog.selectPage('upload');
	            });
	        }	    
		});
	
		// form 방식
		$('#save').click(function(e) {
			e.preventDefault();
		  
			var contentEditor = CKEDITOR.instances.content;
			$("#editData").val(contentEditor.getData());
			$("#downloadData").val(contentEditor.getData());
			      
			$("#vForm").submit();
		});
	
		// 목록
		$("#cancel").click(function(e) {
			document.location = "/editor/sTemplate_search.htm";
		});
		
		// 유효성 검사
		$("#vForm").validate({
			onfocusout:false,
			onkeyup:false,
			rules:{
				title:"required"
			},
			messages:{
				title:{
					required:'<fmt:message key="validate.required"/>'
				}
			}
		});
	});
</script>
</head>
<body class="edit">
<form method="post" id="vForm" name="vForm" action="/editor/sTemplate_update.htm">
	<input type="hidden" id="editData" name="editData" value=""/>
	<input type="hidden" id="downloadData" name="downloadData" value=""/>

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
				<td colspan="6" class="td02">
					<input type="text" id="id" name="id" readOnly="readonly" value="${contentEditor.id}" style="width:500px;margin-bottom:0px;">
				</td>
			</tr>
			<tr class="line-dot"><td colspan="6"/></tr>
			<!-- Title -->
			<tr>
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.contentEditor.title"/><font color="#FF0000">*</font>
				</td>
				<td colspan="6" class="td02">
					<input type="text" id="title" name="title" value="${contentEditor.title}" style="width:500px;margin-bottom:0px;">
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
					<textarea name="content" id="content" style="width:100%; height:420px;">${contentEditor.downloadData}</textarea>
				</td>
			</tr>
			<tr class="line-dot"><td colspan="6"/></tr>

		</table>
	</div>
</div>
</form>

<div class="wrap00">
	<div class="line-bottom"></div>
	<div class="line-clear"></div>
	<div class="" style="float:right">
		<ul class="nav" id="menu-layoutit">
			<li class="divider-vertical"></li>
			<li>
			<c:if test="${authority.create}">
				<button class="btn btn-jquery" id="save"><fmt:message key="label.common.save"/></button>
			</c:if>
				<button class="btn btn-jquery" id="cancel"><fmt:message key="label.common.cancel"/></button>
			</li>
		</ul>
	</div>
	<!-- 
	<div class="" style="float:right">
		<ul class="nav" id="menu-layoutit">
			<li class="divider-vertical"></li>
			<li>
				<button class="btn btn-jquery" id="preview"><i class="icon-eye-open icon-white"></i> <fmt:message key="label.common.preview"/></button>
				<button class="btn btn-jquery" id="undo" ><i class="icon-arrow-left icon-white"></i> <fmt:message key="label.common.undo"/></button>
				<button class="btn btn-jquery" id="redo" ><i class="icon-arrow-right icon-white"></i> <fmt:message key="label.common.redo"/></button>
				<button class="btn btn-jquery" id="clear"><i class="icon-repeat icon-white"></i> <fmt:message key="label.common.clear"/></button>
			</li>
		</ul>
	</div>
	 -->
</div>
</body>
</html>