package ca.bcit.info.pms.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.access.EmployeeManager;
import ca.bcit.info.pms.model.Credentials;
import ca.bcit.info.pms.service.EmployeeService;


@Named("EmployeeService")
public class EmployeeServiceImpl implements EmployeeService, Serializable {

	@Inject
	private EmployeeManager empManager;
	

	private List<Credentials> credentialList;
	
	@Override
	public boolean checkCredentials(Credentials credentials) {
		credentialList = empManager.getCredentials();
		if(credentialList!=null)
		return credentialList.contains(credentials);
		else 
		return false;
	}

}
