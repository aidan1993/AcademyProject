package project.business;

import java.util.List;

import javax.ejb.Local;

import project.entity.Transaction;

@Local
public interface TransactionBeanLocal {

	Transaction findTransaction(Transaction t);

	void deleteTransaction(Transaction t);

	void saveTransaction(Transaction t);

	List<Transaction> retrieveAllTransaction();

	void clearTransaction();

}
