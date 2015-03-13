package ca.bcit.info.pms.access;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ca.bcit.info.pms.model.Budget;
import ca.bcit.info.pms.model.PayLevel;

@Dependent
@Stateless
public class PayGradeManager implements Serializable {
	@PersistenceContext(unitName = "pms-persistence-unit") private EntityManager em;
	
	public PayLevel find(final int id) {
		return em.find(PayLevel.class, id);
	}
	
	public void remove(final PayLevel payLevel) {
		PayLevel pl = find(payLevel.getId());
		em.remove(pl);
	}
	
	public void merge(final PayLevel payLevel) {
		em.merge(payLevel);
	}
	
	public void persist(final PayLevel payLevel) {
		em.persist(payLevel);
	}
	
	public double getCost(final String payLevelName) {
		Query query = em.createNativeQuery("select * "
				+ "from paygrade "
				+ "where name = :payName", PayLevel.class)
				.setParameter("payName", payLevelName);
	    PayLevel payLevel = (PayLevel) query.getSingleResult();
	    BigDecimal cost = payLevel.getCost();
		return cost.doubleValue();
	}
	
}
