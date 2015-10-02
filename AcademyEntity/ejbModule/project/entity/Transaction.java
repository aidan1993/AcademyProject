package project.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name="Transactions")
public class Transaction 
{

	public Transaction() 
	{
		super();
	}
	
	public Transaction(int stockid, String stockSymbol, int volume, double price, String transtype, String strategy) {
		this.stockId = stockid;
		this.stockSymbol = stockSymbol;
		this.volume = volume;
		this.price = price;
		this.transtype = transtype;
		this.strategy = strategy;
	}
	
	@Id
	private int transactionid;
	
	@OneToOne(optional=false)
	@JoinColumn(name="stockId", referencedColumnName="stockId")
	private int stockId;
	
	private String stockSymbol;
	private int volume;
	private double price;
	private String transtime;
	private String transtype;
	private String strategy;

	public int getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(int transactionid) {
		this.transactionid = transactionid;
	}
	public int getStockId() {
		return stockId;
	}
	public void setStockId(int stockid) {
		this.stockId = stockid;
	}
	public String getStockSymbol() {
		return stockSymbol;
	}
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getTranstime() {
		return transtime;
	}
	public void setTranstime(String transtime) {
		this.transtime = transtime;
	}
	public String getTranstype() {
		return transtype;
	}
	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}
	public String getStrategy() {
		return strategy;
	}
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	@Override
	public String toString() {
		return "Transactions: " + this.getTransactionid() + ", Stock ID: " + this.getStockId() + " , Stock: " + this.getStockSymbol()
				+", Volume: " + this.getVolume() + ", Price: " + this.getPrice() + ", Transaction Time: " + this.getTranstime() 
				+ ", Transaction Type: " + this.getTranstype() + ", Strategy Used: " + this.getStrategy();
	}
	
}
