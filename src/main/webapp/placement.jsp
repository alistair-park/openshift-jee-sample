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
  <table>  
	<% 
		DBSetup db = new DBSetup();
		List<Student> students = db.getStudents();
        Iterator<Student> studentInterator = students.iterator();
        while (studentInterator.hasNext()) {
            Student student = studentInterator.next();
            %>
            <TR><TD>
            <%out.print(student.getFirst());%>
            </TD></TR>
            <%}%>  
  </table>

</form>
  
</BODY>
</HTML>

