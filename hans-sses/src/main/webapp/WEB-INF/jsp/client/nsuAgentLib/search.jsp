<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript" src="/js/jquery/ui/jquery.filestyle.js"></script>
<script type="text/javascript">
	function insert() {
		document.location = "/client/nsuAgentLib/create.htm";
	}

	function search() {
		document.vForm.submit();
	}

	function confirmAndDelete() {
		var ids = getSelectedIds();
		if (ids == "" || ids == null) {
			jAlert('<fmt:message key="statement.confirm.noselect"/>');
			return ;
		}

		jConfirm('<fmt:message key="statement.confirm.delete"/>', 'Confirm', function(r) {
			if (r) {
				$.ajax({
					url:'/client/nsuAgentLib/delete.json',
					type:"POST",
					dataType:'json',
					data:{
						id:ids
					},
					success:function(isDelete) {
						if (isDelete) {
							jAlert('<fmt:message key="statement.delete.success"/>','Alert', function() {
								search();
							});
						} else {
							jAlert('<fmt:message key="statement.delete.fail"/>');
						}
					}
				});
			}
		});
	}

	$(function () {
		$("#selectStatusWidget").dialog({
			autoOpen:false,
			modal:true,
			buttons:{
				'<fmt:message key="label.common.select"/>':function() {
					$(this).dialog("close");
					updateStatus();
				},
				'<fmt:message key="label.common.cancel"/>':function() {
					$(this).dialog("close");
				}
			}
		});

		$("#selectOsWidget").dialog({
			autoOpen:false,
			modal:true,
			buttons:{
				'<fmt:message key="label.common.select"/>':function() {
					$(this).dialog("close");
					changeOs();
				},
				'<fmt:message key="label.common.cancel"/>':function() {
					$(this).dialog("close");
				}
			}
		});
		
		$("#updateStatusBtn").click(function() {
			var ids = getSelectedIds();
			if (ids == "" || ids == null) {
				jAlert('<fmt:message key="statement.confirm.noselect"/>');
				return ;
			}
			
			$("#selectStatusWidget").dialog("open");
		});

		$("#deployBtn").click(function() {
			$("#selectOsWidget").dialog("open");
		});

		function updateStatus() {
			var ids = getSelectedIds();
			if (ids == "" || ids == null) {
				jAlert('<fmt:message key="statement.confirm.noselect"/>');
				return;
			}

			var status = $("#updateStatus").val();
			jConfirm('<fmt:message key="statement.confirm.update"/>', 'Confirm', function(r) {
				if (r) {
					$.ajax({
						url:'/client/nsuAgentLib/updateStatus.json',
						type:"POST",
						dataType:'json',
						data:{
							id:ids,
							status:status
						},
	
						success:function(isSuccess) {
							if (isSuccess) {
								jAlert('<fmt:message key="statement.common.updateStatus.success"/>');
								search();
	
							} else {
								jAlert('<fmt:message key="statement.common.updateStatus.fail"/>');
							}
						}
					});
				}
			});
		}

		function changeOs() {
			var os = $("#os").val();
			jConfirm('<fmt:message key="statement.confirm.deploy"/>', 'Confirm', function(r) {
				if (r) {
					$.ajax({
						url:'/client/nsuAgentLib/deployLastVersion.json',
						type:"POST",
						dataType:'json',
						data:{
							os:os
						},
	
						success:function(isSuccess) {
							if (isSuccess) {
								jAlert('<fmt:message key="statement.deploy.success"/>');
							} else {
								jAlert('<fmt:message key="statement.deploy.fail"/>');
							}
						}
					});
				}
			});
		}
	});

	function getSelectedIds() {
		var ids = [];
		$("input:checkbox:checked").each(function() {
			ids.push($(this).val());
		});
		return ids.join(";");
	}
</script>
<!-- select status widget _ start -->
<div id="selectStatusWidget" title="<fmt:message key="label.common.changestatus"/>">
	<fmt:message key="label.common.status"/>:
	<select name="updateStatus" id="updateStatus">
	<c:forEach items="${statusList}" var="statusEnum">
		<option value="${statusEnum}">
			${statusEnum}
		</option>
	</c:forEach>
	</select>
</div>
<!-- select status widget _ end -->
<!-- select os widget _ start -->
<div id="selectOsWidget" title="<fmt:message key="label.nsuAgentLib.changeOs"/>">
	<fmt:message key="label.nsuAgentLib.os"/>:
	<select name="os" id="os">
	<c:forEach items="${osList}" var="osEnum">
		<option value="${osEnum}">
			${osEnum}
		</option>
	</c:forEach>
	</select>
</div>
<!-- select status widget _ end -->
<form method="get" name="vForm" action="/client/nsuAgentLib/search.htm">
<div class="wrap00">
	<!-- search _ start -->
	<fieldset class="searchBox">
		<table style="margin:5px 0 5px 26px">
			<tr>
				<td width="50"><fmt:message key="label.nsuAgentLib.version"/></td>
				<td>
					<input type="text" id="version" name="version" value="${version}" style="width:367px"/>
				</td>
				<td width="55"></td>
				<td width="50"><fmt:message key="label.common.status"/></td>
				<td>
					<select name="status" style="width:357px">
						<option value=""><fmt:message key="label.common.all"/></option>
					<c:forEach items="${statusList}" var="statusEnum">
						<option value="${statusEnum}" ${statusEnum == status ? 'selected':''}>
							${statusEnum}
						</option>
					</c:forEach>
					</select>
					&nbsp;
					<button type="submit" id="search"><fmt:message key="label.common.search"/></button>			
				</td>
			</tr>
		</table>
	</fieldset>
	<!-- search _ end -->
	<!-- list _ start -->
	<display:table name="nsuAgentLibs" id="nsuAgentLib" class="simple" style="margin:5px 0pt;" requestURI="/client/nsuAgentLib/search.htm" pagesize="10" export="false">
	<c:if test="${authority.delete}">
		<display:column titleKey="label.common.select" style="width:40px;" media="html">
			<input type="checkbox" id="selected" name="selected" style="width:15px;" value="${nsuAgentLib.id}"/>
		</display:column>
	</c:if>
		<display:column titleKey="label.common.id" property="id"/>
		<display:column titleKey="label.nsuAgentLib.version" property="version"/>
		<display:column titleKey="label.nsuAgentLib.os" property="os"/>
		<display:column titleKey="label.nsuAgentLib.langCode" property="langCode" />
		<display:column titleKey="label.nsuAgentLib.ynMandatory" property="ynMandatory" />
		<display:column titleKey="label.nsuAgentLib.applyStartYmd" media="html">
			<fmt:formatDate value="${nsuAgentLib.applyStartYmd}" pattern="yyyy/MM/dd"/>
		</display:column>
		<display:column titleKey="label.common.status" property="status"/>
		<display:column titleKey="label.common.detail" style="text-align:center; width:140px;" media="html">
			<input type="button" value='<fmt:message key="label.common.detail"/>' onclick="location.href='/client/nsuAgentLib/detail.htm?id=${nsuAgentLib.id}'"/>
		<c:if test="${authority.update}">
			<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/client/nsuAgentLib/update.htm?id=${nsuAgentLib.id}'"/>
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
				<!-- td class="btn02">
					<a class="link01" href="javascript:insert();"><fmt:message key="label.common.add"/></a>
				</td -->
				</c:if>
				<c:if test="${authority.delete}">
					<input type="button" value='<fmt:message key="label.common.delete"/>' onclick="javascript:confirmAndDelete()"/>
				<!-- td class="btn02">
					<a class="link01" href="javascript:confirmAndDelete();"><fmt:message key="label.common.delete"/></a>
				</td -->
				</c:if>
				<c:if test="${authority.update}">
					<input type="button" id="updateStatusBtn" value='<fmt:message key="label.common.updateStatus"/>' />
					<input type="button" id="deployBtn" value='<fmt:message key="label.nsuAgentLib.deployLastVersion"/>' />
				<!--td class="btn02">
					<span class="link01" id="updateStatusBtn"><fmt:message key="label.common.updateStatus"/></span>
				</td>
				<td class="btn02">
					<span class="link01" id="deployBtn"><fmt:message key="label.nsuAgentLib.deployLastVersion"/></span>
				</td-->
				</c:if>
			</tr>
		</table>
	</div>
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>