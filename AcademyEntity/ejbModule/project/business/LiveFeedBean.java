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
	
	private static boolean clear = true;
	private static long startTime;

	@Asynchronous
	public void runLiveData() {
		Logger log =  Logger.getLogger(this.getClass());
		try {
			InitialContext context = new InitialContext();
			MasterBeanLocal bean = (MasterBeanLocal)context.lookup("java:comp/env/ejb/Master");
			
			//Set start time of the application
			int shortTime = 1;
			int longTime = 2;
			
			if(clear) {
				bean.clearStock();
				clear = false;
				startTime = System.currentTimeMillis();
			}
		
			String st1 = bean.getDiv1();
			String st2 = bean.getDiv2();
			String st3 = bean.getDiv3();
			String st4 = bean.getDiv4();
			String st5 = bean.getDiv5();
			String st6 = bean.getDiv6();
			String st7 = bean.getDiv7();
			String st8 = bean.getDiv8();
			String st9 = bean.getDiv9();

			
			String[] stocks = {st1, st2, st3, st4, st5, st6, st7, st8, st9};
	        
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
    			boolean missing = false;
				for(int l=0;l<fields.length;l++) {
					if(fields[l].equals("N/A")) {
						missing = true;
					}
				}
				
				if(missing) {
					log.info("INFO Missing Data In Feed");
				} else {
					fields[0] = fields[0].replace("\"", "");
	        		String symbol = fields[0];
		        	double bidPrice = Math.round(Double.parseDouble(fields[1]) * 100.0)/100.0;
		        	double askPrice = Math.round(Double.parseDouble(fields[2]) * 100.0)/100.0;
		        	double high = Math.round(Double.parseDouble(fields[3]) * 100.0)/100.0;
		        	double low = Math.round(Double.parseDouble(fields[4]) * 100.0)/100.0;
		        	double open = Math.round(Double.parseDouble(fields[5]) * 100.0)/100.0;		        	
		        	double close = Math.round(Double.parseDouble(fields[6]) * 100.0)/100.0;
		        	Stock s = new Stock(symbol, bidPrice, askPrice, high, low, open, close);
	        		
	        		bean.saveStock(s);
	        		
	        		Thread.sleep(1000);
	        		
	        		if(((System.currentTimeMillis()-startTime) >= shortTime*60*1000) &&
			        		((System.currentTimeMillis()-startTime) >= longTime*60*1000)){
		        		
	        			for(int t=0;t<Strategy.getTwoMAvg().size();t++) {
			        		TwoMovingAverage movingAvg = Strategy.getTwoMAvg().get(t);
			        		
							if(s.getStockSymbol().equals(movingAvg.getStock()) && movingAvg.isActive()) {
				        		movingAvg.calcMovingAverage(startTime);
								movingAvg.carryOutTransaction(s);
				        	}
				        }
		        	}
				}
        	}
		} catch(Exception ex) {
			log.error("ERROR " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
