<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 저장
		$('#save').click(function(e) {
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
		
		// 이전 페이지로 이동
		$('#cancel').click(function(e) {
			window.location.href = "/board/qnaCust/search.htm";
		});
	});
</script>
<spring:hasBindErrors name="board"/>
<form method="post" id="vForm" name="vForm" action="/board/qnaCust/create.htm" >
<div class="wrap00">
	
	<!-- input _ start -->
	<table style="width:100%">
		<!-- title -->
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.boad.custType"/>
			</td>
			<td colspan="5" class="td02">
				<c:if test="${loginGroup.id == 1 }">운영자</c:if>
				<c:if test="${loginGroup.id == 2 }">설치자</c:if>
				<c:if test="${loginGroup.id == 3 }">건물주</c:if>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.contentEditor.title"/>
			</td>
			<td colspan="5" class="td02">
				<input type="text" id="title" name="title" value="${boadQna.title}" />
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		
		<!-- 작성자 -->
		<tr>
			<td style="width:20%;" height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.writer"/>
			</td>
			<td style="width:30%;" colspan="2" class="td02">${loginUser.name}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.regDate"/>
			</td>
			<td colspan="2" class="td02">
				<fmt:formatDate value="${date}" pattern="yyyy.MM.dd HH:mm"/>
			</td>
		</tr>	
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- content -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.contentEditor.content"/>
			</td>
			<td colspan="5" class="td02">
				<textarea id="body" name="body">${boadQna.body}</textarea> 
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		
		<!-- status y/n -->
		<tr>
			<td height="25" class="td01">
			<span class="bul_dot1">◆</span>
			공개설정</td>
			<td colspan="5" class="td02">
				 <input type="radio" checked="checked" name="status" value="Y"> 공개&nbsp;&nbsp;
				 <input type="radio" name="status" value="N"> 비공개
			</td>
		</tr>
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