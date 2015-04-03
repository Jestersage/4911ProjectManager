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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.bcit.info.pms.access.SignatureManager;
import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.SignatureObject;
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
    private TimesheetController timesheetController;
    
    @Inject
    private WorkPackageService wpService;

    @Inject
    private SignatureManager signatureManager;
    
    @Inject private SignatureObject sigObject;
    
    boolean isValid = true;

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

        // if timeheet already submitted.
        if ((timesheet.getSubmitted() != null) && (timesheet.getSubmitted() == true)) {

            timesheetController.viewTimesheet(timesheet);
            return "/timesheets/viewTimesheet";
        }

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
        WorkPackage wp = wpService.findWorkPackageById(tsr.getWorkPackage().getId());
        tsr.setWorkPackage(wp);
    }

    /**
     * Persists the timesheet, or sets error message
     * if the timesheet is not valid.
     * @return
     */
    public String saveTimesheet() {
    	isValid = true;
        FacesMessage msg = null;
        List<TimesheetRow> rows = removeEmptyRows(timesheet.getTimesheetRows());
        timesheet.setTimesheetRows(rows);
        
        // A user CAN save an empty timesheet
        if(rows.size() == 0) {
            timeService.updateTimesheet(timesheet);
            return null;
        }
        
        try {
        	if((timesheet.getSubmitted()) == true && (signatureManager.find(timesheet.getId()) != null)) {
                isValid = false;
                msg = new FacesMessage("Already signed and submitted timesheet. Cannot edit");
            }
        } catch (NullPointerException e) {
        	
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

        
        if( !sheetTotalsLessThanForty() ) {
        	isValid = false;
            return null;
        }
        

        if(isValid){
        	System.out.println("Saving timesheet: " + timesheet);
            timeService.updateTimesheet(timesheet);
        } else {
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        return null;
    }
    
    public String signTimesheet() { 	
    	try {
	        timesheet.setSubmitted(true); //Submits the timesheet
	        timesheet.setApproved(null); //reset approved status (in case timesheet as rejected)
	        
	        saveTimesheet();
	        isValid = sheetTotalsEqualForty();
	        
	        if(isValid == false) {
	        	return null;
	        }
    		
			//Setting up key generator
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(1024, random);
			
			//Generating a keypair
			KeyPair pair = keyGen.generateKeyPair();
			PrivateKey priv = pair.getPrivate();
			PublicKey pub = pair.getPublic();
			
			Signature dsa = Signature.getInstance("SHA1withDSA", "SUN"); //signature object to generate signature
			dsa.initSign(priv); //signing signature with private key

            if (timeService.getCurrentWeekEnd().equals(timesheet.getWeekEnding())) {
                Employee user = userController.getUser();
                timesheet = timeService.getCurrentTimesheet(user);
            }

			String data = timesheet.toString();
			System.out.println("Signing: " + data);

			byte[] dataBytes = new byte[1024]; //Creating a byte array to store the data
			dataBytes = data.getBytes(); //Convert the data from string to bytes
			
			dsa.update(dataBytes); //Update the signature object with the data
			
			byte[] realSig = dsa.sign(); //Create signature

			byte[] key = pub.getEncoded(); //Convert the public key into a byte array
			
			sigObject = new SignatureObject(realSig, key); //Create the signature object model

			sigObject.setId(timesheet.getId());
			signatureManager.persist(sigObject); //Persist the newly created model into the database
		} catch(Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e);
			return null;
		} 

    	return "mypage";
    }
    
    public boolean verifyTimesheet() {
    	try {
    		Employee user = userController.getUser();

	        timesheet = timeService.getCurrentTimesheet(user);
			
			String data = timesheet.toString();
			System.out.println("Verifying: " + data);
			
			SignatureObject sigObject = signatureManager.find(timesheet.getId()); //Retrieve signature model

			byte[] encKey = sigObject.getPublicKey(); //Retrieve public key
			
			//Decode the public key
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
			KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
			PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
			
			byte[] sigToVerify = sigObject.getSignature(); //Retrieve signature
			
			Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
			sig.initVerify(pubKey); //Initialize verification with decoded public key			

			byte[] dataBytes = data.getBytes(); //Retrieve data
			sig.update(dataBytes); //Update signature with data

			return sig.verify(sigToVerify); //Verify whether signature is valid
			
		} catch (Exception e) {
			System.err.println("Caught exception " + e.toString());
		}
    	
    	return false;
    }
    
    /**
     * Returns false if the 'Row Total - (FLEX + OT) > 40'
     * 
     * @return          true if the total is valid
     */
    public boolean sheetTotalsLessThanForty() {
        // Ensure timesheet is not null
        if (timesheet != null) {
            double netTotal = timesheet.getTotal();
            double grossTotal = netTotal - (timesheet.getFlextime().doubleValue() 
                    + timesheet.getOvertime().doubleValue());

            if (grossTotal > 40.0) {
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
     * Returns false if the 'Row Total - (FLEX + OT) != 40'
     * 
     * @return          true if the total is valid
     */
    public boolean sheetTotalsEqualForty() {
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

    /**
     * Removes the row with the workpackage id corresponding
     * to the parameter's work package id, from the timesheet.
     * 
     * @param row   The row whose workpackage id is checked against.
     */
    public void removeRowFromTimesheet(TimesheetRow row) {
        List<TimesheetRow> rows = new ArrayList<TimesheetRow>();
        
        for(TimesheetRow tsr : timesheet.getTimesheetRows()) {
            if( tsr.getWorkPackage().getId() != row.getWorkPackage().getId()) {
                logger.info("\n\tADDED:"+tsr);
                rows.add(tsr);
            }
        }
        
        timesheet.setTimesheetRows(rows);
    }

    public Date getWeekEnding() {
        return timesheet.getWeekEnding();
    }

    public int getWeekNumber() {
        return timesheet.getWeekNumber();
    }
}
