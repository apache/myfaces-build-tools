package generation;

/**
 * A class for which a concrete component will be created using "subclass mode" code-generation.
 * 
 * @JSFComponent
 *   name="fooFromSubclass"
 *   type="foo"
 *   family="foo"
 *   defaultRendererType="FooRenderer"
 *   class="generation.SubclassComponent"
 */
public abstract class ComponentGenSubclass extends ComponentBase implements ComponentInterface
{
}
