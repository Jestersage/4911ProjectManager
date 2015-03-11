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

@Entity
public class TimesheetRow implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "timesheetrowID", updatable = false, nullable = false)
	private long id;

	@ManyToOne
	@JoinColumn(name = "projectID", nullable = false)
    @NotNull(message = "timesheetRow must have a project ID")
	private Project project;
	
	@ManyToOne
	@JoinColumn(name = "packageID", nullable = false)
    @NotNull(message = "timesheetRow must have a package number")
    private WorkPackage workPackage;

	private String notes;

    @Column(name = "saturday")
    private double saturdayHour;

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

    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TimesheetRow{");
        sb.append(", id=").append(id);
        sb.append(", project=").append(project.getId());
        sb.append(", workPackage=").append(workPackage.getPackageNum());
        sb.append(", saturdayHour=").append(saturdayHour);
        sb.append(", sundayHour=").append(sundayHour);
        sb.append(", mondayHour=").append(mondayHour);
        sb.append(", tuesdayHour=").append(tuesdayHour);
        sb.append(", wednesdayHour=").append(wednesdayHour);
        sb.append(", thursdayHour=").append(thursdayHour);
        sb.append(", fridayHour=").append(fridayHour);
        sb.append("notes='").append(notes).append('\'');
        sb.append('}');
        return sb.toString();
    }
}