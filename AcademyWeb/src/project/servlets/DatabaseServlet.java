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
			while((System.currentTimeMillis()-startTime) < 1*60*1000) {
				String[] stocks = {"MSFT", "AAPL", "IBM", "CSCO"};
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
		        	
		        	s.setStockSymbol(fields[0]);
		        	s.setBidPrice(Double.parseDouble(fields[1]));
		        	s.setAskPrice(Double.parseDouble(fields[2]));
		        	s.setMovingAvg(24);
		        	s.setTodaysOpen(24);
		        	s.setPreviousClose(24);
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
