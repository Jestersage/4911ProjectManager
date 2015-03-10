package ca.bcit.info.pms.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.service.ProjectService;

@Named("projectController")
@RequestScoped
public class ProjectController implements Serializable{
    
    @Inject
    private ProjectService projService;
    
    @Inject
    private Project project;

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
    
    public void saveProject(){
        
    }
    
    public void clearFields(){
        
    }
}
