<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript" src="/js/jquery/ui/jquery.ui.button.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("input:button").button();
	});

	function addPushToken(id, pushToken) {
    	if (!window.opener.duplicationCheck(id, "pushToken")) {
    		jAlert("The following items already exist:\n" + pushToken);
    		return;
    	}

    	//step 2:add tr into parent window.
  	    var trCode = "<tr>" +
	   	        	"<td><input type='hidden' name='pushTokenIds' id='pushTokenIds' value='" + id + "'>" + pushToken + "</td>" +
	   	        	"<td onclick='removeTr(this)'><img src='/images/button/btn_remove.png' width='20' height='20'></td>" +
	   	        	"</tr>";
  		$("#pushTokens tbody", opener.document).append(trCode);
	}

	function search() {
		document.vForm.submit();
	}
</script>
<form method="post" name="vForm" action="/push/popup/selectPushToken.htm">
<div class="line-clear"></div>
<table style="width:1010px;">
	<tr>
		<td>
		<!-- search _ start -->
		<c:set var="searchOptionKeys">pushToken</c:set>
		<c:set var="searchOptionValues">
			<fmt:message key="label.endUser.pushToken"/>
		</c:set>
		<%@ include file="/include/common/searchSelectForm.jsp" %>
		<!-- search _ end -->

		<!-- list _ start -->
		<display:table name="pushTokens" id="pushToken" class="simple" style="margin: 5px 0pt;"
				requestURI="/push/popup/selectPushToken.htm" pagesize="10" export="false">
			<display:column titleKey="label.endUser.pushToken" property="pushToken"/>
			<display:column titleKey="label.common.command" media="html" style="width: 10%;">
			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td style="border: 0;">
						<input type="button" onclick="addPushToken(${pushToken.id}, '${pushToken.pushToken}')" value="<fmt:message key='label.common.add'/>" style="width: 80px;">
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