package javax.faces.component.html;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.convert.Converter;
import javax.faces.validator.Validator;

/**
 * 
 * This is a component that inherits from UIComponent 
 * 
 * @author Leonardo
 * @JSFComponent
 *   class = "javax.faces.component.html.HtmlCommandLink"
 *   type = "javax.faces.HtmlCommandLink"
 *   family = "javax.faces.HtmlCommandLink"
 *   tagClass = "javax.faces.component.html.HtmlCommandLinkTag"
 *   desc = "base component"
 */
abstract class _HtmlCommandLink extends UIComponent
{

    /**
     * @JSFProperty
     */
    public abstract Object getValue();
    
    /**
     * @JSFProperty
     */    
    public abstract boolean isRendered();
    
    /**
     * @JSFProperty
     *   stateHolder="true"
     */
    public abstract String getModel();
    
    /**
     * @JSFProperty
     */    
    public abstract Converter getConverter();
    
    /**
     * @JSFProperty
     */    
    public abstract List getListItems();
    
    /**
     * @JSFProperty
     */
    public abstract Validator getValidator();
    
}
