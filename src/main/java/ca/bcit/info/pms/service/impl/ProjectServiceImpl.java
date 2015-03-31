package ca.bcit.info.pms.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.access.ProjectManager;
import ca.bcit.info.pms.access.WorkPackageManager;
import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.service.ProjectService;


@Named( "ProjectService" )
public class ProjectServiceImpl implements Serializable, ProjectService{

    @Inject
    private ProjectManager projManager;
    
    @Inject
    private WorkPackageManager WPManager;

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
    	List<Project> projectList = projManager.getAllProjects();
    	for (Project project : projectList) {
    		int workPackageNum =  WPManager.findNumOfChildWP(project.getId());
    		if(workPackageNum != 0){
    			project.setHasWorkPackage(true);
    		}
		}
        return projectList;
    }

    @Override
    public Project findProjectByProjectName(String projectName) {
        return projManager.findByProjectName(projectName);
    }

    @Override
    public Project getProject(String id) {
    	Project project = projManager.findByProjectId(id);
    	int workPackageNum = WPManager.findNumOfChildWP(project.getId());
    	if(workPackageNum != 0){
			project.setHasWorkPackage(true);
		}
        return project;
    }

    @Override
    public List<Project> getManagedProjectsFor(String empId) {
    	List<Project> projectList = projManager.getManagedProjects(empId);
    	for (Project project : projectList) {
    		int workPackageNum = WPManager.findNumOfChildWP(project.getId());
    		if(workPackageNum != 0){
    			project.setHasWorkPackage(true);
    		}
		}
        return projectList;
    }

    @Override
    public List<Project> getAssistedProjectsFor(String empId) {
        List<Project> projectList = projManager.getAssistedProjects(empId);
    	for (Project project : projectList) {
    		int workPackageNum =  WPManager.findNumOfChildWP(project.getId());
    		if(workPackageNum != 0){
    			project.setHasWorkPackage(true);
    		}
		}
        return projectList;
    }

}