package ca.bcit.info.pms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EngineerBudget {
	@Id
	//@GeneratedValue( strategy = GenerationType.AUTO )
    @Column(name = "engBudgetID")
    private Integer id=0;
	
	private Integer JS;
    private Integer SS;
    private Integer DS;
    private Integer P1;
    private Integer P2;
    private Integer P3;
    private Integer P4;
    private Integer P5;
    private Integer P6;
    private Integer other;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getJS() {
		return JS;
	}
	public void setJS(Integer jS) {
		JS = jS;
	}
	public Integer getSS() {
		return SS;
	}
	public void setSS(Integer sS) {
		SS = sS;
	}
	public Integer getDS() {
		return DS;
	}
	public void setDS(Integer dS) {
		DS = dS;
	}
	public Integer getP1() {
		return P1;
	}
	public void setP1(Integer p1) {
		P1 = p1;
	}
	public Integer getP2() {
		return P2;
	}
	public void setP2(Integer p2) {
		P2 = p2;
	}
	public Integer getP3() {
		return P3;
	}
	public void setP3(Integer p3) {
		P3 = p3;
	}
	public Integer getP4() {
		return P4;
	}
	public void setP4(Integer p4) {
		P4 = p4;
	}
	public Integer getP5() {
		return P5;
	}
	public void setP5(Integer p5) {
		P5 = p5;
	}
	public Integer getP6() {
		return P6;
	}
	public void setP6(Integer p6) {
		P6 = p6;
	}
	public Integer getOther() {
		return other;
	}
	public void setOther(Integer other) {
		this.other = other;
	}
}
