package project.strategies;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import project.business.StockBeanLocal;


public class TwoMovingAverage extends Strategy {
	
	private String stock;
	private int shortLength;
	private int longLength;
	private double[] shortPrices;
	private double[] longPrices;
	
	
	public TwoMovingAverage(String stock, int shortTime, int longTime) {
		this.shortLength = shortTime;
		this.longLength = longTime;
	}
	
	
	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public int getShortLength() {
		return shortLength;
	}

	public void setShortLength(int shortLength) {
		this.shortLength = shortLength;
	}

	public int getLongLength() {
		return longLength;
	}

	public void setLongLength(int longLength) {
		this.longLength = longLength;
	}
	
	public double[] getShortPrices() {
		return shortPrices;
	}

	public void setShortPrices(double[] shortPrices) {
		this.shortPrices = shortPrices;
	}

	public double[] getLongPrices() {
		return longPrices;
	}

	public void setLongPrices(double[] longPrices) {
		this.longPrices = longPrices;
	}
	
	
}
