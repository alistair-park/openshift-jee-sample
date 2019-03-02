package placement;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;  


public class DBSetup {
	private String server = "jdbc:mysql://172.30.136.50:3306/mysql";
	private String rootUser = "root";//System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
	private String rootPassword = "RFH2019!";//System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
	private String appUser = "placement";//System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
	private String appPassword = "RFH2019!";//System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
	private Connection conn;

	public static DBSetup getDBSetup() {
		return new DBSetup();
	}

	public String getEnvVariables() {
		StringBuffer buf = new StringBuffer();
		buf.append("<p>" + System.getenv("MYSQL_USER") + "/p>");
		buf.append("<p>" + System.getenv("MYSQL_PASSWORD") + "/p>");
		buf.append("<p>" + System.getenv("MYSQL_ROOT_PASSWORD") + "/p>");
		buf.append("<p>" + System.getenv("MYSQL_DATABASE") + "/p>");
		return buf.toString();
	}
	public String clear() throws SQLException {
		StringBuffer buf = new StringBuffer("Clearing tables");
		try {
			this.conn = DriverManager.getConnection(server, rootUser, rootPassword);

			Statement stmt=conn.createStatement();  
			stmt.executeUpdate("TRUNCATE TABLE STUDENT");
			stmt=conn.createStatement();  
			stmt.executeUpdate("TRUNCATE TABLE PRACTICE");
			conn.close();
		}catch(Exception e){ buf.append(e);}
		return buf.toString();	
	}

	public String testConnection() {
		StringBuffer buf = new StringBuffer("<p>Hello world</p>");
		try {
			this.conn = DriverManager.getConnection(server, rootUser, rootPassword);

			//			Connection con=DriverManager.getConnection(  
			//					"jdbc:mysql://localhost:3306/placement","placement","RFH2019!");  
			//			//here sonoo is database name, root is username and password  
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from STUDENT");
			buf.append("<p>Executed query</p>/r/n");
			while(rs.next())  
				buf.append("<p>"+ rs.getString(2) + "</p>");  

			rs=stmt.executeQuery("select * from PRACTICE");
			buf.append("<p>Executed query</p>/r/n");
			while(rs.next())  
				buf.append("<p>"+ rs.getString(2) + "</p>");  
			conn.close();  
		}catch(Exception e){ buf.append(e);}//System.out.println(e);}  
		return buf.toString();
	}  
	public List<Student> getStudents() {
		List<Student> students = new ArrayList<>();
		Student student;
		try {
			this.conn = DriverManager.getConnection(server, rootUser, rootPassword);
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from STUDENT");
			while(rs.next())  {
				student = new Student(
							rs.getInt("id"),
							rs.getString("ref"),
							rs.getString("first"),
							rs.getString("last"),
							rs.getString("postcode"),
							rs.getString("allocatedPractice"));
				students.add(student);
			}
			conn.close();  
		}catch(Exception e){ e.printStackTrace();}
		return students;
	}
	public void create(String databaseName) {
		
	}
	public String initialiseDatabase() {
		StringBuffer buf = new StringBuffer("<p>initialiseDatabase</p>");

		// Create user
		// Create Student table
		// Create Practice table
		// Create DriveTime table
		try {
			this.conn = DriverManager.getConnection(server, rootUser, rootPassword);
			createStudentTable();
			createPracticeTable();

			buf.append(addStudentRecord("B05","Fred","Bloggs","W1D 4LR"));
			buf.append(addStudentRecord("B05","John","Doe","W127AP"));
			buf.append(addPracticeRecord("Sheema Sufi", "Eltham Park Surgery", "SE9 1JE", 4));
			buf.append(addPracticeRecord("Dr A Marks & Dr Kanagarajah", "Eagle House Surgery", "EN3 4DN", 6));
			conn.close();  
		}
		catch(Exception e)
		{ 
			buf.append(e);
		}  
		return buf.toString();

	}


	private String addPracticeRecord(String gp, String practiceName, String postCode, int places) throws SQLException {
		PreparedStatement statement = conn.prepareStatement("INSERT INTO PRACTICE (gp, practicename, postcode, places) VALUES (?,?,?,?)");

		statement.setString(1, gp);
		statement.setString(2, practiceName);
		statement.setString(3, postCode);
		statement.setInt(4, places);

		int affectedRows = statement.executeUpdate();

		if (affectedRows == 0) {
			throw new SQLException("Creating user failed, no rows affected.");
		}
		return "Affected rows = " + affectedRows;
	}
	private String addStudentRecord(String ref, String firstName, String familyName, String postCode) throws SQLException {
		PreparedStatement statement = conn.prepareStatement("INSERT INTO STUDENT (ref, first,last, postcode) VALUES (?,?,?,?)");

		statement.setString(1, ref);
		statement.setString(2, firstName);
		statement.setString(3, familyName);
		statement.setString(4, postCode);

		int affectedRows = statement.executeUpdate();

		if (affectedRows == 0) {
			throw new SQLException("Creating user failed, no rows affected.");
		}
		return "Affected rows = " + affectedRows;
	}
	public void addStudentRecords(ArrayList<Student> students) throws SQLException {
		if (students != null) {
			Iterator<Student> studentIterator = students.iterator();
			Student s;
			while (studentIterator.hasNext()) {
				s = studentIterator.next();
				addStudentRecord(s.getRef(), s.getFirst(), s.getLast(), s.getPostcode());
			}
		}
	}


	private void createPracticeTable() throws SQLException {
		Statement stmt=conn.createStatement();  
		stmt.executeUpdate("DROP TABLE IF EXISTS PRACTICE");
		stmt=conn.createStatement();
		String sql = 
				"CREATE TABLE PRACTICE ("+
						"id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"+
						"gp VARCHAR(255) NOT NULL,"+
						"practiceName VARCHAR(255) NOT NULL,"+
						"postcode VARCHAR(10),"+
						"places INT)";
		stmt.executeUpdate(sql);
	}
	private void createStudentTable() throws SQLException {
		Statement stmt=conn.createStatement();  
		stmt.executeUpdate("DROP TABLE IF EXISTS STUDENT");
		stmt=conn.createStatement();
		String sql = 
				"CREATE TABLE STUDENT ("+
						"id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"+
						"ref VARCHAR(6) NOT NULL,"+
						"first VARCHAR(255) NOT NULL,"+
						"last VARCHAR(255) NOT NULL,"+
						"postcode VARCHAR(10),"+
						"allocatedPractice VARCHAR(100))";
		stmt.executeUpdate(sql);
	}
}
