package ca.bcit.info.pms.service.impl;

import java.util.List;

import javax.inject.Inject;

import ca.bcit.info.pms.access.WorkPackageManager;
import ca.bcit.info.pms.model.WorkPackage;
import ca.bcit.info.pms.service.WorkPackageService;

/**
 * 
 *
 */
public class WorkPackageServiceImpl implements WorkPackageService {
	
	
	@Inject
    private WorkPackageManager WPManager;

	/* (non-Javadoc)
	 * @see ca.bcit.info.pms.service.WorkPackageService#persistWorkPackage(ca.bcit.info.pms.model.WorkPackage)
	 */
	@Override
	public void persistWorkPackage(WorkPackage newWorkPackage) {
		WPManager.persist(newWorkPackage);

	}

	/* (non-Javadoc)
	 * @see ca.bcit.info.pms.service.WorkPackageService#updateWorkPackage(ca.bcit.info.pms.model.WorkPackage)
	 */
	@Override
	public void updateWorkPackage(WorkPackage newWorkPackage) {
		WPManager.updateWorkPackage(newWorkPackage);

	}

	/* (non-Javadoc)
	 * @see ca.bcit.info.pms.service.WorkPackageService#getAllWorkPackages()
	 */
	@Override
	public List<WorkPackage> getAllWorkPackages() {
		// TODO Auto-generated method stub
		return null;
	}

}
