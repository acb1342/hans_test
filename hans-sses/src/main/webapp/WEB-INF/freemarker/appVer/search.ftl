<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title> AppVer </title>

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
		
		page_move('/board/appVer/search.htm', '');
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
	
	//삭제
	function confirmAndDelete() {
		deleteByChecked('/board/appVer/delete.json', function() { 
 			searchList();
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
			<#assign lastPage='${RequestParameters.lastPage!""}'>
			<input type="hidden" id="countAll" value="${countAll}"/>
			<input type="hidden" id="currPage" name="page" value="${page}"/>
			<input type="hidden" id="lastPage" name="lastPage" value="${lastPage}"/>
			
			<select style="width:20%; margin:0px 0px 5px;" class="form-control" name="searchType" id="searchType" onChange="javascript:searchList('',1);">
				<option value="">전체</option>
				<option value="301401" <#if searchType == '301401'> selected=""</#if>>ANDROID</option>
				<option value="301402" <#if searchType == '301402'> selected=""</#if>>IOS</option>
				<option value="301403" <#if searchType == '301403'> selected=""</#if>>PC</option>
			</select>
			
			<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
				<thead>
					<tr class="headings" role="row">
						<th>선택</th>
						<th>No.</th>
						<th>생성일</th>
						<th>OS</th>
						<th>Version</th>
						<th>필수 여부</th>
						<th>업데이트 내용</th>
						<th>배포 예정일시</th>
						<th>상세보기</th>
					</tr>
				</thead>
				<tbody role="alert" aria-live="polite" aria-relevant="all">
					<#assign row = rownum>
					<#list appVerList as appVer>
						<tr class="even pointer" style="height:1px;">
							<td style="width:5%; text-align:center;">
								<input type="checkbox" id="selected" name="selected" value="${appVer.id}">
								<#--
								<div class="icheckbox_flat-green" style="position:relative;">
									<input type="checkbox" class="tableflat" style="position:absolute; opacity:0;" id="selected" name="selected" value="${appVer.id}">
									<ins class="iCheck-helper" style="position:absolute; top:0%; left:0%; display:block; width:100%; height:100%; margin:0px; padding:0px; background:rgb(255, 255, 255); border:0px; opacity:0;"></ins>
								</div>
								-->
							</td>
							<td style="width:5%; text-align:center;">
								${row}
								<#assign row = row - 1>
							</td>
							<td style="width:15%;">${appVer.fstRgDt}</td>
							<td style="width:10%;">
								<#if appVer.os == '301401'>ANDROID</#if>
								<#if appVer.os == '301402'>IOS</#if>
								<#if appVer.os == '301403'>PC</#if>
							</td>
							<td style="width:10%;">${appVer.ver}</td>
							<td style="width:10%;">${appVer.updateType}</td>
							<td style="width:15%;">${appVer.content?if_exists}</td>
							<td style="width:15%;">${appVer.deployYmd?if_exists}</td>
							<td style="width:15%;">
								<input type="button" class="btn btn-default" value='상세' onclick="javascript:page_move('/board/appVer/detail.htm','${appVer.id}');"/>
								<input type="button" class="btn btn-default" value='수정' onclick="javascript:page_move('/board/appVer/update.htm','${appVer.id}');"/>
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
						<input class="btn btn-default" type="button" value='추가' onclick="javascript:page_move('/board/appVer/create.htm','');"/>
						<input class="btn btn-danger" type="button" value='삭제' onclick="javascript:confirmAndDelete()"/>
					</td>
				</tr>
			</table>
		</div>
			
		</form>
	</div>
</body>
</html>