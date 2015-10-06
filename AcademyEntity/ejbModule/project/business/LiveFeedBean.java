package project.business;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import project.dataaccess.DataAccess;
import project.entity.Stock;
import project.strategies.Strategy;
import project.strategies.TwoMovingAverage;

@Stateless
@Remote(LiveFeedBeanRemote.class)
@Local(LiveFeedBeanLocal.class)
public class LiveFeedBean implements LiveFeedBeanLocal, LiveFeedBeanRemote {
	
	@Asynchronous
	public void runLiveData() {
		Logger log =  Logger.getLogger(this.getClass());
		
		try {
			
			
			DataAccess.clearStock();
			
			Stock s;
			String[] stocks = {"TSCO", "AAPL", "BP"};			
			Strategy strategy = new Strategy();
			
			//Set start time of the application
			long startTime = System.currentTimeMillis();
			int shortTime = 1;
			int longTime = 2;
			boolean missing = false;
			TwoMovingAverage bpMAvg = new TwoMovingAverage("TSCO", shortTime, longTime);
			strategy.addTwoMAvg(bpMAvg);
			while(true) {
				
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
		        	
		        	if(missing == false) {
		        		s = new Stock(symbol, bidPrice, askPrice, high, low, open, close);
		        		DataAccess.saveStock(s);
		        	} else {
		        		s = new Stock(symbol, bidPrice, askPrice, close);
		        		DataAccess.saveStock(s);
		        	}
		        	
		        	List<Stock> sts = DataAccess.getAllStocks();
		        	for(Stock st : sts) {
		        		System.out.println(st.toString());
		        	}
		        	
//		        	if(((System.currentTimeMillis()-startTime) >= shortTime*60*1000) &&
//			        		((System.currentTimeMillis()-startTime) >= longTime*60*1000)){
//		        		for(int i=0;i<strategy.getTwoMAvg().size();i++) {
//			        		TwoMovingAverage movingAvg = strategy.getTwoMAvg().get(i);
//			        		
//							if(s.getStockSymbol().equals(movingAvg.getStock())) {
//				        		movingAvg.calcMovingAverage(startTime);
//								movingAvg.carryOutTransaction(stock.get(0), movingAvg);
//				        	}
//				        }
//		        	}
		        	
		        	try {
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
						log.error("ERROR " + ex.getMessage());
					}
				}
				
				
			}
		} catch(Exception ex) {
			log.error("ERROR " + ex.getMessage());
		}
	}
}
