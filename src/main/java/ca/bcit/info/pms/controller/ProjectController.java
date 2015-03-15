package ca.bcit.info.pms.controller;

import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.service.ProjectService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("projectController")
@RequestScoped
public class ProjectController implements Serializable{
    
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
        projService.persistProject(project);
        logger.info("successfully create new project: " + project.toString());
        return "newProject";
    }
    
    public void clearFields(){
        
    }
}