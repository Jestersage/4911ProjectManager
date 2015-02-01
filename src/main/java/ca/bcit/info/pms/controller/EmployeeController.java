package ca.bcit.info.pms.controller;

import java.io.Serializable;

import javax.inject.Inject;

import ca.bcit.info.pms.access.EmployeeManager;
import ca.bcit.info.pms.model.Credentials;
import ca.bcit.info.pms.model.Employee;

public class EmployeeController implements Serializable {
	@Inject
	private EmployeeManager empManager;
	/**
	 * Hold the new employee information
	 */
	private Employee newEmp;
	/**
	 * Hold the new Credential information
	 */
	private Credentials creden;
	
	/**
	 * Add new Employee to database.
	 */
	public void addEmployee() {
		empManager.persistEmployee(newEmp);
	}
	
	/**
	 * Add new Credential to database.
	 */
	public void addCredential() {
		empManager.persistCredential(creden);
	}
}
