<HTML>
<HEAD>
	<TITLE>Placement Test</TITLE>
	<%@ page import="java.lang.management.*" %>
	<%@ page import="java.util.*" %>
	<%@ page import="placement.*" %>
	<%@ page import="com.mysql.jdbc.*" %>
</HEAD>
<BODY>

<H1>WebApp JSP Test Page</H1>

<%=DBSetup.getDBSetup().testConnection()%>

</BODY>
</HTML>

