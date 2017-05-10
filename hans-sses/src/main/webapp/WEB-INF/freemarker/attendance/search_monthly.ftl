<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>SSES</title>
<link rel="stylesheet" type="text/css" href="/css/gentelella-master/vendors/fullcalendar/dist/fullcalendar.css"/>
<link rel="stylesheet" type="text/css" href="/css/gentelella-master/vendors/fullcalendar/dist/fullcalendar.min.css"/>
<script src="/css/gentelella-master/vendors/moment/min/moment.min.js"></script>
<script src="/css/gentelella-master/vendors/fullcalendar/dist/fullcalendar.js"></script>
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
			//allDaySlot: false,
			titleFormat: 'YYYY년 MMMM',
			//monthYearFormat: 'YYYY년 MMMM',
			timeFormat: 'H:mm',
			buttonText: {
				today: '오늘',
				month: '월별',
				basicWeek: '주별',
				basicDay: '일별'
			},
			/* customButtons: {
				customDay: {
					text: '일별조회',
					click: function() {searchDayList(1);}
				}
			}, */
			header: {
				left: 'prev,today,next',
           	center: 'title',
           	right: 'month,basicWeek'//,basicDay'//,customDay'
			},
			defaultDate: moment().format('YYYY-MM-DD'),
			monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'],
			dayNamesShort: ['일','월','화','수','목','금','토'],
	       //navLinks: true,
	       eventLimit: true,
	       dayPopoverFormat: 'MMMM D일, dddd',
	       events: data,
	       eventOrder:['order', 'regTime']
		});
	}
	      
	function searchDayList(page) {
		$("#page").val(page);
		var formData = $("#vForm").serialize();
		var url = "/attendance/daily/search.htm";
		$.ajax({
			type : "POST",
			url : url,
			data : formData,			
			success : function(response){
				$(".fc-view-container").html(response);
			},
			error : function(){
				console.log("error!!");
				return false;
			}
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