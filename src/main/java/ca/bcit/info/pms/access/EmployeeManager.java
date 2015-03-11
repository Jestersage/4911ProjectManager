package ca.bcit.info.pms.access;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ca.bcit.info.pms.model.Credential;
import ca.bcit.info.pms.model.Employee;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless
@LocalBean
public class EmployeeManager  implements Serializable {	
	@PersistenceContext
	private EntityManager entityManager;

    private static final Logger logger = LogManager.getLogger(EmployeeManager.class);

	public Employee findById(final String id) {

		return this.entityManager.find(Employee.class, id);
	}

    /**
     * Find employee by username
     * @param username
     * @return
     */
    public Employee findByUsername(final String username)
    {
        TypedQuery<Employee> query = entityManager.createQuery(
                "SELECT e FROM Employee e WHERE e.username = :username ",
                Employee.class
        );
        query.setParameter("username", username);

        try {
            Employee match = query.getSingleResult();
            logger.info("match found: " + match.getUsername());
            return match;
        } catch (NoResultException nre) {
            logger.warn("match not found for username " + username );
            return null;
        }
    }

    @SuppressWarnings("unchecked")
	public List<Credential> getCredentials() {

		return this.entityManager.createQuery("select c from Credential c",Credential.class ).getResultList();
	}

	/**
	 * Support updating and deleting Employee entities
	 * 
	 * @param employee     The employee to be updated
	 * @return             redirection string
	 */
	public String updateEmployee(Employee employee) {
		try {
			if (employee.getId() == null) {
				persistEmployee(employee);				 
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

	/**
	 * 
	 * @param  id      Primary Key of Employee to delete
	 * @return         redirection string
	 */
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

	/**
	 * Support listing and POSTing back Employee entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 * 
	 * @return List of all Employee objects
	 */
	public List<Employee> getAllEmployee() {
		CriteriaQuery<Employee> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Employee.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(Employee.class))).getResultList();
	}

	/**
	 * Support listing and POSTing back Credential entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 * 
	 * @return         List of all Credential objects
	 */
	public List<Credential> getAllCredentials() {

		CriteriaQuery<Credential> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Credential.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(Credential.class))).getResultList();
	}

	/**
	 * Create employee entry into database.
	 */
    public void persistEmployee(Employee employee) {
        Employee match = entityManager.find(Employee.class, employee.getId());

        if (match != null) {
            logger.warn("Record(Employee) already exist! Username: " + employee.getUsername());
        }
        else {
            entityManager.persist(employee);
            logger.info("Employee added: " + employee.getUsername());
        }
    }

    /**
     * Create credential entry into database.
     */
    public void persistCredential(Credential credential) {
        Credential match = entityManager.find(Credential.class, credential.getUsername());

        if (match != null) {
            logger.warn("Record(Credential) already exist! Username: " + credential.getUsername());
        } else {
            entityManager.persist(credential);
            logger.info("Employee added: " + credential.getUsername());
        }
    }

    public String updateCredential(Credential credential) {
		try {
			if (credential.getUsername() == null) {
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(credential);
				return "view?faces-redirect=true&id=" + credential.getUsername();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			return null;
		}
    }
    
}
