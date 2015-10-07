package project.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import project.business.LiveFeedBean;
import project.business.LiveFeedBeanLocal;
import project.business.MasterBeanLocal;

@WebServlet("/EditStockSymbol")
@EJB(name="ejb/Master", beanInterface=MasterBeanLocal.class)
public class EditStockSymbol extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditStockSymbol() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Logger log = Logger.getLogger(this.getClass());

		try {

			if (request.getParameter("setStockPrefs") != null) {
				
				InitialContext context = new InitialContext();
				MasterBeanLocal bean = (MasterBeanLocal)context.lookup("java:comp/env/ejb/Master");

				bean.setDiv1(request.getParameter("txtDiv1"));
				bean.setDiv2(request.getParameter("txtDiv2"));
				bean.setDiv3(request.getParameter("txtDiv3"));
				bean.setDiv4(request.getParameter("txtDiv4"));
				bean.setDiv5(request.getParameter("txtDiv5"));
				bean.setDiv6(request.getParameter("txtDiv6"));
				bean.setDiv7(request.getParameter("txtDiv7"));
				bean.setDiv8(request.getParameter("txtDiv8"));
				bean.setDiv9(request.getParameter("txtDiv9")); 
			}

			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);

		} catch (NamingException e) {
			
			//Add logger
			e.printStackTrace();

		}
	}

}
