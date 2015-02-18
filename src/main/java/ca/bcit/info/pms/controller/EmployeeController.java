package ca.bcit.info.pms.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.model.Credential;
import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.service.EmployeeService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Named("empController")
@RequestScoped
public class EmployeeController implements Serializable {

    @Inject
    private EmployeeService empService;
    /**
     * Hold the new employee information
     */
    @Inject
    private Employee employee;

	/**
	 * Hold the new Credential information
	 */
	@Inject
	private Credential credential;

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
    public String addEmployee() {
        logger.debug(employee.toString());
        empService.persistEmployee(employee, credential);
        logger.info("successfully create new employee ");
        return "newEmployee";
    }

    /**
     * Add new Employee to database.
     */
/*
    public void updateEmployee() {
        //	employee = new Employee("A00123456", "aa@bb.cc", "huanan", "wang", "P1", "a12345678", "fredxie", 1);

        empService.updateEmployee(employee);
        employee = new Employee();
    }
*/

    public List<Employee> getEmployees() {
        return empService.getAllEmployee();
    }

    public Employee getEmployee() {
        return employee;
    }

    public Credential getCredential() {
        return credential;
    }
}
