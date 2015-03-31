package ca.bcit.info.pms.access;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ca.bcit.info.pms.model.SignatureObject;

@Dependent
@Stateless
public class SignatureManager {
	@PersistenceContext(unitName="pms-persistence-unit") private EntityManager em;
	
	public SignatureObject find(final int id) {
		return em.find(SignatureObject.class, id);
	}
	
	public void persist(final SignatureObject newSignature) {
		em.persist(newSignature);
	}
}
