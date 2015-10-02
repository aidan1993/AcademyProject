package project.strategies;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import project.business.StockBeanLocal;
import project.entity.Stock;

@EJB(name="ejb/Stock", beanInterface=StockBeanLocal.class)
public class TwoMovingAverage extends Strategy {
	
	private String stock;
	private int shortDuration;
	private int longDuration;
	private List<Double> shortPrices = new ArrayList<>();
	private List<Double> longPrices = new ArrayList<>();
	private boolean active = false;
	
	public TwoMovingAverage(String stock, int shortTime, int longTime) {
		this.stock = stock;
		this.shortDuration = shortTime;
		this.longDuration = longTime;
		this.active = true;
	}
	
	
	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public int getShortLength() {
		return shortDuration;
	}

	public void setShortLength(int shortLength) {
		this.shortDuration = shortLength;
	}

	public int getLongLength() {
		return longDuration;
	}

	public void setLongLength(int longLength) {
		this.longDuration = longLength;
	}
	
	public List<Double> getShortPrices() {
		return shortPrices;
	}

	public void setShortPrices(List<Double> shortPrices) {
		this.shortPrices = shortPrices;
	}

	public List<Double> getLongPrices() {
		return longPrices;
	}

	public void setLongPrices(List<Double> longPrices) {
		this.longPrices = longPrices;
	}
	
	//Duration is set to minutes
	public void calcMovingAverage(long startTime) {
		InitialContext context;
		StockBeanLocal bean;
		Logger log =  Logger.getLogger(this.getClass());
		try {
			context = new InitialContext();
			bean = (StockBeanLocal)context.lookup("java:comp/env/ejb/Stock");
			
			//Calculate short average
			if((System.currentTimeMillis()-startTime) >= shortDuration*60*1000) {
	        	List<Stock> shortAvgStocks = new ArrayList<Stock>();
	        	shortAvgStocks = bean.retrieveMovingAvgStock(shortDuration, stock);
	        	
	        	double shortAvg = super.calcMovingAverage(shortAvgStocks);
	        	shortPrices.add(shortAvg);
	        }
			
			//Calculate long average
			if((System.currentTimeMillis()-startTime) >= longDuration*60*1000) {
	        	List<Stock> longAvgStocks = new ArrayList<Stock>();
	        	longAvgStocks = bean.retrieveMovingAvgStock(longDuration, stock);
	        	
	        	double longAvg = super.calcMovingAverage(longAvgStocks);
	        	longPrices.add(longAvg);
	        }
		} catch (NamingException ex) {
			log.error("ERROR " + ex.getMessage());
		}
	}
}
