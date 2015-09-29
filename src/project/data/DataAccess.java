package project.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DataAccess {
	public static Connection getConnection() {
		Connection cn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cn = DriverManager.getConnection("jdbc:mysql://localhost/AcademyProject", "root", "password");
		}
		catch(SQLException ex) {
			System.out.println("Database Connection error: " + ex);
		}
		catch(ClassNotFoundException ex) {
			System.out.println("Class not found: " + ex);
		}
		return cn;
	}
	
	public static void addStock(String stock, String bidprice, double bidAvg, String askprice, double askAvg, int capture, int movingAvg) throws SQLException {
		Connection cn = null;
		try {
			cn = getConnection();
			PreparedStatement st = cn.prepareStatement("INSERT INTO stocks(name, bidprice, bidaverage, askprice, askaverage, captureamount, movingaverage)"
					+ " VALUES(?, ?, ?, ?, ?, ?, ?)");
			st.setString(1, stock);
			st.setDouble(2, Double.parseDouble(bidprice));
			st.setDouble(3, bidAvg);
			st.setDouble(4, Double.parseDouble(askprice));
			st.setDouble(5, askAvg);
			st.setInt(6, capture);
			st.setInt(7, movingAvg);
			int rows = st.executeUpdate();
			
			if(rows == 1) {
				System.out.println(stock + " added");
			}
		}
		catch(SQLException ex) {
			System.out.println("Error adding data: " + ex);
		}
		finally {
			if(cn != null) {
				cn.close();
			}
		}
	}
	
	public static void clearDatabase() throws SQLException {
		Connection cn = null;
		try {
			cn = getConnection();
			Statement st = cn.createStatement();
			int rows = st.executeUpdate("DELETE FROM Stocks");
			
			if(rows != 0) {
				System.out.println("Database Cleared");
			}
		}
		catch(SQLException ex) {
			System.out.println("Error adding data: " + ex);
		}
		finally {
			if(cn != null) {
				cn.close();
			}
		}
	}
}
