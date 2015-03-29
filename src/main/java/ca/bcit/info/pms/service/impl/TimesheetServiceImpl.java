package ca.bcit.info.pms.service.impl;

import ca.bcit.info.pms.access.TimesheetManager;
import ca.bcit.info.pms.access.TimesheetRowManager;
import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.Timesheet;
import ca.bcit.info.pms.model.TimesheetRow;
import ca.bcit.info.pms.model.WorkPackage;
import ca.bcit.info.pms.service.TimesheetService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Named( "TimesheetService" )
public class TimesheetServiceImpl implements Serializable, TimesheetService{
    private static final Logger logger = LogManager.getLogger(TimesheetServiceImpl.class);
    @Inject
    private TimesheetManager timesheetManager;
    
    @Inject
    private TimesheetRowManager tsrManager;

    @Override
    public Timesheet getCurrentTimesheet(Employee emp) {
        // Get current week end date
        Calendar c = new GregorianCalendar();
        c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        Date wkEnding = new Date(c.getTime().getTime()); 

        Timesheet sheet = timesheetManager.find(emp, wkEnding);
        logger.info("wkEnding:"+wkEnding);
        logger.info("getCurrentTimesheet().sheet:"+sheet);
        if( sheet == null ) {
            sheet = createNewCurrentTimesheetForEmployee(emp, c);
        }
             
        return sheet;
    }

    @Override
    public Timesheet createNewCurrentTimesheetForEmployee(Employee emp, Calendar c) {
        Timesheet sheet = new Timesheet();
        Date wkEnding = new Date(c.getTime().getTime());
        sheet.setOwner(emp);
        sheet.setWeekEnding(wkEnding);        
        sheet.setWeekNumber(c.get(Calendar.WEEK_OF_YEAR));
        sheet.setFlextime(new BigDecimal(0));
        sheet.setOvertime(new BigDecimal(0));
        List<TimesheetRow> rows = new ArrayList<TimesheetRow>();
        for(WorkPackage wp : timesheetManager.getWorkPackagesByOwner(emp.getId())) {
            TimesheetRow tsr = new TimesheetRow();
            tsr.setProject(wp.getProject());
            tsr.setWorkPackage(wp);
            tsr.setMondayHour(0);
            tsr.setTuesdayHour(0);
            tsr.setWednesdayHour(0);
            tsr.setThursdayHour(0);
            tsr.setFridayHour(0);
            tsr.setSaturdayHour(0);
            tsr.setSundayHour(0);
            tsr.setNotes("");
            rows.add(tsr);
        }
        sheet.setTimesheetRows(rows);
        logger.info("newTimesheet().sheet:"+sheet.toString());
        timesheetManager.persist(sheet);
        return sheet;
    }

    @Override
    public void persistTimesheet(Timesheet newTimesheet) {
        timesheetManager.persist(newTimesheet);        
    }

    @Override
    public void updateTimesheet(Timesheet timesheet) {
        //tsrManager.merge(timesheet.getTimesheetRows());
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

    @Override
    public List<Timesheet> findPendingTimesheetsByOwner(String empId) {
        return timesheetManager.getAllPendingTimesheets(empId);
    }
}
