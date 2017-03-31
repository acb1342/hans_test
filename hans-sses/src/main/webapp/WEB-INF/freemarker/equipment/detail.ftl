<script type="text/javascript">
	$(function() {
	});

	function confirmAndDelete(equip_seq) {
		if(confirm("삭제 하시겠습니까?")) {
            var formData = {
                equip_seq : equip_seq
            };

			$.ajax({
                type		: "POST",
				url			: "/member/equipment/delete.json",
                contentType	: "application/json",
                data		: JSON.stringify(formData),
				success:function(isDelete) {
					if (isDelete) {
						console.log("delete success")
						alert("삭제 되었습니다.")
                        page_move('/member/equipment/search.htm');
					} else {
						console.log("delete fail")
						alert("삭제 실패하였습니다.");
					}
				}
			});
		}
	}
</script>
<div id="wrap00" style="padding-top: 20px;">
	<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
		<tbody>	
		<tr>
			<td style="width:20%">장비명</td><td>${equipment.name}</td>
		</tr>
		<tr>
			<td>제조사</td><td>${equipment.manufacturer}</td>
		</tr>
		<tr>
			<td>제조년도</td><td>${equipment.make_date?date("yyyyMMdd")}</td>
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
</div>