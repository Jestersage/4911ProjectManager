package ca.bcit.info.pms.access;

import java.io.Serializable;
import java.util.HashMap;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ca.bcit.info.pms.model.PayLevel;
import ca.bcit.info.pms.model.TimesheetRow;

@Dependent
@Stateless
public class TimesheetRowManager implements Serializable {
	@PersistenceContext(unitName = "pms-persistence-unit") private EntityManager em;
	
	public TimesheetRow find(final long l) {
		return em.find(TimesheetRow.class, l);
	}
	
	public void remove(final TimesheetRow timesheetRow) {
		TimesheetRow tr = find(timesheetRow.getId());
		em.remove(tr);
	}
	
	public void merge(final TimesheetRow timesheetRow) {
		em.merge(timesheetRow);
	}
	
	public void merge(final List<TimesheetRow> timesheetRows) {
	    for ( TimesheetRow tsr : timesheetRows ) {
	        em.merge(tsr);
	    }
	}
	
	public void persist(final TimesheetRow timesheetRow) {
		em.persist(timesheetRow);
	}
	
	public void persist(final List<TimesheetRow> tsRows) {
	    for(TimesheetRow row : tsRows ) {
	        em.persist(row);
	    }
	}
	
	public double getTotalManDays(final int wpId) {
		double totalManDays = 0;
		Query query = em.createNativeQuery("select tr.* "
				+ "from timesheetrow tr "
				+ "join timesheet t on tr.timesheetID = t.timesheetID "
				+ "where packageID = :workpackageId "
				+ "and t.approved = true", TimesheetRow.class)
				.setParameter("workpackageId", wpId);
		try{
			List<TimesheetRow> timesheetRows = query.getResultList();
		    for(int i = 0; i < timesheetRows.size(); i++) {
		    	totalManDays += timesheetRows.get(i).getFridayHour();
		    	totalManDays += timesheetRows.get(i).getMondayHour();
		    	totalManDays += timesheetRows.get(i).getSaturdayHour();
		    	totalManDays += timesheetRows.get(i).getSundayHour();
		    	totalManDays += timesheetRows.get(i).getThursdayHour();
		    	totalManDays += timesheetRows.get(i).getTuesdayHour();
		    	totalManDays += timesheetRows.get(i).getWednesdayHour();
		    }
			return totalManDays / 8.0;
		} catch (NoResultException e) {
			return 0;
		}
	}
	
	public Map<String, Double> getManHoursPerPayLevel(final int wpId) {
		Map<String, Double> payLevelAmount = new HashMap<String, Double>();
		String key;
		double value = 0;
		double currentValue;
		
		payLevelAmount.put("JS", 0.0);
		payLevelAmount.put("DS", 0.0);
		payLevelAmount.put("SS", 0.0);
		payLevelAmount.put("P1", 0.0);
		payLevelAmount.put("P2", 0.0);
		payLevelAmount.put("P3", 0.0);
		payLevelAmount.put("P4", 0.0);
		payLevelAmount.put("P5", 0.0);
		payLevelAmount.put("P6", 0.0);
		
		Query query = em.createNativeQuery("select tr.* "
				+ "from timesheetrow tr "
				+ "join timesheet t on tr.timesheetID = t.timesheetID "
				+ "where packageID = :workpackageId "
				+ "and t.approved = true", TimesheetRow.class)
				.setParameter("workpackageId", wpId);
		try {
			List<TimesheetRow> timesheetRows = query.getResultList();
			
			for(int i = 0; i < timesheetRows.size(); i++) {
				value = 0;
				currentValue = 0;
				currentValue += timesheetRows.get(i).getFridayHour();
				currentValue += timesheetRows.get(i).getMondayHour();
				currentValue += timesheetRows.get(i).getSaturdayHour();
				currentValue += timesheetRows.get(i).getSundayHour();
				currentValue += timesheetRows.get(i).getThursdayHour();
				currentValue += timesheetRows.get(i).getTuesdayHour();
				currentValue += timesheetRows.get(i).getWednesdayHour();
				currentValue = currentValue / 8.0;
				query = em.createNativeQuery("select e.paygrade "
						+ "from employee e join timesheet t "
						+ "on e.employeeID=t.employeeID join timesheetrow tr "
						+ "on t.timesheetID=tr.timesheetID where tr.timesheetrowID = :trID")
						.setParameter("trID", timesheetRows.get(i).getId());
				key = (String) query.getSingleResult();
				value = payLevelAmount.get(key);
				value += currentValue;
				payLevelAmount.remove(key);
				payLevelAmount.put(key, value);
			}
			
			return payLevelAmount;
		} catch (NoResultException e) {
			return null;
		}
	
	}
}
