package project.business;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import project.entity.Transactions;

@Stateless
@Remote(TransactionBeanRemote.class)
@Local(TransactionBeanLocal.class)
public class TransactionBean implements TransactionBeanLocal, TransactionBeanRemote{
	@PersistenceContext(unitName = "JPADB")
	private EntityManager entityManager;
	
public TransactionBean() {	
	
}
	

@Override
public void saveTransaction(Transactions t) {
	entityManager.persist(t);
	
//	entityManager.merge(s);
//	entityManager.flush();
	
}

@Override
public void deleteTransaction(Transactions t) {
	//Need to find shipper before deleting
			entityManager.remove(t);
	
}

@Override
public Transactions findTransaction(Transactions t) {
	Transactions tr = entityManager.find(Transactions.class, t.getTransactionid());
	return tr;
	
}

@Override
public List<Transactions> retrieveAllTransaction() {
	String q = "SELECT t FROM " + Transactions.class.getName() + " s";
	Query query = entityManager.createQuery(q);
	List<Transactions> Transactions = query.getResultList();
	return Transactions;
	
}

@Override
public void clearTransaction() {
	String q = "DELETE FROM " + Transactions.class.getName();
	int rows = entityManager.createQuery(q).executeUpdate();
	
	if(rows > 0) {
		System.out.println("Database Cleared");
	
}
}
}

