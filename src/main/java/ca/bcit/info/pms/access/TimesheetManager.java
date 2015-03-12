package ca.bcit.info.pms.access;

import java.io.Serializable;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.model.Timesheet;

@Dependent
@Stateless
public class TimesheetManager implements Serializable {
    @PersistenceContext
    private EntityManager entityManager;
    // @PersistenceContext(unitName = "pms-persistence-unit") private EntityManager em;

    //private static final Logger logger = LogManager.getLogger(TimesheetManager.class);


    public Timesheet findById(final long id) {

        return this.entityManager.find(Timesheet.class, id);
    }

    //Find Timesheet by owner
    /*public Timesheet findByOwner(final Employee employee) {

        return this.entityManager.find(Timesheet.class, owner);
    }*/


}
