<script type="text/javascript">
	$(function() {
	});

	function confirmAndDelete(equip_seq) {
		if(confirm("삭제 하시겠습니까?")){
			$.ajax({
				url:"/member/equipment/delete.json",
				type:"POST",
				data:{
					id:id
				},
				success:function(isDelete) {
					if (isDelete) {
						console.log("delete success")
						alert("삭제 되었습니다.")
						history_0();
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
		var formData = $("#vForm").serialize();
		if(confirm("취소 하시겠습니까?")) {
			$.ajax({
				type	 :	"POST",
				url		 :	"/member/equipment/search.htm",
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

	function history_0() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		
		var url;
		if (callbackUrl != 'null') {
			url = "/member/equipment/search.htm?" + callbackUrl;
		} else {
			url = "/member/equipment/search.htm";
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

<div id="wrap00" style="padding-top: 20px;">
	<!-- list _ start -->
	
	<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
		<tbody>	
		<tr>
			<td style="width:20%">장비명</td><td>${equipment.name}</td>
		</tr>
		<tr>
			<td>제조사</td><td>${equipment.manufacturer}</td>
		</tr>
		<tr>
			<td>제조년도</td><td>${equipment.make_date?string("yyyy-MM-dd HH:mm")}</td>
		</tr>
		<tr>
			<td>기타</td><td>${equipment.etc}</td>
		</tr>
		<tr>
			<td>소비전력</td><td>${equipment.elect_power}</td>
		</tr>
		<tr>
			<td>등록일</td><td>${equipment.reg_date?string("yyyy-MM-dd HH:mm")}</td>
		</tr>
        <tr>
            <td>수정일</td><td><#if equipment.mod_date?exists>${equipment.mod_date?string("yyyy-MM-dd HH:mm")}</#if></td>
        </tr>

		</tbody>
	
	</table>
	<div align="right">
		<button type="button" class="btn btn-dark" onclick="javascript:page_move('/member/equipment/update.htm','${equipment.equip_seq}')">수정</button>
		<button type="button" class="btn btn-danger" onclick="javascript:confirmAndDelete('${equipment.equip_seq}')">삭제</button>
		<button type="button" class="btn btn-default" onclick="javascript:page_move('/member/equipment/search.htm')">목록</button>
		
	</div>
		
	<!-- button _ end -->
</div>
