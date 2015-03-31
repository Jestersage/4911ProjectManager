package ca.bcit.info.pms.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.bcit.info.pms.controller.EditTimesheetController;

@FacesValidator("ca.bcit.pms.validation.WeekDayHourValidator")
public class WeekDayHourValidator implements Validator {
    private static final Logger logger = LogManager.getLogger(WorkPackageValidator.class);

    /**
     * validates uniqueness of WorkPackage
     * among rows in a timesheet.
     */
    @Override
    public void validate(FacesContext facesContext,
            UIComponent component, Object value) {
        FacesMessage msg;
        
        logger.info("\t\nWeekDayHourValidator.value::"+value);
            if ((double) value > 24.0) {
                logger.info("\t\nIf [value] > 24::value="+((double)value));
                msg = new FacesMessage("Week-day hours invalid", "Must not be > 24");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        
    }
}
