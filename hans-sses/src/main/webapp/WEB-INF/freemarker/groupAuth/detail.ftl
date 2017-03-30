<script type="text/javascript">
$(function() {
	
	$('#save').click(function(e) {
		page_move('/admin/group/update.htm');
	});
	
	$('#cancle').click(function(e) {
		page_move('/admin/group/search.htm');
	});
		
	$('#delete').click(function(e) {
		if (confirm("삭제하시겠습니까?")) page_move('/admin/group/delete.json');
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
				page_move('/admin/group/search.htm');
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
	<input type="hidden" name="searchValue" value="${searchValue?if_exists}"/>
	<input type="hidden" name="id" value="${adminGroup.id}"/>
	
	<div class="wrap00">
	
		<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
			<tbody>
				<tr>
					<td style="width:20%">그룹명</td>
					<td>${adminGroup.name}</td>
				</tr>
				<tr>
					<td>설명</td>
					<td>${adminGroup.description}</td>
				</tr>
				<tr>
					<td>등록일</td>
					<td>${adminGroup.regDate}</td>
				</tr>
				<tr>
					<td>메뉴별 권한</td>
					<td>
						
						<table class="table table-striped responsive-utilities jambo_table dataTable">
						<#list groupAuthList as groupAuth>
								<tr>
									<td style="width:20%">
										<#if groupAuth.parentSeq == 1> <div><b>* ${groupAuth.title}</b></div>
										<#else><div style="padding-left:10%">- ${groupAuth.title}</div>
										</#if>
									</td>
									<td style="width:50%">
										<select class="form-control col-md-7 col-xs-12">
											<option>
												<#if groupAuth.auth??>
													<#if groupAuth.auth == 'N'>권한 없음</#if>
													<#if groupAuth.auth == 'R'>읽기</#if>
													<#if groupAuth.auth == 'RA'>읽기/승인</#if>
													<#if groupAuth.auth == 'RC'>읽기/생성</#if>
													<#if groupAuth.auth == 'RUA'>읽기/수정/승인</#if>
													<#if groupAuth.auth == 'RCU'>읽기/생성/수정</#if>
													<#if groupAuth.auth == 'RCUD'>읽기/생성/수정/삭제</#if>
													<#if groupAuth.auth == 'RCUDA'>읽기/생성/수정/삭제/승인</#if>
												<#else>권한 없음
												</#if>
											</option>
										</select>
									</td>
								</tr>
								<tr class="ln_solid"/>
							</#list>
						</table>
						
					</td>
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
