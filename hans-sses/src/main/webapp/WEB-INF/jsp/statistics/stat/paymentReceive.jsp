<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<link href="/js/jquery/ui/month/MonthPicker.min.css" rel="stylesheet" type="text/css" />
<link href="/js/jquery/ui/month/examples.css" rel="stylesheet" type="text/css" />
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="/js/jquery/ui/month/MonthPicker.min.js"></script>
<script type="text/javascript" src="/js/jquery/ui/month/jquery.mtz.monthpicker.js"></script>

<script type="text/javascript">
	$(function() {
		$("#usname").click(function() {
			openWindow("/statistics/stat/userPopup.htm", "userPopup", "width=448,height=448,top=100,left=100");
		});
	});
	
	function sendPG(url) {
		openWindow(url, "_blank", "");
	}
</script>
<form method="get" id="vForm" name="vForm" action="/statistics/stat/payment.htm">
	<div class="wrap00">

		<!-- list _ start -->
		<c:set var="row" value="${ rownum-((page-1)*10) }"/>
		<display:table name="paymentReceiveStat" id="paymentReceiveStat" class="simple" style="margin:5px 0pt;" requestURI="/statistics/stat/paymentReceive.htm" pagesize="10" export="false">
			<display:column titleKey="label.statistics.stat.pgName" property="chargerName"/>
			<display:column titleKey="label.statistics.stat.pgRecevic" media="html" >
				<input type="button" value='<fmt:message key="label.statistics.stat.pgRecevic"/>' onclick="javascript:sendPG('https://iniweb.inicis.com/security/login.do');"/>
			</display:column>
		</display:table>
		<!-- list _ end -->
	
		<!-- button _ start -->
		<%--
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
		--%>
		<!-- button _ end -->
	</div>
</form>
<%@ include file="/include/footer.jspf" %>