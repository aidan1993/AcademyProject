package project.business;

import java.util.List;

import javax.ejb.Local;

import project.entity.Stock;

@Local
public interface StockBeanLocal {
	void saveStock(Stock s);
	void deleteStock(Stock s);
	Stock findStock(Stock s);
	List<Stock> retrieveAllStock();
	List<Stock> retrieveMostRecent();
	void clearStock();
}
