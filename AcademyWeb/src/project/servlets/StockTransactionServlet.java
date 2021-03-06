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

import project.business.MasterBeanLocal;
import project.entity.Stock;
import project.entity.Transaction;

@WebServlet(urlPatterns={"/StockTransactionServlet"}, asyncSupported=true)
@EJB(name="ejb/Master", beanInterface=MasterBeanLocal.class)
public class StockTransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public StockTransactionServlet() {
        super();
  
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Logger log =  Logger.getLogger(this.getClass());
		try {	
			
			InitialContext context = new InitialContext();
			MasterBeanLocal bean = (MasterBeanLocal)context.lookup("java:comp/env/ejb/Master");
			Transaction t = new Transaction();
						
			List<Stock> divList1 = bean.retrieveMostRecent(bean.getDiv1());	
			System.out.println(divList1.get(0).toString());
			if(request.getParameter("buyStockDiv1") != null) {
				for (Stock s: divList1) {
					t.setStockSymbol(s.getStockSymbol());
					t.setStock(s);
					t.setPrice(s.getAskPrice());
					t.setTranstype("BUY");
					t.setStrategy("Manual");
					t.setVolume(Integer.parseInt(request.getParameter("txtQuantity")));
					bean.saveTransaction(t);
					//Integer.parseInt(request.getParameter("txtQuantity"))
					
				}
			}
			 if(request.getParameter("sellStockDiv1") != null) {
					for (Stock s: divList1) {
						
						t.setStockSymbol(s.getStockSymbol());
						t.setStock(s);
						t.setPrice(s.getBidPrice());
						t.setTranstype("SELL");
						t.setStrategy("Manual");
						t.setVolume(Integer.parseInt(request.getParameter("txtQuantity")));
						bean.saveTransaction(t);
					}
			 }
			 
			 List<Stock> divList2 = bean.retrieveMostRecent(bean.getDiv2());	
				
			 if(request.getParameter("buyStockDiv2") != null) {
				for (Stock s: divList2) {
					t.setStockSymbol(s.getStockSymbol());
					t.setStock(s);
					t.setPrice(s.getAskPrice());
					t.setTranstype("BUY");
					t.setStrategy("Manual");
					t.setVolume(Integer.parseInt(request.getParameter("txtQuantity")));
					bean.saveTransaction(t);
					//Integer.parseInt(request.getParameter("txtQuantity"))
					
			}
				}
			 if(request.getParameter("sellStockDiv2") != null) {
					for (Stock s: divList2) {
						
						t.setStockSymbol(s.getStockSymbol());
						t.setStock(s);
						t.setPrice(s.getBidPrice());
						t.setTranstype("SELL");
						t.setStrategy("Manual");
						t.setVolume(Integer.parseInt(request.getParameter("txtQuantity")));
						bean.saveTransaction(t);
					}
			 }
			 
			 List<Stock> divList3 = bean.retrieveMostRecent(bean.getDiv3());	
				
			 if(request.getParameter("buyStockDiv3") != null) {
				for (Stock s: divList3) {
					t.setStockSymbol(s.getStockSymbol());
					t.setStock(s);
					t.setPrice(s.getAskPrice());
					t.setTranstype("BUY");
					t.setStrategy("Manual");
					t.setVolume(Integer.parseInt(request.getParameter("txtQuantity")));
					bean.saveTransaction(t);
					//Integer.parseInt(request.getParameter("txtQuantity"))
					
			}
				}
			 if(request.getParameter("sellStockDiv3") != null) {
					for (Stock s: divList3) {
						
						t.setStockSymbol(s.getStockSymbol());
						t.setStock(s);
						t.setPrice(s.getBidPrice());
						t.setTranstype("SELL");
						t.setStrategy("Manual");
						t.setVolume(Integer.parseInt(request.getParameter("txtQuantity")));
						bean.saveTransaction(t);
					}
			 }
			 List<Stock> divList4 = bean.retrieveMostRecent(bean.getDiv4());	
				
			 if(request.getParameter("buyStockDiv4") != null) {
				for (Stock s: divList4) {
					t.setStockSymbol(s.getStockSymbol());
					t.setStock(s);
					t.setPrice(s.getAskPrice());
					t.setTranstype("BUY");
					t.setStrategy("Manual");
					t.setVolume(Integer.parseInt(request.getParameter("txtQuantity")));
					bean.saveTransaction(t);
					//Integer.parseInt(request.getParameter("txtQuantity"))
					
			}
				}
			 if(request.getParameter("sellStockDiv4") != null) {
					for (Stock s: divList4) {
						
						t.setStockSymbol(s.getStockSymbol());
						t.setStock(s);
						t.setPrice(s.getBidPrice());
						t.setTranstype("SELL");
						t.setStrategy("Manual");
						t.setVolume(Integer.parseInt(request.getParameter("txtQuantity")));
						bean.saveTransaction(t);
					}
			 }
			 List<Stock> divList5 = bean.retrieveMostRecent(bean.getDiv5());	
				
			 if(request.getParameter("buyStockDiv5") != null) {
				for (Stock s: divList5) {
					t.setStockSymbol(s.getStockSymbol());
					t.setStock(s);
					t.setPrice(s.getAskPrice());
					t.setTranstype("BUY");
					t.setStrategy("Manual");
					t.setVolume(Integer.parseInt(request.getParameter("txtQuantity")));
					bean.saveTransaction(t);
					//Integer.parseInt(request.getParameter("txtQuantity"))
					
			}
				}
			 if(request.getParameter("sellStockDiv5") != null) {
					for (Stock s: divList5) {
						
						t.setStockSymbol(s.getStockSymbol());
						t.setStock(s);
						t.setPrice(s.getBidPrice());
						t.setTranstype("SELL");
						t.setStrategy("Manual");
						t.setVolume(Integer.parseInt(request.getParameter("txtQuantity")));
						bean.saveTransaction(t);
					}
			 }
			 List<Stock> divList6 = bean.retrieveMostRecent(bean.getDiv6());	
				
			 if(request.getParameter("buyStockDiv6") != null) {
				for (Stock s: divList6) {
					t.setStockSymbol(s.getStockSymbol());
					t.setStock(s);
					t.setPrice(s.getAskPrice());
					t.setTranstype("BUY");
					t.setStrategy("Manual");
					t.setVolume(Integer.parseInt(request.getParameter("txtQuantity")));
					bean.saveTransaction(t);
					//Integer.parseInt(request.getParameter("txtQuantity"))
					
			}
				}
			 if(request.getParameter("sellStockDiv6") != null) {
					for (Stock s: divList6) {
						
						t.setStockSymbol(s.getStockSymbol());
						t.setStock(s);
						t.setPrice(s.getBidPrice());
						t.setTranstype("SELL");
						t.setStrategy("Manual");
						t.setVolume(Integer.parseInt(request.getParameter("txtQuantity")));
						bean.saveTransaction(t);
					}
			 }
			 List<Stock> divList7 = bean.retrieveMostRecent(bean.getDiv7());	
				
			 if(request.getParameter("buyStockDiv7") != null) {
				for (Stock s: divList7) {
					t.setStockSymbol(s.getStockSymbol());
					t.setStock(s);
					t.setPrice(s.getAskPrice());
					t.setTranstype("BUY");
					t.setStrategy("Manual");
					t.setVolume(Integer.parseInt(request.getParameter("txtQuantity")));
					bean.saveTransaction(t);
					//Integer.parseInt(request.getParameter("txtQuantity"))
					
			}
				}
			 if(request.getParameter("sellStockDiv7") != null) {
					for (Stock s: divList7) {
						
						t.setStockSymbol(s.getStockSymbol());
						t.setStock(s);
						t.setPrice(s.getBidPrice());
						t.setTranstype("SELL");
						t.setStrategy("Manual");
						t.setVolume(Integer.parseInt(request.getParameter("txtQuantity")));
						bean.saveTransaction(t);
					}
			 }
			 List<Stock> divList8 = bean.retrieveMostRecent(bean.getDiv8());	
				
			 if(request.getParameter("buyStockDiv8") != null) {
				for (Stock s: divList8) {
					t.setStockSymbol(s.getStockSymbol());
					t.setStock(s);
					t.setPrice(s.getAskPrice());
					t.setTranstype("BUY");
					t.setStrategy("Manual");
					t.setVolume(Integer.parseInt(request.getParameter("txtQuantity")));
					bean.saveTransaction(t);
					//Integer.parseInt(request.getParameter("txtQuantity"))
					
			}
				}
			 if(request.getParameter("sellStockDiv8") != null) {
					for (Stock s: divList8) {
						
						t.setStockSymbol(s.getStockSymbol());
						t.setStock(s);
						t.setPrice(s.getBidPrice());
						t.setTranstype("SELL");
						t.setStrategy("Manual");
						t.setVolume(Integer.parseInt(request.getParameter("txtQuantity")));
						bean.saveTransaction(t);
					}
			 }
			 List<Stock> divList9 = bean.retrieveMostRecent(bean.getDiv9());	
				
			 if(request.getParameter("buyStockDiv9") != null) {
				for (Stock s: divList9) {
					t.setStockSymbol(s.getStockSymbol());
					t.setStock(s);
					t.setPrice(s.getAskPrice());
					t.setTranstype("BUY");
					t.setStrategy("Manual");
					t.setVolume(Integer.parseInt(request.getParameter("txtQuantity")));
					bean.saveTransaction(t);
					//Integer.parseInt(request.getParameter("txtQuantity"))
					
			}
				}
			 if(request.getParameter("sellStockDiv9") != null) {
					for (Stock s: divList9) {
						
						t.setStockSymbol(s.getStockSymbol());
						t.setStock(s);
						t.setPrice(s.getBidPrice());
						t.setTranstype("SELL");
						t.setStrategy("Manual");
						t.setVolume(Integer.parseInt(request.getParameter("txtQuantity")));
						bean.saveTransaction(t);
					}
			 }			 
			 			
			// request.getRequestDispatcher("/index.jsp").forward(request, response);
			 response.sendRedirect(request.getContextPath() + "/index.jsp");
			
		} catch(Exception ex) {
			log.error("ERROR " + ex.getMessage());
		}
	}
}