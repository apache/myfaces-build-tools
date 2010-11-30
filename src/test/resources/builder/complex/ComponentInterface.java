/**
 * An interface that defines component properties.
 * <p>
 * This is an interface component.
 * 
 * @JSFComponent
 *   type="iface"
 *   family="iface"
 *   defaultRendererType="BaseRenderer"
 */

public interface ComponentInterface
{
    /**
     * The ifaceProp property.
     * <p>
     * Some dummy ifaceProp documentation.
     *
     * @JSFProperty
     */
    String getIfaceProp();
    
    void setIfaceProp(String val);
}
