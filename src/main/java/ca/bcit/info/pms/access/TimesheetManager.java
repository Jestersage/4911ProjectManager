package ca.bcit.info.pms.access;

import java.io.Serializable;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.Timesheet;

@Stateless
@LocalBean
public class TimesheetManager implements Serializable {
    @PersistenceContext
    private EntityManager entityManager;
    
    //private static final Logger logger = LogManager.getLogger(TimesheetManager.class);


    public Timesheet findById(final long id) {

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
                "SELECT t FROM Timesheet t WHERE t.employee = :employeeId",
                Timesheet.class);
        query.setParameter("employeeId", employee.getId());

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
     * @return all timesheets stored.
     */
    public List<Timesheet> getAllTimesheets(){
        TypedQuery<Timesheet> query = entityManager.createQuery("select t "
                + "from Timesheet t ORDER BY t.endWeek DESC", Timesheet.class);
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
        entityManager.merge(timesheet);
    }
    
    //total
    public void getTotal (final Timesheet timesheet){
//        Timesheet ts = findById(timesheet.getId());
//        Query query = entityManager.createNativeQuery("select * "
//                + "from timeshee "
//                + "where packageID = :workpackageId", Timesheet.class);

    }

   
}
