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
	drawPage(${page});
});

function drawPage(pagenum){
	var total = ${countAll};								//전체 게시물 수
	var perPage = ${rowPerPage};						//페이지당 게시물 수
	var totalPage = Math.ceil(total/perPage);			//전체 페이지 수
	// 보여줄 pagination 갯수  10
	var pageGroup = Math.ceil(pagenum/10);				//pagination 그룹 넘버
	var next = pageGroup*10;								//현재 그룹 마지막 페이지 넘버
	var prev = next-9;                          		//현재 그룹 첫 페이지 넘버
	var goNext = next+1;									//다음버튼 이동 페이지
	var goPrev = prev-1;									//이전버튼 이동 페이지
	
	var strPageNum="";                 
	var strPrevStep="";
	var strNextStep="";
	
	$("#lastPage").val(totalPage);
	
	//첫 페이지로
	$("#pagenation").append("<a class='paginate_button' id='datatable-buttons_previous' href='javascript:search_list(1);'>First</a>");
	
    if(prev-1>0){
        strPrevStep="<a class='paginate_button' id='datatable-buttons_previous' href='javascript:search_list("+goPrev+");'>Prev</a>";
    }
    $("#pagenation").append(strPrevStep);
      
    if(next>totalPage){
        next = totalPage;
    }
    else{
        strNextStep ="<a class='paginate_button' id='datatable-buttons_next' href='javascript:search_list("+goNext+");'>Next</i></a>";            
    }
    
    for(var i=prev;i<=next;i++){
    	strPageNum = "<a class='paginate_button' id='num_"+i+"' onclick='javascript:search_list( " + i + ");'>" + i + "</a>";
       $("#pagenation").append(strPageNum);
    }   
	$("#pagenation").append(strNextStep);
	
	//마지막 페이지로
	$("#pagenation").append("<a class='paginate_button' id='datatable-buttons_previous' href='javascript:search_list("+totalPage+");'>Last</a>");
 
	$("#num_"+$("#page").attr("value")).attr('class','paginate_active');
	$("#num_"+$("#page").attr("value")).attr('onclick','');
}

function search_list(page) {
	var currPage = $("#page").val();
	var lastPage = $("#lastPage").val();
	if (page > lastPage) page = lastPage;
	
	$("#page").val(page);
	
	var formData = $("#vForm").serialize();
	var url = "/board/notice/search.htm";
	
	$.ajax({
		type : "POST",
		url : url,
		data : formData,			
		success : function(response){
			$("#content").html(response);
		},
		error : function(){
			console.log("error!!");
			return false;
		}
	});
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
			<#assign searchType='${RequestParameters.searchType!""}'>
			<#assign searchValue='${RequestParameters.searchValue!""}'>
			<input type="hidden" id="countAll" value="${countAll}"/>
			<input type="hidden" id="page" name="page" value="${page}"/>
			<input type="hidden" id="lastPage" name="lastPage" value="${lastPage?if_exists}"/>
			
			<div style="margin:1% 0 1% 0;" class="col-sm-2">
				<select class="form-control" name="searchType" id="searchType">
					<option value="all" <#if searchType == 'all'> selected=""</#if>>전체</option>
					<option value="title" <#if searchType == 'title'> selected=""</#if>>제목</option>
					<option value="contents" <#if searchType == 'contents'> selected=""</#if>>내용</option>
				</select>
			</div>
			<div style="margin:1% 0 1% 0;" class="col-sm-4">
				<input type="text" class="form-control" name="searchValue" id="searchValue" value="${searchValue}" onkeypress="if (event.keyCode == 13) {search_list(1);}"/>
			</div>
			<div style="margin:1% 0 1% 0;" class="col-sm-2">
				<input type="button" class="btn btn-dark" value="검색" onclick="javascript:search_list(1)"/>
			</div>
			
			<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info" style="text-align:left;">
				<thead>
					<tr class="headings" role="row">
						<!-- <th>선택</th> -->
						<th>No.</th>
						<th>제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>공개여부</th>
						<th></th>
					</tr>
				</thead>
				<tbody role="alert" aria-live="polite" aria-relevant="all">
					<#assign row = rownum>
					<#list noticeList as notice>
						<tr class="even pointer" style="height:1px;">
							<!-- <td style="width:5%;"><input type="checkbox" id="selected" name="selected" value="${notice.id}"></td> -->
							<td style="width:10%;">
								${row}
								<#assign row = row - 1>
							</td>
							<td style="width:35%;">
								<span style="display:inline-block; width:350px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis; text-align:left;">${notice.title?if_exists}</span>
							</td>
							<td style="width:15%;">${notice.adminId?if_exists}</td>
							<td style="width:15%;">${notice.regDate}</td>
							<td style="width:10%;">
								<#if notice.displayYn??>
									<#if notice.displayYn == 'Y'>공개</#if>
									<#if notice.displayYn == 'N'>비공개</#if>
								</#if>
							</td>
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
					<td width="10%" align="left"></td>
					<td style="width:75%" align="center">
						<div class="dataTables_paginate paging_full_numbers" style="float: none; text-align:center; width:100%">
							<ul id="pagenation"></ul>
						</div>
					</td>
					<td style="width:15%" align="right">
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