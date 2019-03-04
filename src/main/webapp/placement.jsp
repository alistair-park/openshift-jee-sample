<HTML>
<HEAD>
	<TITLE>Placement Test</TITLE>
	<%@ page import="java.io.*" %>
	<%@ page import="java.lang.management.*" %>
	<%@ page import="java.util.*" %>
	<%@ page import="placement.*" %>
	<%@ page import="com.mysql.cj.jdbc.*" %>
	<%@ page import = "org.apache.commons.fileupload.*" %>
	<%@ page import = "org.apache.commons.fileupload.disk.*" %>
	<%@ page import = "org.apache.commons.fileupload.servlet.*" %>
	<%@ page import = "org.apache.commons.io.output.*" %>
	<%@ page import = "org.apache.commons.csv.CSVFormat" %>
	<%@ page import = "org.apache.commons.csv.CSVRecord" %>

	    <script type="text/javascript">
        function _submit(buttonName) {
	     	document.getElementById('buttonId').value=buttonName;
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

  <button onclick="_submit('calculate')">Calculate Distances</button>
  
    <button onclick="_submit('allocate')">Allocate</button>
  
  <input type="hidden" id="buttonId"  name = "mybutton"  value=""/>
[] <%out.print(request.getParameter("mybutton"));%> []

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
		else if ("initialiseDB".equals(result)) {
			db.initialiseDatabase();
		}
		else if ("testConnection".equals(result)) {
			db.testConnection();
		}
		else if ("calculate".equals(result)) {
			db.calculate();
		}
		else if ("allocate".equals(result)) {
			db.allocate();
		}
		List<Student> students = db.getStudents();
        Iterator<Student> studentInterator = students.iterator();
		List<Practice> practices = db.getPractices();
        Iterator<Practice> practiceInterator = practices.iterator();
        int distances = db.countDistances();
        %>
        <P>STUDENTS</P>
        <TABLE>
        <TR><TH>Ref</TH><TH>First</TH><TH>Last</TH><TH>Postcode</TH></TR><%
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
 		</TABLE>
        <TABLE>
        <P>GPs</P>
        <TR><TH>GP</TH><TH>Practice</TH><TH>Postcode</TH><TH>Places</TH></TR><%
        while (practiceInterator.hasNext()) {
            Practice practice = practiceInterator.next();
           %>
            <TR><TD>
            <%out.print(practice.getGp());%>
            </TD><TD>
            <%out.print(practice.getPracticeName());%>
            </TD><TD>
            <%out.print(practice.getPostcode());%>
           </TD><TD>
            <%out.print(practice.getPlaces());%>
            </TD></TR>
            <%}%>  
 		</TABLE>
 		Distance records: <%out.print(distances);%>
</form>

      <h3>File Upload:</h3>
      Select a file to upload: <br />
      <form action = "placement.jsp" method = "post"
         enctype = "multipart/form-data">
         <input type = "file" name = "fil" size = "50" />
         <br />
         <input type = "submit" value = "Upload File" />
      </form>
      
      <%
   File file ;
  
   String contentType = request.getContentType();
   if (contentType != null && (contentType.indexOf("multipart/form-data") >= 0)) {
 
      DiskFileItemFactory factory = new DiskFileItemFactory();
      ServletFileUpload upload = new ServletFileUpload(factory);
      try{ 
         List fileItems = upload.parseRequest(request);
         Iterator i = fileItems.iterator();
        
		 while ( i.hasNext () ) 
         {
            FileItem fi = (FileItem)i.next();
            if ( !fi.isFormField () )  {
				String content = fi.getString();
				StringReader sReader = new StringReader(content);
				//
				// Reads the file with headers as First Name and Last Name
				//
				Iterable<CSVRecord> records = CSVFormat.RFC4180
						.withFirstRecordAsHeader().parse(sReader);
				//
				// Iterate over different rows
				//
				
				// What kind of records do we have?
				if (fi.getName().toUpperCase().contains("STUDENT")) {
					ArrayList<Student> studentsToUpload = new ArrayList<Student>();
					for (CSVRecord record : records) {
						Student s = new Student(
								record.get("Ref"), 
								record.get("First"), 
								record.get("Last"), 
								record.get("Postcode"));
						studentsToUpload.add(s);
					}
					if (studentsToUpload.isEmpty()){
						out.print("No students to upload");
					}
					else {
						out.print("Read " +studentsToUpload.size() + " students");
						db.addStudentRecords(studentsToUpload);
					}					
				}
				else if (fi.getName().toUpperCase().contains("PRACTICE")) {
					ArrayList<Practice> practicesToUpload = new ArrayList<Practice>();
					for (CSVRecord record : records) {
						Practice p = new Practice(
								record.get("GP"), 
								record.get("Practice"), 
								record.get("Postcode"), 
								Integer.parseInt(record.get("Slots")));
						practicesToUpload.add(p);
					}
					if (practicesToUpload.isEmpty()){
						out.print("No practices to upload");
					}
					else {
						out.print("Read " +practicesToUpload.size() + " practices");
						db.addPracticeRecords(practicesToUpload);
					}					
				}
				else {
					out.print("File should either be named STUDENT or PRACTICE");
				}
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

