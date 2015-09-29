package project.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LiveFeed {
	
	public static void getLiveFeed(int duration) throws Exception {
		
		long startTime = System.currentTimeMillis();
		while((System.currentTimeMillis()-startTime) < duration*60*1000) {
			String[] stocks = {"AAPL", "IBM", "CSCO"};
			StringBuilder url = 
		            new StringBuilder("http://finance.yahoo.com/d/quotes.csv?s=");
			for(String stock : stocks) {
				url.append(stock + ",");
			}
	        url.append("&f=sba&e=.csv");
	        
	        String theUrl = url.toString();
	        URL obj = new URL(theUrl);
	        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	        // This is a GET request
	        con.setRequestMethod("GET");
	        con.setRequestProperty("User-Agent", "Mozilla/5.0");
	        int responseCode = con.getResponseCode();
	        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        String inputLine;
	        
	        while((inputLine = in.readLine()) != null)
	        {	
	        	String[] fields = inputLine.split(",");
	
		        DataAccess.addStock(fields[0], fields[1], 24, fields[2], 24, 24, 24);
	        
	        	System.out.println(inputLine);
	        	
	        }
		}
	}
}
