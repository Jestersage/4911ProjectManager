package ca.bcit.info.pms.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.model.Credential;
import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.PayLevel;
import ca.bcit.info.pms.model.PayLevel.PayGrade;
import ca.bcit.info.pms.service.EmployeeService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Named("empController")
@RequestScoped
public class EmployeeController implements Serializable {

    /** Hold the new employee information. */
    @Inject
    private Employee employee;

    /** Hold info about new employee's supervisor username from and to form. */
    private String supervisorUsername;

    @Inject
    private EmployeeService empService;

    private static final Logger logger = LogManager.getLogger(EmployeeController.class);

    public PayGrade[] getPayLevelItems() {
        return PayLevel.PayGrade.values();
    }

    public List<Employee> getEmployees() {
        return empService.getAllEmployee();
    }

    public Employee getEmployee() {
        if (employee.getCredential() == null) {
            employee.setCredential(new Credential());
        }
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getSupervisorUsername() {
        return supervisorUsername;
    }

    public void setSupervisorUsername(String supervisorUsername) {
        this.supervisorUsername = supervisorUsername;
    }

    /**
     * Add new Employee to database.
     */
    public String addEmployee() {
        // find and set supervisor
        final Employee supervisor = empService.findEmployeeByUsername(supervisorUsername);
        if (supervisor == null) {
            FacesContext.getCurrentInstance().addMessage("newEmployeeForm:mnSupervisor",
                    new FacesMessage("No employee found with username: " + supervisorUsername));
            return null;
        } else {
            employee.setSupervisor(supervisor);
        }

        initializeNewEmployee();

        empService.persistEmployee(employee);
        logger.info("successfully create new employee: " + employee.toString());
        return "viewAllEmployees";
    }

    private Employee initializeNewEmployee() {
        employee.setTimesheetApprover(employee.getSupervisor());
        employee.setActiveStatus(true);
        employee.setFlexTimeBanked(0);
        employee.setVacationBanked(0);

        return employee;
    }

}
