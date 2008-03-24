/**
 * An interface that defines component properties.
 * <p>
 * This is an interface component.
 * 
 * @mfp.component
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
     * @mfp.property
     */
    String getIfaceProp();
    
    void setIfaceProp(String val);
}
