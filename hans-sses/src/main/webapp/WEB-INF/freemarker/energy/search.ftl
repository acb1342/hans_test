<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>SSES</title>

<script type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
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
	
	//첫 페이지로
	$("#pagenation").append("<a class='paginate_button' id='datatable-buttons_previous' href='javascript:search_list(1);'>First</a>");
	
    if(prev-1>0){
        strPrevStep="<a class='paginate_button' id='datatable-buttons_previous' href='javascript:search_list("+goPrev+");'>Prev</a>";
    }
    $("#pagenation").append(strPrevStep);
      
    if(next>=totalPage){
        next = totalPage;
    }
    else{
        strNextStep ="<a class='paginate_button' id='datatable-buttons_next' href='javascript:search_list("+goNext+");'>Next</a>";            
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
	$("#page").val(page);
	
	var formData = $("#vForm").serialize();
	var url = "/energy/energy/search.htm";
	
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
</script>
</head>
<body>
	<div class="x_content">
		<form method="get" id="vForm" name="vForm" onsubmit="return false;">

			<#assign searchType='${RequestParameters.searchType!""}'>
			<#assign searchValue='${RequestParameters.searchValue!""}'>
			<input type="hidden" name="page" id="page" value="${page}"/> 
			
			<div id="searchBox" style="height:40px; margin-bottom:1%">
				<div class="form-group">
					<div class="col-sm-2">
						<select class="form-control" name="searchType" id="searchType">
							<option value="mac" <#if searchType == 'part'> selected=""</#if>>장비코드</option> 
						</select>
					</div>
					<div class="col-sm-4">
						<input type="text" class="form-control" name="searchValue" id="searchValue" value='${searchValue}' onkeypress="if (event.keyCode == 13) {search_list(1);}" />
					</div>
					<div class="col-sm-2">
						<input type="button" class="btn btn-dark" value="검색" onclick="javascript:search_list(1)"/>
					</div>
				</div>
			</div>
			<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">

				<thead>
					<tr>
						<th>순서</th>
						<th>사용자</th>
						<th>이벤트</th>
						<th>장비 코드</th>
						<th>가동 시간</th>
						<th>절약 시간</th>
						<th>요청IP</th>
						<th>등록일</th>
					</tr>
				</thead>
				<tbody>
				<#assign row = rownum>
				<#list energyList as energy>
					<tr class="headings" role="row" height="10px">
						<td style="width:5%;">${row} <#assign row = row - 1></td>
						<td style="width:10%;">${energy.userName?if_exists}</td>
						<td style="width:10%;">${energy.eventType?if_exists}</td>
						<td style="width:15%;">${energy.macAddress?if_exists}</td>
						<td style="width:15%;">${energy.upTime?if_exists}</td>
						<td style="width:15%;">${energy.savingTime?if_exists}</td>
						<td style="width:15%;">${energy.requestIp?if_exists}</td>
						<td style="width:15%;">${energy.regDate?if_exists}</td>
					</tr>
					</#list>
				</tbody>
			</table>
			
			<div class="footer">
		<table style="width:100%">
			<tr>
			
				<td width="10%" align="left"></td>
				<td style="width:75%">
					<div class="dataTables_paginate paging_full_numbers" style="float: none; text-align:center; width:100%">
						<ul id="pagenation"></ul>
					</div>
				</td>
				<td width="15%" align="right"></td>
			</tr>
		</table>
		</div>
			
		</form>
	</div>
</body>
</html>