<script type="text/javascript">
	$(function() {
		
		$('#save').click(function(e) {	
			if(confirm("등록하시겠습니까?")) {
				var contents = $('#textEditor').html();
				$('#contents').val(contents);
				page_move('/board/notice/create.htm');
			}
			else return;
		});
		
		$('#cancle').click(function(e) {	
			if(confirm("취소하시겟습니까?")) page_move('/board/notice/search.htm');
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
	
	function setCompanySelect() {
		$('#companySelect').find("option").remove();
		$('#companySelect').append("<option value='' selected=''> === 부서명 === </option>");
		$('#userSelect').find("option").remove();
		$('#userSelect').append("<option value='' selected=''> === 사용자명 === </option>");
		
		if ($('#parentCompanySelect').val() == '') return;
		$.ajax({
			type:'POST',
			url:'/member/userEq/setCompanySelect.json',
			data:{
				parentCompanySeq:$('#parentCompanySelect').val()					
			},
			success:function (data) {
					if(data.length > 0) {
						for(var i=0; i<data.length; i++) {
							$('#companySelect').append("<option value='" + data[i].companySeq + "'>" + data[i].companyName + "</option>");
						}
					}
				},
			error: function(e) {
				alert("error!!!");
			}
		});
	}
	
	function setUserSelect() {
		$('#userSelect').find("option").remove();
		$('#userSelect').append("<option value='' selected=''> === 사용자명 === </option>");
		
		if ($('#companySelect').val() == '') return;
		$.ajax({
			type:'POST',
			url:'/member/userEq/setUserSelect.json',
			data:{
				companySeq:$('#companySelect').val()					
			},
			success:function (data) {
					if(data.length > 0) {
						for(var i=0; i<data.length; i++) {
							$('#userSelect').append("<option value='" + data[i].userSeq + "'>" + data[i].userName + "</option>");
						}
					}
				},
			error: function(e) {
				alert("error!!!");
			}
		});
	}
	
</script>

	<form id="vForm" name="vForm">
	<input type="hidden" name="page" value="${page?if_exists}"/>
	<input type="hidden" name="searchType" value="${searchType?if_exists}"/>
	<input type="hidden" name="searchValue" value="${searchValue?if_exists}"/>
	<input type="hidden" id="contents" name="contents" value=""/>
	
	<div class="wrap00">
		
		<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
			<tbody>
				<tr>
					<td style="width:20%">회사명</td>
					<td>
						<select class="form-control col-md-7 col-xs-12" id="parentCompanySelect" name="parentCompanySelect" onchange="setCompanySelect()">
							<option value="" selected=""> === 회사명 ===</option>
							<#list parentCompanyList as parentCompany>
								<option value="${parentCompany.companySeq}">${parentCompany.companyName}</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<td>부서명</td>
					<td>
						<select class="form-control col-md-7 col-xs-12" id="companySelect" name="companySelect" onchange="setUserSelect()">
							<option value="" selected=""> === 부서명 ===</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>사용자명</td>
					<td>
						<select class="form-control col-md-7 col-xs-12" id="userSelect" name="userSelect">
							<option value="" selected=""> === 사용자명 ===</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>장비 목록</td>
					<td>
						<input type="checkbox" id="selected" name="selected" value=""/>
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