import java.sql.*;
import java.util.HashMap;

public class Credentials {
	private HashMap<String, String[]> userCredentials = new HashMap<String, String[]>();
	
	public Credentials() {
		try {
			Connection connection = ConnectionDb.getConnection();
			String query = "SELECT * FROM USERS";
			Statement statement = connection.createStatement();
			ResultSet results = statement.executeQuery(query);
			
			while(results.next()) {
				// Get username, password, admin & user id
				String username = results.getString("Username");
				String password = results.getString("Password");
				String isAdmin = results.getString("Admin");
				String userId = results.getString("UID");

				String[] logInfo = {password, isAdmin, userId};
				userCredentials.put(username, logInfo);
			}
			
			statement.close();
			results.close();
			connection.close();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	
	public HashMap<String, String[]> getCredentials() {
		return userCredentials;
	}
}
