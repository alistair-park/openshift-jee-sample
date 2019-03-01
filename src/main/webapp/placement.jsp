<HTML>
<HEAD>
	<TITLE>Placement Test</TITLE>
	<%@ page import="java.lang.management.*" %>
	<%@ page import="java.util.*" %>
	<%@ page import="placement.*" %>
	<%@ page import="com.mysql.jdbc.*" %>
	
	    <script type="text/javascript">
	    function initialiseDB() {
         	clearResponse();
	    	var request = new XMLHttpRequest();
	    	request.onreadystatechange = function(){
	      		if(this.readyState == 4 && this.status == 200){
	         		var response = this.responseText;
		  			document.getElementById("info").innerHTML=response;
	       		}
            };
            request.open("GET", "intialiseDB.jsp", true);
	   	 	request.send();
	   	 	_submit();
	    }
	  
	    function testConnection() {
         	clearResponse();
	    	var request = new XMLHttpRequest();
	    	request.onreadystatechange = function(){
	      		if(this.readyState == 4 && this.status == 200){
	         		var response = this.responseText;
		  			document.getElementById("info").innerHTML=response;
	       		}
            };
            request.open("GET", "testConnection.jsp", true);
	   	 	request.send();
	   	 	_submit();
	    }
	    
	    
         function clear(){
         	clearResponse();
	    	var request = new XMLHttpRequest();
	    	request.onreadystatechange = function(){
	      		if(this.readyState == 4 && this.status == 200){
	         		var response = this.responseText;
		  			document.getElementById("info").innerHTML=response;
	       		}
            };
            request.open("GET", "clear.jsp", true);
	   	 	request.send();
	   	 	_submit();
         }
         function clearResponse() {
         	document.getElementById("info").innerHTML="";
         
         }
        function _submit() {
            document.getElementById('placementform').submit();
        }
      </script>
</HEAD>
<BODY>

<H1>WebApp JSP Test Page</H1>
hello
  <form id="placementform" action = "placement.jsp" method="POST">
  
  <button onclick="initialiseDB()">Initialise DB</button>
  
  <button onclick="testConnection()">Test Connection</button>

  <button onclick="clear()">Clear</button>
  
  xxx - test
  
       <jsp:useBean id = "students" class = "placement.DBSetup"> 
         <jsp:setProperty name = "students" property = "firstName" value = "Zara"/>
         <jsp:setProperty name = "students" property = "lastName" value = "Ali"/>
         <jsp:setProperty name = "students" property = "age" value = "10"/>
      </jsp:useBean>

      <p>Student First Name: 
         <jsp:getProperty name = "students" property = "firstName"/>
      </p>
      
      <p>Student Last Name: 
         <jsp:getProperty name = "students" property = "lastName"/>
      </p>
      
      <p>Student Age: 
         <jsp:getProperty name = "students" property = "age"/>
      </p>
 xxx
</form>
  
</BODY>
</HTML>

