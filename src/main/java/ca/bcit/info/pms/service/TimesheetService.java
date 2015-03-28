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
     * Find employee by username.
     * @param owner employee username
     * @return employee or null
     */
    List<Timesheet> findTimesheetsByOwner(final Employee owner);

    List<Timesheet> getApproverPendingTimesheets(final String empId);
}
