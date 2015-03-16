package ca.bcit.info.pms.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.access.ProjectManager;
import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.service.ProjectService;


@Named( "ProjectService" )
public class ProjectServiceImpl implements Serializable, ProjectService{

    @Inject
    private ProjectManager projManager;

    @Override
    public void persistProject(Project newProject) {
        projManager.persist(newProject);
    }

    @Override
    public void updateProject(Project newProject) {
        projManager.updateProject(newProject);
    }

    @Override
    public List<Project> getAllProjects() {
        // TODO Auto-generated method stub
        return projManager.getAllProjects();
    }

    @Override
    public Project findProjectByProjectName(String projectName) {
        // TODO Auto-generated method stub
        return projManager.findByProjectName(projectName);
    }

}