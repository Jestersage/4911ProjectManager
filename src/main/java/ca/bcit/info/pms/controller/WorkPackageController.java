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

@Named("WPController")
@RequestScoped
public class WorkPackageController implements Serializable {

	@Inject
	private WorkPackage WP;
	@Inject
	private WorkPackageService WPService;
	
    private static final Logger logger = LogManager.getLogger(WorkPackageController.class);

	public String addWorkPackage(){
		WPService.persistWorkPackage(WP);
        logger.info("successfully create new WorkPackage: " + WP.toString());
        return "newProject";
    }
}
