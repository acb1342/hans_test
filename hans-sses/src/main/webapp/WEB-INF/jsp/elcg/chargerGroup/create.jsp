<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		
		$('#bdGroup').click(function(e) {
			window.open("/elcg/building/popup.htm","new","width=448,height=448,top=100,left=100");
		});
		
		// 저장
		$('#save').click(function(e) {
			if(!validationCheck()) return; 
			
			if(confirm("입력하신 내용으로 충전그룹정보를 등록하시겠습니까?")) {
				$("#vForm").submit();
				alert("입력하신 내용으로 충전그룹정보가 등록되었습니다.");
			}
			else {
				return;
			}
			
		});
		
		// 이전 페이지로 이동
		$('#cancel').click(function(e) {
			history.go(-1);
		});
	});
	
	function setChildValue(name, id) {
		$('#bdSelect').find("option").remove();
		$('#bdSelect').append("<option>상세/동명 선택</option>");
		document.getElementById("bdGroup").value = name;
		document.getElementById("bdGroupId").value = id;
		setBdSelect();
	}
	
	function setBdSelect() {
		$.ajax({
			type:'POST',
			url:'/elcg/building/setBdSelect.json',
			data:{
				bdGroupId:$("#bdGroupId").val()				
			},
			success:function (data) {
					if(data.length > 0) {
						$('#bdSelect').find("option").remove();
						$('#bdSelect').append("<option>상세/동명 선택</option>");
						for(var i=0; i<data.length; i++) {
							$('#bdSelect').append("<option value='" + data[i].bdId + "'>" + data[i].name + "</option>");
						}
					}
				},
			error: function(e) {
				alert("error!!!");
			}
		});
	}
	
	function validationCheck() {
		if( $('#chargerGroupName').val().trim() == '' ) {
			alert("충전그룹명을 입력하세요.");
			return;
		}
		
		if( !$('#capacity').val().match(/^\d+$/ig) ) {
			alert("최대전류량은 숫자만 입력 가능합니다.");
			return ;
		}
		
		if( $('#capacity').val().trim() == '' ) {
			alert("최대전류량을 입력하세요.");
			return;
		}
		
		if( $('#description').val().trim() == '' ) {
			alert("설명을 입력하세요.");
			return;
		}
		
		if( $('#bdSelect option').index($('#bdSelect option:selected')) == 0 ) {
			alert("상세/동명을 선택하세요.");
			return;
		}
		
		return true;
	}
	
</script>
<spring:hasBindErrors name="chargerGroup"/>
<form method="post" id="vForm" name="vForm" action="/elcg/chargerGroup/create.htm" >
<div class="wrap00">
	<input type="hidden" id="bdGroupId" name="bdGroupId" />
	<!-- input _ start -->
	<c:set var="chargerGroupName" value='<%=request.getParameter("name")%>'/>
	<c:set var="capacity" value='<%=request.getParameter("capacity")%>'/>
	<c:set var="description" value='<%=request.getParameter("description")%>'/>
	<table style="width:100%">
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerGroupName"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="chargerGroupName" name="name" value="${chargerGroupName}"/>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.max"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="capacity" name="capacity" value="${capacity}"/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.buildingName"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="bdGroup" name="bdGroup" placeholder="<fmt:message key="label.elcg.buildingName"/>" />
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.detailName"/>
			</td>
			<td colspan="2" class="td02">
				<select id="bdSelect" name="bdSelect">
					<c:choose>
						<c:when test="${bdGroup eq '' || bdGroup eq null}">
							<option>상세/동명 선택</option>
						</c:when>
						<c:otherwise>
							<option value="${bdSelect}">${bdSelect}</option>
						</c:otherwise>
					</c:choose>
				</select>
			</td>	
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerGroupDescription"/>
			</td>
			<td colspan="2" class="td02">
				<textarea id="description" name="description">${description}</textarea>
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.elcg.chargerCnt"/>
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
				<td width="100%" align="right">	
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