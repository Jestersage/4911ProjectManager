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

    /**
     * Find employee by Id.
     * @param id employee id.
     * @return Employee
     */
	public Employee findById(final String id) {

		return this.entityManager.find(Employee.class, id);
	}

    /**
     * Find employee by username
     * @param username employee username
     * @return Employee
     */
    public Employee findByUsername(final String username)
    {
        TypedQuery<Employee> query = entityManager.createQuery(
                "SELECT e FROM Employee e WHERE e.credential.username = :username ",
                Employee.class
        );
        query.setParameter("username", username);

        try {
            Employee match = query.getSingleResult();
            logger.info("match found: " + match.getCredential().getUsername());
            return match;
        } catch (NoResultException nre) {
            logger.warn("match not found for username " + username );
            return null;
        }
    }

	/**
	 * Support updating Employee entities
	 * 
	 * @param employee     The employee to be updated
	 * @return             updated employee.
	 */
	public Employee updateEmployee(Employee employee) {
		try {
            this.entityManager.merge(employee);
            return employee;
		} catch (Exception e) {
            logger.warn("Update employee failed.");
			return null;
		}
	}

    /**
     * Create employee entry into database.
     */
    public void persistEmployee(Employee employee) {
        Employee match = entityManager.find(Employee.class, employee.getId());

        if (match != null) {
            // TODO throw exception
            logger.warn("Record(Employee) already exist! Username: " + employee.getCredential().getUsername());
        }
        else {
            try {
                entityManager.persist(employee);
                logger.info("Employee added: " + employee.getCredential().getUsername());

            } catch (Exception e) {
                logger.warn("Save employee failed.");
            }
        }
    }

    /**
	 * @param  id      Primary Key of Employee to deleteEmployee
	 * @return         boolean delete success.
	 */
	public boolean deleteEmployee(final String id) {

		try {
			Employee deletableEntity = findById(id);

			this.entityManager.remove(deletableEntity);
			this.entityManager.flush();
			return true;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			return false;
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


    @SuppressWarnings("unchecked")
    public List<Credential> getCredentials() {

        return this.entityManager.createQuery("select c from Credential c",Credential.class ).getResultList();
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
     * Create credential entry into database.
     */
    public boolean persistCredential(Credential credential) {
        Credential match = entityManager.find(Credential.class, credential.getUsername());

        if (match != null) {
            logger.warn("Record(Credential) already exist! Username: " + credential.getUsername());
            return false;
        } else {
            entityManager.persist(credential);
            logger.info("Employee added: " + credential.getUsername());
            return true;
        }
    }

    /**
     * Update credential.
     * @param credential entity to save.
     * @return updated credential.
     */
    public Credential updateCredential(Credential credential) {
		try {
			if (credential == null) {
				return null;
			} else {
				return this.entityManager.merge(credential);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			return null;
		}
    }
    
}
