package project.business;

import java.util.List;

import javax.ejb.Local;

import project.entity.Stock;
import project.entity.Transaction;

@Local
public interface MasterBeanLocal {
	void saveStock(Stock s);
	void deleteStock(Stock s);
	Stock findStock(Stock s);
	List<Stock> retrieveAllStock();
	List<Integer> retrieveMaxId(Stock s);
	List<Stock> retrieveMostRecent(String stock);
	List<Stock> retrieveMovingAvgStock(int avgTime, String stock);
	void clearStock();
	
	Transaction findTransaction(Transaction t);
	void deleteTransaction(Transaction t);
	void saveTransaction(Transaction t);
	List<Transaction> retrieveAllTransaction();
	void clearTransaction();
}
