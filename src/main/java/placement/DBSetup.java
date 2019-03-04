package placement;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;  


public class DBSetup {
	private String server = "jdbc:mysql://" + System.getenv("MYSQL_SERVER") + "/mysql?useSSL=false";
	private String rootUser = System.getenv("MYSQL_ROOT_USER");
	private String rootPassword = System.getenv("MYSQL_ROOT_PW");
	private String appUser = System.getenv("MYSQL_APP_USER");
	private String appPassword = System.getenv("MYSQL_APP_PW");
	private String googleAPI = System.getenv("GOOGLE_API");
	//	private Connection conn;

	public static DBSetup getDBSetup() {
		return new DBSetup();
	}

	public String getEnvVariables() {
		StringBuffer buf = new StringBuffer();
		buf.append("<p>" + System.getenv("MYSQL_SERVER") + "/p>");
		buf.append("<p>" + System.getenv("MYSQL_ROOT_USER") + "/p>");
		buf.append("<p>" + System.getenv("MYSQL_ROOT_PW") + "/p>");
		buf.append("<p>" + System.getenv("MYSQL_APP_USER") + "/p>");
		buf.append("<p>" + System.getenv("MYSQL_APP_PW") + "/p>");
		buf.append("<p>" + System.getenv("GOOGLE_API") + "/p>");
		return buf.toString();
	}
	public String clear() throws SQLException {
		StringBuffer buf = new StringBuffer("Clearing tables");
		try {
			Connection conn = DriverManager.getConnection(server, rootUser, rootPassword);

			Statement stmt=conn.createStatement();  
			stmt.executeUpdate("TRUNCATE TABLE STUDENT");
			stmt.close();
			stmt=conn.createStatement();  
			stmt.executeUpdate("TRUNCATE TABLE PRACTICE");
			stmt.close();
			conn.close();
		}catch(Exception e){ buf.append(e);}
		return buf.toString();	
	}

	public String testConnection() {
		StringBuffer buf = new StringBuffer("<p>Hello world</p>");
		try {
			Connection conn = DriverManager.getConnection(server, rootUser, rootPassword);

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
			Connection conn = DriverManager.getConnection(server, rootUser, rootPassword);
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
			stmt.close();
			conn.close();  
		}catch(Exception e){ e.printStackTrace();}
		return students;
	}
	public List<Practice> getPractices() {
		List<Practice> practices = new ArrayList<>();
		Practice practice;
		try {
			Connection conn = DriverManager.getConnection(server, rootUser, rootPassword);
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select GP,PRACTICENAME, POSTCODE, PLACES from PRACTICE");
			while(rs.next())  {
				practice = new Practice(
						rs.getString("GP"),
						rs.getString("PRACTICENAME"),
						rs.getString("POSTCODE"),
						rs.getInt("PLACES"));
				practices.add(practice);
			}
			stmt.close();
			conn.close();  
		}catch(Exception e){ e.printStackTrace();}
		return practices;
	}
	public String countDistances() {
		StringBuffer buf = new StringBuffer();
		try {
			Connection conn = DriverManager.getConnection(server, rootUser, rootPassword);
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT COUNT(*) AS TOTAL FROM DISTANCE");
			while(rs.next())  {
				buf.append("Total = " +  rs.getInt("TOTAL"));
			}
			stmt.close();
			stmt=conn.createStatement();  
			rs=stmt.executeQuery("SELECT COUNT(*) AS TOTAL FROM DISTANCE WHERE DISTANCE > 0");
			while(rs.next())  {
				buf.append("Distances known = " +  rs.getInt("TOTAL"));
			}
			stmt.close();
			conn.close();  
		}catch(Exception e){ 
			e.printStackTrace();
		}
		return buf.toString();	
	}

	public void create(String databaseName) {

	}
	private void updateDistance(Connection conn, Distance distance) throws SQLException {
		PreparedStatement statement = conn.prepareStatement("UPDATE DISTANCE SET DISTANCE=? WHERE FROM_POSTCODE=? AND TO_POSTCODE=?");

		statement.setLong(1, distance.distance);
		statement.setString(2, distance.fromPostcode);
		statement.setString(3, distance.toPostcode);

		int affectedRows = statement.executeUpdate();
		statement.close();
		if (affectedRows == 0) {
			throw new SQLException("Creating user failed, no rows affected.");
		}
	
	}
	public void initialiseDatabase() {
		try {
			Connection conn = DriverManager.getConnection(server, rootUser, rootPassword);
			createStudentTable(conn);
			createPracticeTable(conn);
			createDistanceTable(conn);

			addStudentRecord(conn, "B05","Fred","Bloggs","W1D 4LR");
			addStudentRecord(conn, "B05","John","Doe","W127AP");
			addPracticeRecord(conn, "Sheema Sufi", "Eltham Park Surgery", "SE9 1JE", 4);
			addPracticeRecord(conn, "Dr A Marks & Dr Kanagarajah", "Eagle House Surgery", "EN3 4DN", 6);
			addDistanceRecord(conn, "WD3 5DN", "HA5 3YJ", 100);
			
			conn.close();  
		}
		catch(Exception e)
		{ 
			e.printStackTrace();
		}  
	}
	public List<Distance> getDistances() {
		List<Distance> distances = new ArrayList<>();
		Distance distance;
		try {
			Connection conn = DriverManager.getConnection(server, rootUser, rootPassword);
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT FROM_POSTCODE, TO_POSTCODE, DISTANCE FROM DISTANCE");
			while(rs.next())  {
				distance = new Distance(
						rs.getString("FROM_POSTCODE"),
						rs.getString("TO_POSTCODE"),
						rs.getLong("DISTANCE"));
				distances.add(distance);
			}
			stmt.close();
			conn.close();  
		}catch(Exception e){ e.printStackTrace();}
		return distances;
	}
	public void allocate() {
		try {
			Connection conn = DriverManager.getConnection(server, rootUser, rootPassword);
			createDistanceTable(conn);
			// Get all distances
			List<Distance> distances = getDistances();
			Iterator<Distance> distanceIterator = distances.iterator();
			Distance distance;
			DistanceCalculator calc = new DistanceCalculator(googleAPI, "drive");
			long distanceMeters;
			while (distanceIterator.hasNext()) {
				distance = distanceIterator.next();
				if (distance.getDistance() == 0) {
					try {
						distanceMeters = calc.getDistance(distance.fromPostcode, distance.toPostcode);
						if(distanceMeters > 0) {
							distance.setDistance(distanceMeters);
							updateDistance(conn, distance);
							System.out.println("Distance from " + distance.fromPostcode + " to " + distance.toPostcode + " = " + distanceMeters);
						}						
					}
					catch(Exception e)
					{ 
						e.printStackTrace();
					}  
				}
			}
			conn.close();  
		}
		catch(Exception e)
		{ 
			e.printStackTrace();
		}  
	}
	public void calculate() {
		try {
			Connection conn = DriverManager.getConnection(server, rootUser, rootPassword);
			createDistanceTable(conn);
			conn.close();  
		}
		catch(Exception e)
		{ 
			e.printStackTrace();
		}  
	}

	private void addPracticeRecord(Connection conn, String gp, String practiceName, String postCode, int places) throws SQLException {
		PreparedStatement statement = conn.prepareStatement("INSERT INTO PRACTICE (gp, practicename, postcode, places) VALUES (?,?,?,?)");

		statement.setString(1, gp);
		statement.setString(2, practiceName);
		statement.setString(3, postCode.toUpperCase().replaceAll("\\s+",""));
		statement.setInt(4, places);

		int affectedRows = statement.executeUpdate();
		statement.close();
		if (affectedRows == 0) {
			throw new SQLException("Creating user failed, no rows affected.");
		}
	}
	private void addDistanceRecord(Connection conn, String from_postcode, String to_postcode, int distance) throws SQLException {
		PreparedStatement statement = conn.prepareStatement("INSERT INTO distance (from_postcode, to_postcode, distance) VALUES (?,?,?)");

		statement.setString(1, from_postcode);
		statement.setString(2, to_postcode);
		statement.setInt(3, distance);

		int affectedRows = statement.executeUpdate();
		statement.close();
		if (affectedRows == 0) {
			throw new SQLException("adding distance failed, no rows affected.");
		}
	}
	private void addStudentRecord(Connection conn, String ref, String firstName, String familyName, String postCode) throws SQLException {
		PreparedStatement statement = conn.prepareStatement("INSERT INTO STUDENT (ref, first,last, postcode) VALUES (?,?,?,?)");

		statement.setString(1, ref);
		statement.setString(2, firstName);
		statement.setString(3, familyName);
		statement.setString(4, postCode.toUpperCase().replaceAll("\\s+",""));

		int affectedRows = statement.executeUpdate();
		statement.close();
		if (affectedRows == 0) {
			throw new SQLException("Creating user failed, no rows affected.");
		}
	}
	public void addStudentRecords(ArrayList<Student> students) throws SQLException {

		if (students != null) {
			Connection conn = DriverManager.getConnection(server, rootUser, rootPassword);

			Iterator<Student> studentIterator = students.iterator();
			Student s;
			while (studentIterator.hasNext()) {
				s = studentIterator.next();
				addStudentRecord(conn, s.getRef(), s.getFirst(), s.getLast(), s.getPostcode());
			}
			conn.close();
		}
	}
	public void addPracticeRecords(ArrayList<Practice> practices) throws SQLException {

		if (practices != null) {
			Connection conn = DriverManager.getConnection(server, rootUser, rootPassword);

			Iterator<Practice> practiceIterator = practices.iterator();
			Practice p;
			while (practiceIterator.hasNext()) {
				p = practiceIterator.next();
				addPracticeRecord(conn,p.getGp(), p.getPracticeName(), p.getPostcode(), p.getPlaces());
			}
			conn.close();
		}
	}


	private void createPracticeTable(Connection conn) throws SQLException {
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
		stmt.close();
	}
	private void createStudentTable(Connection conn) throws SQLException {
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
		stmt.close();
	}
	private void createDistanceTable(Connection conn)  throws SQLException {
		Statement stmt=conn.createStatement();  
		stmt.executeUpdate("DROP TABLE IF EXISTS DISTANCE");
		stmt.close();
		stmt=conn.createStatement();
		String sql = 
				"CREATE TABLE DISTANCE AS SELECT DISTINCT STUDENT.POSTCODE AS FROM_POSTCODE, PRACTICE.POSTCODE AS TO_POSTCODE, 0 as DISTANCE FROM STUDENT CROSS JOIN PRACTICE";
		stmt.executeUpdate(sql);
		stmt.close();
	}
}
