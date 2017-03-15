<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript" src="/js/jquery/ui/jquery.ui.button.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("input:button").button();
	});

	function addButton(id, position, title, type, action, color) {
    	if (!window.opener.duplicationCheck(id, "pushButton")) {
    		jAlert("The following items already exist:\n" + id);
    		return;
    	}

    	//step 2:add tr into parent window.
  	    var trCode = "<tr>" +
	   	        	"<td><input type='hidden' name='pushButtonIds' id='pushButtonIds' value='" + id + "'>" + id + "</td>" +
	   	        	"<td>" + position + "</td>" +
	   	        	"<td>" + title + "</td>" +
	   	        	"<td>" + type + "</td>" +
	   	        	"<td>" + action + "</td>" +
					'<td><input type="button" value="<fmt:message key="label.common.remove"/>" onclick="javascript:removeFile(this);" class="ui-button ui-widget ui-state-default ui-corner-all" style="width:70px;"/></td>'+
	   	        	"</tr>";
  		$("#buttons tbody", opener.document).append(trCode);
	}

	function search() {
		document.vForm.submit();
	}
</script>
<form method="post" name="vForm" action="/push/popup/selectButton.htm">
<div class="line-clear"></div>
<table style="width:1010px;">
	<tr>
		<td>
		<!-- search _ start -->
		<c:set var="searchOptionKeys">title</c:set>
		<c:set var="searchOptionValues">
			<fmt:message key="label.pushButton.title"/>
		</c:set>
		<%@ include file="/include/common/searchSelectForm.jsp" %>
		<!-- search _ end -->

		<!-- list _ start -->
		<display:table name="buttons" id="button" class="simple" style="margin: 5px 0pt;"
				requestURI="/push/popup/selectButton.htm" pagesize="10" export="false">
			<display:column titleKey="label.common.id" property="id"/>
			<display:column titleKey="label.pushButton.position" property="position"/>
			<display:column titleKey="label.pushButton.title" property="title"/>
			<display:column titleKey="label.pushButton.type" property="type"/>
			<display:column titleKey="label.pushButton.action" property="action"/>
			<display:column titleKey="label.common.command" media="html" style="width: 10%;">
			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td style="border: 0;">
						<input type="button" onclick="addButton(${button.id}, '${button.position}', '${button.title}', '${button.type}', '${button.action}')" value="<fmt:message key='label.common.add'/>" style="width: 80px;">
					</td>
				</tr>
			</table>
			</display:column>
		</display:table>
		<!-- list _ end -->
		</td>
	</tr>
</table>
</form>
<%@ include file="/include/footer.jspf" %>