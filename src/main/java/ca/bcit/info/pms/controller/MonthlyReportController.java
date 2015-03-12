package ca.bcit.info.pms.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.access.BudgetManager;
import ca.bcit.info.pms.access.MonthlyReportManager;
import ca.bcit.info.pms.access.PayGradeManager;
import ca.bcit.info.pms.access.TimesheetRowManager;
import ca.bcit.info.pms.access.WorkPackageManager;
import ca.bcit.info.pms.model.Budget;
import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.model.WorkPackage;

@Named("monthlyReport")
@RequestScoped
public class MonthlyReportController implements Serializable {
	@Inject private MonthlyReportManager reportManager;
	@Inject private WorkPackageManager workPackageManager;
	@Inject private BudgetManager budgetManager;
	@Inject private PayGradeManager payGradeManager;
	@Inject private TimesheetRowManager timesheetRowManager;
	List<WorkPackage> workPackages;
	Project project;
	Budget budget;
	
	public String getProject(final String id) {
		project = reportManager.find("1202");
		
		return "display";
	}
	
	public String getProjectName() {
		project = reportManager.find("1202"); //Using hard coded value now for testing
		return project.getName();
	}
	
	public List<WorkPackage> getWorkPackages() {
		double totalCost = 0;
		workPackages = workPackageManager.getWorkPackages(project.getId());
		for(int i = 0; i < workPackages.size(); i++) {
			budget = budgetManager.getBudget(workPackages.get(i).getId());
			totalCost += budget.getDS() * payGradeManager.getCost("DS");
			totalCost += budget.getJS() * payGradeManager.getCost("JS");
			totalCost += budget.getSS() * payGradeManager.getCost("SS");
			totalCost += budget.getP1() * payGradeManager.getCost("P1");
			totalCost += budget.getP2() * payGradeManager.getCost("P2");
			totalCost += budget.getP3() * payGradeManager.getCost("P3");
			totalCost += budget.getP4() * payGradeManager.getCost("P4");
			totalCost += budget.getP5() * payGradeManager.getCost("P5");
			totalCost += budget.getP6() * payGradeManager.getCost("P6");
			workPackages.get(i).setTotalBudget(budgetManager.getTotalBudget(workPackages.get(i).getId()));
			workPackages.get(i).setTotalCost(totalCost);
			workPackages.get(i).setActualManDays(timesheetRowManager.getTotalManDays(workPackages.get(i).getId()));
		}
		return workPackages;
	}
}
