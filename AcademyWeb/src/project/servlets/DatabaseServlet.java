package project.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.business.StockBeanLocal;
import project.entity.Stock;

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
			Stock s = new Stock();
			
			bean.clearStock();
			
			long startTime = System.currentTimeMillis();
			while((System.currentTimeMillis()-startTime) < 1*60*1000*100) {
				String[] stocks = {"AAPL", "MSFT"};
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
		        
		        while((inputLine = in.readLine()) != null)
		        {	
		        	String[] fields = inputLine.split(",");
		        	fields[0] = fields[0].replace("\"", "");
		        	
		        	String symbol = fields[0];
		        	double bidPrice = Double.parseDouble(fields[1]);
		        	double askPrice = Double.parseDouble(fields[2]);
		        	
		        	//Round data to two decimal places
		        	double high = Math.round(Double.parseDouble(fields[3]) * 100.0)/100.0;
		        	double low = Math.round(Double.parseDouble(fields[4]) * 100.0)/100.0;
		        	double open = Math.round(Double.parseDouble(fields[5]) * 100.0)/100.0;
		        	double close = Math.round(Double.parseDouble(fields[6]) * 100.0)/100.0;
		        	double avg = (bidPrice + askPrice)/2;
		        	avg = Math.round(avg * 100.0)/100.0;
		        	
		        	s.setStockSymbol(symbol);
		        	s.setBidPrice(bidPrice);
		        	s.setAskPrice(askPrice);
		        	s.setMovingAvg(avg);
		        	s.setDayHigh(high);
		        	s.setDayLow(low);
		        	s.setTodaysOpen(open);
		        	s.setPreviousClose(close);
			        bean.saveStock(s);
		            			        
			        
		        }
			}
			
			List<Stock> stocks = bean.retrieveAllStock();
			for(Stock st : stocks) {
				out.println(st.toString() + "<br>");
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
