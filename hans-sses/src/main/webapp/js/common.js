Number.prototype.zf = function(len) { return this.toString().zf(len); };

String.prototype.replaceAll = function(from, to) { return this.replace(new RegExp(from, "g"), to); };
String.prototype.trim = function() { return this.replace(/(^\s*)|(\s*$)/g, ""); };
String.prototype.ltrim = function() { return this.replace(/(^\s*)/g, ""); };
String.prototype.rtrim = function() { return this.replace(/(\s*$)/g, ""); };
String.prototype.string = function(len) { var s = '', i = 0; while (i++ < len) { s += this; } return s; };
String.prototype.zf = function(len) { return "0".string(len - this.length) + this; };

if (typeof String.prototype.startsWith != 'function') {
	String.prototype.startsWith = function (str) { return this.indexOf(str) == 0; };
}

Date.prototype.format = function(f) {
    if (!this.valueOf()) { return " "; }

    var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
    var d = this;

    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
        switch ($1) {
            case "yy": return d.getFullYear();
            case "y": return (d.getFullYear() % 1000).zf(2);
            case "mm": return (d.getMonth() + 1).zf(2);
            case "dd": return d.getDate().zf(2);
            case "E": return weekName[d.getDay()];
            case "HH": return d.getHours().zf(2);
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
            case "mi": return d.getMinutes().zf(2);
            case "ss": return d.getSeconds().zf(2);
            case "a/p": return d.getHours() < 12 ? "오전" : "오후";
            default: return $1;
        }
    });
};

Date.prototype.getLastDayOfMonth = function() {
	var year = this.getYear();
	var month = this.getMonth();

	return (new Date((new Date(year, month + 1, 1)) - 1)).getDay();
};

// make the links disable
function disableLinks() {
	var objLinks = document.links;
	for (var i = 0; i < objLinks != null && objLinks.length; i++) {
		if (objLinks[i] == undefined) { continue; }
		if ((objLinks[i].href.indexOf("updateRefundFlag(") > 0)) {
			objLinks[i].disabled = true;
			objLinks[i].onclick = function() { return false; };
		}
	}
}

// append a query string to the links, the onclick script of buttons
function appendCallBackUrl(qs) {
	var objLinks = document.links;
	for (var i = 0; objLinks != null && i < objLinks.length; i++) {
		// console.log(i)
		if (objLinks[i] == undefined) { continue; }
		if ((objLinks[i].href.indexOf("detail.htm?") > 0)) {
			objLinks[i].href = objLinks[i].href + qs;
			// console.log(objLinks[i].href);
		}
	}
	
	var objButtons = $(":button");
	for (var i = 0; objButtons != null && i < objButtons.length; i++) {
		var script = null;
		// console.log(i);
		if (objButtons[i] == undefined) { continue; }
		script = objButtons[i].getAttribute('onclick');
		if (script != null) {
			script = script.substring(0, script.length - 1);
			if (script.indexOf("detail.htm?") > 0) {
				objButtons[i].setAttribute('onclick', script + qs + "'");
				// console.log(objButtons[i].getAttribute('onclick'));
			}
		}
	}
}

function getLastDateOfMonth(date) {
	var year = date.getFullYear();
	var month = date.getMonth();

	var date = new Date(year, month+1, 1);
	date.setDate(date.getDate() - 1);

	return date;
}

function hideElement(element) {
	element.style.display = 'none';
	element.style.visivility = 'hidden';
}

function displayElement(element) {
	element.style.display = 'block';
	element.style.visivility = 'visible';
}

function createOption(value, label) {
	var option = document.createElement("option");
	option.setAttribute("value", value);
	option.appendChild(document.createTextNode(label));

	return option;
}

function countByte(value) {
	var retVal = 0;
	for(var j=0;j<value.length;j++) {
		if (value.charCodeAt(j) > 255)
			retVal += 2;
		else
			retVal++;
	}
	return retVal;
}

function isNull(value) {
	return (value == null);
}

function isEmpty(value) {
	return (value == "");
}

function isEmptyOrWhitespace(value) {
	return (value == null || value.trim() == "");
}

function isInteger(value) {
	var integerVal = parseInt(value);
	return (value == String(integerVal));
}

function isPositiveInteger(value) {
	var integerVal = parseInt(value);
	return (value == String(integerVal) && integerVal > 0);
}

function isFloat(value) {
	return Number(value) == value;
}

function isAlpha(value) {
	for (var j=0;j<value.length;j++) {
    	if (value.charCodeAt(j) >= 255) {
	        return false;
        }
    }
    return true;
}

function assertNotEmpty(value, message, callback) {
	if (isEmptyOrWhitespace(value)) {
		if (callback != null) callback(message);
		else alert(message);
		return false;
	}
	return true;
}

function assertInteger(value, message, callback) {
	if (!isInteger(value)) {
		if (callback != null) callback(message);
		else alert(message);
		return false;
	}
	return true;
}

function assertFloat(value, message, callback) {
	if (!isFloat(value)) {
		if (callback != null) callback(message);
		else alert(message);
		return false;
	}
	return true;
}

function assertAlpha(value, message, callback) {
	if (!isAlpha(value)) {
		if (callback != null) callback(message);
		else alert(message);
		return false;
	}
	return true;
}

function assertByteLength(value, byteLength, message, callback) {
	var length = countByte(value);
	if (length > byteLength) {
		if (callback != null) callback(message);
		else alert(message);
		return false;
	}
	return true;
}

function resizeIFrame(iframe) {
	iframe.height = iframe.contentDocument.body.scrollHeight + 10;
}

function findXSSChar(content) {
	var isNormal = true;
	var reg_exp = /&lt;[\s*(&nbsp;)*\s*]*script[\w\W\d\D]*&gt/i;
	
	if(reg_exp.test(content))
		isNormal = false;
	reg_exp = /&lt;[\s*(&nbsp;)*\s*]*object[\w\W\d\D]*&gt/i;
	if(reg_exp.test(content))
		isNormal = false;
	reg_exp = /&lt;[\s*(&nbsp;)*\s*]*applet[\w\W\d\D]*&gt/i;
	if(reg_exp.test(content))
		isNormal = false;
	reg_exp = /&lt;[\s*(&nbsp;)*\s*]*embed[\w\W\d\D]*&gt/i;
	if(reg_exp.test(content))
		isNormal = false;
	reg_exp = /&lt;[\s*(&nbsp;)*\s*]*form[\w\W\d\D]*&gt/i;
	if(reg_exp.test(content))
		isNormal = false;
	reg_exp = /&lt;[\s*(&nbsp;)*\s*]*iframe[\w\W\d\D]*&gt/i;
	if(reg_exp.test(content))
		isNormal = false;
	
	if(!isNormal) {
		alert("<script>, <object>, <applet>, <embed>, <form>, <iframe> 허용되지 않습니다.");
		return true;
	}
	
	return false;
}

// cookie get, setter
function setCookie(name, value){
	var argc = setCookie.arguments.length;
	var argv = setCookie.arguments;

	var expires = ( argc > 2) ? argv[2]:null;
	var path = ( argc > 3) ? argv[3]:null;
	var domain = ( argc > 4) ? argv[4]:null;
	var secure = ( argc > 5) ? argv[5]:false;

	document.cookie = name + "=" + escape(value) +
		((expires == null) ? "" : ("; expires =" + expires.toGMTString())) +
		((path == null) ? "" : ("; path =" + path)) +
		((domain == null) ? "" : ("; domain =" + domain)) +
		((secure == true) ? "; secure" : "");
}

function getCookie(name) {
	var dcookie = document.cookie;
	var cname = name + "=";
	var clen = dcookie.length;
	var cbegin = 0;
	while (cbegin < clen) {
		var vbegin = cbegin + cname.length;
			if (dcookie.substring(cbegin, vbegin) == cname) {
				var vend = dcookie.indexOf (";", vbegin);
				if (vend == -1) vend = clen;
				return unescape(dcookie.substring(vbegin, vend));
		}
		cbegin = dcookie.indexOf(" ", cbegin) + 1;
		if (cbegin == 0) break;
	}
	return "";
}

// 유선, 휴대폰 전화번호 체크
function prefixCheck(checkType, elem, errElem, option) {
	var value = elem.val();
	var result = true;
	
	if (checkType == 'userMobile') {
		var regex = /^010|^011|^016|^017|^018|^019/;
		result = regex.test(value);
		if(!result){
			errElem.show().text("유효하지 않은 휴대폰 번호 입니다.");
			elem.addClass(option);
		}
	} else if (checkType == 'userPhone') {
		var regex = /^02|^031|^032|^033|^041|^042|^043|^051|^052|^053|^054|^055|^061|^062|^063|^064|^07[0-9]|^08[0-9]|^09[0-9]|^1[3-9]/;
		result = regex.test(value);
		if(!result){
			errElem.show().text("유효하지 않은 유선 번호 입니다.");
			elem.addClass(option);
		}
	} else {
		result = false;
	}
	
	return result;
}

// 유선, 휴대폰 전화번호 체크
function prefixCheck2(checkType, elem, errElem, option) {
	var value = elem;
	var result = true;
	
	if (checkType == 'userMobile') {
		var regex = /^010|^011|^016|^017|^018|^019/;
		result = regex.test(value);
		if(!result){
			errElem.show().text("유효하지 않은 휴대폰 번호 입니다.");
		}
	} else if (checkType == 'userPhone') {
		var regex = /^02|^031|^032|^033|^041|^042|^043|^051|^052|^053|^054|^055|^061|^062|^063|^064|^07[0-9]|^08[0-9]|^09[0-9]|^1[3-9]/;
		result = regex.test(value);
		if(!result){
			errElem.show().text("유효하지 않은 유선 번호 입니다.");
		}
	} else {
		result = false;
	}
	
	return result;
}

// popup
function openPopup(page, name, width, height) {
	var screenW = 0;
	
	if (parseInt(navigator.appVersion) > 3) {
		screenW = screen.width;
   	} else if (navigator.appName == "Netscape" && parseInt(navigator.appVersion)==3 && navigator.javaEnabled()) {
   	 	var jToolkit = java.awt.Toolkit.getDefaultToolkit();
   	 	var jScreenSize = jToolkit.getScreenSize();
   	 	screenW = jScreenSize.width;
   	}

	var option = 'scrollbars=yes,resizable=yes,status=no,location=no,toolbar=no,menubar=no,'+ 
				 'width='+width+',height='+height+',left='+(screenW/3)+',top=100';
	
	window.open(page, name, option);
}