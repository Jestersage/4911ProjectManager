package ca.bcit.info.pms.controller;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.Timesheet;
import ca.bcit.info.pms.model.TimesheetRow;
import ca.bcit.info.pms.model.WorkPackage;
import ca.bcit.info.pms.service.TimesheetService;

@Named( "editTimesheetController" )
@SessionScoped
public class editTimesheetController implements Serializable {
    
    private static final Logger logger = LogManager.getLogger(editTimesheetController.class);

    @Inject
    private Timesheet timesheet;
    
    @Inject
    private TimesheetService timeService;
    
    @Inject
    private UserController userController;

    /**
     * @return Return timesheet
     */
    public Timesheet getTimesheet(){
        return timesheet;
    }
    
    public void addTimesheetRow() {
        List<TimesheetRow> tsRows = timesheet.getTimesheetRows();
        tsRows.add(newTimesheetRow());
        timesheet.setTimesheetRows(tsRows);
    }
    
    public TimesheetRow newTimesheetRow() {
        TimesheetRow tsr = new TimesheetRow();
        WorkPackage wp = new WorkPackage();
        tsr.setWorkPackage(wp);
        tsr.setFridayHour(0);
        tsr.setSaturdayHour(0);
        tsr.setSundayHour(0);
        tsr.setMondayHour(0);
        tsr.setTuesdayHour(0);
        tsr.setWednesdayHour(0);
        tsr.setThursdayHour(0);
        return tsr;
    }
    /**
     * Populates timesheet data-member for current user.
     * 
     * @return navigation string
     */
    public String fillThisWeek() {
        Employee user = userController.getUser();
        
        timesheet = timeService.getCurrentTimesheet(user);
                
        return "currentTimesheet";
    }

    /**
     * @param timesheet the timesheet to set
     */
    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }

    public String addTimesheet(){
        //set functions
        //persist timesheet
        return "newTimesheet";
    }

    public List<Timesheet> getTsApproverPendingList() {
        final String currUserid = userController.getUser().getId();
        return timeService.getApproverPendingTimesheets(currUserid);
    }
    
    public String saveTimesheet() {
        List<TimesheetRow> validRows = new ArrayList<TimesheetRow>();
        
        for(TimesheetRow row : timesheet.getTimesheetRows()) {
            if ( (row.getTotalHours() > 0) && (row.getWorkPackage().getId() != null) ) {
                validRows.add(row);
            }
        }
        timesheet.setTimesheetRows(validRows);
        timeService.updateTimesheet(timesheet);
        
        return "currentTimesheet";
    }
    
    // TODO Implement correct logic
    public double getTotalHours() {
        double total = 0;
        
        List<TimesheetRow> rows = timesheet.getTimesheetRows();
        
        if ( rows != null ) {
            for (TimesheetRow tsr : rows) {
                total += tsr.getTotalHours();
            }
        }
        
        return total;
    }
    
    public void removeRowFromTimesheet(TimesheetRow row) {
        List<TimesheetRow> rows = timesheet.getTimesheetRows();
        
        rows.remove(row);
        
        timesheet.setTimesheetRows(rows);
    }
    
    public Date getWeekEnding() {
        return timesheet.getWeekEnding();
    }
    
    public int getWeekNumber() {
        return timesheet.getWeekNumber();
    }
}
