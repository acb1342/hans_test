<script type="text/javascript">
	$(function() {
	});

	function confirmAndDelete(id) {
		if(confirm("삭제 하시겠습니까?")){
			$.ajax({
				url:"/admin/operator/delete.json",
				type:"POST",
				data:{
					id:id
				},
				success:function(isDelete) {
					if (isDelete) {
						console.log("delete success")
						alert("삭제 되었습니다.")
						page_move('/admin/operator/search.htm');
					} else {
						console.log("delete fail")
						alert("삭제 실패하였습니다.");
					}
				}
			});
		}
	}
	
	// 이전 페이지로 이동
	$('#cancel').click(function(e) {
		//window.location.href = "/admin/operator/search.htm";
		
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
	
</script>

<div id="wrap00" style="padding-top: 20px;">
	<!-- list _ start -->
	
	<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
		<tbody>	
		<tr>
			<td style="width:20%">ID</td><td>${admin.id}</td>
		</tr>
		<tr>
			<td>이름</td><td>${admin.name}</td>
		</tr>
		<tr>
			<td>사용자그룹</td><td>${admin.groupName}</td>
		</tr>
		<tr>
			<td>휴대전화</td><td>${admin.mobile}</td>
		</tr>
		<tr>
			<td>이메일</td><td>${admin.email}</td>
		</tr>
		<tr>
			<td>등록일</td><td>${admin.fstRgDt?string("yyyy-MM-dd")}</td>
		</tr>
		
		</tbody>
	
	</table>
	<div align="right">
		<button type="button" class="btn btn-dark" onclick="javascript:page_move('/admin/operator/update.htm','${admin.id}')">수정</button>
		<button type="button" class="btn btn-danger" onclick="javascript:confirmAndDelete('${admin.id}')">삭제</button>
		<button type="button" class="btn btn-default" onclick="javascript:page_move('/admin/operator/search.htm')">목록</button>
	</div>
		
	<!-- button _ end -->
</div>
