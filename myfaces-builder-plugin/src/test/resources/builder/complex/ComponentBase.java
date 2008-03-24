/**
 * A base component class.
 * <p>
 * This is a base component. It has no name, so is not a concrete component.
 * 
 * @mfp.component
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
     * @mfp.property
     */
    public abstract String getProp1();
    
    public abstract void setProp1(String prop1);
}
