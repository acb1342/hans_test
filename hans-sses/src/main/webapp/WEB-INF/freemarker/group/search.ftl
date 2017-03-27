<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>SSES</title>
<script type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript" src="/js/jquery/alert/jquery.alerts.custom.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript">
	$(function() {
		paging();
	});
	
	// 화면 하단 페이지 리스트 생성
	function paging() {
		var step;
		var cnt = $('#countAll').val();
		var lastPage = parseInt(cnt/10);
		var namuzi = cnt%10;
		
		if (namuzi > 0) lastPage = lastPage + 1;
		
		if (lastPage < 1 && namuzi == 0) return;
		
		document.getElementById('lastPage').value = lastPage;
		
		for (step = 1; step <= lastPage; step++) {
			var id = "p" + step;
			var html = "<a class='paginate_button' id='" + id + "' onclick='javascript:searchList(\"\"," + step + ");'>" + step + "</a>";
			$('#paging_span').append(html);
		}
		
		// 현재 페이지번호 class 변경
		var currPage = $('#currPage').val();
		var id = "p" + currPage;
		document.getElementById(id).className = "paginate_active";
	}
	
	// 검색
	function searchList(pageType, pageNum) {
		var currPage = parseInt(document.getElementById('currPage').value);
		var lastPage = parseInt(document.getElementById('lastPage').value);
		
		if (typeof pageNum == 'number') document.getElementById('currPage').value = pageNum;
		else if (pageType != null && pageType != '') {
			if (pageType == "First") pageNum = 1;
			if (pageType == "Previous") pageNum = currPage - 1;
			if (pageType == "Next") pageNum = currPage + 1;
			if (pageType == "Last") pageNum = lastPage;
			
			if (pageNum == 0 || (pageType == "First" && currPage <= 1) || pageNum > lastPage || (pageType == "Last" && currPage == lastPage)) return;
			
			document.getElementById('currPage').value = pageNum; 
		}
		
		page_move('/admin/group/search.htm');
	}
	
	// 페이지 이동
	function page_move(url, id) {
		var formData = $("#vForm").serialize() + "&id=" + id;
		$.ajax({
			type	 :	"GET",
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
<style type="text/css">
    .table>tbody>tr>td{vertical-align:middle;}
</style>
</head>
<body>

	<div class="x_content">
		<form method="get" id="vForm" name="vForm" onsubmit="return false;">			 
			<#assign searchValue='${RequestParameters.searchValue!""}'>
			<input type="hidden" id="countAll" value="${countAll}"/>
			<input type="hidden" id="currPage" name="page" value="${page}"/>
			<input type="hidden" id="lastPage" name="lastPage" value="${lastPage?if_exists}"/>
			
			<div style="margin:1% 0 1% 0;" class="col-sm-4" style="width:30%;" >
				<input type="text" class="form-control" name="searchValue" id="searchValue" value="${searchValue}" placeholder="그룹명"/>
			</div>
			<div style="margin:1% 0 1% 0;" class="col-sm-2">
				<input type="button" class="btn btn-dark" value="검색" onclick="javascript:searchList('',1)"/>
			</div>
				
			<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info" style="text-align:left;">
				<thead>
					<tr class="headings" role="row">
						<!-- <th>선택</th> -->
						<th>No.</th>
						<th>그룹명</th>
						<th>설명</th>
						<th>생성일</th>
						<th>상세보기</th>
					</tr>
				</thead>
				<tbody role="alert" aria-live="polite" aria-relevant="all">
					<#assign row = 1>
					<#list groupList as group>
						<tr class="even pointer">
							<td style="width:10%;">
								${row}
								<#assign row = row + 1>
							</td>
							<td style="width:25%;">${group.name?if_exists}</td>
							<td style="width:25%;">${group.description?if_exists}</td>
							<td style="width:25%;">${group.regDate?if_exists}</td>
							<td style="width:15%;">
								<input type="button" class="btn btn-default" value='상세' onclick="javascript:page_move('/admin/group/detail.htm','${group.id}');"/>
								<input type="button" class="btn btn-default" value='수정' onclick="javascript:page_move('/admin/group/update.htm','${group.id}');"/>
							</td>
						</tr>
					</#list>
				</tbody>
			</table>
			
		<div class="footer">
			<table style="width:100%">
				<tr>
					<td style="width:25%">
						<div>${countAll} items found, displaying all items.</div>
					</td>
					<td style="width:55%">
						<div class="dataTables_paginate paging_full_numbers" style="float: none;">
							<a id="first_paginate_button" class="First paginate_button paginate_button_disabled" onclick="javascript:searchList('First','')">First</a>
							<a class="Previous paginate_button paginate_button_disabled" onclick="javascript:searchList('Previous','')">Previous</a>
							<span id="paging_span"></span>
							<a class="next paginate_button" onclick="javascript:searchList('Next','')">Next</a>
							<a class="last paginate_button" onclick="javascript:searchList('Last','')">Last</a>
						</div>
					</td>
					<td style="width:20%" align="right">
						<input class="btn btn-dark" type="button" value='추가' onclick="javascript:page_move('/admin/group/create.htm','');"/>
					</td>
				</tr>
			</table>
		</div>
			
		</form>
	</div>
</body>
</html>