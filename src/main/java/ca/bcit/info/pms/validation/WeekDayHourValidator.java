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
    @Inject
    EditTimesheetController editTsController;

    private static final Logger logger = LogManager.getLogger(WeekDayHourValidator.class);

    /**
     * validates uniqueness of WorkPackage
     * among rows in a timesheet.
     */
    @Override
    public void validate(FacesContext facesContext,
            UIComponent component, Object value) {
        FacesMessage msg;
        
        logger.info("component::"+component);
        logger.info("component.ClientId::"+component.getClientId());
        logger.info("component.id::"+component.getId());
        
        try {
            double hrs = (double) value;
            if (hrs > 24.0) {
                logger.info("\n\t[IN-IF]::Value::"+value);
                msg = new FacesMessage("Week-day hours invalid", "Must not be > 24");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        } catch (Exception ex) {
            msg = new FacesMessage("Week-day hours invalid","Not a valid decimal");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}