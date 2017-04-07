<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title> User - Equipment </title>
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
      
    if(next>=totalPage){
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
	$("#page").val(page);
	
	var formData = $("#vForm").serialize();
	$.ajax({
		type : "POST",
		url : "/member/userEq/search.htm",
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

function deleteById(id) {
	var formData = $("#vForm").serialize() + "&id=" + id;
	if (confirm("삭제 하시겠습니까?")) {
		$.ajax({
			type	 :	"POST",
			url		 :	"/member/userEq/delete.json",
			data	 :	formData,
			success :	function(response){
				if (response == true) {
					page_move('/member/userEq/search.htm');
				}
			},
			error : function(){
				console.log("error!!");
				//err_page();
				return false;
			}
		});
	}
	else return;
}
</script>
<style type="text/css">
    .table>tbody>tr>td{vertical-align:middle;}
</style>
</head>
<body>

	<div class="x_content">
		<form id="vForm" name="vForm">	 
			<#assign searchType='${RequestParameters.searchType!""}'>
			<#assign searchValue='${RequestParameters.searchValue!""}'>
			<input type="hidden" id="countAll" value="${countAll}"/>
			<input type="hidden" id="page" name="page" value="${page}"/>
			<input type="hidden" id="lastPage" name="lastPage" value="${lastPage?if_exists}"/>
			
			<div style="margin:1% 0 1% 0;" class="col-sm-2">
				<select class="form-control" name="searchType" id="searchType">
					<option value="user" <#if searchType == 'user'> selected=""</#if>>사용자명</option>
					<option value="equip" <#if searchType == 'equip'> selected=""</#if>>장비명</option>
					<option value="macAddr" <#if searchType == 'macAddr'> selected=""</#if>>MacAddress</option>
				</select>
			</div>
			<div style="margin:1% 0 1% 0;" class="col-sm-4">
				<input type="text" class="form-control" name="searchValue" id="searchValue" value="${searchValue}" onkeypress="if (event.keyCode == 13) {javascript:search_list(1)}"/>
			</div>
			<div style="margin:1% 0 1% 0;" class="col-sm-2">
				<input type="button" class="btn btn-dark" value="검색" onclick="javascript:search_list(1)"/>
			</div>
			
			<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info" style="text-align:left;">
				<thead>
					<!-- <tr class="headings" role="row">
						<th>No.</th>
						<th>회사명</th>
						<th>부서명</th>
						<th>사용자 식별코드</th>
						<th>사용자명</th>
						<th>보유 장비 수</th>
						<th></th>
					</tr> -->
					<tr class="headings" role="row">
						<th>No.</th>
						<th>사용자명</th>
						<th>장비명</th>
						<th>MacAddress</th>
						<th>등록일</th>
						<th></th>
					</tr>
				</thead>
				<tbody role="alert" aria-live="polite" aria-relevant="all">
					<#assign row = rownum>
					<#list userEqList as userEq>
						<tr class="even pointer" style="height:1px;">
							<td style="width:10%;">
								${row}
								<#assign row = row - 1>
							</td>
							<!-- <td style="width:15%;">${userEq.user.parentName?if_exists}</td>
							<td style="width:15%;">${userEq.user.company_name?if_exists}</td>
							<td style="width:15%;">${userEq.userSeq?if_exists}</td>
							<td style="width:15%;">${userEq.user.user_name?if_exists}</td>
							<td style="width:15%;">${userEq.volume?if_exists}</td>  -->
							<td style="width:20%;">${userEq.user.user_name?if_exists}</td>
							<td style="width:20%;">${userEq.equipment.name?if_exists}</td>
							<td style="width:20%;">${userEq.macAddress?if_exists}</td>
							<td style="width:20%;">${userEq.regDate?string("yyyy-MM-dd")}</td>
							<td style="text-align:right"><input class="btn btn-danger" type="button" value='삭제' onclick="javascript:deleteById('${userEq.seq}');"/></td>
							<!-- <td style="width:15%;">
								<input type="button" class="btn btn-default" value='상세' onclick="javascript:page_move('/member/userEq/detail.htm','${userEq.seq}');"/>
								<input type="button" class="btn btn-default" value='수정' onclick="javascript:page_move('/member/userEq/update.htm','${userEq.seq}');"/>
							</td> -->
						</tr>
					</#list>
				</tbody>
			</table>
			
		<div class="footer">
			<table style="width:100%">
				<tr>
					<td width="10%" align="left"></td>
					<td style="width:75%" align="center">
						<div class="dataTables_paginate paging_full_numbers" style="float: none;text-align:center; width:100%">
							<ul id="pagenation"></ul>
						</div>
					</td>
					<td style="width:15%" align="right">
						<input class="btn btn-dark" type="button" value='추가' onclick="javascript:page_move('/member/userEq/create.htm','');"/>
					</td>
				</tr>
			</table>
		</div>
			
		</form>
	</div>
</body>
</html>