package ca.bcit.info.pms.validation;

import ca.bcit.info.pms.model.Employee;
import ca.bcit.info.pms.service.EmployeeService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

@FacesValidator("ca.bcit.pms.validation.UniqueUsernameValidator")
public class UniqueUsernameValidator implements Validator {

    @Inject
    EmployeeService employeeService;

    private static final Logger logger = LogManager.getLogger(UniqueUsernameValidator.class);

    /**
     * validates uniqueness of WorkPackage
     * among rows in a timesheet.
     */
    @Override
    public void validate(FacesContext facesContext,
            UIComponent component, Object value) {
        FacesMessage msg;

        logger.info("unique username validator called.");

        final String username = (String) value;
        final Employee match = employeeService.findEmployeeByUsername(username);

        if (match != null) {
            logger.info("username not unique.");

            msg = new FacesMessage("Username must be unique; this one already exits. Please try another one.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}