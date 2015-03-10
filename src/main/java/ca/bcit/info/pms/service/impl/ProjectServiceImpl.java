package ca.bcit.info.pms.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import ca.bcit.info.pms.access.ProjectManager;
import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.service.ProjectService;

public class ProjectServiceImpl implements Serializable, ProjectService{

    @Inject
    private ProjectManager projManager;
    
    @Override
    public void persistProject(Project newProject) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateProject(Project newProject) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Project> getAllProjects() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Project findProjectByProjectName(String projectName) {
        // TODO Auto-generated method stub
        return null;
    }

}
