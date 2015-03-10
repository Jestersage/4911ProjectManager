package ca.bcit.info.pms.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Collection;

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

    // TODO map credential & change manager saving logic
    @NotNull(message = "User name cannot be null")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,30}$",
            message = "Username can only contain alphabet characters, "
                    + "underscore (_) and hyphen (-). "
                    + "Minimum length 3 and maximum length 30.")
    private String username;

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

	@Column(name = "payGrade")
    @Enumerated(EnumType.STRING)
	@NotNull(message = "Pay grade cannot be null")
	private PayLevel payLevel;

//	@Size(max = 10, message = "Supervisor ID cannot be longer than 10")
    @ManyToOne
    @JoinColumn(name = "SupervisorID")
	private Employee supervisor;

    @ManyToOne
    @JoinColumn(name = "approverID")
    private Employee timesheetApprover;

	@Column(name = "active")
    @NotNull (message = "employee status cannot be null.")
	private boolean activeStatus;

    @Column(name = "flexTime")
    @Min(MAX_OWNED_FLEX_HOURS)
    private double flexTimeBanked;

    @Column(name = "vacationTime")
    @Max(MAX_VACATION_HOURS)
    private double vacationBanked;

    @ManyToMany(mappedBy = "employees")
    private Collection<WorkPackage> workPackage;

    @ManyToMany(mappedBy = "employees")
    private Collection<Project> projects;

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

	public PayLevel getPayLevel() {
		return payLevel;
	}

	public void setPayLevel(PayLevel payLevel) {
		this.payLevel = payLevel;
	}

	public Employee getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Employee supervisor) {
		this.supervisor = supervisor;
	}

    public Employee getTimesheetApprover() {
        return timesheetApprover;
    }

    public void setTimesheetApprover(Employee timesheetApprover) {
        this.timesheetApprover = timesheetApprover;
    }

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    public Collection<Project> getProjects() {
        return projects;
    }

    public void setProjects(Collection<Project> projects) {
        this.projects = projects;
    }

    public Collection<WorkPackage> getWorkPackage() {
        return workPackage;
    }


    public void setWorkPackage(Collection<WorkPackage> workPackage) {
        this.workPackage = workPackage;
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
				+ payLevel + ", supervisorID=" + supervisor.getUsername() + ", username="
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