<script type="text/javascript">
	$(function() {
		
		$('#save').click(function(e) {	
			if( !validator.checkAll( $('#vForm') ) ) {
				$('#name').focus();
				return;
			}
			if(confirm("등록하시겠습니까?")) page_move('/admin/group/create.htm');
			else return;
		});
		
		$('#cancle').click(function(e) {	
			if(confirm("취소하시겟습니까?")) page_move('/admin/group/search.htm');
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
	<input type="hidden" name="searchValue" value="${searchValue?if_exists}"/>
	
	<div class="wrap00">
		
		<table class="table table-hover">
			<tbody>
				<tr class="item">
					<td style="width:20%">그룹명</td>
					<td><input class="form-control col-md-7 col-xs-12" type="text" id="name" name="name" value="" required="required"></td>
				</tr>
				<tr>
					<td>설명</td>
					<td><input class="form-control col-md-7 col-xs-12" type="text" name="description" value=""></td>
				</tr>
				<tr>
					<td>등록일</td>
					<td>${date?string('yyyy-MM-dd')}</td>
				</tr>
				<tr>
					<td style="vertical-align:top">메뉴별 권한</td>
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
										<select name="${groupAuth.menuSeq}" class="form-control col-md-7 col-xs-12">
											<option value="N">권한 없음</option>
											<option value="R">읽기</option>	
											<option value="RA">읽기/승인</option>
											<option value="RC">읽기/생성</option>
											<option value="RUA">읽기/수정/승인</option>
											<option value="RCU">읽기/생성/수정</option>
											<option value="RCUD">읽기/생성/수정/삭제</option>
											<option value="RCUDA">읽기/생성/수정/삭제/승인</option>
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
			<button class="btn btn-dark" type="button" id="save">등록</button>
			<button class="btn btn-danger" type="button" id="cancle">취소</button>
		</div>
		
	</div>
	
</form>