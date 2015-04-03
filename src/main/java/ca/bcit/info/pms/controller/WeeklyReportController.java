package ca.bcit.info.pms.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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

	private WorkPackage wp;

	private EngineerBudget engineerBudget;

	private StatusReport statusReport;

	Map< String, Double > cost;

	List< StatusReport > reports;

	private String id;

	private String name;

	private String packageNum;

	private Double p1;
	private Double p2;
	private Double p3;
	private Double p4;
	private Double p5;
	private Double p6;
	private Double ss;
	private Double js;
	private Double ds;

	public WeeklyReportController()
	{
		statusReport = new StatusReport();
	}

	public String save()
	{
		// WorkPackage w = workPackageMngr.find( id.intValue() );
		WorkPackage w = workPackageMngr.findByPackageNum( id );

		statusReport.setWorkPackage( w );
		statusReportMngr.persist( statusReport );

		return "mypage";
	}

	public String findWP()
	{
		int engBudgetNum;
		EngineerBudget engBudget = null;

		// get wp
		wp = new WorkPackage();
		wp = workPackageMngr.findByPackageNum( id );
		System.out.println( "WP = " + wp );

		// wp doesn't exist
		if ( wp == null )
		{
			id = null;
			FacesContext.getCurrentInstance().addMessage( null, new FacesMessage( "WP not found" ) );

			return "weekReport";
		}

		// get engineer budget of wp
		engBudgetNum = workPackageMngr.getEngBudgetID( wp.getId().intValue() );

		// there is a engineer budget
		if ( engBudgetNum != 0 )
		{
			engBudget = engineerBudgetMngr.find( engBudgetNum );

		} else
		{
			// error message
			FacesContext.getCurrentInstance().addMessage( null,
			        new FacesMessage( "Work package has no engineer budget estimated." ) );

		}

		// there are all values in the db
		if ( wp != null && engBudget != null )
		{
			setName( wp.getName() );
			System.out.println( "wp num" + wp.getPackageNum() );
			engineerBudget = new EngineerBudget();

			// set up values to display
			// allocated budget
			engineerBudget.setP1( engBudget.getP1() );
			engineerBudget.setP2( engBudget.getP2() );
			engineerBudget.setP3( engBudget.getP3() );
			engineerBudget.setP4( engBudget.getP4() );
			engineerBudget.setP5( engBudget.getP5() );
			engineerBudget.setP6( engBudget.getP6() );
			engineerBudget.setDS( engBudget.getDS() );
			engineerBudget.setJS( engBudget.getJS() );
			engineerBudget.setSS( engBudget.getSS() );

			// get remaining budget based on estimation made my the engineer
			cost = timeSheetRowMngr.getManHoursPerPayLevel( wp.getId().intValue() );
			System.out.println( "cost = " + cost.toString() );

			// calculate remaining budget
			// allocated - cost. Cost has the total number of hours. Divide by 8
			// to get the total number of man-days
			setP1( engBudget.getP1() - ( cost.get( "P1" ) / 8 ) );
			setP2( engBudget.getP2() - ( cost.get( "P2" ) / 8 ) );
			setP3( engBudget.getP3() - ( cost.get( "P3" ) / 8 ) );
			setP4( engBudget.getP4() - ( cost.get( "P4" ) / 8 ) );
			setP5( engBudget.getP5() - ( cost.get( "P5" ) / 8 ) );
			setP6( engBudget.getP6() - ( cost.get( "P6" ) / 8 ) );
			setDs( engBudget.getDS() - ( cost.get( "DS" ) / 8 ) );
			setJs( engBudget.getJS() - ( cost.get( "JS" ) / 8 ) );
			setSs( engBudget.getSS() - ( cost.get( "SS" ) / 8 ) );
		}

		return "weekReport";

	}

	// not working
	public String goGetReports( Integer i )
	{
		int ii = i.intValue();
		System.out.println( "ii= " + ii );
		reports = new ArrayList< StatusReport >();

		List< StatusReport > rep = statusReportMngr.getStatusReports( 95 );
		reports.toArray( rep.toArray() );
		System.out.println( "reports= " + reports );

		if ( reports == null )
		{
			return "viewWorkPackageDetails";
		}

		return "viewReports";
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

	public String getId()
	{
		return id;
	}

	public void setId( String id )
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

	public String getPackageNum()
	{
		return packageNum;
	}

	public void setPackageNum( String packageNum )
	{
		this.packageNum = packageNum;
	}

	public List< StatusReport > getReports()
	{
		return reports;
	}

	public void setReports( List< StatusReport > reports )
	{
		this.reports = reports;
	}

}