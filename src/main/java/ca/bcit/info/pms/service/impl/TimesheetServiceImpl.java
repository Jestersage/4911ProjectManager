package ca.bcit.info.pms.service.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.access.TimesheetManager;
import ca.bcit.info.pms.controller.UserController;
import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.Timesheet;
import ca.bcit.info.pms.service.TimesheetService;

@Named( "TimesheetService" )
public class TimesheetServiceImpl implements Serializable, TimesheetService{
    
    @Inject
    private TimesheetManager timesheetManager;

    @Override
    public Timesheet getCurrentTimesheet(Employee emp) {
       
        // Get current week end date
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        Date wkEnding = c.getTime(); 
        
        return timesheetManager.find(emp, wkEnding);
    }
    
    @Override
    public void persistTimesheet(Timesheet newTimesheet) {
        timesheetManager.persist(newTimesheet);        
    }

    @Override
    public void updateTimesheet(Timesheet timesheet) {
        timesheetManager.merge(timesheet);
    }

    @Override
    public List<Timesheet> getAllTimesheets() {
        return timesheetManager.getAllTimesheets();
    }

    @Override
    public Timesheet findTimesheetById(int id) {
        return timesheetManager.findById(id);
    }

    @Override
    public List<Timesheet> findTimesheetsByOwner(Employee owner) {
        return timesheetManager.findAllByOwner(owner);
    }

    @Override
    public List<Timesheet> getApproverPendingTimesheets(String empId) {
        return timesheetManager.getTimesheetsToApprove(empId);
    }
}
