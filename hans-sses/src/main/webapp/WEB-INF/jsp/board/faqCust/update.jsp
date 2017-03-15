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
			if( !(validationCheck()) ) return;
			$("#vForm").submit();	
		});
		
		
	});

	function cancel() {
		if(confirm("정말 취소하시겠습니까?")) {
			var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
			if (callbackUrl != 'null') {
				location.href = "/board/faqCust/search.htm?" + callbackUrl;
			} else {
				location.href = "/board/faqCust/search.htm";
			}
		}
	}
	
	function getTextLength(str) {
		var len = 0;
		for(var i = 0; i < str.length; i++) {
			if(escape(str.charAt(i)).length == 6) {
				len++;
			}
			len++;
		}
		return len;
	}
	
	function validationCheck() {
		var title = document.vForm.question.value;
		var content = document.vForm.answer.value;
		var sel = document.getElementById("category");
		
		if( sel.options[sel.selectedIndex].value == '0') {
			alert("카테고리 선택하세요.");
			return;
		}
		
		if(title.trim() == '') {
			alert("제목을 입력하세요.");
			return;
		}
		
		if(getTextLength(title)>100) {
			alert("제목은 한글 50자까지만");
			return;
		}
		
		if(content.trim() == '') {
			alert("내용을 입력하세요.");
			return;
		}
		
		if(getTextLength(content)>2400) {
			alert("내용은 한글 1200자까지만");
			return;
		}
		
		return true;
	}
</script>


<form method="post" id="vForm" name="vForm" action="/board/faqCust/update.htm?id=${boadFaq.snId}">
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span> 카테고리분류				
			</td>
			<td colspan="2" class="td02">
				<select name="category" id="category">
					<option <c:if test="${'0' eq boadFaq.category || '601100' eq boadFaq.category }">selected</c:if> value="0">선택</option>
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
				<span class="bul_dot1">◆</span>	<fmt:message key="label.common.regDate"/></td>
			<td colspan="5" class="td02"><fmt:formatDate value="${boadFaq.fstRgDt}" pattern="yyyy.MM.dd"/></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- content -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>	<fmt:message key="label.contentEditor.content"/></td>
			<td colspan="5" class="td02"><textarea maxlength="1200" name="answer">${boadFaq.answer}</textarea></td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- status y/n -->
		<tr>
			<td height="25" class="td01">
			<span class="bul_dot1">◆</span>
			노출여부</td>
			<td colspan="5" class="td02">
				 <input type="radio" <c:if test="${'Y' eq boadFaq.displayYn}">checked</c:if> name="displayYn" value="Y"> 활성화&nbsp;&nbsp;
				 <input type="radio" <c:if test="${'N' eq boadFaq.displayYn}">checked</c:if> name="displayYn" value="N"> 비활성화
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