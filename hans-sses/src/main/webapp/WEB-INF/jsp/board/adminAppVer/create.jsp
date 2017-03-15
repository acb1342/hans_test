<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<link href="/js/jquery/ui/month/MonthPicker.min.css" rel="stylesheet" type="text/css" />
<link href="/js/jquery/ui/month/examples.css" rel="stylesheet" type="text/css" />
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="/js/jquery/ui/month/MonthPicker.min.js"></script>
<script type="text/javascript" src="/js/jquery/ui/month/jquery.mtz.monthpicker.js"></script>
<script type="text/javascript">
month_options = {
		pattern: 'yyyy-mm',
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']		
};
$.datepicker.regional['ko'] = {
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		dayNames: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
		dateFormat: 'yy-mm-dd',
		yearSuffix: '년',
		showMonthAfterYear: true
};
$.datepicker.setDefaults($.datepicker.regional['ko']);

	$(function() {
		
		$("#selDate").datepicker();
		
		// 저장
		$('#save').click(function(e) {	
			if(!validationCheck()) return;
			if(confirm("등록하시겠습니까?")) {
				$("#vForm").submit();
				alert("등록되었습니다.");
			}
		});
		
		// 이전 페이지로 이동
		$('#cancel').click(function(e) {
			history.go(-1);
		});
	});
	
	function validationCheck() {
		/* if( $('#searchType option').index($('#searchType option:selected')) == 0 ) {
			alert("분류를 선택하세요!");
			return;
		}
		if( !$("#content").val() ) {
			alert("내용을 입력하세요!");
			return;
		}
		if( $('#searchType option').index($('#searchType option:selected')) == 2 ) {
			if( $('#setChargerMgmtNoSelect option').index($('#setChargerMgmtNoSelect option:selected')) == 0 ) {
				alert("건물정보를 불러온후에 충전그룹/관리번호를 선택하세요!");
				return;
			}
		}
		if( $('#bdSelect option').index($('#bdSelect option:selected')) == 0 ) {
			alert("건물정보를 불러온후에 상세/동명을 선택하세요!");
			return;
		}
		 */
		return true;
	}
</script>

<form method="post" id="vForm" name="vForm" action="/board/adminAppVer/create.htm" >
<div class="wrap00">
	<input type="hidden" name="seq" value="${appVer.snId}" />
	<!-- input _ first -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01"><fmt:message key="label.boad.target"/></td>
			<td class="td02" colspan="5">
				<c:set var="targetKeys">101203,101204</c:set>
				<c:set var="targetValues">건물주,설치자</c:set>
				<c:set var="target" value='<%=request.getParameter("target")%>'/>
				<select name="target" id="target" style="width: 100px">
					<c:forTokens var="key" items="${targetKeys}" delims="," varStatus="statKey">
						<c:forTokens var="val" items="${targetValues}" delims="," varStatus="statVal">
							<c:if test="${statKey.index == statVal.index}">
								<option value="${key}" ${key == target ? 'selected':''}>${val}</option>
							</c:if>
						</c:forTokens>
					</c:forTokens>
				</select>
				</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.os"/>
			</td>
			<td class="td02">
			<c:set var="OSKeys">301402,301401</c:set>
				<c:set var="OSValues">iOS,Android</c:set>
				<c:set var="OS" value='<%=request.getParameter("OS")%>'/>
				<select name="OS" id="OS" style="width: 100px">
					<c:forTokens var="key" items="${OSKeys}" delims="," varStatus="statKey">
						<c:forTokens var="val" items="${OSValues}" delims="," varStatus="statVal">
							<c:if test="${statKey.index == statVal.index}">
								<option value="${key}" ${key == OS ? 'selected':''}>${val}</option>
							</c:if>
						</c:forTokens>
					</c:forTokens>
				</select>
			</td>
			<td height="25" class="td01" nowrap>
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.boad.inputVersion"/>
			</td>
			<td class="td02"><input type="text" name="ver" style="width: 100px"/>${appVer.ver}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.boad.isRequired"/>
			</td>
			<td class="td02">
			<c:set var="udtKeys">605101,605102</c:set>
				<c:set var="udtValues">필수,선택</c:set>
				<c:set var="udt" value='<%=request.getParameter("udt")%>'/>
				<select name="udt" id="udt" style="width: 100px">
					<c:forTokens var="key" items="${udtKeys}" delims="," varStatus="statKey">
						<c:forTokens var="val" items="${udtValues}" delims="," varStatus="statVal">
							<c:if test="${statKey.index == statVal.index}">
								<option value="${key}" ${key == udt ? 'selected':''}>${val}</option>
							</c:if>
						</c:forTokens>
					</c:forTokens>
				</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01" nowrap>
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.boad.udtBody"/>
			</td>
			<td colspan="5" class="td02">
				<textarea id="content" name="content" >${appVer.content}</textarea>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.boad.dueDateToDeply"/>
			</td>
			<td colspan="5" class="td02">
				<c:set var="selDate" value='<%=request.getParameter("selDate")%>' />
				<input id="selDate" name="selDate" style="width: 100px" value="${selDate}"/>
			<span>
				<select name="hour" style="width: 100px">
					<c:forEach begin="0" end="23" step="1" var="hour">
						<option value="${hour}">${hour}시</option>
					</c:forEach>
				</select>
			</span>
			<span>
				<select name="minute" style="width: 100px">
					<c:forEach begin="0" end="50" step="10" var="minute">
						<option value="${minute}">${minute}분</option>
					</c:forEach>
				</select>
			</span>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				바이너리 파일(URL)
			</td>
			<td colspan="5" class="td02">
				<input type="text" name="url" />
			</td>
		</tr>
	</table>
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