<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroupAuth" %>
<%@ page import="com.mobilepark.doit5.admin.model.Menu" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	function reply() {
		var content = document.vForm.body.value;
		
		if(content.trim() == '') {
			alert("내용을 입력하세요.");
			return;
		}
		
		if(getTextLength(content)>2400) {
			alert("내용은 한글 1200자까지만");
			return;
		}
	
		$("#vForm").submit();
	}
	
	function replyUpdate() {
		var content = document.vForm.content.value;
		
		if(content.trim() == '') {
			alert("내용을 입력하세요.");
			return;
		}
		
		if(getTextLength(content)>2400) {
			alert("내용은 한글 1200자까지만");
			return;
		}
		
		location.href = "/board/qnaAdmin/replyUpdate.htm?id=${boadQna.snId}";
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
	
	// 수정 페이지로 이동
	function update() {
		location.href = "/board/qnaAdmin/update.htm?id=${boadQna.snId}&wk=update&page=${page}";
	}
	
	function updateQuestion() {
		location.href = "/board/qnaAdmin/updateQuestion.htm?id=${boadQna.snId}&page=${page}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/board/qnaAdmin/deleteRepl.json', '${boadQna.snId}', function() { 
			search();
		});
	}

	function deleteQuestion() {
		deleteById('/board/qnaAdmin/delete.json', '${boadQna.snId}', function() { 
			search();
		});
	}
	
	// 검색 페이지로 이동
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			location.href = "/board/qnaAdmin/search.htm?" + callbackUrl;
		} else {
			location.href = "/board/qnaAdmin/search.htm";
		}
	}
	
</script>
<div class="wrap00">
<input type="hidden" name="page" value="${page}"/>
	<!-- list _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.boad.custType"/>
			</td>
			<td colspan="5" class="td02">
				<c:if test="${ '0' eq boadQna.writerType }">사용자</c:if>
				<c:if test="${ '1' eq boadQna.writerType }">운영자</c:if>
				<c:if test="${ '2' eq boadQna.writerType }">설치자</c:if>
				<c:if test="${ '3' eq boadQna.writerType }">건물주</c:if>
				<c:if test="${ '4' eq boadQna.writerType }">상담사</c:if>
				<c:if test="${ '99' eq boadQna.writerType }">미등록 사용자</c:if>
			</td>
		</tr>
		<!-- 제목 -->
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.contentEditor.title"/>
			</td>
			<td colspan="5" class="td02">${boadQna.title}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		
		<!-- 작성자 id -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.writer"/>
			</td>
			<td colspan="2" class="td02">${boadQna.penName}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.regDate"/>
			</td>
			<td colspan="2" class="td02"><fmt:formatDate value="${boadQna.fstRgDt}" pattern="yyyy.MM.dd"/></td>
			</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		
		<!-- 내용 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.contentEditor.content"/>
			</td>
			<td colspan="5" class="td02">
				<textarea name="body" readonly="readonly">${boadQna.body}</textarea>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		
		<!-- 공개여부 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>공개여부</td>
			<td colspan="5" class="td02">
				<c:if test="${'Y' eq boadQna.openYn}">공개</c:if>
				<c:if test="${'N' eq boadQna.openYn}">비공개</c:if>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
	</table></br>
	<!-- list _ end -->
	<input type="hidden" id="repl" value="${boadQna.snId}" />
	<div class="line-clear"></div>
	<!-- 답변달기 -->
	<form method="post" id="vForm" name="vForm" action="/board/qnaAdmin/reply.htm?id=${boadQna.snId}">
	<table style="width:100%">
		<!-- ---------------답변글 있을 때------------------------------------------------------------------ -->
		<c:if test="${'Y' eq replyStatus }">
			<tr class="line-top"><td colspan="6"/></tr>
			<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.createDate"/>
			</td>
			<td colspan="2" class="td02">
				<fmt:formatDate value="${replyTime}" pattern="yyyy.MM.dd"/>
			</td>
			</tr>
			<tr class="line-dot"><td colspan="6"/></tr>
			<tr>
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.contentEditor.content"/>
				</td>
				<td colspan="2" class="td02">
					<textarea name="content" id="content" readonly="readonly">${replyContent}</textarea>
				</td>
			</tr>
			<tr class="line-dot"><td colspan="6"/></tr>
			<!-- status y/n -->
		</c:if>
	</table>
	</form>
	

	<!-- button _ start -->
	<div class="footer">	
		<table style="width:100%">
			<tr>
				<td height="30">
					<input type="button" value='<fmt:message key="label.common.list"/>' onclick="javascript:search()"/>
				</td>
				<td align="right">
				<c:if test="${authority.update}">
					<c:choose>
					<c:when test="${loginGroup.id == 2 || loginGroup.id == 3 }">
						<c:if test="${loginUser.id eq boadQna.fstRgUsid }">
							<input <c:if test="${'Y' eq replyStatus }">disabled</c:if> type="button" value='<fmt:message key="label.common.modify"/>' onclick="javacript:updateQuestion()"/>
						</c:if>
					</c:when>
					<c:otherwise>
						<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="javascript:update()"/>
					</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${authority.delete}">
					<c:if test="${loginGroup.id == 1 }"><input type="button" class="btn_red" value='<fmt:message key="label.common.delete"/>' onclick="javascript:confirmAndDelete()"/></c:if>
					<c:if test="${loginGroup.id == 2 || loginGroup.id == 3 }">
						<c:if test="${loginUser.id eq boadQna.fstRgUsid }">
							<input type="button" class="btn_red" value='<fmt:message key="label.common.delete"/>' onclick="javascript:deleteQuestion()"/>
						</c:if>
					</c:if>
				</c:if>
				</td>
			</tr>
		</table>
	</div>
	<!-- button _ end -->
</div>
<%@ include file="/include/footer.jspf" %>