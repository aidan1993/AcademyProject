package project.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="Transactions")
public class Transactions 
{

	public Transactions() 
	{
		super();
	}
	
	@Id
	private int transactionid;
	private int stockid;
	private String stockSymbol;
	private int volume;
	private double price;
	private String transtime;
	private String transtype;
	
	public int getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(int transactionid) {
		this.transactionid = transactionid;
	}
	public int getStockid() {
		return stockid;
	}
	public void setStockid(int stockid) {
		this.stockid = stockid;
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

	@Override
	public String toString() {
		return "Transactions: " + this.getTransactionid() + ", Stock ID: " + this.getStockid() + " , Stock: " + this.getStockSymbol()
				+", Volume: " + this.getVolume() + ", Price: " + this.getPrice() + ", Transaction Time: " + this.getTranstime() 
				+ ", Transaction Type: " + this.getTranstype();
	}
	
}
