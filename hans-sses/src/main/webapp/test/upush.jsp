<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="com.fasterxml.jackson.core.util.DefaultPrettyPrinter" %>
<%@ page import="org.springframework.web.client.RestTemplate" %>
<%@ page import="com.mobilepark.doit5.ifs.upush.UPUSH" %>
<%@ page import="com.uangel.platform.collection.BaseData" %>
<%
	// curl --verbose --request POST http://192.168.1.129:7001/if/upush_recv_msg.xcg?op=create --data 'data={"type":"0","app_id":"com.uangel.upush","mdn":["821012345678","821098765432"],"title":"[U-PUSH] UPUSH 테스트 메시지","message":"[U-PUSH] 알림메시지가 도착했습니다."}'

	String type = request.getParameter("type");

	if (type != null) {
		try {
			String msgType = "0"; // 전송요청타입 - 0: MDN, 1: PUSH, 2: 일괄전송
			String appId = "com.uangel.upush";
			Set<String> mdn = new HashSet<String>(0);
			mdn.add("821012345678");
			mdn.add("821098765432");
			String title = "[U-PUSH] UPUSH 테스트 메시지";
			String message = "[U-PUSH] 알림메시지가 도착했습니다.";

//			BaseData data = UPUSH.sendNotification(type, null, appId, "821012345678", null, title, message);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test - uPUSH</title>
</head>
<body>
<form name="cForm" id="cForm" action="/test/upush.jsp">
	<input type="hidden" name="type" value="c"/>
	UPUSH 메시지 전송 : <input type="submit" value="확인"/>
</form>
<%--
<form name="uForm" id="cForm" action="/test/upush.jsp">
	<input type="hidden" name="type" value="u"/>
	u : <input type="submit" value="확인"/>
</form>
<form name="pForm" id="cForm" action="/test/upush.jsp">
	<input type="hidden" name="type" value="d"/>
	d : <input type="submit" value="확인"/>
</form>
--%>
</body>
</html>