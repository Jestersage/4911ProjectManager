package ca.bcit.info.pms.access;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ca.bcit.info.pms.model.Budget;
import ca.bcit.info.pms.model.EngineerBudget;

@Dependent
@Stateless
public class EngineerBudgetManager implements Serializable {
	@PersistenceContext(unitName = "pms-persistence-unit") private EntityManager em;
	
	public EngineerBudget find(final int id) {
		return em.find(EngineerBudget.class, id);
	}
	
	public void remove(final EngineerBudget budget) {
		EngineerBudget b = find(budget.getId());
		em.remove(b);
	}
	
	public void merge(final EngineerBudget budget) {
		em.merge(budget);
	}
	
	public void persist(final EngineerBudget newBudget) {
		em.persist(newBudget);
	}
	
	public int getTotalBudget(final int wpId) {
		Query query = em.createNativeQuery("select * "
				+ "from engineerbudget "
				+ "where engBudgetID = :wpId", EngineerBudget.class)
				.setParameter("wpId", wpId);
		try {
			EngineerBudget budget = (EngineerBudget) query.getSingleResult();
		    int totalBudget = budget.getDS() + budget.getJS() + budget.getSS() + budget.getP1() + budget.getP2()
		    		+ budget.getP3() + budget.getP4() + budget.getP5() + budget.getP6();
			return totalBudget;
		} catch (NoResultException e) {
			return 0;
		}

	}
	
	public EngineerBudget getBudget(final int wpId) {
		Query query = em.createNativeQuery("select * "
				+ "from engineerbudget "
				+ "where engBudgetID = :wpId", EngineerBudget.class)
				.setParameter("wpId", wpId);
		try{
			EngineerBudget budget = (EngineerBudget) query.getSingleResult();
			return budget;
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
