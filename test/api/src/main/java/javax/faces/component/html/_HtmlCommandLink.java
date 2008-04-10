package javax.faces.component.html;

import javax.faces.component.UIComponent;

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
}
