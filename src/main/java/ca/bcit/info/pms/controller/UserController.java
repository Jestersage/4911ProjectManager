package ca.bcit.info.pms.controller;

import ca.bcit.info.pms.model.Credential;
import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.service.EmployeeService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import java.util.StringTokenizer;

@Named( "userController" )
@SessionScoped
public class UserController implements Serializable {

    @Inject
    private EmployeeService empService;

    @Inject
    private Credential credential;

    @Inject
    private Employee user;

    private Map<String, Boolean> authorizations;

    private String newCredential;
    private String currentPassword;
    private String confirmCredential;

    private static final Logger logger = LogManager.getLogger(UserController.class);

    /**
     * Verify login credential.
     * If credential valid, initialize user, check user authorizations.
     * @return navigation view-id
     */
    public String login() {
        final boolean isCorrect = empService.checkCredentials(credential);
        String returnPath;

        if (isCorrect) {
            user = empService.findEmployeeByUsername(credential.getUsername());
            authorizations = empService.checkAuthorization( user.getId() );

            returnPath = "loginSuccess";
            logger.info("Login Success");
        }
        else  {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Your username and password didn't match. Try again. ", null));
            context.getExternalContext().invalidateSession();

            returnPath = "loginFail";
            logger.info("Login Failed");
        }

        return returnPath;
    }

    /**
     * PreRender event listener, prevent unauthenticated access to pages.
     * If not authenticated, redirect to login page and show error message.
     * @param event
     */
    public void checkAuthentication(ComponentSystemEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();

        if (!isAuthenticated()) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Please login to access the page.", null));

            context.getApplication().getNavigationHandler().
                    handleNavigation(context, null, "unauthenticated");

            logger.info("unauthenticated access");
        }
    }


    /**
     * @return if current user is authenticated (has login).
     */
    private boolean isAuthenticated() {
        boolean isAuthenticated = false;

        if (user != null && user.getId() != null) {
            isAuthenticated = true;
        }

        return isAuthenticated;
    }

    public void checkAuthorization(ComponentSystemEvent event) {
        if(!isAuthenticated()) {
            return; // no need to check HR permission if not authenticated
        }

        final FacesContext ctx = FacesContext.getCurrentInstance();
        final String roles = (String) event.getComponent().getAttributes().get("requiredRoles");

        if (!isAuthorized(roles)) {
            ctx.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "You don't have authorization to access " + ctx.getViewRoot().getViewId(), null));

            ctx.getApplication().getNavigationHandler().
                    handleNavigation(ctx, null, "unauthorized");

            logger.info("unauthorized access to HR functions.");
        }
    }

    /**
     * @param roles string of roles required for authorization, delimited by comma(,) .
     *              Role name must match ones defined in Employee class
     * @return if current user has required authorization
     */
    public boolean isAuthorized(final String roles) {
        boolean isAuthorized;
        final StringTokenizer tokenizer;
        String role;

        isAuthorized = false;
        tokenizer = new StringTokenizer(roles, ",");

        while (tokenizer.hasMoreTokens()) {
            role = tokenizer.nextToken();
            Boolean hasRole = authorizations.get(role);
            if (hasRole != null && hasRole) {
                isAuthorized = true;
                break;
            }
        }

        return isAuthorized;
    }

    /**
     * End current user session.
     * @return navigation view-id
     */
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "logout";
    }

    /**
     * Change current user's password.
     * @return navigation view-id
     */
    public String changePassword() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (currentPassword.equals(credential.getPassword())) {
            if (newCredential.equals(confirmCredential)) {
                credential.setPassword(newCredential);
                empService.updateCredential(credential);
                credential = new Credential();

                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Password changed successfully. Please login again.", null));
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

    public Employee getUser() {
        return user;
    }

    public void setUser(Employee user) {
        this.user = user;
    }

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

}
