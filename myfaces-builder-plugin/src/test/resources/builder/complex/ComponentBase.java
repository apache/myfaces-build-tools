/**
 * A base component class.
 * <p>
 * This is a base component. It has no name, so is not a concrete component.
 * 
 * @JSFComponent
 *   type="base"
 *   family="base"
 *   defaultRendererType="BaseRenderer"
 */

public abstract class ComponentBase
{
    /**
     * The prop1 property.
     * <p>
     * Some dummy prop1 documentation.
     *
     * @JSFProperty
     */
    public abstract String getProp1();
    
    public abstract void setProp1(String prop1);
}
