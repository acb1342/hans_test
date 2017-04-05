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
		var chart = echarts.init(document.getElementById("chart"));
		option = {
			    color: ['#3398DB'],
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {    
			            type : 'shadow' 
			        }
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    xAxis : [
			        {
			            type : 'category',
			            data : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
			            axisTick: {
			                alignWithLabel: true
			            }
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'aa',
			            type:'bar',
			            barWidth: '60%',
			            data:[10, 52, 200, 334, 390, 330, 220]
			        }
			    ]
			};
			chart.setOption(option);
		
	});
		 
	function getChart(){
		
		if(document.getElementById("searchValue").value == ''){
			alert("검색어를 입력하세요.");
			return;
		} 
		else if(document.getElementById("searchType").value == 'user' 
				&& isNaN(document.getElementById("searchValue").value)) {
			alert("사용자는 숫자만 입력 가능합니다.");
			return;
		}
		
		
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
</script>
</head>
<body>
<div class="x_content">
	<div class="row">
	
	   <div class="col-sm-6 col-xs-12">
	     <div class="x_panel">
	       <div class="x_title">
	         <h2>에너지 사용 현황 (금일) <small></small></h2>
	         <div class="clearfix"></div>
	       </div>
	       <div class="x_content">
         		<div id="chart" style="height:350px;"></div>
	         
	       </div>
	     </div>
	   </div>
	   
	   <div class="col-sm-6 col-xs-12">
	     <div class="x_panel">
	       <div class="x_title">
	         <h2>CO2 배출 현황 <small></small></h2>
	         <div class="clearfix"></div>
	       </div>
	       <div class="x_content">
	       <div id="chart2" style="height:350px;"></div>
	         
	       </div>
	     </div>
	   </div>
	   
	   <div class="col-sm-6 col-xs-12">
	     <div class="x_panel">
	       <div class="x_title">
	         <h2>등록 장비 현황 <small>Sessions</small></h2>
	         <div class="clearfix"></div>
	       </div>
	       <div class="x_content">
	       <div id="chart3" style="height:350px;"></div>
	         
	       </div>
	     </div>
	   </div>
	   	
	
	 </div>
 </div>

</body>
</html>