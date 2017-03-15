function changeSelectOption1() {
}

function changeSelectOption2() {
}

function validateSearchForm() {
	var fromDateStr = $("#datepicker1").val();
	var toDateStr = $("#datepicker2").val();
	var fromLimitedDay = null;
	var toLimitedDay = null;
	var fromDate = null;
	var toDate = null;
	
	if (fromDateStr != null && fromDateStr.length > 0 && toDateStr != null && toDateStr.length > 0) {
		var today = new Date();
		var from_year = parseInt(today.getFullYear());
		var from_month = parseInt(today.getMonth()) - 3;
		var from_day = parseInt(today.getDate());
		var to_year = parseInt(today.getFullYear());
		var to_month = parseInt(today.getMonth());
		var to_day = parseInt(today.getDate()) - 1;
		
		if (from_month < 0) {
			from_year = from_year - 1;
			from_month = from_month + 12;
		}
		if (from_month == 1 && from_day > 28) {
			from_day = 28;
		}
		if (to_day == 0) {
			to_month = to_month - 1;
			if (to_month < 0) {
				to_year = to_year - 1;
				to_month = to_month + 12;
			}
			
			if (to_month == 1) {	
				to_day = 28;
			} else if (to_month == 0 || to_month == 2 || to_month == 4 || to_month == 6 || to_month == 7 || to_month == 9 || to_month == 11) {
				to_day = 31;
			} else if (to_month == 3 || to_month == 5 || to_month == 8	|| to_month == 10) {
				to_day = 30;
			}
		}
		
		fromLimitedDay = new Date(from_year, from_month, from_day, 0, 0, 0, 0);
		toLimitedDay = new Date(to_year, to_month, to_day, 0, 0, 0, 0);
		
		var fromDateArr = fromDateStr.split("/");
		var toDateArr = toDateStr.split("/");
		for (var i=0; i<fromDateArr.length; i++) {
			if (fromDateArr[i].indexOf("0") == 0) {
				fromDateArr[i] = fromDateArr[i].substring(1);
			}
		}
		for (var i=0; i<toDateArr.length; i++) {
			if (toDateArr[i].indexOf("0") == 0) {
				toDateArr[i] = toDateArr[i].substring(1);
			}
		}
		
		fromDate = new Date(parseInt(fromDateArr[2]), parseInt(fromDateArr[0])-1, parseInt(fromDateArr[1]),0, 0, 0, 0);
		toDate = new Date(parseInt(toDateArr[2]), parseInt(toDateArr[0])-1, parseInt(toDateArr[1]),0, 0, 0, 0);
	}
	
	if (fromDateStr == null || fromDateStr.length == 0) {
		alert("기간을 입력해주십시오");
		return false;
	} else if (toDateStr == null || toDateStr.length == 0) {
		alert("기간을 입력해주십시오");
		return false;
	} else if (fromDateStr.match("^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$") == null) {
		alert("유효한 날짜를 입력해주십시오.");
		return false;
	} else if (toDateStr.match("^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$") == null) {
		alert("유효한 날짜를 입력해주십시오.");
		return false;
	} else if (fromDate > toDate) {
		alert("기간 선택이 잘못되었습니다.");
		return false;
	} else if (fromDate < fromLimitedDay || toDate > toLimitedDay) {
		alert("검색 가능 기간을 초과하였습니다.");
		return false;
	} else{
		return true;
	}
}

function searchUserSubsStat() {
	if (validateSearchForm()) {
		var packageId = $("#packageId option:selected").val();
		var userType = $("#userType option:selected").val();
		var mobileType = $("#mobileType option:selected").val();
		var fromDate = $("#datepicker1").val();
		var toDate = $("#datepicker2").val();
		var query="?fromDate="+fromDate+"&toDate="+toDate+"&packageId="+packageId+"&userType="+userType+"&mobileType="+mobileType;
		
		location.href="/statistics/userSubs/search.htm" + query;
	}
}

function searchMsgCounterStat() {
	if (validateSearchForm()) {
		var packageId = $("#packageId option:selected").val();
		var mobileType = $("#mobileType option:selected").val();
		var fromDate = $("#datepicker1").val();
		var toDate = $("#datepicker2").val();
		var query="?fromDate="+fromDate+"&toDate="+toDate+"&packageId="+packageId+"&mobileType="+mobileType;
		
		location.href="/statistics/msgCounter/search.htm" + query;
	}
}