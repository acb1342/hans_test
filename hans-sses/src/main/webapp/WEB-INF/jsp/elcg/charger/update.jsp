<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroupAuth" %>
<%@ page import="com.mobilepark.doit5.admin.model.Menu" %>
<%@ page import="java.util.Map" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 저장
		$('#save').click(function(e) {	
			$("#vForm").submit();					
		});
		
		$('#validationCheck').click(function(e) {
			if (!Boolean($("#serial").val())) {
				alert("S/N을 입력하세요.");
				return;
			}
			else {
				$.ajax({
					type:'POST',
					url:'/elcg/charger/serialCheck.json',
					data:{
						serial:$("#serial").val(),					
					},
					success:function (data) {
						if(data.result=="1") {
							alert("이미 존재하는 충전기입니다.");
							return ;
						}	
							var chargerId = data.chargerId;
							var serialNo  = data.serialNo;
							var capacity  = Number(data.capacity);
							var mgmtNo = data.mgmtNo;
							
							$('#mgmtNo').val(mgmtNo);
							$('#capa').val(capacity);
							$('#chargerId').val(chargerId);
							
							$('#viewMgmtNo').empty();
							$('#viewCapacity').empty();
							
							$('#viewMgmtNo').append(mgmtNo);
							$('#viewCapacity').append(capacity);			
					},
					error: function(e) {
						alert("error!!!");
					}
				});
			}
		});
		
	});

	function cancel() {
		history.back();
	}

</script>

<spring:hasBindErrors name="chargerGroup"/>
<form method="post" id="vForm" name="vForm" action="/elcg/charger/update.htm?seq=${charger.chargerId}">
<div class="wrap00">
<input type="hidden" id="chargerId" name="listChargerId" />
<input type="hidden" id="mgmtNo" name="mgmtNo" value="${charger.mgmtNo}"/>
<input type="hidden" id="capa" name="capacity" value="${charger.capacity}"/>
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.mgmtNo"/>
			</td>
			<td colspan="2" class="td02" id="viewMgmtNo">${charger.mgmtNo}
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.sn"/>
			</td>
			<td colspan="2" class="td02">
			<c:if test="${loginGroup.id == 1 }">
				<input type="text" id="serial" name="serialNo" value="${charger.chargerList.serialNo}"/>
				<input type="button" id="validationCheck" value="유효성 확인" />
			</c:if>
			<c:if test="${loginGroup.id == 2 || loginGroup.id == 3 }">
				${charger.chargerList.serialNo}
			</c:if>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerLocation"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="name" value="${charger.name}"/>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.max"/>
			</td>
			<td colspan="2" class="td02" id="viewCapacity">${charger.capacity}</td>
			</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.installer"/>
			</td>
			<td colspan="2" class="td02">${loginUser.name}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.insDate"/>
			</td>
			<td colspan="2" class="td02">
				<fmt:formatDate value="${charger.fstRgDt}" pattern="yyyy.MM.dd"/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.buildingName"/>
			</td>
			<td colspan="2" class="td02">${charger.chargerGroup.bd.bdGroup.name}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.detailName"/>
			</td>
			<td colspan="2" class="td02">${charger.chargerGroup.bd.name}</td>	
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.ownerName"/>
			</td>
			<td colspan="2" class="td02">${owner.name }</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerGroupName"/>
			</td>
			<td colspan="2" class="td02">
				<c:if test="${loginGroup.id == 2 || loginGroup.id == 3 }">
					${charger.chargerGroup.name}
				</c:if>
				<c:if test="${loginGroup.id == 1 }">
				<select id="chargerGroupId" name="chargerGroupId">
					<c:forEach items="${chargerGroupList}" var="list" varStatus="status">
						<option <c:if test="${charger.chargerGroup.chargerGroupId eq list.chargerGroupId }">selected</c:if> value="${list.chargerGroupId}">${list.name}</option>
					</c:forEach>
				</select>
				</c:if>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.status"/>
			</td>
			<td colspan="2" class="td02">
				<c:if test="${ !empty charger.status }"><fmt:message key="${charger.status}"/></c:if>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.record"/>
			</td>
			<td colspan="2" class="td02"></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
	</table>
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