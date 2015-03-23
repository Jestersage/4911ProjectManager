package ca.bcit.info.pms.model;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import ca.bcit.info.pms.util.PasswordHash;

import java.lang.Override;
import javax.inject.Named;
import javax.enterprise.inject.New;

/**
 * User login credential - username and password.
 */
@Named
@Entity
@Table(name = "Credentials")
public class Credential implements Serializable {

    @Id
    @NotNull(message = "Username must not be null")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,30}$",
            message = "Username can only contain alphabet characters, "
                    + "underscore (_) and hyphen (-). "
                    + "Minimum length 3 and maximum length 30.")
    // TODO check uniqueness
    private String username;

    @NotNull (message = "Password must not be null")
    protected String password;

    public Credential() {}

    public Credential(String username, String password) {
        this.username = username;
        try {
            this.password = PasswordHash.createHash(password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        try {
            this.password = PasswordHash.createHash(password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credential other = (Credential) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (username != null && !username.trim().isEmpty())
			result += ", username: " + username;
		if (password != null && !password.trim().isEmpty())
			result += ", password: " + password;
		return result;
	}
}