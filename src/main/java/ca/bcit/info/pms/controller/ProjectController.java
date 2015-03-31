package ca.bcit.info.pms.controller;

import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.service.ProjectService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
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
    
    /*
     * holds the project information
     */
    @Inject
    private Project project;
    
    private static final Logger logger = LogManager.getLogger(ProjectController.class);

    /**
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.project = project;
    }
    
    
    public String addProject(){
    	project.setStartDate(convertJavaDateToSqlDate(startDate));
    	project.setEndDate(convertJavaDateToSqlDate(endDate));
        projService.persistProject(project);
        logger.info("successfully create new project: " + project.toString());
        return "viewManagedProjects";
    }
    
    public java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }
    
    public List<Project> getProjects() {
        return projService.getAllProjects();
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
     * @param prject
     * @return
     */
    public String viewProjectDetails(final Project project){
    	this.project = project;
    	return "viewProjectDetails";
    }
    
    /**
     * 
     * @param updatedProject
     * @return
     */
    public String updateProject(){
    	project.setStartDate(convertJavaDateToSqlDate(startDate));
    	project.setEndDate(convertJavaDateToSqlDate(endDate));
    	projService.updateProject(project);
    	return "viewProjectDetails";
    }
}