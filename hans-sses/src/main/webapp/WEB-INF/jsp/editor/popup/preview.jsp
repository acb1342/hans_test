<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="u" uri="/WEB-INF/tlds/util.tld" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="title" content="uPUSH" />
<meta name="description" content="uPUSH" />
<meta name="keywords" content="" />
<meta name="language" content="ko" />

<title>Admin - SKT EVC</title>

<!-- Le styles -->
<link href="${contextPath}/editor/css/bootstrap-combined.min.css" rel="stylesheet">

<!-- fav and touch icons -->
<link rel="shortcut icon" href="http://faviconist.com/icons/2d3196a422c2b68cb307c4d8a1958527/favicon.ico" />

<script type="text/javascript" src="${contextPath}/editor/js/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="${contextPath}/editor/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/editor/js/jquery-ui.js"></script>
<script type="text/javascript" src="${contextPath}/editor/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript">
	function supportstorage() {
		if (typeof window.localStorage == 'object') { 
			return true;
		} else {
			return false;
		}
	}
	
	function cleanHtml(e) {
		$(e).parent().append($(e).children().html());
	}

	$(function() {
		var tmp = '';
		tmp = '${downloadData}';
		
		if (tmp != '') {
			$('#content-preview').html(tmp);
		} else {
			if (supportstorage()) {
				layouthistory = JSON.parse(localStorage.getItem("layoutdata"));
				if (!layouthistory) return false;
				tmp = layouthistory.list[layouthistory.count-1];
				
				$('#content-preview').html(tmp);

				var t = $("#content-preview").children();
				t.find(".preview, .configuration, .drag, .remove").remove();
				t.find(".lyrow").addClass("removeClean");
				t.find(".box-element").addClass("removeClean");
				t.find(".lyrow .lyrow .lyrow .lyrow .lyrow .removeClean").each(function() {
					cleanHtml(this);
				});
				t.find(".lyrow .lyrow .lyrow .lyrow .removeClean").each(function() {
					cleanHtml(this);
				});
				t.find(".lyrow .lyrow .lyrow .removeClean").each(function() {
					cleanHtml(this);
				});
				t.find(".lyrow .lyrow .removeClean").each(function() {
					cleanHtml(this);
				});
				t.find(".lyrow .removeClean").each(function() {
					cleanHtml(this);
				});
				t.find(".removeClean").each(function() {
					cleanHtml(this);
				});
				t.find(".removeClean").remove();
			}
		}
	});
</script>
</head>
<body>
	<div class="container"><div id="content-preview"></div></div>
</body>
</html>