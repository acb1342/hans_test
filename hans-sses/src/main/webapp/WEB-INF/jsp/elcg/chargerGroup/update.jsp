<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroupAuth" %>
<%@ page import="com.mobilepark.doit5.admin.model.Menu" %>
<%@ page import="java.util.Map" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function(){
		// 저장
		$('#save').click(function(e) {
			if(!validationCheck()) return ; 
			
			$("#vForm").submit();					
		});
		
	});

	function cancel() {
		history.go(-1);
	}
	
	function validationCheck() {
		var groupId = ${loginGroup.id};
		
		if(groupId == 1) {
			if( !$('#capacity').val().match(/^\d+$/ig) ) {
				alert("최대전류량은 숫자만 입력 가능합니다.");
				return ;
			}
			
			if( $('#capacity').val().trim() == '' ) {
				alert("최대전류량을 입력하세요.");
				return;
			}
			
			if( $('#name').val().trim() == '' ) {
				alert("충전그룹명을 입력하세요.");
				return;
			}
		}
		if( $('#description').val().trim() == '' ) {
			alert("설명을 입력하세요.");
			return;
		}
		
		return true;
	}

</script>

<spring:hasBindErrors name="chargerGroup"/>
<form method="post" id="vForm" name="vForm" action="/elcg/chargerGroup/update.htm?seq=${chargerGroup.chargerGroupId}">

<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-dot"><td colspan="4"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerGroupName"/>
			</td>
			<td class="td02">
				<c:if test="${loginGroup.id == 1 }">
					<input type="text" id="name" name="name" value="${chargerGroup.name}" />
				</c:if>
				<c:if test="${loginGroup.id != 1 }">
					<input type="hidden" name="name" value="${chargerGroup.name}"/>
					${chargerGroup.name}
				</c:if>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.max"/>
			</td>
			<td class="td02">
			<c:if test="${loginGroup.id == 1 }">
				<input type="text" id="capacity" name="capacity" value="${chargerGroup.capacity}" />
			</c:if>
			<c:if test="${loginGroup.id != 1 }">
				<input type="hidden" name="capacity" value="${chargerGroup.capacity}"/>
				${chargerGroup.capacity}
			</c:if>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.buildingName"/>
			</td>
			<td class="td02">${chargerGroup.bd.bdGroup.name}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.detailName"/>
			</td>
			<td class="td02">${chargerGroup.bd.name}</td>	
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
		<tr>
			<td style="width:15%" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerGroupDescription"/>
			</td>
			<td style="width:35%" class="td02">
				<textarea id="description" name="description">${chargerGroup.description}</textarea>
			</td>
			<td style="width:15%" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerCnt"/>
			</td>
			<td style="width:35%" class="td02">${chargerCnt}</td>
		</tr>
		<tr class="line-dot"><td colspan="4"/></tr>
		
	</table>
	<br/>
	<c:if test="${ fn:length(chargerGroup.chargerList) > 0 }">
			<table style="width:100%">
			<tr class="line-top"><td colspan="4"/></tr>
			<tr>
				<td height="25" class="td01"><fmt:message key="label.elcg.mgmtNo"/></td>
			</tr>
				<c:forEach items="${chargerGroup.chargerList}" var="charger" varStatus="status">	
					<tr>
						<td style="width:25%" class="td02">${charger.chargerList.mgmtNo}</td>
					</tr>
					<tr class="line-dot"><td colspan="4"/>			
				</c:forEach>
			</table>
		</c:if>
	<!-- input _ end -->
			
	<div class="line-clear"></div>
		
	<!-- button _ start -->
	<div class="footer"><br/>
		<table style="width:100%">
		<tr>
			<td width="50%" height="30"></td>
			<td width="50%" align="right">		
				<c:if test="${authority.update}">
					<input type="button" id="save" value='<fmt:message key="label.common.save"/>'/>
				</c:if>
				<c:if test="${authority.delete}">
					<input type="button" value='<fmt:message key="label.common.cancel"/>' onclick="javascript:cancel()"/>
				</c:if>
			</td>
		</tr>
		</table>
	</div>				
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>