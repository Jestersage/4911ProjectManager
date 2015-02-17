package ca.bcit.info.pms.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.access.EmployeeManager;
import ca.bcit.info.pms.model.Credential;
import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.service.EmployeeService;


@Named("EmployeeService")
public class EmployeeServiceImpl implements EmployeeService, Serializable {

	@Inject
	private EmployeeManager empManager;

	private List<Credential> credentialList;

	@Override
	public boolean checkCredentials(Credential credential) {
		credentialList = empManager.getCredentials();
		if(credentialList != null)
		    return credentialList.contains(credential);
		else
		    return false;
	}

	@Override
	public void persistEmployee(Employee newEmp) {
		empManager.persistEmployee(newEmp);
		
	}

	@Override
	public void updateEmployee(Employee newEmp) {
		empManager.updateEmployee(newEmp);
		
	}

	@Override
	public List<Employee> getAllEmployee() {
		
		return empManager.getAllEmployee();
	}

	public void updateCreden(Credential credential) {
		empManager.updateCredential(credential);
	}
	
}
