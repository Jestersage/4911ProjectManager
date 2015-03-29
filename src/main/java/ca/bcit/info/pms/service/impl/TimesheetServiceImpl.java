package ca.bcit.info.pms.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.bcit.info.pms.access.ProjectManager;
import ca.bcit.info.pms.access.TimesheetManager;
import ca.bcit.info.pms.access.TimesheetRowManager;
import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.Timesheet;
import ca.bcit.info.pms.model.TimesheetRow;
import ca.bcit.info.pms.model.WorkPackage;
import ca.bcit.info.pms.service.TimesheetService;

@Named( "TimesheetService" )
public class TimesheetServiceImpl implements Serializable, TimesheetService{
    private static final Logger logger = LogManager.getLogger(TimesheetServiceImpl.class);
    @Inject
    private TimesheetManager timesheetManager;
    
    @Inject
    private TimesheetRowManager tsrManager;
    
    @Inject
    private ProjectManager projectManager;

    @Override
    public Timesheet getCurrentTimesheet(Employee emp) {
        // Get current week end date
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        java.sql.Date wkEnding = new java.sql.Date(c.getTime().getTime()); 

        Timesheet sheet = timesheetManager.find(emp, wkEnding);
        
        if( sheet == null) {
            sheet = new Timesheet();
            sheet.setOwner(emp);
            sheet.setWeekEnding(wkEnding);
            sheet.setWeekNumber(sheet.getWeekNumber());

            List<TimesheetRow> rows = new ArrayList<TimesheetRow>();
            for(WorkPackage wp : timesheetManager.getWorkPackages(emp)) {
                TimesheetRow tsr = new TimesheetRow();
                tsr.setProject(wp.getProject());
                tsr.setWorkPackage(wp);
                rows.add(tsr);
            }
        }
        
        return sheet;
    }
    
    @Override
    public void persistTimesheet(Timesheet newTimesheet) {
        timesheetManager.persist(newTimesheet);        
    }

    @Override
    public void updateTimesheet(Timesheet timesheet) {
        tsrManager.merge(timesheet.getTimesheetRows());
        timesheetManager.merge(timesheet);
    }

    @Override
    public List<Timesheet> getAllTimesheets() {
        return timesheetManager.getAllTimesheets();
    }

    @Override
    public Timesheet findTimesheetById(final int id) {
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

    @Override
    public List<Timesheet> findApprovedTimesheetsByOwner(String empId) {
        return timesheetManager.getAllApprovedTimesheets(empId);
    }
}
