<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>SSES</title>

<script type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
<script src="/css/gentelella-master/vendors/echarts/dist/echarts.js"></script>
<script src="/css/gentelella-master/vendors/jquery/dist/jquery.min.js"></script>
<script src="/css/gentelella-master/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="/css/gentelella-master/vendors/moment/min/moment.min.js"></script>
<script src="/css/gentelella-master/vendors/bootstrap-daterangepicker/daterangepicker.js"></script>
<script type="text/javascript">
	
	$(function() {

		jQuery.ajax({
			type : "GET",
			url : "/admin/dashboard/energy.json",
			dataType : "JSON",
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
		
		/* 
		$(window).resize( respondCanvas );

	    function respondCanvas(){
	    	
	    	alert($("#energy").getElementsByTagName("canvas").width());
	    }

	    //Initial call
	    respondCanvas();
 */
	});

	function display_chart(data) {
		
		var strData = JSON.stringify(data.series);
		var dataArr = JSON.parse(strData);
		
		var chart = echarts.init(document.getElementById("energy"));

		var ObSsesOn = new Object();				//SSES 사용 obj
		var ObSsesOff = new Object();				//SSES 미사용 obj
		
		// chart 한 블럭 구성
		ObSsesOn.name = 'SSES사용전력';
		ObSsesOn.type = 'line';
		ObSsesOn.data = data.onData;
		ObSsesOn.label = {normal : {show : false, position : 'top', textStyle:{color:'#000000'}}};
		
		ObSsesOff.name = 'SSES미사용전력';
		ObSsesOff.type = 'line';
		ObSsesOff.data = data.offData;
		ObSsesOff.label = {normal : {show : false, position : 'top', textStyle:{color:'#000000'}}};

		var option = {
			tooltip: {
				trigger: 'axis'
		    },
			xAxis : [ {
				name : '(Hour)',
				type : 'category',
				data : data.category,
				nameLocation : 'end',
				nameTextStyle: {fontWeight:"bold"}
			} ],
			
			yAxis : [ {
				name : '(Kw)',
				type : 'value',
				nameLocation : 'end',
				nameTextStyle: {fontWeight:"bold"}
			} ],
			grid: {
		        left: '0%',
				 right: '9%',
		        top: '10%',
		        bottom: '15%',
		        containLabel:true
		    },
			legend : {
				data : ["SSES사용전력", "SSES미사용전력"], //범례
				bottom : true
			},
			series : [ObSsesOn,ObSsesOff]
		};

		chart.setOption(option);

	}
	
	$(function() {
		jQuery.ajax({
			type : "GET",
			url : "/admin/dashboard/equip.json",
			dataType : "JSON",
			success : function(data) {
				var strData = JSON.stringify(data);
				var dataArr = JSON.parse(strData);
				if (dataArr.length != 0) {
					display_eChart(dataArr, 'equip');
				}
			},
			complete : function(data) {
			},
			error : function(xhr, status, error) {
				console.log("error");

			}
		});
	});
	
	function display_eChart(dataArr, charttype) {
		if (charttype != 'equip') return;
		
		var eChart = echarts.init(document.getElementById('equip'));
		
		var objSeries = new Array();
		var arrLegend = new Array();
		for (var i in dataArr) {
			var series = new Object();
			series.value = dataArr[i].watt;
			series.name = dataArr[i].equipName;
			objSeries.push(series);
			
			arrLegend[i] = dataArr[i].equipName;
		}
		
		var option = {
			    title : {
			        text: '에너지 사용 점유율(Kw)',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: '{c0} ({d}%)' //'{a} : {b0}<br/>{c0} ({d}%)<br/>'
			    },
			    legend: {
			        orient: 'vertical',
			        top: 'top',
			        left: 'left',
			        data: arrLegend
			    },
			    series : [
			        {
			            name: 'name',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data: objSeries,
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                }
			            }
			        }
			    ]
			};
		
		eChart.setOption(option);
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
         	
         	<div id="energy" style="height:260px;"></div>
         		
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
	       <div id="co2" style="height:260px;"></div>
	       
	         
	       </div>
	     </div>
	   </div>
	   
	   <div class="col-sm-6 col-xs-12">
	     <div class="x_panel">
	       <div class="x_title">
	         <h2>등록 장비 현황 <small></small></h2>
	         <div class="clearfix"></div>
	       </div>
	       <div class="x_content">
	       <div id="equip" style="height:260px;"></div>
	         
	       </div>
	     </div>
	   </div>
	   	
	
	 </div>
 </div>

</body>
</html>