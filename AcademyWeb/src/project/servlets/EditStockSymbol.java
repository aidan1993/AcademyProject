package project.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import project.business.LiveFeedBean;
import project.business.MasterBeanLocal;

@WebServlet("/EditStockSymbol")
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

		InitialContext context;
		try {
			context = new InitialContext();

			if (request.getParameter("setStockPrefs") != null) {

				 LiveFeedBean.setDiv1(request.getParameter("txtDiv1"));
				 LiveFeedBean.setDiv2(request.getParameter("txtDiv2"));
				 LiveFeedBean.setDiv3(request.getParameter("txtDiv3"));
				 LiveFeedBean.setDiv4(request.getParameter("txtDiv4"));
				 LiveFeedBean.setDiv5(request.getParameter("txtDiv5"));
				 LiveFeedBean.setDiv6(request.getParameter("txtDiv6"));
				 LiveFeedBean.setDiv7(request.getParameter("txtDiv7"));
				 LiveFeedBean.setDiv8(request.getParameter("txtDiv8"));
				 LiveFeedBean.setDiv9(request.getParameter("txtDiv9"));
				 
			}

			response.sendRedirect(request.getContextPath() + "/index.jsp");

		} catch (NamingException e) {
			e.printStackTrace();

		}
	}

}
