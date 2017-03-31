<script type="text/javascript">
	$(function() {
				
		// 저장
		$('#save').click(function(e) {
            if ($('#name').val() == '') {
                alert("장비명을 입력해 주세요.");
                return;
            }

            if ($('#manufacturer').val() == '') {
                alert("제조사를 입력해 주세요.");
                return;
            }

            if ($('#make_date').val() == '') {
                alert("제조일을 입력해 주세요.");
                return;
            }

            if ($('#etc').val() == '') {
                alert("기타를 입력해 주세요.");
                return;
            }

            if ($('#elect_power').val() == '') {
                alert("소비전력을 입력해 주세요.");
                return;
            }

            var formData = {
                name : $('#name').val(),
                manufacturer : $('#manufacturer').val(),
                make_date : $('#make_date').val(),
                etc : $('#etc').val(),
                elect_power : $('#elect_power').val()
            };

			var url = "/member/equipment/update.json";
			
			$.ajax({
				type : "POST",
				url : url,
                contentType : "application/json",
                data : JSON.stringify(formData),
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
					url		 :	"/member/equipment/search.htm",
					data	 :	formData,
					success :	function(response){
						$("#content").html(response);
					},
					error : function(){
						console.log("error!!");
						return false;
					}
				});
			}
		});
	});
	
</script>
<form method="post" id="vForm" name="vForm">
<div class="wrap00">
	<!-- input _ start -->
    <table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
        <tbody>
        <tr>
            <td>장비명</td>
            <td><input type="text" id="name" name="name" value="${equipment.name}"></td>
        </tr>
        <tr>
            <td>제조사</td>
            <td><input type="text" id="manufacturer" name="manufacturer" value="${equipment.manufacturer}"></td>
        </tr>
        <tr>
            <td>제조년도</td>
            <td><input type="text" id="make_date" name="make_date" value="${equipment.make_date?string("yyyy-MM-dd HH:mm")}"></td>
        </tr>
        <tr>
            <td>기타</td>
            <td><input type="text" id="etc" name="etc" value="${equipment.etc}"></td>
        </tr>
        <tr>
            <td>소비전력</td>
            <td><input type="text" id="elect_power" name="elect_power" value="${equipment.elect_power}"></td>
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