package ca.bcit.info.pms.service;

import java.util.List;

import ca.bcit.info.pms.model.Credential;
import ca.bcit.info.pms.model.Employee;

public interface EmployeeService {
	boolean checkCredentials(Credential credential);

	void updateCreden(Credential credential); 
	
	void persistEmployee(Employee newEmp);

	void updateEmployee(Employee newEmp);

	List<Employee> getAllEmployee();
}
