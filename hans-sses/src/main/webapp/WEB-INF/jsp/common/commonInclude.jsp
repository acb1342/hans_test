<%@ page pageEncoding="utf-8" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="javax.servlet.jsp.JspWriter" %>
<%!
	public void printMapHTML(JspWriter out, String fileName) {
		File file = new File(fileName);
		FileInputStream fis = null;
		if (file != null && file.exists()) {
			try {
				fis = new FileInputStream(file);
				byte buff[] = new byte[fis.available()];
				fis.read(buff);
				out.write(new String(buff));
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fis != null) {
					try { 
						fis.close(); 
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
%>