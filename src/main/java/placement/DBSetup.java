package placement;
import java.sql.*;  


public class DBSetup {

	public static DBSetup getDBSetup() {
		return new DBSetup();
	}
	public String testConnection() {
		StringBuffer buf = new StringBuffer();
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/placement","placement","RFH2019!");  
			//here sonoo is database name, root is username and password  
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from table");  
			while(rs.next())  
				buf.append(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
			con.close();  
		}catch(Exception e){ buf.append(e);}//System.out.println(e);}  
		return buf.toString();
	}  
}
