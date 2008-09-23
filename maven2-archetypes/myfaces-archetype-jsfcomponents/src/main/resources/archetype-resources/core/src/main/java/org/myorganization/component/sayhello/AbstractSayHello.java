package org.myorganization.component.sayhello;

import javax.faces.component.UIOutput;

/**
 * @JSFComponent
 *   name = "mycomponents:sayHello"
 *   class = "org.myorganization.component.sayhello.SayHello"
 *   tagClass = "org.myorganization.component.sayhello.SayHelloTag"
 */
public abstract class AbstractSayHello extends UIOutput
{
    public static final String COMPONENT_TYPE = "org.myorganization.SayHello";
    public static final String DEFAULT_RENDERER_TYPE = "org.myorganization.SayHelloRenderer";
    public static final String COMPONENT_FAMILY = "javax.faces.Output";

    /**
     * User's first name.
     * 
     * @JSFProperty
     */
    public abstract String getFirstName();


    /**
     * User's last name.
     * 
     * @JSFProperty
     */
    public abstract String getLastName();
    
    /**
     * Rendered property.
     * 
     * @JSFProperty
     *     defaultValue="true"     
     */
     public abstract boolean isRendered();                   
}
