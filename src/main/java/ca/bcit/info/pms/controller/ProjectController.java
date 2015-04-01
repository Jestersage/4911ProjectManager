package ca.bcit.info.pms.controller;

import ca.bcit.info.pms.model.Credential;
import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.service.EmployeeService;
import ca.bcit.info.pms.service.ProjectService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

@Named("projectController")
@RequestScoped
public class ProjectController implements Serializable{
    private java.util.Date startDate, endDate;
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

    /*
     * holds the project information
     */
    @Inject
    private Project project;

    /** keeps track of new project manager's id.*/
    private String managerId;
    
    private static final Logger logger = LogManager.getLogger(ProjectController.class);

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
//        // if project manager not found, rerender page
//        if (!updateProjectManager()) {
//            return null;
//        }
    	project.setStartDate(convertJavaDateToSqlDate(startDate));
    	project.setEndDate(convertJavaDateToSqlDate(endDate));
        projService.persistProject(project);
        logger.info("successfully create new project: " + project.toString());
        return "viewProjectDetails";
    }

    /**
     *
     * @return
     */
    public String updateProjectManager() {
        project = projService.getProject(project.getId());

        final Employee newManager = empService.findEmployeeById(managerId);
        project.setProjectManager(newManager);

        projService.updateProject(project);
        return null;
//        return "viewProjectDetails";
    }
//
//    private boolean updateProjectManager() {
//        boolean updated = false;
//
//        final String username = project.getProjectManager().getCredential().getUsername();
//        final Employee manager = empService.findEmployeeByUsername(username);
//        if ( manager == null )
//        {
//            FacesContext.getCurrentInstance().addMessage(
//                    "newProjectForm:mnManager",
//                    new FacesMessage( FacesMessage.SEVERITY_ERROR, "", "No employee found with username: "
//                            + username ) );
//        } else
//        {
//            project.setProjectManager(manager);
//            updated = true;
//        }
//
//        return updated;
//    }

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

        if (userController.isAuthorized(Employee.ROLE_PROJECT_MANAGER)) {
            managedProjects = projService.getManagedProjectsFor(userId);
        }
        else {
            managedProjects = projService.getAssistedProjectsFor(userId);
        }

        return managedProjects;
    }

    public List<Employee> getProjectManagerCandidates() {
        List<Employee> candidates = employeeController.getManagedEmployees();
        final Employee currentManager = project.getProjectManager();

        if (!candidates.contains(currentManager)) {
            candidates.add(currentManager);
        }

        candidates.sort((o1, o2) -> o1.getLastName().compareTo(o2.getLastName()));

        return candidates;
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
        return "assignEmpToProject";
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
}