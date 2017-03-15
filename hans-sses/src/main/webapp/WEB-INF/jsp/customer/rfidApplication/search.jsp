<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 검색 조건에 따라 검색 UI 리셋
		searchChange();
	});

	// 생성 페이지로 이동
	function insert() {
		document.location = "/customer/rfidApplication/create.htm";
	}

	// 검색
	function search() {
		$("#vForm").submit();
	}

	// 검색 조건에 따라 검색 UI 리셋
	function searchChange() {
		
		var searchType = $('input:radio[name="searchType"]:checked').val();
		
		if (searchType == "name") {
			$("#searchValue").show();
			$("#searchSelect").hide();
			$("#searchButton").show();
		} else if (searchType == "status") { 
			$("#searchValue").hide();
			$("#searchSelect").show();
			$("#searchButton").show();
		} else {
			$("#searchValue").hide();
			$("#searchSelect").hide();
			$("#searchButton").hide();
		}
	}

	// 삭제
	function confirmAndDelete() {
		deleteByChecked('/customer/rfidApplication/delete.json', function() { 
			search(); 
		});
	}
</script>
<form method="get" id="vForm" name="vForm" action="/customer/rfidApplication/search.htm">
	<!-- search _ start -->
	<input type="hidden" name="id" id="id"/>
	<div class="wrap00">
	<!-- search _ start -->
	<c:set var="searchType" value='<%=request.getParameter("searchType")%>'/>
	<c:set var="searchValue" value='<%=request.getParameter("searchValue")%>'/>
	<c:set var="searchSelect" value='<%=request.getParameter("searchSelect")%>'/>
	<fieldset class="searchBox">
		<table align="left" class="searchTbl" style="margin:5px 0 5px 26px" border="0" cellpadding="1" cellspacing="0">
			<tr>
				<td align="left">
					<input type="radio" name="searchType" value="name" onclick="searchChange();"<c:if test="${searchType == 'name'}">checked</c:if>>   사용자별 &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="searchType" value="status" onclick="searchChange();"<c:if test="${searchType == 'status'}">checked</c:if>> 카드 상태
					<input type="text" name="searchValue" id="searchValue" value="${searchValue}" style="margin:0 7px 0 8px;"/>
					<select name="searchSelect" id="searchSelect" style="width:150px;">
						<option value="">전체</option>
						<c:forEach items="${statusList}" var="status">
						<option value="${status.key}" ${status.key == searchSelect? 'selected':''}>${status.value}</option>
						</c:forEach>
					</select>
					<input type="button" id="searchButton" value='<fmt:message key="label.common.search"/>' onclick="javascript:search()"/>
				</td>
			</tr>
		</table>
	</fieldset>
	<!-- search _ end -->

	<!-- list _ start -->
	<display:table name="rfidApplicationList" id="rfid" class="simple" style="margin:5px 0pt;" requestURI="/customer/rfidApplication/search.htm" pagesize="10" export="false">
		<display:column titleKey="label.customer.no" property="id"/>
		<display:column titleKey="label.customer.user.name" property="member.name"/>
		<display:column titleKey="label.customer.user.cardNo" property="cardNo"/>
		<display:column titleKey="label.customer.rfid.uid" property="rfidCard.uid"/>
		<display:column titleKey="label.customer.os" property="member.os"/>
		<display:column titleKey="label.customer.app.date">
			<fmt:parseDate var="strDate" value="${rfid.rcDt}" pattern="yyyy-MM-dd" />
			<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
		</display:column>
		<display:column titleKey="label.customer.regi.date">
			<fmt:parseDate var="strDate" value="${rfid.fstRgDt}" pattern="yyyy-MM-dd" />
			<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
		</display:column>
		<display:column titleKey="label.customer.stop.date">
			<fmt:parseDate var="strDate" value="${rfid.stDt}" pattern="yyyy-MM-dd" />
			<fmt:formatDate value="${strDate}" pattern="yyyy-MM-dd" />
		</display:column>
		<display:column titleKey="label.common.status" media="html">
		<c:choose>
			<c:when test="${rfid.status == '308101'}">미사용</c:when>
			<c:when test="${rfid.status == '308102'}">발급요청</c:when>
			<c:when test="${rfid.status == '308103'}">발급취소</c:when>
			<c:when test="${rfid.status == '308104'}">배송요청</c:when>
			<c:when test="${rfid.status == '308105'}">배송중</c:when>
			<c:when test="${rfid.status == '308106'}">배송완료</c:when>
			<c:when test="${rfid.status == '308107'}">사용중</c:when>
			<c:when test="${rfid.status == '308108'}">사용중지</c:when>
			<c:otherwise>-</c:otherwise>
		</c:choose>
		</display:column>
		<display:column titleKey="label.common.detail" style="text-align:center; width:140px;" media="html">
			<input type="button" style="width:65px !important;" value='<fmt:message key="label.common.detail"/>' onclick="location.href='/customer/rfidApplication/detail.htm?id=${rfid.id}'"/>
		<c:if test="${authority.update}">
			<input type="button" style="width:65px !important;" value='<fmt:message key="label.common.modify"/>' onclick="location.href='/customer/rfidApplication/update.htm?id=${rfid.id}'"/>
		</c:if>
		</display:column>
	</display:table>
	<!-- list _ end -->
	
	<!-- button _ start -->
	<%--
	<div class="footer">
		<table style="width:100%">
			<tr>
				<td height="30" align="right">
				<c:if test="${authority.create}">
					<input type="button" value='<fmt:message key="label.common.add"/>' onclick="javascript:insert()"/>
				</c:if>
				<c:if test="${authority.delete}">
					<input type="button" value='<fmt:message key="label.common.delete"/>' onclick="javascript:confirmAndDelete()"/>
				</c:if>
				</td>
			</tr>
		</table>
	</div>
	--%>
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>