<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>SSES</title>

<script type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
<script src="/css/gentelella-master/vendors/echarts/dist/echarts.min.js"></script>
<script src="/css/gentelella-master/vendors/jquery/dist/jquery.min.js"></script>
<script src="/css/gentelella-master/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="/css/gentelella-master/vendors/moment/min/moment.min.js"></script>

<script src="/css/gentelella-master/vendors/bootstrap-daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="/js/jquery/ui/month/jquery.mtz.monthpicker.js"></script>
<link href="/css/common.css" rel="stylesheet">
<link href="/css/gentelella-master/vendors/bootstrap-daterangepicker/jquery-ui.css" rel="stylesheet"/>

<style type="text/css">
.ui-datepicker-header.mtz-monthpicker  {background:#1ABB9C;}		/* 월달력  타이틀 배경색 */
</style>

<script type="text/javascript">

	$(function() {
				
		var date = new Date();
		var year  = date.getFullYear();
       var month = date.getMonth() + 1
       month = month >= 10 ? month : '0' + month;
		
		month_options = {
				pattern: 'yyyymm',
				monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']		
		};
		$('.selMonthDate').monthpicker(month_options);
		$('.selMonthDate').val(String(year)+String(month));
		
		$('.selDate').daterangepicker({			
	        singleDatePicker: true,
	        singleClasses: "picker_3",
	        locale : {
	            format: "YYYYMMDD",
	            monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	            daysOfWeek: ['일','월', '화', '수', '목', '금', '토']
	        }
	    }, function(start, end, label) {
	        console.log(start.toISOString(), end.toISOString(), label);
	    });
		
		$('#iradioDay').click(function() {
			$("#iradioDay").prop("class","iradio_flat-green checked");
			$("#iradioMon").prop("class","iradio_flat-green");
			$("input:radio[id='radioDay']").prop("checked", true);
			$("input:radio[id='radioMon']").prop("checked", false);
			
			$("#monthSearchform").hide();
			$("#daySearchform").show();
			
		});
		
		$('#iradioMon').click(function() {
			$("#iradioMon").prop("class","iradio_flat-green checked");
			$("#iradioDay").prop("class","iradio_flat-green");
			$("input:radio[id='radioMon']").prop("checked", true);
			$("input:radio[id='radioDay']").prop("checked", false);
			
			$("#daySearchform").hide();
			$("#monthSearchform").show();
		});
				
	});
		 
	function getChart(){
		
		if(document.getElementById("searchValue").value == ''){
			alert("검색어를 입력하세요.");
			return;
		}
		
		var formData = $("#vForm").serialize();
		
		jQuery.ajax({
	           type:"POST",
	           url:"/dashboard/energy/status.json",
	           data: formData,
	           dataType:"JSON",
	           success : function(data) {
	        	   console.log("SUC data = " +JSON.stringify(data));
	        	   display_chart(data);
	        	   
	           },
	           complete : function(data) {
	           },
	           error : function(xhr, status, error) {
	        	   console.log("error");
	                
	           }
	     });   
	}
	
	function display_chart(data) {
		
		var strData = JSON.stringify(data.series);
		var dataArr = JSON.parse(strData);

		if(dataArr.length!=0){				
			var chart = echarts.init(document.getElementById("chart"));
			var ObSeries = new Object();
			var ArYaxis = new Array();
			var legend;
					
			if (data.searchType == 'user') legend = dataArr[0].userName;
			else legend = dataArr[0].macAddress;
	
			for (i = 0; i < dataArr.length; i++) {
				ArYaxis.push(dataArr[i].regDate);
	
			}
			
			ObSeries.name = name;
			ObSeries.type = 'line';	
			ObSeries.data = data.data;
			ObSeries.label = {normal : {show : true, position : 'top', textStyle:{color:'#000000', fontWeight:'bold'}}};
	
			var option = {
				title : {
					text : '총 전력량' //타이틀
				},
				tooltip: {
					trigger: 'axis'
			    },
				legend : {
					data : [legend], //범례
					bottom : true
				},
				xAxis : [ {
					type : 'category',
					data : ArYaxis
				//카테고리
				} ],
				yAxis : [ {
					name : '(Kw)',
					type : 'value',
					nameLocation : 'end',
					nameTextStyle: {fontWeight:"bold"}
				} ],
				series : ObSeries			
	
			};
			chart.setOption(option);
			window.onresize = function() {
				chart.resize();
			};		
		}
		else{
			alert("검색결과 없음");
		}
	}
	
</script>
</head>
<body>
	<div class="x_content">

		<form method="get" id="vForm" name="vForm" onsubmit="return false;">
			<div id="searchBox" style="height:100px; margin-bottom:1%">
			
				<div class="form-group" style="line-height:34px; height:34px;">
					<div class="col-sm-2">
						<div class="iradio_flat-green checked" style="position: relative;" id="iradioDay">
							<input type="radio" class="flat" id="radioDay" name="radioDate" value="D" checked='checked' style="position: absolute; opacity: 0;" >
						</div>&nbsp;일별&nbsp;
						<div class="iradio_flat-green" style="position: relative;" id="iradioMon">
							<input type="radio" class="flat" id="radioMon" name="radioDate" value="M" style="position: absolute; opacity: 0;">
						</div>&nbsp;월별
					</div>
					<div class="col-sm-4" id="daySearchform" style="padding:0px;">
						<div class="col-sm-5">
							<input class="selDate form-control" type="text" id="beforeday" name="beforeday" readonly>
						</div>
						<div class="col-sm-2" style="line-height:40px; text-align:center">~</div>
						<div class="col-sm-5">
							<input class="selDate form-control" type="text" id="afterday" name="afterday" readonly>
						</div>
					</div>
					
					<div class="col-sm-4" id="monthSearchform" style="display:none; padding:0px;">
						<div class="col-sm-5">
							<input class="selMonthDate form-control" type="text" id="monthbeforeday" name="monthbeforeday" readonly>
						</div>
						<div class="col-sm-2" style="line-height:40px; text-align:center">~</div>
						<div class="col-sm-5">
							<input class="selMonthDate form-control" type="text" id="monthafterday" name="monthafterday" readonly>
						</div>
					</div>
				</div>
			
				<div class="form-group" style="height:40px;">
					<div class="col-sm-2">
						<select class="form-control" name="searchType" id="searchType">
							<option value="user">사용자</option> 
							<option value="equip">장비</option>
						</select>
					</div>
					<div class="col-sm-4">
						<input type="text" class="form-control" name="searchValue" id="searchValue" onkeypress="if (event.keyCode == 13) {getChart();}" />
					</div>
					<div class="col-sm-4">
						<input type="button" class="btn btn-dark" value="조회" onclick="javascript:getChart()"/>
					</div>
				</div>
			</div>
		
		
		</form>
		<div id="chart" style="width: 100%; height:500px; margin-top:50px"></div>

	</div>
</body>
</html>