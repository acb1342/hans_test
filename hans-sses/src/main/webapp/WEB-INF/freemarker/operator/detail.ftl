<script type="text/javascript">
	$(function() {
	});

	function confirmAndDelete(id) {
		$.ajax({
			url:"/admin/operator/delete.json",
			type:"POST",
			data:{
				id:id
			},
			success:function(isDelete) {
				if (isDelete) {
					console.log("delete success")
					alert("삭제 성공")
					history_0();
				} else {
					console.log("delete fail")
					alert("삭제 실패");
				}
			}
		});
	}

	// 검색 페이지로 이동
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			location.href = "/admin/operator/search.htm?" + callbackUrl;
		} else {
			location.href = "/admin/operator/search.htm";
		}
	}

	function history_0(){
		//$("#content").load("/admin/operator/detail.htm?id="+id);
		
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		
		var url;
		if (callbackUrl != 'null') {
			url = "/admin/operator/search.htm?" + callbackUrl;
		} else {
			url = "/admin/operator/search.htm";
		}
		console.log("url = "+ url);
		
		$.ajax({
			type : "GET",
			url : url,	
			success : function(response){
				$("#content").html(response);
			},
			error : function(){
				console.log("error!!");
				//err_page();
				return false;
			}
		});		
	}
	
	
</script>

<form  data-parsley-validate class="form-horizontal form-label-left">
<div class="wrap00" id="wrap00">
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
			<td>등록일</td><td>${admin.fstRgDt?string("yyyy-MM-dd HH:mm")}</td>
		</tr>
		
		</tbody>
	
	</table>
	
	<div class="col-md-2" align="left">
		<button type="button" class="btn btn-default" onclick="javascript:history_0()">목록</button></div> 
	<div class="col-md-10" align="right">
		<button type="button" class="btn btn-dark" onclick="javascript:page_move('/admin/operator/update.htm','${admin.id}')">수정</button>
		<button type="button" class="btn btn-danger" onclick="javascript:confirmAndDelete('${admin.id}')">삭제</button>
	</div> 

		
	<!-- button _ end -->
</div>

</form>