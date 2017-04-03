<script src="/css/gentelella-master/vendors/jquery/dist/jquery.min.js"></script>
<script src="/css/gentelella-master/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="/css/gentelella-master/vendors/moment/min/moment.min.js"></script>
<script src="/css/gentelella-master/vendors/bootstrap-daterangepicker/daterangepicker.js"></script>
<script type="text/javascript">
	$(function() {
		$('#selDate').daterangepicker({
            singleDatePicker: true,
            singleClasses: "picker_3",
            locale : {
                direction: "kr",
                format: "YYYY-MM-DD"
            }
        }, function(start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
        });
		
		$('#save').click(function(e) {	
			if(confirm("등록하시겠습니까?")) page_move('/board/appVer/create.htm');
			else return;
		});
		
		$('#cancle').click(function(e) {	
			if(confirm("취소하시겟습니까?")) page_move('/board/appVer/search.htm');
			else return;
		});
		
	});
	
	// 페이지 이동
	function page_move(url) {
		var formData = $("#vForm").serialize();
		$.ajax({
			type	 :	"POST",
			url		 :	url,
			data	 :	formData,
			success :	function(response){
				$("#content").html(response);
				window.scrollTo(0,0);
			},
			error : function(){
				console.log("error!!");
				//err_page();
				return false;
			}
		});
	}
</script>

	<form id="vForm" name="vForm">
	<input type="hidden" name="page" value="${page?if_exists}"/>
	<input type="hidden" name="searchType" value="${searchType?if_exists}"/>
			
	<div class="wrap00">
	
		<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
			<tbody>
				<tr>
					<td style="width:20%">OS</td>
					<td>
						<select class="form-control col-md-7 col-xs-12" name="os">
							<option value="301401">ANDROID</option>
							<option value="301402">IOS</option>
							<option value="301403">PC</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>필수 여부</td>
					<td>
						<select class="form-control col-md-7 col-xs-12" name="updateType">
							<option value="605101">필수</option>
							<option value="605102">선택</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>Version</td>
					<td><input type="text" class="form-control col-md-7 col-xs-12" name="ver"></td>
				</tr>
				<tr>
					<td>업데이트 내용</td>
					<td><textarea class="form-control col-md-7 col-xs-12" name="content"></textarea></td>
				</tr>
				<tr>
					<td>배포 예정일시</td>
					<td>
						<span>
							<input style="width:20%;" class="col-xs-12" type="text" id="selDate" name="selDate" readonly>
							<select style="width:15%;" class="form-control col-md-7 col-xs-12" name="hour">
								<#list 0..23 as hour>
									<option value="${hour}">${hour}시</option>
								</#list>
							</select>
							<select style="width:15%;" class="form-control col-md-7 col-xs-12" name="minute">
								<#list 0..50 as minute>
									<#if minute%10 == 0>
										<option value="${minute}">${minute}분</option>
									</#if>
								</#list>
							</select>
						</span>
					</td>
				</tr>
				<tr>
					<td>바이너리 파일(URL)</td>
					<td><input type="text" class="form-control col-md-7 col-xs-12" name="url"></td>
				</tr>
			</tbody>
		</table>
			
				
		<div align="right">
			<button class="btn btn-dark" type="button" id="save">등록</button>
			<button class="btn btn-danger" type="button" id="cancle">취소</button>
		</div>
		
	</div>
		
</form>