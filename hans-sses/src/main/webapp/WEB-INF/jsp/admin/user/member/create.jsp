<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		// 저장
		$('#save').click(function(e) {
			e.preventDefault();
			$("#vForm").submit();
		});

		// 유효성 체크
		$.validator.addMethod("passwordConfirm", function(value, element) {
			return $("#password").val() == $("#passwordCfm").val();
		},"비밀번호가 일치하지 않습니다.");
		
		$("#vForm").validate({
			onfocusout:false,
			onkeyup:false,
			rules:{
				id:{
					required:true,
					remote:{
						type:"POST",
						url:"/admin/user/checkid.json"
					}
				},
				password:{
					required:true,
					passwordConfirm:true
				},
				passwordCfm:{
					required:true,
					passwordConfirm:true
				},
				homePhone:"number",
				mobilePhone:"required number",
				officePhone:"number",
				email:"required email"
			},
			messages:{
				id:{
					required:'<fmt:message key="validate.required"/>',
					remote:'<fmt:message key="validate.duplicated.id"/>'
				},
				email:{
					required:'<fmt:message key="validate.required"/>',
					email:'<fmt:message key="validate.email"/>'
				},
				password:{
					required:'<fmt:message key="validate.required"/>'
				},
				homePhone:{
					number:'<fmt:message key="validate.number"/>'
				},
				mobilePhone:{
					required:'<fmt:message key="validate.required"/>',
					number:'<fmt:message key="validate.number"/>'
				},
				officePhone:{
					number:'<fmt:message key="validate.number"/>'
				}
			}
		});
	});
	
	// 이전 페이지로 이동
	function cancel() {
		window.location.href = "/admin/user/detail.htm?id=${defaultUser.id}";
	}
</script>
<spring:hasBindErrors name="cmsUser"/>
<form method="post" id="vForm" name="vForm" action="/admin/user/member/create.htm">
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="3"/></tr>
		<!-- Default ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsUser.id"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<table>
					<tr>
						<td>
							<input type="hidden" name="defaultUserId" id="defaultUserId" value="${defaultUser.id}"/>
							&nbsp;${defaultUser.id}
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 사용자 ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsUser.id"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<table>
					<tr>
						<td>
							<input type="text" name="id">&nbsp;&nbsp;
						</td>
					</tr>
				</table>
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
				<input type="password" id="password" name="password" maxlength="16">
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
				<input type="password" id="passwordCfm" name="passwordCfm" maxlength="16">
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
			<input type="hidden" id="cmsGroup" name="cmsGroup.id" value="${defaultUser.cmsGroup.id}"/>
			&nbsp;${defaultUser.cmsGroup.name}
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 사용자명 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.cmsUser.name"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="name" value="${defaultUser.name}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 집전화번호 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.homePhone"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="homePhone" value="${defaultUser.homePhone}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 핸드폰번호 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.mobilePhone"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="mobilePhone" value="${defaultUser.mobilePhone}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 사무실번호 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.officePhone"/>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="officePhone" value="${defaultUser.officePhone}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="3"/></tr>
		<!-- 이메일 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.email"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<input type="text" name="email" value="${defaultUser.email}">
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
				<input type="text" name="address" value="${defaultUser.address}" style="width:400px;">
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