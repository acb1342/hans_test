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

				console.log("SUC data = " + JSON.stringify(data));

				var strData = JSON.stringify(data);
				var dataArr = JSON.parse(strData);

				if (dataArr.length != 0) {
					display_chart(dataArr, "energy");
					display_chart(dataArr, "co2");
				}
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

	function display_chart(dataArr, charttype) {
		
		var chart = echarts.init(document.getElementById(charttype));
		
		var chartcolor;
		var data;

		var ObChart = new Object();
		var ObSeries = new Object();

		if (charttype == "energy") {
			data = dataArr[0].watt;
			chartcolor = '#ff0000';
		}
		else {
			data = dataArr[0].watt * 0.4836;
			chartcolor = '#0000ff';
		}
		
		if(String(data).indexOf('.') > -1){
			data = data.toFixed(2);
		}

		ObSeries.name = '';
		ObSeries.type = 'bar';
		ObSeries.barWidth = '30';
		ObSeries.data = [data];
		ObSeries.label = {
			normal : {
				show : true,
				position : 'top'
			}
		};
		ObChart.series = [ ObSeries ];

		var option = {
			xAxis : [ {
				type : 'category',
				data : []
			//카테고리
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			series : ObChart.series,
			color : [chartcolor]

		};

		chart.setOption(option);

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
	         <h2>등록 장비 현황 <small>Sessions</small></h2>
	         <div class="clearfix"></div>
	       </div>
	       <div class="x_content">
	       <div id="chart3" style="height:260px;"></div>
	         
	       </div>
	     </div>
	   </div>
	   	
	
	 </div>
 </div>

</body>
</html>