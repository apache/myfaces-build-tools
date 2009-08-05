package org.myorganization.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * @JSFConverter
 *   name = "mycomponents:phoneNumberConverter"
 *   tagClass = "org.myorganization.converter.PhoneNumberConverterTag"
 */
public class PhoneNumberConverter
        implements Converter
{
    public static final String CONVERTER_ID = "javax.faces.PhoneNumber";
    
    public Object getAsObject(FacesContext context, UIComponent component, String value)
            throws ConverterException
    {
        String [] strPhone = value.split("-");
        PhoneNumber objPhone = null;
        if(strPhone.length == 2) 
        {
            objPhone = new PhoneNumber(strPhone[0], strPhone[1]);
        } 
        else if(strPhone.length == 1)
        {
            objPhone = new PhoneNumber(strPhone[0]);
        }
        
        return objPhone;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value)
            throws ConverterException
    {
        if(value != null)
        {
            PhoneNumber phone = (PhoneNumber)value;
            return phone.getAreaCode() + "-" + phone.getNumber();
        }
        
        return null;
    }

}
