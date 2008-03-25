package org.myorganization.component.sayhello;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * The sayHello component
 */
public class SayHello extends UIOutput
{
    public static final String COMPONENT_TYPE = "org.myorganization.SayHello";
    public static final String DEFAULT_RENDERER_TYPE = "org.myorganization.SayHelloRenderer";
    public static final String COMPONENT_FAMILY = "javax.faces.Output";

    private String firstName;
    private String lastName;

    public SayHello()
    {
        // do nothing
    }


    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);

        this.firstName = (String)values[1];
        this.lastName = (String)values[2];
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[3];
        values[0] = super.saveState(context);
        values[1] = firstName;
        values[2] = lastName;
        return values;
    }

    public String getFirstName()
    {
        if (firstName != null)
        {
            return firstName;
        }

        ValueBinding vb = getValueBinding("firstName");
        return (vb == null)? null : (String) vb.getValue(FacesContext.getCurrentInstance());
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        if (lastName != null)
        {
            return lastName;
        }

        ValueBinding vb = getValueBinding("lastName");
        return (vb == null)? null : (String) vb.getValue(FacesContext.getCurrentInstance());
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
}
