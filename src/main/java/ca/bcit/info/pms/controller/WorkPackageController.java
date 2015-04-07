/**
 * 
 */
package ca.bcit.info.pms.controller;

import ca.bcit.info.pms.model.Budget;
import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.EngineerBudget;
import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.model.ProjectStatus;
import ca.bcit.info.pms.model.WorkPackage;
import ca.bcit.info.pms.service.EmployeeService;
import ca.bcit.info.pms.service.ProjectService;
import ca.bcit.info.pms.service.WorkPackageService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
import java.util.List;

/**
 * 
 *
 */

@Named( "workPackageController" )
@ConversationScoped
public class WorkPackageController implements Serializable
{

	@Inject
	private WorkPackage workPackage;

	@Inject
	private WorkPackageService workPackageService;

	@Inject
	private ProjectService projectService;

	@Inject
	private EmployeeService employeeService;
	
	@Inject
	private UserController userController;

	@Inject
	private Conversation conversation;
	
	private Employee employee;

	private String reId;

	private Integer parentWPId;
	

	private static final Logger logger = LogManager.getLogger( WorkPackageController.class );


	private void beginConversation() {
		logger.info("attempt start wp conversation...");
		if (conversation.isTransient()) {
			logger.info("Work Package, begin conversation.");
			conversation.begin();
		}
	}

	private void endConversation() {
		if(!conversation.isTransient()){
			logger.info("Work Package, end conversation.");
			conversation.end();
		}
	}

	

	public  ProjectStatus[] getStatuses()
	{			
		return ProjectStatus.values();
	}

	public String addWorkPackage()
	{
		logger.info("Entered add work package");
		if(workPackage == null) 
			return null;
		
		logger.info("Work Package is not null");

		WorkPackage workPackages = workPackageService.getUniquePackage(workPackage.getProject().getId(), workPackage.getPackageNum());
    	if(workPackages != null){
    		FacesContext.getCurrentInstance().addMessage( "newWorkPackageForm:itWorkPackageNumber",
                    new FacesMessage( FacesMessage.SEVERITY_ERROR, "", "Work Package is not unique with combinition of Project ID and Package Number!: "
                            + workPackage.getPackageNum() ) );
    		return null;
    	}
		
    	Employee employee = employeeService.findEmployeeById(reId);
    	if(employee == null){
    		FacesContext.getCurrentInstance().addMessage( "newWorkPackageForm:mnSupervisor",
                    new FacesMessage( FacesMessage.SEVERITY_ERROR, "", "Employee does not exist!: "
                            + reId ) );
    		return null;
    	}
    	
		WorkPackage parentWP = null;
		
		if ( parentWPId != null ) {
			parentWP = workPackageService.findWorkPackageById(parentWPId);
		}
		
		logger.info("Attempting to save workpackage");
		
		workPackage.setParentWP(parentWP);
		workPackage.setEmployee(employee);
		workPackageService.persistWorkPackage( workPackage );
		
		logger.info( "successfully create new WorkPackage: " + workPackage.toString() );

		beginConversation();
		return "viewWorkPackageDetails";
	}

	public WorkPackage getWorkPackage()
	{
		if (workPackage != null && workPackage.getParentWP() == null )
			workPackage.setParentWP( new WorkPackage() );
		return workPackage;
	}

	public void setWorkPackage( WorkPackage workPackage )
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
		if(workPackage.getBudget()==null)
			workPackage.setBudget(new Budget());
		if(workPackage.getEngineerBudget()==null)
			workPackage.setEngineerBudget(new EngineerBudget());

		if(workPackage!=null && workPackage.getParentWP() != null)
			parentWPId = workPackage.getParentWP().getId();
		return "editWorkPackage";
	}

	public String goNewTopLevelPackage( Project project )
	{
		workPackage.setProject( project );
		return "newWorkPackage";
	}

	/**
	 * Go to create new work package. Similar to goNewTopLevelPackage
	 * but takes project id rather than the project object.
	 * @param projId project id
	 * @return navigation view id
	 */
	public String goCreatePackage( final String projId )
	{
		workPackage = new WorkPackage();
		final Project project = projectService.getProject( projId );
		workPackage.setProject( project );

		return "newWorkPackage";
	}

	public String goChildPackage( WorkPackage parentWorkPackage )
	{
		beginConversation();
		workPackage = new WorkPackage();
		Project project = projectService.getProject(parentWorkPackage.getProject().getId());
		workPackage.setProject( project );
		workPackage.setEmployee(new Employee());
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

	/**
	 * @return a list of projects managed by current user.
	 */
	public List<WorkPackage> getAssociatedWorkPackages() {
		List<WorkPackage> workPackageList = null;
		final String userId = userController.getUser().getId();
		workPackageList = workPackageService.findResponsibleWorkPackages(userId);

		return workPackageList;
	}
	
	public List<Employee> getEmployees() {
		return workPackageService.getAssignedEmployeesToWp(workPackage.getId());
	}
	
	public List<Employee> getAvailableEmployees() {
		return workPackageService.getAssignedEmployeesToProjectNotWp(workPackage.getId(), workPackage.getProject().getId());
	}
	
	public String assignToProject() {
		return "addEmpToWorkPackage";
	}

	
	public List<WorkPackage> getChildWorkPackages() {
		return workPackageService.getChildWorkPackages(workPackage);
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
		beginConversation();
		this.workPackage = workPackage;
		return "viewWorkPackageDetails";
	}

	public String updateWorkpackage(){		
		WorkPackage parentWP = null;
		//logger.info( parentWPId );
		if ( parentWPId != null )
			parentWP = workPackageService.findWorkPackageById( parentWPId );

		workPackage.setParentWP( parentWP );

		String engineerId = reId;
		if (engineerId == null) {
			engineerId = workPackage.getEmployee().getId();
		}
		Employee employee = employeeService.findEmployeeById(engineerId);
		if(employee == null){
			FacesContext.getCurrentInstance().addMessage( "newWorkPackageForm:mnSupervisor",
					new FacesMessage( FacesMessage.SEVERITY_ERROR, "", "Employee does not exist!: "
							+ reId ) );
			return null;
		}

		workPackage.setEmployee(employee);

		workPackageService.updateWorkPackage(workPackage);
		workPackage = workPackageService.findWorkPackageById(workPackage.getId());
		//engineerBudgetMngr.merge(engBudget);
		return "viewWorkPackageDetails";
	}
	
	/**
	 * Assign employee to project and update project in database.
	 * 
	 * @param emp
	 *            employee to assign.
	 * @return navigating route.
	 */
	public String insertEmpToWp(final Employee emp) {
		workPackage = workPackageService.findWorkPackageById(workPackage.getId());
		workPackage.assignEmployee(emp);

		workPackageService.updateWorkPackage(workPackage);
		return null;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getReId() {
		return reId;
	}

	public void setReId(String reId) {
		this.reId = reId;
	}

}
