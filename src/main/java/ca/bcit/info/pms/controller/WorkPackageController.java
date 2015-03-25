/**
 * 
 */
package ca.bcit.info.pms.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.model.WorkPackage;
import ca.bcit.info.pms.service.WorkPackageService;

/**
 * 
 *
 */

@Named("workPackageController")
@RequestScoped
public class WorkPackageController implements Serializable {

	@Inject
	private WorkPackage workPackage;
	@Inject
	private WorkPackageService workPackageService;

	private Project[] projectList;
	
	
//    @ManagedProperty("#{param.projId}")
//    private String projectId;
//
//	public String getProjectId() {
//		return projectId;
//	}
//
//
//	public void setProjectId(String projectId) {
//		this.projectId = projectId;
//	}

	private static final Logger logger = LogManager
			.getLogger(WorkPackageController.class);

//	public String index() {
//		if (workPackage == null)
//			workPackage = new WorkPackage();
//		if (workPackage.getProject() == null) {
////			 FacesContext context = FacesContext.getCurrentInstance();
////			 context.addMessage(null,
////	                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
////	                            "You haven't select a Project", null));	
////			 return "";
//			workPackage.setProject(new Project());
//			projectList = getProjectList();
//			   
//		}
//		
//		logger.info("index new WorkPackage: ");
//		return "newWorkPackage";
//
//	}

	
	public Project[] getProjectList() {
	    projectList = new Project[workPackageService.getAllProjects().size()];
	    projectList =  workPackageService.getAllProjects().toArray(projectList);
	    return projectList;
 
	}
	
	
	public String addWorkPackage() {
		System.out.println(workPackage.getProject().getId());
		workPackageService.persistWorkPackage(workPackage);
		logger.info("successfully create new WorkPackage: "
				+ workPackage.toString());
		return "viewAllPackages";
	}

	public WorkPackage getworkPackage() {
		return workPackage;
	}

	public void setworkPackage(WorkPackage workPackage) {
		this.workPackage = workPackage;
	}

    public List<WorkPackage> getWorkPackages() {
        return workPackageService.getAllWorkPackages();
    }
    
    public String goEditPackage() {
    	return "editWorkPackage";
    }
    
    public String goNewTopLevelPackage(Project project) {
    	workPackage.setProject(project);
    	return "newWorkPackage";
    }
    
	public int findNumOfChildWP(String projId) {
		int i = workPackageService.getNumOfChildWP(projId);
		System.out.println(i);
		return workPackageService.getNumOfChildWP(projId);
	}
}
