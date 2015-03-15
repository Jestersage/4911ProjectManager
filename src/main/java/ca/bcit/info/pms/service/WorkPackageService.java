	
package ca.bcit.info.pms.service;

import java.util.List;

import ca.bcit.info.pms.model.WorkPackage;

public interface WorkPackageService {

	void persistWorkPackage(WorkPackage newWorkPackage);
    
    void updateWorkPackage(WorkPackage newWorkPackage);
    
    List<WorkPackage> getAllWorkPackages();
    
    	
}
