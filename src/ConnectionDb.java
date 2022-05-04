import java.sql.*;
public class ConnectionDb {
	public static Connection getConnection() throws  SQLException {
		Connection connection = null;
		
		if(connection == null) {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/LibraryDB?autoReconnect=true&openSSL=false", 
					"root",
					"system@1989");
		}
		
		return connection;
	}
}
