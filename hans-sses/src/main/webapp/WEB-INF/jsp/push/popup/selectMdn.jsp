<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript" src="/js/jquery/ui/jquery.ui.button.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("input:button").button();
	});

	function addMdn(id, mdn) {
    	if (!window.opener.duplicationCheck(id, "mdn")) {
    		jAlert("The following items already exist:\n" + mdn);
    		return;
    	}

    	//step 2:add tr into parent window.
  	    var trCode = "<tr>" +
	   	        	"<td><input type='hidden' name='mdnIds' id='mdnIds' value='" + id + "'>" + mdn + "</td>" +
	   	        	"<td onclick='removeTr(this)'><img src='/images/button/btn_remove.png' width='20' height='20'></td>" +
	   	        	"</tr>";
  		$("#mdns tbody", opener.document).append(trCode);
	}

	function search() {
		document.vForm.submit();
	}
</script>
<form method="post" name="vForm" action="/push/popup/selectMdn.htm">
<div class="line-clear"></div>
<table style="width:1010px;">
	<tr>
		<td>
		<!-- search _ start -->
		<c:set var="searchOptionKeys">mdn</c:set>
		<c:set var="searchOptionValues">
			<fmt:message key="label.endUser.mdn"/>
		</c:set>
		<%@ include file="/include/common/searchSelectForm.jsp" %>
		<!-- search _ end -->

		<!-- list _ start -->
		<display:table name="mdns" id="mdn" class="simple" style="margin: 5px 0pt;"
				requestURI="/push/popup/selectMdn.htm" pagesize="10" export="false">
			<display:column titleKey="label.endUser.mdn" property="mdn"/>
			<display:column titleKey="label.common.command" media="html" style="width: 10%;">
			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td style="border: 0;">
						<input type="button" onclick="addMdn(${mdn.id}, '${mdn.mdn}')" value="<fmt:message key='label.common.add'/>" style="width: 80px;">
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