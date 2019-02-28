<HTML>
<HEAD>
	<TITLE>Placement Test</TITLE>
	<%@ page import="java.lang.management.*" %>
	<%@ page import="java.util.*" %>
	<%@ page import="placement.*" %>
	<%@ page import="com.mysql.jdbc.*" %>
	
	    <script type="text/javascript">
         function fetch(){
	    	var request = new XMLHttpRequest();
	    	request.onreadystatechange = function(){
	      		if(this.readyState == 4 && this.status == 200){
	         		var response = this.responseText;
		  			document.getElementById("mobiles").innerHTML=response;
	       		}
            };
            request.open("GET", "details.jsp", true);
	   	 	request.send();
         }
      </script>
</HEAD>
<BODY>

<H1>WebApp JSP Test Page</H1>

  <%
  	DBSetup db = new DBSetup();
	out.print(db.initialiseDatabase());
	out.print(db.testConnection());
  %>
  
  <button onclick="fetch()">Show Details</button>
      <div id="mobiles"></div>
</BODY>
</HTML>

