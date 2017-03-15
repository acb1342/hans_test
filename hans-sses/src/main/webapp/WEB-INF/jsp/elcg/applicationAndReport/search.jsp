<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<style type="text/css" media="all">
	@importA url("/js/jquery/ui/jquery-ui-timepicker-addon.css");
</style>
<script type="text/javascript" src="/js/jquery/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript">
	$(function () {
		$('#bdGroup').click(function(e) {
			window.open("/elcg/building/popup.htm","new","width=448,height=448,top=100,left=100");
		});
	});

	function search() {
		var groupType = ${loginGroup.id};
		if ( groupType == 1 || groupType == 2) saveSelected();
		
		$('#vForm').submit();
	}
	
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
		document.location = "/elcg/applicationAndReport/create.htm";
	}

	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/elcg/applicationAndReport/delete.json', function() { 
			search(); 
		});
	}
	
	function saveSelected() {
		// 상세/동명 검색값 유지
		var targetBd = document.getElementById("bdSelect");
		document.getElementById("bdText").value = targetBd.options[targetBd.selectedIndex].text;
	}
</script>

<form method="get" id="vForm" name="vForm" action="/elcg/applicationAndReport/search.htm">
	<!-- search _ start -->
	<input type="hidden" name="id" id="id"/>
	<input type="hidden" id="bdGroupId" name="bdGroupId" value="${selBdGroupId}"/>
	<div class="wrap00">
	
	<!-- search _ start -->
	<c:if test="${loginGroup.id == 1 || loginGroup.id == 2}">
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<td>
				<c:set var="bdGroup" value='<%=request.getParameter("bdGroup")%>' />
				<input type="text" id="bdGroup" name="bdGroup" value="${bdGroup}" placeholder="<fmt:message key="label.elcg.buildingName"/>" />
				<input type="hidden" id="bdText" name="bdText"/>
				<c:set var="bdText" value='<%=request.getParameter("bdText")%>' />
				<c:set var="bdSelect" value='<%=request.getParameter("bdSelect")%>' />
				<select id="bdSelect" name="bdSelect">
					<c:choose>
						<c:when test="${bdGroup eq '' || bdGroup eq null}">
							<option value="0">상세/동명 선택</option>
						</c:when>
						<c:otherwise>
							<c:if test="${ fn:length(bdList) > 0 }">
								<c:forEach items="${bdList}" var="bd" varStatus="status">
									<option <c:if test="${bd.bdId == bdSelect}">selected</c:if> value="${bd.bdId}">${bd.name}</option>
								</c:forEach>
							</c:if>
						</c:otherwise>
					</c:choose>
				</select>
				
				<c:set var="content" value='<%=request.getParameter("content")%>' />
				<input type="text" name="content" value="${content}" placeholder="내용"/>
				
				<input type="button" id="searchAllType" value="조회" onclick="javascript:search()"/>
			</td>		
			</tr>
		</table>
	</fieldset>
	</c:if>
	
		<div style="float:right; margin:5px 0px 5px 0px">
			<b>분류</b>
			<c:set var="searchTypeKeys">0,1,2</c:set>
			<c:set var="searchTypeValues">전체,설치신청,고장신고</c:set>
			<c:set var="searchType" value='<%=request.getParameter("searchType")%>'/>
			<select name="searchType" id="searchType" onchange="javascript:search()">
				<c:forTokens var="key" items="${searchTypeKeys}" delims="," varStatus="statKey">
					<c:forTokens var="val" items="${searchTypeValues}" delims="," varStatus="statVal">
						<c:if test="${statKey.index == statVal.index}">
							<option value="${key}" ${key == searchType ? 'selected':''}>${val}</option>
						</c:if>
					</c:forTokens>
				</c:forTokens>
			</select>
		</div>
	<!-- search _ end -->
	
	<!-- list _ start -->

	<c:set var="row" value="${ rownum-((page-1)*10) }"/>
	<display:table name="viewList" id="list" class="simple" style="margin:5px 0pt;" requestURI="/elcg/applicationAndReport/search.htm" pagesize="10" export="false">
	<c:if test="${authority.delete}">
	<display:column titleKey="label.common.select" style="width:40px;" media="html">
		<input type="checkbox" id="selected" name="selected" style="width:15px;" value="${list.wkType}_${list.snId}"/>
	</display:column>
	</c:if>
	<display:column titleKey="label.common.seq" >
		<c:out value="${row}"/>
		<c:set var="row" value="${row-1}"/>
	</display:column>
	<display:column titleKey="label.cmsMenu.function.type"><fmt:message key="${list.wkType}"/></display:column>
	<display:column titleKey="label.contentEditor.content" style="width:300px;">
		<span style="display:inline-block; width:250px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;">${list.body}</span>
	</display:column>
	<c:set var="stat" value="${list.status}" />
	<display:column titleKey="label.elcg.buildingName" property="bdGroupName"/>
	<display:column titleKey="label.elcg.detailName" property="bdName"/>
	<display:column titleKey="label.elcg.installer" style="width:100px">
		<span style="display:inline-block; width:100px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;">${list.wkName}</span>
	</display:column>
	<display:column titleKey="label.elcg.update"><fmt:formatDate value="${list.lstChDt}" pattern="yyyy.MM.dd"/></display:column>
	<display:column titleKey="label.common.regDate"><fmt:formatDate value="${list.fstRgDt}" pattern="yyyy.MM.dd"/></display:column>
	<display:column titleKey="label.elcg.status"><fmt:message key="${list.status}"/></display:column>
	<display:column titleKey="label.boad.detail" style="text-align:center;" media="html">
		<input type="button" value='<fmt:message key="label.boad.detail"/>' onclick="location.href='/elcg/applicationAndReport/detail.htm?id=${agent.id}&seq=${list.snId}&wkType=${list.wkType}'"/>
	<c:if test="${authority.update}">
		<c:if test="${loginGroup.id != 3}">
			<input type="button" <c:if test="${loginGroup.id == 2 }">disabled</c:if> value='<fmt:message key="label.common.modify"/>' onclick="location.href='/elcg/applicationAndReport/update.htm?id=${agent.id}&seq=${list.snId}&wkType=${list.wkType}'"/>
		</c:if>
		<c:if test="${loginGroup.id == 3 && loginUser.id eq list.rcUsid}">
			<input type="button" <c:if test="${list.status eq '409103' || list.status eq '407103' }">disabled</c:if> value='<fmt:message key="label.common.modify"/>' onclick="location.href='/elcg/applicationAndReport/update.htm?id=${agent.id}&seq=${list.snId}&wkType=${list.wkType}'"/>
		</c:if>
	</c:if>
	</display:column>
	</display:table>
	
	<!-- list _ end -->
	
	<!-- button _ start -->
	<div class="footer">
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