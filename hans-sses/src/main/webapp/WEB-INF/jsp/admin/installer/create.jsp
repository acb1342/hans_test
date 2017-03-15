<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 저장
		$('#save').click(function(e) {
			if ($('#isIdCheckComplete').val() == '' || $('#regId').val() == '') {
				alert("ID 중복확인을 해주세요.");
				$('#regId').focus();
				return;
			}
			if ($('#isNameCheckComplete').val() == '' || $('#regName').val() == '') {
				alert("이름 중복확인을 해주세요.");
				$('#name').focus();
				return;
			}
			$("#vForm").submit();
		});
		
		// 이전 페이지로 이동
		$('#cancel').click(function(e) {
			window.location.href = "/admin/installer/search.htm";
		});
	
		// 아이디 중복 확인 버튼
		$('#idCheckBtn').click(function(e) {
			if (!idValCheck()) return;
			else {
				$.ajax({
					type:'POST',
					url:'/admin/operator/checkid.json',
					data:{
						id:$("#regId").val(),					
					},
					success:function (data) {						
						if(!data) {
							alert("사용할 수 없는 ID 입니다.");
							return;
						}
						alert("사용가능한 ID 입니다.");
						document.getElementById("isIdCheckComplete").value = 1;
					},
					error: function(e) {
						
					}
				});
			}
		});
		
		// 이름 중복 확인 버튼 
		$('#nameCheckBtn').click(function(e) {
			$.ajax({
				type:'POST',
				url:'/admin/installer/checkName.json',
				data:{
					name:$("#name").val(),					
				},
				success:function (data) {						
					if(!data) {
						alert("중복된 이름 입니다.");
						document.getElementById("isNameCheckComplete").value = '';
						return;
					}
					alert("사용가능한 이름 입니다.");
					document.getElementById("isNameCheckComplete").value = 1;
					document.getElementById("regName").value = document.getElementById("name").value;
					//document.getElementById("name").readOnly = true;
					//document.getElementById("nameCheckBtn").disabled = true;
				},
				error: function(e) {
					
				}
			});
		});
		
		// 유효성 체크
		$.validator.addMethod("passwordConfirm", function(value, element) {
			return $("#passwd").val() == $("#passwdCfm").val();
		},"비밀번호가 일치하지 않습니다.");
		
		$.validator.addMethod("passwdMix", function(value, element) {
			var pattern = /^.*(?=.{8,64})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
			var pwd = $("#passwd").val();
			if (pattern.test(pwd)) return true;
			else return false;
		},"<fmt:message key='validate.passwdMinlength'/>");
		
		$("#vForm").validate({
			onfocusout:false,
			onkeyup:false,
			rules:{
				passwd:{
					required:true,
					passwordConfirm:true,
					passwdMix:true
				},
				passwdCfm:{
					required:true,
					passwordConfirm:true
				},
				tel:"number",
				mobile:"required number",
				email:"required email"
			},
			messages:{
				email:{
					required:'<fmt:message key="validate.required"/>',
					email:'<fmt:message key="validate.email"/>'
				},
				passwd:{
					required:'<fmt:message key="validate.required"/>'
				},
				passwdCfm:{
					required:'<fmt:message key="validate.required"/>'
				},
				tel:{
					number:'<fmt:message key="validate.number"/>'
				},
				mobile:{
					required:'<fmt:message key="validate.required"/>',
					number:'<fmt:message key="validate.number"/>'
				}
			}
		});
	});
	
	function idValCheck() {
		var strSpace = $('#regId').val().replace(/s/gi,'');
		var blank = /^\s+|\s+$/g;
		var pattern = /^.*(?=.{8,32})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
		
		// ID 공백체크
		if (strSpace == '') {
			alert("ID를 입력해주세요.");
			$('#regId').focus();
			return;
		}
		else if(strSpace.replace(blank,'') == "") {
			alert("공백은 사용이 불가합니다.");
			return;
		}
		else if (!$('#regId').val().match(pattern)) {
			alert("ID는 영문+숫자 8자 이상이어야 합니다.");
			return;
		}
		
		return true;
	}
	
	function textName() {
		if ($('#name').val() == '')  return;
		if ($('#isNameCheckComplete').val() == 1) {
			$('#isNameCheckComplete').val("");
		} 
	}
	
	function onkeydownId() {
		if ($('#isIdCheckComplete').val() == 1) {
			document.getElementById("isIdCheckComplete").value = '';
		}
	}
</script>

<form method="post" id="vForm" name="vForm" action="/admin/installer/create.htm">
<input type="hidden" id="isIdCheckComplete" value=""/>
<input type="hidden" id="isNameCheckComplete" value=""/>
<input type="hidden" id="regName" name="regName" />
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- 사용자 ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsUser.installerId"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="regId" name="id" value="${admin.id}" onkeydown="javascript:onkeydownId()">
				<input type="button" id="idCheckBtn" value="ID 중복확인"/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 사용자명 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsUser.installerName"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" id="name" name="name" onkeydown="javascript:textName()" value="${admin.name}">
				<input type="button" id="nameCheckBtn" value="이름 중복확인"/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>		
		<!-- 사용자 패스워드 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.password"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="password" id="passwd" name="passwd" maxlength="16" >
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 사용자 패스워드 확인-->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.passwordCfm"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="password" id="passwdCfm" name="passwdCfm" maxlength="16">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 그룹 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsGroup"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
			<select id="adminGroup" name="adminGroup.id" onchange="afterUserGroupCheck()">
			<c:forEach items="${adminGroupList}" var="adminGroup">
				<c:if test="${adminGroup.id == 2}">
				<option value="${adminGroup.id}">${adminGroup.name}</option>
				</c:if>
			</c:forEach>
			</select>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 핸드폰번호 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsUser.installerPhone"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="mobile" value="${admin.mobile}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.email"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="email" value="${admin.email}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 주소 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.address"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="area" value="${admin.area}" style="width:400px;">
			</td>
		</tr>
		<tr class="line-bottom"><td colspan="3"/></tr>
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