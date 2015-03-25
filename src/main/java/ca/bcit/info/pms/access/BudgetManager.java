package ca.bcit.info.pms.access;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ca.bcit.info.pms.model.Budget;
import ca.bcit.info.pms.model.WorkPackage;

@Dependent
@Stateless
public class BudgetManager implements Serializable {
	@PersistenceContext(unitName = "pms-persistence-unit") private EntityManager em;
	
	public Budget find(final int id) {
		return em.find(Budget.class, id);
	}
	
	public void remove(final Budget budget) {
		Budget b = find(budget.getId());
		em.remove(b);
	}
	
	public void merge(final Budget budget) {
		em.merge(budget);
	}
	
	public void persist(final Budget newBudget) {
		em.persist(newBudget);
	}
	
	public int getTotalBudget(final int wpId) {
		Query query = em.createNativeQuery("select * "
				+ "from budget "
				+ "where budgetId = :wpId", Budget.class)
				.setParameter("wpId", wpId);
		try {
			Budget budget = (Budget) query.getSingleResult();
		    int totalBudget = budget.getDS() + budget.getJS() + budget.getSS() + budget.getP1() + budget.getP2()
		    		+ budget.getP3() + budget.getP4() + budget.getP5() + budget.getP6();
			return totalBudget;
		} catch (NoResultException e) {
			return 0;
		}
	}
	
	public Budget getBudget(final int wpId) {
		Query query = em.createNativeQuery("select * "
				+ "from budget "
				+ "where budgetId = :wpId", Budget.class)
				.setParameter("wpId", wpId);
		try {
			Budget budget = (Budget) query.getSingleResult();
			return budget;
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
