package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCuntil {
    	private static JDBCuntil instance;
	private Connection connection;
    
    public static JDBCuntil getInstance() {
		if(instance == null) {
			instance = new JDBCuntil();
		}
		return instance;
	}
    public JDBCuntil() {
	}
	public static Connection getconection() {
        Connection c = null;
        try {
            String url = "jdbc:mysql://localhost:3306/restaurant";
            String username = "root";
            String password = "";
            c = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }
    public static void closeconection(Connection c){
        try {
         if(c!=null)
         {c.close();}
        } catch (Exception e) {
            e.printStackTrace();
        }
     
    }
}
