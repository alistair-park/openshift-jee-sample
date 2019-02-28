package placement;
import java.sql.*;  


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
	public DBSetup() {
		initialiseDatabase();
	}
	public String getEnvVariables() {
		StringBuffer buf = new StringBuffer();
		buf.append("<p>" + System.getenv("MYSQL_USER") + "/p>");
		buf.append("<p>" + System.getenv("MYSQL_PASSWORD") + "/p>");
		buf.append("<p>" + System.getenv("MYSQL_ROOT_PASSWORD") + "/p>");
		buf.append("<p>" + System.getenv("MYSQL_DATABASE") + "/p>");
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
			ResultSet rs=stmt.executeQuery("select * from REGISTRATION");
			buf.append("<p>Executed query</p>/r/n");
			while(rs.next())  
				buf.append("<p>"+ rs.getString(2) + "</p>/r/n");  
			conn.close();  
		}catch(Exception e){ buf.append(e);}//System.out.println(e);}  
		return buf.toString();
	}  
	public void initialiseDatabase() {
		// Create user
		// Create Student table
		// Create Practice table
		// Create DriveTime table
		try {
			this.conn = DriverManager.getConnection(server, rootUser, rootPassword);
			createStudentTable();
			addStudentRecord(1,"Fred","Bloggs",37);
			conn.close();  
		}catch(Exception e){ e.printStackTrace();}  

	}


	private void addStudentRecord(int id, String firstName, String familyName, int age) throws SQLException {
		PreparedStatement statement = conn.prepareStatement("INSERT INTO STUDENT ('id','first','last','age') VALUES (?,?,?,?)");

		statement.setInt(1,id);
		statement.setString(2, firstName);
		statement.setString(3, familyName);
		statement.setInt(4, age);

		int affectedRows = statement.executeUpdate();

		if (affectedRows == 0) {
			throw new SQLException("Creating user failed, no rows affected.");
		}
	}


	private void createStudentTable() throws SQLException {
		Statement stmt=conn.createStatement();  
		String sql = "CREATE OR REPLACE TABLE REGISTRATION " +
				"(id INTEGER not NULL, " +
				" first VARCHAR(255), " + 
				" last VARCHAR(255), " + 
				" age INTEGER, " + 
				" PRIMARY KEY ( id ))"; 

		stmt.executeUpdate(sql);
	}
}
