<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>

<script type="text/javascript">
	$(function () {
				
		if(!Boolean($('input[name="status"]:checked').val())) {
			$('#ALLDiv').hide();
			$('#BDDiv').hide();	
			$('#footerDiv').hide();
		}
		else $('#footerDiv').show();
		searchChange();
		
		var group = ${loginGroup.id};
		var isInst = '';
		if (group == 2) isInst = "inst";

		$('#bdGroup').click(function(e) {
			window.open("/elcg/building/popup.htm?isInst="+isInst,"new","width=448,height=448,top=100,left=100");
			
		});
		
	});
	
	function searchChange() {	
		if( $('input[name="status"]:checked').val() == "ALL" ) {
			$('#ALLDiv').show();
			$('#BDDiv').hide();
		} 
		else if( $('input[name="status"]:checked').val() == "BD") {	
			$('#ALLDiv').hide();
			$('#BDDiv').show();
		}
	}
	
	function setChildValue(name, id) {
		$('#bdSelect').find("option").remove();
		$('#bdSelect').append("<option value='0'>상세/동명</option>");
		document.getElementById("bdGroup").value = name;
		document.getElementById("bdGroupId").value = id;
		setBdSelect();
	}
	
	function setBdSelect() {
		var group = ${loginGroup.id};
		var isInst = '';
		if (group == 2) isInst = "inst";

		$.ajax({
			type:'POST',
			url:'/elcg/building/setBdSelect.json',
			data:{
				bdGroupId:$("#bdGroupId").val(),
				isInst:isInst
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
	
	// 생성 페이지로 이동
	function insert() {
		document.location = "/elcg/building/create.htm";
	}

	// 검색
	function search() {
		var selBdId = document.getElementById("bdSelect");
		var selType = $('input:radio[name="status"]:checked').val();
			
		if (selType == 'BD' && selBdId.options[selBdId.selectedIndex].value == '0') {
			alert("상세/동명을 선택하세요.");
			return;
		}
				
		saveSelected();
		
		var group = ${loginGroup.id};
		if (group == 2) $('#isInst').val("isInst");
		
		$("#vForm").submit();
	}

	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/elcg/building/delete.json', function() { 
			search(); 
		});
	}
	
	// select 선택한 항목 유지
	function saveSelected() {
		var targetBd = document.getElementById("bdSelect");
		document.getElementById("bdText").value = targetBd.options[targetBd.selectedIndex].text;
	}
</script>

<form method="get" id="vForm" name="vForm" action="/elcg/building/search.htm">
	<div class="wrap00">
	<!-- search _ start -->
	<input type="hidden" id="bdGroupId" name="bdGroupId" value="${selBdGroupId}"/>
	<input type="hidden" id="isInst" name="isInst" value="${isInst}"/>
	<c:set var="status" value="${type}" />
	<c:set var="searchValue" value='<%=request.getParameter("searchValue")%>'/>
	
	<c:if test="${loginGroup.id != 3}">
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
		<tr>
			<td id="tdRadio" style="width: 100px">
				<c:if test="${loginGroup.id == 1}">
				<input type="radio" id="status" name="status" value="ALL" <c:if test="${type eq 'ALL'}">checked</c:if> onclick="javascript:searchChange()">전체&nbsp;
				</c:if>
				<input type="radio" id="status" name="status" value="BD" <c:if test="${type eq 'BD' || type eq 'fail'}">checked</c:if> onclick="javascript:searchChange()"> 건물별
			</td>
			<td>
				<div id="ALLDiv">
					<input type="button" value='<fmt:message key="label.common.search"/>' style="margin:0 7px 0 8px;" onclick="javascript:search()" />
				</div>
				<div id="BDDiv">
					<c:set var="bdGroup" value='<%=request.getParameter("bdGroup")%>'/>
					<input type="text" name="bdGroup" id="bdGroup" value="${bdGroup}" style="margin:0 7px 0 8px;" placeholder="<fmt:message key="label.elcg.buildingName"/>" />
					
					<input type="hidden" id="bdText" name="bdText"/>
					<c:set var="bdText" value='<%=request.getParameter("bdText")%>' />
					<c:set var="bdSelect" value='<%=request.getParameter("bdSelect")%>' />
					<select id="bdSelect" name="bdSelect">
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
					<input type="button" value='<fmt:message key="label.common.search"/>' onclick="javascript:search()" />
				</div>
			</td>
		</tr>
		</table>
	</fieldset><br/>
	</c:if>
	<!-- search _ end -->
	
	<!-- list _ start -->
	<table style="width:100%">
	<c:choose>
		<c:when test="${ 'default' eq type }">
			<tr class="line-top"><td colspan="4"/></tr>
			<tr>
				<td style="width:20%" height="25" class="td01"><fmt:message key="label.elcg.bdCountByCharger"/></td>
				<td style="width:30%" class="td02">${totalBdCnt}</td>
				<td style="width:20%" height="25" class="td01"><fmt:message key="label.elcg.installedChargerCount"/></td>
				<td style="width:30%" class="td02">${chargerCount}</td>
			</tr>
		</c:when>
		<c:otherwise>
			<c:set var="row" value="${ rownum-((page-1)*10) }"/>
			<display:table name="bdList" id="bd" class="simple" style="margin:5px 0pt;" requestURI="/elcg/building/search.htm" pagesize="10" export="false">
				<c:if test="${authority.delete}">
					<display:column titleKey="label.common.select" style="width:40px;" media="html">
						<input type="checkbox" id="selected" name="selected" style="width:15px;" value="${bd.bdId}"/>
					</display:column>
				</c:if>
				<display:column titleKey="label.common.seq">
					<c:out value="${row}"/>
					<c:set var="row" value="${row-1}"/>
				</display:column>
				<display:column titleKey="label.common.regDate" media="html" style="width:40px;">
					<fmt:formatDate value="${bd.fstRgDt}" pattern="yyyy.MM.dd" />
				</display:column>
				<display:column titleKey="label.elcg.buildingName" style="width:300px;">
					<span style="display:inline-block; width:300px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;">${bd.bdGroup.name}</span>
				</display:column>
				<display:column titleKey="label.elcg.detailName" style="width:150px;" property="name"/>
				<display:column titleKey="label.elcg.chargerGroupCnt" property="chargerGroupSize"/>
				<display:column titleKey="label.elcg.chargerCnt" property="chargerSize"/>
				<display:column titleKey="label.elcg.insDate" style="width:100px;">
					<fmt:parseDate var="strDate" value="${bd.lstInsDate}" pattern="yyyyMMddHHmm" />
					<fmt:formatDate value="${strDate}" pattern="yyyy.MM.dd HH:mm"/>
				</display:column>
				<display:column titleKey="label.elcg.detail" style="text-align:center; width:100px;" media="html">
					<input type="button" value='<fmt:message key="label.elcg.detail"/>' onclick="location.href='/elcg/building/detail.htm?id=${agent.id}&seq=${bd.bdId}'"/>
				<c:if test="${authority.update}">
					<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/elcg/building/update.htm?id=${agent.id}&seq=${bd.bdId}'"/>
				</c:if>
				</display:column>	
			</display:table>
		</c:otherwise>
	</c:choose>
	</table>
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