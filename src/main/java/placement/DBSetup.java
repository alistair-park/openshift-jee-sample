package placement;
import java.sql.*;  


public class DBSetup {
	  private String server = "jdbc:mysql://" + System.getenv("OPENSHIFT_MYSQL_DB_HOST") + ":" + System.getenv("OPENSHIFT_MYSQL_DB_PORT") + "/" + "placement";//System.getenv("OPENSHIFT_APP_NAME") + "";
	    private String user = "placement";//System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
	    private String pass = "RFH2019!";//System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
	    private Connection conn;

	public static DBSetup getDBSetup() {
		return new DBSetup();
	}
	public String testConnection() {
		StringBuffer buf = new StringBuffer();
		try {
            this.conn = DriverManager.getConnection(server, user, pass);

//			Connection con=DriverManager.getConnection(  
//					"jdbc:mysql://localhost:3306/placement","placement","RFH2019!");  
//			//here sonoo is database name, root is username and password  
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from table");  
			while(rs.next())  
				buf.append(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
			conn.close();  
		}catch(Exception e){ buf.append(e);}//System.out.println(e);}  
		return buf.toString();
	}  
}
