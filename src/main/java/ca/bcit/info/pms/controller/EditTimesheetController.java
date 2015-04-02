package ca.bcit.info.pms.controller;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.Timesheet;
import ca.bcit.info.pms.model.TimesheetRow;
import ca.bcit.info.pms.model.WorkPackage;
import ca.bcit.info.pms.service.TimesheetService;
import ca.bcit.info.pms.service.WorkPackageService;

@Named( "editTimesheetController" )
@SessionScoped
public class EditTimesheetController implements Serializable {
    private static final Logger logger 
        = LogManager.getLogger(EditTimesheetController.class);

    @Inject
    private Timesheet timesheet;

    @Inject
    private TimesheetService timeService;

    @Inject
    private UserController userController;
    
    @Inject
    private WorkPackageService wpService;

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

    public String fillSheetToCorrect(final Timesheet ts) {
        timesheet = ts;

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
    
    public void populateWorkPackage(TimesheetRow tsr) {
        logger.info("\n\tPRE-populateWorkPackage.tsr::"+tsr);
        logger.info("\n\tPRE-populateWorkPackage.tsr.wp::"+tsr.getWorkPackage());
        
        WorkPackage wp = wpService.findWorkPackageById(tsr.getWorkPackage().getId());
        tsr.setWorkPackage(wp);
        

        logger.info("\n\tPOST-populateWorkPackage.tsr::"+tsr);
        logger.info("\n\tPOST-populateWorkPackage.tsr.wp::"+tsr.getWorkPackage());  
    }

    /**
     * Persists the timesheet, or sets error message
     * if the timesheet is not valid.
     * @return
     */
    public String saveTimesheet() {
        boolean isValid = true;
        FacesMessage msg = null;
        List<TimesheetRow> rows = removeEmptyRows(timesheet.getTimesheetRows());
        timesheet.setTimesheetRows(rows);
        
        // TODO Remove : Debug info
        for (TimesheetRow row : timesheet.getTimesheetRows()) {
            logger.info("\n\tTimesheet.TSR.WorkPackage::"+row.getWorkPackage());
            logger.info("\n\tTimesheet.TSR.WorkPackage.ID::"+row.getWorkPackage().getId());
        }

        // A user CAN save an empty timesheet
        if(rows.size() == 0) {
            timeService.updateTimesheet(timesheet);
            return null;
        }

        if(!noWorkPackagesAreNull(rows) && isValid) {
            isValid = false;
            msg = new FacesMessage("Each row must be associated with a work package");
        }

        if (!dayTotalsAreValid(rows) && isValid){
            isValid = false;
            msg = new FacesMessage("Total hours on individual day cannot exceed 24.00 hours");
        }

        if (!rowWorkPackagesAreUnique(rows) && isValid) {
            isValid = false;
            msg = new FacesMessage("Work Packages must be unique among rows");
        }

        
        if( !sheetTotalsAreValid() ) {
            return null;
        }
        

        if(isValid){
            timeService.updateTimesheet(timesheet);
        } else {
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        return null;
    }

    /**
     * Returns false if the 'Row Total - (FLEX + OT) > 40'
     * 
     * @return          true if the total is valid
     */
    public boolean sheetTotalsAreValid() {
        // Ensure timesheet is not null
        if (timesheet != null) {
            double netTotal = timesheet.getTotal();
            double grossTotal = netTotal - (timesheet.getFlextime().doubleValue() 
                    + timesheet.getOvertime().doubleValue());

            if (grossTotal != 40.0) {
                FacesMessage msg = new FacesMessage(
                        "Total person-hours in week must be exactly equal to 40.00\n"
                                + "Where person-hours = Total - (FLEX + OT)");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            }
            return true;
        } else {
            // Return false if timesheet is null
            logger.info("sheetTotalsAreVald()::Timesheet is null");
            return false;
        }
    }

    /**
     * Returns true if the total of every day (across rows)
     * is less than or equal to 24.0
     * @param rows      The rows the check the total for each day.
     * @return
     */
    private boolean dayTotalsAreValid(List<TimesheetRow> rows) {
        double[] dayTotals = new double[7];

        for(TimesheetRow row : rows) {
            dayTotals[0] += row.getSundayHour();
            dayTotals[1] += row.getMondayHour();
            dayTotals[2] += row.getTuesdayHour();
            dayTotals[3] += row.getWednesdayHour();
            dayTotals[4] += row.getThursdayHour();
            dayTotals[5] += row.getFridayHour();
            dayTotals[6] += row.getSaturdayHour();
        }

        for(int i = 0; i < dayTotals.length; i++) {
            if (dayTotals[i] > 24.0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Removes rows that do not have any hours entered.
     * 
     * @param rows  List of timesheetRows (not-trimmed)
     * @return      List of timesheetRows with hours > 0
     */
    private List<TimesheetRow> removeEmptyRows(List<TimesheetRow> rows) {
        List<TimesheetRow> trimmed = new ArrayList<TimesheetRow>();  
        for(TimesheetRow row : rows) {
            if(row.getTotalHours() > 0.0) {
                trimmed.add(row);
            }
        }
        return trimmed;
    }

    /**
     * Returns true if no work package id's are null.
     * 
     * @param rows      List of TimesheetRows for current user timesheet
     * @return          True if NO work package id's are null
     */
    private boolean noWorkPackagesAreNull(List<TimesheetRow> rows) {
        for(TimesheetRow row : rows) {
            if(row.getWorkPackage().getId() == null) {
                return false;
            }
        }  
        return true;
    }

    /**
     * Returns true if the Work Packages are unique for the timesheet rows
     * 
     * @param rows      The timesheet rows to be checked
     * @return
     */
    private boolean rowWorkPackagesAreUnique(List<TimesheetRow> rows) {
        if(rows.size() == 1) {
            if( rows.get(0).getWorkPackage().getId() == null ) {
                return false;
            }
        } else {
            TimesheetRow rowOne, rowTwo;
            final int size = rows.size();

            for(int i = 0; i < (size - 1 ); i++) {
                rowOne = rows.get(i);
                if(rowOne.getWorkPackage() == null)  {
                    return false;
                }
                for(int j = i + 1; j < size; j++) {
                    rowTwo = rows.get(j);
                    if(rowTwo.getWorkPackage().getId() == null) {
                        return false;
                    }
                    if(rowOne.getWorkPackage().getId() == rowTwo.getWorkPackage().getId()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public void removeRowFromTimesheet(TimesheetRow row) {
        logger.info("\t\nremoveRowFromTimesheet.row::"+row);
        logger.info("\t\nremoveRowFromTimesheet.row.workPackage::"+row.getWorkPackage());
        logger.info("\t\nremoveRowFromTimesheet.row.workPackage.id::"+row.getWorkPackage().getId());
        
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
