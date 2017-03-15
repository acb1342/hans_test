<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="messages">
	<fmt:message key="statement.session.internalErrorPage" var="internalErrorPageMsg"/>
</fmt:bundle>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css" media="all">
	@import url("/css/common.css");
</style>
<title>Admin - SKT EVC</title>
</head>
<body>
	${internalErrorPageMsg}
</body>
</html>