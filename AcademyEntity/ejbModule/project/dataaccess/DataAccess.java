package project.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import project.entity.Stock;

public class DataAccess {
	public static Connection getConnection() {
		Connection cn = null;
		try {
			Context initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/ProjectDS");
			cn = ds.getConnection();
		}
		catch(SQLException ex) {
			System.out.println("Database Connection error: " + ex);
		}
		catch(NamingException ex){
			System.out.println("Naming Error: " + ex);
		}
		return cn;
	}
	
	public static void clearStock() throws SQLException {
		Connection cn = null;
		try {
			cn = getConnection();
			Statement st = cn.createStatement();
			st.executeUpdate("DELETE FROM Stocks");
		}
		catch(SQLException ex) {
			System.out.println("Error getting data: " + ex);
		}
		finally {
			if(cn != null) {
				cn.close();
			}
		}
	}
	
	public static void saveStock(Stock s) throws SQLException {
		Connection cn = null;
		try {
			cn = getConnection();
			PreparedStatement st = cn.prepareStatement("INSERT INTO Stocks(StockSymbol, BidPrice, AskPrice," +
														" DayHigh, DayLow, TodaysOpen, PreviousClose)" + 
														" VALUES(?,?,?,?,?,?,?)");
			st.setString(1,s.getStockSymbol());
			st.setDouble(2,s.getBidPrice());
			st.setDouble(3,s.getAskPrice());
			st.setDouble(4,s.getDayHigh());
			st.setDouble(5,s.getDayLow());
			st.setDouble(6,s.getTodaysOpen());
			st.setDouble(7,s.getPreviousClose());
			int rows = st.executeUpdate();
			if(rows > 0) {
				System.out.println("Added");
			}
		}
		catch(SQLException ex) {
			System.out.println("Error getting data: " + ex);
		}
		finally {
			if(cn != null) {
				cn.close();
			}
		}
	}
	
	public static int getMostRecentStockId(String symbol) throws SQLException {
		Connection cn = null;
		int stockid = 0;
		try {
			cn = getConnection();
			PreparedStatement st = cn.prepareStatement("SELECT MAX(Stockid) AS Stockid FROM Stocks WHERE " +
														"StockSymbol = ?");
			st.setString(1,symbol);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				stockid = rs.getInt("Stockid");
			}
		}
		catch(SQLException ex) {
			System.out.println("Error getting data: " + ex);
		}
		finally {
			if(cn != null) {
				cn.close();
			}
		}
		return stockid;
	}
	
	public static List<Stock> getAllStocks() throws SQLException {
		List<Stock> temp = new ArrayList<Stock>();
		Connection cn = null;
		try {
			cn = getConnection();
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Stocks");
			
			while(rs.next()) {
				Stock s = new Stock(rs.getString("StockSymbol"), rs.getDouble("BidPrice"), rs.getDouble("AskPrice"),
						rs.getDouble("DayHigh"), rs.getDouble("DayLow"), rs.getDouble("TodaysOpen"), 
						rs.getDouble("PreviousClose"));
				s.setStockId(rs.getInt("Stockid"));
				temp.add(s);
			}
		}
		catch(SQLException ex) {
			System.out.println("Error getting data: " + ex);
		}
		finally {
			if(cn != null) {
				cn.close();
			}
		}
		return temp;
	}
}
