package ca.bcit.info.pms.controller;

import ca.bcit.info.pms.access.BudgetManager;
import ca.bcit.info.pms.access.EngineerBudgetManager;
import ca.bcit.info.pms.access.MonthlyReportManager;
import ca.bcit.info.pms.access.PayGradeManager;
import ca.bcit.info.pms.access.StatusReportManager;
import ca.bcit.info.pms.access.TimesheetRowManager;
import ca.bcit.info.pms.access.WorkPackageManager;
import ca.bcit.info.pms.model.Budget;
import ca.bcit.info.pms.model.EngineerBudget;
import ca.bcit.info.pms.model.Project;
import ca.bcit.info.pms.model.StatusReport;
import ca.bcit.info.pms.model.WorkPackage;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

@Named("monthlyReport")
@RequestScoped
public class MonthlyReportController implements Serializable {
	@Inject private MonthlyReportManager reportManager;
	@Inject private WorkPackageManager workPackageManager;
	@Inject private BudgetManager budgetManager;
	@Inject private PayGradeManager payGradeManager;
	@Inject private TimesheetRowManager timesheetRowManager;
	@Inject private StatusReportManager statusReportManager;
	@Inject private EngineerBudgetManager engineerBudgetManager;
	List<WorkPackage> workPackages;
	Project project;
	Budget budget;
	EngineerBudget engineerBudget;
	StatusReport statusReport;
	Map<String, Double> payLevelMap;
	private int totalProjectBudget = 0;
	private double totalProjectBudgetCost = 0;
	private int totalEngineerBudget = 0;
	private double totalEngineerBudgetCost = 0;
	private double totalActualBudget = 0;
	private double totalActualBudgetCost = 0;
	private int totalEacBudget = 0;
	private double totalEacBudgetCost = 0;
	private String totalVarianceBudget = "";
	private String totalVarianceBudgetCost = "";

	public double getTotalProjectBudgetCost() {
		return totalProjectBudgetCost;
	}

	public void setTotalProjectBudgetCost(double totalProjectBudgetCost) {
		this.totalProjectBudgetCost = totalProjectBudgetCost;
	}

	public int getTotalEngineerBudget() {
		return totalProjectBudget - totalEngineerBudget;
	}

	public void setTotalEngineerBudget(int totalEngineerBudget) {
		this.totalEngineerBudget = totalEngineerBudget;
	}

	public double getTotalEngineerBudgetCost() {
		return totalProjectBudgetCost - totalEngineerBudgetCost;
	}

	public void setTotalEngineerBudgetCost(double totalEngineerBudgetCost) {
		this.totalEngineerBudgetCost = totalEngineerBudgetCost;
	}

	public double getTotalActualBudget() {
		return totalActualBudget;
	}

	public void setTotalActualBudget(int totalActualBudget) {
		this.totalActualBudget = totalActualBudget;
	}

	public double getTotalActualBudgetCost() {
		return totalActualBudgetCost;
	}

	public void setTotalActualBudgetCost(double totalActualBudgetCost) {
		this.totalActualBudgetCost = totalActualBudgetCost;
	}

	public int getTotalEacBudget() {
		return totalEacBudget;
	}

	public void setTotalEacBudget(int totalEacBudget) {
		this.totalEacBudget = totalEacBudget;
	}

	public double getTotalEacBudgetCost() {
		return totalEacBudgetCost;
	}

	public void setTotalEacBudgetCost(double totalEacBudgetCost) {
		this.totalEacBudgetCost = totalEacBudgetCost;
	}

	public String getTotalVarianceBudget() {
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(-100 * (1 - ((double)totalEacBudget / (double)totalProjectBudget )));
	}

	public void setTotalVarianceBudget(String totalVarianceBudget) {
		this.totalVarianceBudget = totalVarianceBudget;
	}

	public String getTotalVarianceBudgetCost() {
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(-100 * (1 - (totalEacBudgetCost / totalProjectBudgetCost )));
	}

	public void setTotalVarianceBudgetCost(String totalVarianceBudgetCost) {
		this.totalVarianceBudgetCost = totalVarianceBudgetCost;
	}

	public int getTotalProjectBudget() {
		return totalProjectBudget;
	}

	public void setTotalProjectBudget(int totalProjectBudget) {
		this.totalProjectBudget = totalProjectBudget;
	}

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
		double totalActualCost = 0;
		double totalEstimateCost = 0;
		double totalEngineerCost = 0;
		DecimalFormat df = new DecimalFormat("#.##");
		
		workPackages = workPackageManager.getWorkPackages(project.getId());
		for(int i = 0; i < workPackages.size(); i++) {
			totalCost = 0;
			totalActualCost = 0;
			totalEstimateCost = 0;
			totalEngineerCost = 0;
			
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
			totalProjectBudget += budgetManager.getTotalBudget(workPackages.get(i).getId());
			workPackages.get(i).setTotalCost(totalCost);
			totalProjectBudgetCost += totalCost;
			
			workPackages.get(i).setActualManDays(timesheetRowManager.getTotalManDays(workPackages.get(i).getId()));
			totalActualBudget += timesheetRowManager.getTotalManDays(workPackages.get(i).getId());
			payLevelMap = timesheetRowManager.getManHoursPerPayLevel(workPackages.get(i).getId());
			totalActualCost += payLevelMap.get("DS") * payGradeManager.getCost("DS");
			totalActualCost += payLevelMap.get("JS") * payGradeManager.getCost("JS");
			totalActualCost += payLevelMap.get("SS") * payGradeManager.getCost("SS");
			totalActualCost += payLevelMap.get("P1") * payGradeManager.getCost("P1");
			totalActualCost += payLevelMap.get("P2") * payGradeManager.getCost("P2");
			totalActualCost += payLevelMap.get("P3") * payGradeManager.getCost("P3");
			totalActualCost += payLevelMap.get("P4") * payGradeManager.getCost("P4");
			totalActualCost += payLevelMap.get("P5") * payGradeManager.getCost("P5");
			totalActualCost += payLevelMap.get("P6") * payGradeManager.getCost("P6");
			workPackages.get(i).setTotalActualCost(totalActualCost);
			totalActualBudgetCost += totalActualCost;
			
			statusReport = statusReportManager.getStatusReport(workPackages.get(i).getId());
			totalEstimateCost += statusReport.getDS() * payGradeManager.getCost("DS");
			totalEstimateCost += statusReport.getJS() * payGradeManager.getCost("JS");
			totalEstimateCost += statusReport.getSS() * payGradeManager.getCost("SS");
			totalEstimateCost += statusReport.getP1() * payGradeManager.getCost("P1");
			totalEstimateCost += statusReport.getP2() * payGradeManager.getCost("P2");
			totalEstimateCost += statusReport.getP3() * payGradeManager.getCost("P3");
			totalEstimateCost += statusReport.getP4() * payGradeManager.getCost("P4");
			totalEstimateCost += statusReport.getP5() * payGradeManager.getCost("P5");
			totalEstimateCost += statusReport.getP6() * payGradeManager.getCost("P6");
			workPackages.get(i).setEstimateManDays(statusReportManager.getTotalCompletionEstimate(workPackages.get(i).getId()));
			workPackages.get(i).setTotalEstimateCost(totalEstimateCost);
			totalEacBudget += statusReportManager.getTotalCompletionEstimate(workPackages.get(i).getId());
			totalEacBudgetCost += totalEstimateCost;
			
			workPackages.get(i).setVarianceManDays(df.format((1 - ((double)statusReportManager.getTotalCompletionEstimate(workPackages.get(i).getId())
					/ (double)budgetManager.getTotalBudget(workPackages.get(i).getId()))) * -100));
			workPackages.get(i).setTotalVarianceCost(df.format(-100 * (1 - (totalEstimateCost / totalCost ))));
			
			engineerBudget = engineerBudgetManager.getBudget(workPackages.get(i).getId());
			if(engineerBudget == null) {
				totalEngineerCost = 0;
			} else {
				totalEngineerCost += engineerBudget.getDS() * payGradeManager.getCost("DS");
				totalEngineerCost += engineerBudget.getJS() * payGradeManager.getCost("JS");
				totalEngineerCost += engineerBudget.getSS() * payGradeManager.getCost("SS");
				totalEngineerCost += engineerBudget.getP1() * payGradeManager.getCost("P1");
				totalEngineerCost += engineerBudget.getP2() * payGradeManager.getCost("P2");
				totalEngineerCost += engineerBudget.getP3() * payGradeManager.getCost("P3");
				totalEngineerCost += engineerBudget.getP4() * payGradeManager.getCost("P4");
				totalEngineerCost += engineerBudget.getP5() * payGradeManager.getCost("P5");
				totalEngineerCost += engineerBudget.getP6() * payGradeManager.getCost("P6");
			}
			workPackages.get(i).setEngineerManDays(engineerBudgetManager.getTotalBudget(workPackages.get(i).getId()));
			totalEngineerBudget += budgetManager.getTotalBudget(workPackages.get(i).getId()) 
					- engineerBudgetManager.getTotalBudget(workPackages.get(i).getId());
			workPackages.get(i).setTotalEngineerCost(totalEngineerCost);
			totalEngineerBudgetCost += totalCost - totalEngineerCost;
			
			workPackages.get(i).setCompletionPercentage(df.format(((double)timesheetRowManager.getTotalManDays(workPackages.get(i).getId()))
					/ (double)statusReportManager.getTotalCompletionEstimate(workPackages.get(i).getId()) * 100));
		}
		return workPackages;
	}
}
