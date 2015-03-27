package ca.bcit.info.pms.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.access.EngineerBudgetManager;
import ca.bcit.info.pms.access.StatusReportManager;
import ca.bcit.info.pms.access.WorkPackageManager;
import ca.bcit.info.pms.model.EngineerBudget;
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
	private EngineerBudgetManager engineerBudgetMngr;

	@Inject
	private EngineerBudget engineerBudget;

	@Inject
	private StatusReport statusReport;

	private int id;
	private int prev;
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

		if ( wp != null )
		{
			setName( wp.getName() );

			EngineerBudget budget = new EngineerBudget();
			int num = workPackageMngr.getEngBudgetID( id );
			budget = engineerBudgetMngr.find( num );
			engineerBudget.setP1( budget.getP1() );
			engineerBudget.setP2( budget.getP2() );
			engineerBudget.setP3( budget.getP3() );
			engineerBudget.setP4( budget.getP4() );
			engineerBudget.setP5( budget.getP5() );
			engineerBudget.setDS( budget.getDS() );
			engineerBudget.setJS( budget.getJS() );
			engineerBudget.setSS( budget.getSS() );
		} else
		{
			setName( "" );
		}
	}

	public EngineerBudget getBudgetForWP( int id )
	{
		EngineerBudget budget = engineerBudgetMngr.find( id );

		return budget;
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

	public EngineerBudgetManager getEngineerBudgetMngr()
	{
		return engineerBudgetMngr;
	}

	public void setEngineerBudgetMngr( EngineerBudgetManager engineerBudgetMngr )
	{
		this.engineerBudgetMngr = engineerBudgetMngr;
	}

	public EngineerBudget getEngineerBudget()
	{
		return engineerBudget;
	}

	public void setEngineerBudget( EngineerBudget engineerBudget )
	{
		this.engineerBudget = engineerBudget;

	}

}
