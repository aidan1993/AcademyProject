package project.business;

import java.util.List;

import javax.ejb.Local;

import project.entity.Transactions;


@Local
public interface TransactionBeanLocal {

	Transactions findTransaction(Transactions t);

	void deleteTransaction(Transactions t);

	void saveTransaction(Transactions t);

	List<Transactions> retrieveAllTransaction();

	void clearTransaction();

}
