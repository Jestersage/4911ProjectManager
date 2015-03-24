package ca.bcit.info.pms.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.access.TimesheetManager;
import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.Timesheet;
import ca.bcit.info.pms.service.TimesheetService;

@Named( "TimesheetService" )
public class TimesheetServiceImpl implements Serializable, TimesheetService{
    
    @Inject
    private TimesheetManager timesheetManager;

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
}
