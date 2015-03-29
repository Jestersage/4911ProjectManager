package ca.bcit.info.pms.service;

import java.util.List;

import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.Timesheet;

public interface TimesheetService {
    
    Timesheet getCurrentTimesheet(Employee emp);

//      Timesheet CRUD
    /**
     * Add a new employee.
     * @param newTimesheet    timesheet information
     */
    void persistTimesheet(Timesheet newTimesheet);
    
    /**
     * Update existing employee.
     * @param timesheet timesheet to update.
     */
    void updateTimesheet(Timesheet timesheet);
    
    /**
     * @return a list of all timesheets.
     */
    List<Timesheet> getAllTimesheets();
    
//          Helper methods
    /**
     * Find timesheet by id.
     * @param id - timesheet id.
     * @return employee or null
     */
    Timesheet findTimesheetById(final int id);
    
    /**
     * @param owner employee
     * @return all timesheets of a owner
     */
    List<Timesheet> findTimesheetsByOwner(final Employee owner);

    /**
     * @param empId timesheet's owner's employee id
     * @return a list of all submitted and approved timesheets by this employee.
     */
    List<Timesheet> getApproverPendingTimesheets(final String empId);

    List<Timesheet> findApprovedTimesheetsByOwner(final String empId);
}
