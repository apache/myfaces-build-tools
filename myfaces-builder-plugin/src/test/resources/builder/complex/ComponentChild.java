/**
 * The ComponentChild component.
 * <p>
 * This is a child component. It does nothing useful.
 * 
 * @JSFComponent
 *   name="foo"
 *   type="foo"
 *   family="foo"
 *   defaultRendererType="FooRenderer"
 */
public class ComponentChild extends ComponentBase implements ComponentInterface
{
    String prop1;
    boolean prop2;
    
    /**
     * Implement the abstract property defined on the base class.
     * Note that because this method is not annotated with the
     * JSFProperty annotation, this comment does not become the
     * documentation for the property itself.
     */
    public String getProp1()
    {
        return prop1;
    }

    public void setProp1(String prop1)
    {
        this.prop1 = prop1;
    }

    /**
     * The prop2 property.
     * <p>
     * Some dummy prop2 documentation.
     *
     * @JSFProperty
     */
    public boolean getProp2() {
        return prop2;
    }
    
    public void setProp2(boolean prop2) {
        this.prop2 = prop2;
    }

    /**
     * Implement the method defined on ComponentInterface. Because this method does
     * not use the JSFProperty annotation, the property settings are taken from the
     * parent interface, not here.
     */
    public String getIfaceProp()
    {
        return null;
    }

    public void setIfaceProp(String val)
    {
    }
}
