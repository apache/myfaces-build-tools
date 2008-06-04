package org.apache.myfaces.custom.focus2;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

/**
 * @JSFComponent
 *   name = "s:focus2"
 *   class = "org.apache.myfaces.custom.focus2.HtmlFocus"
 *   superClass = "org.apache.myfaces.custom.focus2.AbstractHtmlFocus"
 *   tagClass = "org.apache.myfaces.custom.focus2.HtmlFocusTag"
 *   
 * @author Rogerio Pereira Araujo (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlFocus extends UIInput
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.Focus2";
    public static final String COMPONENT_FAMILY = "javax.faces.Output";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Focus2";

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public AbstractHtmlFocus()
    {
        super.setRendererType(DEFAULT_RENDERER_TYPE);
    }

    /**
     * Defines that the first element of the form should receive the 
     * focus by default
     * (if the overrideFocusId-attribute is not specified, no error has
     *  been queued and the focus
     * has not already been set).
     * 
     * @JSFProperty
     *   defaultValue = "true"
     */
    public abstract boolean isFocusOnFirst();

    /**
     * Defines that the first element of the form with an associated error should
     * receive the focus by default (if the override focus-id attribute wasn't set).
     * 
     * @JSFProperty
     *   defaultValue = "true"
     * @return
     */
    public abstract boolean isFocusOnError();

    /**The id (locally in this naming container) of the component which
     *  should receive the focus.
     * Overrides all other behaviour if set.
     * 
     * @JSFProperty
     */
    public abstract String getOverrideFocusId();

    /**
     * Defines an id of a command-button or command-link that will be
     * focussed and submitted when the enter-key is pressed.
     * 
     * @JSFProperty
     */
    public abstract String getFocusAndSubmitOnEnter();

    /**
     * The client-id (fully specified as a concatenation of the id of
     * this component and all naming container
     * parent ids) of the component which receives the focus (works only if
     * overrideFocusId hasn't been set and no error has been queued).
     *
     * The value will automatically be updated when the focus is changed.
     * 
     * @JSFProperty
     * @see javax.faces.component.UIInput#setValue(java.lang.Object)
     */
    public void setValue(Object value)
    {
        super.setValue(value);
    }

    public Object getValue()
    {
        return super.getValue();
    }

    public void updateModel(FacesContext context)
    {
        super.updateModel(context);
    }

    public void processValidators(FacesContext context)
    {
        super.processValidators(context);
    }

}