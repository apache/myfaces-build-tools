package org.myorganization.component.sayhello;

import javax.faces.render.Renderer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;
import java.io.IOException;

/**
 * The renderer for my component
 */
public class SayHelloRenderer extends Renderer
{

    public void decode(FacesContext facesContext, UIComponent uiComponent)
    {
        super.decode(facesContext, uiComponent);
        // nothing to decode
    }

    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        super.encodeBegin(facesContext, uiComponent);
        // no need to use encodeBegin.
    }

    public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        super.encodeChildren(facesContext, uiComponent);
        // this component does not have children
    }

    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        super.encodeEnd(facesContext, uiComponent);

        if (!uiComponent.isRendered())
        {
            return;
        }

        ResponseWriter writer = facesContext.getResponseWriter();

        SayHello sayHello = (SayHello) uiComponent;

        String firstName = sayHello.getFirstName();
        String lastName = sayHello.getLastName();

        writer.write("Hello ");

        if (firstName != null)
        {
            writer.write(firstName);
        }

        if (lastName != null)
        {
            writer.write(" "+lastName);
        }

        writer.write("!");
    }
}
