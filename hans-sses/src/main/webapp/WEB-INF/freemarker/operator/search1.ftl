<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>Admin - T Charger</title>

<script type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript">
$(function() {	
	paging();
});

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
	
	page_move1('/admin/operator/search.htm', '');
}

// 페이지 이동
function page_move1(url, id) {
	var formData = $("#vForm").serialize() + "&id=" + id;
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

<style type="text/css">
    .table>tbody>tr>td{vertical-align:middle;}
	</style>

</head>
<body>

	<div class="x_content">
		<form method="get" id="vForm" name="vForm" onsubmit="return false;">

			<#assign searchType='${RequestParameters.searchType!""}'>
			<#assign searchValue='${RequestParameters.searchValue!""}'>
			<#assign searchSelect='${RequestParameters.searchSelect!""}'>
			<#assign lastPage='${RequestParameters.lastPage!""}'>
			<input type="hidden" id="countAll" value="${countAll}"/>
			<input type="hidden" id="currPage" name="page" value="${page}"/>
			<input type="hidden" id="lastPage" name="lastPage" value="${lastPage}"/>
			
			<div id="searchBox" style="height:40px;" >
				<div class="form-group">
				
					<div class="col-sm-2">
						<select class="form-control" name="searchType" id="searchType">
							<option value="id" <#if searchType == 'id'> selected=""</#if>>ID</option> 
							<option value="name"  <#if searchType == 'name'> selected=""</#if>>이름</option>
						</select>
					</div>
					
					<div class="col-sm-4">
						<input type="text" class="form-control" name="searchValue" id="searchValue" value='${searchValue}' onkeypress="if (event.keyCode == 13) {search_list(1);}" />
					</div>
					
					<div class="col-sm-2">
						<select class="form-control" id="searchSelect" name="searchSelect">						
							<option value="0">전체</option>
							<#list groupList as group>
							<option value="${group.id}" <#if searchSelect == "${group.id}"> selected=""</#if>> ${group.name}</option>
							</#list>
						</select>
					</div>
					
					<div class="col-sm-2">
						<input type="button" class="btn btn-default" value="검색" onclick="javascript:search_list(1)"/>
					</div>
				</div>
				
			</div>
			<p>
			<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">

				<thead>
					<tr>
						<th style="text-align:center;">순서</th>
						<th style="text-align:center;">ID</th>
						<th style="text-align:center;">이름</th>
						<th style="text-align:center;">사용권한</th>
						<th style="text-align:center;">상세</th>
					</tr>
				</thead>
				<tbody>
				<#assign row = rownum>
				<#list adminList as admin>
					<tr class="headings" role="row" height="5px">
						
						<td style="width:5%; text-align:center;">${row} <#assign row = row - 1></td>
						<td style="width:30%;">${admin.id}</td>
						<td style="width:30%;">${admin.name}</td>
						<td style="width:10%; text-align:center;">${admin.groupName}</td>
						<td style="width:15%; text-align:center;">
							<input type="button" class="btn btn-default" value='상세보기' onclick="javascript:page_move('/admin/operator/detail.htm','${admin.id}');"/>
						</td>
					</tr>
					</#list>
					
				</tbody>
			</table>
			
			<div class="footer">
		<table style="width:100%">
			<tr>
			
				<td width="25%" align="left">
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
				<td width="20%" align="right">
				
					<input type="button" class="btn btn-default" value='추가' onclick="javascript:page_move('/admin/operator/create.htm','');"/>
				</td>
			</tr>
		</table>
		</div>
			
		</form>

	</div>

</body>
</html>