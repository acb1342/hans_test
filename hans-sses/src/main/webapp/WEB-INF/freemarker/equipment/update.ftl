<script src="/css/gentelella-master/vendors/jquery/dist/jquery.min.js"></script>
<script src="/css/gentelella-master/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="/css/gentelella-master/vendors/moment/min/moment.min.js"></script>
<script src="/css/gentelella-master/vendors/bootstrap-daterangepicker/daterangepicker.js"></script>
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

            if ($('#watt').val() == '') {
                alert("소비전력을 입력해 주세요.");
                return;
            }

            if ($('#wattInfoSelect').val() == '') {
                alert("전력요금을 선택해 주세요.");
                return;
            }

            var make_date = $('#make_date').val().replace(/\-/g, "");

            var formData = {
                macaddress : $('#macaddress').val(),
                name : $('#name').val(),
                manufacturer : $('#manufacturer').val(),
                make_date : make_date,
                watt : $('#watt').val(),
                charge : $('#wattInfoSelect').val()
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

        $('#make_date').daterangepicker({
            singleDatePicker: true,
            singleClasses: "picker_3",
            locale : {
                format: "YYYY-MM-DD",
                monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	            daysOfWeek: ['일','월', '화', '수', '목', '금', '토']
            },
            startDate: "${equipment.make_date}"
        }, function(start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
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
    <table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
        <tbody>
        <tr>
            <td style="width:20%">Mac Address</td>
            <td><input type="text" class="form-control col-md-7 col-xs-12" id="macaddress" name="macaddress" value="${equipment.macaddress}"></td>
        </tr>
        <tr>
            <td>장비명</td>
            <td><input type="text" class="form-control col-md-7 col-xs-12" id="name" name="name" value="${equipment.name}"></td>
        </tr>
        <tr>
            <td>제조사</td>
            <td><input type="text" class="form-control col-md-7 col-xs-12" id="manufacturer" name="manufacturer" value="${equipment.manufacturer}"></td>
        </tr>
        <tr>
            <td>제조년</td>
            <td><input type="text" class="form-control col-md-7 col-xs-12" id="make_date" name="make_date" value="${equipment.make_date}"></td>
        </tr>
        <tr>
            <td>소비전력</td>
            <td><input type="text" class="form-control col-md-7 col-xs-12" id="watt" name="watt" value="${equipment.watt}"></td>
        </tr>
        <tr>
            <td>소비전력요금</td>
            <td>
            <#assign charge = equipment.charge?if_exists?number>
                <select class="form-control col-md-7 col-xs-12" id="wattInfoSelect" name="wattInfoSelect" required="required">
                    <option value="" selected=""> === 전력요금 ===</option>
                <#list wattInfoList as wattInfo>
                    <option value="${wattInfo.charge}" <#if wattInfo.charge == charge> selected = "selected"</#if>>${wattInfo.charge} [${wattInfo.code}]</option>
                </#list>
                </select>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="col-md-12 col-sm-6 col-xs-12" align="right">
        <button type="button" class="btn btn-dark" id="save">저장</button>
        <button type="button" class="btn btn-default" id="cancel">취소</button>
    </div>
</div>

</form>