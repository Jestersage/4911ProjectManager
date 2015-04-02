/**
 * 
 */
package ca.bcit.info.pms.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.bcit.info.pms.access.EngineerBudgetManager;
import ca.bcit.info.pms.access.WorkPackageManager;
import ca.bcit.info.pms.model.EngineerBudget;
import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.model.WorkPackage;
import ca.bcit.info.pms.service.ProjectService;
import ca.bcit.info.pms.service.WorkPackageService;

/**
 * 
 *
 */

@Named( "workPackageController" )
@RequestScoped
public class WorkPackageController implements Serializable
{

	@Inject
	private WorkPackage workPackage;

	@Inject
	private WorkPackageService workPackageService;

	@Inject
	private ProjectService projectService;

//	@Inject
//	private EngineerBudget engBudget;
//
//	@Inject
//	private EngineerBudgetManager engineerBudgetMngr;


	private Project [] projectList;

	private Integer parentWPId;

	private static final Logger logger = LogManager.getLogger( WorkPackageController.class );

	@Inject
	private UserController userController;


	public Project [] getProjectList()
	{
		projectList = new Project [ workPackageService.getAllProjects().size() ];
		projectList = workPackageService.getAllProjects().toArray( projectList );
		return projectList;

	}

	public String addWorkPackage()
	{
		WorkPackage parentWP = null;
		//logger.info( parentWPId );
		if ( parentWPId != null ){
			parentWP = workPackageService.findWorkPackageById( parentWPId );
			workPackage.setParentWP( parentWP );
		}
		workPackageService.persistWorkPackage( workPackage );
		// workPackageService.persistBudget(budget);
		logger.info( "successfully create new WorkPackage: " + workPackage.toString() );
		return "viewAllPackages";
	}

	public WorkPackage getworkPackage()
	{
		if (workPackage != null && workPackage.getParentWP() == null )
			workPackage.setParentWP( new WorkPackage() );
		return workPackage;
	}

	public void setworkPackage( WorkPackage workPackage )
	{
		this.workPackage = workPackage;
	}

	public List< WorkPackage > getWorkPackages()
	{
		return workPackageService.getAllWorkPackages();
	}

	public String goEditPackage(String id)
	{
		workPackage = workPackageService.findWorkPackageById(Integer.valueOf(id));

		// get engineer budget and assign to property
//		if(workPackage != null){
//			int engBudgetID = workPackageService.getEngBudgetID( workPackage.getId() );
//			engBudget = engineerBudgetMngr.find( engBudgetID );
//		}
		if(workPackage!=null && workPackage.getParentWP() != null)
			parentWPId = workPackage.getParentWP().getId();
		return "editWorkPackage";
	}

	public String goNewTopLevelPackage( Project project )
	{
		workPackage.setProject( project );
		return "newWorkPackage";
	}

	public String goChildPackage( WorkPackage parentWorkPackage )
	{
		Project project = projectService.getProject( parentWorkPackage.getProject().getId() );
		workPackage.setProject( project );
		parentWPId = parentWorkPackage.getId();
		return "newWorkPackage";
	}

	/**
	 * @return a list of all work packages an employee is assigned to.
	 */
	public List< WorkPackage > getMyWorkPackages()
	{
		final String userId = userController.getUser().getId();
		return workPackageService.findAssignedWorkPackages( userId );
	}

	public Integer getParentWPId()
	{
		return parentWPId;
	}

	public void setParentWPId( Integer parentWPId )
	{
		this.parentWPId = parentWPId;
	}

	public void setBooleanLeaf( AjaxBehaviorEvent event )
	{
		workPackage.setLeaf( workPackage.isLeaf() );
	}

	/**
	 * 
	 * @return
	 */
	public String viewWorkPackageDetails( final WorkPackage workPackage )
	{
		this.workPackage = workPackage;
		return "viewWorkPackageDetails";
	}

	public String goCreatePackage( final String projId )
	{
		final Project project = projectService.getProject( projId );
		workPackage.setProject( project );

		return "newWorkPackage";
	}
	
	
	public String updateWorkpackage(){		
		WorkPackage parentWP = null;
		//logger.info( parentWPId );
		if ( parentWPId != null ){
			parentWP = workPackageService.findWorkPackageById( parentWPId );
			workPackage.setParentWP( parentWP );
		}
		workPackageService.updateWorkPackage(workPackage);
		workPackage = workPackageService.findWorkPackageById(workPackage.getId());
		//engineerBudgetMngr.merge(engBudget);
		return "viewWorkPackageDetails";
	}

//	public EngineerBudget getEngBudget()
//	{
//		return engBudget;
//	}
//
//	public void setEngBudget( EngineerBudget engBudget )
//	{
//		this.engBudget = engBudget;
//	}

}
