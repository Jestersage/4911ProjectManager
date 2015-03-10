package ca.bcit.info.pms.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.info.pms.model.Project;

@Named("monthlyReport")
@RequestScoped
public class MonthlyReportController implements Serializable {
	@Inject Project project;
}
