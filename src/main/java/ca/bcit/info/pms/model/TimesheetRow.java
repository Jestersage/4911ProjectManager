package ca.bcit.info.pms.model;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.lang.Override;
import java.sql.Date;

//@Entity
public class TimesheetRow implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "timesheetID")
	private Timesheet timesheet;
	
	@ManyToOne
	@JoinColumn(name = "projectID", nullable = false)
	private Project project;
	
	@ManyToOne
	@JoinColumn(name = "packageID", nullable = false)
	private WorkPackage workPackage;
	
	@Column(name = "weekending")
	@NotNull(message = "Ending week can not be null")
	private Date weekEnding;

//	@Column(name = "total")
//	@NotNull(message = "Total hours can not be null")
//	private double total;
	
	@Column(name = "notes")
	@NotNull(message = "Notes can not be null")
	private String notes;
	
	@Column(name = "sunday")
	private double sundayHour;
	
	@Column(name = "monday")
	private double mondayHour;
	
	@Column(name = "tuesday")
	private double tuesdayHour;
	
	@Column(name = "wednesday")
	private double wednesdayHour;
	
	@Column(name = "thursday")
	private double thursdayHour;
	
	@Column(name = "friday")
	private double fridayHour;
	
	@Column(name = "saturday")
	private double saturdayHour;
	
	public Timesheet getTimesheet() {
		return timesheet;
	}

	public void setTimesheet(Timesheet timesheet) {
		this.timesheet = timesheet;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public WorkPackage getWorkPackage() {
		return workPackage;
	}

	public void setWorkPackage(WorkPackage workPackage) {
		this.workPackage = workPackage;
	}

	public Date getWeekEnding() {
		return weekEnding;
	}

	public void setWeekEnding(Date weekEnding) {
		this.weekEnding = weekEnding;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public double getSundayHour() {
		return sundayHour;
	}

	public void setSundayHour(double sundayHour) {
		this.sundayHour = sundayHour;
	}

	public double getMondayHour() {
		return mondayHour;
	}

	public void setMondayHour(double mondayHour) {
		this.mondayHour = mondayHour;
	}

	public double getTuesdayHour() {
		return tuesdayHour;
	}

	public void setTuesdayHour(double tuesdayHour) {
		this.tuesdayHour = tuesdayHour;
	}

	public double getWednesdayHour() {
		return wednesdayHour;
	}

	public void setWednesdayHour(double wednesdayHour) {
		this.wednesdayHour = wednesdayHour;
	}

	public double getThursdayHour() {
		return thursdayHour;
	}

	public void setThursdayHour(double thursdayHour) {
		this.thursdayHour = thursdayHour;
	}

	public double getFridayHour() {
		return fridayHour;
	}

	public void setFridayHour(double fridayHour) {
		this.fridayHour = fridayHour;
	}

	public double getSaturdayHour() {
		return saturdayHour;
	}

	public void setSaturdayHour(double saturdayHour) {
		this.saturdayHour = saturdayHour;
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
		if (!(obj instanceof TimesheetRow)) {
			return false;
		}
		TimesheetRow other = (TimesheetRow) obj;
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
		result = prime * result + ((id < 0) ? 0 : (id + "").hashCode());
		return result;
	}
}