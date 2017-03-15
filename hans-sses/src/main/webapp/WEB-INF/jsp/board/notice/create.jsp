<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript" src="${contextPath}/editor/ckeditor453/ckeditor.js"></script>
<script type="text/javascript" src="${contextPath}/editor/ckeditor453/config.js"></script>
<script type="text/javascript">
	$(function() {
		
		// ckEditor 생성
		var editor = CKEDITOR.replace('content',{
	    	enterMode:'2',
	    	shiftEnterMode:'3',
	    	height:'450px'
	    });
		
		CKEDITOR.on('dialogDefinition', function(e) {
	    	var dialogName = e.data.name;
	    	var dialog     = e.data.definition.dialog;
	
	        if (dialogName == 'image') {
	        	dialog.on('show', function () {
	        		dialog.hidePage('Link');
	        		dialog.selectPage('Upload');
	            });
	        } else if (dialogName == 'link') {
	        	dialog.on('show', function () {
	        		dialog.hidePage('target');
	        		dialog.selectPage('upload');
	            });
	        }
	    });
		
		// 중복방지
		$('input[type="checkbox"][name="displayWho"]').click(function(){
			if($(this).prop('checked')) {
				$('input[type="checkbox"][name="displayWho"]').prop('checked', false);
				$(this).prop('checked', true);
			}
		});
		// 저장
		$('#save').click(function(e) {
			var contentEditor = CKEDITOR.instances.content;
			$("#body").val(contentEditor.getData());
			
			var title = $('#title').val();
			var body = document.vForm.body.value;
			
			if(title == '' || title == null) {
				alert("제목을 입력하세요.");
				$('#title').focus();
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
			if(confirm("정말 취소하시겠습니까?")) window.location.href = "/board/notice/search.htm";
		});
	});
</script>
<spring:hasBindErrors name="boadNotice"/>
<form method="post" id="vForm" name="vForm" action="/board/notice/create.htm" >
<input type="hidden" id="body" name="body" value=""/>
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<!-- title -->
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>노출대상	</td>
			<td colspan="5" height="25" class="td02">
				<input type="checkbox" checked id="displayWho" name="displayWho" value="0" style="margin:0 7px 0 8px;"/>사용자
				<input type="checkbox" id="displayWho" name="displayWho" value="1" style="margin:0 7px 0 8px;"/>건물주
				<input type="checkbox" id="displayWho" name="displayWho" value="2" style="margin:0 7px 0 8px;"/>설치자
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.contentEditor.title"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="title" name="title" value="${boardNotice.title}" />
			</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.regDate"/>
			</td>
			<td colspan="2" class="td02">
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
				<textarea id="content" name="content">${boadNotice.body}</textarea> 
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- status y/n -->
		<tr>
			<td height="25" class="td01">
			<span class="bul_dot1">◆</span>
			노출여부</td>
			<td colspan="5" class="td02">
				 <input type="radio" checked="checked" name="display_yn" value="Y"> 노출&nbsp;&nbsp;
				 <input type="radio" name="display_yn" value="N"> 비노출
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