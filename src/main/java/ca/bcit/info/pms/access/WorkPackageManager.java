package ca.bcit.info.pms.access;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.model.WorkPackage;

@Dependent
@Stateless
public class WorkPackageManager implements Serializable {
	@PersistenceContext(unitName = "pms-persistence-unit") private EntityManager em;
	
	public WorkPackage find(final int id) {
		return em.find(WorkPackage.class, id);
	}
	
	public void remove(final WorkPackage workPackage) {
		WorkPackage wp = find(workPackage.getId());
		em.remove(wp);
	}
	
	public void merge(final WorkPackage workPackage) {
		em.merge(workPackage);
	}
	
	public void persist(final WorkPackage newWorkPackage) {
		em.persist(newWorkPackage);
	}
	
	public List<WorkPackage> getWorkPackages(final String projectId) {
		Query query = em.createNativeQuery("select * "
				+ "from workpackage "
				+ "where projectId LIKE :projectId", WorkPackage.class)
				.setParameter("projectId", projectId);
		List<WorkPackage> workPackages = query.getResultList();
		return workPackages;
	}
	
}
