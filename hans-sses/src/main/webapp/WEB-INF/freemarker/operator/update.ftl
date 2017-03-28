<script type="text/javascript">
	$(function() {
				
		// 저장
		$('#save').click(function(e) {
			e.preventDefault();
			var formData = $("#vForm").serialize();
			var url = "/admin/operator/update.json";
			
			$.ajax({
				type : "POST",
				url : url,	
				data : formData,
				success : function(response){
					$("#content").html(response);
				},
				error : function(){
					console.log("error!!");
					//err_page();
					return false;
				}
			});
		});	

		// 비밀번호 초기화
		$("#passwordReset").click(function(e) {
			if(confirm("비밀번호가 '0000' 으로 초기화 됩니다. 초기화 하시겠습니까?") == false) return;
 			$.ajax({
				type:'POST',
				url:'/admin/operator/resetPassword.json?tid=' + $.now(),
				data:{
					id:$("#id").val(),
					password: '0000'				
				},
				success:function (data) {
					alert('비밀번호를 초기화하였습니다.');
				}
			});
		});
		
		$('#savePwd').click(function(e) {
			if (!Boolean($("#password").val())) {
				$("#passwordError").text("비밀번호를 입력해주세요.");
				$("#password").addClass('error');
				return;
			}
			
			if ($("#password").val() == $("#passwordCfm").val()) {
				$.ajax({
					type:'POST',
					url:'/admin/operator/changePassword.json',
					data:{
						id:$("#id").val(),
						password:$("#password").val()					
					},
					success:function (data) {
						$("#password").val("");
						$("#passwordCfm").val("");
						$(".modal.in").modal("hide");
						$("#passwordError").text("");
					}
				});
			} else {
				$("#passwordError").text("비밀번호가 일치하지 않습니다.");
				$("#password").addClass('error');
				return;
			}			
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
	});
	
	function modal_close(){
		$("#password").val("");
		$("#passwordCfm").val("");
		$("#passwordError").text("");
		$(".modal.in").modal("hide");
	}
	
</script>
<form method="post" id="vForm" name="vForm">
<div class="wrap00">
	<!-- input _ start -->
		<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
			<tbody>
			<tr>
				<td style="width:20%">ID</td><td><input type="hidden" id="id" name="id" value="${admin.id}">${admin.id}</td>
			</tr>
			
			<tr>
				<td style="width:20%">비밀번호</td>
				<td>
					<div>
						<button class="btn btn-primary" id="passwordUpdate" type="button"  data-toggle="modal" data-target=".bs-example-modal-md">비밀번호 변경</button>
						<button class="btn btn-success" id="passwordReset" type="button">비밀번호 초기화</button>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>이름</td><td><input type="text" id="name" name="name" value="${admin.name}"></td>
			</tr>
			<tr>
				<td>사용자그룹</td><td><input type="hidden" id="id" name="id" value="${admin.groupName}">${admin.groupName}</td>
			</tr>
			<tr>
				<td>휴대전화</td><td><input type="text" id="mobile" name="mobile" value="${admin.mobile}"></td>
			</tr>
			<tr>
				<td>이메일</td><td><input type="text" id="email" name="email" value="${admin.email}"></td>
			</tr>
			<tr>
				<td>등록일</td><td>${admin.fstRgDt?string("yyyy-MM-dd HH:mm")}</td>
			</tr>
			
			</tbody>
		</table>
	
		<div class="col-md-12 col-sm-6 col-xs-12" align="right">
			<button type="button" class="btn btn-dark" id="save">저장</button>
			<button type="button" class="btn btn-default" id="cancel">취소</button>
		</div>
	<!-- button _ end -->
</div>
</form>

<div class="modal fade bs-example-modal-md in" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-md">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" onClick="javascript:modal_close();"><span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel"> 비밀번호 변경</h4>
            </div>
            
            <div class="modal-body">
            	<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
	        		<tbody>
		        		<tr>
			        		<td style="width:20%"> 비밀번호 </td>
			            	<td>
			              	<input type="password" name="password" id="password" style="width:160px;" maxlength="16"/>
								<label class="error" for="password" id="passwordError" generated="true" style="padding-left:20px; color:red;"></label>
			            	</td>
		            	</tr>
		            	<tr>
		            		<td style="width:20%"> 비밀번호 확인 </td>
		            		<td>
		                		<input type="password" name="passwordCfm" id="passwordCfm" style="width:160px;" maxlength="16"/>
		            		</td>
		            	</tr>
	        		</tbody>
        		</table>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" onClick="javascript:modal_close();">취소</button>
                <button type="button" class="btn btn-primary" id="savePwd">저장</button>
            </div>

        </div>
    </div>
</div>
