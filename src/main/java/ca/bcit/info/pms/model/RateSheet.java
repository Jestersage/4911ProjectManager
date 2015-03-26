package ca.bcit.info.pms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Budgeting information in man-days for project.
 */
@Entity
public class RateSheet {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column(name = "ratesheetID")
    private int id;
    
    private int year;

    private int JS;
    private int SS;
    private int DS;
    private int P1;
    private int P2;
    private int P3;
    private int P4;
    private int P5;
    private int P6;
    private int other;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getJS() {
        return JS;
    }

    public void setJS(int JS) {
        this.JS = JS;
    }

    public int getSS() {
        return SS;
    }

    public void setSS(int SS) {
        this.SS = SS;
    }

    public int getDS() {
        return DS;
    }

    public void setDS(int DS) {
        this.DS = DS;
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

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RateSheet rateSheet = (RateSheet) o;

        if (id != rateSheet.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RateSheet{");
        sb.append("id=").append(id);
        sb.append(", year=").append(year);
        sb.append(", JS=").append(JS);
        sb.append(", SS=").append(SS);
        sb.append(", DS=").append(DS);
        sb.append(", P1=").append(P1);
        sb.append(", P2=").append(P2);
        sb.append(", P3=").append(P3);
        sb.append(", P4=").append(P4);
        sb.append(", P5=").append(P5);
        sb.append(", P6=").append(P6);
        sb.append(", other=").append(other);
        sb.append('}');
        return sb.toString();
    }
}
