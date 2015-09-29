package project.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetLiveFeed {
	
	public static void getConnection() {
		Connection cn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cn = DriverManager.getConnection(
					"jdbc:mysql://localhost/DATABASENAME", "root", "password");
		} catch (SQLException ex) {
			System.out.println("Database connection error: " + ex);
		} catch (ClassNotFoundException ex) {
			System.out.println("Class not found" + ex);
		}

	}

}