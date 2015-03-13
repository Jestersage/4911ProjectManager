package ca.bcit.info.pms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Status Report.
 */
@Entity
public class StatusReport
{
    @Id
    @Column(name = "reportID", updatable = false, nullable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "packageID")
    private WorkPackage workPackage;

    private String comments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public WorkPackage getWorkPackage() {
        return workPackage;
    }

    public void setWorkPackage(WorkPackage workPackage) {
        this.workPackage = workPackage;
    }

    public String getComment() {
        return comments;
    }

    public void setComment(String comment) {
        this.comments = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatusReport)) return false;

        StatusReport that = (StatusReport) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("StatusReport{");
        sb.append("workPackage=").append(workPackage.getProject().getId())
                .append("-")
                .append(workPackage.getPackageNum());
        sb.append('}');
        return sb.toString();
    }
}
