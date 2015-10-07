package project.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import project.business.LiveFeedBeanLocal;

/**
 * Servlet implementation class StartFeed
 */
@WebServlet("/StartFeed")
@EJB(name="ejb/LiveFeed", beanInterface=LiveFeedBeanLocal.class)
public class StartFeed extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartFeed() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			InitialContext context = new InitialContext();
			LiveFeedBeanLocal bean = (LiveFeedBeanLocal)context.lookup("java:comp/env/ejb/LiveFeed");
			
			bean.runLiveData(10);
			
		} catch(Exception ex) {
			Logger log =  Logger.getLogger(this.getClass());
			log.error("ERROR " + ex.getMessage());
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
