package javax.faces.component.html;

import javax.faces.component.UIComponent;

/**
 * 
 * This is a component that inherits from UIComponent 
 * 
 * @author Leonardo
 * @JSFComponent
 *   type = "javax.faces.HtmlCommandButton"
 *   family = "javax.faces.HtmlCommandButton"
 *   tagClass = "javax.faces.component.html.HtmlCommandButtonTag"
 *   desc = "base component"
 */
public class HtmlCommandButton extends UIComponent
{

    /**
     * @JSFProperty
     */
    public Object getValue(){
        return null;
    }
    
    /**
     * @JSFProperty
     */    
    public boolean isRendered(){
        return false;
    }
}
