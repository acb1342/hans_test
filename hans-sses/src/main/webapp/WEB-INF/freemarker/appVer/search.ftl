<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>SSES</title>

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
		var url = "/board/appVer/search.htm";
		
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
			<input type="hidden" id="countAll" value="${countAll}"/>
			<input type="hidden" id="page" name="page" value="${page}"/>
			<input type="hidden" id="lastPage" name="lastPage" value="${lastPage?if_exists}"/>
			
			<select style="width:20%; margin:1% 0 1% 0;" class="form-control" name="searchType" id="searchType" onChange="javascript:search_list(1);">
				<option value="">전체</option>
				<option value="301401" <#if searchType == '301401'> selected=""</#if>>ANDROID</option>
				<option value="301402" <#if searchType == '301402'> selected=""</#if>>IOS</option>
				<option value="301403" <#if searchType == '301403'> selected=""</#if>>PC</option>
			</select>
			
			<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
				<thead>
					<tr class="headings" role="row">
						<!-- <th>선택</th> -->
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
							<!-- <td style="width:5%; text-align:center;">
								<input type="checkbox" id="selected" name="selected" value="${appVer.id}">
								
								<div class="icheckbox_flat-green" style="position:relative;">
									<input type="checkbox" class="tableflat" style="position:absolute; opacity:0;" id="selected" name="selected" value="${appVer.id}">
									<ins class="iCheck-helper" style="position:absolute; top:0%; left:0%; display:block; width:100%; height:100%; margin:0px; padding:0px; background:rgb(255, 255, 255); border:0px; opacity:0;"></ins>
								</div>
								
							</td> -->
							<td style="width:5%; text-align:center;">
								${row}
								<#assign row = row - 1>
							</td>
							<td style="width:15%;">${appVer.regDate}</td>
							<td style="width:10%;">
								<#if appVer.os == '301401'>ANDROID</#if>
								<#if appVer.os == '301402'>IOS</#if>
								<#if appVer.os == '301403'>PC</#if>
							</td>
							<td style="width:10%;">${appVer.ver}</td>
							<td style="width:10%;">
								<#if appVer.updateType == '605101'>필수</#if>
								<#if appVer.updateType == '605102'>선택</#if>
							</td>
							<td style="width:20%;">${appVer.content?if_exists}</td>
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
					<td style="width:80%" align="center">
						<div class="dataTables_paginate paging_full_numbers" style="float: none;">
							<ul id="pagenation"></ul>
						</div>
					</td>
					<td style="width:20%" align="right">
						<input class="btn btn-dark" type="button" value='추가' onclick="javascript:page_move('/board/appVer/create.htm','');"/>
						<!-- <input class="btn btn-danger" type="button" value='삭제' onclick="javascript:confirmAndDelete()"/> -->
					</td>
				</tr>
			</table>
		</div>
			
		</form>
	</div>
</body>
</html>