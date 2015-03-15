package ca.bcit.info.pms.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Employee pay grade - an average of pay for each salary level.
 */
@Entity
@Table(name="paygrade")
public class PayLevel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paygradeID")
    private int id;
	
	private int year;
	
	public enum PayGrade {
		P1("P1"),
		P2("P2"),
		P3("P3"),
		P4("P4"),
		P5("P5"),
		P6("P6"),
		JS("JS"),
		SS("SS"),
		DS("DS");
		
		private String label;
		
		private PayGrade(final String name) {
			label = name;
		}
		
		public String getLabel() {
			return label;
		}
	}
	
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

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	private BigDecimal cost;
}
