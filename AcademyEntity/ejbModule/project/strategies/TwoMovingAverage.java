package project.strategies;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import project.business.MasterBeanLocal;
import project.entity.Stock;
import project.entity.Transaction;

@EJB(name="ejb/Master", beanInterface=MasterBeanLocal.class)
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

		Logger log =  Logger.getLogger(this.getClass());
		try {
			InitialContext context = new InitialContext();
			MasterBeanLocal bean = (MasterBeanLocal)context.lookup("java:comp/env/ejb/Master");
			
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
	
	public void carryOutTransaction(Stock s, TwoMovingAverage movingAvg) {
		Logger log =  Logger.getLogger(this.getClass());
		
		try {
			InitialContext context = new InitialContext();
			MasterBeanLocal bean = (MasterBeanLocal)context.lookup("java:comp/env/ejb/Master");
			
			if((movingAvg.getShortPrices().size() > 0) && (movingAvg.getLongPrices().size() > 0)) {
				List<Double> shortP = movingAvg.getShortPrices();
				List<Double> longP = movingAvg.getLongPrices();
				double recentShort = shortP.get(shortP.size()-1);
				double recentLong = longP.get(longP.size()-1);
				
				String symbol = s.getStockSymbol();
				int volume = 100;
				double price = (s.getAskPrice()+s.getBidPrice())/2;
				price = Math.round(price * 100.0)/100.0;
				String transtype = "";
				String strategyStr = "TwoMAvg";
				
				/*
				 * Get recent transaction from the database and wait for profit/loss of > 1%
				 * before exiting position (Buy/Sell)
				 */
				
				Transaction t;
				if(recentShort > recentLong) {
					transtype = "Buy";
		        	t = new Transaction(s, symbol, volume, price, transtype, strategyStr);
		        	bean.saveTransaction(t);
		        	System.out.println(t.toString());
		        } else if(recentShort < recentLong) {
		        	transtype = "Sell";
		        	t = new Transaction(s, symbol, volume, price, transtype, strategyStr);
		        	bean.saveTransaction(t);
		        	System.out.println(t.toString());
		        } else {
		        	System.out.println("EQUAL: " + recentShort + " AND " + recentLong);
		        }
			}
		} catch (Exception ex) {
			log.error("ERROR " + ex.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return "Two Moving Average for stock: " + this.getStock();
	}
}
