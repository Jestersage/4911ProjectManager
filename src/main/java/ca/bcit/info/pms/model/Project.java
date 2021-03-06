package ca.bcit.info.pms.model;

import javax.persistence.*;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.lang.Override;
import java.sql.Date;
import java.util.Set;

/**
 * Project information.
 */
@Entity
public class Project implements Serializable {
	@Id
	@Column(name = "projectID", nullable = false)
	private String id;

	@Column(name = "projectName", nullable = false)
	@NotNull(message = "Project name can not be null")
	private String name;

	@NotNull(message = "Project description can not be null")
	private String description;

//	@NotNull(message = "Manager Id can not be null")
//	private String employeeID;

	@NotNull(message = "Project manager can not be null")
	@ManyToOne
	@JoinColumn(name="EmployeeID")
	private Employee projectManager;

	@ManyToOne
	@JoinColumn(name = "assistantID")
	private Employee assistant;

	@NotNull(message = "Start date can not 	be null")
	private Date startDate;

	private Date endDate;

	@Enumerated(EnumType.ORDINAL)
	private ProjectStatus status;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ProjectAssignment", joinColumns = { @JoinColumn(name = "projectID") }, inverseJoinColumns = { @JoinColumn(name = "employeeID") })
	private Set<Employee> employees;

    @OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.ALL},orphanRemoval = true)
    @JoinColumn(name = "ratesheetID")
    private RateSheet rateSheet;


	@Column(name = "markupValue")
	private Double markupValue;

	@Column(name = "genReport")
	private boolean genReport;
	
	@Column(name = "contractorName")
	private String contractorName;
	
	@Transient
	private boolean hasWorkPackage = false;

	public boolean isHasWorkPackage() {
		return hasWorkPackage;
	}

	public void setHasWorkPackage(boolean hasWorkPackage) {
		this.hasWorkPackage = hasWorkPackage;
	}

	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String projectName) {
		this.name = projectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String projectDescription) {
		this.description = projectDescription;
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

	public ProjectStatus getStatus() {
		return status;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	public Employee getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(Employee projectManager) {
		this.projectManager = projectManager;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}


	public double getMarkupValue() {
		if (markupValue == null)
			return 0;
		return markupValue;
	}

	public void setMarkupValue(double markupValue) {
		this.markupValue = markupValue;
	}

	public boolean getGenReport() {
		return genReport;
	}

	public void setGenReport(boolean genReport) {
		this.genReport = genReport;
	}

    public RateSheet getRateSheet() {
        return rateSheet;
    }

	public void setRateSheet(RateSheet rateSheet) {
		this.rateSheet = rateSheet;
	}

	/**
	 * Assign employee to this project.
	 * 
	 * @param employee
	 *            employee to be aded.
	 * @return if insert success. False if employee already included.
	 */
	public boolean assignEmployee(final Employee employee) {
		return employees.add(employee);
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

	public Project() {
		this.rateSheet = new RateSheet();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public Employee getAssistant() {
		return assistant;
	}

	public void setAssistant(Employee assistant) {
		this.assistant = assistant;
	}

	public void setMarkupValue(Double markupValue) {
		this.markupValue = markupValue;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}
}
