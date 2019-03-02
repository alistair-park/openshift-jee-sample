<HTML>
<HEAD>
	<TITLE>Placement Test</TITLE>
	<%@ page import="java.io.*" %>
	<%@ page import="java.lang.management.*" %>
	<%@ page import="java.util.*" %>
	<%@ page import="placement.*" %>
	<%@ page import="com.mysql.jdbc.*" %>
	<%@ page import = "org.apache.commons.fileupload.*" %>
	<%@ page import = "org.apache.commons.fileupload.disk.*" %>
	<%@ page import = "org.apache.commons.fileupload.servlet.*" %>
	<%@ page import = "org.apache.commons.io.output.*" %>
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
[] <%out.print(request.getParameter("mybutton"));%> []

  <table>  
	<% 
		DBSetup db = new DBSetup();
		String result = request.getParameter("mybutton");
		String databaseName = request.getParameter("databaseName");

		if ("clear".equals(result)) {
			db.clear();
		}
		else if ("create".equals(result)) {
			db.create(databaseName);
		}

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

      <h3>File Upload:</h3>
      Select a file to upload: <br />
      <form action = "placement.jsp" method = "post"
         enctype = "multipart/form-data">
         <input type = "file" name = "file" size = "50" />
         <br />
         <input type = "submit" value = "Upload File" />
      </form>
      
      <%
   File file ;
 //  int maxFileSize = 5000 * 1024;
   //int maxMemSize = 5000 * 1024;
   //String filePath = "E:/guru99/data";
 
   String contentType = request.getContentType();
   if ((contentType.indexOf("multipart/form-data") >= 0)) {
 
      DiskFileItemFactory factory = new DiskFileItemFactory();
     // factory.setSizeThreshold(maxMemSize);
      //factory.setRepository(new File("c:\\temp"));
      ServletFileUpload upload = new ServletFileUpload(factory);
      //upload.setSizeMax( maxFileSize );
      try{ 
         List fileItems = upload.parseRequest(request);
         Iterator i = fileItems.iterator();
        
		 while ( i.hasNext () ) 
         {
            FileItem fi = (FileItem)i.next();
            if ( !fi.isFormField () )  {
                String fieldName = fi.getFieldName();
                String fileName = fi.getName();
                boolean isInMemory = fi.isInMemory();
                long sizeInBytes = fi.getSize();
                out.println("Uploaded Filename: " + fileName + "<br>");
            }
         }
      }catch(Exception ex) {
         System.out.println(ex);
      }
   }else{
      out.println("<p>No file uploaded</p>"); 
   }
%>
  
</BODY>
</HTML>

