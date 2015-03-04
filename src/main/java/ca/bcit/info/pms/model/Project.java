package ca.bcit.info.pms.model;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.lang.Override;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "Project")
public class Project implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private String id;

	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	@Column(name = "projectName")
	@NotNull(message = "Project name can not be null")
	private String projectName;

	@Column(name = "projectDescription")
	@NotNull(message = "Project description can not be null")
	private String projectDescription;

	@Column(name = "startDate")
	@NotNull(message = "Start date can not 	be null")
	private Date startDate;

	@Column(name = "endDate")
	private Date endDate;

	@Column(name = "budget")
	private double budget;

	@Column(name = "status")
	private int status;

	@OneToMany(mappedBy = "project")
	@NotNull(message = "Employee ID can not be null")
	@Size(max = 10, message = "Employee ID cannot be longer than 10")
	private Collection<Employee> employees;

	@Column(name = "markupValue")
	private double markupValue;

	@Column(name = "genReport")
	private int genReport;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Collection<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Collection<Employee> employees) {
		this.employees = employees;
	}

	public double getMarkupValue() {
		return markupValue;
	}

	public void setMarkupValue(double markupValue) {
		this.markupValue = markupValue;
	}

	public int getGenReport() {
		return genReport;
	}

	public void setGenReport(int genReport) {
		this.genReport = genReport;
	}
	
	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (id != null)
			result += "id: " + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Project)) {
			return false;
		}
		Project other = (Project) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}