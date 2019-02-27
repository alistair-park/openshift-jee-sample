<HTML>
<HEAD>
	<TITLE>Placement Test</TITLE>
	<%@ page import="javax.servlet.http.HttpUtils,java.util.Enumeration" %>
	<%@ page import="java.lang.management.*" %>
	<%@ page import="java.util.*" %>
	<%@ page import="placement.*" %>
</HEAD>
<BODY>

<H1>WebApp JSP Test Page</H1>
<%=DBSetup.getDBSetup().testConnection()%>

</BODY>
</HTML>

