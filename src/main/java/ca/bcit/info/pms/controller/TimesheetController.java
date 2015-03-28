package ca.bcit.info.pms.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.Timesheet;
import ca.bcit.info.pms.model.TimesheetRow;
import ca.bcit.info.pms.service.TimesheetService;

@Named( "timesheetController" )
@SessionScoped
public class TimesheetController implements Serializable {

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
    
    /**
     * Populates timesheet data-member for current user.
     * 
     * @return navigation string
     */
    public String fillThisWeek() {
        Employee user = userController.getUser();
        
        
        // Get current week end date
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        Date weekEnd = c.getTime(); 
        
        List<Timesheet> sheets = timeService.findTimesheetsByOwner(user);
        
        if( sheets == null ) {
            timesheet = new Timesheet();
            List<TimesheetRow> tsRows = new ArrayList<TimesheetRow>();
            tsRows.add(new TimesheetRow());
            timesheet.setTimesheetRows(tsRows);
            timesheet.setOwner(user);
        } else {
            for( Timesheet t : sheets ) {
                // If there is a timesheet for this week
                if (t.getWeekEnding() == weekEnd) {
                    // Set it
                    timesheet = t;
                } else {
                    // Otherwise create a new timesheet
                    timesheet = new Timesheet();
                    List<TimesheetRow> tsRows = new ArrayList<TimesheetRow>();
                    tsRows.add(new TimesheetRow());
                    timesheet.setTimesheetRows(tsRows);
                    timesheet.setOwner(user);
                }
            }
        }
        
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
}
