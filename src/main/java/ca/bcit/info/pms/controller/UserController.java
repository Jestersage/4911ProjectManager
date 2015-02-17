package ca.bcit.info.pms.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
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
			return "loginSuccess";
		}

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("passwordForm",
                new FacesMessage("Your username and password didn't match. Try again."));
        context.getExternalContext().invalidateSession();
        logger.info("Login Failed");
		return "loginFail";
	}

    public void checkPermissions(ComponentSystemEvent event) {
        boolean isAuthorized = false;

        if (credential != null && credential.getUsername() != null) {
            isAuthorized = true;
        }

        if (!isAuthorized) {
            final FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("passwordForm",
                    new FacesMessage("Please login to access the page."));

            context.getApplication().getNavigationHandler().
                    handleNavigation(context, null, "unauthorized");

            logger.info("unauthorized access");
            return;
        }
    }

	public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "logout";
	}
	
	public String  changePassword(){
        throw new UnsupportedOperationException();
	}

}

