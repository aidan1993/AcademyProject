package project.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="Stocks")
public class Stock {
	
	public Stock() {
		super();
	}
	
	@Id
	private int stockId;
	private String stockSymbol;
	private double bidPrice;
	private double bidMAvg;
	private double askPrice;
	private double askMAvg;
	private double todaysOpen;
	private double previousClose;
	private String time_Of;
	
	
	public int getStockId() {
		return stockId;
	}
	public void setStockId(int stockId) {
		this.stockId = stockId;
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
	public double getBidMAvg() {
		return bidMAvg;
	}
	public void setBidMAvg(double bidMAvg) {
		this.bidMAvg = bidMAvg;
	}
	public double getAskPrice() {
		return askPrice;
	}
	public void setAskPrice(double askPrice) {
		this.askPrice = askPrice;
	}
	public double getAskMAvg() {
		return askMAvg;
	}
	public void setAskMAvg(double askMAvg) {
		this.askMAvg = askMAvg;
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
		return "Stock: " + this.getStockSymbol() + ", \n Bid Price: " + this.getBidPrice() + ", Bid Price Moving Average: "
				+ this.getBidMAvg() + ", Ask Price: " + this.getAskPrice() + ", Ask Price Moving Average: " + this.getAskMAvg() 
				+ ", Today's Open Price: " + this.getTodaysOpen() + ", Previous Close: " + this.getPreviousClose() + ", Time: "
				+ this.getTimeOf();
	}
}