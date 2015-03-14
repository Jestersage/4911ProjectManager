package ca.bcit.info.pms.service;

import java.util.List;

import ca.bcit.info.pms.model.Credential;
import ca.bcit.info.pms.model.Employee;

/**
 * Services and business logic of employee services.
 * 1. Current user permission, authentication.
 * 2. Current user profile (view & partial edit)
 * 3. Employee CRUD
 */
public interface EmployeeService {

    // ========= current user authorization ========
    /**
     * @param credential username & password pair
     * @return if login credential is valid, and if user should login
     */
    boolean checkCredentials(Credential credential);

    /**
     * Update current user login credential.
     * @param credential username & password pair
     */
    void updateCredential(Credential credential);

    /**
     * @param user currently authenticated user
     * @return if current user is in HR role.
     */
    boolean isRoleHr(final Employee user);

    // ========= Employee CRUD ========
    /**
     * Add a new employee.
     * @param newEmp    employee information
     */
	void persistEmployee(Employee newEmp);

	void updateEmployee(Employee newEmp);

	List<Employee> getAllEmployee();

    // ========= Helper methods ========
    /**
     * Find employee by username.
     * @param username employee username
     * @return employee or null
     */
    Employee findEmployeeByUsername(final String username);
}
