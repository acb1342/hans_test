<script type="text/javascript">
	var passwordUpdated = false;
	
	$(function() {
		
		// 저장
		$('#save').click(function(e) {
			
			e.preventDefault();
			
			var formData = $("#vForm").serialize();
			console.log(formData);
			var url = "/admin/operator/update.htm";
			
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
	});
	
</script>
<form method="post" id="vForm" name="vForm" action="/admin/operator/update.htm" data-parsley-validate class="form-horizontal form-label-left">
<div class="wrap00">
	<!-- input _ start -->
		<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
			<tbody>
			<tr>
				<td style="width:20%">ID</td><td><input type="hidden" id="id" name="id" value="${admin.id}">${admin.id}</td>
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
			<button type="button" class="btn btn-default" onclick="javascript:page_move('/admin/operator/detail.htm','${admin.id}')">취소</button>
		</div>
	<!-- button _ end -->
</div>
</form>