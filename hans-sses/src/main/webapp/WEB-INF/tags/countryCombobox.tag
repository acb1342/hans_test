<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag import="com.uangel.platform.util.Country" %>
<%@ attribute name="name" %>
<%@ attribute name="value" %>
<%@ attribute name="id" %>

<%
	String comboBoxName = name != null && name.length() > 0 ? name : "country";
	String comboBoxId = id != null && id.length() > 0 ? id : "countryComboBox";
	out.println(String.format("<select name='%s' id='%s'>\n", comboBoxName, id));

	String defaultLocalCode = "ko"; // 한국국가코드
	
	java.util.List<com.uangel.platform.util.Country> countries = com.uangel.platform.util.Country.getCountires();
	for (com.uangel.platform.util.Country country : countries) {
		String code = country.getCountryCode();
		String name = Country.getCountryName(code, request.getLocale().toString());
		String selected = code.equalsIgnoreCase(defaultLocalCode) ? "selected='selected'" : "";
		out.println(String.format("\t<option value='%s' %s>%s [%s]</option>\n", code, selected, name, code));
	}
	
	// 수동 추가
	String code = "kp";
	String name = "북한";
	String selected = code.equalsIgnoreCase(value) ? "selected='selected'" : "";
	out.println(String.format("\t<option value='%s' %s>%s [%s]</option>\n", code, selected, name, code));
	out.println("</select>\n");
%>