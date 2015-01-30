package ca.bcit.info.pms.View;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;

import ca.bcit.info.pms.model.Credentials;
import ca.bcit.info.pms.service.EmployeeService;

@ConversationScoped
public class EmployeeController
{
	@Inject
	EmployeeService empService;
	
	@Inject
	Credentials credentials;
	
	public void login(){
		
	}
	
	public void logout(){
		
	}
}