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
<script type="text/javascript">
	$(function() {
		
		$('.selDate').daterangepicker({			
	        singleDatePicker: true,
	        singleClasses: "picker_3",
	        locale : {
	            format: "YYYYMMDD"
	        }
	    }, function(start, end, label) {
	        console.log(start.toISOString(), end.toISOString(), label);
	    });
		
		$('#iradioDay').click(function() {
			$("#iradioDay").prop("class","iradio_flat-green checked");
			$("#iradioMon").prop("class","iradio_flat-green");
			$("input:radio[id='radioDay']").prop("checked", true);
			$("input:radio[id='radioMon']").prop("checked", false);
		});
		
		$('#iradioMon').click(function() {
			$("#iradioMon").prop("class","iradio_flat-green checked");
			$("#iradioDay").prop("class","iradio_flat-green");
			$("input:radio[id='radioMon']").prop("checked", true);
			$("input:radio[id='radioDay']").prop("checked", false);
		});
				
	});
		 
	function getChart(){
		var formData = $("#vForm").serialize();
		
		jQuery.ajax({
	           type:"GET",
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
		var name;		
				
		if (data.searchType == 'user') {
			name = dataArr[0].userSeq;
		}
		else if (data.searchType == 'company') {
			//searchType = 'userSeq';
		}
		else {
			name = dataArr[0].macAddress;
		}

		var ObStatus = new Object();

		var ArSeries = new Array();
		var ArLegend = new Array();
		var ArYaxis = new Array();

		var ArData = new Array();
		var ObSeries = new Object();

		ObSeries.name = String(name);
		ObSeries.type = 'line';

		for (i = 0; i < dataArr.length; i++) {

			ArData.push(dataArr[i].watt);
			ArYaxis.push(dataArr[i].regDate);

		}

		ObSeries.data = ArData;
		ObSeries.label = {
			normal : {
				show : true,
				position : 'top'
			}
		};

		ArSeries.push(ObSeries);
		ArLegend.push(String(name));

		ObStatus.series = ArSeries;
		ObStatus.legend = ArLegend;
		ObStatus.aryaxis = ArYaxis;

		console.log(ObStatus.series);
		console.log(ObStatus.legend);
		console.log(ObStatus.aryaxis);

		var option = {
			title : {
				text : '전력량' //타이틀
			},
			tooltip : {},
			legend : {
				data : ObStatus.legend, //범례
				bottom : true
			},
			xAxis : [ {
				data : ObStatus.aryaxis
			//카테고리
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			series : ObStatus.series

		};
		chart.setOption(option);
		
		
		}
		else{
			alert("검색결과 없음");
		}

	}

	function search_form() {

		$("#idenSearchform").hide();
		$("#equipSearchform").hide();

		if (document.getElementById("searchType").value == 'identity') {
			$("#idenSearchform").show();
		} else if (document.getElementById("searchType").value == 'equip') {
			$("#equipSearchform").show();
		}
	}
</script>
</head>
<body>
	<div class="x_content">

		<form method="get" id="vForm" name="vForm" onsubmit="return false;">
			<div id="searchBox" style="height:100px; margin-bottom:1%">
				<div class="form-group" style="vertical-align:middle; height:40px;">
					<div class="col-sm-2">
						<div class="iradio_flat-green checked" style="position: relative;" id="iradioDay">
							<input type="radio" class="flat" id="radioDay" name="radioDate" value="D" checked='checked' style="position: absolute; opacity: 0;" >
						</div>일별
						<div class="iradio_flat-green" style="position: relative;" id="iradioMon">
							<input type="radio" class="flat" id="radioMon" name="radioDate" value="M" style="position: absolute; opacity: 0;">
						</div>월별
					</div>
					<div class="col-sm-7" id="equipSearchform">
						<div class="col-sm-3">
							<input class="selDate form-control" type="text" id="beforeday" name="beforeday" readonly>
						</div>
						<div class="col-sm-1" style="line-height:34px; text-align:center">~</div>
						<div class="col-sm-3">
							<input class="selDate form-control" type="text" id="afterday" name="afterday" readonly>
						</div>
					</div>
				</div>
			
				<div class="form-group" style="height:40px;">
					<div class="col-sm-2">
						<select class="form-control" name="searchType" id="searchType">
							<option value="user">사용자</option> 
							<option value="company">조직</option>
							<option value="equip">장비</option>
						</select>
					</div>
					<div class="col-sm-4">
						<input type="text" class="form-control" name="searchValue" id="searchValue" onkeypress="if (event.keyCode == 13) {getChart();}" />
					</div>
					<div class="col-sm-2">
						<input type="button" class="btn btn-dark" value="조회" onclick="javascript:getChart()"/>
					</div>
				</div>
			</div>
		
		
		</form>
		<div id="chart" style="width: 100%; height:500px; margin-top:50px"></div>

	</div>
</body>
</html>