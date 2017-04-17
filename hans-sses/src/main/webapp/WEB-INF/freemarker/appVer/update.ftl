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
                format: "YYYY-MM-DD",
                monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	            daysOfWeek: ['일','월', '화', '수', '목', '금', '토']
            }
        }, function(start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
        });
		
		$('#save').click(function(e) {
			if( !validator.checkAll($("#vForm")) ) return;
			if(confirm("수정하시겠습니까?")) page_move('/board/appVer/update.htm');
			else return;
		});
		
		$('#cancle').click(function(e) {	
			if(confirm("취소하시겟습니까?")) page_move('/board/appVer/detail.htm');
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
		<input type="hidden" name="id" value="${appVer.id}"/>
		
	<div class="wrap00">
			
		<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
			<tbody>
				<tr>
					<td style="width:20%">OS</td>
					<td>
						<select class="form-control col-md-7 col-xs-12" name="os">
							<option value="301401" <#if appVer.os == '301401'> selected=""</#if>>ANDROID</option>
							<option value="301402" <#if appVer.os == '301402'> selected=""</#if>>IOS</option>
							<option value="301403" <#if appVer.os == '301403'> selected=""</#if>>PC</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>필수 여부</td>
					<td>
						<select class="form-control col-md-7 col-xs-12" name="updateType">
							<option value="605101" <#if appVer.updateType == '605101'> selected=""</#if>>필수</option>
							<option value="605102" <#if appVer.updateType == '605102'> selected=""</#if>>선택</option>
						</select>
					</td>
				</tr>
				<tr class="item">
					<td>Version</td>
					<td><input type="text" class="form-control col-md-7 col-xs-12" id="ver" name="ver" required="required" value="${appVer.ver?if_exists}"></td>
				</tr>
				<tr>
					<td>업데이트 내용</td>
					<td><textarea class="form-control col-md-7 col-xs-12" name="content">${appVer.content?if_exists}</textarea></td>
				</tr>
				<tr>
					<td>배포 예정일시</td>
					<td>
						<span>
							<input style="width:20%;" class="col-xs-12" type="text" id="selDate" name="selDate" value="${appVer.deployYmd?if_exists}" readonly>
							<select style="width:15%;" class="form-control col-md-7 col-xs-12" name="hour">
								<#list 0..23 as hour>
									<option value="${hour}" <#if appVer.beforeHour??><#if appVer.beforeHour == hour>selected</#if></#if>>${hour}시</option>
								</#list>
							</select>
							<select style="width:15%;" class="form-control col-md-7 col-xs-12" name="minute">
								<#list 0..50 as minute>
									<#if minute%10 == 0>
										<option value="${minute}" <#if appVer.beforeMinute??><#if appVer.beforeMinute == minute>selected</#if></#if>>${minute}분</option>
									</#if>
								</#list>
							</select>
						</span>
					</td>
				</tr>
				<tr class="item">
					<td>바이너리 파일(URL)</td>
					<td><input type="text" class="form-control col-md-7 col-xs-12" id="url" name="url" required="required" value="${appVer.url?if_exists}"></td>
				</tr>
			</tbody>
		</table>
			
		<div align="right">
			<button class="btn btn-dark" type="button" id="save">수정</button>
			<button class="btn btn-danger" type="button" id="cancle">취소</button>
		</div>
			
	</div>
</form>