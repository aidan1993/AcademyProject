package project.business;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import project.entity.Stock;

@Stateless
@Remote(StockBeanRemote.class)
@Local(StockBeanLocal.class)
public class StockBean implements StockBeanLocal, StockBeanRemote {
	@PersistenceContext(unitName = "JPADB")
	private EntityManager entityManager;
	
	public StockBean() {
		
	}

	@Override
	public void saveStock(Stock s) {
		entityManager.persist(s);
		
//		entityManager.merge(s);
//		entityManager.flush();
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
	
	
	
	
}