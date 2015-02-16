package ca.bcit.info.pms.access;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ca.bcit.info.pms.model.Credential;
import ca.bcit.info.pms.model.Employee;

@Stateless
@LocalBean
public class EmployeeManager  implements Serializable {	
	@PersistenceContext
	private EntityManager entityManager;

	public Employee findById(final String id) {

		return this.entityManager.find(Employee.class, id);
	}

	
	@SuppressWarnings("unchecked")
	public List<Credential> getCredentials() {

		return this.entityManager.createQuery("select c from Credential c",Credential.class ).getResultList();
	}
	/*
	 * Support updating and deleting Employee entities
	 */

	public String updateEmployee(Employee employee) {
		try {
			if (employee.getId() == null) {
				this.entityManager.persist(employee);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(employee);
				return "view?faces-redirect=true&id=" + employee.getId();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			return null;
		}
	}

	public String delete(final String id) {

		try {
			Employee deletableEntity = findById(id);

			this.entityManager.remove(deletableEntity);
			this.entityManager.flush();
			return "search?faces-redirect=true";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			return null;
		}
	}

	/*
	 * Support searching Employee entities with pagination
	 */

	private int page;
	private long count;
	private List<Employee> pageItems;

	private Employee example = new Employee();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public Employee getExample() {
		return this.example;
	}

	public void setExample(Employee example) {
		this.example = example;
	}

	public String search() {
		this.page = 0;
		return null;
	}

	public void paginate() {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		// Populate this.count

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<Employee> root = countCriteria.from(Employee.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
		root = criteria.from(Employee.class);
		TypedQuery<Employee> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<Employee> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<Employee> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back Employee entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<Employee> getAllEmployee() {

		CriteriaQuery<Employee> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Employee.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(Employee.class))).getResultList();
	}

	/*
	 * Support listing and POSTing back Credential entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<Credential> getAllCredentials() {

		CriteriaQuery<Credential> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Credential.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(Credential.class))).getResultList();
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

//	private Employee add = new Employee();
//
//	public Employee getAdd() {
//		return this.add;
//	}
//
//	public Employee getAdded() {
//		Employee added = this.add;
//		this.add = new Employee();
//		return added;
//	}
	
    public void persistEmployee(Employee employee) {
    	boolean exist = false;
		List<Employee> allEmployees = null;
		allEmployees = this.getAllEmployee();
		for(Employee oldRecord : allEmployees){
			if(oldRecord.getId().equalsIgnoreCase(employee.getId())) {
				exist = true;
				System.out.println("Record(Employee) already exist!");
			}
		}
		if(!exist)
			entityManager.persist(employee);	
    }
    
    public void persistCredential(Credential credential) {
    	boolean exist = false;
		List<Credential> allCredentials = null;
		allCredentials = this.getAllCredentials();
		for(Credential oldRecord : allCredentials){
			if(oldRecord.getUsername().equals(credential.getUsername())) {
				exist = true;
				System.out.println("Record(Credential) already exist!");
			}
		}
		if(!exist)
			entityManager.persist(credential);
    }
}