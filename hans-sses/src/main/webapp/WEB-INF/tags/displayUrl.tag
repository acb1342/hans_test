<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag import="com.hans.sses.common.MessageType" %>
<%@ tag import="com.hans.sses.common.util.UrlUtil" %>
<%@ tag import="com.uangel.platform.util.Env" %>
<%@ attribute name="type" %>
<%@ attribute name="id" %>
<%@ attribute name="link" %>
<%
    String url = "";

    if (MessageType.CONTENT_EDITOR.toString().equals(type)) {
        url = UrlUtil.appendQueryString(Env.get("message.popup.url"), "id=" + id);
    } else if (MessageType.SP_LINK.toString().equals(type)) {
        url = link;
    } else if (MessageType.SP_CONTENT_ID.toString().equals(type)) {
        url = UrlUtil.appendQueryString(link, "id=" + id);        
    }

    out.print(url);
%>