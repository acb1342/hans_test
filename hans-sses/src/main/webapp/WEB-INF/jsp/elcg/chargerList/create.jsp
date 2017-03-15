<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 저장
		$('#save').click(function(e) {
			if(confirm("입력하신 내용으로 등록하시겠습니까?")) {
				$("#vForm").submit();
				alert("입력하신 내용으로 등록되었습니다.")
			}
			else {
				return;
			}
			
		});
		
		// 이전 페이지로 이동
		$('#cancel').click(function(e) {
			//if(confirm("정말 취소하시겠습니까?")) window.location.href = "/elcg/chargerGroup/search.htm";
			history.go(-1);
		});
	});
</script>
<spring:hasBindErrors name="chargerList"/>
<form method="post" id="vForm" name="vForm" action="/elcg/chargerList/create.htm" >
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerId"/>
			</td>
			<td colspan="2" class="td02"></td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.sn"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" />
				<input type="button" value="유효성 확인" />
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerLocation"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" />
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				최대전류량(A)
			</td>
			<td colspan="2" class="td02"></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.installer"/>
			</td>
			<td colspan="2" class="td02">
				<select></select>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.insDate"/>
			</td>
			<td colspan="2" class="td02"></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.buildingName"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" />
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.detailName"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" />
			</td>	
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.ownerName"/>
			</td>
			<td colspan="2" class="td02"></td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerGroupName"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" />
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.status"/>
			</td>
			<td colspan="2" class="td02"></td>
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
	<div class="footer">
		<table style="width:100%">
			<tr>
				<td width="50%" height="30"></td>
				<td width="50%" align="right">	
				<c:if test="${authority.update}">
					<input type="button" id="save" value='<fmt:message key="label.common.save"/>'/>
				</c:if>
				<c:if test="${authority.delete}">
					<input type="button" id="cancel" value='<fmt:message key="label.common.cancel"/>'/>
				</c:if>	
				</td>
			</tr>
		</table>
	</div>				
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>