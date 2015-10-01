package project.business;

import java.util.List;

import javax.ejb.Remote;

import project.entity.Stock;
import project.entity.Transaction;

@Remote
public interface TransactionBeanRemote {
	Transaction findTransaction(Transaction t);

	void deleteTransaction(Transaction t);

	void saveTransaction(Transaction t);

	List<Transaction> retrieveAllTransaction();

	void clearTransaction();
}
