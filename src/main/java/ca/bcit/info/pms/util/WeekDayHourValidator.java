package ca.bcit.info.pms.util;

import java.util.List;

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
import ca.bcit.info.pms.model.TimesheetRow;

@FacesValidator("ca.bcit.pms.util.WeekDayHourValidator")
public class WeekDayHourValidator implements Validator {
    @Inject
    EditTimesheetController editTsController;

    private static final Logger logger = LogManager.getLogger(WorkPackageValidator.class);

    /**
     * validates uniqueness of WorkPackage
     * among rows in a timesheet.
     */
    @Override
    public void validate(FacesContext facesContext,
            UIComponent component, Object value) {
        FacesMessage msg;
        
        try {
            double hrs = (double) value;
            if (hrs > 24.0) {
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
