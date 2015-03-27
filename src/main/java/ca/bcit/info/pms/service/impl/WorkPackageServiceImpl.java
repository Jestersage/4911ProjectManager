package ca.bcit.info.pms.service.impl;

import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.access.BudgetManager;
import ca.bcit.info.pms.access.ProjectManager;
import ca.bcit.info.pms.access.WorkPackageManager;
import ca.bcit.info.pms.model.Budget;
import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.model.ProjectStatus;
import ca.bcit.info.pms.model.WorkPackage;
import ca.bcit.info.pms.service.WorkPackageService;

/**
 * 
 *
 */

@Named( "WorkPackageService" )
public class WorkPackageServiceImpl implements WorkPackageService {
	
	
	@Inject
    private WorkPackageManager WPManager;
	
	@Inject
	private ProjectManager projManager;
	
	@Inject
    private BudgetManager bgManager;
	
	/* (non-Javadoc)
	 * @see ca.bcit.info.pms.service.WorkPackageService#persistWorkPackage(ca.bcit.info.pms.model.WorkPackage)
	 */
	@Override
	public void persistWorkPackage(WorkPackage newWorkPackage) {

		if(newWorkPackage.getStatus() == null)
			newWorkPackage.setStatus(ProjectStatus.CREATED);
//		if(newWorkPackage.getEmployees() == null)
//			newWorkPackage.setEmployees(new HashSet<Employee>());
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
		return WPManager.getAllWorkPackages();
	}
	
    @Override
    public List<Project> getAllProjects() {
        // TODO Auto-generated method stub
        return projManager.getAllProjects();
    }

    public int getNumOfChildWP(String projId) {
    	return WPManager.findNumOfChildWP(projId);
    }
}
