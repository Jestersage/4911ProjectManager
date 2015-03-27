package ca.bcit.info.pms.access;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.bcit.info.pms.model.WorkPackage;

@Dependent
@Stateless
public class WorkPackageManager implements Serializable
{

	private static final Logger logger = LogManager.getLogger( WorkPackageManager.class );

	@PersistenceContext( unitName = "pms-persistence-unit" )
	private EntityManager em;

	public WorkPackage find( final int id )
	{
		return em.find( WorkPackage.class, id );
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
	public List< WorkPackage > getManagedWorkPackage( final String empId )
	{
		TypedQuery< WorkPackage > query = em.createQuery( "SELECT w FROM WorkPackage w "
		        + "WHERE w.employeeID = :assistantId", WorkPackage.class );
		query.setParameter( "assistantId", empId );
		return query.getResultList();
	}

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

		int num = ( int ) query.getSingleResult();
		return num;
	}
}
