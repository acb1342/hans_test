<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>SSES</title>
<link rel="stylesheet" type="text/css" href="/css/gentelella-master/vendors/fullcalendar/dist/fullcalendar.css"/>
<link rel="stylesheet" type="text/css" href="/css/gentelella-master/vendors/fullcalendar/dist/fullcalendar.min.css"/>
<script src="/css/gentelella-master/vendors/moment/min/moment.min.js"></script>
<script src="/css/gentelella-master/vendors/fullcalendar/dist/fullcalendar.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#calender').html("");
		calendarEvent();
	});

	function calendarEvent(eventData) {
		var data;
		$.ajax({
			url: '/attendance/monthly/calendarData.json',
			type : 'POST',
			async:false,
			success: function(response) {
             	data = response;
			}
		});
		
		$('#calender').fullCalendar({
			header: {
				left: 'today prev,next',
           	center: 'title',
           	right: ''//'month'
			},
			defaultDate: moment().format('YYYY-MM-DD'),
	       //navLinks: true,
	       eventLimit: true,
	       events: data,
	       eventOrder:["order","regTime"]
		});
	}
	      
</script>
<style type="text/css">
	#calender {max-width:70%;margin:1% 0 1% 0;}
</style>
</head>
<body >
	<div class="x_content">
		<div id="calender"></div>
	</div>
</body>
</html>