package ca.bcit.info.pms.model;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import java.lang.Override;
import java.sql.Date;

@Entity
@Table(name = "timesheet")
public class Timesheet implements Serializable {
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
	@JoinColumn(name = "employeeID", nullable = false)
	private Employee employee;
	
	@Column(name = "weeknumber")
	@NotNull(message = "Week number can not be null")
	private Date weeknumber;	
	
	@Column(name = "weekending")
	@NotNull(message = "Ending week can not be null")
	private Date weekEnding;
	
//	@Column(name = "grandTotal")
//	@NotNull(message = "Grand total can not be null")
//	private double grandTotal;
//	
//	@Column(name = "overtime")
//	private double overtime;
//	
//	@Column(name = "flextime")
//	private double flextime;
	
	@Column(name = "signed")
	private String signed;
	
	@Column(name = "approved")
	private String approved;
	
	@Column(name = "signID")
	private int signID;
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getWeeknumber() {
		return weeknumber;
	}

	public void setWeeknumber(Date weeknumber) {
		this.weeknumber = weeknumber;
	}

	public Date getWeekEnding() {
		return weekEnding;
	}

	public void setWeekEnding(Date weekEnding) {
		this.weekEnding = weekEnding;
	}

	public String getSigned() {
		return signed;
	}

	public void setSigned(String signed) {
		this.signed = signed;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public int getSignID() {
		return signID;
	}

	public void setSignID(int signID) {
		this.signID = signID;
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
}