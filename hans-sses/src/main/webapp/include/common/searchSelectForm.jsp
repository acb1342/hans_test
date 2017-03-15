<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style type="text/css" media="all">
fieldset.searchBox {
	background-color:#EDEDED;
	-moz-border-radius:5px;
	border-radius: 5px;
	-webkit-border-radius: 5px;
	border-width: 1px;
	border-style: solid;
	border-color: rgb(204, 204, 204);
	padding-top: 8px;
	padding-bottom: 8px;
}
</style>
<script type="text/javascript">
	function search() {
		document.vForm.submit();
	}
</script>
<c:set var="searchType" value='<%=request.getParameter("searchType")%>'/>
<c:set var="searchValue" value='<%=request.getParameter("searchValue")%>'/>
<fieldset class="searchBox">
	<table align="left" style="margin:5px 0px 5px 26px;" border="0" cellpadding="1" cellspacing="0">
		<tr>
			<td class="td00">
				<select name="searchType" style="width:300px;">
				<c:forTokens var="key" items="${searchOptionKeys}" delims="," varStatus="statKey">
					<c:forTokens var="val" items="${searchOptionValues}" delims="," varStatus="statVal">
						<c:if test="${statKey.index == statVal.index}">
							<option value="${key}" ${key == searchType ? 'selected' : ''}>${val}</option>
						</c:if>
					</c:forTokens>
				</c:forTokens>
				</select>
			</td>
			<td align="left">
				<input type="text" name="searchValue" value="${searchValue}" onkeydown="javascript: if (event.keyCode == 13) {search();}" style="width:578px;margin:0 7px 0 8px;" />
				<input type="button" value='<fmt:message key="label.common.search"/>' onclick="javascript:search()"/>
			</td>
		</tr>
	</table>
</fieldset>