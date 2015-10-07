package project.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="Stocks")
public class Stock {
	
	public Stock() {
		
	}
	
	public Stock(String symbol, double bid, double ask) {
		this.stockSymbol = symbol;
		this.bidPrice = bid;
		this.askPrice = ask;
	}
	
	public Stock(String symbol, double bid, double ask, double high, double low, double open, double close) {
		this.stockSymbol = symbol;
		this.bidPrice = bid;
		this.askPrice = ask;
		this.dayHigh = high;
		this.dayLow = low;
		this.todaysOpen = open;
		this.previousClose = close;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int stockid;
	@OneToMany(cascade = {CascadeType.ALL}, mappedBy="stock")
	private List<Transaction> transactions;
	
	private String stockSymbol;
	private double bidPrice;
	private double askPrice;
	private double dayHigh;
	private double dayLow;
	private double todaysOpen;
	private double previousClose;
	private String time_Of;
	
	
	public int getStockId() {
		return stockid;
	}
	public void setStockId(int stockId) {
		this.stockid = stockId;
	}
	public String getStockSymbol() {
		return stockSymbol;
	}
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}
	public double getBidPrice() {
		return bidPrice;
	}
	public void setBidPrice(double bidPrice) {
		this.bidPrice = bidPrice;
	}
	public double getAskPrice() {
		return askPrice;
	}
	public void setAskPrice(double askPrice) {
		this.askPrice = askPrice;
	}
	public double getDayHigh() {
		return dayHigh;
	}
	public void setDayHigh(double dayHigh) {
		this.dayHigh = dayHigh;
	}
	public double getDayLow() {
		return dayLow;
	}
	public void setDayLow(double dayLow) {
		this.dayLow = dayLow;
	}
	public double getTodaysOpen() {
		return todaysOpen;
	}
	public void setTodaysOpen(double todaysOpen) {
		this.todaysOpen = todaysOpen;
	}
	public double getPreviousClose() {
		return previousClose;
	}
	public void setPreviousClose(double previousClose) {
		this.previousClose = previousClose;
	}
	public String getTimeOf() {
		return time_Of;
	}
	public void setTimeOf(String timeOf) {
		this.time_Of = timeOf;
	}
	
	@Override
	public String toString() {
		return "Stock ID: " + this.getStockId() + ", Stock: " + this.getStockSymbol() + ", Bid Price: " + this.getBidPrice() + ", Ask Price: " 
				+ this.getAskPrice() + ", Today's Open Price: " + this.getTodaysOpen() + ", Previous Close: " 
				+ this.getPreviousClose() + ", Time: " + this.getTimeOf();
	}
}
