package ca.bcit.info.pms.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.access.MonthlyReportManager;
import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.model.WorkPackage;

@Named("monthlyReport")
@RequestScoped
public class MonthlyReportController implements Serializable {
	Project project;
	@Inject private MonthlyReportManager reportManager;
	
	public String getProject(final String id) {
		project = reportManager.find("1202");
		
		return "display";
	}
	
	public String getProjectName() {
		return project.getName();
	}
	
	public List<WorkPackage> getWorkPackages() {
		List<WorkPackage> workPackages = reportManager.getWorkPackages(project.getId());
		return workPackages;
	}
}
