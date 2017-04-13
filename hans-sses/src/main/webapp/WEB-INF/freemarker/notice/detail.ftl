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
	<input type="hidden" id="id" name="id" value="${notice.id}"/>
	
	<div class="wrap00" id="wrap00">
		
		<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
			<tbody>
				<tr>
					<td style="width:20%">작성자</td><td>${notice.adminId?if_exists}</td>
				</tr>
				<tr>
					<td>등록일</td><td>${notice.regDate?string("yyyy-MM-dd")}</td>
				</tr>
				<tr>
					<td>제목</td><td>${notice.title}</td>
				</tr>
				<tr>
					<td>내용</td><td>${notice.contents?if_exists}</td>
				</tr>
				<tr>
					<td>공개여부</td>
					<td>
						<#if notice.displayYn == 'N'>
							<input type="hidden" name="displayYn" value="N">&nbsp;비공개&nbsp;
						</#if>
						<#if notice.displayYn == 'Y'>
							<input type="hidden" name="displayYn" value="Y">&nbsp;공개&nbsp;
						</#if>
					</td>
				</tr>
				<tr>
					<td>파일 업로드</td>
					<td><#if notice.fileName??><a href="#" onclick="location.href='/board/notice/downFile.htm?id=${notice.id}'">${notice.fileName}</a></#if></td>
				</tr>
			</tbody>
		</table>
		
		<div align="right">
			<button class="btn btn-default" type="button" id="cancle">목록</button>
			<button class="btn btn-dark" type="button" id="save">수정</button>
			<button class="btn btn-danger" type="button" id="delete">삭제</button>
		</div>
		
	</div>
</form>
	
