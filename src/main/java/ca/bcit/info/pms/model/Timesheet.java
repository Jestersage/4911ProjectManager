package ca.bcit.info.pms.model;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.lang.Override;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Weekly timesheet.
 */
@Entity
public class Timesheet implements Serializable
{
    /**
     * 24 hr * 7 days/week = 168 hr
     * 168 hrs - 40 hrs = 128 hrs maximum possible hours
     * a man can physically work outside normal work hours per week.
     * Applies to flexible hours too.
     */
    public static final int MAX_OVERTIME = 128;

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "timesheetID", updatable = false, nullable = false)
	private int id;

	@ManyToOne
	@JoinColumn(name = "employeeID", nullable = false)
    @NotNull(message = "Timesheet must have a owner")
	private Employee owner;
	
	@Column(name = "weeknumber")
	@NotNull(message = "Week number can not be null")
	private int weekNumber;

	@NotNull(message = "Ending week can not be null")
    @Column(name = "weekending")
	private Date weekEnding;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "timesheetID")
    private List<TimesheetRow> timesheetRows;

    @Min(value = 0, message = "Overtime cannot be negative")
    @Max(value = MAX_OVERTIME, message = "Overtime cannot exceed" + MAX_OVERTIME +"hr")
	private BigDecimal overtime;

    @Min(value = 0, message = "Flexible hours cannot be negative")
    @Max(value = MAX_OVERTIME, message = "Flexible hours cannot exceed" + MAX_OVERTIME +"hr")
	private BigDecimal flextime;

	private Boolean approved;

    @OneToOne
    @JoinColumn(name = "signId")
	private Signature signID;
    
    public Timesheet() { super(); }
    
    public Timesheet(Employee e) {
        super();
        this.owner = e;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public Employee getOwner() {
		return owner;
	}

	public void setOwner(Employee employee) {
		this.owner = employee;
	}

	public int getWeekNumber() {
        Calendar c = new GregorianCalendar();
        c.setTime(weekEnding);
        c.setFirstDayOfWeek(Calendar.SATURDAY);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

	public void setWeekNumber(int weekNum) {
		this.weekNumber = weekNum;
	}

	public Date getWeekEnding() {
		return weekEnding;
	}

	public void setWeekEnding(Date weekEnding) {
		this.weekEnding = weekEnding;
	}

    public List<TimesheetRow> getTimesheetRows() {
        return timesheetRows;
    }

    public void setTimesheetRows(List<TimesheetRow> timesheetRows) {
        this.timesheetRows = timesheetRows;
    }

    public BigDecimal getOvertime() {
        return overtime;
    }

    public void setOvertime(BigDecimal overtime) {
        this.overtime = overtime;
    }

    public BigDecimal getFlextime() {
        return flextime;
    }

    public void setFlextime(BigDecimal flextime) {
        this.flextime = flextime;
    }

	public Boolean isApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public Signature getSignID() {
		return signID;
	}

	public void setSignID(Signature signID) {
		this.signID = signID;
	}

    private void checkFriday(final Date end) {
        Calendar c = new GregorianCalendar();
        c.setTime(end);
        int currentDay = c.get(Calendar.DAY_OF_WEEK);
        if (currentDay != Calendar.FRIDAY) {
            throw new IllegalArgumentException("EndWeek must be a Friday");
        }

    }

    @Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Timesheet)) {
			return false;
		}
		Timesheet other = (Timesheet) obj;
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
        final StringBuffer sb = new StringBuffer("Timesheet{");
        sb.append("id=").append(id);
        sb.append(", employee=").append(owner.getCredential().getUsername());
        sb.append(", weekNumber=").append(weekNumber);
        sb.append(", weekEnding=").append(weekEnding.toLocalDate());
        sb.append(", timesheetRows=").append(timesheetRows);
        sb.append(", approved=").append(approved);
        sb.append(", signID=").append(signID);
        sb.append('}');
        return sb.toString();
    }
}