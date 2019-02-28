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
	public void testConnection() {
		StringBuffer buf = new StringBuffer();
		try {
            this.conn = DriverManager.getConnection(server, rootUser, rootPassword);

//			Connection con=DriverManager.getConnection(  
//					"jdbc:mysql://localhost:3306/placement","placement","RFH2019!");  
//			//here sonoo is database name, root is username and password  
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from REGISTRATION");  
			while(rs.next())  
				buf.append(rs.getString(1));  
			conn.close();  
		}catch(Exception e){ buf.append(e);}//System.out.println(e);}  
		System.out.println(buf.toString());
	}  
	public void initialiseDatabase() {
		// Create user
		// Create Student table
		// Create Practice table
		// Create DriveTime table
		createStudentTable();
	}
	private void createStudentTable() {
		try {
            this.conn = DriverManager.getConnection(server, rootUser, rootPassword);
			Statement stmt=conn.createStatement();  
			  String sql = "CREATE TABLE REGISTRATION " +
	                  "(id INTEGER not NULL, " +
	                  " first VARCHAR(255), " + 
	                  " last VARCHAR(255), " + 
	                  " age INTEGER, " + 
	                  " PRIMARY KEY ( id ))"; 

	     stmt.executeUpdate(sql);
	     conn.close();  
		}catch(Exception e){ e.printStackTrace();}  


	}
}
