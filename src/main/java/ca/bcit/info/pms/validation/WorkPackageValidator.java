package ca.bcit.info.pms.validation;

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

@FacesValidator("ca.bcit.pms.validation.WorkPackageValidator")
public class WorkPackageValidator implements Validator {
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
        
        List<TimesheetRow> tsRows = editTsController.getTimesheet().getTimesheetRows();
        
        int count = 0;

        // Count occurrences
        for(TimesheetRow row : tsRows) {
            if (row.getWorkPackage().getId() == value)
                count++;
        }

        logger.info("\n\tCOUNT::"+count);
        // If more than 'current row' present raise error
        if(count > 1) {
            msg = new FacesMessage("WorkPackage validation failed",
                    "WorkPackage must be unique for each row");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }        
    }
}
