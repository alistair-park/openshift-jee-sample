<HTML>
<HEAD>
	<TITLE>Placement Test</TITLE>
	<%@ page import="java.lang.management.*" %>
	<%@ page import="java.util.*" %>
	<%@ page import="placement.*" %>
	<%@ page import="com.mysql.jdbc.*" %>
	
	    <script type="text/javascript">
        function _submit(buttonName) {
	     	document.getElementById('buttonId').value=buttonName;
	     	alert(buttonName);
            document.getElementById('placementform').submit();
        }
      </script>
</HEAD>
<BODY>

<H1>WebApp JSP Test Page</H1>

  <form id="placementform" action = "placement.jsp" method="POST">
  
  <button onclick="_submit('initialiseDB')">Initialise DB</button>
  
  <button onclick="_submit('testConnection')">Test Connection</button>

  <button onclick="_submit('clear')">Clear</button>
  <input type="hidden" id="buttonId"  name = "mybutton"  value=""/>
[] <%out.print(session.getAttribute("mybutton"));%> []
[] <%out.print(session.getAttribute("buttonId"));%> []
  <table>  
	<% 
		DBSetup db = new DBSetup();
		List<Student> students = db.getStudents();
        Iterator<Student> studentInterator = students.iterator();
        while (studentInterator.hasNext()) {
            Student student = studentInterator.next();
           %>
            <TR><TD>
            <%out.print(student.getRef());%>
            </TD><TD>
            <%out.print(student.getFirst());%>
            </TD><TD>
            <%out.print(student.getLast());%>
           </TD><TD>
            <%out.print(student.getPostcode());%>
            </TD></TR>
            <%}%>  
  </table>

</form>
  
</BODY>
</HTML>

