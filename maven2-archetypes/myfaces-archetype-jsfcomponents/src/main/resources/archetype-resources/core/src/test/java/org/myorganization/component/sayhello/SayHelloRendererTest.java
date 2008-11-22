package org.myorganization.component.sayhello;

import junit.framework.Test;
import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockRenderKitFactory;
import org.apache.shale.test.mock.MockResponseWriter;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Class to test the behaviour of the SayHello renderer
 */
public class SayHelloRendererTest extends AbstractJsfTestCase
{

    public static Test suite()
    {
        return null;
    }

    private MockResponseWriter writer ;
    private SayHello sayHello;

    public SayHelloRendererTest(String name)
    {
        super(name);
    }

    public void setUp() throws Exception
    {
        super.setUp();

        sayHello = new SayHello();
        sayHello.setFirstName("John");
        sayHello.setLastName("Smith");

        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);
        // TODO remove these two lines once shale-test goes alpha, see MYFACES-1155
        facesContext.getViewRoot().setRenderKitId(MockRenderKitFactory.HTML_BASIC_RENDER_KIT);
        facesContext.getRenderKit().addRenderer(
                sayHello.getFamily(),
                sayHello.getRendererType(),
                new SayHelloRenderer());
    }

    public void tearDown() throws Exception
    {
        super.tearDown();
        sayHello = null;
        writer = null;
    }

    public void testEncodeEnd()  throws Exception
    {
        sayHello.encodeEnd(facesContext);
        facesContext.renderResponse();

        String output = writer.getWriter().toString();

        assertEquals("Hello John Smith!", output);
        assertNotSame("Bye John Smith!", output);
    }

}
