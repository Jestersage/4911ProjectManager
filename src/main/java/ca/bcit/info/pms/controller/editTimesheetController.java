package ca.bcit.info.pms.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.Timesheet;
import ca.bcit.info.pms.service.TimesheetService;

@Named( "editTimesheetController" )
@SessionScoped
public class editTimesheetController implements Serializable {

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
}
