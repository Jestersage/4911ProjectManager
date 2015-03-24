package ca.bcit.info.pms.service;

import java.util.List;

import ca.bcit.info.pms.model.Project;

public interface ProjectService {

    void persistProject(Project newProject);
    
    void updateProject(Project newProject);
    
    List<Project> getAllProjects();
    
    Project findProjectByProjectName(final String projectName);

    /**
     * @param id project id.
     * @return project with specified id.
     */
    Project getProject(final String id);
}
