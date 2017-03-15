<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 중복방지
		/* $('input[type="checkbox"][name="displayWho"]').click(function(){
			if($(this).prop('checked')) {
				$('input[type="checkbox"][name="displayWho"]').prop('checked', false);
				$(this).prop('checked', true);
			}
		}); */
		// 저장
		$('#save').click(function(e) {
			
			if( !(validationCheck()) ) return;
			
			var groups = getCheckedIds();
			$('#groups').val(groups);
			
			$("#vForm").submit();
		});
		
		// 이전 페이지로 이동
		$('#cancel').click(function(e) {
			window.location.href = "/board/FAQ/search.htm";
		});
	});
	
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
	
	function getCheckedIds() {
		var ids = [];
		
		$("input:checkbox:checked").each(function() {
			ids.push($(this).val());
		});
		
		return ids.join(";");
	}
	
	function validationCheck() {
		var title = document.vForm.question.value;
		var content = document.vForm.answer.value;

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
	
		if( $('input:checkbox:checked').length < 1 ) {
			alert("노출대상 선택 후 등록해주세요.");
			return;
		}
		
		var sel = document.getElementById("category");
		if( sel.options[sel.selectedIndex].value == '0') {
			alert("카테고리 선택하세요.");
			return;
		}
		
		return true;
	}
	
</script>
<spring:hasBindErrors name="board"/>
<form method="post" id="vForm" name="vForm" action="/board/FAQ/create.htm" >
<input type="hidden" id="groups" name="groups" />
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<!-- 노출대상 -->
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>노출대상	</td>
			<td colspan="2" height="25" class="td02">
				<input type="checkbox" checked id="selected" name="selected" value="0" style="margin:0 7px 0 8px;"/>사용자
				<input type="checkbox" id="selected" name="selected" value="1" style="margin:0 7px 0 8px;"/>건물주
				<input type="checkbox" id="selected" name="selected" value="2" style="margin:0 7px 0 8px;"/>설치자
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span> 카테고리분류				
			</td>
			<td colspan="2" class="td02">
				<c:set var="categoryKeys">0,1,2,3,4,5</c:set>
				<c:set var="categoryValues">선택,회원가입,회원인증,충전,요금 및 결제,기타</c:set>
				<c:set var="category" value='<%=request.getParameter("category")%>'/>
				<select name="category" id="category">
					<c:forTokens var="key" items="${categoryKeys}" delims="," varStatus="statKey">
						<c:forTokens var="val" items="${categoryValues}" delims="," varStatus="statVal">
							<c:if test="${statKey.index == statVal.index}">
								<option value="${key}" ${key == category ? 'selected':''}>${val}</option>
							</c:if>
						</c:forTokens>
					</c:forTokens>
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
			<td colspan="5" class="td02"><fmt:formatDate value="${date}" pattern="yyyy.MM.dd"/></td>
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
				 <input type="radio" checked="checked" name="display_yn" value="Y"> 활성화&nbsp;&nbsp;
				 <input type="radio" name="display_yn" value="N"> 비활성화
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