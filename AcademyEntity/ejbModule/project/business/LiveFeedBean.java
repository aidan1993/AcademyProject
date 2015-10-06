package project.business;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.InitialContext;

import org.jboss.logging.Logger;

import project.entity.Stock;
import project.strategies.Strategy;
import project.strategies.TwoMovingAverage;

@Stateless
@Remote(LiveFeedBeanRemote.class)
@Local(LiveFeedBeanLocal.class)
@EJB(name="ejb/Master", beanInterface=MasterBeanLocal.class)
public class LiveFeedBean implements LiveFeedBeanLocal, LiveFeedBeanRemote {
	
	static String div1 = "TSCO";
	static String div2 = "VOD";
	static String div3 = "RIO";
	static String div4 = "GSK";
	static String div5 = "BP";
	static String div6 = "CHTR";
	static String div7 = "CHTR";
	static String div8 = "CHTR";
	static String div9 = "CHTR";
	
	public static String getDiv1() {
		return div1;
	}

	public static void setDiv1(String div1) {
		LiveFeedBean.div1 = div1;
	}

	public static String getDiv2() {
		return div2;
	}

	public static void setDiv2(String div2) {
		LiveFeedBean.div2 = div2;
	}

	public static String getDiv3() {
		return div3;
	}

	public static void setDiv3(String div3) {
		LiveFeedBean.div3 = div3;
	}

	public static String getDiv4() {
		return div4;
	}

	public static void setDiv4(String div4) {
		LiveFeedBean.div4 = div4;
	}

	public static String getDiv5() {
		return div5;
	}

	public static void setDiv5(String div5) {
		LiveFeedBean.div5 = div5;
	}

	public static String getDiv6() {
		return div6;
	}

	public static void setDiv6(String div6) {
		LiveFeedBean.div6 = div6;
	}

	public static String getDiv7() {
		return div7;
	}

	public static void setDiv7(String div7) {
		LiveFeedBean.div7 = div7;
	}

	public static String getDiv8() {
		return div8;
	}

	public static void setDiv8(String div8) {
		LiveFeedBean.div8 = div8;
	}

	public static String getDiv9() {
		return div9;
	}

	public static void setDiv9(String div9) {
		LiveFeedBean.div9 = div9;
	}

	@Asynchronous
	public void runLiveData() {
		Logger log =  Logger.getLogger(this.getClass());
		
		try {
			InitialContext context = new InitialContext();
			MasterBeanLocal bean = (MasterBeanLocal)context.lookup("java:comp/env/ejb/Master");
			
			bean.clearStock();
			
						
//			Strategy strategy = new Strategy();
			
			//Set start time of the application
			long startTime = System.currentTimeMillis();
			int shortTime = 1;
			int longTime = 2;
			boolean missing = false;
//			TwoMovingAverage bpMAvg = new TwoMovingAverage("IBM", shortTime, longTime);
//			strategy.addTwoMAvg(bpMAvg);
			while(true) {
				
				String[] stocks = {getDiv1(), getDiv2(), getDiv3(), getDiv4(), getDiv5(), getDiv6(), getDiv7(), getDiv8(), getDiv9()};
				
				StringBuilder url = 
			            new StringBuilder("http://finance.yahoo.com/d/quotes.csv?s=");
				for(String stock : stocks) {
					url.append(stock + ",");
				}
		        url.append("&f=sbahgop&e=.csv");
		        
		        String theUrl = url.toString();
		        URL obj = new URL(theUrl);
		        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		        // This is a GET request
		        con.setRequestMethod("GET");
		        con.setRequestProperty("User-Agent", "Mozilla/5.0");
		        int responseCode = con.getResponseCode();
		        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		        String inputLine;
		        
				while((inputLine = in.readLine()) != null) {
					String[] fields = inputLine.split(",");
					fields[0] = fields[0].replace("\"", "");
		        	
					for(int loop=0;loop<fields.length;loop++) {
						if(fields[loop].equals("N/A")) {
							missing = true;
						}
					}

		        	String symbol = fields[0];
		        	double bidPrice = Double.parseDouble(fields[1]);
		        	double askPrice = Double.parseDouble(fields[2]);
		        	
		        	double high = 0;
		        	if(!fields[3].equals("N/A")) {
		        		high = Math.round(Double.parseDouble(fields[3]) * 100.0)/100.0;
		        	}
		        	double low = 0;
		        	if(!fields[4].equals("N/A")) {
		        		low = Math.round(Double.parseDouble(fields[4]) * 100.0)/100.0;
		        	}
		        	double open = 0;
		        	if(!fields[5].equals("N/A")) {
		        		open = Math.round(Double.parseDouble(fields[5]) * 100.0)/100.0;
		        	}
		        	
		        	double close = Math.round(Double.parseDouble(fields[6]) * 100.0)/100.0;
		        	
		        	Stock s;
		        	if(missing == false) {
		        		s = new Stock(symbol, bidPrice, askPrice, high, low, open, close);
		        	} else {
		        		s = new Stock(symbol, bidPrice, askPrice, close);
		        	}
		        	
		        	bean.saveStock(s);
		        	
		        	try {
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
						log.error("ERROR " + ex.getMessage());
					}
				}
				
//	        	for(TwoMovingAverage movingAvg : strategy.getTwoMAvg()) {
//		        	if(symbol.equals(movingAvg.getStock())) {
//		        		movingAvg.calcMovingAverage(startTime);
//		        	}
//		        	
//		        	if(movingAvg.getShortPrices().size() > 0 && movingAvg.getLongPrices().size() > 0) {
//						List<Double> shortP = movingAvg.getShortPrices();
//						List<Double> longP = movingAvg.getLongPrices();
//						double recentShort = shortP.get(shortP.size()-1);
//						double recentLong = longP.get(longP.size()-1);
//						
//						int volume = 100;
//						double price = (s.getAskPrice()+s.getBidPrice())/2;
//						String transtype = "";
//						String strategyStr = "TwoMAvg";
//						
//						Transaction t;
//						if(recentShort > recentLong) {
//							transtype = "Buy";
//							Stock st = bean.findStock(s);
//				        	t = new Transaction(st, symbol, volume, price, transtype, strategyStr);
//				        	bean.saveTransaction(t);
//				        } else if(recentShort < recentLong) {
//				        	transtype = "Sell";
//				        	Stock st = bean.findStock(s);
//				        	t = new Transaction(st, symbol, volume, price, transtype, strategyStr);
//				        	bean.saveTransaction(t);
//				        } else {
//				        	System.out.println("EQUAL: " + recentShort + " AND " + recentLong);
//				        }
//					}
//	        	}
			}
		} catch(Exception ex) {
			log.error("ERROR " + ex.getMessage());
		}
	}
}
