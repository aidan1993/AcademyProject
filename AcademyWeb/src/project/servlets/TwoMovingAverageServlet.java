package project.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.strategies.Strategy;
import project.strategies.TwoMovingAverage;

/**
 * Servlet implementation class TwoMovingAverageServlet
 */
@WebServlet("/TwoMovingAverageServlet")
public class TwoMovingAverageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TwoMovingAverageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		boolean exists = false;
		String checked = request.getParameter("activate");
		String symbol = request.getParameter("selSymbol");
		int shortAvg = Integer.parseInt(request.getParameter("shortAvg"));
		int longAvg = Integer.parseInt(request.getParameter("shortAvg"));
		
		List<TwoMovingAverage> twoMAvg = Strategy.getTwoMAvg();
		
		//Check to see if strategy is already implemented
		for(TwoMovingAverage mAvg : twoMAvg) {
			if(mAvg.getStock().equals(symbol)) {
				exists = true;
				mAvg.setShortLength(shortAvg);
				mAvg.setLongLength(longAvg);
				
				if(checked != null) {
					mAvg.setActive(true);
				} else {
					mAvg.setActive(false);
				}
			}
			
			System.out.println(mAvg.getStock());
		}
		
		if(!exists && checked != null) {
			TwoMovingAverage newMAvg = new TwoMovingAverage(symbol, shortAvg, longAvg, true);
			Strategy.addTwoMAvg(newMAvg);
		}
		
//		response.sendRedirect(request.getContextPath() + "/index.jsp");
		
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/index.jsp");
		
		request.setAttribute("movingAvgList", twoMAvg);
		rd.forward(request, response);
	}

}
