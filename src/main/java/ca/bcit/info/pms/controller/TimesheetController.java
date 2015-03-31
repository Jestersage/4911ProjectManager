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
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.access.SignatureManager;
import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.SignatureObject;
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
        
        try {
			
			String data = timesheet.toString();
			
			sigObject = signatureManager.find(timesheet.getId()); //Retrieve signature model

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
			
			isVerified = sig.verify(sigToVerify); //Verify whether signature is valid
			
		} catch (Exception e) {
			System.err.println("Caught exception " + e.toString());
		}
        return "reviewTimesheet";
    }

    public String viewPendingTimesheet(final Timesheet ts) {
        timesheet = ts;
        return "viewPendingTimesheet";
    }

    public String viewTimesheet(final Timesheet ts) {
        timesheet = ts;
        reviewTimesheet(timesheet);
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
    
    public String signTimesheet() {
    	try {
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
			
			String data = timesheet.toString();
			byte[] dataBytes = new byte[1024]; //Creating a byte array to store the data
			dataBytes = data.getBytes(); //Convert the data from string to bytes
			
			dsa.update(dataBytes); //Update the signature object with the data
			
			byte[] realSig = dsa.sign(); //Create signature

			byte[] key = pub.getEncoded(); //Convert the public key into a byte array
			
			sigObject = new SignatureObject(realSig, key); //Create the signature object model
			
			Employee user = userController.getUser();
	        
	        timesheet = timeService.getCurrentTimesheet(user); 
	        
	        timesheet.setSubmitted(true); //Submits the timesheet
	        
			sigObject.setId(timesheet.getId());
			signatureManager.persist(sigObject); //Persist the newly created model into the database
			
			//id = sigObject.getId(); //Grab the id for testing purposes. This should match timesheet id?
			
			
		} catch(Exception e) {
			System.err.println("Caught exception " + e.toString());
			return "currentTimesheet";
		}
    	
    	return "mypage";
    }
}
