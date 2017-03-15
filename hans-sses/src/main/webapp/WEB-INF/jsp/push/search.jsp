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
		document.location = "/push/create.htm";
	}
	
	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/push/delete.json', function() { 
			search();
		});
	}
	
	// 검색 조건 변경
	function searchChange() {
	}
</script>
<form method="get" id="vForm" name="vForm" action="/push/search.htm">
<div class="wrap00">
	<!-- search _ start -->
	<fieldset class="searchBox">
		<table align="left" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<td width="80" style="padding-bottom:7px;"><fmt:message key="label.common.description"/></td>
				<td style="padding-bottom:7px;">
					<input type="text" id="description" name="description" value="${description}" style="width:327px"/>
				</td>
				<td width="55"></td>
				<td width="80" style="padding-bottom:7px;"><fmt:message key="label.application.appName"/></td>
				<td style="padding-bottom:7px;">
					<input type="text" id="appName" name="appName" value="${appName}" style="width:327px"/>
				</td>
			</tr>
			<tr>
			<c:if test="${loginGroup.name eq 'ADMIN'}">
				<td ><fmt:message key="label.provider.cpId"/></td>
				<td>
					<input type="text" id="cpId" name="cpId" value="${cpId}" style="width:327px"/>
				</td>
				<td></td>
			</c:if>
				<td><fmt:message key="label.pushSvc.sendReqType"/></td>
				<td>
					<select id="sendReqType" name="sendReqType" style="width:337px">
						<option value="" <c:if test="${sendReqType == ''}">selected</c:if>>ALL</option>
						<option value="APP_ID" <c:if test="${sendReqType == 'APP_ID'}">selected</c:if>>APP_ID</option>
						<option value="MDN" <c:if test="${sendReqType == 'MDN'}">selected</c:if>>MDN</option>
						<!--<option value="PUSH_TOKEN" <c:if test="${sendReqType == 'PUSH_TOKEN'}">selected</c:if>>PUSH_TOKEN</option>-->
					</select>
					&nbsp;
					<button class="search" type="submit" id="search"><fmt:message key="label.common.search"/></button>
				</td>
			</tr>	
		</table>
	</fieldset>
	<!-- search _ end -->

	<!-- list _ start -->
	<display:table name="pushSvcs" id="pushSvc" class="simple" style="margin:5px 0pt;" requestURI="/push/search.htm" pagesize="10" export="false">
	<c:if test="${authority.delete}">
		<display:column titleKey="label.common.select" style="width:40px;" media="html">
			<input type="checkbox" name="selected" style="width:15px;" value="${pushSvc.id}"/>
		</display:column>
	</c:if>
		<display:column titleKey="label.pushSvc.requestId" property="id"/>
		<display:column titleKey="label.common.description" media="html">
			<c:if test="${pushSvc.description != '' || pushSvc.description != null}">${pushSvc.description}</c:if>
			<c:if test="${pushSvc.description == '' || pushSvc.description == null}">-</c:if>			
		</display:column>
		<display:column titleKey="label.provider.cpId" property="cpId"/>
		<display:column titleKey="label.pushSvc.sendReqType" property="sendReqType"/>
		<display:column titleKey="label.pushSvc.sendMsgType" media="html">
			<c:if test="${pushSvc.sendMsgType == 'BOTH' }">Both (Noti+Push)</c:if>
			<c:if test="${pushSvc.sendMsgType == 'NOTI' }">Noti Only</c:if>
			<c:if test="${pushSvc.sendMsgType == 'PUSH' }">Push Only</c:if>
		</display:column>
		<display:column titleKey="label.application.appName" property="appName" maxLength="20"/>
		<display:column titleKey="label.pushSvc.status" media="html">
			<c:if test="${pushSvc.status == 'NOTCOMPLETE'}"><font color="red">Not Completed</font></c:if>
			<c:if test="${pushSvc.status == 'COMPLETE'}">Completed</c:if>			
		</display:column>
		<display:column titleKey="label.common.createDate" property="createDate" format="{0,date,yyyy/MM/dd HH:mm}"/>
		<display:column titleKey="label.common.detail" style="text-align:center; width:140px;" media="html">
			<input type="button" value='<fmt:message key="label.common.detail"/>' onclick="location.href='/push/detail.htm?id=${pushSvc.id}'"/>
		<c:if test="${authority.update}">
			<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/push/update.htm?id=${pushSvc.id}'"/>
		</c:if>
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
		
<script type="text/javascript">
	$('#pushSvc tr').each(function() {
		$(this).find("td").each(function() {
			if ($(this).html().trim() == '') { $(this).html('NONE'); }
		});
	});
</script>
</div>
</form>
<%@ include file="/include/footer.jspf" %>