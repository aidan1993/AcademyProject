package project.business;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import project.entity.Stock;
import project.entity.Transaction;

@Stateless
@Remote(MasterBeanRemote.class)
@Local(MasterBeanLocal.class)
public class MasterBean implements MasterBeanLocal, MasterBeanRemote {
	@PersistenceContext(unitName = "JPADB")
	private EntityManager entityManager;
	
	static String div1 = "DOM.L";
	static String div2 = "BOK.L";
	static String div3 = "BRBY.L";
	static String div4 = "ITV.L";
	static String div5 = "CTY.L";
	static String div6 = "DLN.L";
	static String div7 = "ULVR.L";
	static String div8 = "GSK.L";
	static String div9 = "DOM.L";
	
	public MasterBean() {
		
	}
	
	/**
	 * Stock Table Methods	
	 */

	@Override
	public void saveStock(Stock s) {
		entityManager.merge(s);
		entityManager.flush();
	}

	@Override
	public void deleteStock(Stock s) {
		//Need to find shipper before deleting
		entityManager.remove(s);
	}

	@Override
	public Stock findStock(Stock s) {
		Stock st = entityManager.find(Stock.class, s.getStockId());
		return st;
	}

	@Override
	public List<Stock> retrieveAllStock() {
		String q = "SELECT s FROM " + Stock.class.getName() + " s";
		Query query = entityManager.createQuery(q);
		List<Stock> stocks = query.getResultList();
		return stocks;
	}
	
	@Override
	public List<Integer> retrieveMaxId(Stock s) {
		String q = "SELECT s FROM " + Stock.class.getName() + " s " + 
				"WHERE s.stockSymbol = :symbol AND s.stockid = (" +
				"SELECT MAX(ss.stockid) FROM " + Stock.class.getName() + " ss)";

		Query query = entityManager.createQuery(q);
		query.setParameter("symbol", s.getStockSymbol());
		query.setMaxResults(1);
		List<Integer> stockid = query.getResultList();
		return stockid;
	}
	
	@Override
	public List<Stock> retrieveMostRecent(String stock) {
		String q = "SELECT s FROM " + Stock.class.getName() + " s " + 
					"WHERE s.stockSymbol = :symbol " +
					"ORDER BY s.time_Of DESC";
		Query query = entityManager.createQuery(q);
		query.setParameter("symbol", stock);
		query.setMaxResults(1);
		List<Stock> stocks = query.getResultList();
		return stocks;
	}
	
	@Override
	public void clearStock() {
		String q = "DELETE FROM " + Stock.class.getName();
		int rows = entityManager.createQuery(q).executeUpdate();
		
		if(rows > 0) {
			System.out.println("Database Cleared");
		}
	}

	@Override
	public List<Stock> retrieveMovingAvgStock(int avgTime, String stock) {
		//Format date to an acceptable SQL format
		Date start = new Date(System.currentTimeMillis()-avgTime*60*1000);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startStr = formatter.format(start);
		
		String q = "SELECT s FROM " + Stock.class.getName() + " s" + 
					" WHERE s.time_Of >= :time " + 
					" AND s.stockSymbol = :symbol ";
		Query query = entityManager.createQuery(q);
		query.setParameter("time", startStr);
		query.setParameter("symbol", stock);
		List<Stock> stocks = query.getResultList();
		return stocks;
	}
	
/*	@Override
	public List<Stock> retrieveTop5Stock() {
		String q = "SELECT TOP 5 FROM " + Stock.class.getName();
		Query query = entityManager.createQuery(q);
		List<Stock> stocks = query.getResultList();
		return stocks;
	}*/
	
	/**
	 * Transaction Table Methods	
	 */
	
	@Override
	public void saveTransaction(Transaction t) {
		entityManager.merge(t);
		entityManager.flush();
	}

	@Override
	public void deleteTransaction(Transaction t) {
		entityManager.remove(t);

	}

	@Override
	public Transaction findTransaction(Transaction t) {
		Transaction tr = entityManager.find(Transaction.class,
				t.getTransactionid());
		return tr;

	}

	@Override
	public List<Transaction> retrieveAllTransaction() {
		String q = "SELECT t FROM " + Transaction.class.getName() + " t";
		Query query = entityManager.createQuery(q);
		List<Transaction> Transaction = query.getResultList();
		return Transaction;

	}
	
	@Override
	public List<Transaction> retrieveMostRecent1() {
	String qt = "SELECT t FROM " + Transaction.class.getName() + " t " + 
						"ORDER BY Transactionid DESC";
	Query query = entityManager.createQuery(qt);
	query.setMaxResults(5);
	List<Transaction> transactions = query.getResultList();
	return transactions;
		
	}

	@Override
	public void clearTransaction() {
		String q = "DELETE FROM " + Transaction.class.getName();
		int rows = entityManager.createQuery(q).executeUpdate();

		if (rows > 0) {
			System.out.println("Database Cleared");

		}
	}
	
	/*
	 * Set and Get Stock Symbols
	 */
	@Override
	public String getDiv1() {
		return div1;
	}
	@Override
	public void setDiv1(String div1) {
		MasterBean.div1 = div1;
	}
	@Override
	public String getDiv2() {
		return div2;
	}
	@Override
	public void setDiv2(String div2) {
		MasterBean.div2 = div2;
	}
	@Override
	public String getDiv3() {
		return div3;
	}
	@Override
	public void setDiv3(String div3) {
		MasterBean.div3 = div3;
	}
	@Override
	public String getDiv4() {
		return div4;
	}
	@Override
	public void setDiv4(String div4) {
		MasterBean.div4 = div4;
	}
	@Override
	public String getDiv5() {
		return div5;
	}
	@Override
	public void setDiv5(String div5) {
		MasterBean.div5 = div5;
	}
	@Override
	public String getDiv6() {
		return div6;
	}
	@Override
	public void setDiv6(String div6) {
		MasterBean.div6 = div6;
	}
	@Override
	public String getDiv7() {
		return div7;
	}
	@Override
	public void setDiv7(String div7) {
		MasterBean.div7 = div7;
	}
	@Override
	public String getDiv8() {
		return div8;
	}
	@Override
	public void setDiv8(String div8) {
		MasterBean.div8 = div8;
	}
	@Override
	public String getDiv9() {
		return div9;
	}
	@Override
	public void setDiv9(String div9) {
		MasterBean.div9 = div9;
	}
}
