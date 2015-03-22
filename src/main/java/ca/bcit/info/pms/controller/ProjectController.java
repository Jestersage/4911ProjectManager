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
        return "newProject";
    }
    
    public java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }
    
    public List<Project> getProjects() {
        return projService.getAllProjects();
    }
    
    public String goEditProject() {
    	return "editProject";
    }
    
    public void clearFields(){
        
    }
}