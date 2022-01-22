<!-- 예약 날짜 선택 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header/afterLoginHeader.jsp" %>
	<link href='../../fullcalendar/core/main.css' rel='stylesheet' />
	<link href='../../fullcalendar/daygrid/main.css' rel='stylesheet' />
	<link href='../../fullcalendar/timegrid/main.min.css' rel='stylesheet' />
	<script src='../../fullcalendar/core/main.js'></script>
	<script src='../../fullcalendar/daygrid/main.js'></script>
	<script src="../../fullcalendar/interaction/main.min.js"></script>
	<script src="../../fullcalendar/timegrid/main.min.js"></script></head>
	<script src="../../fullcalendar/list/main.js"></script></head>
	<script class="cssdesk" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.0/moment.min.js" type="text/javascript"></script>
	<script>
		document.addEventListener('DOMContentLoaded', function() {
			var calendarEl = document.getElementById('calendar');
		    var calendar = new FullCalendar.Calendar(calendarEl, {
				plugins: [ 'interaction', 'dayGrid' ],
				contentHeight: 600,
				selectable:true,
				select: function(arg) {											// 날짜 클릭시 이벤트 실행
					var start=moment(arg.start).format('YYYY-MM-DD');
					var today=moment().format('YYYY-MM-DD');
					if(moment(start.toString()).isBefore(today.toString())){	// 과거 날짜는 선택 불가
						alert("과거 날짜를 선택하셨습니다.");
					}
					else{
						location.href = "selectTime?date="+start;				// 날짜 선택 시 시간을 정하는 페이지로 이동
					}
				},
			});
    		calendar.render();
		});
		
	</script>
	<div id='calendar'></div>	
<%@ include file="../header/footer.jsp" %>