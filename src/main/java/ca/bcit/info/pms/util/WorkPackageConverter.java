/**
 * 
 */
package ca.bcit.info.pms.util;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.bcit.info.pms.model.WorkPackage;
import ca.bcit.info.pms.service.impl.TimesheetServiceImpl;

/**
 * @author Finlay
 *
 */
@ManagedBean(name = "workPackageConverterBean") 
@FacesConverter(value = "workPackageConverter")
public class WorkPackageConverter implements Converter {
    private static final Logger logger = 
            LogManager.getLogger(WorkPackageConverter.class);
    
    @PersistenceContext(unitName = "pms-persistence-unit")
    // I include this because you will need to 
    // lookup  your entities based on submitted values
    private transient EntityManager em;  

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component,
            String value) {
        if ( value == null || value.isEmpty() ) 
            return null;
        
        try {
            return em.find(WorkPackage.class, Integer.valueOf(value));
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(String.format("%s is not a valid workPackageID", value)), e);
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object obj) {
        //This will return view-friendly output for the dropdown menu
        if ( obj == null )
            return "";
        
        if ( obj instanceof WorkPackage ){
            return String.valueOf(((WorkPackage) obj).getId());
        } else {
            throw new ConverterException(new FacesMessage(String.format("%s is not a valid User", obj)));
        }
      
    }
}