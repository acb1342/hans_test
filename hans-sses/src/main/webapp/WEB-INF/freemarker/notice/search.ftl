<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title> Notice </title>
<script type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript" src="/js/jquery/alert/jquery.alerts.custom.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript">
	$(function() {
		paging();
	});
	
	// 화면 하단 페이지 리스트 생성
	function paging() {
		var cnt = $('#countAll').val();		// 총 로우 수
		var lastPage = parseInt(cnt/10);	// 마지막 페이지 번호
		var namuzi = parseInt(cnt%10);
		var currPage = $('#currPage').val();
		
		if (namuzi > 0) lastPage = lastPage + 1;
		
		if (lastPage < 1 && namuzi == 0) return;
		
		document.getElementById('lastPage').value = lastPage;
		
		var pageNumMok = parseInt(currPage/10);
		var pageNumNamuzi = parseInt(currPage%10);
		
		if (currPage != lastPage) {
			var step = pageNumMok * 10 + 1;
			
			for (var i = step; i < step + 10; i++) {
				var id = "p" + i;
				var html = "<a class='paginate_button' id='" + id + "' onclick='javascript:searchList(\"\"," + i + ");'>" + i + "</a>";
				$('#paging_span').append(html);
				if (i >= lastPage) break;
			}
		}
		else {
			var id = "p" + lastPage;
			var html = "<a class='paginate_button' id='" + id + "' onclick='javascript:searchList(\"\"," + lastPage + ");'>" + lastPage + "</a>";
			$('#paging_span').append(html);
		}
		/* for (step = 1; step <= lastPage; step++) {
			var id = "p" + step;
			var html = "<a class='paginate_button' id='" + id + "' onclick='javascript:searchList(\"\"," + step + ");'>" + step + "</a>";
			$('#paging_span').append(html);
		} */
		
		// 현재 페이지번호 class 변경
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
			if (pageType == "Next") {
				if (currPage == lastPage) return;
				if (currPage%10 == 0) pageNum = parseInt(currPage/10) + 11; //pageNum = currPage + 1;
				else pageNum = parseInt(currPage/10) + 11;
			}
			if (pageType == "Last") pageNum = lastPage;
			
			if (pageNum == 0 || (pageType == "First" && currPage <= 1) || pageNum > lastPage || (pageType == "Last" && currPage == lastPage)) return;
			
			document.getElementById('currPage').value = pageNum; 
		}
		
		page_move('/board/notice/search.htm','');
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
		deleteByChecked('/board/notice/delete.json', function() { 
 			searchList();
		});
	}
	
	function deleteByChecked(url, callback) {
		var ids = getCheckedIds();
		if (ids == "") {
			jAlert('삭제하려면 체크박스를 선택하세요.');
			return ;
		}
		jConfirm('삭제하시겠습니까?', 'Confirm', function(r) {
			if (r) {
				$.ajax({
					url:url,
					type:"POST",
					dataType:'json',
					data:{
						id:ids
					},
					success:function(isDelete) {
						if (isDelete) {
							jAlert('삭제되었습니다.', 'Alert', function() {
								callback();
							});
						} else {
							jAlert('<fmt:message key="statement.delete.fail"/>');
						}
					}
				});
			}
		});
		
		function getCheckedIds() {
			var ids = [];
			
			$("input:checkbox:checked").each(function() {
				ids.push($(this).val());
			});
			
			return ids.join(";");
		}
		
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
			<#assign lastPage='${RequestParameters.lastPage!""}'>
			<input type="hidden" id="countAll" value="${countAll}"/>
			<input type="hidden" id="currPage" name="page" value="${page}"/>
			<input type="hidden" id="lastPage" name="lastPage" value="${lastPage}"/>
				
			<div style="margin:1% 0 1% 0;" class="col-sm-2">
				<select class="form-control" name="searchType" id="searchType">
					<option value="all" <#if searchType == 'all'> selected=""</#if>>전체</option>
					<option value="title" <#if searchType == 'title'> selected=""</#if>>제목</option>
					<option value="contents" <#if searchType == 'contents'> selected=""</#if>>내용</option>
				</select>
			</div>
			<div style="margin:1% 0 1% 0;" class="col-sm-4" style="width:30%;" >
				<input type="text" class="form-control" name="searchValue" id="searchValue" value="${searchValue}"/>
			</div>
			<div style="margin:1% 0 1% 0;" class="col-sm-2">
				<input type="button" class="btn btn-dark" value="검색" onclick="javascript:searchList('',1)"/>
			</div>
				
			<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info" style="text-align:left;">
				<thead>
					<tr class="headings" role="row">
						<!-- <th>선택</th> -->
						<th>No.</th>
						<th>제목</th>
						<th>내용</th>
						<th>아이디</th>
						<th>등록일</th>
						<th>상세보기</th>
					</tr>
				</thead>
				<tbody role="alert" aria-live="polite" aria-relevant="all">
					<#assign row = rownum>
					<#list noticeList as notice>
						<tr class="even pointer" style="height:1px;">
							<!-- <td style="width:5%;"><input type="checkbox" id="selected" name="selected" value="${notice.id}"></td> -->
							<td style="width:5%;">
								${row}
								<#assign row = row - 1>
							</td>
							<td style="width:25%;">${notice.title?if_exists}</td>
							<td style="width:25%;">
								<span style="display:inline-block; width:300px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis; text-align:left;">${notice.contents?if_exists}</span>
							</td>
							<td style="width:15%;">${notice.adminId?if_exists}</td>
							<td style="width:15%;">${notice.regDate?if_exists}</td>
							<td style="width:15%;">
								<input type="button" class="btn btn-default" value='상세' onclick="javascript:page_move('/board/notice/detail.htm','${notice.id}');"/>
								<input type="button" class="btn btn-default" value='수정' onclick="javascript:page_move('/board/notice/update.htm','${notice.id}');"/>
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
						<input class="btn btn-dark" type="button" value='추가' onclick="javascript:page_move('/board/notice/create.htm','');"/>
						<!-- <input class="btn btn-danger" type="button" value='삭제' onclick="javascript:confirmAndDelete()"/> -->
					</td>
				</tr>
			</table>
		</div>
			
		</form>
	</div>
</body>
</html>