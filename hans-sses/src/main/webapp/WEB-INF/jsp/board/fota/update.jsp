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
			$("#vForm").submit();
		});
		
		// 유효성 검사
// 		$("#vForm").validate({
// 			onfocusout:false,
// 			onkeyup:false,
// 			rules:{
// 				name:"required"
// 			},
// 			messages:{
// 				name:{
// 					required:'<fmt:message key="validate.required"/>'
// 				}
// 			}
// 		});
	});

	// 이전 페이지로 이동
	function cancel() {
		if(confirm("정말 취소하시겠습니까?")) history.back();
	}

</script>

<spring:hasBindErrors name="board"/>
<form method="post" id="vForm" name="vForm" action="/board/fota/update.htm?snId=${boadFota.snId}" enctype="multipart/form-data">
<div class="wrap00">
	<!-- input _ start -->
<table style="width:100%">
		<!-- title -->
		<tr class="line-top"><td colspan="2"/></tr>
		<tr class="line-dot"><td colspan="2"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				파일명
			</td>
			<td class="td02">
				<input type="text" name="fileName" style="width:600px" value="${boadFota.fileName}" />
			</td>
		</tr>
		<tr class="line-dot"><td colspan="2"/></tr>
		<!-- body -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				버전 정보
			</td>
			<td class="td02">
				<input type="text" name="ver" style="width:600px" value="${boadFota.ver}" />
			</td>
		</tr>
		<tr class="line-dot"><td colspan="2"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				업데이트 내용
			</td>
			<td class="td02">
				<input type="text" name="content" style="width:600px" value="${boadFota.content}" />
			</td>
		</tr>
		<tr class="line-dot"><td colspan="2"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				FOTA업로드
			</td>
			<td class="td02">
				<input type="file" name="fotaFile" style="width:300px" value="" />&nbsp;${boadFota.fileName}		
			</td>
		</tr>
		<tr class="line-dot"><td colspan="2"/></tr>
		
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
					<input type="button" value='<fmt:message key="label.common.cancel"/>' onclick="cancel()"/>
				</c:if>
				</td>
			</tr>
		</table>
	</div>				
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>