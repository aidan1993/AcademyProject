package project.business;

import java.util.List;

import javax.ejb.Remote;

import project.entity.Stock;
import project.entity.Transaction;

@Remote
public interface MasterBeanRemote {
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
	List<Transaction> retrieveMostRecent1();

	void setDiv1(String div1);
	void setDiv2(String div2);
	void setDiv3(String div3);
	void setDiv4(String div4);
	void setDiv5(String div5);
	void setDiv6(String div6);
	void setDiv7(String div7);
	void setDiv8(String div8);
	void setDiv9(String div9);
	
	String getDiv1();
	String getDiv2();
	String getDiv3();
	String getDiv4();
	String getDiv5();
	String getDiv6();
	String getDiv7();
	String getDiv8();
	String getDiv9();
}
