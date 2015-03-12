package ca.bcit.info.pms.model;

import javax.persistence.*;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.lang.Override;
import java.sql.Date;
import java.util.List;
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

	@NotNull(message = "Start date can not 	be null")
	private Date startDate;

	private Date endDate;

    @Enumerated(EnumType.ORDINAL)
	private ProjectStatus status;

	@ManyToMany
    @JoinTable(name = "ProjectAssignment",
        joinColumns = {@JoinColumn(name = "projectID")},
        inverseJoinColumns = {@JoinColumn(name = "employeeID")})
	@NotNull(message = "Employee ID can not be null")
	@Size(max = 10, message = "Employee ID cannot be longer than 10")
	private Set<Employee> employees;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "ratesheetID")
    private RateSheet rateSheet;

//    @OneToMany(mappedBy = "id", orphanRemoval = true)
//    private List<WorkPackage> workPackages;

	@Column(name = "markupValue")
	private Double markupValue;

	@Column(name = "genReport")
	private int genReport;

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

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

//    public List<WorkPackage> getWorkPackages() {
//        return workPackages;
//    }
//
//    public void setWorkPackages(List<WorkPackage> workPackages) {
//        this.workPackages = workPackages;
//    }

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

    public RateSheet getRateSheet() {
        return rateSheet;
    }

    public void setRateSheet(RateSheet rateSheet) {
        this.rateSheet = rateSheet;
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