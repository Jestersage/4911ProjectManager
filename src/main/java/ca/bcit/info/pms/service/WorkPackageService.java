	
package ca.bcit.info.pms.service;

import java.util.List;

import ca.bcit.info.pms.model.Budget;
import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.model.WorkPackage;

public interface WorkPackageService {

	void persistWorkPackage(WorkPackage newWorkPackage);
	
	//void persistBudget(Budget budget);
    
    void updateWorkPackage(WorkPackage newWorkPackage);
    
    List<WorkPackage> getAllWorkPackages();
    
    List<Project> getAllProjects();	
    
    int getNumOfChildWP(String projId);
}
