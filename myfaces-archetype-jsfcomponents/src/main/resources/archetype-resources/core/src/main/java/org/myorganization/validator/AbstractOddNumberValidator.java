package org.myorganization.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.myfaces.commons.validator.ValidatorBase;

/**
 * @JSFValidator
 *   name = "mycomponents:validateOddNum"
 *   class = "org.myorganization.validator.OddNumberValidator"
 *   tagClass = "org.myorganization.validator.OddNumberValidatorTag"
 */
public abstract class AbstractOddNumberValidator extends ValidatorBase
{
    public static final String VALIDATOR_ID = "org.myorganization.validator.OddNumberValidator";
    
    public void validate(FacesContext arg0, UIComponent component, Object value)
            throws ValidatorException
    {
        if(!(value instanceof Integer)) {
            throw new ValidatorException(new FacesMessage("Please enter an integer."));
        }
        
        int intVal = ((Integer)value).intValue();
        if(intVal % 2 == 0) {
            throw new ValidatorException(new FacesMessage("Please enter an odd number."));
        }
    }    
}
