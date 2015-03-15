package ca.bcit.info.pms.controller;

import ca.bcit.info.pms.model.Credential;
import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.PayLevel;
import ca.bcit.info.pms.model.PayLevel.PayGrade;
import ca.bcit.info.pms.service.EmployeeService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Named("empController")
@RequestScoped
public class EmployeeController implements Serializable {

    @Inject
    private EmployeeService empService;

    /** Hold the new employee information. */
    @Inject
    private Employee employee;

    /** Hold info about new employee's supervisor username from and to form. */
    private String supervisorUsername;

    private static final Logger logger = LogManager.getLogger(EmployeeController.class);

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
        employee.setFlexTimeBanked(new BigDecimal(0));
        employee.setVacationBanked(new BigDecimal(0));

        return employee;
    }

    public String viewEmployee(final Employee emp) {
        employee = emp;
        return "viewEmployee";
    }

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

    public String getEmpAuthorizations() {
        final Map<String, Boolean> authMap = empService.checkAuthorization(employee.getId());
        return empService.getAvailableAuths(authMap);
    }
}
