<script src="/css/gentelella-master/vendors/echarts/dist/echarts.min.js"></script>
<script type="text/javascript">
	$(function() {
		
        
	});
	/* 
	function getChart(){
		var chart2 = echarts.init(document.getElementById("chart2"));
		var day = document.getElementById("day").value;


		console.log("day= " + day);
		
		jQuery.ajax({
	           type:"GET",
	           url:"/dashboard/energy/status.json",
	           data: {day:day},
	           dataType:"JSON",
	           success : function(data) {
	        	   var option = {
    		           title: {
    		               text: '일 전력량'                                  //타이틀
    		           },
    		           tooltip: {
    		           },
    		           legend: {
    		               data: data.legend                            //범례
    		           },
    		           xAxis: [{
    		               type: 'value'
    		           }],
    		           yAxis: [{
    		               type: 'category',
    		               data: [day]                              //카테고리
    		           }],
    		           series: data.series
    		           
    		       };
    		       chart2.setOption(option);
	           },
	           complete : function(data) {
	        	   console.log("com data = " +JSON.stringify(data));
	           },
	           error : function(xhr, status, error) {
	        	   console.log("error");
	                
	           }
	     });
		 */
		 
	function getChart(){
		var chart2 = echarts.init(document.getElementById("chart2"));
		var beforeday = document.getElementById("beforeday").value;
		var afterday = document.getElementById("afterday").value;
		
		
		jQuery.ajax({
	           type:"GET",
	           url:"/dashboard/energy/status.json",
	           data: {beforeday:beforeday,afterday:afterday},
	           dataType:"JSON",
	           success : function(data) {
	        	   //console.log("suc data = " +JSON.stringify(data));
	        	   
	        	   
	        	   var str = JSON.stringify(data);

	        	   var newArr = JSON.parse(str);
	        	   
	        	   console.log(newArr[0].indentityCode);
	        	   
	        	   
	        	   var ObStatus = new Object();
	        	   
	        	   var ArSeries = new Array();
	        	   var ArLegend = new Array();
	        	   var ArYaxis = new Array();

	        	   for(i=0 ; i<newArr.length ; i++){
	        		   var ObSeries = new Object();
	        		   var ArData = new Array();
	        		   
	        		   ArData.push(newArr[i].sumPower);
	        		   
	        		   ObSeries.name=newArr[i].identityCode;
	        		   ObSeries.type='bar';
	        		   ObSeries.data=ArData;
	        		   
	        		   ArSeries.push(ObSeries);
	        		   ArLegend.push(newArr[i].identityCode);
	        		   ArYaxis.push(newArr[i].identityCode)
	        	   }
	        	   
	        	   ObStatus.series=ArSeries;
	        	   ObStatus.legend=ArLegend;
	        	   
	        	    var option = {
    		           title: {
    		               text: '전력량'                                  //타이틀
    		           },
    		           tooltip: {
    		           },
    		           legend: {
    		               data: ObStatus.legend                            //범례
    		           },
    		           xAxis: [{
    		               type: 'value'
    		           }],
    		           yAxis: [{
    		               type: 'category',
    		               data: ObStatus.legend                             //카테고리
    		           }],
    		           series: ObStatus.series
    		           
    		       };
    		       chart2.setOption(option); 
	           },
	           complete : function(data) {
	        	   console.log("com data = " +JSON.stringify(data));
	           },
	           error : function(xhr, status, error) {
	        	   console.log("error");
	                
	           }
	     });
		
		       
	}
	
</script>
<input type="text" id="beforeday" name="beforeday"  onkeypress="if (event.keyCode == 13) {getChart();}">
<input type="text" id="afterday" name="afterday"  onkeypress="if (event.keyCode == 13) {getChart();}">
<input type="button" value="차트" onClick="javascript:getChart();">
<div id="chart2" style="width: 100%; height:500px;"></div>