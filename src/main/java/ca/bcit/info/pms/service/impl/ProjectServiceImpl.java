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
        return projManager.getAllProjects();
    }

    @Override
    public Project findProjectByProjectName(String projectName) {
        return projManager.findByProjectName(projectName);
    }

    @Override
    public Project getProject(String id) {
        return projManager.findByProjectId(id);
    }

    @Override
    public List<Project> getManagedProjectsFor(String empId) {
        return projManager.getManagedProjects(empId);
    }

    @Override
    public List<Project> getAssistedProjectsFor(String empId) {
        return projManager.getAssistedProjects(empId);
    }

}