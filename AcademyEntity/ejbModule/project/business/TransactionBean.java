package project.business;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import project.entity.Transaction;

@Stateless
@Remote(TransactionBeanRemote.class)
@Local(TransactionBeanLocal.class)
public class TransactionBean implements TransactionBeanLocal,
		TransactionBeanRemote {
	@PersistenceContext(unitName = "JPADB")
	private EntityManager entityManager;

	public TransactionBean() {

	}

	@Override
	public void saveTransaction(Transaction t) {
		entityManager.persist(t);

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
		String q = "SELECT t FROM " + Transaction.class.getName() + " s";
		Query query = entityManager.createQuery(q);
		List<Transaction> Transaction = query.getResultList();
		return Transaction;

	}

	@Override
	public void clearTransaction() {
		String q = "DELETE FROM " + Transaction.class.getName();
		int rows = entityManager.createQuery(q).executeUpdate();

		if (rows > 0) {
			System.out.println("Database Cleared");

		}
	}
}
