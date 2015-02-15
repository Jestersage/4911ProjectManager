package ca.bcit.info.pms.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.model.Credential;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.bcit.info.pms.service.EmployeeService;

@Named("userController")
@SessionScoped	
public class UserController implements Serializable {
	
	@Inject
	private EmployeeService empService;
	
	@Inject
	private Credential credential;
	
	private static final Logger logger = LogManager.getLogger(UserController.class);

	public Credential getCredential() {
		return credential;
	}

	public void setCredential(Credential credential) {
		this.credential = credential;
	}

	public String login(){
		boolean isCorrect = empService.checkCredentials(credential);
	
		if(isCorrect){
			logger.info("Login Success");
			return "success";
		}

        logger.info("Login Failed");
		return null;
	}

	public String logout(){
		throw new UnsupportedOperationException();
	}
	
	public String  changePassword(){
        throw new UnsupportedOperationException();
	}

}

