<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript" src="/js/jquery/ui/jquery.ui.button.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("input:button").button();
	});

	function addPushBody(id) {
		window.opener.setPushBody(id);
  		window.close();
	}

	function search() {
		document.vForm.submit();
	}

	// preview
	function preview(id) {
		var popupStatus = "width=767, height=900, toolbar=no, location=no, status=no, menubar=no, scrollbars=yes, resizable=yes ";
		window.open("/editor/popup/preview.htm?id=" + id, "uPUSH", popupStatus);
	}
</script>
<form method="post" name="vForm" action="/push/popup/selectPushBody.htm">
<div class="line-clear"></div>
<table style="width:1010px;">
	<tr>
		<td>
		<!-- search _ start -->
		<c:set var="searchOptionKeys">title</c:set>
		<c:set var="searchOptionValues">
			<fmt:message key="label.contentEditor.title"/>
		</c:set>
		<%@ include file="/include/common/searchSelectForm.jsp" %>
		<!-- search _ end -->

		<!-- list _ start -->
		<display:table name="editors" id="editor" class="simple" style="margin:5px 0pt;" requestURI="/push/popup/selectPushBody.htm" pagesize="10" export="false">
			<display:column titleKey="label.common.id" property="id"/>
			<display:column titleKey="label.contentEditor.title" property="title"/>
			<display:column titleKey="label.common.createDate" media="html">
				<fmt:formatDate value="${editor.createDate}" pattern="yyyy/MM/dd"/>
			</display:column>
			<display:column titleKey="label.common.modifyDate" media="html">
				<fmt:formatDate value="${editor.modifyDate}" pattern="yyyy/MM/dd"/>
			</display:column>
			<display:column titleKey="label.common.command" style="text-align:center; width:255px;" media="html">
				<input type="button" onclick="addPushBody(${editor.id})" value="<fmt:message key='label.common.select'/>" style="width:80px;">
				<input type="button" value='<fmt:message key="label.common.preview"/>' onclick="javascript:preview('${editor.id}')"/  style="width:80px;">
			</display:column>
		</display:table>
		<!-- list _ end -->
		</td>
	</tr>
</table>
</form>
<%@ include file="/include/footer.jspf" %>