<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">

// 초기화
	$(function () {
		searchChange();
	});

	// 검색
	function search() {
		$("#vForm").submit();
	}

	function selectTemplate(id) {
		window.opener.setSelectedTemplate(id);
  		window.close();
	}
	
	// preview
	function previewTemplate(id) {
		var popupStatus = "width=767, height=900, toolbar=no, location=no, status=no, menubar=no, scrollbars=yes, resizable=yes ";
		window.open("/editor/popup/preview.htm?id=" + id, "uPUSH", popupStatus);
	}

	// 검색 조건 변경
	function searchChange() {
	}
</script>
<form method="get" id="vForm" name="vForm" action="/editor/popup/sTemplateList.htm">
<div class="wrap00">
	<!-- search _ start -->
	<c:set var="searchOptionKeys">title</c:set>
	<c:set var="searchOptionValues">
		<fmt:message key="label.contentEditor.title"/>
	</c:set>
	<%@ include file="/include/common/searchSelectForm.jsp" %>
	<!-- search _ end -->
	
	<!-- list _ start -->
	<display:table name="contentEditors" id="contentEditor" class="simple" style="margin:5px 0pt;" requestURI="editor/popup/sTemplateList.htm" pagesize="10" export="false">
		<display:column titleKey="label.common.id" property="id" style="width:40px;"/>
		<display:column titleKey="label.contentEditor.title" property="title" style="text-align:left;width:40%;"/>
		
		<display:column titleKey="label.common.detail" style="text-align:center; width:450px;" media="html">
			<input type="button" value='<fmt:message key="label.common.preview"/>' onclick="javascript:previewTemplate('${contentEditor.id}')"/>
			<input type="button" value='<fmt:message key="label.common.select"/>' onclick="javascript:selectTemplate('${contentEditor.id}')"/>
		</display:column>
		
		
	</display:table>
	<!-- list _ end -->
	
	<div class="line-clear"></div>
	
</div>
</form>
<%@ include file="/include/footer.jspf" %>