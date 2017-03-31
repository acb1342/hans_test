<script type="text/javascript">
	$(function() {
		checkRadio();
		
		$('#radioY').click(function() {
			$("#radioY").prop("class","iradio_flat-green checked");
			$("#radioN").prop("class","iradio_flat-green");
			$("input:radio[id='displayY']").prop("checked", true);
			$("input:radio[id='displayN']").prop("checked", false);
		});
		
		$('#radioN').click(function() {
			$("#radioN").prop("class","iradio_flat-green checked");
			$("#radioY").prop("class","iradio_flat-green");
			$("input:radio[id='displayN']").prop("checked", true);
			$("input:radio[id='displayY']").prop("checked", false);
		});
		
		$('#save').click(function(e) {	
			if(confirm("수정하시겠습니까?")) page_move('/board/notice/update.htm');
			else return;
		});
		
		$('#cancle').click(function(e) {	
			if(confirm("취소하시겟습니까?")) page_move('/board/notice/detail.htm');
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
	
	// 로딩 시 라디오버튼 체크
	function checkRadio() {
		var displayYn = $('#displayYn').val();
		if (displayYn == 'Y') $("#radioY").prop("class","iradio_flat-green checked");
		if (displayYn == 'N') $("#radioN").prop("class","iradio_flat-green checked");
	}
</script>

	<form id="vForm" name="vForm">
		<input type="hidden" name="page" value="${page?if_exists}"/>
		<input type="hidden" name="searchType" value="${searchType?if_exists}"/>
		<input type="hidden" name="searchValue" value="${searchValue?if_exists}"/>
		<input type="hidden" name="id" value="${notice.id?if_exists}"/>
		<input type="hidden" id="displayYn" value="${notice.displayYn?if_exists}"/>
		
		<div class="wrap00">
			<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
				<tbody>
					<tr>
						<td style="width:20%">작성자</td>
						<td><input class="form-control col-md-7 col-xs-12" type="text" name="adminId" readonly="readonly" value="${userId}"></td>
					</tr>
					<tr>
						<td>작성일</td>
						<td><input class="form-control col-md-7 col-xs-12" type="text" readonly="readonly" value="${date?string('yyyy.MM.dd')}"></td>
					</tr>
					<tr>
						<td>제목</td>
						<td><input class="form-control col-md-7 col-xs-12" type="text" name="title" value="${notice.title?if_exists}"></td>
					</tr>
					<tr>
						<td>내용</td>
						<td><textarea class="form-control col-md-7 col-xs-12" name="contents">${notice.contents?if_exists}</textarea></td>
					</tr>
					<tr>
						<td>공개여부</td>
						<td>
							<div class="iradio_flat-green" style="position: relative;" id="radioN">
								<input type="radio" class="flat" id="displayN" name="displayYn" value="N" style="position: absolute; opacity: 0;">
							</div>&nbsp;비공개&nbsp;
							<div class="iradio_flat-green" style="position: relative;" id="radioY">
								<input type="radio" class="flat" id="displayY" name="displayYn" value="Y" style="position: absolute; opacity: 0;">
							</div>&nbsp;공개&nbsp;
						</td>
					</tr>
				</tbody>
			</table>

			<div align="right">
				<button class="btn btn-dark" type="button" id="save">저장</button>
				<button class="btn btn-danger" type="button" id="cancle">취소</button>
			</div>
			
		</div>
	</form>
	