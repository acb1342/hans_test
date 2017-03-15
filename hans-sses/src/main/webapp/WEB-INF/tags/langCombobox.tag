<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag import="com.uangel.platform.util.Language" %>
<%@ tag import="java.util.Locale" %>
<%@ attribute name="name" %>
<%@ attribute name="value" %>
<%@ attribute name="id" %>

<%
	String comboBoxName = name != null && name.length() > 0 ? name : "lang";
	String comboBoxId = id != null && id.length() > 0 ? id : "langCombo";
	out.println(String.format("<select name='%s' id='%s'>\n", comboBoxName, id));

	String defaultLocalCode = "ko";
	
	java.util.List<Language> langs = Language.getLanguages();
	for (Language lang : langs) {
		String code = lang.getLanguageCode();
		String name = Language.getLanguageName(code, request.getLocale().toString());
		String selected = code.equalsIgnoreCase(defaultLocalCode) ? "selected='selected'" : "";
		out.println(String.format("\t<option value='%s' %s>%s [%s]</option>\n", code, selected, name, code));
	}

	out.println("</select>\n");
%>