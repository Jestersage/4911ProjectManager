package ca.bcit.info.pms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * For sole purpose of keeping track of employees with HR role.
 */
@Entity
@Table(name = "HR")
public class Hr {

    @Id
    @Column(name = "employeeID")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
