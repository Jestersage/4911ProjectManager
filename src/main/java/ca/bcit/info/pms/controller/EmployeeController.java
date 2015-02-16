package ca.bcit.info.pms.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.access.EmployeeManager;
import ca.bcit.info.pms.model.Credential;
import ca.bcit.info.pms.model.Employee;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Named("empController")
@SessionScoped	
public class EmployeeController implements Serializable {
	public Employee getNewEmp() {
		return newEmp;
	}

	public Credential getCreden() {
		return creden;
	}

	@Inject
	private EmployeeManager empManager;
	/**
	 * Hold the new employee information
	 */
	@Inject
	private Employee newEmp; 
	/**
	 * Hold the new Credential information
	 */
	@Inject
	private Credential creden;

	/**
	 * Select list for pay level on newEmployee.xhtml page
	 */
	private ArrayList<String> payLevelItems = new ArrayList<String>() {{
	    add("P1");
	    add("P2");
	    add("P3");
	    add("P4");
	    add("P5");
	    add("P6");
	}};
	
	public ArrayList<String> getPayLevelItems() {
		return payLevelItems;
	}

	public void setPayLevelItems(ArrayList<String> payLevelItems) {
		this.payLevelItems = payLevelItems;
	}

    private static final Logger logger = LogManager.getLogger(EmployeeController.class);

	/**
	 * Add new Employee to database.
	 */
	public void addEmployee() {
		newEmp = new Employee("A00123456", "aa@bb.cc", "fred", "xie", "P1", "a12345678", "fredxie", 1);
		empManager.persistEmployee(newEmp);
		newEmp = new Employee();
	}
	
	/**
	 * Add new Credential to database.
	 */
	public void addCredential() {
		creden = new Credential("fredxie", "aaaaaa");
		empManager.persistCredential(creden);
		creden = new Credential();
	}
	
	/**
	 * Add new Employee to database.
	 */
	public void updateEmployee() {
		newEmp = new Employee("A00123456", "aa@bb.cc", "huanan", "wang", "P1", "a12345678", "fredxie", 1);
		empManager.updateEmployee(newEmp);
		newEmp = new Employee();
	}

    public List<Employee> getEmployees() {
        return empManager.getAllEmployee();
    }
}
