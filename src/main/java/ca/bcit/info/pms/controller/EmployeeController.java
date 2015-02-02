package ca.bcit.info.pms.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.access.EmployeeManager;
import ca.bcit.info.pms.model.Credentials;
import ca.bcit.info.pms.model.Employee;

@Named("empController")
@SessionScoped	
public class EmployeeController implements Serializable {
	@Inject
	private EmployeeManager empManager;
	/**
	 * Hold the new employee information
	 */
	private Employee newEmp = new Employee("A00123456", "aa@bb.cc", "fred", "xie", 11.11, "a12345678", "fredxie", 1);
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
