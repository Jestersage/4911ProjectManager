package ca.bcit.info.pms.controller;

import java.io.Serializable;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.access.SignatureManager;
import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.SignatureObject;
import ca.bcit.info.pms.model.Timesheet;
import ca.bcit.info.pms.service.TimesheetService;

import org.apache.log4j.Level;
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
    
    @Inject
    private EditTimesheetController editTimesheetController;
    
    @Inject private SignatureManager signatureManager;
    
    @Inject private SignatureObject sigObject;
    
    private int id;
    
    private boolean isVerified;

    private List<Timesheet> tsApproverPendingList;

    private List<Timesheet> previousSheets;

    private List<Timesheet> pendingsSheets;

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

    public List<Timesheet> getPendingsSheets() {
        if (pendingsSheets == null) {
            final String currUserid = userController.getUser().getId();
            pendingsSheets = timeService.findPendingTimesheetsByOwner(currUserid);
        }
        return pendingsSheets;
    }

    public String reviewTimesheet(final Timesheet ts) {
        timesheet = ts;
        editTimesheetController.setTimesheet(ts);
        
        return "reviewTimesheet";
    }

    public String viewPendingTimesheet(final Timesheet ts) {
        timesheet = ts;
        return "viewPendingTimesheet";
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
        timesheet.setSubmitted(false);
        signatureManager.remove(signatureManager.find(timesheet.getId()));

        timeService.updateTimesheet(timesheet);

        return "pendingTimesheetTA";
    }
    
    public byte[] getSignature() {
		return sigObject.getSignature();
	}
	
	public byte[] getPublicKey() {
		return sigObject.getPublicKey();
	}
	
	public boolean getIsVerified() {
		return isVerified;
	}
    
}
