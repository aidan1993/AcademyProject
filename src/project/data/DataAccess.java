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
	
	public static void addStock(String stock, String bidPrice, double bidAvg, String askPrice, double askAvg, double todaysOpen, double previousClose) throws SQLException {
		Connection cn = null;
		try {
			cn = getConnection();
			PreparedStatement st = cn.prepareStatement("INSERT INTO Stocks(StockSymbol, BidPrice, BidMAvg, AskPrice, AskMAvg, TodaysOpen, PreviousClose)"
					+ " VALUES(?, ?, ?, ?, ?, ?, ?)");
			st.setString(1, stock);
			st.setDouble(2, Double.parseDouble(bidPrice));
			st.setDouble(3, bidAvg);
			st.setDouble(4, Double.parseDouble(askPrice));
			st.setDouble(5, askAvg);
			st.setDouble(6, todaysOpen);
			st.setDouble(7, previousClose);
			int rows = st.executeUpdate();
			
			if(rows == 1) {
				System.out.println(stock + " added to database");
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
