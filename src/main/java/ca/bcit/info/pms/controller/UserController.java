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

    private String newCredential;
    private String currentPassword;
    private String confirmCredential;

    private static final Logger logger = LogManager.getLogger(UserController.class);

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewCredential() {
        return newCredential;
    }

    public void setNewCredential(String newCredential) {
        this.newCredential = newCredential;
    }

    public String getConfirmCredential() {
        return confirmCredential;
    }

    public void setConfirmCredential(String confirmCredential) {
        this.confirmCredential = confirmCredential;
    }


    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public String login() {
        boolean isCorrect = empService.checkCredentials(credential);

        if (isCorrect) {
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

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "logout";
    }

    public String changePassword() {
        if (currentPassword.equals(credential.getPassword())) {
            if (newCredential.equals(confirmCredential)) {
                credential.setPassword(newCredential);
                empService.updateCreden(credential);
                credential = new Credential();
            }
        }

        return null;
    }

}

