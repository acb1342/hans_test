<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>

<script type="text/javascript">
	$(function () {
		if(!Boolean($('input[name="status"]:checked').val())) {
			$('#CGdiv').hide();
			$('#ALLDiv').hide();
			$('#footerDiv').hide();
		} else $('#footerDiv').show();
				
		searchChange();
		
		$('#bdGroup').click(function(e) {
			window.open("/elcg/building/popup.htm","new","width=448,height=448,top=100,left=100");
		});
		
	});
	
	function setChildValue(name, id) {
		$('#bdSelect').find("option").remove();
		$('#bdSelect').append("<option value='0'>상세/동명 선택</option>");
		document.getElementById("bdGroup").value = name;
		document.getElementById("bdGroupId").value = id;
		setBdSelect();
	}
	
	// 충전그룹별 검색
	function searchByGroup() {
		var selBdId = document.getElementById("bdSelect");
		var selCgId = document.getElementById("chargerGroupSelect");
		var selType = $('input:radio[name="status"]:checked').val();
			
		if (selType == 'CG') {
			if (selBdId.options[selBdId.selectedIndex].value == '0'
					|| selCgId.options[selCgId.selectedIndex].value == '0') {
				alert("상세/동명 및 충전그룹을 선택하세요.");
				return;
			}
		}
		
		saveSelected();
		$('#vForm').submit();
		
	}

	function searchChange() {	
		if( $('input[name="status"]:checked').val() == "CG" ) {
			$('#CGdiv').show();
			$('#ALLDiv').hide();
		} 
		else if( $('input[name="status"]:checked').val() == "ALL") {	
			$('#CGdiv').hide();
			$('#ALLDiv').show();
		}
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
	
	function setChargerGroupSelect() {
		$('#chargerGroupSelect').find("option").remove();
		$('#chargerGroupSelect').append("<option value='0'>충전그룹 선택</option>");
		$.ajax({
			type:'POST',
			url:'/elcg/building/setChargerGroupSelect.json',
			data:{
				bdSelect:$("#bdSelect").val()					
			},
			success:function (data) {
					if(data.length > 0) {
						console.log(data);
						for(var i=0; i<data.length; i++) {
							$('#chargerGroupSelect').append("<option value='" + data[i].chargerGroupId + "'>" + data[i].name + "</option>");
						}
					}
				},
			error: function(e) {
				alert("error!!!");
			}
		});
	}
	
	// 추가
	function insert() {
		document.location = "/elcg/chargerGroup/create.htm";
	}

	// 검색
	function search() {
		$("#vForm").submit();
	}

	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/elcg/chargerGroup/delete.json', function() { 
			search(); 
		});
	}
	
	// select 선택한 항목 유지
	function saveSelected() {
		var targetGroup = document.getElementById("chargerGroupSelect");
		document.getElementById("groupText").value = targetGroup.options[targetGroup.selectedIndex].text;
		var targetBd = document.getElementById("bdSelect");
		document.getElementById("bdText").value = targetBd.options[targetBd.selectedIndex].text;
	}
</script>

<form method="get" id="vForm" name="vForm" action="/elcg/chargerGroup/search.htm">
	<input type="hidden" name="id" id="id"/>
	<!-- search _ start -->
	<div class="wrap00">
	<input type="hidden" id="bdGroupId" name="bdGroupId" value="${selBdGroupId}"/>
	<input type="hidden" id="selBdId" name="selBdId" value="${bdSelect}"/>
	<c:set var="status" value="${type}" />
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
		<tr>
			<td>
				<input type="radio" id="status" name="status" value="ALL" <c:if test="${type eq 'ALL'}">checked</c:if> onclick="javascript:searchChange()"> <fmt:message key="label.common.all"/>&nbsp;&nbsp;
				<input type="radio" id="status" name="status" value="CG" <c:if test="${type eq 'CG' || type eq 'fail'}">checked</c:if> onclick="javascript:searchChange()"> <fmt:message key="label.elcg.chargerGroupName"/>
			</td>
			<td>
				<div id="ALLDiv">
					<input type="button" value='<fmt:message key="label.common.search"/>' style="margin:0 7px 0 8px;" onclick="javascript:search()" />
				</div>
				<div id="CGdiv">
					<c:set var="bdGroup" value='<%=request.getParameter("bdGroup")%>' />
					<input type="text" id="bdGroup" name="bdGroup" value="${bdGroup}" placeholder="<fmt:message key="label.elcg.buildingName"/>" />
					
					<input type="hidden" id="bdText" name="bdText"/>
					<c:set var="bdText" value='<%=request.getParameter("bdText")%>' />
					<c:set var="bdSelect" value='<%=request.getParameter("bdSelect")%>' />
					<select id="bdSelect" name="bdSelect" onchange="javascript:setChargerGroupSelect()">
						<c:choose>
							<c:when test="${bdGroup eq '' || bdGroup eq null}">
								<option value='0'>상세/동명 선택</option>
							</c:when>
							<c:otherwise>
								<c:if test="${ fn:length(selBdList) > 0 }">
									<c:forEach items="${selBdList}" var="selBd" varStatus="status">
										<option <c:if test="${selBd.bdId == bdSelect}">selected</c:if> value="${selBd.bdId}">${selBd.name}</option>
									</c:forEach>
								</c:if>
							</c:otherwise>
						</c:choose>
					</select>
				
					<input type="hidden" id="groupText" name="groupText"/>
					<c:set var="groupText" value='<%=request.getParameter("groupText")%>' />
					<c:set var="chargerGroupSelect" value='<%=request.getParameter("chargerGroupSelect")%>' />
					<select id="chargerGroupSelect" name="chargerGroupSelect">
						<c:choose>
							<c:when test="${bdSelect eq '' || bdSelect eq null}">
								<option value='0'>충전그룹 선택</option>
							</c:when>
							<c:otherwise>
								<c:if test="${ fn:length(selCgList) > 0 }">
									<c:forEach items="${selCgList}" var="selCg" varStatus="status">
										<option <c:if test="${selCg.chargerGroupId == chargerGroupSelect}">selected</c:if> value="${selCg.chargerGroupId}">${selCg.name}</option>
									</c:forEach>
								</c:if>
							</c:otherwise>
						</c:choose>
					</select>
					
					<input type="button" id="searchGroup" value="조회" onclick="javascript:searchByGroup()" />
				</div>
			</td>
		</tr>
		</table>
	</fieldset><br/>
	<!-- search _ end -->
	
	<!-- list _ start -->
	<c:choose>
		<c:when test="${ type eq 'default' }">
			<div style="text-align:center">
			<br/><h4><fmt:message key="label.elcg.chargerGroup.default"/></h4>
			</div>
		</c:when>
		<c:when test="${ type eq 'ALL' || type eq 'CG' }">
			<c:set var="row" value="${ rownum-((page-1)*10) }"/>
			<display:table name="chargerGroupList" id="chargerGroup" class="simple" style="margin:5px 0pt;" requestURI="/elcg/chargerGroup/search.htm" pagesize="10" export="false">
				<c:if test="${authority.delete}">
					<display:column titleKey="label.common.select" style="width:40px;" media="html">
						<input type="checkbox" id="selected" name="selected" style="width:15px;" value="${chargerGroup.chargerGroupId}"/>
					</display:column>
				</c:if>
				<display:column titleKey="label.common.seq" style="width:40px;">
					<c:out value="${row}"/>
					<c:set var="row" value="${row-1}"/>
				</display:column>
				<display:column titleKey="label.common.regDate" media="html" style="width:40px;">
					<fmt:formatDate value="${chargerGroup.fstRgDt}" pattern="yyyy.MM.dd" />
				</display:column>
				<display:column titleKey="label.elcg.buildingName">
					<span style="display:inline-block; width:150px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;">${chargerGroup.bd.bdGroup.name}</span>
				</display:column>
				<display:column titleKey="label.elcg.detailName" style="width:70px;" property="bd.name"/>
				<display:column titleKey="label.elcg.chargerGroupName" style="width:100px;" property="name"/>
				<display:column titleKey="label.elcg.chargerCnt" style="width:70px;" property="chargerSize"/>
				<display:column titleKey="label.elcg.lstInsDate" media="html" style="width:80px;">
					<fmt:formatDate value="${chargerGroup.fstRgDt}" pattern="yyyy.MM.dd" />
				</display:column>
				<display:column titleKey="label.common.detail" style="text-align:center; width:100px;" media="html">
					<input type="button" value='<fmt:message key="label.elcg.detail"/>' onclick="location.href='/elcg/chargerGroup/detail.htm?seq=${chargerGroup.chargerGroupId}'"/>
				<c:if test="${authority.update && loginGroup.id != 3}">
					<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/elcg/chargerGroup/update.htm?seq=${chargerGroup.chargerGroupId}'"/>
				</c:if>
				</display:column>
			</display:table>
		</c:when>
	</c:choose>
	<!-- list _ end -->
	
	<!-- button _ start -->
	<div class="footer" id="footerDiv">
		<table style="width:100%">
			<tr>
				<td height="30" align="right">
				<c:if test="${authority.create}">
					<input type="button" value='<fmt:message key="label.common.add"/>' onclick="javascript:insert()"/>
				</c:if>
				<c:if test="${authority.delete}">
					<input type="button" class="btn_red" value='<fmt:message key="label.common.delete"/>' onclick="javascript:confirmAndDelete()"/>
				</c:if>
				</td>
			</tr>
		</table>
	</div>
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>