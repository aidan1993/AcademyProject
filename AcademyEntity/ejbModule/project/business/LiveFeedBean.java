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
	
	private static int clear = 1;
	private static long startTime;
	
	@Asynchronous
	public void runLiveData(int loop) {
		Logger log =  Logger.getLogger(this.getClass());
		try {
			InitialContext context = new InitialContext();
			MasterBeanLocal bean = (MasterBeanLocal)context.lookup("java:comp/env/ejb/Master");
			
			if(clear == 1) {
				bean.clearStock();
				clear++;
				startTime = System.currentTimeMillis();
			}
						
			Strategy strategy = new Strategy();
			
			//Set start time of the application
			int shortTime = 1;
			int longTime = 2;
			TwoMovingAverage bpMAvg = new TwoMovingAverage("DOM.L", shortTime, longTime);
			strategy.addTwoMAvg(bpMAvg);
			
			for(int i=0;i<loop;i++) {
				String[] stocks = {bean.getDiv1(), bean.getDiv2(), bean.getDiv3(), bean.getDiv4(), 
						bean.getDiv5(), bean.getDiv6(), bean.getDiv7(), bean.getDiv8(), bean.getDiv9()};
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
						if(fields[i].equals("N/A")) {
							missing = true;
						}
					}
		        	
		        	Stock s;
		        	if(missing == false) {
		        		fields[0] = fields[0].replace("\"", "");
		        		String symbol = fields[0];
			        	double bidPrice = Double.parseDouble(fields[1]);
			        	double askPrice = Double.parseDouble(fields[2]);
			        	double high = Math.round(Double.parseDouble(fields[3]) * 100.0)/100.0;
			        	double low = Math.round(Double.parseDouble(fields[4]) * 100.0)/100.0;
			        	double open = Math.round(Double.parseDouble(fields[5]) * 100.0)/100.0;		        	
			        	double close = Math.round(Double.parseDouble(fields[6]) * 100.0)/100.0;
		        		s = new Stock(symbol, bidPrice, askPrice, high, low, open, close);
		        		bean.saveStock(s);
		        		
		        		if(((System.currentTimeMillis()-startTime) >= shortTime*60*1000) &&
				        		((System.currentTimeMillis()-startTime) >= longTime*60*1000)){
			        		for(int t=0;t<strategy.getTwoMAvg().size();t++) {
				        		TwoMovingAverage movingAvg = strategy.getTwoMAvg().get(t);
				        		
								if(s.getStockSymbol().equals(movingAvg.getStock())) {
					        		movingAvg.calcMovingAverage(startTime);
									movingAvg.carryOutTransaction(s, movingAvg);
					        	}
					        }
			        	}
		        	} else {
		        		System.out.println("Missing Data For Stock");
		        	}
		        	
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

	public static long getStartTime() {
		return startTime;
	}

	public static void setStartTime(long startTime) {
		LiveFeedBean.startTime = startTime;
	}
}
