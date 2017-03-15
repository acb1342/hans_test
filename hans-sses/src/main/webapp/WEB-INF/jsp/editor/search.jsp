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

	// 등록
	function insert() {
		document.location = "/editor/create.htm";
	}

	function copyTemplate(url, id, template, callback) {
		
		jConfirm('<fmt:message key="statement.confirm.copy"/>', 'Confirm', function(r) {
			if (r) {
				$.ajax({
					url:url,
					type:"POST",
					dataType:'json',
					data:{
						id:id,
						template:template
					},
					success:function(isCopy) {
						if (isCopy) {
							jAlert('<fmt:message key="statement.copy.success"/>', 'Alert', function() {
								// 저작용 복사인 경우 N, 템플릿 복사인 경우 Y
								if (template == 'N')
									document.location = "/editor/search.htm";
								else
									callback();
							});
						} else {
							jAlert('<fmt:message key="statement.copy.fail"/>');
						}
					}
				});
			}
		});
	}	
	
	// 복사
	function copy(id, template) {
		copyTemplate('/editor/copyTemplate.json', id, template, function() {
			search();
		});
	}
	
	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/editor/delete.json', function() { 
			search();
		});
	}

	// preview
	function preview(id) {
		var popupStatus = "width=767, height=900, toolbar=no, location=no, status=no, menubar=no, scrollbars=yes, resizable=yes ";
		window.open("/editor/popup/preview.htm?id=" + id, "uPUSH", popupStatus);
	}

	// 검색 조건 변경
	function searchChange() {
	}
</script>
<form method="get" id="vForm" name="vForm" action="/editor/search.htm">
<div class="wrap00">
	<!-- search _ start -->
	<c:set var="searchOptionKeys">title</c:set>
	<c:set var="searchOptionValues">
		<fmt:message key="label.contentEditor.title"/>
	</c:set>
	<%@ include file="/include/common/searchSelectForm.jsp" %>
	<!-- search _ end -->
	
	<!-- list _ start -->
	<display:table name="contentEditors" id="contentEditor" class="simple" style="margin:5px 0pt;" requestURI="/editor/search.htm" pagesize="10" export="false">
	<c:if test="${authority.delete}">
		<display:column titleKey="label.common.select" style="width:40px;" media="html">
			<input type="checkbox" name="selected" style="width:15px;" value="${contentEditor.id}"/>
		</display:column>
	</c:if>
		<display:column titleKey="label.common.id" property="id" style="width:40px;"/>
		<display:column titleKey="label.contentEditor.title" property="title" style="text-align:left;width:45%;"/>
		<display:column titleKey="label.common.createDate" media="html">
			<fmt:formatDate value="${contentEditor.createDate}" pattern="yyyy/MM/dd"/>
		</display:column>
		<display:column titleKey="label.common.modifyDate" media="html">
			<fmt:formatDate value="${contentEditor.modifyDate}" pattern="yyyy/MM/dd"/>
		</display:column>
		<display:column titleKey="label.common.detail" style="text-align:center; width:255px;" media="html">
			<input type="button" value='<fmt:message key="label.common.detail"/>' onclick="location.href='/editor/detail.htm?id=${contentEditor.id}'"/>
		<c:if test="${authority.update}">
			<input type="button" value='<fmt:message key="label.common.copy"/>' onclick="javascript:copy('${contentEditor.id}', 'N')"/>
		</c:if>

		<input type="button" value='<fmt:message key="label.common.preview"/>' onclick="javascript:preview('${contentEditor.id}')"/>
		</display:column>
	</display:table>
	<!-- list _ end -->
	
	<div class="line-clear"></div>
	
	<!-- button _ start -->
	
	<div class="footer">
		<table style="width:100%">
			<tr>
				<td height="30" align="right">
				<c:if test="${authority.create}">
					<input type="button" value='<fmt:message key="label.common.add"/>' onclick="javascript:insert()"/>
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
</form>
<%@ include file="/include/footer.jspf" %>