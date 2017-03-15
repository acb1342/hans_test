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
			e.preventDefault();
			var title = document.vForm.title.value;
			var content = document.vForm.body.value;
			
			if(title.trim() == '') {
				alert("제목을 입력하세요.");
				return;
			}
			if(content.trim() == '') {
				alert("내용을 입력하세요.");
				return;
			}

			$("#vForm").submit();
		});
		

	function cancel() {
		if(confirm("정말 취소하시겠습니까?")) history.back();
	}

</script>

<spring:hasBindErrors name="boadQna"/>
<form method="post" id="vForm" name="vForm" action="/board/QnA/replyUpdate.htm?seq=${boadQna.sn_id}&&body=${replyBody}">
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">	
		<tr class="line-dot"><td colspan="3"/></tr>		
		<!-- 제목 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.contentEditor.title"/>
			</td>
			<td colspan="2" class="td02">${boadQna.title}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		
		<!-- 작성자 id -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.writer"/>
			</td>
			<td colspan="2" class="td02">${boadQna.pen_name}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		
		<!-- 생성일 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cdt"/>
			</td>
			<td colspan="2" class="td02"><fmt:formatDate value="${boadQna.fst_cdt}" pattern="yyyy.MM.dd HH:mm"/></td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		
		<!-- 내용 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.contentEditor.content"/>
			</td>
			<td colspan="2" class="td02">
				<textarea name="body" readonly="readonly">${boadQna.body}</textarea>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		
		<!-- 공개여부 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>공개여부</td>
			<td colspan="2" class="td02">
				<input type="radio" checked="checked">&nbsp;${boadQna.open_yn}</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
	</table><br/>
	
	<table style="width:100%">
			<tr class="line-dot"><td colspan="3"/></tr>
			<!-- 수정일 -->
			<tr>
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.cdt"/>
				</td>
				<td colspan="2" class="td02">
					<fmt:formatDate value="${date}" pattern="yyyy.MM.dd HH:mm"/>
				</td>
			</tr>
		
		<tr class="line-dot"><td colspan="3"/></tr>
			<!-- 내용 -->
			<tr class="line-dot"><td colspan="3"/></tr>
			<tr>
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.contentEditor.content"/>
				</td>
				<td colspan="2" class="td02">
					<textarea name="body" id="body">${replyBody}</textarea> 
				</td>
			</tr>
			<tr class="line-dot"><td colspan="3"/></tr>
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