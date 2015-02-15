package ca.bcit.info.pms.service;

import ca.bcit.info.pms.model.Credential;

public interface EmployeeService {
	boolean checkCredentials(Credential credential);
}
