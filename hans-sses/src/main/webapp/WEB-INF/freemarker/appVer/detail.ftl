<script type="text/javascript">
$(function() {
	
	$('#save').click(function(e) {
		page_move('/board/appVer/update.htm');
	});
	
	$('#cancle').click(function(e) {
		page_move('/board/appVer/search.htm');
	});
	
	$('#delete').click(function(e) {
		if (confirm("삭제하시겠습니까?")) page_move('/board/appVer/delete.json');
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
				page_move('/board/appVer/search.htm');
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
	<input type="hidden" name="id" value="${appVer.id}"/>
			
	<div class="wrap00" id="wrap00">
	
		<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
			<tbody>
				<tr>
					<td style="width:20%">OS</td>
					<td>
						<#if appVer.os == '301401'> <#assign strOs="ANDROID"> </#if>
						<#if appVer.os == '301402'> <#assign strOs="IOS"> </#if>
						<#if appVer.os == '301403'> <#assign strOs="PC"> </#if>
						${strOs?if_exists}
					</td>
				</tr>
				<tr>
					<td>필수 여부</td>
					<td>
						<#if appVer.updateType == '605101'> <#assign strType="필수"> </#if>
					 	<#if appVer.updateType == '605102'> <#assign strType="선택"> </#if>
						${strType?if_exists}
					</td>
				</tr>
				<tr>
					<td>Version</td>
					<td>${appVer.ver?if_exists}</td>
				</tr>
				<tr>
					<td>등록일</td>
					<td>${appVer.regDate?string('yyyy.MM.dd')}</td>
				</tr>
				<tr>
					<td>업데이트 내용</td>
					<td>${appVer.content?if_exists}</td>
				</tr>
				<tr>
					<td>배포 예정일시</td>
					<td>${appVer.deployYmd?if_exists}</td>
				</tr>
				<tr>
					<td>바이너리 파일(URL)</td>
					<td>${appVer.url?if_exists}</td>
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

