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

@FacesValidator("ca.bcit.pms.validation.UniqueEmployeeIdValidator")
public class UniqueEmpIdValidator implements Validator {

    @Inject
    EmployeeService employeeService;

    private static final Logger logger = LogManager.getLogger(UniqueEmpIdValidator.class);

    /**
     * validates uniqueness of WorkPackage
     * among rows in a timesheet.
     */
    @Override
    public void validate(FacesContext facesContext,
            UIComponent component, Object value) {
        FacesMessage msg;

        logger.info("unique employee id validator called.");

        final String empId = (String) value;
        final Employee match = employeeService.findEmployeeById(empId);

        if (match != null) {
            logger.info("employee id not unique.");

            msg = new FacesMessage("This employee ID must be unique; this one already exits. Please try another one.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}