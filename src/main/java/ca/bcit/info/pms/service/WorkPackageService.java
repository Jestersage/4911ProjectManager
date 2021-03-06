	
package ca.bcit.info.pms.service;

import java.util.List;

import ca.bcit.info.pms.model.Budget;
import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.model.WorkPackage;

public interface WorkPackageService {

	void persistWorkPackage(WorkPackage newWorkPackage);
	
	//void persistBudget(Budget budget);
    
    void updateWorkPackage(WorkPackage newWorkPackage);
    
    List<WorkPackage> getAllWorkPackages();
    
    List<Project> getAllProjects();

    /**
     * @param empId id of employee assigned to work packages.
     * @return a list of all work packages an employee is assigned to.
     */
    List<WorkPackage> findAssignedWorkPackages(final String empId);

    List<WorkPackage> findResponsibleWorkPackages(final String empId);

    int getNumOfChildWP(String projId);

	WorkPackage findWorkPackageById(Integer id);

	int getEngBudgetID(Integer id);

    /**
     * @param parentWp parent work pacakge
     * @return list of immediate child work package of specified wp.
     */
    List<WorkPackage> getChildWorkPackages(final WorkPackage parentWp);
    
    WorkPackage getUniquePackage(final String projId, final String packageNo);
    
    List<Employee> getAssignedEmployeesToWp(final Integer integer);
    
    List<Employee> getAssignedEmployeesToProjectNotWp(final Integer wpId, final String projId);
}
