package ca.bcit.info.pms.controller;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.access.EngineerBudgetManager;
import ca.bcit.info.pms.access.StatusReportManager;
import ca.bcit.info.pms.access.TimesheetRowManager;
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
	private EngineerBudgetManager engineerBudgetMngr;

	@Inject
	private TimesheetRowManager timeSheetRowMngr;

	@Inject
	private WorkPackage wp;

	@Inject
	private EngineerBudget engineerBudget;

	@Inject
	private StatusReport statusReport;

	Map< String, Double > cost;

	private Double p1;
	private Double p2;
	private Double p3;
	private Double p4;
	private Double p5;
	private Double p6;
	private Double ss;
	private Double js;
	private Double ds;

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

			cost = timeSheetRowMngr.getManHoursPerPayLevel( id );

			setP1( cost.get( "P1" ).doubleValue() );
			setP2( cost.get( "P2" ).doubleValue() );
			setP3( cost.get( "P3" ).doubleValue() );
			setP4( cost.get( "P4" ).doubleValue() );
			setP5( cost.get( "P5" ).doubleValue() );
			setP6( cost.get( "P6" ).doubleValue() );
			setSs( cost.get( "SS" ).doubleValue() );
			setJs( cost.get( "JS" ).doubleValue() );
			setDs( cost.get( "DS" ).doubleValue() );
			System.out.println( "cost = " + cost.toString() );

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

	public Double getP1()
	{
		return p1;
	}

	public void setP1( Double p1 )
	{
		this.p1 = p1;
	}

	public Double getP2()
	{
		return p2;
	}

	public void setP2( Double p2 )
	{
		this.p2 = p2;
	}

	public Double getP3()
	{
		return p3;
	}

	public void setP3( Double p3 )
	{
		this.p3 = p3;
	}

	public Double getP4()
	{
		return p4;
	}

	public void setP4( Double p4 )
	{
		this.p4 = p4;
	}

	public Double getP5()
	{
		return p5;
	}

	public void setP5( Double p5 )
	{
		this.p5 = p5;
	}

	public Double getP6()
	{
		return p6;
	}

	public void setP6( Double p6 )
	{
		this.p6 = p6;
	}

	public Double getSs()
	{
		return ss;
	}

	public void setSs( Double ss )
	{
		this.ss = ss;
	}

	public Double getJs()
	{
		return js;
	}

	public void setJs( Double js )
	{
		this.js = js;
	}

	public Double getDs()
	{
		return ds;
	}

	public void setDs( Double ds )
	{
		this.ds = ds;
	}

}