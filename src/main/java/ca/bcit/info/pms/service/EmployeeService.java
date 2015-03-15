package ca.bcit.info.pms.service;

import java.util.List;

import ca.bcit.info.pms.model.Credential;
import ca.bcit.info.pms.model.Employee;

public interface EmployeeService
{
	/**
	 * @param credential
	 *            username & password pair
	 * @return if login credential is valid, and if user should login
	 */
	boolean checkCredentials( Credential credential );

	/**
	 * Update current user login credential.
	 * 
	 * @param credential
	 *            username & password pair
	 */
	void updateCredential( Credential credential );

	/**
	 * Add a new employee.
	 * 
	 * @param newEmp
	 *            employee information
	 */
	void persistEmployee( Employee newEmp );

	void updateEmployee( Employee newEmp );

	List< Employee > getAllEmployee();

	/**
	 * Find employee by username.
	 * 
	 * @param username
	 *            employee username
	 * @return employee or null
	 */
	Employee findEmployeeByUsername( final String username );

	String checkAuthorization( String id );
}
