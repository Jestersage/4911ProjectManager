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
    
    private int JS;
    private int DS;
    private int SS;
    private int P1;
    private int P2;
    private int P3;
    private int P4;
    private int P5;
    private int P6;

    private String comments;

    public int getJS() {
		return JS;
	}

	public void setJS(int jS) {
		JS = jS;
	}

	public int getDS() {
		return DS;
	}

	public void setDS(int dS) {
		DS = dS;
	}

	public int getSS() {
		return SS;
	}

	public void setSS(int sS) {
		SS = sS;
	}

	public int getP1() {
		return P1;
	}

	public void setP1(int p1) {
		P1 = p1;
	}

	public int getP2() {
		return P2;
	}

	public void setP2(int p2) {
		P2 = p2;
	}

	public int getP3() {
		return P3;
	}

	public void setP3(int p3) {
		P3 = p3;
	}

	public int getP4() {
		return P4;
	}

	public void setP4(int p4) {
		P4 = p4;
	}

	public int getP5() {
		return P5;
	}

	public void setP5(int p5) {
		P5 = p5;
	}

	public int getP6() {
		return P6;
	}

	public void setP6(int p6) {
		P6 = p6;
	}
	
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
		if (workPackage != null && workPackage.getProject() != null) {
			sb.append("workPackage=").append(workPackage.getProject().getId())
					.append("-")
					.append(workPackage.getPackageNum());
		}
        sb.append('}');
        return sb.toString();
    }
}
