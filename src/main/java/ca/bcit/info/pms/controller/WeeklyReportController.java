package ca.bcit.info.pms.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.access.StatusReportManager;
import ca.bcit.info.pms.access.WorkPackageManager;
import ca.bcit.info.pms.model.StatusReport;
import ca.bcit.info.pms.model.WorkPackage;

/**
 * 
 * Persists data used for the weekly report. Data is entered from the
 * weekReport.xhtml page.
 *
 */
@Named( "weekReport" )
@RequestScoped
public class WeeklyReportController implements Serializable
{

	@Inject
	private WorkPackageManager workPackageMngr;

	@Inject
	private StatusReportManager statusReportMngr;

	@Inject
	private WorkPackage wp;

	@Inject
	private StatusReport statusReport;

	private int id;
	private String name;

	public WeeklyReportController()
	{

	}

	public String save()
	{
		statusReport.setId( wp.getId() );
		statusReport.setWorkPackage( wp );
		statusReportMngr.persist( statusReport );

		return "mypage";
	}

	public void findWP()
	{
		wp = workPackageMngr.find( id );
		setName( wp.getName() );
	}

	public WorkPackage getWp()
	{
		return wp;
	}

	public void setWp( WorkPackage wp )
	{
		this.wp = wp;
	}

	public StatusReport getStatusReport()
	{
		return statusReport;
	}

	public void setStatusReport( StatusReport statusReport )
	{
		this.statusReport = statusReport;
	}

	public int getId()
	{
		return id;
	}

	public void setId( int id )
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

}
