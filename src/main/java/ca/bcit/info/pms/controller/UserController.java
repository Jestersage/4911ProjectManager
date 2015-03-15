package ca.bcit.info.pms.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.bcit.info.pms.model.Credential;
import ca.bcit.info.pms.service.EmployeeService;

@Named( "userController" )
@SessionScoped
public class UserController implements Serializable
{

	@Inject
	private EmployeeService empService;

	@Inject
	private Credential credential;

	private String authorization;

	private String newCredential;
	private String currentPassword;
	private String confirmCredential;

	private static final Logger logger = LogManager.getLogger( UserController.class );

	public String getCurrentPassword()
	{
		return currentPassword;
	}

	public void setCurrentPassword( String currentPassword )
	{
		this.currentPassword = currentPassword;
	}

	public String getNewCredential()
	{
		return newCredential;
	}

	public void setNewCredential( String newCredential )
	{
		this.newCredential = newCredential;
	}

	public String getConfirmCredential()
	{
		return confirmCredential;
	}

	public void setConfirmCredential( String confirmCredential )
	{
		this.confirmCredential = confirmCredential;
	}

	public Credential getCredential()
	{
		return credential;
	}

	public void setCredential( Credential credential )
	{
		this.credential = credential;
	}

	public String login()
	{
		boolean isCorrect = empService.checkCredentials( credential );

		if ( isCorrect )
		{
			authorization = authorize( credential.getUsername() );

			logger.info( "Login Success" );
			return "loginSuccess";
		}

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage( "authForm", new FacesMessage( FacesMessage.SEVERITY_ERROR,
		        "Invalid Login", "Your username and password didn't match. Try again." ) );
		context.getExternalContext().invalidateSession();
		logger.info( "Login Failed" );
		return "loginFail";
	}

	public void checkPermissions( ComponentSystemEvent event )
	{
		boolean isAuthorized = false;
		FacesContext context = FacesContext.getCurrentInstance();

		if ( credential != null && credential.getUsername() != null )
		{
			isAuthorized = true;
		}

		if ( !isAuthorized )
		{
			context.addMessage( "authForm", new FacesMessage(
			        FacesMessage.SEVERITY_ERROR, "Unauthorized! ",
			        "Please login to access the page." ) );

			context.getApplication().getNavigationHandler()
			        .handleNavigation( context, null, "unauthorized" );

			logger.info( "unauthorized access" );
		}
	}

	public String logout()
	{
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "logout";
	}

	public String changePassword()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		if ( currentPassword.equals( credential.getPassword() ) )
		{
			if ( newCredential.equals( confirmCredential ) )
			{
				credential.setPassword( newCredential );
				empService.updateCredential( credential );
				credential = new Credential();

				context.addMessage( "authForm", new FacesMessage(
				        FacesMessage.SEVERITY_INFO, "Success",
				        "Password changed. Please login again." ) );
			} else
			{
				context.addMessage( "changePassForm:confirmNewPassword",
				        new FacesMessage( FacesMessage.SEVERITY_ERROR, "Error",
				                "Make sure confirm password match your new password." ) );
			}
		} else
		{
			context.addMessage(
			        "changePassForm:currentPassword",
			        new FacesMessage( FacesMessage.SEVERITY_ERROR, "Error",
			                "You must enter your current password to change your password. This one doesn't match" ) );

		}

		return null;
	}

	public String getAuthorization()
	{
		return authorization;
	}

	public void setAuthorization( String authorization )
	{
		this.authorization = authorization;
	}

	public String authorize( String userName )
	{

		return empService.checkAuthorization( userName );

	}

}
