package ca.bcit.info.pms.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.model.Timesheet;
import ca.bcit.info.pms.service.TimesheetService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Named( "timesheetController" )
@RequestScoped
public class TimesheetController implements Serializable {

    @Inject
    private TimesheetService timeService;
    
    @Inject
    private UserController userController;

    @Inject
    private Timesheet timesheet;

    private List<Timesheet> tsApproverPendingList;

    private List<Timesheet> previousSheets;

    private static final Logger logger = LogManager.getLogger(TimesheetController.class);

    /**
     * @return Return timesheet
     */
    public Timesheet getTimesheet(){
        return timesheet;
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
        if (tsApproverPendingList == null) {
            final String currUserid = userController.getUser().getId();
            tsApproverPendingList = timeService.getApproverPendingTimesheets(currUserid);
        }
        return tsApproverPendingList;
    }

    public List<Timesheet> getPreviousSheets() {
        if (previousSheets == null) {
            final String currUserid = userController.getUser().getId();
            previousSheets = timeService.findApprovedTimesheetsByOwner(currUserid);
        }
        return previousSheets;
    }

    public String reviewTimesheet(final Timesheet ts) {
        timesheet = ts;
        return "reviewTimesheet";
    }

    public String viewTimesheet(final Timesheet ts) {
        timesheet = ts;
        return "viewTimesheet";
    }

    public String approveTimesheet() {
        timesheet = timeService.findTimesheetById(timesheet.getId());
        timesheet.setApproved(true);

        timeService.updateTimesheet(timesheet);

        return "pendingTimesheetTA";
    }

    public String rejectTimesheet() {
        timesheet = timeService.findTimesheetById(timesheet.getId());
        timesheet.setApproved(false);

        timeService.updateTimesheet(timesheet);

        return "pendingTimesheetTA";
    }
}
