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

import org.jboss.logging.Logger;

import project.business.StockBeanLocal;
import project.business.TransactionBeanLocal;
import project.entity.Transaction;
import project.entity.Stock;

@WebServlet("/StockTransactionServlet")
@EJB(name="ejb/Transaction", beanInterface=TransactionBeanLocal.class)
public class StockTransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public StockTransactionServlet() {
        super();
  
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Logger log =  Logger.getLogger(this.getClass());
		try {
			
			InitialContext context2 = new InitialContext();
			TransactionBeanLocal Bean = (TransactionBeanLocal) context2
					.lookup("java:comp/env/ejb/Transaction");
			
			Transaction t = new Transaction();
			Bean.saveTransaction(t);
			
	        
	} catch(Exception ex) {
		log.error("ERROR " + ex.getMessage());
	}
	}
	


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
