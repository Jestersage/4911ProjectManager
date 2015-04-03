package ca.bcit.info.pms.access;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.WorkPackage;

@Dependent
@Stateless
public class WorkPackageManager implements Serializable
{

	private static final Logger logger = LogManager.getLogger( WorkPackageManager.class );

	@PersistenceContext( unitName = "pms-persistence-unit" )
	private EntityManager em;

	public WorkPackage find( final Integer id )
	{
		if ( id != null )
			return em.find( WorkPackage.class, id );
		else
			return null;
	}

	public void remove( final WorkPackage workPackage )
	{
		WorkPackage wp = find( workPackage.getId() );
		em.remove( wp );
	}

	public void merge( final WorkPackage workPackage )
	{
		em.merge( workPackage );
	}

	public void persist( final WorkPackage newWorkPackage )
	{
		WorkPackage match = em.find( WorkPackage.class, newWorkPackage.getId() );

		if ( match != null )
		{
			logger.warn( "Record(WorkPackage) already exist! " );
		} else
		{
			if ( !newWorkPackage.isLeaf() )
				newWorkPackage.setBudget( null );
			em.persist( newWorkPackage );
			logger.info( "Workpackage added: " + newWorkPackage.getId() + ", " + newWorkPackage.getName() );
		}

	}

	public List< WorkPackage > getWorkPackages( final String projectId )
	{
		Query query = em.createNativeQuery(
		        "select * " + "from workpackage " + "where projectId LIKE :projectId", WorkPackage.class )
		        .setParameter( "projectId", projectId );
		List< WorkPackage > workPackages = query.getResultList();
		return workPackages;
	}

	public WorkPackage getUniqueWP( final String projectId, final String packageId )
	{
		Query query = em
		        .createNativeQuery( "select * from workpackage where projectID = ? and packageNum = ?",
		                WorkPackage.class ).setParameter( 1, projectId ).setParameter( 2, packageId );
		WorkPackage workPackage = null;
		try
		{
			workPackage = ( WorkPackage ) query.getSingleResult();
		} catch ( NoResultException nre )
		{
			// Code for handling NoResultException
		}
		return workPackage;
	}

	/*
	 * updates a WorkPackage in the database
	 */
	public String updateWorkPackage( WorkPackage workPackage )
	{
		try
		{
			if ( workPackage.getId() == null )
			{
				persist( workPackage );
				return "search?faces-redirect=true";
			} else
			{
				this.em.merge( workPackage );
				return "view?faces-redirect=true&id=" + workPackage.getId();
			}
		} catch ( Exception e )
		{
			FacesContext.getCurrentInstance().addMessage( null, new FacesMessage( e.getMessage() ) );
			return null;
		}
	}

	/*
	 * gets all the packages from the database
	 */
	public List< WorkPackage > getAllWorkPackages()
	{
		CriteriaQuery< WorkPackage > criteria = this.em.getCriteriaBuilder().createQuery( WorkPackage.class );
		return this.em.createQuery( criteria.select( criteria.from( WorkPackage.class ) ) ).getResultList();
	}

	/**
	 * @param empId
	 *            responsible engineer's employee id.
	 * @return a list of all work package managed by this employee disregarding
	 *         which project.
	 */
	// TODO fix to engineer.id when employeeID change to manager in WorkPackage
	// public List< WorkPackage > getManagedWorkPackage( final String empId )
	// {
	// TypedQuery< WorkPackage > query =
	// em.createQuery("SELECT w FROM WorkPackage w "
	// + "WHERE w.employeeID = :assistantId", WorkPackage.class);
	// query.setParameter( "assistantId", empId );
	// return query.getResultList();
	// }

	public int findNumOfChildWP( String projId )
	{
		Query query = em.createNativeQuery( "select COUNT(*) from WorkPackage  where projectID = :projectId" );
		query.setParameter( "projectId", projId );
		int workPackages = ( ( Number ) query.getSingleResult() ).intValue();
		return workPackages;
	}

	public int getEngBudgetID( int id )
	{
		Query query = em
		        .createNativeQuery( "SELECT engBudgetID from WorkPackage WHERE packageID = :packageID" );
		query.setParameter( "packageID", id );

		try
		{

			int num = ( int ) query.getSingleResult();
			return num;

		} catch ( Exception e )
		{

			return 0;
		}

	}

	/**
	 * @param empId
	 *            id of employees assigned to work package
	 * @return a list of all work packages an employee is assigned to.
	 */
	public List< WorkPackage > getWorkPackagesByAssignee( final String empId )
	{
		TypedQuery< WorkPackage > query = em.createQuery( "SELECT wp FROM WorkPackage wp "
		        + "WHERE wp.employee.id = :empId", WorkPackage.class );
		query.setParameter( "empId", empId );

		return query.getResultList();
	}

	/**
	 * @param parentWp
	 *            parent work package
	 * @return a list of immediate child work packages with specified project
	 *         and parent work package.
	 */
	public List< WorkPackage > getChildWorkPackages( final WorkPackage parentWp )
	{
		TypedQuery< WorkPackage > query = em.createQuery( "SELECT wp FROM WorkPackage wp "
		        + "WHERE wp.project.id = :projId " + "AND  wp.parentWP.id = :parentId", WorkPackage.class );
		query.setParameter( "projId", parentWp.getProject().getId() );
		query.setParameter( "parentId", parentWp.getId() );

		return query.getResultList();
	}

	/**
	 * @param projId
	 *            project id
	 * @return a list of top level work packages for specified project.
	 */
	public List< WorkPackage > getTopLevelWorkPackages( final String projId )
	{
		TypedQuery< WorkPackage > query = em.createQuery( "SELECT wp FROM WorkPackage wp "
		        + "WHERE wp.project.id = :projId " + "AND  wp.parentWP.id IS NULL", WorkPackage.class );
		query.setParameter( "projId", projId );

		return query.getResultList();
	}

	public List< Employee > getEmployeesAssignedToWp( final String wpId )
	{
		Query query = em.createNativeQuery( "select e.* " + "from employee e "
		        + "join workassignment wa on e.employeeID = wa.employeeID " + "where packageID = :wpId",
		        Employee.class );
		query.setParameter( "wpId", wpId );

		return query.getResultList();
	}

	public List< Employee > getEmployeesAssignedToProjectNotInWp( final String wpId, final String projId )
	{
		Query query = em.createNativeQuery( "select distinct e.* " + "from employee e "
		        + "join workassignment wa on e.employeeID = wa.employeeID "
		        + "join projectassignment pa on e.employeeID = pa.employeeID "
		        + "where wa.packageID <> :wpId " + "and pa.projectID = :projId", Employee.class );
		query.setParameter( "wpId", wpId );
		query.setParameter( "projId", projId );

		return query.getResultList();
	}

	public WorkPackage findByPackageNum( final String wpNum )
	{
		Query query = em.createNativeQuery( "select * from workpackage where packageNum = ?",
		        WorkPackage.class ).setParameter( 1, wpNum );
		WorkPackage workPackage = null;
		try
		{
			workPackage = ( WorkPackage ) query.getSingleResult();
		} catch ( NoResultException nre )
		{
			// Code for handling NoResultException
		}
		return workPackage;
	}
}
