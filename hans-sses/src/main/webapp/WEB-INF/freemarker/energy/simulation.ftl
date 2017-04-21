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

				
	});
		 
	function getChart(){

		var formData = $("#vForm").serialize();
		
		jQuery.ajax({
	           type:"GET",
	           url:"/energy/simulation/status.json",
	           data: formData,
	           dataType:"JSON",
	           success : function(data) {	        	   
	        	 
	        	   display_chart1(data);
                   display_chart2(data);
	        	   
	           },
	           complete : function(data) {
	        	   console.log("com data = " +JSON.stringify(data));
	           },
	           error : function(xhr, status, error) {
	        	   console.log("error");
	                
	           }
	     });
		       
	}
	
	function display_chart1(data){
	    var chart2 = echarts.init(document.getElementById("chart1"));
	    var str = JSON.stringify(data.data);

	    var newArr = JSON.parse(str);

		var arr0 = newArr[0];
        var arr1 = newArr[1];


        option = {
            tooltip : {
                trigger: 'axis',
                axisPointer : {
                    type : 'shadow'
                }
            },
            legend: {
                data:['미적용요금','적용요금']
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
                    data : ['요금표']
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'미적용요금',
                    type:'bar',
                    barWidth : 80,
                    data:[arr0]
                },
                {
                    name:'적용요금',
                    type:'bar',
                    barWidth : 80,
                    data:[arr1]
                }
            ]
        };

		chart2.setOption(option);

	}

    function display_chart2(data){
        var chart2 = echarts.init(document.getElementById("chart2"));
        var str = JSON.stringify(data.data);

        var newArr = JSON.parse(str);

        var arr2 = newArr[2];
        var arr3 = newArr[3];

        option = {
            tooltip : {
                trigger: 'axis',
                axisPointer : {
                    type : 'shadow'
                }
            },
            legend: {
                data:['미적용전력량','적용전력량']
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
                    data : ['전력량']
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [

                {
                    name:'미적용전력량',
                    type:'bar',
                    barWidth : 80,
                    itemStyle: {
                        normal : {color:'#000002'}
                    },
                    data:[arr2]
                },
                {
                    name:'적용전력량',
                    type:'bar',
                    barWidth : 80,
                    itemStyle: {
                        normal : {color:'#6B8E23'}
                    },
                    data:[arr3]
                }
            ]
        };

        chart2.setOption(option);

    }

</script>
</head>
<body>
	<div class="x_content">

		<form method="get" id="vForm" name="vForm" onsubmit="return false;">
			<div id="searchBox" style="height:100px; margin-bottom:1%">

				<div class="form-group" style="height:40px;">
                    <div class="col-sm-2">
                        <select class="form-control" name="selectDay" id="selectDay">
                            <option value="1" selected="selected">1일</option>
                            <option value="7">일주일</option>
                            <option value="31">한달</option>
						</select>
                    </div>
                    <div class="col-sm-2">
                        <select class="form-control" name="selectHour" id="selectHour">
							<#list 1..24 as i>
                            <option value="${i}">${i}시간</option>
							<#assign i=i+1?int/>
							</#list>
                        </select>
                    </div>
                    <div class="col-sm-2">
                        <select class="form-control" name="selectCharge" id="selectCharge">
                            <option value="60.7" selected="selected">한국전력</option>
                            <option value="40.5">미국전력</option>
                        </select>
                    </div>
					<div class="col-sm-1">
						<input type="button" class="btn btn-dark" value="조회" onclick="javascript:getChart()"/>
					</div>
				</div>
			</div>


		
		</form>
        <div class="row">
            <div >
			<div id="chart1" class="col-sm-6 col-xs-12" style="height:300px;"></div>
			<div id="chart2" class="col-sm-6 col-xs-12" style="height:300px;"></div>
		</div>
	</div>
</body>
</html>
