package ca.bcit.info.pms.model;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.lang.Override;

@Entity
@Table(name = "Employee")
public class Employee implements Serializable {

	/**
	 * The no-argument constructor. Used to create new employees from within the
	 * application.
	 */
	public Employee() {
	}

	/**
	 * The argument-containing constructor. Used to create the initial employees
	 */
	public Employee(String id, String email, String firstName, String lastName,
			String payLevel, String supervisorID, String username,
			int activeStatus) {
		super();
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.payLevel = payLevel;
		this.supervisorID = supervisorID;
		this.username = username;
		this.activeStatus = activeStatus;
	}

	@Id
	@Column(name = "id", updatable = false)
	@NotNull(message = "ID cannot be null")
	@Size(max = 10, message = "Employee ID cannot be longer than 10")
	private String id;

	// @Version
	// @Column(name = "version")
	// private int version;

	@Column(name = "email")
	@NotNull(message = "Email cannot be null")
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
			+ "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9]"
			+ "(?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]"
			+ "(?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")
	private String email;

	@Column(name = "firstName")
	@NotNull(message = "First name cannot be null")
	@Size(max = 30, message = "First name cannot be longer than 30")
	private String firstName;

	@Column(name = "lastName")
	@NotNull(message = "Last name cannot be null")
	@Size(max = 30, message = "Last name cannot be longer than 30")
	private String lastName;

	@Column(name = "payLevel")
	@NotNull(message = "Pay grade cannot be null")
	private String payLevel;

	@Column(name = "supervisorID")
	@NotNull(message = "Supervisor ID cannot be null")
	@Size(max = 10, message = "Supervisor ID cannot be longer than 10")
	private String supervisorID;

	@Column(name = "username")
	@NotNull(message = "User name cannot be null")
	@Pattern(regexp = "^[a-zA-Z0-9_-]{5,30}$", 
			 message = "User name can only contain alphabet characters, "
			 		 + "underscore (_) and hyphen (-). "
			 		 + "Minimun length 5 and maximum length 30.")
	private String username;

	@Column(name = "activeStatus")
	private int activeStatus;

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

	public int getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	// public int getVersion() {
	// return this.version;
	// }
	//
	// public void setVersion(final int version) {
	// this.version = version;
	// }

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}