package ca.bcit.info.pms.access;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
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
	
	public void persist(final TimesheetRow timesheetRow) {
		em.persist(timesheetRow);
	}
	
	public double getTotalManDays(final int wpId) {
		double totalManDays = 0;
		Query query = em.createNativeQuery("select * "
				+ "from timesheetrow "
				+ "where packageID = :workpackageId", TimesheetRow.class)
				.setParameter("workpackageId", wpId);
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
	}
	
}
