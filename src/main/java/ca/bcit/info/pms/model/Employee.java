package ca.bcit.info.pms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "Employee")
public class Employee implements Serializable {

    /**
     * Maximum hours of flex time that can be spent in advance.
     * Max 2 weeks * 40 hrs/wk = 80 hrs.
     */
    public static final int MAX_OWNED_FLEX_HOURS = -80;

    /**
     * Maximum hours of vacation hours an employee can earn working a full year.
     * Max 3 weeks + 40 hrs/wk = 120 hrs.
     */
    public static final int MAX_VACATION_HOURS = 120;

    @Id
	@Column(name = "employeeID", updatable = false)
	@NotNull(message = "Employee number cannot be null")
	@Size(max = 10, message = "Employee number cannot be longer than 10")
	private String id;

	@NotNull(message = "Email cannot be null")
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
			+ "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9]"
			+ "(?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]"
			+ "(?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")
	private String email;

	@NotNull(message = "First name cannot be null")
	@Size(max = 30, message = "First name cannot be longer than 30")
	private String firstName;

	@NotNull(message = "Last name cannot be null")
	@Size(max = 30, message = "Last name cannot be longer than 30")
	private String lastName;

	@Column(name = "paygradeID")
	@NotNull(message = "Pay grade cannot be null")
	private String payLevel;

	@NotNull(message = "Supervisor ID cannot be null")
	@Size(max = 10, message = "Supervisor ID cannot be longer than 10")
    // TODO custom valid empID supervisor
	private String supervisorID;

	@NotNull(message = "User name cannot be null")
	@Pattern(regexp = "^[a-zA-Z0-9_-]{3,30}$",
			 message = "Username can only contain alphabet characters, "
			 		 + "underscore (_) and hyphen (-). "
			 		 + "Minimum length 3 and maximum length 30.")
    // TODO custom unique validator
	private String username;

	@Column(name = "active")
	private boolean activeStatus;

    @Column(name = "flexTime")
    @Min(MAX_OWNED_FLEX_HOURS)
    private double flexTimeBanked;

    @Column(name = "vacationTime")
    @Max(MAX_VACATION_HOURS)
    private double vacationBanked;


    /**
     * The no-argument constructor. Used to create new employees from within the
     * application.
     */
    public Employee() {
    }


    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPayLevel() {
		return payLevel;
	}

	public void setPayLevel(String payLevel) {
		this.payLevel = payLevel;
	}

	public String getSupervisorID() {
		return supervisorID;
	}

	public void setSupervisorID(String supervisorID) {
		this.supervisorID = supervisorID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

    public double getFlexTimeBanked() {
        return flexTimeBanked;
    }

    public void setFlexTimeBanked(double flexTimeBanked) {
        this.flexTimeBanked = flexTimeBanked;
    }

    public double getVacationBanked() {
        return vacationBanked;
    }

    public void setVacationBanked(double vacationBanked) {
        this.vacationBanked = vacationBanked;
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Employee)) {
			return false;
		}
		Employee other = (Employee) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", email=" + email + ", firstName="
				+ firstName + ", lastName=" + lastName + ", payLevel="
				+ payLevel + ", supervisorID=" + supervisorID + ", username="
				+ username + ", activeStatus=" + activeStatus + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}