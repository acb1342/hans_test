<script type="text/javascript">
$(function() {
	
	$('#save').click(function(e) {
		page_move('/board/notice/update.htm');
	});
	
	$('#cancle').click(function(e) {
		page_move('/board/notice/search.htm');
	});
		
	$('#delete').click(function(e) {
		if (confirm("삭제하시겠습니까?")) page_move('/board/notice/delete.json');
		else return;
	});
	
});
// 페이지 이동
function page_move(url) {
	var formData = $("#vForm").serialize();
	$.ajax({
		type	 :	"GET",
		url		 :	url,
		data	 :	formData,
		success :	function(response){
			if (response == true) {
				page_move('/board/notice/search.htm');
			}
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
	<input type="hidden" name="searchValue" value="${searchValue?if_exists}"/>
	<input type="hidden" name="id" value="${notice.id}"/>
	
	<div class="wrap00" id="wrap00">
		
		<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
			<tbody>
				<tr>
					<td style="width:20%">작성자</td><td>${notice.adminId?if_exists}</td>
				</tr>
				<tr>
					<td>작성일</td><td>${notice.regDate?string('yyyy.MM.dd')}</td>
				</tr>
				<tr>
					<td>제목</td><td>${notice.title}</td>
				</tr>
				<tr>
					<td>내용</td><td>${notice.contents}</td>
				</tr>
				<tr>
					<td>공개여부</td>
					<td>
						<div class="btn-group" data-toggle="buttons">
							<#if notice.displayYn == 'N'> 
								<label class="btn btn-default" disabled>
									<input type="radio" name="displayYn" value="N"> &nbsp;비공개&nbsp;
								</label>
							</#if>
							<#if notice.displayYn == 'Y'>
								<label class="btn btn-default" disabled>
									<input type="radio" name="displayYn" value="Y"> &nbsp;공개&nbsp;
								</label>
							</#if>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		
		<div class="ln_solid"></div>
		<div align="right">
			<button class="btn btn-default" type="button" id="cancle">목록</button>
			<button class="btn btn-dark" type="button" id="save">수정</button>
			<button class="btn btn-danger" type="button" id="delete">삭제</button>
		</div>
		
	</div>
	
</form>
	
