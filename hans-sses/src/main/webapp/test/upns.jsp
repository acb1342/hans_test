<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="com.fasterxml.jackson.core.util.DefaultPrettyPrinter" %>
<%@ page import="org.springframework.web.client.RestTemplate" %>
<%@ page import="com.mobilepark.doit5.ifs.upns.UPNSMessage" %>
<%@ page import="com.mobilepark.doit5.ifs.upns.UPNSInterfaceImpl" %>
<%
	// curl --verbose --request POST http://agit.synology.me:3000/msgif/create --data 'data={"route":["PRI","PUB"],"target":"","messageType":"RICH","message":"메시지","cpId":"testcp","appName":"U-PUSH","appMngNum":"20140610134051","send":"01091717233","reply":"01091717233","timeToLive":2419200,"dryRun":false,"sound":"default","alert":"[U-PUSH] 알림메시지가 도착했습니다.","payload":"{\"zoneID\":\"2\",\"messageType\":\"CONTENT_EDITOR\",\"messageUrl\":\"http://192.168.1.145:5210/editor/popup/preview.htm?id=7\"}"}'

	String type = request.getParameter("type");

	if (type != null) {
		try {
			UPNSInterfaceImpl upns = new UPNSInterfaceImpl();
			upns.setRestTemplate(new RestTemplate());
			upns.setIndentJson(false);
		
			UPNSMessage parameter = new UPNSMessage();
			Set<String> route = new HashSet<String>(0);
			route.add("PRI"); route.add("PUB"); //route.add("SMS");
			parameter.setRoute(route);
			parameter.setTarget("");
			parameter.setMessageType("RICH");
			parameter.setMessage("메시지");
			parameter.setCpId("testcp");
			parameter.setAppPkgName("U-PUSH");
			parameter.setAppCode("20140610134051");
			parameter.setSenderNo("01091717233");
			parameter.setReplyNo("01091717233");
			parameter.setTimeToLive(2419200);
			parameter.setDryRun(false);
			parameter.setSound("default");
			parameter.setAlert("[U-PUSH] 알림메시지가 도착했습니다.");
			parameter.setPayload("{\"zoneID\":\"2\",\"messageType\":\"CONTENT_EDITOR\",\"messageUrl\":\"http://192.168.1.145:5210/editor/popup/preview.htm?id=7\"}");
			
			if ("c".equals(type)) {
				upns.sendNotification(parameter);
			} else if ("u".equals(type)) {
				upns.sendNotification(parameter);
			} else if ("d".equals(type)) {
				upns.sendNotification(parameter);
			}
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
<form name="cForm" id="cForm" action="/test/upns.jsp">
	<input type="hidden" name="type" value="c"/>
	UPNS 메시지 전송 : <input type="submit" value="확인"/>
</form>
<%--
<form name="uForm" id="cForm" action="/test/upns.jsp">
	<input type="hidden" name="type" value="u"/>
	u : <input type="submit" value="확인"/>
</form>
<form name="pForm" id="cForm" action="/test/upns.jsp">
	<input type="hidden" name="type" value="d"/>
	d : <input type="submit" value="확인"/>
</form>
--%>
</body>
</html>