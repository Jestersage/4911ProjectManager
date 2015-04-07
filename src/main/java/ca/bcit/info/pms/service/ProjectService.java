package ca.bcit.info.pms.service;

import java.util.List;

import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.model.WorkPackage;

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

    /**
     * @param empId project manager's employee id.
     * @return list of projects managed by this employee.
     */
    public List<Project> getManagedProjectsFor(String empId);

    /**
     * @param empId project assistant's employee id.
     * @return list of projects managed by this assistant.
     */
    public List<Project> getAssistedProjectsFor(final String empId);

    /**
     * @param id project id.
     * @return a list of top level work packages belongs to this project.
     */
    List<WorkPackage> getTopLevelPackages(final String id);

    List<Employee> getAssignedEmployee(final String id);
}
