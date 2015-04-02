package ca.bcit.info.pms.controller;

import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.model.WorkPackage;
import ca.bcit.info.pms.service.EmployeeService;
import ca.bcit.info.pms.service.ProjectService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named("projectController")
@ConversationScoped
public class ProjectController implements Serializable{
    private java.util.Date startDate, endDate;
    private String assistantUsername;

    public java.util.Date getStartDate() {
		return startDate;
	}

	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	public java.util.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	@Inject
    private ProjectService projService;

    @Inject
    private UserController userController;

    @Inject
    private EmployeeController employeeController;

    @Inject
    private EmployeeService empService;

    @Inject
    private Conversation conversation;

    /*
     * holds the project information
     */
    @Inject
    private Project project;

    /** keeps track of new project manager's id.*/
    private String managerId;
    
    private static final Logger logger = LogManager.getLogger(ProjectController.class);

    private void beginConversation() {
        if (conversation.isTransient()) {
            logger.info("Project, begin conversation.");
            conversation.begin();
        }
    }

    private void endConversation() {
        if(!conversation.isTransient()){
            logger.info("Project, end conversation.");
            conversation.end();
        }
    }

    /**
     * @return the project
     */
    public Project getProject() {
        if (project.getProjectManager() == null) {
            final Employee manager = userController.getUser();
            project.setProjectManager(manager);
        }

        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.project = project;
    }
    
    
    public String addProject(){

    	if(project == null) return null;
    	
    	Project backProject = projService.getProject(project.getId());
    	if(backProject != null){
    		FacesContext.getCurrentInstance().addMessage( "newProjectForm:itProjectNumber",
                    new FacesMessage( FacesMessage.SEVERITY_ERROR, "", "Project already exist!: "
                            + project.getId() ) );
    		return null;
    	}
    	
    	project.setStartDate(convertJavaDateToSqlDate(startDate));
    	project.setEndDate(convertJavaDateToSqlDate(endDate));
        projService.persistProject(project);
        logger.info("successfully create new project: " + project.toString());

        beginConversation();
        return "viewProjectDetails";
    }

    /**
     * Update a project's manager.
     * @return
     */
    public String updateProjectManager() {
        project = projService.getProject(project.getId());

        final Employee newManager = empService.findEmployeeById(managerId);
        project.setProjectManager(newManager);

        projService.updateProject(project);
        return "viewProjectDetails";
    }

    public String updateAssistant() {
        project = projService.getProject(project.getId());

        final Employee assistant = empService.findEmployeeByUsername(assistantUsername);
        if ( assistant == null )
        {
            FacesContext.getCurrentInstance().addMessage(
                    "projectForm:mnAssistant",
                    new FacesMessage( FacesMessage.SEVERITY_ERROR, "", "No employee found with username: "
                            + assistantUsername ) );

            return null;
        }

        project.setAssistant(assistant);
        projService.updateProject(project);

        return "viewProjectDetails";
    }

    public java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }
    
    public List<Project> getProjects() {
        return projService.getAllProjects();
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    /**
     * @return a list of projects managed by current user.
     */
    public List<Project> getManagedProjects() {
        List<Project> managedProjects;
        final String userId = userController.getUser().getId();

        final boolean isManager = userController.isAuthorized(Employee.ROLE_PROJECT_MANAGER);
        final boolean isAssistant = userController.isAuthorized(Employee.ROLE_ASSISTANT);
        if (isManager && isAssistant) {
            final List<Project> assistantList = projService.getAssistedProjectsFor(userId);
            managedProjects = projService.getManagedProjectsFor(userId);
            managedProjects.addAll(assistantList);
        } else if (isManager) {
            managedProjects = projService.getManagedProjectsFor(userId);

        } else {
            managedProjects = projService.getAssistedProjectsFor(userId);
        }

        return managedProjects;
    }

    /**
     * @return a list of possible employee to be assigned as project manager.
     */
    public List<Employee> getProjectManagerCandidates() {
        final List<Employee> candidates = employeeController.getManagedEmployees();
        final Employee currentManager = project.getProjectManager();
        final Employee currentUser = userController.getUser();

        // allow current manager as a choice
        if (!candidates.contains(currentManager)) {
            candidates.add(currentManager);
        }

        // allow supervisor to assign himself as manager
        if (!candidates.contains(currentUser)) {
            candidates.add(currentUser);
        }

        candidates.sort((o1, o2) -> o1.getLastName().compareTo(o2.getLastName()));

        return candidates;
    }

    /**
     * @return a list of the top level work packages for this project
     */
    public List<WorkPackage> getTopLevelPackages() {
        return projService.getTopLevelPackages(project.getId());
    }

    /**
     * 
     * @return
     */
    public String goEditProject() {
    	this.project = projService.getProject(project.getId());
    	if(project.getStartDate() != null)
    		startDate = new java.util.Date(project.getStartDate().getTime());
    	if(project.getEndDate() != null)
    		endDate = new java.util.Date(project.getEndDate().getTime());
    	return "editProject";
    }
    
    public void clearFields(){
        
    }

    /**
     * Route to assign employee to project page.
     * @return navigating route.
     */
    public String assignToProject(final Project proj) {
        project = proj;
        return "addEmpToProject";
    }

    /**
     * Assign employee to project and update project in database.
     * @param emp employee to assign.
     * @return navigating route.
     */
    public String insertEmpToProject(final Employee emp) {
        project = projService.getProject(project.getId());
        project.assignEmployee(emp);

        projService.updateProject(project);
        return null;
    }

    /**
     * 
     * @return
     */
    public String viewProjectDetails(final Project project){
        beginConversation();
    	this.project = project;
    	return "viewProjectDetails";
    }
    
    /**
     * 
     * @return
     */
    public String updateProject(){
    	project.setStartDate(convertJavaDateToSqlDate(startDate));
    	project.setEndDate(convertJavaDateToSqlDate(endDate));
    	projService.updateProject(project);
    	return "viewProjectDetails";
    }

    public String changeManager() {
        project = projService.getProject(project.getId());
        return "changeProjectManager";
    }

    public String changeAssistant() {
        project = projService.getProject(project.getId());
        return "changeAssistant";
    }

    public String goCreateProject() {
        endConversation();
        return "newProject";
    }

    public void setAssistantUsername(String assistantUsername) {
        this.assistantUsername = assistantUsername;
    }

    public String getAssistantUsername() {
        return assistantUsername;
    }
}