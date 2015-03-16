/**
 * 
 */
package ca.bcit.info.pms.util;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ca.bcit.info.pms.model.Project;

/**
 * @author Huanan
 *
 */

@ManagedBean(name = "projectConverterBean") 
@FacesConverter(value = "projectConverter")
public class ProjectConverter implements Converter {

	@PersistenceContext(unitName = "pms-persistence-unit")
    // I include this because you will need to 
    // lookup  your entities based on submitted values
    private transient EntityManager em;  

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component,
            String value) {
      // This will return the actual object representation
      // of your Category using the value (in your case 52) 
      // returned from the client side
      return em.find( Project.class, value); 
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        //This will return view-friendly output for the dropdown menu
    	
    	if ( o instanceof Project )
        {
            return ((Project)o).getId().toString(); 
        }
        else
        {
            throw new IllegalArgumentException( "Cannot convert non-project object in ProjectConverter" );
        }
      
    }
}