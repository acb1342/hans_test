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
	        	 
	        	   display_chart(data);
	        	   
	           },
	           complete : function(data) {
	        	   console.log("com data = " +JSON.stringify(data));
	           },
	           error : function(xhr, status, error) {
	        	   console.log("error");
	                
	           }
	     });
		       
	}
	//사용자별 총전력
	
	function display_chart(data){
		var chart2 = echarts.init(document.getElementById("chart"));

 	   var str = JSON.stringify(data);

 	   var newArr = JSON.parse(str);	        	   
 	   
 	   var ObStatus = new Object();
 	   
 	   var ArSeries = new Array();
 	   var ArLegend = new Array();
 	   var ArYaxis = new Array();

		var ArData = new Array();
 	   var ObSeries = new Object();
 	   
 		ObSeries.name=newArr[0].userSeq;
 		ObSeries.type='bar';
 		
 	   for(i=0 ; i<newArr.length ; i++){
 		   
 		   ArData.push(newArr[i].sumPower);
 		   ArYaxis.push(newArr[i].regDate);
 		 
 	   }
 	   
 	   ObSeries.data=ArData;
	   ObSeries.label= {
          normal: {
              show: true,
              position: 'top'
          }
      };
 	   
	   ArSeries.push(ObSeries);
	   ArLegend.push(newArr[0].userSeq);
 	   
 	   ObStatus.series=ArSeries;
 	   ObStatus.legend=ArLegend;
 	   ObStatus.aryaxis=ArYaxis;
 	   
 	   
 	  console.log("=============='");
 	   console.log(ObStatus.aryaxis);
 	   
 	    var option = {
	           title: {
	               text: '전력량'                                  //타이틀
	           },
	           tooltip: {
	           },
	           legend: {
	               data: ObStatus.legend,                            //범례
	               bottom: true
	           },
	           xAxis: [{     
	        	   type: 'category',
	               data: ObStatus.aryaxis                           //카테고리
	           }],
	           yAxis: [{
	               type: 'value'
	           }],
	           series: ObStatus.series
	           
	       };
 	    
 	    	console.log(option);
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
                        <select class="form-control" name="selectValue1" id="selectValue1">
                            <option value="1" selected="selected">1일</option>
                            <option value="7">일주일</option>
                            <option value="31">한달</option>
						</select>
                    </div>
                    <div class="col-sm-2">
                        <select class="form-control" name="selectValue2" id="selectValue2">
							<#list 1..24 as i>
                            <option value="${i}">${i}시간</option>
							<#assign i=i+1?int/>
							</#list>
                        </select>
                    </div>
					<div class="col-sm-1">
						<input type="button" class="btn btn-dark" value="조회" onclick="javascript:getChart()"/>
					</div>
				</div>
			</div>


		
		</form>
		<div id="chart" style="width: 100%; height:500px; margin-top:50px"></div>

	</div>
</body>
</html>
