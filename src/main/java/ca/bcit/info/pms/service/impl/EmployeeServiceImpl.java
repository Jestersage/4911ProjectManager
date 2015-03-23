package ca.bcit.info.pms.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.access.EmployeeManager;
import ca.bcit.info.pms.model.Credential;
import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.service.EmployeeService;

@Named( "EmployeeService" )
public class EmployeeServiceImpl implements EmployeeService, Serializable
{

	@Inject
	private EmployeeManager empManager;

	private List< Credential > credentialList;

    // ========= current user authorization ========
	@Override
	public boolean checkCredentials( final Credential credential )
	{
		credentialList = empManager.getCredentials();
		
		if ( credentialList != null ) {
		    for (Credential c : credentialList) {
		        if (c.getUsername().equals(credential.getUsername())) {
		            // TODO: Authenticate password with HashTest
		            return true;
		        }
		    }
		    // If no match found in list
		    return false;
		} else {
			return false;
		}
	}

    @Override
    public void updateCredential(final Credential credential) {
        empManager.updateCredential(credential);
    }

    @Override
    public Map<String, Boolean> checkAuthorization( final String empId )
    {
        Map<String, Boolean> authorization = new HashMap<>();

        authorization.put(Employee.ROLE_HR, empManager.hasHrRole(empId));

        // TODO stub method, create empManager methods
        authorization.put(Employee.ROLE_SUPERVISOR, false);
        authorization.put(Employee.ROLE_TS_APPROVER, false);
        authorization.put(Employee.ROLE_PROJECT_MANAGER, false);
        authorization.put(Employee.ROLE_ASSISTANT, false);
        authorization.put(Employee.ROLE_WP_MANAGER, false);

        return authorization;
    }

    // ========= Employee CRUD ========
    @Override
	public void persistEmployee(Employee newEmp) {
		empManager.persistEmployee(newEmp);
	}

	@Override
	public void updateEmployee( Employee employee)
	{
		empManager.updateEmployee(employee);
	}

	@Override
	public List< Employee > getAllEmployee()
	{

		return empManager.getAllEmployee();
	}

    // ========= Helper methods ========
    @Override
    public Employee findEmployeeByUsername(String username) {
        return empManager.findByUsername(username);
    }

    @Override
    public Employee findEmployeeById(String id) {
        return empManager.findById(id);
    }

    @Override
    public String getAvailableAuths(Map<String, Boolean> authorizations) {
        StringBuilder sb = new StringBuilder();

        final Set<Map.Entry<String, Boolean>> authSet = authorizations.entrySet();

        for(Map.Entry<String, Boolean> auth : authSet) {
            final Boolean authVal = auth.getValue();
            if (authVal != null & authVal == true) {
                sb.append(auth.getKey()).append(", ");
            }
        }

        if(sb.length() > 0) {
            sb.delete(sb.length() - 2, sb.length() - 1);
        }

        return sb.toString();
    }
}
