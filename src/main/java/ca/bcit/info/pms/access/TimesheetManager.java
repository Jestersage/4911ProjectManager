package ca.bcit.info.pms.access;

import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.Timesheet;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Stateless
@LocalBean
public class TimesheetManager implements Serializable {
    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = LogManager.getLogger(TimesheetManager.class);


    public Timesheet findById(final int id) {

        return this.entityManager.find(Timesheet.class, id);
    }

    /**
     * Get timesheet belong to a given employee
     *
     * @param employee - owner of timesheet.
     * @return a List of timesheets of the given owner.
     */
    public List<Timesheet> findAllByOwner(final Employee employee) {
        TypedQuery<Timesheet> query = entityManager.createQuery(
                "SELECT t FROM Timesheet t WHERE t.owner = :emp",
                Timesheet.class);
        query.setParameter("emp", employee);

        try
        {
            List<Timesheet> match = query.getResultList();
            //logger.info( "match found: " + match.getOwner().getId() );
            return match;
        } catch ( NoResultException nre )
        {
            //logger.warn( "timesheet not found for username " + employee.getId() );
            return null;
        }
    }
    
    /**
     * Get timesheet belong to a given employee and given week.
     *
     * @param e owner of timesheet.
     * @param weekEnd timesheet's week end day
     * @return a timesheet of a given employee and given week.
     */
    public Timesheet find(final Employee e, final Date weekEnd) {
        TypedQuery<Timesheet> query = entityManager.createQuery(
                "SELECT t FROM Timesheet t WHERE t.owner = :employee "
                        + "AND t.weekEnding = :endWeek", Timesheet.class);
        query.setParameter("employee", e);
        query.setParameter("endWeek", weekEnd);

        Timesheet result;
        try {
            result = query.getSingleResult();
        } catch (NoResultException nre) {
            result = null;
        }

        return result;
    }
    
    /**
     * @return all timesheets stored.
     */
    public List<Timesheet> getAllTimesheets(){
        TypedQuery<Timesheet> query = entityManager.createQuery("select t "
                + "from Timesheet t ORDER BY t.weekEnding DESC", Timesheet.class);
        return query.getResultList();
    }

    //delete a timesheet
    public void remove(final Timesheet timesheet) {
        Timesheet ts = findById(timesheet.getId());
        entityManager.remove(ts);
    }

    public void persist(final Timesheet timesheet) {
        entityManager.persist(timesheet);
        entityManager.flush();
    }
    
    /**
     * Updates a timesheet.
     *
     * @param timesheet timesheet to store
     * @return new timesheet after update
     */
    public void merge(final Timesheet timesheet) {
        logger.info("merge:" + timesheet.toString());
        entityManager.merge(timesheet);
    }
    
    //total
    public void getTotal (final Timesheet timesheet){
//        Timesheet ts = findById(timesheet.getId());
//        Query query = entityManager.createNativeQuery("select * "
//                + "from timeshee "
//                + "where packageID = :workpackageId", Timesheet.class);

    }

    /**
     * @param empId timesheet approver's employee id.
     * @return a list of timesheets to be approved by this employee.
     */
    public List<Timesheet> getTimesheetsToApprove(final String empId) {
        TypedQuery<Timesheet> query = entityManager
                .createQuery("SELECT t FROM Timesheet t, " +
                        "IN (t.owner) AS e " +
                        "WHERE e.timesheetApprover.id = :approverId " +
                        "AND t.submitted = true " +
                        "AND t.approved IS NULL " +
                        "ORDER BY t.owner.id, t.weekEnding", Timesheet.class);
        query.setParameter("approverId", empId);
        List<Timesheet> timesheets = query.getResultList();
        return timesheets;
    }
    
    /**
     * @param empId timesheet's owner's employee id
     * @return a list of all submitted and approved timesheets by this employee.
     */
    public List<Timesheet> getAllApprovedTimesheets(final String empId) {
        TypedQuery<Timesheet> query = entityManager
                .createQuery("SELECT t FROM Timesheet t " +
                        "WHERE t.owner.id = :empId " +
                        "AND t.submitted = true " +
                        "AND t.approved = true", Timesheet.class);
        query.setParameter("empId", empId);
        return query.getResultList();
    }
    

    public List<Timesheet> getAllPendingTimesheets(final String empId) {
        TypedQuery<Timesheet> query = entityManager
                .createQuery("SELECT t from Timesheet t " +
                        "WHERE t.owner.id = :empId " +
                        "AND ( (t.submitted = true AND t.approved IS NULL) " +
                        "OR (t.submitted = false AND t.approved = false) )", Timesheet.class);
        query.setParameter("empId", empId);

        return query.getResultList();
    }

    public BigDecimal getBankedFlexTime(final String id) {
        TypedQuery<BigDecimal> query = entityManager
                .createQuery("SELECT SUM(t.flextime) FROM Timesheet t " +
                        "WHERE t.owner.id = :empId " +
                        "AND t.approved = true", BigDecimal.class);
        query.setParameter("empId", id);

        try {
            BigDecimal hours = query.getSingleResult();
            logger.info("banked flextime: " + hours);
            return hours;
        } catch (NoResultException nre) {
            logger.error("Error getting banked flextime for employee with id: " + id);
            return null;
        }
    }

    public BigDecimal getSpentFlexTime(final String id) {
        TypedQuery<BigDecimal> query = entityManager
                .createQuery("SELECT SUM(tsr.saturdayHour) + " +
                            "SUM(tsr.sundayHour) + " +
                            "SUM(tsr.mondayHour) + " +
                            "SUM(tsr.tuesdayHour) + " +
                            "SUM(tsr.wednesdayHour) + " +
                            "SUM(tsr.thursdayHour) + " +
                            "SUM(tsr.fridayHour) " +
                        "FROM Timesheet t, " +
                        "IN (t.timesheetRows) AS tsr " +
                        "WHERE t.owner.id = :empId " +
                        "AND t.approved = true " +
                        "AND (tsr.workPackage.project.id = '10' " +
                            "AND tsr.workPackage.packageNum = 'FLEX')", BigDecimal.class);
        query.setParameter("empId", id);

        try {
            BigDecimal hours = query.getSingleResult();
            logger.info("spent flextime: " + hours);
            return hours;
        } catch (NoResultException nre) {
            logger.error("Error getting spent flextime for employee with id: " + id);
            return null;
        }
    }
}
