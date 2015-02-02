package ca.bcit.info.pms.service;

import java.io.Serializable;

import ca.bcit.info.pms.model.Credentials;

public interface EmployeeService {
	boolean checkCredentials(Credentials credentials);
}
