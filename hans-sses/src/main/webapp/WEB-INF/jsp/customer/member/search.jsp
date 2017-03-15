<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 검색 조건에 따라 검색 UI 리셋
		searchChange();
	});

	// 생성 페이지로 이동
	function insert() {
		document.location = "/customer/member/create.htm";
	}

	// 검색
	function search() {
		if ($("#searchValue").val() == "") {
			alert("검색어를 먼저 입력하세요.");
			return;
		} else if ($("#searchValue").val().length < 2) {
			alert("검색어는 2자 이상 입력하셔야 합니다.");
			return;
		}
		$("#vForm").submit();
	}

	// 검색 조건에 따라 검색 UI 리셋
	function searchChange() {
		if ($("#searchType").val() == 'group') {
			$("#searchValue").hide();
			$("#searchSelect").show();
		} else {
			$("#searchValue").show();
			$("#searchSelect").hide();
		}
	}

	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/customer/member/delete.json', function() { 
			search(); 
		});
	}
</script>
<form method="get" id="vForm" name="vForm" action="/customer/member/search.htm">
	<!-- search _ start -->
	<input type="hidden" name="id" id="id"/>
	<div class="wrap00">
	<!-- search _ start -->
	<c:set var="searchOptionKeys">name,id</c:set>
	<c:set var="searchOptionValues">
		<fmt:message key="label.cmsUser.name"/>,
		<fmt:message key="label.cmsUser.id"/>
	</c:set>
	<c:set var="searchType" value='<%=request.getParameter("searchType")%>'/>
	<c:set var="searchValue" value='<%=request.getParameter("searchValue")%>'/>
	<c:set var="searchSelect" value='<%=request.getParameter("searchSelect")%>'/>
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<td class="td00">
					<select name="searchType" id="searchType" onchange="searchChange()">
					<c:forTokens var="key" items="${searchOptionKeys}" delims="," varStatus="statKey">
						<c:forTokens var="val" items="${searchOptionValues}" delims="," varStatus="statVal">
							<c:if test="${statKey.index == statVal.index}">
								<option value="${key}" ${key == searchType ? 'selected':''}>${val}</option>
							</c:if>
						</c:forTokens>
					</c:forTokens>
					</select>
				</td>
				<td align="left">
					<input type="text" name="searchValue" id="searchValue" value="${searchValue}" style="margin:0 7px 0 8px;"/>
					<select name="searchSelect" id="searchSelect" style="width:150px;">
						<c:forEach items="${groupList}" var="groupList">
							<option value="${groupList.id}" ${groupList.id == searchSelect? 'selected':''}>${groupList.name}</option>
						</c:forEach>
					</select>
					<input type="button" value='<fmt:message key="label.common.search"/>' onclick="javascript:search()"/>
				</td>
			</tr>
		</table>
	</fieldset>
	<!-- search _ end -->

	<!-- list _ start -->
	<c:set var="row" value="${ totalCount-((page-1)*10) }"/>
	<display:table name="members" id="member" class="simple" style="margin:5px 0pt;" requestURI="/customer/member/search.htm" pagesize="10" export="false">
		<display:column titleKey="label.customer.no" style="width:40px;" media="html">
			<c:out value="${row}"/>
			<c:set var="row" value="${row-1}"/>	
		</display:column>
		<display:column titleKey="label.customer.user.name" property="name"/>
		<display:column titleKey="label.cmsUser.id" property="subsId"/>
		<display:column titleKey="label.customer.mobile" property="mdn"/>
		<display:column titleKey="label.customer.udt">
			<fmt:parseDate var="strDate" value="${member.fstRgDt}" pattern="yyyy-MM-dd" />
			<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
		</display:column>
		<display:column titleKey="label.customer.status" media="html">
		<c:choose>
			<c:when test="${member.status == '301101'}">준회원</c:when>
			<c:when test="${member.status == '301102'}">정회원</c:when>
			<c:when test="${member.status == '301103'}">중지(탈퇴)</c:when>
			<c:otherwise>-</c:otherwise>
		</c:choose>
		</display:column>
		<display:column titleKey="label.customer.close.udt">
			<fmt:parseDate var="strDate" value="${member.close.fstRgDt}" pattern="yyyy-MM-dd" />
			<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
		</display:column>
		
		<display:column titleKey="label.common.detail" style="text-align:center; width:140px;" media="html">
			<input type="button" style="width:65px !important;" value='<fmt:message key="label.customer.detail"/>' onclick="location.href='/customer/member/detail.htm?id=${member.id}'"/>
		</display:column>
	</display:table>
	<!-- list _ end -->
	
</div>
</form>
<%@ include file="/include/footer.jspf" %>