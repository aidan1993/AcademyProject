package project.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.business.StockBeanLocal;
import project.business.TransactionBeanLocal;
import project.entity.Stock;
import project.entity.Transaction;

@WebServlet("/StockTransactionServlet")
public class StockTransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public StockTransactionServlet() {
        super();
  
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			
			InitialContext context = new InitialContext();
			TransactionBeanLocal bean = (TransactionBeanLocal)context.lookup("java:comp/env/ejb/Transaction");
			Transaction t = new Transaction();
			//t.setStockSymbol(request.);
			
			/*	An if statement calling method from trade strategies placed here will
			 * 	enable the trades to be added to the transaction table if the proper
			 * 	conditions are met.
			 * 	
			 * 
			 */
	        
	        
	} catch(Exception ex) {
		out.println("Exception occurred: " + ex.getMessage());
	}
	}
	


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
