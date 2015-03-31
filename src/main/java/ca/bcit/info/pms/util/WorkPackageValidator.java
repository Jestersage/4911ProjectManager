package ca.bcit.info.pms.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.bcit.info.pms.model.WorkPackage;

@FacesValidator("ca.bcit.pms.util.WorkPackageValidator")
public class WorkPackageValidator implements Validator {
    private static final Logger logger = LogManager.getLogger(WorkPackageValidator.class);

    @Override
    public void validate(FacesContext facesContext,
            UIComponent component, Object value) {
        // TODO Validate WorkPackage ID
        //          Value == WorkPackage.id (Integer)
            FacesMessage msg = 
                    new FacesMessage("WorkPackage validation failed",
                            "Value is not a WorkPackage");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        
    }
}
