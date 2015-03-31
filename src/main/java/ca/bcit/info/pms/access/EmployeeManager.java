package ca.bcit.info.pms.access;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import ca.bcit.info.pms.model.Credential;
import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.Hr;
import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.model.WorkPackage;
import ca.bcit.info.pms.service.PasswordHash;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


@Stateless
@LocalBean
public class EmployeeManager implements Serializable
{
	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LogManager.getLogger( EmployeeManager.class );

	/**
	 * Find employee by Id.
	 *
	 * @param id
	 *            employee id.
	 * @return Employee
	 */
	public Employee findById( final String id )
	{

		return this.entityManager.find( Employee.class, id );
	}

	/**
	 * Find employee by username
	 *
	 * @param username
	 *            employee username
	 * @return Employee
	 */
	public Employee findByUsername( final String username )
	{
		TypedQuery< Employee > query = entityManager.createQuery(
		        "SELECT e FROM Employee e WHERE e.credential.username = :username ",
		        Employee.class );
		query.setParameter( "username", username );

		try
		{
			Employee match = query.getSingleResult();
			logger.info( "match found: " + match.getCredential().getUsername() );
			return match;
		} catch ( NoResultException nre )
		{
			logger.warn( "match not found for username " + username );
			return null;
		}
	}

	/**
	 * Support updating Employee entities
	 *
	 * @param employee
	 *            The employee to be updated
	 * @return updated employee.
	 */
	public Employee updateEmployee( Employee employee )
	{
		try
		{
			this.entityManager.merge( employee );
			return employee;
		} catch ( Exception e )
		{
			logger.warn( "Update employee failed. Error: " );
            logger.warn(e.getMessage());
			return null;
		}
	}

	/**
	 * Create employee entry into database.
	 */
	public void persistEmployee( Employee employee )
	{
		Employee match = entityManager.find( Employee.class, employee.getId() );

		if ( match != null )
		{
			// TODO throw exception
			logger.warn( "Record(Employee) already exist! Username: "
			        + employee.getCredential().getUsername() );
		} else
		{
			try
			{
			    // Hash the password
			    employee.getCredential().setPassword(PasswordHash.createHash(employee.getCredential().getPassword()));

				entityManager.persist( employee );
				logger.info( "Employee added: " + employee.getCredential().getUsername() );

			} catch ( Exception e )
			{
				logger.warn( "Save employee failed." );
			}
		}
	}

	/**
	 * @param id
	 *            Primary Key of Employee to deleteEmployee
	 * @return boolean delete success.
	 */
	public boolean deleteEmployee( final String id )
	{

		try
		{
			Employee deletableEntity = findById( id );

			this.entityManager.remove( deletableEntity );
			this.entityManager.flush();
			return true;
		} catch ( Exception e )
		{
			FacesContext.getCurrentInstance().addMessage( null,
			        new FacesMessage( e.getMessage() ) );
			return false;
		}
	}

    /**
   * @param id
   *            Primary Key of Employee to disableEmployee
   * @return boolean Active success.
   */
  public boolean enableEmployee( final String id )
  {

      try
      {
          Employee activeEntity = findById( id );
          activeEntity.setActiveStatus(true);

          return true;
      } catch ( Exception e )
      {
          FacesContext.getCurrentInstance().addMessage( null,
                  new FacesMessage( e.getMessage() ) );
          return false;
      }
  }

	   /**
     * @param id
     *            Primary Key of Employee to disableEmployee
     * @return boolean Active success.
     */
    public boolean disableEmployee( final String id )
    {

        try
        {
            Employee activeEntity = findById( id );
            activeEntity.setActiveStatus(false);

            return true;
        } catch ( Exception e )
        {
            FacesContext.getCurrentInstance().addMessage( null,
                    new FacesMessage( e.getMessage() ) );
            return false;
        }
    }

	/**
	 * Support listing and POSTing back Employee entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 *
	 * @return List of all Employee objects
	 */
	public List< Employee > getAllEmployee()
	{
		CriteriaQuery< Employee > criteria = this.entityManager.getCriteriaBuilder()
		        .createQuery( Employee.class );
		return this.entityManager.createQuery(
		        criteria.select( criteria.from( Employee.class ) ) ).getResultList();
	}

	@SuppressWarnings( "unchecked" )
	public List< Credential > getCredentials()
	{
	  //select c.username from credentials c, employee e where c.username = e.username and e.active=true;
		TypedQuery<Credential> query = entityManager
				.createQuery("SELECT c FROM Credential c, Employee e " +
						"WHERE c.username = e.credential.username " +
						"AND e.activeStatus = true ", Credential.class);

		return query.getResultList();
	}

	/**
	 * Support listing and POSTing back Credential entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 *
	 * @return List of all Credential objects
	 */
	public List< Credential > getAllCredentials()
	{

		CriteriaQuery< Credential > criteria = this.entityManager.getCriteriaBuilder()
		        .createQuery( Credential.class );
		return this.entityManager.createQuery(
		        criteria.select( criteria.from( Credential.class ) ) ).getResultList();
	}

	/**
	 * Create credential entry into database.
	 */
	public boolean persistCredential( Credential credential )
	{
		Credential match = entityManager
		        .find( Credential.class, credential.getUsername() );

		if ( match != null )
		{
			logger.warn( "Record(Credential) already exist! Username: "
			        + credential.getUsername() );
			return false;
		} else
		{
			entityManager.persist( credential );
			logger.info( "Employee added: " + credential.getUsername() );
			return true;
		}
	}

	/**
	 * Update credential.
	 *
	 * @param credential
	 *            entity to save.
	 * @return updated credential.
	 */
	public Credential updateCredential( Credential credential )
	{
		try
		{
			if ( credential == null )
			{
				return null;
			} else
			{
			    // If the password is not hashed
			    if(credential.getPassword().length() != 102 ) {
			        // Hash the password
			        credential.setPassword(PasswordHash.createHash(credential.getPassword()));
			    }
				return this.entityManager.merge( credential );
			}
		} catch ( Exception e )
		{
			FacesContext.getCurrentInstance().addMessage( null,
			        new FacesMessage( e.getMessage() ) );
			return null;
		}
    }

    /**
     * @param id employee ID
     * @return if employee has HR role
     */
    public boolean hasHrRole(final String id) {
        boolean userInHrTable = false;

        TypedQuery<Hr> query = entityManager
                .createQuery("SELECT e FROM Hr e " +
                        "WHERE e.id = :empId", Hr.class);
        query.setParameter("empId", id);

        try {
            final Hr hr = query.getSingleResult();
            userInHrTable = (hr != null);
        } catch (NoResultException nre) {
            // do nothing.
        }

        return userInHrTable;
    }

    /**
     * @param id supervisor's employee id.
     * @return if this employee is a supervisor.
     */
    public boolean hasSupervisorRole(final String id) {
        List<Employee> managedEmp = getManagedEmployees(id);

        return (managedEmp.size() != 0);
    }

    /**
     * @param id supervisor's employee id.
     * @return a list of employees managed by the specified supervisor.
     */
    public List<Employee> getManagedEmployees(final String id) {
        TypedQuery<Employee> query = entityManager
                .createQuery("SELECT e FROM Employee  e " +
                        "WHERE e.supervisor.id = :supId", Employee.class);
        query.setParameter("supId", id);
        return query.getResultList();
    }

    /**
     * @param id supervisor's employee id.
     * @return if this employee is a timesheet approver.
     */
    public boolean hasTsApproverRole(final String id) {
        TypedQuery<Employee> query = entityManager
                .createQuery("SELECT e FROM Employee e " +
                        "WHERE e.timesheetApprover.id = :approverId", Employee.class);
        query.setParameter("approverId", id);

        List<Employee> toApprove = query.getResultList();
        boolean isApprover = toApprove.size() != 0;

        logger.info("Employee with id " + id + ", has " + toApprove.size() + " emp to approve");
        return isApprover;
    }

    /**
     * @param id project manager's employee id.
     * @return if this employee is project manager.
     */
    public boolean hasProjectManagerRole(final String id) {
        final TypedQuery<Project> query = entityManager
                .createQuery("SELECT p FROM Project p " +
                        "WHERE p.projectManager.id = :managerId", Project.class);
        query.setParameter("managerId", id);

        final int numOfManagedProjects = query.getResultList().size();
        return (numOfManagedProjects != 0);
    }

    /**
     * @param id project assistant's employee id.
     * @return if this employee is an assistant to a project manager.
     */
    public boolean hasAssistantRole(final String id) {
        final TypedQuery<Project> query = entityManager
                .createQuery("SELECT p FROM Project p " +
                        "WHERE p.assistant.id = :assistantId", Project.class);
        query.setParameter("assistantId", id);

        final int numOfManagedProjects = query.getResultList().size();
        return (numOfManagedProjects != 0);
    }

    /**
     * @param id responsible engineer's employee id.
     * @return if this employee is an responsible engineer to a work package.
     */
    public boolean hasWpManagerRole(final String id) {
        final TypedQuery<WorkPackage> query = entityManager
                .createQuery("SELECT w FROM WorkPackage w " +
                        "WHERE w.employeeID = :assistantId", WorkPackage.class);
        query.setParameter("assistantId", id);

        final int numOfWPs = query.getResultList().size();
        return (numOfWPs != 0);
    }
}
