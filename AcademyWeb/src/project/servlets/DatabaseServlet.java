package project.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.business.StockBeanLocal;
import project.entity.Stock;
import project.feed.LiveFeed;
import project.strategies.TwoMovingAverage;

/**
 * Servlet implementation class DatabaseServlet
 */
@WebServlet("/DatabaseServlet")
@EJB(name="ejb/Stock", beanInterface=StockBeanLocal.class)
public class DatabaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DatabaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			InitialContext context = new InitialContext();
			StockBeanLocal bean = (StockBeanLocal)context.lookup("java:comp/env/ejb/Stock");
			Stock s;
			
			bean.clearStock();
			
			//Set start time of the application
			long startTime = System.currentTimeMillis();
			int shortTime = 1;
			int longTime = 2;
			boolean missing = false;
			String[] stocks = {"IBM", "MSFT", "GOOG", "GE", "YHOO", "CSCO", "AAPL", "BAB", "AV"};
			TwoMovingAverage twoMAvg = new TwoMovingAverage("AV", shortTime, longTime);
			while(true) {
				for(int i=0;i<stocks.length;i++) {
					String[] fields = LiveFeed.runLiveFeed(stocks[i]);
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
		        	} else {
		        		s = new Stock(symbol, bidPrice, askPrice, close);
		        	}
		        	
		        	if(symbol.equals(twoMAvg.getStock())) {
		        		twoMAvg.calcMovingAverage(startTime);
		        	}
			        
			        bean.saveStock(s);

				}
		        
				if(twoMAvg.getShortPrices().size() > 0 && twoMAvg.getLongPrices().size() > 0) {
					List<Double> shortP = twoMAvg.getShortPrices();
					List<Double> longP = twoMAvg.getLongPrices();
					double recentShort = shortP.get(shortP.size()-1);
					double recentLong = longP.get(longP.size()-1);
					
					if(recentShort > recentLong) {
			        	System.out.println("BUY: " + recentShort + " GREATER THAN " + recentLong);
			        } else if(recentShort < recentLong) {
			        	System.out.println("SELL: " + recentShort + " LESS THAN " + recentLong);
			        } else {
			        	System.out.println("EQUAL: " + recentShort + " AND " + recentLong);
			        }
				}
			}
			
		} catch(Exception ex) {
			out.println("Exception occurred: " + ex.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
