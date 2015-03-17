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

    /** Hold info about new employee's timesheet approver username from and to form. */
    private String tsApproverUsername;

    private static final Logger logger = LogManager.getLogger(EmployeeController.class);

    /**
     * Add new Employee to database.
     */
    public String addEmployee() {
        boolean foundSupervisor = addEmpSupervisor();
        // return to the form to correct information
        if (!foundSupervisor) {
            return null;
        }

        initializeNewEmployee();

        empService.persistEmployee(employee);
        logger.info("successfully create new employee: " + employee.toString());

        return "viewEmployee";
    }

    public String updateEmployee() {
        boolean foundSupervisor = addEmpSupervisor();
        // return to the form to correct information
        if (!foundSupervisor) {
            return null;
        }

        boolean foundApprover = addEmpTimesheetApprover();
        if (!foundApprover) {
            return null;
        }

        empService.updateEmployee(employee);
        logger.info("successfully updated employee: " + employee.toString());

        return "viewEmployee";
    }

    private Employee initializeNewEmployee() {
        employee.setTimesheetApprover(employee.getSupervisor());
        employee.setActiveStatus(true);
        employee.setFlexTimeBanked(new BigDecimal(0));
        employee.setVacationBanked(new BigDecimal(0));

        return employee;
    }

    /**
     * find employee with username = supervisorUsername, and set it as employee's supervisor.
     * @return if supervisor was found
     */
    private boolean addEmpSupervisor() {
        if (supervisorUsername == null || supervisorUsername.isEmpty()) {
            return true; // no supervisor assigned, no need to search
        }

        boolean found = false;
        final Employee supervisor = empService.findEmployeeByUsername(supervisorUsername);
        if (supervisor == null) {
            FacesContext.getCurrentInstance().addMessage("employeeForm:mnSupervisor",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "No employee found with username: " + supervisorUsername));
        } else {
            employee.setSupervisor(supervisor);
            found = true;
        }

        return found;
    }

    private boolean addEmpTimesheetApprover() {
        boolean found = false;

        if (tsApproverUsername == null || tsApproverUsername.isEmpty()) {
            return true; // no supervisor assigned, no need to search
        }
        else if (tsApproverUsername.equals(supervisorUsername)) {
            employee.setTimesheetApprover(employee.getSupervisor());
            found = true;
        }
        else {
            final Employee approver = empService.findEmployeeByUsername(tsApproverUsername);
            if (approver == null) {
                FacesContext.getCurrentInstance().addMessage("employeeForm:mnTimesheetApprover",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                "No employee found with username: " + tsApproverUsername));
            } else {
                employee.setTimesheetApprover(approver);
                found = true;
            }
        }

        return found;
    }

    /**
     * View an employee profile detail.
     * @param emp employee to view
     * @return navigation view-id
     */
    public String viewEmployee(final Employee emp) {
        employee = emp;
        return "viewEmployee";
    }

    /**
     * Edit an employee's profile
     * @param emp employee to edit
     * @return navigation view-id
     */
    public String editEmployee(final Employee emp) {
        employee = emp;
        logger.info("before edit: " + employee);
        return "editEmp";
    }

    /**
     * Edit an employee's profile.
     * NOTE: somehow from viewEmployee.xhtml to edit, will loose
     * employee object before it hit action method.
     * @param empId employee id of the employee to edit
     * @return navigation view-id
     */
    public String editEmployee(final String empId) {
        employee = empService.findEmployeeById(empId);
        return "editEmp";
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
        final Employee supervisor = employee.getSupervisor();
        if (supervisor != null) {
            supervisorUsername = supervisor.getCredential().getUsername();
        }

        return supervisorUsername;
    }

    public void setSupervisorUsername(String username) {
        this.supervisorUsername = username;
    }

    public String getTsApproverUsername() {
        final Employee approver = employee.getTimesheetApprover();
        if (approver != null) {
            tsApproverUsername = approver.getCredential().getUsername();
        }
        return tsApproverUsername;
    }

    public void setTsApproverUsername(String username) {
        this.tsApproverUsername = username;
    }

    public String getEmpAuthorizations() {
        final Map<String, Boolean> authMap = empService.checkAuthorization(employee.getId());
        return empService.getAvailableAuths(authMap);
    }
}
