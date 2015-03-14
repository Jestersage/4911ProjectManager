package ca.bcit.info.pms.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.model.Credential;

import ca.bcit.info.pms.model.Employee;
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

    @Inject
    private Employee user;

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
        final boolean isCorrect = empService.checkCredentials(credential);
        String returnPath;

        if (isCorrect) {
            user = empService.findEmployeeByUsername(credential.getUsername());

            returnPath = "loginSuccess";
            logger.info("Login Success");
        }
        else  {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("authForm",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Login",
                            "Your username and password didn't match. Try again."));
            context.getExternalContext().invalidateSession();

            returnPath = "loginFail";
            logger.info("Login Failed");
        }

        return returnPath;
    }

    public void checkAuthentication(ComponentSystemEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();

        if (!isAuthenticated()) {
            context.addMessage("authForm",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unauthenticated! ",
                            "Please login to access the page."));

            context.getApplication().getNavigationHandler().
                    handleNavigation(context, null, "unauthenticated");

            logger.info("unauthenticated access");
        }
    }


    private boolean isAuthenticated() {
        boolean isAuthenticated = false;

        if (credential != null && credential.getUsername() != null) {
            isAuthenticated = true;
        }

        return isAuthenticated;
    }

    public void checkHrPermission(ComponentSystemEvent event) {
        if(!isAuthenticated()) {
            return; // no need to check HR permission if not authenticated
        }

        final FacesContext ctx = FacesContext.getCurrentInstance();
        boolean isHr = empService.isRoleHr(user);

        if (!isHr) {
            ctx.addMessage("notifications",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unauthorized! ",
                            "You don't have authorization to access " + ctx.getViewRoot().getViewId()));

            ctx.getApplication().getNavigationHandler().
                    handleNavigation(ctx, null, "unauthorized");

            logger.info("unauthorized access to HR functions.");
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "logout";
    }

    public String changePassword() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (currentPassword.equals(credential.getPassword())) {
            if (newCredential.equals(confirmCredential)) {
                credential.setPassword(newCredential);
                empService.updateCredential(credential);
                credential = new Credential();

                context.addMessage("authForm",
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                                "Password changed. Please login again."));
            } else {
                context.addMessage("changePassForm:confirmNewPassword",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                "Make sure confirm password match your new password."));
            }
        } else {
            context.addMessage("changePassForm:currentPassword",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                            "You must enter your current password to change your password. This one doesn't match"));

        }

        return null;
    }

}

