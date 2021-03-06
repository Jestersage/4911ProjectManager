package ca.bcit.info.pms.access;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ca.bcit.info.pms.model.StatusReport;

@Dependent
@Stateless
public class StatusReportManager implements Serializable
{
	@PersistenceContext( unitName = "pms-persistence-unit" )
	private EntityManager em;

	public StatusReport find( final long l )
	{
		return em.find( StatusReport.class, l );
	}

	public void remove( final StatusReport statusReport )
	{
		StatusReport sr = find( statusReport.getId() );
		em.remove( sr );
	}

	public void merge( final StatusReport statusReport )
	{
		em.merge( statusReport );
	}

	public void persist( final StatusReport newStatusReport )
	{
		em.persist( newStatusReport );
	}

	public int getTotalCompletionEstimate( final int wpId )
	{
		Query query = em.createNativeQuery( "select * " + "from statusreport " + "where packageID = :wpId",
		        StatusReport.class ).setParameter( "wpId", wpId );
		try
		{
			StatusReport statusReport = ( StatusReport ) query.getSingleResult();
			int totalBudget = statusReport.getDS() + statusReport.getJS() + statusReport.getSS()
			        + statusReport.getP1() + statusReport.getP2() + statusReport.getP3()
			        + statusReport.getP4() + statusReport.getP5() + statusReport.getP6();
			return totalBudget;
		} catch ( NoResultException e )
		{
			return 0;
		}
	}

	public StatusReport getStatusReport( final int wpId )
	{
		Query query = em.createNativeQuery( "select * " + "from statusreport " + "where packageID = :wpId",
		        StatusReport.class ).setParameter( "wpId", wpId );

		try
		{
			StatusReport statusReport = ( StatusReport ) query.getSingleResult();
			return statusReport;
		} catch ( NoResultException e )
		{
			return null;
		}
	}

	public List< StatusReport > getStatusReports( final int wpId )
	{

		Query query = em.createNativeQuery( "select * " + "from statusReport " + "where packageID = :Id",
		        StatusReport.class ).setParameter( "Id", wpId );

		try
		{
			List< StatusReport > statusReport = query.getResultList();
			return statusReport;
		} catch ( NoResultException e )
		{
			return null;
		}
	}

}
