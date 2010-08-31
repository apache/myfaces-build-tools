/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.myfaces.buildtools.maven2.plugin.builder.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation identifies a class as being a JSF component, an ancestor of
 * one, or a template for one.
 * <p>
 * A JSF component is a concrete class that is a subclass of UIComponent, and
 * is registered in a faces-config.xml file. It always implements the StateHolder
 * interface, and typically has several properties that can be defined from a
 * view file (jsp, facelets or other similar technology).
 * <p>
 * The myfaces-builder-plugin uses this annotation information to generate the
 * correct faces-config.xml declarations, thus ensuring that the config declarations
 * are consistent with the code. The plugin can also optionally use this information
 * to generate JSP tag classes, and concrete UIComponent implementations.
 * <p>
 * This tag can be used in several ways:
 * <ul>
 * <li>To mark an interface as one that declares component properties.
 * <li>To mark a class that will be used as a base class by other hand-written
 * classes.
 * <li>To mark a class as an actual concrete JSF Component class which should be
 * registered as a component in faces-config etc.
 * <li>To mark a class as a base class for which a concrete component subclass should
 * be created using code generation.
 * <li>To mark a class as a "template" from which a concrete component class should
 * be created using code generation.
 * </ul>
 * Any class or interface marked with this annotation will also have its "metadata"
 * stored in the metadata file that the builder plugin creates and stores in the
 * META-INF directory of the processed project.
 * <p>
 * Note that a JSPProperty annotation (on a method) is not processed unless the class
 * is marked with this JSPComponent annotation. 
 * 
 * <h2>Annotating an Interface</h2>
 * 
 * When an interface is marked with this annotation, then classes which implement that
 * interface and are themselves marked with this annotation inherit the JSFProperty
 * settings declared on the methods in the interface.
 * <p>
 * This allows common groups of JSF properties to be defined as an interface, and each
 * JSF component class that is expected to provide those properties simply declares that
 * it implements that interface. The normal java compilation rules ensure that the concrete
 * JSF component class implements all of the methods on the interface, and the builder
 * plugin ensures that the inherited JSFProperty settings are used.
 * 
 * <h2>Annotating an Abstract Base Class</h2>
 * 
 * When an abstract class is marked with this annotation, and the name attribute is
 * not set, then this is a base class that concrete components can extend. Any
 * JSFProperty settings defined on this class are inherited by subclasses.
 * 
 * <h2>Annotating a Concrete Component Class</h2>
 * 
 * When a non-abstract class is marked with this annotation, and the name attribute
 * is defined then an entry will be created in the generated faces-config.xml file
 * marking this class as a component. 
 * <p>
 * In addition, a JSP TagHandler class will be created using code-generation and an
 * entry will be created in the generated .tld file so that the tag class can be
 * used from JSP applications.
 * 
 * <h2>Annotating a Class for Subclass Generation</h2>
 * 
 * When an abstract class is marked with this annotation, the name attribute is
 * defined, and the "clazz" attribute is defined then code-generation will be
 * used to create a concrete UIComponent class which is a subclass of this class.
 * The subclass will be registered in the generated faces-config.xml file, a
 * JSP TagHandler class will be created and registered in the .tld file, etc.
 * <p>
 * Using code-generation to create a subclass allows automatic generation of
 * the "boilerplate code" that is commonly needed for implementing component
 * property getter/setter methods and the saveState/restoreState methods.
 * <p>
 * Any abstract property getter/setter methods on the annotated class will have
 * the appropriate implementation automatically written for it in the new
 * subclass. However the parent class can override this for any particular
 * method simply by providing a concrete implementation of the method.
 * 
 * <h2>Annotating a Class as a Component Template</h2>
 * 
 * When an abstract class is marked with this annotation, the name attribute
 * is set, the clazz attribute is set, and attribute template is set to "true"
 * then code-generation will be used to create a new class that is a "copy" of
 * the source code here. As for "subclass generation", any abstract methods will
 * have an appropriate implementation generated.
 * <p>
 * This approach allows a component "template" to be written as a normal class
 * (with the usual support tools provided by IDEs etc) while allowing the
 * actual UIComponent classes to have a hierarchy that is completely under
 * the control of the annotations. In particular, this is useful for generating
 * components for a JSF specification implementation, which is very specific
 * about the inheritance hierarchy of the standard component classes.
 * <p>
 * When using this "template" approach, the annotated class itself can (and
 * probably should be) package-scoped.
 * 
 * @author Leonardo Uribe (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface JSFComponent
{
    /**
     * The name of the component in a page. This is the name by which the
     * component will be referenced by the used (the name used to register
     * JSP TagHandlers for example). For example: "mycomp".
     * <p>
     * When a project is generating components that live in more than one
     * namespace, then the name can optionally include a namespace prefix,
     * for example "x:mycomp".
     * <p>
     * If a class is marked with the JSFComponent annotation, but does not
     * define the name attribute then it is a "base class" that other
     * UIComponent classes can extend (and therefore inherit any JSFProperty
     * definitions from the base class), but is not a component that can be
     * instantiated.
     * <p>
     * This property should never be set for an interface.
     * <p>
     * This property should only be set for an abstract class when code
     * generation is expected to automatically create a component based on
     * this one (ie when clazz and perhaps template are set).
     * <p>
     * This attribute is not inheritable.
     */
    String name() default "";

    /**
     * The fully-qualified-name of a concrete component class.
     * <p>
     * This attribute is only relevant when "name" is also set, ie the
     * annotation is indicating that a component is really being declared.
     * <p>
     * When this attribute is not defined then it is assumed that this
     * annotated class is the actual component class.
     * <p>
     * When this attribute is set to something other than the name of the
     * annotated class then the specified class is the one that the JSF
     * component registration in faces-config.xml will refer to. And if that
     * class does not exist in the classpath (which will normally be the
     * case) then code-generation will be triggered to create it.
     * <p>
     * This attribute is not inheritable.
     * <p>
     * The doclet-annotation equivalent of this attribute is named "class".
     */
    String clazz() default "";
    
    /**
     * The JSF Component Family that this component belongs to.
     * <p>
     * A UIComponent instance's "family" setting is used together with a "renderer-type" to
     * find a renderer for the component (see method UIComponent.getRenderer). Each
     * Renderer implementation is registered in a faces-config.xml file using a
     * (component-family, renderer-type) pair.
     * <p>
     * If this attribute is not set then the builder plugin will look for a
     * constant on the class with name COMPONENT_FAMILY. It is an error if a
     * concrete component has no such setting (but the value can be inherited from an
     * ancestor class).
     */
    String family() default "";
    
    /**
     * The type string used to create new instances of this component.
     * <p>
     * Components are registered in faces-config.xml using this type value, and
     * new instances are created via Application.createComponent(type).
     * <p>
     * If this attribute is not set, then the builder plugin will look for a
     * constant on the class with name COMPONENT_TYPE. It is an error if a
     * component has no such setting.
     * <p>
     * This should only be set on concrete component classes (or classes for which
     * code generation creates a concrete component class). The attribute is
     * not inheritable.
     */
    String type() default "";
    
    /**
     * Define the renderer-type attribute that (together with "family") is used to
     * look up a renderer for instances of a component.
     * <p>
     * See attribute "family" for more details.
     * <p>
     * If this attribute is not defined, the builder plugin looks for a constant
     * on the class with name DEFAULT_RENDERER_TYPE. It is an error if a
     * concrete component has no such setting (but the value can be inherited from an
     * ancestor class).
     * <p>
     * This attribute is inheritable.
     */
    String defaultRendererType() default "";
    
    /**
     * Indicate if the component is allowed to have other UIComponent instances
     * as children (ie "nested within it" in a page).
     * <p>
     * This information can be inserted into .tld files and similar to present
     * users with an error message if an attempt is made to define child
     * components for a component that does not support them.
     * <p>
     * Code generation might also use this information to ensure that an exception
     * is thrown if an attempt to add child components occurs at runtime.
     * <p>
     * Note that this is related to, but not quite the same as, the "bodyContent"
     * attribute. That attribute prevents anything being nested inside the component
     * in the page, while this prevents UIComponents being nested.
     * 
     * ?? TODO: but in JSF1.2, isn't static text a transient child??
     * <p>
     * This attribute is inheritable.
     */
    boolean canHaveChildren() default false;
    
    /**
     * TODO: this is hopefully unused, and can be deleted.
     * <p>
     * Indicate that this component should not be defined on faces-config.xml.
     * Anyway, if this is true or false does not have any significative impact. 
     */
    boolean configExcluded() default false;
    
    /**
     * The name of the JSP Tag class that should be generated for this component.
     * <p>
     * It is an error to set this attribute if the annotated class is not a
     * concrete JSF component.
     * <p>
     * TODO: ? WHat happens if a class is a concrete component but this is not
     * set? Is a tagClass name automatically created? Or is no tag class created?
     */
    String tagClass() default "";
     
    /**
     * Tag super class that the generated tag class extends.
     * <p>
     * The generated tag class for this component may need to be a subclass of
     * different tag base classes depending upon the type of this component.
     * For example, the JSF standard provides the following tag classes:
     * <ul>
     * <li>UIComponentTag (the default tag base class)
     * <li>UIComponentBodyTag, for tags that "need to process their tag bodies".
     * Examples from the JSF standard are h:panelGrid, h:panelGroup, h:dataTable,
     * h:column.
     * </ul>
     * <p>
     * This is optional; when not defined, the standard UIComponentTag class is
     * used. This is appropriate for most JSF components.
     */
    String tagSuperclass() default "";
    
    /**
     * Indicate tag handler class used for this component on facelets.
     * 
     */
    String tagHandler() default "";
    
    /**
     * Indicate if the element accept inner elements (JSP) or not (empty).
     */
    String bodyContent() default "";
    
    /**
     * A Short description of the purpose of this UIComponent.
     * <p>
     * This information is output into the .tld and faces-config.xml files as help
     * for users of this component, and may be displayed by IDEs and similar tools.
     * <p>
     * If not set, then the javadoc "summary" sentence for the class will be
     * used. If there is no javadoc for the class then the description will be
     * an empty string (but that will never happen, right?).
     * <p>
     * Note that tld or faces-config files also have a "long description" field.
     * That value is always the complete javadoc for the component class.
     * <p>
     * This attribute is not inheritable.
     */
    String desc() default "";
    
    /**
     * Serial UID to be put on class when generated.
     * <p>
     * This should be set only for "component template" code generation, ie
     * where the superClass attribute is set. In this case, the generated
     * class will have a serialVersionUID constant defined which is set to
     * this value.
     * <p>
     * TODO: is this valid? Why can't the code-generation just add a
     * serialUid value based upon what version of the template was used?
     * Maybe because the template generates different stuff based upon
     * what attributes are defined here?
     */
    String serialuid() default "";
    
    /**
     * Interfaces that generated classes should implement.
     * <p>
     * Used only when "component template" code generation is used. When
     * defined, then the generated class will implement the listed
     * interfaces in addition to the interfaces that this annotated class
     * implements.
     */
    String implementz() default "";
    
    /**
     * Indicate if the pattern used to generate component class code is
     * template (true) or subclass (false).
     */
    boolean template() default false;
    
    /**
     * The name of the default client event to be return on method
     * ClientBehaviorHolder.getDefaultEventName(). This property is
     * JSF 2.0 specific, and the component must implement 
     * javax.faces.component.behavior.ClientBehaviorHolder interface.  
     * 
     * @since 1.0.4
     */
    String defaultEventName() default "";

    /**
     * 
     * @since 1.0.5
     */
    boolean composite() default false;
}
