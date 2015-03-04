package ca.bcit.info.pms.model;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.lang.Override;
import java.util.Collection;

@Entity
@Table(name = "WorkPackage")
public class WorkPackage implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	@ManyToOne
	@Column(name = "projectID", updatable = false)
	@NotNull(message = "Project ID can not be null")
	@Size(max = 20, message = "Project ID cannot be longer than 20")
	private Project project;

	@Column(name = "packageNum", updatable = false)
	@NotNull(message = "Package ID can not be null")
	@Size(max = 20, message = "Project ID cannot be longer than 20")
	private String packageNum;

	@OneToMany(mappedBy = "workPackage")
	@NotNull(message = "Employee ID can not be null")
	@Size(max = 10, message = "Employee ID cannot be longer than 10")
	private Collection<Employee> employees;

	// @Column(name = "estimateCost")
	// private double estimateCost;
	//
	// @Column(name = "actualCost")
	// private double actualCost;
	//
	// @Column(name = "manDates")
	// private double manDates;

	@ManyToOne
	private WorkPackage parentWP;

	@OneToMany(mappedBy = "parentWP")
	private Collection<WorkPackage> childWP;

	@Column(name = "packageName")
	@Size(max = 20, message = "Package name cannot be longer than 20")
	private String packageName;

	@Column(name = "packageDesc")
	@Size(max = 20, message = "Package description cannot be longer than 20")
	private String packageDesc;

	@Column(name = "status")
	private int status;

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getPackageNum() {
		return packageNum;
	}

	public void setPackageNum(String packageNum) {
		this.packageNum = packageNum;
	}

	public Collection<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Collection<Employee> employees) {
		this.employees = employees;
	}

	public WorkPackage getParentWP() {
		return parentWP;
	}

	public void setParentWP(WorkPackage parentWP) {
		this.parentWP = parentWP;
	}

	public Collection<WorkPackage> getChildWP() {
		return childWP;
	}

	public void setChildWP(Collection<WorkPackage> childWP) {
		this.childWP = childWP;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageDesc() {
		return packageDesc;
	}

	public void setPackageDesc(String packageDesc) {
		this.packageDesc = packageDesc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (id >= 0)
			result += "id: " + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof WorkPackage)) {
			return false;
		}
		WorkPackage other = (WorkPackage) obj;
		if (id >= 0) {
			if (id != other.id) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id >= 0) ? 0 : (id + "").hashCode());
		return result;
	}
}