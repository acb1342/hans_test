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
		calendarEvent();
	});

	function calendarEvent(eventData) {
		$("#calender").html("");
		
		var lang_cd = 'ko';
		$('#calender').fullCalendar({
			header: {
				left: 'today prev,next',
           	center: 'title',
           	right: 'month'
			},
			defaultDate: moment().format('YYYY-MM-DD'),
	       //locale: initialLocaleCode,
	       //editable: true,
	       navLinks: true,
	       eventLimit: true,
			events: [{title:'aaa', start:'2017-04-14', end:'2017-04-15'},{title:'aaa', start:'2017-04-14'},{title:'aaa', start:'2017-04-14'},{title:'aaa', start:'2017-04-14'},{title:'aaa', start:'2017-04-14'},{title:'bbb', start:'2017-04-14'}]
	       /*  events: function(start, end, timezone, callback) {
	            $.ajax({
	                url: '/test/eventAll.do',
	                type : 'post',
	                data : {EVENT_CODE : '11', LANG : lang_cd, startDate : start.format(), endDate : end.format() },
	                dataType: 'json',
	                success: function(data) {
	                    var events = [];
	                    $(data).each(function() {
	                        events.push({
	                            title: $(this).attr('title'),
	                            start: $(this).attr('start'),
	                            end: $(this).attr('end'),
	                            url: "/test/eventDetail.do?id="+$(this).attr('id')+"&amp;lang="+$(this).attr('lang')+"&amp;start="+$(this).attr('start')+"&amp;end="+$(this).attr('end'),
	                            lang : $(this).attr('lang')
	                        });
	                    });
	                    callback(events);
	                }
	            });
	        } */
	      
	    });
	}
	
</script>
<style type="text/css">
	#calender {max-width:65%;margin:1% 0 1% 0;"}
</style>
</head>
<body >
	<div class="x_content">
		<div id="calender"></div>
	</div>
</body>
</html>