package javax.faces.component;

/**
 * 
 * This is a base component 
 * 
 * @author Leonardo
 * @JSFComponent
 *   type = "javax.faces.Component"
 *   family = "javax.faces.Component"
 *   desc = "base component"
 */
public class UIComponent
{

    private String _id = null;
    
    /**
     * Get a string which uniquely identifies this UIComponent within the
     * scope of the nearest ancestor NamingContainer component. The id is
     * not necessarily unique across all components in the current view.
     * 
     * @JSFProperty
     */
    public String getId()
    {
        return _id;
    }
    
    public void setId(String id)
    {
        _id = id;
    }
}
