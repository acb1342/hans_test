<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroupAuth" %>
<%@ page import="com.mobilepark.doit5.admin.model.Menu" %>
<%@ page import="java.util.Map" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		
		// 저장
		$('#save').click(function(e) {
			if( $('input[type="checkbox"][name="selected"]').is(":checked") == true) {
				
				var groups = getCheckedIds();
				$('#groups').val(groups);
				
				$("#vForm").submit();	
			}			
			else {
				alert("노출대상 체크하세요");
				return;
			}
		});
		
		
	});

	function cancel() {
		if(confirm("정말 취소하시겠습니까?")) {
			var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
			if (callbackUrl != 'null') {
				location.href = "/board/FAQ/search.htm?" + callbackUrl;
			} else {
				location.href = "/board/FAQ/search.htm";
			}
		}
		
	}

	function getCheckedIds() {
		var ids = [];
		
		$("input:checkbox:checked").each(function() {
			ids.push($(this).val());
		});
		
		return ids.join(";");
	}
</script>

<spring:hasBindErrors name="boadFaq"/>
<form method="post" id="vForm" name="vForm" action="/board/FAQ/update.htm?seq=${boadFaq.sn_id}">
<div class="wrap00">
<input type="hidden" id="groups" name="groups" />
	<!-- input _ start -->
	<table style="width:100%">
		<!-- 노출대상 -->
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>노출대상	</td>
			<td colspan="2" height="25" class="td02">
				<input <c:if test="${'Y' eq boadFaq.cust_yn}">checked</c:if> type="checkbox" id="selected" name="selected" value="0" style="margin:0 7px 0 8px;"/>사용자
				<input <c:if test="${'Y' eq boadFaq.owner_yn}">checked</c:if> type="checkbox" id="selected" name="selected" value="1" style="margin:0 7px 0 8px;"/>건물주
				<input <c:if test="${'Y' eq boadFaq.installer_yn}">checked</c:if> type="checkbox" id="selected" name="selected" value="2" style="margin:0 7px 0 8px;"/>설치자
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span> 카테고리분류				
			</td>
			<td colspan="2" class="td02">
				<select name="category" id="category">
					<option <c:if test="${'' eq boadFaq.category || '601100' eq boadFaq.category }">selected</c:if> value="0">전체</option>
					<option <c:if test="${'601101' eq boadFaq.category }">selected</c:if> value="1">회원가입</option>
					<option <c:if test="${'601102' eq boadFaq.category }">selected</c:if> value="2">회원인증</option>
					<option <c:if test="${'601103' eq boadFaq.category }">selected</c:if> value="3">충전</option>
					<option <c:if test="${'601104' eq boadFaq.category }">selected</c:if> value="4">요금 및 결제</option>
					<option <c:if test="${'601105' eq boadFaq.category }">selected</c:if> value="5">기타</option>
				</select>
			</td>	
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- title -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.contentEditor.title"/>
			</td>
			<td colspan="5" class="td02">
				<input type="text" name="question" value="${boadFaq.question}" />
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- 등록일 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>	<fmt:message key="label.common.createDate"/></td>
			<td colspan="5" class="td02"><fmt:formatDate value="${boadFaq.fstRgDt}" pattern="yyyy.MM.dd"/></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- content -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>	<fmt:message key="label.contentEditor.content"/></td>
			<td colspan="5" class="td02"><textarea  maxlength="1200" name="answer">${boadFaq.answer}</textarea></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- status y/n -->
		<tr>
			<td height="25" class="td01">
			<span class="bul_dot1">◆</span>
			노출여부</td>
			<td colspan="5" class="td02">
				 <input type="radio" <c:if test="${'Y' eq boadFaq.disply_yn}">checked</c:if> name="display_yn" value="Y"> 활성화&nbsp;&nbsp;
				 <input type="radio" <c:if test="${'N' eq boadFaq.disply_yn}">checked</c:if> name="display_yn" value="N"> 비활성화
			</td>
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
					<input type="button" value='<fmt:message key="label.common.cancel"/>' onclick="javascript:cancel()"/>
				</c:if>
				</td>
			</tr>
		</table>
	</div>				
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>