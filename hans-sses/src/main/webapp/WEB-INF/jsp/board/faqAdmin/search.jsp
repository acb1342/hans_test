<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>

<script type="text/javascript">

	$(function () {
		
	});
	
	// 생성 페이지로 이동
	function insert() {
		document.location = "/board/faqAdmin/create.htm";
	}

	// 검색
	function search() {
		$("#vForm").submit();
	}

	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/board/faqAdmin/delete.json', function() { 
			search(); 
		});
	}
	
</script>
<form method="get" id="vForm" name="vForm" action="/board/faqAdmin/search.htm">
	<div class="wrap00">
	<!-- search _ start -->
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<td align="left">
					<c:set var="searchValue" value='<%=request.getParameter("searchValue")%>'/>
					<b>제목검색</b>&nbsp;<input type="text" name="searchValue" id="searchValue" value="${searchValue}" style="margin:0 30px 0 7px;"/>
					<b>카테고리</b>
					<c:set var="categoryKeys">0,1,2,3,4,5</c:set>
					<c:set var="categoryValues">전체,건물/그룹,충전기정보,설치신청,고장신고,기타</c:set>
					<c:set var="category" value='<%=request.getParameter("category")%>'/>
					
					<select name="category" id="category" style="width:100px">
						<c:forTokens var="key" items="${categoryKeys}" delims="," varStatus="statKey">
							<c:forTokens var="val" items="${categoryValues}" delims="," varStatus="statVal">
								<c:if test="${statKey.index == statVal.index}">
									<option value="${key}" ${key == category ? 'selected':''}>${val}</option>
								</c:if>
							</c:forTokens>
						</c:forTokens>
					</select>
					
					<input type="button" value='<fmt:message key="label.common.search"/>' onclick="javascript:search()"/>
				</td>
			</tr>
		</table>
	</fieldset><br/>
	
	<!-- search _ end -->
	
	<!-- list _ start -->
	<c:set var="row" value="${ rownum-((page-1)*10) }"/>
	<display:table name="boardList" id="boadFaq" class="simple" style="margin:5px 0pt;" requestURI="/board/faqAdmin/search.htm" pagesize="10" export="false">
		<c:if test="${authority.delete}">
			<display:column titleKey="label.common.select" media="html">
				<input type="checkbox" id="selected" name="selected" style="width:15px;" value="${boadFaq.snId}"/>
			</display:column>
		</c:if>
		<display:column title="순서" style="width:40px">
			<c:out value="${row}"/>
			<c:set var="row" value="${row-1}"/>	
		</display:column>
		<display:column title="카테고리" style="width:100px"><fmt:message key="${boadFaq.category}"/></display:column>
		<display:column titleKey="label.contentEditor.title" style="width:300px">
			<span style="display:inline-block; width:300px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis; text-align:left;">${boadFaq.question}</span>
		</display:column>
		<display:column titleKey="label.common.regDate" media="html">
			<fmt:formatDate value="${boadFaq.fstRgDt}" pattern="yyyy/MM/dd"/>
		</display:column>
		<c:if test="${loginGroup.id == 1 }">
			<display:column title="노출여부">
				<c:if test="${'Y' eq boadFaq.displayYn}">공개</c:if>
				<c:if test="${'N' eq boadFaq.displayYn}">비공개</c:if>
			</display:column>
		</c:if>
		<display:column titleKey="label.common.detail" style="text-align:center; width:100px;" media="html">
			<input type="button" value='<fmt:message key="label.boad.detail"/>' onclick="location.href='/board/faqAdmin/detail.htm?id=${boadFaq.snId}'"/>
		<c:if test="${authority.update}">
			<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/board/faqAdmin/update.htm?id=${boadFaq.snId}'"/>
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