package ca.bcit.info.pms.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.access.BudgetManager;
import ca.bcit.info.pms.access.ProjectManager;
import ca.bcit.info.pms.access.WorkPackageManager;
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
public class WorkPackageServiceImpl implements Serializable, WorkPackageService {

	
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

	@Override
	public List<WorkPackage> findAssignedWorkPackages(String empId) {
		return WPManager.getWorkPackagesByAssignee(empId);
	}

	@Override
	public List<WorkPackage> findResponsibleWorkPackages(String empId) {
		return WPManager.getManagedWorkPackage(empId);
	}

	public int getNumOfChildWP(String projId) {
    	return WPManager.findNumOfChildWP(projId);
    }

	@Override
	public WorkPackage findWorkPackageById(Integer id) {
	
		return WPManager.find(id);
	}

	@Override
	public int getEngBudgetID(Integer id) {
		// TODO Auto-generated method stub
		return WPManager.getEngBudgetID(id);
	}

	@Override
	public List<WorkPackage> getChildWorkPackages(final WorkPackage parentWp) {
		return WPManager.getChildWorkPackages(parentWp);
	}

	public WorkPackage getUniquePackage(final String projId, final String packageNo) {
		return WPManager.getUniqueWP(projId, packageNo);
	}
	
	public List<Employee> getAssignedEmployeesToWp(final Integer wpId) {
		return WPManager.getEmployeesAssignedToWp(wpId.toString());
	}
	
	public List<Employee> getAssignedEmployeesToProjectNotWp(final Integer wpId, final String projId) {
		System.out.println("WP: " + wpId + " Proj: " + projId);
		return WPManager.getEmployeesAssignedToProjectNotInWp(projId);
	}
}
