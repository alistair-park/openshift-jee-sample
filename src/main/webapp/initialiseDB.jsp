  	<%@ page import="placement.*" %>
  
  <%
  	DBSetup db = new DBSetup();
	db.initialiseDatabase();
	out.print("Initialise called");
	out.print(db.countDistances());
  %>
