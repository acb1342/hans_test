<script type="text/javascript">
	
</script>

<form id="vForm" name="vForm">
	<input type="hidden" name="page" value="${page?if_exists}"/>
	<input type="hidden" name="searchType" value="${searchType?if_exists}"/>
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
					<td>권한</td>
					<td>
					
						
						
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
