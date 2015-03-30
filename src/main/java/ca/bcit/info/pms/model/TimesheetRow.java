package ca.bcit.info.pms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Timesheet row for each work package of a timesheet.
 */
@Entity
public class TimesheetRow implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "timesheetrowID", updatable = false, nullable = false)
	private int id;

	@ManyToOne
	@JoinColumn(name = "packageID", nullable = false)
    @NotNull(message = "timesheetRow must have a package number")
    private WorkPackage workPackage;

	private String notes;

    @Column(name = "saturday")
    private BigDecimal saturdayHour;

    @Column(name = "sunday")
	private BigDecimal sundayHour;

	@Column(name = "monday")
	private BigDecimal mondayHour;
	
	@Column(name = "tuesday")
	private BigDecimal tuesdayHour;
	
	@Column(name = "wednesday")
	private BigDecimal wednesdayHour;
	
	@Column(name = "thursday")
	private BigDecimal thursdayHour;
	
	@Column(name = "friday")
	private BigDecimal fridayHour;
	
    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
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

	public double getSaturdayHour() {
		return saturdayHour.doubleValue();
	}

	public void setSaturdayHour(double saturdayHour) {
		this.saturdayHour = new BigDecimal(saturdayHour);
	}

	public double getSundayHour() {
		return sundayHour.doubleValue();
	}

	public void setSundayHour(double sundayHour) {
		this.sundayHour = new BigDecimal(sundayHour);
	}

	public double getMondayHour() {
		return mondayHour.doubleValue();
	}

	public void setMondayHour(double mondayHour) {
		this.mondayHour = new BigDecimal(mondayHour);
	}

	public double getTuesdayHour() {
		return tuesdayHour.doubleValue();
	}

	public void setTuesdayHour(double tuesdayHour) {
		this.tuesdayHour = new BigDecimal(tuesdayHour);
	}

	public double getWednesdayHour() {
		return wednesdayHour.doubleValue();
	}

	public void setWednesdayHour(double wednesdayHour) {
		this.wednesdayHour = new BigDecimal(wednesdayHour);
	}

	public double getThursdayHour() {
		return thursdayHour.doubleValue();
	}

	public void setThursdayHour(double thursdayHour) {
		this.thursdayHour = new BigDecimal(thursdayHour);
	}

	public double getFridayHour() {
		return fridayHour.doubleValue();
	}

	public void setFridayHour(double fridayHour) {
		this.fridayHour = new BigDecimal(fridayHour);
	}

	@Transient
	public double getTotalHours() {
		double total;
		try {
			total = fridayHour.add(saturdayHour.add(sundayHour.add(
					mondayHour.add(tuesdayHour.add(wednesdayHour.add(
							thursdayHour)))))).doubleValue();
		} catch (Exception ex) {
			total = 0;
		}
		return total;
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
        final StringBuilder sb = new StringBuilder("TimesheetRow{");
        sb.append("id=").append(id);
		if (workPackage != null && workPackage.getProject() != null) {
			sb.append(", workPackage=")
					.append(workPackage.getProject().getId()).append("-")
					.append(workPackage.getPackageNum());
		}
        sb.append(", saturdayHour=").append(saturdayHour);
        sb.append(", sundayHour=").append(sundayHour);
        sb.append(", mondayHour=").append(mondayHour);
        sb.append(", tuesdayHour=").append(tuesdayHour);
        sb.append(", wednesdayHour=").append(wednesdayHour);
        sb.append(", thursdayHour=").append(thursdayHour);
        sb.append(", fridayHour=").append(fridayHour);
        sb.append(", notes='").append(notes).append('\'');
        sb.append('}');
        return sb.toString();
    }
}