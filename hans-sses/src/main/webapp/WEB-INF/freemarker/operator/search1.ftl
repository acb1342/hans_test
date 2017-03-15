<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>Admin - T Charger</title>

<script type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript">
$(function() {	
	drawPage($("#page").attr("value"));
	searchChange();
});
 
function drawPage(pagenum){
	var total = ${totalCount};
	var perPage = ${PerPage};
	var totalPage = Math.ceil(total/perPage); 
    var pageGroup = Math.ceil(pagenum/10);
    var next = pageGroup*10;
    var prev = next-9;                          
    var goNext = next+1;      
    

    var PageNum;                 
    var prevStep;
    var nextStep; 

    if(prev-1<=0){
        var goPrev = 1;
        prevStep="";
    }else{
        var goPrev = prev-1;
        prevStep="<li class='paginate_button previous' id='datatable-buttons_previous'><a href='javascript:search_list("+goPrev+");'>◀</a></li>";
    }    

    if(next>totalPage){
        var goNext = totalPage;
        next = totalPage;
        nextStep ="";            
        
    }else{
        var goNext = next+1;
        nextStep ="<li class='paginate_button next' id='datatable-buttons_next'><a href='javascript:search_list("+goNext+");'>▶</a></li>";            
    }

    $("#pagenation").empty();
    $("#pagenation").append(prevStep);
    
    for(var i=prev; i<=next;i++){
        PageNum = "<li class='paginate_button' id='num_"+i+"'><a href='#' aria-controls='datatable-keytable' id='num_"+i+"_a' onclick='javascript:search_list( " + i + ");'>" + i + "</a></li>";
        $("#pagenation").append(PageNum);
    }    

    $("#pagenation").append(nextStep);  
    
    if($("#page").attr("value")==1){
		$("#datatable-buttons_previous").attr('class','paginate_button previous disabled');
		$("#datatable-buttons_previous_a").attr('onclick','');
	}
	
    if($("#page").attr("value")==${pageCount}){
		$("#datatable-buttons_next").attr('class','paginate_button next disabled');
		$("#datatable-buttons_next_a").attr('onclick','');
	}
	
	$("#num_"+$("#page").attr("value")).attr('class','paginate_button active');
	$("#num_"+$("#page").attr("value")+"_a").attr('onclick','');
}

function search_list(page) {
	
	$("#page").val(page);
	
	var formData = $("#vForm").serialize();
	var url = "/admin/operator/search.htm";
	
	$.ajax({
		type : "POST",
		url : url,
		data : formData,			
		success : function(response){
			$("#content").html(response);
		},
		error : function(){
			console.log("error!!");
			//err_page();
			return false;
		}
	});
	
}


function check_event(id){
	
	if($("input:checkbox[id='checked_"+id+"']").is(":checked")){
		$("#icheckbox_"+id).prop("class","icheckbox_flat-green");
		$("input:checkbox[id='checked_"+id+"']").prop("checked", false);
		
	}
	else{
		$("#icheckbox_"+id).prop("class","icheckbox_flat-green checked");
		$("input:checkbox[id='checked_"+id+"']").prop("checked", true);
		
	}
	
}

function searchChange() {
	if ($("#searchType").val() == 'group') {
		$("#searchValue").hide();
		$("#searchSelect").show();
	} else {
		$("#searchValue").show();
		$("#searchSelect").hide();
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

			<input type="hidden" name="id" id="id" /> 
			<#assign page='${RequestParameters.page!"1"}'> 
			<#assign searchType='${RequestParameters.searchType!""}'>
			<#assign searchValue='${RequestParameters.searchValue!""}'>
			<#assign searchSelect='${RequestParameters.searchSelect!""}'>
			<#assign searchValid='${RequestParameters.searchValid!""}'>
			
			<input type="hidden" name="page" id="page" value="${page}"/> 

			<div id="searchBox" style="height:40px;" >
				<div class="form-group">
				
					<div class="col-sm-2">
						<select class="form-control" name="searchType" id="searchType">
							<option value="id">ID</option> <option value="name">이름</option>
						</select>
					</div>	
					
					<div class="col-sm-4">
						<input type="text" class="form-control" name="searchValue" id="searchValue" value='${searchValue}' onkeypress="if (event.keyCode == 13) {search_list(1);}" />
					</div>
					
					<div class="col-sm-2" id="searchSelect">
						<select class="form-control">
							<#list groupList as group>
							<option value="${group.id}">${group.name}</option>
							</#list>
						</select>
					</div>
					
					<div class="col-sm-2">
						<select class="form-control" name="searchValid" id="searchValid">
							<option value="">전체</option>					
							<option value="Y" <#if searchValid == 'Y'> selected=""</#if>>사용중</option>
							<option value="N" <#if searchValid == 'N'> selected=""</#if>>사용중지</option>
						</select>
					</div>
					
					<div class="col-sm-2">
						<!-- <input type="button" value="검색" onclick="javascript:search_list(1)" style="background:#BDBDBD; border:0; color:white;"/> -->
						<input type="button" class="btn btn-primary" value="검색" onclick="javascript:search_list(1)"/>
					</div>
				</div>
				
			</div>
			<p>
			<table id="datatable"
				class="table table-bordered dataTable no-footer bulk_action"
				role="grid" aria-describedby="datatable_info">

				<thead>
					<tr>
						<th style="text-align:center;">순서</th>
						<th style="text-align:center;">ID</th>
						<th style="text-align:center;">이름</th>
						<th style="text-align:center;">사용권한</th>
						<th style="text-align:center;">계정상태</th>
					</tr>
				</thead>
				<tbody>
				<#assign row = rownum>
				<#list adminList as admin>
					<tr class="even pointer" height="10px" style="cursor:pointer;" onclick="javascript:page_move('/admin/operator/detail.htm','${admin.id}')" onmouseover="this.style.background='#EAEAEA'" onmouseout="this.style.background='#f7f7f7'">
						
						<td style="width:5%; text-align:center;">${row} <#assign row = row - 1></td>
						<td style="width:30%;">${admin.id}</td>
						<td style="width:29%;">${admin.name}</td>
						<td style="width:9%; text-align:center;">${admin.groupName}</td>
						<td style="width:10%; text-align:center;"> <#if admin.validYn == 'Y'>사용중<#else>사용중지</#if></td>
					</tr>
					</#list>
					
				</tbody>
			</table>
			
			<div class="footer">
		<table style="width:100%">
			<tr>
			
				<td width="25%" align="left">
				<div class="dataTables_info" id="datatable-checkbox_info" role="status" aria-live="polite">${startRow+1} to ${endRow+1} of ${totalCount}</div>
				</td>
				<td width="50%">
				<div class="dataTables_paginate paging_simple_numbers" id="datatable-keytable_paginate" style="float:none; text-align:center;">
				<ul class="pagination" id="pagenation">
					
				</ul>
			</div>
				</td>
				<td width="25%" align="right">
					<#--<input type="button" value='추가' onclick="javascript:page_move('/admin/operator/create.htm','');" style="background:#BDBDBD; border:0; color:white;"/>
					<input type="button" value='삭제' onclick="javascript:confirmAndDelete()" style="background:#F15F5F; border:0; color:white;"/>-->
					
					<input type="button" class="btn btn-primary" value='추가' onclick="javascript:page_move('/admin/operator/create.htm','');"/>
				</td>
			</tr>
		</table>
		</div>
			
		</form>

	</div>

</body>
</html>