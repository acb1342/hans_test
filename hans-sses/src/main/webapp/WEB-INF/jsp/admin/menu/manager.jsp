<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<style type="text/css" media="all">
	@import url("/js/extjs/resources/css/ext-all.css");
	@import url("/js/extjs/resources/css/xtheme-gray.css");
</style>
<script src="/js/extjs/adapter/ext/ext-base.js" type="text/javascript"></script>
<script src="/js/extjs/ext-all.js" type="text/javascript"></script>
<script type="text/javascript">
<c:if test="${message != null}">
	jAlert('${message}');
</c:if>
	var tree = parent.leftFrame.tree;
	var reloadNode = tree.getNodeById("${reloadNodeId}");
	reloadNode.reload();
	reloadNode.expand(1);
<c:if test="${redirectUrl != null}">
	document.location.href = "${redirectUrl}";
</c:if>
</script>
<%@ include file="/include/footer.jspf" %>
