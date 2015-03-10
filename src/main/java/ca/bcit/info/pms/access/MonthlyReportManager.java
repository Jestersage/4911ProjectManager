package ca.bcit.info.pms.access;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ca.bcit.info.pms.model.Project;

@Dependent
@Stateless
public class MonthlyReportManager implements Serializable {
	@PersistenceContext(unitName = "pms-persistence-unit") private EntityManager em;
	
	public Project find(final String id) {
		return em.find(Project.class, id);
	}
	
	public void remove(final Project project) {
		Project proj = find(project.getId());
		em.remove(project);
	}
	
	public void merge(final Project project) {
		em.merge(project);
	}
	
	public void persist(final Project newProject) {
		em.persist(newProject);
	}
	
	public List<Project> getProjects() {
		TypedQuery<Project> query = em.createQuery("select p "
				+ "from Project p", Project.class);
		List<Project> projects = query.getResultList();
		return projects;
	}
}
