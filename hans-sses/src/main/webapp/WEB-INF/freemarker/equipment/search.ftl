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
      
    if(next>totalPage){
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
	var url = "/member/equipment/search.htm";
	
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
			<#assign searchSelect='${RequestParameters.searchSelect!""}'>
			<input type="hidden" name="page" id="page" value="${page}"/> 
			
			<div id="searchBox" style="height:40px; margin-bottom:1%">
				<div class="form-group">
					<div class="col-sm-2">
						<select class="form-control" name="searchType" id="searchType">
							<option value="name" <#if searchType == 'name'> selected=""</#if>>장비명</option>
						</select>
					</div>
					<div class="col-sm-4">
						<input type="text" class="form-control" name="searchValue" id="searchValue" value='${searchValue}' onkeypress="if (event.keyCode == 13) {search_list(1);}" />
					</div>
					<#--
					<div class="col-sm-2">
						<select class="form-control" id="searchSelect" name="searchSelect">						
							<option value="0">전체</option>
							<#list groupList as group>
							<option value="${group.id}" <#if searchSelect == "${group.id}"> selected=""</#if>> ${group.name}</option>
							</#list>
						</select>
					</div-->
					<div class="col-sm-2">
						<input type="button" class="btn btn-dark" value="검색" onclick="javascript:search_list(1)"/>
					</div>
				</div>
			</div>
			<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">

				<thead>
					<tr>
						<th>MacAddress</th>
						<th>장비명</th>
						<th>제조사</th>
						<th>제조년도</th>
                        <th>기타</th>
                        <th>소비전력</th>
						<th>상세</th>
					</tr>
				</thead>
				<tbody>
				<#assign row = rownum>
				<#list equipmentList as equipment>
					<tr class="headings" role="row" height="10px">
						
						<td style="width:10%;">${equipment.macaddress}</td>
						<td style="width:15%;">${equipment.name}</td>
						<td style="width:15%;">${equipment.manufacturer}</td>
						<td style="width:15%;">${equipment.make_date?date("yyyyMMdd")}</td>
                        <td style="width:15%;">${equipment.etc}</td>
                        <td style="width:15%;">${equipment.elect_power}</td>
						<td style="width:15%;">
							<input type="button" class="btn btn-default" value='상세' onclick="javascript:page_move('/member/equipment/detail.htm','${equipment.macaddress}');"/>
							<input type="button" class="btn btn-default" value='수정' onclick="javascript:page_move('/member/equipment/update.htm','${equipment.macaddress}');"/>
						</td>
					</tr>
					</#list>
					
				</tbody>
			</table>
			
			<div class="footer">
		<table style="width:100%">
			<tr>
			
				<td width="10%" align="left">
				</td>
				<td style="width:75%">
					<div class="dataTables_paginate paging_full_numbers" style="float: none; text-align:center; width:100%">
						<ul id="pagenation"></ul>
					</div>
				</td>
				<td width="15%" align="right">
					<input type="button" class="btn btn-dark" value='추가' onclick="javascript:page_move('/member/equipment/create.htm','');"/>
				</td>
			</tr>
		</table>
		</div>
			
		</form>

	</div>

</body>
</html>