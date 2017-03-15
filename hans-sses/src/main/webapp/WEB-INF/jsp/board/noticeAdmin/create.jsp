<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 저장
		$('#save').click(function(e) {
			var title = document.forms[0][0].value;
			var body = document.vForm.body.value;
			
			if(title.trim() == '') {
				alert("제목을 입력하세요.");
				return;
			}
			if(body.trim() == '') {
				alert("내용을 입력하세요.");
				return;
			}
			$("#vForm").submit();
		});
		
		// 이전 페이지로 이동
		$('#cancel').click(function(e) {
			if(confirm("정말 취소하시겠습니까?")) window.location.href = "/board/noticeAdmin/search.htm";
		});
	});
</script>
<spring:hasBindErrors name="boadNotice"/>
<form method="post" id="vForm" name="vForm" action="/board/noticeAdmin/create.htm" >
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%;">
		<!-- title -->
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td style="width:25%;" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.contentEditor.title"/>
			</td>
			<td style="width:25%;" class="td02">
				<input type="text" name="title" value="${boardNotice.title}" />
			</td>
			<td style="width:25%;" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.regDate"/>
			</td>
			<td style="width:25%;" class="td02">
				<fmt:formatDate value="${date}" pattern="yyyy.MM.dd"/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- body -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.contentEditor.content"/>
			</td>
			<td colspan="5" class="td02">
				<textarea name="body">${boadNotice.body}</textarea> 
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- status y/n -->
		<tr>
			<td height="25" class="td01">
			<span class="bul_dot1">◆</span>
			노출여부</td>
			<td colspan="5" class="td02">
				 <input type="radio" checked="checked" name="displayYn" value="Y"> 노출&nbsp;&nbsp;
				 <input type="radio" name="displayYn" value="N"> 비노출
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