import org.apache.myfaces.buildtools.annotation.*;

/**
 * The Foo component defined via java15 annotations.
 * <p>
 * This is an annotated foo component. It does nothing useful.
 */
@JSFComponent(name="foo", type="foo", family="foo", defaultRendererType="FooRenderer")
public class Foo
{
    String prop1;
    
    /**
     * The prop1 property.
     * <p>
     * Some dummy prop1 documentatoin.
     */
    @JSFProperty
    public String getProp1() {
        return prop1;
    }
    
    public void setProp1(String prop1) {
        this.prop1 = prop1;
    }
}
