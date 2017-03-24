<script type="text/javascript">
	$(function() {
		// 저장
		$('#save').click(function(e) {
			if ($('#isIdCheckComplete').val() == '' || $('#regId').val() == '') {
				alert("ID 중복확인을 해주세요.");
				return;
			}
			var formData = $("#vForm").serialize();
			var url = "/admin/operator/create.json";
			
			$.ajax({
				type : "POST",
				url : url,
				data : formData,			
				success : function(response){
					$("#content").html(response);
				},
				error : function(){
					console.log("error!!");
					return false;
				}
			});			
		});
		
		// 이전 페이지로 이동
		$('#cancel').click(function(e) {			
			var formData = $("#vForm").serialize();
			if(confirm("취소 하시겠습니까?")) {
				$.ajax({
					type	 :	"POST",
					url		 :	"/admin/operator/search.htm",
					data	 :	formData,
					success :	function(response){
						$("#content").html(response);
					},
					error : function(){
						console.log("error!!");
						//err_page();
						return false;
					}
				});
			}
		});
		// 중복 확인 버튼
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
	
	function onkeydownId() {
		if ($('#isIdCheckComplete').val() == 1) {
			document.getElementById("isIdCheckComplete").value = '';
		}
	}
</script>
<form method="post" id="vForm" name="vForm">
<input type="hidden" id="isIdCheckComplete" value=""/>
<div class="wrap00">

	<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
		<tbody>
		<tr>
			<td style="width:20%">ID</td>
			<td><input type="text" id="regId" name="id" onkeydown="javascript:onkeydownId()">
			<input type="button" id="idCheckBtn" value='중복확인'/>
			</td>
		</tr>
		<tr>
			<td>이름</td>
			<td><input type="text" id="name" name="name">
			</td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" id="passwd" name="passwd" maxlength="16" ></td>
		</tr>
		<tr>
			<td>비밀번호 확인</td>
			<td><input type="password" id="passwdCfm" name="passwdCfm" maxlength="16"></td>
		</tr>
		<tr>
			<td>사용자그룹</td>
			<td>
				<select id="adminGroup" name="adminGroupid">
					<#list adminGroupList as group>
					<option value="${group.id}">${group.name}</option>
					</#list>
				</select>
			</td>
		</tr>
		<tr>
			<td>휴대전화</td><td><input type="text" name="mobile"></td>
		</tr>
		<tr>
			<td>이메일</td><td><input type="text" name="email"></td>
		</tr>
		</tbody>
	</table>

	<div class="col-md-12 col-sm-6 col-xs-12" align="right">
		<button type="button" class="btn btn-dark" id="save">추가</button>
		<button type="button" class="btn btn-default" id="cancel">취소</button>
	</div>
	
	<!-- button _ end -->
</div>
</form>
<#-- <%@ include file="/include/footer.jspf" %> -->