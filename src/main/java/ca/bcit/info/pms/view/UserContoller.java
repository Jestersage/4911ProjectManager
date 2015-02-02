package ca.bcit.info.pms.view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.bcit.info.pms.model.Credentials;
import ca.bcit.info.pms.service.EmployeeService;

@Named("userContoller")
@SessionScoped	
public class UserContoller implements Serializable {
	
	@Inject
	private EmployeeService empService;
	
	@Inject
	private Credentials credential;
	
	private static final Logger logger = LogManager.getLogger(UserContoller.class);

	public Credentials getCredential() {
		return credential;
	}

	public void setCredential(Credentials credential) {
		this.credential = credential;
	}

	public String login(){
		boolean isCorrect = empService.checkCredentials(credential);
	
		if(isCorrect){
			logger.info("Success");
			return "success";
		}
		return "failed";
		
	}

	public String logout(){
		return null;
		
	}
	
	public String  changePassword(){
		return null;
		
	}

}

