<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroupAuth" %>
<%@ page import="com.mobilepark.doit5.admin.model.Menu" %>
<%@ page import="java.util.Map" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 중복방지
		$('input[type="checkbox"][name="displayWho"]').click(function(){
			if($(this).prop('checked')) {
				$('input[type="checkbox"][name="displayWho"]').prop('checked', false);
				$(this).prop('checked', true);
			}
		});
		// 저장
		$('#save').click(function(e) {
			e.preventDefault();
			$("#vForm").submit();
		});
		
		// 유효성 검사
		$("#vForm").validate({
			onfocusout:false,
			onkeyup:false,
			rules:{
				name:"required"
			},
			messages:{
				name:{
					required:'<fmt:message key="validate.required"/>'
				}
			}
		});
	});

	// 이전 페이지로 이동
	function cancel() {
		if(confirm("정말 취소하시겠습니까?")) history.back();
	}

</script>

<spring:hasBindErrors name="board"/>
<form method="post" id="vForm" name="vForm" action="/board/noticeCust/update.htm?id=${boadNotice.snId}">

<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">	
		<!-- title -->
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.contentEditor.title"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="title" value="${boadNotice.title}" />
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.regDate"/>
			</td>
			<td colspan="2" class="td02">
				<fmt:formatDate value="${boadNotice.fstRgDt}" pattern="yyyy.MM.dd"/>
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
				<textarea name="body">${boadNotice.body}</textarea> 
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- 노출여부 -->
		<tr>
			<td height="25" class="td01">
			<span class="bul_dot1">◆</span>	노출여부</td>
			<td colspan="5" class="td02">
				 <input type="radio" <c:if test="${'Y' eq boadNotice.displayYn }">checked</c:if> checked="checked" name="displayYn" value="Y"> 노출&nbsp;&nbsp;
				 <input type="radio" <c:if test="${'N' eq boadNotice.displayYn }">checked</c:if> name="displayYn" value="N"> 비노출
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