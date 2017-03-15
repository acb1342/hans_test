<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>

<script type="text/javascript">

	$(function () {
		var custType = ${loginGroup.id};
		if (custType == 2 || custType == 3) $('#spanOpenTarget').hide();
		
	});
	
	// 생성 페이지로 이동
	function insert() {
		document.location = "/board/FAQ/create.htm";
	}

	// 검색
	function search() {
		var groups = getCheckedIds();
		$('#groups').val(groups);
		
		$("#vForm").submit();
	}

	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/board/FAQ/delete.json', function() { 
			search(); 
		});
	}
	
	function getCheckedIds() {
		var ids = [];
		
		$("input:checkbox:checked").each(function() {
			ids.push($(this).val());
		});
		
		return ids.join(";");
	}
</script>
<form method="get" id="vForm" name="vForm" action="/board/FAQ/search.htm">
	<!-- search _ start -->
	<input type="hidden" name="id" id="id"/>
	<input type="hidden" id="groups" name="groups" />
	<div class="wrap00">
	<!-- search _ start -->
	
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<td align="left">
					<c:set var="searchValue" value='<%=request.getParameter("searchValue")%>'/>
					<b>제목검색</b>&nbsp;<input type="text" name="searchValue" id="searchValue" value="${searchValue}" style="margin:0 30px 0 7px;"/>
					<span id="spanOpenTarget" style="margin: 0px 10px 0px 0px;">
					<b>노출대상</b>&nbsp;
					<input type="checkbox" <c:if test="${'Y' eq custYn }">checked</c:if> id="selected" name="selected" value="0" style="margin:0 4px 0 4px;"/>사용자
					<input type="checkbox" <c:if test="${'Y' eq ownerYn }">checked</c:if> id="selected" name="selected" value="1" style="margin:0 4px 0 4px;"/>건물주
					<input type="checkbox" <c:if test="${'Y' eq instYn }">checked</c:if> id="selected" name="selected" value="2" style="margin:0 4px 0 4px;"/>설치자
					</span>
					<b>카테고리</b>
					<c:set var="categoryKeys">0,1,2,3,4,5</c:set>
					<c:set var="categoryValues">전체,회원가입,회원인증,충전,요금 및 결제,기타</c:set>
					<c:set var="category" value='<%=request.getParameter("category")%>'/>
					
					<select name="category" id="category">
						<c:forTokens var="key" items="${categoryKeys}" delims="," varStatus="statKey">
							<c:forTokens var="val" items="${categoryValues}" delims="," varStatus="statVal">
								<c:if test="${statKey.index == statVal.index}">
									<option value="${key}" ${key == category ? 'selected':''}>${val}</option>
								</c:if>
							</c:forTokens>
						</c:forTokens>
					</select>
					
					<input style="margin: 0px 0px 0px 0px" type="button" value='<fmt:message key="label.common.search"/>' onclick="javascript:search()"/>
				</td>
			</tr>
		</table>
	</fieldset><br/>
	
	<!-- search _ end -->
	
	<!-- list _ start -->
	<c:set var="row" value="${ rownum-((page-1)*10) }"/>
	<display:table name="boardList" id="boadFaq" class="simple" style="margin:5px 0pt;" requestURI="/board/FAQ/search.htm" pagesize="10" export="false">
		<c:if test="${authority.delete}">
			<display:column titleKey="label.common.select" media="html">
				<input type="checkbox" id="selected" name="selected" style="width:15px;" value="${boadFaq.sn_id}"/>
			</display:column>
		</c:if>
		<display:column title="순서" style="width:40px">
			<c:out value="${row}"/>
			<c:set var="row" value="${row-1}"/>	
		</display:column>
		<display:column title="카테고리" style="width:100px">
			<c:choose>	
			<c:when test="${boadFaq.category eq '601100' || boadFaq.category eq '0' || boadFaq.category eq ''}" >전체</c:when>
			<c:otherwise><fmt:message key="${boadFaq.category}"/></c:otherwise>
			</c:choose>
		</display:column>
		<display:column titleKey="label.contentEditor.title" >
			<span style="display:inline-block; width:300px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis; text-align:left;">${boadFaq.question}</span>
		</display:column>
		<display:column titleKey="label.common.regDate" media="html">
			<fmt:formatDate value="${boadFaq.fstRgDt}" pattern="yyyy/MM/dd"/>
		</display:column>
		<c:if test="${loginGroup.id == 1 }">
			<display:column title="노출대상">
				<c:choose>
				<c:when test="${'Y' eq boadFaq.cust_yn && 'Y' eq boadFaq.owner_yn && 'Y' eq boadFaq.installer_yn }">전체
				</c:when>
				<c:otherwise>
					<c:if test="${'Y' eq boadFaq.cust_yn }">사용자</c:if>
					<c:if test="${'Y' eq boadFaq.owner_yn }">건물주</c:if>
					<c:if test="${'Y' eq boadFaq.installer_yn }">설치자</c:if>
					<c:if test="${'Y' eq boadFaq.counselor_yn }">상담사</c:if>
				</c:otherwise>
				</c:choose>
			</display:column>
			<display:column title="노출여부">
				<c:if test="${'Y' eq boadFaq.disply_yn}">공개</c:if>
				<c:if test="${'N' eq boadFaq.disply_yn}">비공개</c:if>
			</display:column>
		</c:if>
		<display:column titleKey="label.common.detail" style="text-align:center; width:140px;" media="html">
			<input type="button" value='<fmt:message key="label.common.detail"/>' onclick="location.href='/board/FAQ/detail.htm?id=${agent.id}&seq=${boadFaq.sn_id}'"/>
		<c:if test="${authority.update}">
			<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/board/FAQ/update.htm?id=${agent.id}&seq=${boadFaq.sn_id}'"/>
		</c:if>
		</display:column>
	</display:table>
	
	<!-- list _ end -->
	
	<!-- button _ start -->
	<div class="footer">
		<table style="width:100%">
			<tr class="td02">
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