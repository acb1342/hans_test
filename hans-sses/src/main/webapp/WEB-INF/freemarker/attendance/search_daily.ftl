<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>SSES</title>
<!-- <script type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript" src="/js/jquery/alert/jquery.alerts.custom.js"></script>
<script type="text/javascript" src="/js/common.js"></script> -->
<script src="/css/gentelella-master/vendors/moment/min/moment.min.js"></script>
<script src="/css/gentelella-master/vendors/bootstrap-daterangepicker/daterangepicker.js"></script>
<script type="text/javascript">
	$(function() {
		drawPage(${page});
		
		$('.selDate').daterangepicker({
            singleDatePicker: true,
            singleClasses: "picker_3",
            locale : {
                format: "YYYY-MM-DD"
            }
        }, function(start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
        });
		
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
		var url = "/attendance/daily/search.htm";
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
	
	function dateCheck() {
		var befArr = $('#beforeDay').val().split('-');
		var aftArr = $('#afterDay').val().split('-');
		
		var befDate = new Date(befArr[0], befArr[1]-1, befArr[2]);
		var aftDate = new Date(aftArr[0], aftArr[1]-1, aftArr[2]);
		
		if (befDate > aftDate) {
			alert("시작시점은 종료시점보다 앞서야 합니다.\n기간을 다시 설정한 후 조회해 주세요.");
			return;
		}
		else search_list(1);
	}
	
	function test() {
		var data = {
					macAddress:"AA-BB-CC-DD-EE-FF",
					userSeq:"5",
					type:"1"
					};
		$.ajax({
			beforeSend : function(request) {
				request.setRequestHeader("Accept","application/json");
				//request.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			},
			type	 :	"POST",
			url		 :	"/sendAttendance",
			data	 :	data, //JSON.stringify(data),
			success :	function(response) {
				alert(response.errorMsg);
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
		<form id="vForm" name="vForm" onsubmit="return false">		 
			<#assign searchType='${RequestParameters.searchType!""}'>
			<#assign searchValue='${RequestParameters.searchValue!""}'>
			<#assign beforeDay='${RequestParameters.beforeDay!""}'>
			<#assign afterDay='${RequestParameters.afterDay!""}'>
			<input type="hidden" id="countAll" value="${countAll}"/>
			<input type="hidden" id="page" name="page" value="${page}"/>
			
			<div class="col-sm-11" style="margin:1% 0 1% 0;">
				<div class="col-sm-2">
					<select class="form-control" name="searchType" id="searchType">
						<option value="all" <#if searchType == 'all'> selected=""</#if>>접속 구분</option>
						<option value="attendance" <#if searchType == 'attendance'> selected=""</#if>>출근</option>
						<option value="leave" <#if searchType == 'leave'> selected=""</#if>>퇴근</option>
					</select>
				</div>
				<div  class="col-sm-2">
					<input class="selDate form-control" type="text" id="beforeDay" name="beforeDay" value="${beforeDay}">
				</div>
				<div style="width:1%; line-height:40px; text-align:center;" class="col-sm-1">~</div>
				<div class="col-sm-2">
					<input class="selDate form-control" type="text" id="afterDay" name="afterDay" value="${afterDay}">
				</div>
				
				<div class="col-sm-3">
					<input type="text" class="form-control" name="searchValue" id="searchValue" value="${searchValue}" placeholder="사용자명" onkeypress="if(event.keyCode==13){dateCheck();}"/>
				</div>
				<div class="col-sm-2">
					<input type="button" class="btn btn-dark" value="조회" onclick="dateCheck();"/>
				</div>
			</div>

			<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
				<thead>
					<tr class="headings" role="row">
						<th>No.</th>
						<th>사용자명</th>
						<th>장비명</th>
						<th>Mac Address</th>
						<th>접속 구분</th>
						<th>일시</th>
					</tr>
				</thead>
				<tbody role="alert" aria-live="polite" aria-relevant="all">
					<#assign row = rownum>
					<#list attendanceList as attendance>
						<tr class="even pointer" style="height:1px;">
							<td style="width:10%;">
								${row}
								<#assign row = row - 1>
							</td>
							<td style="width:18%;">${attendance.userName?if_exists}</td>
							<td style="width:18%;">${attendance.equipName?if_exists}</td>
							<td style="width:18%;">${attendance.macAddress?if_exists}</td>
							<td style="width:18%;">
								<#if attendance.type??>
									<#if attendance.type == "1">출근</#if>
									<#if attendance.type == "0">퇴근</#if>
								</#if>
							</td>
							<td style="width:18%;">${attendance.regDate?string("YYYY-MM-dd HH:mm")?if_exists}</td>
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
						</td>
					</tr>
				</table>
			</div>
			
		</form>
	</div>
</body>
</html>