package project.business;

import java.util.List;

import javax.ejb.Remote;

import project.entity.Stock;

@Remote
public interface StockBeanRemote {
	void saveStock(Stock s);
	void deleteStock(Stock s);
	Stock findStock(Stock s);
	List<Stock> retrieveAllStock();
	List<Stock> retrieveMostRecent();
	void clearStock();
}
