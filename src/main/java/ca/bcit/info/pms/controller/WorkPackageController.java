/**
 * 
 */
package ca.bcit.info.pms.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.model.WorkPackage;
import ca.bcit.info.pms.service.ProjectService;
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
	@Inject
	private ProjectService projectService;
	
	private Project[] projectList;
	
	private Integer parentWPId;

	private static final Logger logger = LogManager
			.getLogger(WorkPackageController.class);

	@Inject
	private UserController userController;

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
		WorkPackage parentWP = null;
		logger.info(parentWPId);
		if(parentWPId != null)
			parentWP = workPackageService.findWorkPackageById(parentWPId);
			workPackage.setParentWP(parentWP);
		workPackageService.persistWorkPackage(workPackage);
		//workPackageService.persistBudget(budget);
		logger.info("successfully create new WorkPackage: "
				+ workPackage.toString());
		return "viewAllPackages";
	}

	public WorkPackage getworkPackage() {
		if(workPackage.getParentWP() == null)
			workPackage.setParentWP(new WorkPackage());
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
    
    public String goChildPackage(WorkPackage parentWorkPackage) {
    	Project project = projectService.getProject(parentWorkPackage.getProject().getId());    		
    	workPackage.setProject(project);
    	parentWPId = parentWorkPackage.getId();
    	return "newWorkPackage";
    }

	/**
	 * @return a list of all work packages an employee is assigned to.
	 */
	public List<WorkPackage> getMyWorkPackages() {
		final String userId = userController.getUser().getId();
		return workPackageService.findAssignedWorkPackages(userId);
	}


	public Integer getParentWPId() {
		return parentWPId;
	}


	public void setParentWPId(Integer parentWPId) {
		this.parentWPId = parentWPId;
	}


}
