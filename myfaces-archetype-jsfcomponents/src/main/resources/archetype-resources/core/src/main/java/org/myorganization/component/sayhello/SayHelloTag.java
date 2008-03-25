package org.myorganization.component.sayhello;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentTag;

/**
 * The tag for my component
 */
public class SayHelloTag extends UIComponentTag
{

    private String firstName;
    private String lastName;

    /**
     * @see javax.faces.webapp.UIComponentTag#getComponentType()
     */
    public String getComponentType()
    {
        return SayHello.COMPONENT_TYPE;
    }

    /**
     * @see javax.faces.webapp.UIComponentTag#getRendererType()
     */
    public String getRendererType()
    {
        return SayHello.DEFAULT_RENDERER_TYPE;
    }

    /**
     * @see javax.faces.webapp.UIComponentTag#setProperties
     */
     protected void setProperties(UIComponent component) {
          super.setProperties(component);

         FacesContext context = FacesContext.getCurrentInstance();
         Application application = context.getApplication();

        // firstName
        if (UIComponentTag.isValueReference(firstName))
        {
            component.setValueBinding("firstName", application.createValueBinding(firstName));
        }
        else
        {
            component.getAttributes().put("firstName", firstName);
        }

        // lastName
        if (lastName != null)
        {
            if (UIComponentTag.isValueReference(lastName))
            {
                component.setValueBinding("lastName", application.createValueBinding(lastName));
            }
            else
            {
                component.getAttributes().put("lastName", lastName);
            }
        }

     }

    public void release() {
        super.release();
        this.firstName = null;
        this.lastName = null;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
}
