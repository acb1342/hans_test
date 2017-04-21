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
		
		var chart = echarts.init(document.getElementById("energy"));
		var eChart = echarts.init(document.getElementById('equip'));
		var co2chart = echarts.init(document.getElementById("co2"));
        var chargeChart = echarts.init(document.getElementById("charge"));

		jQuery.ajax({
			type : "GET",
			url : "/admin/dashboard/energy.json",
			dataType : "JSON",
			success : function(data) {
				console.log("SUC data = " +JSON.stringify(data));
	        	   
	        	display_chart(data);
	        	display_co2Chart(data);
                display_chargeChart(data);
	        	   
	         },
			complete : function(data) {
			},
			error : function(xhr, status, error) {
				console.log("error");

			}
		});
		
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
		
		function display_chart(data) {				
			var ObSsesOn = new Object();				//SSES 사용 obj
			var ObSsesOff = new Object();				//SSES 미사용 obj
		
			// chart 한 블럭 구성
			ObSsesOn.name = 'SSES사용전력';
			ObSsesOn.type = 'line';
			ObSsesOn.data = data.onData;
			ObSsesOn.label = {normal : {show : false, position : 'top', textStyle:{color:'#000000'}}};
			ObSsesOn.itemStyle = {normal : {color:'#df5353'}}
			
			ObSsesOff.name = 'SSES미사용전력';
			ObSsesOff.type = 'line';
			ObSsesOff.data = data.offData;
			ObSsesOff.label = {normal : {show : false, position : 'top', textStyle:{color:'#000000'}}};
			ObSsesOff.itemStyle = {normal : {color:'#6799ff'}}
			
			var option = {
				tooltip: {
					trigger: 'axis'
			    },
				xAxis : [ {
					name : '(time)',
					type : 'category',
					data : data.category,
					nameLocation : 'end',
					nameTextStyle: {fontWeight:"bold"}
				} ],
				
				yAxis : [ {
					name : '(kwh)',
					type : 'value',
					nameLocation : 'end',
					nameTextStyle: {fontWeight:"bold"}
				} ],
				grid: {
			        left: '5%',
					 right: '13%',
			        top: '12%',
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
		
		function display_co2Chart(data) {
			var ObCo2 = new Object();				//CO2 배출량

			// chart 한 블럭 구성
			ObCo2.name = 'CO2 배출량';
			ObCo2.type = 'bar';
			ObCo2.data = data.co2Data;
			ObCo2.label = {normal : {show : false, position : 'top', textStyle:{color:'#000000'}}};
			ObCo2.itemStyle = {normal : {color:'#6B8E23'}}
			
			var option = {
				tooltip: {
					trigger: 'axis'
			    },
				xAxis : [ {
					name : '(time)',
					type : 'category',
					data : data.category,
					nameLocation : 'end',
					nameTextStyle: {fontWeight:"bold"}
				} ],
				
				yAxis : [ {
					name : '(ton)',
					type : 'value',
					nameLocation : 'end',
					nameTextStyle: {fontWeight:"bold"}
				} ],
				grid: {
			        left: '5%',
					 right: '13%',
			        top: '12%',
			        bottom: '15%',
			        containLabel:true
			    },
				legend : {
					data : ["CO2 배출량"], //범례
					bottom : true
				},
				series : [ObCo2]
			};
			 co2chart.setOption(option);
		}

        function display_chargeChart(data) {
            var ObCharge = new Object();				//적용 요금
			var OfCharge = new Object();
            // chart 한 블럭 구성
            ObCharge.name = '적용 요금';
            ObCharge.type = 'bar';
            ObCharge.data = data.onChargeData;

            OfCharge.name = '미적용 요금';
            OfCharge.type = 'bar';
            OfCharge.data = data.offChargeData;

            var option = {
                tooltip: {
                    trigger: 'axis'
                },
                xAxis : [ {
                    name : '(time)',
                    type : 'category',
                    data : data.category,
                    nameLocation : 'end',
                    nameTextStyle: {fontWeight:"bold"}
                } ],

                yAxis : [ {
                    name : '(won)',
                    type : 'value',
                    nameLocation : 'end',
                    nameTextStyle: {fontWeight:"bold"}
                } ],
                grid: {
                    left: '5%',
                    right: '13%',
                    top: '12%',
                    bottom: '15%',
                    containLabel:true
                },
                legend : {
                    data : ["적용 요금" , "미적용 요금"], //범례
                    bottom : true
                },
                series : [ObCharge, OfCharge]
            };
            chargeChart.setOption(option);
        }
		
		
		function display_eChart(dataArr, charttype) {
			if (charttype != 'equip') return;
			
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
				        text: '에너지 사용 점유율(w)',
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'item',
				        formatter: '{c0} ({d}%)' //'{a} : {b0}<br/>{c0} ({d}%)<br/>'
				    },
				    legend: {
				        orient: 'vertical',
				        top: 'middle',
				        left: 'left',
				        data: arrLegend
				    },
				    series : [
				        {
				            name: 'name',
				            type: 'pie',
				            radius : '55%',
				            center: ['65%', '60%'],
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
	
	
		window.onresize = function() {
			chart.resize();
			eChart.resize();
			co2chart.resize();
		};
	
	});
	
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
         		<div id="energy" style="height:240px;"></div>         		
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
	       	<div id="co2" style="height:240px;"></div>
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
	       	<div id="equip" style="height:240px;"></div>
	       </div>
	     </div>
	   </div>

        <div class="col-sm-6 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>전력 요금 현황 <small></small></h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div id="charge" style="height:240px;"></div>
                </div>
            </div>
        </div>
	
	 </div>
 </div>
</body>
</html>