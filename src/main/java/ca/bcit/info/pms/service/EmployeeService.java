package ca.bcit.info.pms.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
    boolean checkCredentials(final Credential credential);

    /**
     * Update current user login credential.
     * @param credential username & password pair
     */
    void updateCredential(final Credential credential);

    /**
     * @param empId currently authenticated user's employee id
     * @return map of if user has certain roles or not.
     */
    Map<String, Boolean> checkAuthorization( final String empId );


    boolean isProjectManagerFor(final String userId, final String projectId);
    boolean isAssistantFor(final String userId, final String projectId);
    boolean isEngineerFor(final String userId, final int wpId);
    boolean isSupervisorFor(final String supervisorId, final String empId);

    // ========= Employee CRUD ========
    /**
     * Add a new employee.
     * @param newEmp    employee information
     */
	void persistEmployee(Employee newEmp);

    /**
     * Update existing employee.
     * @param employee employee to update.
     */
	void updateEmployee(Employee employee);

    /**
     * @return a list of all employees.
     */
	List<Employee> getAllEmployee();

    /**
     * @param empId supervisor employee id
     * @return a list of employees managed by specified supervisor.
     */
    List<Employee> getManagedEmployeeFor(final String empId);

    // ========= Helper methods ========
    /**
     * Find employee by username.
     * @param username employee username
     * @return employee or null
     */
    Employee findEmployeeByUsername(final String username);

    /**
     * Find employee by id.
     * @param id employee id.
     * @return employee or null
     */
    Employee findEmployeeById(final String id);

    /**
     * Convert authorization map into string representation, displaying
     * only available ones.
     * @param authorizations auth map of an employee
     * @return string representation of available authorizations.
     */
    String getAvailableAuths(final Map<String, Boolean> authorizations);

    /**
     * @param id employee id.
     * @return flex time banked by specified employee.
     */
    BigDecimal getBankedFlexTime(final String id);
}
