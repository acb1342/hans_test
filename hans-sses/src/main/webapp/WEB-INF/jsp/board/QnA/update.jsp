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
			$("#vForm").submit();
		});
		
		$('#questionUpdate').click(function(e) {
			var body = document.getElementById('body').value;
			var openYn = $("input[type=radio][name=openYn]:checked").val();
			var appendUrl = "&body="+body+"&openYn="+openYn;
			
			location.href="/board/QnA/execUpdateQuestion.htm?seq=${boadQna.sn_id}&page=${page}&target=question"+appendUrl;
		});
		
		
	});

	function cancel() {
		if(confirm("정말 취소하시겠습니까?")) window.location.href = "/board/QnA/search.htm";
	}

</script>

<spring:hasBindErrors name="board"/>
<form method="post" id="vForm" name="vForm" action="/board/QnA/update.htm?seq=${boadQna.sn_id}&page=${page}">
<input type="hidden" name="wk" value="${wk}"/>
<input type="hidden" name="page" value="${page}"/>

<div class="wrap00">
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
			<td colspan="2" class="td02">${boadQna.pen_name}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.regDate"/>
			</td>
			<td colspan="2" class="td02"><fmt:formatDate value="${boadQna.fstRgDt}" pattern="yyyy.MM.dd HH:mm"/></td>
			</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		
		<!-- 내용 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.contentEditor.content"/>
			</td>
			<td colspan="5" class="td02">
				<c:if test="${loginGroup.id == 1 }"><textarea readonly="readonly">${boadQna.body}</textarea></c:if>
				<c:if test="${loginGroup.id == 2 || loginGroup.id == 3 }"><textarea id="body" name="body" >${boadQna.body}</textarea></c:if>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		
		<!-- 공개여부 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>공개여부</td>
			<td colspan="5" class="td02">
				<c:if test="${loginGroup.id == 1 }">
					<c:if test="${'Y' eq boadQna.open_yn}">공개</c:if>
					<c:if test="${'N' eq boadQna.open_yn}">비공개</c:if>
				</c:if>
				<c:if test="${loginGroup.id == 2 || loginGroup.id == 3 }">
					<input <c:if test="${'Y' eq boadQna.open_yn}">checked</c:if> type="radio" id="openYn" name="openYn" value="Y"> 공개&nbsp;&nbsp;
					<input <c:if test="${'N' eq boadQna.open_yn}">checked</c:if> type="radio" id="openYn" name="openYn" value="N"> 비공개
				</c:if>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
	</table></br>
	<div class="line-clear"></div>
	<!-- list _ end -->
	
	<!-- input _ end -->
	<input type="hidden" id="repl" value="${boadQna.sn_id}" />
	<!-- 답변달기 -->
	<table style="width:100%">
		<c:if test="${ 'Y' ne replyStatus && authority.create }">
			<c:if test="${loginGroup.id == 1 }">
			<tr class="line-top"><td colspan="3"/></tr>
			<!-- 등록일 -->
			<tr>
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.common.regDate"/>
				</td>
				<td colspan="2" class="td02">
					<fmt:formatDate value="${date}" pattern="yyyy.MM.dd"/>
				</td>
			</tr>
			<!-- 내용 -->
			<tr class="line-dot"><td colspan="3"/></tr>
			<tr>
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.contentEditor.content"/>
				</td>
				<td colspan="2" class="td02">
					<textarea name="body" id="body">${beforeBody}</textarea> 
				</td>
			</tr>
			<tr class="line-dot"><td colspan="3"/></tr>
			</c:if>	
		</c:if>
			<tr class="line-dot"><td colspan="3"/></tr>
	</table>

	<!-- button _ start -->
	<div class="footer">
		<table style="width:100%">
			<tr>
				<td width="50%" height="30"></td>
				<td width="50%" align="right">		
				<c:if test="${authority.update}">
					<c:if test="${loginGroup.id == 1 }"><input type="button" id="save" value='<fmt:message key="label.common.save"/>'/>	</c:if>
				</c:if>
				<c:if test="${loginGroup.id == 2 || loginGroup.id == 3 }">
					<input type="button" id="questionUpdate" value='<fmt:message key="label.common.save"/>' />
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