/**
 * 
 */
package ca.bcit.info.pms.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
	
	
	
    private static final Logger logger = LogManager.getLogger(WorkPackageController.class);

    
    public String index(){
    	
    	logger.info("index new WorkPackage: ");
		return "newWorkPackage";
    	
    }

	public String addWorkPackage(){
		workPackageService.persistWorkPackage(workPackage);
        logger.info("successfully create new WorkPackage: " + workPackage.toString());
        return "index";
    }
	
	public WorkPackage getworkPackage() {
		return workPackage;
	}
	public void setworkPackage(WorkPackage workPackage) {
		this.workPackage = workPackage;
	}
}
