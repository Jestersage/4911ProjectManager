package ca.bcit.info.pms.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Company employee and user of system.
 */
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

    public static final String ROLE_HR = "HR";
    public static final String ROLE_SUPERVISOR = "Supervisor";
    public static final String ROLE_TS_APPROVER = "TimesheetApprover";
    public static final String ROLE_PROJECT_MANAGER = "ProjectManager";
    public static final String ROLE_ASSISTANT = "Assistant";
    public static final String ROLE_WP_MANAGER = "WorkPackageManager";

    @Id
	@Column(name = "employeeID", updatable = false)
	@NotNull(message = "Employee number cannot be null")
	@Size(max = 10, message = "Employee number cannot be longer than 10")
    // TODO check uniqueness
	private String id;

    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE},fetch=FetchType.EAGER)
    @JoinColumn(name = "username")
    @NotNull(message = "User credential cannot be null")
    private Credential credential;

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

//	@ManyToOne
//	@JoinColumn(name = "name")	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "Pay grade cannot be null")
	private PayLevel.PayGrade payGrade;

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
    private BigDecimal flexTimeBanked;

    @Column(name = "vacationTime")
    @Max(MAX_VACATION_HOURS)
    private BigDecimal vacationBanked;

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

//	public PayLevel getPayLevel() {
//		return payGrade;
//	}
//
//	public void setPayLevel(PayLevel payLevel) {
//		this.payGrade = payLevel;
//	}

	public Employee getSupervisor() {
		return supervisor;
	}

	public PayLevel.PayGrade getPayGrade() {
		return payGrade;
	}


	public void setPayGrade(PayLevel.PayGrade payGrade) {
		this.payGrade = payGrade;
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

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

//    public double getFlexTimeBanked() {
//        return flexTimeBanked;
//    }
//
//    public void setFlexTimeBanked(double flexTimeBanked) {
//        this.flexTimeBanked = flexTimeBanked;
//    }

//    public double getVacationBanked() {
//        return vacationBanked;
//    }

    public BigDecimal getFlexTimeBanked() {
		return flexTimeBanked;
	}


	public void setFlexTimeBanked(BigDecimal flexTimeBanked) {
		this.flexTimeBanked = flexTimeBanked;
	}


//	public void setVacationBanked(double vacationBanked) {
//        this.vacationBanked = vacationBanked;
//    }

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

	public BigDecimal getVacationBanked() {
		return vacationBanked;
	}


	public void setVacationBanked(BigDecimal vacationBanked) {
		this.vacationBanked = vacationBanked;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Employee{");
        sb.append("id='").append(id).append('\'');
        sb.append(", username='").append(credential.getUsername()).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", payLevel=").append(payGrade);
        if (supervisor != null) {
            sb.append(", supervisor=").append(supervisor.credential.getUsername());
        }
        if (timesheetApprover != null) {
            sb.append(", timesheetApprover=").append(timesheetApprover.credential.getUsername());
        }
        sb.append(", activeStatus=").append(activeStatus);
        sb.append(", flexTimeBanked=").append(flexTimeBanked);
        sb.append(", vacationBanked=").append(vacationBanked);
        sb.append('}');
        return sb.toString();
    }
}