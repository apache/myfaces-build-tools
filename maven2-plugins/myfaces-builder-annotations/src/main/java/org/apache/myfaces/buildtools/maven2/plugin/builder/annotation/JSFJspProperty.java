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
 * Defines a logical property of a component that is accessed via the
 * component's Attributes map, rather than javaBean getter/setter methods.
 * <p>
 * These logical properties are accessible from views (eg available as
 * JSP tag attributes) just like properties declared with the JSFProperty
 * annotation.
 * <p>
 * A class or interface that defines just one logical property may use this
 * annotation directly; to declare more than one logical property should
 * use the JSFJspProperties annotation to group a set of these. Classes that
 * implement interfaces with this annotation, or subclass a base class that
 * has this annotation, will inherit the declared attributes.
 * <p>
 * This annotation should only be applied to classes that also have the JSFComponent
 * and JSFValidator annotation.
 * 
 * @author Leonardo Uribe (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface JSFJspProperty
{

    /**
     * The name that identifies this attribute.
     * <p>
     * This String is the key used to look up the attribute in the component's
     * Attributes map.
     * <p>
     * Examples: border, id, value
     */
    String name() default "";
    
    /**
     * The type of this attribute.
     * <p>
     * This must be a fully-qualified class name of a java type.
     * <p>
     * TODO: doesn't the JSF spec also allow short-cuts for common types here?
     */
    String returnType() default "";
    
    /**
     * Define whether this attribute is mandatory for the associated component,
     * ie whether it is an error to create an instance of the component and
     * not to provide a value for this attribute.
     */
    boolean required() default false;
    
    /**
     * Define if this tag is excluded from tld.
     * <p>
     * This is very ugly feature, and should be used as little as possible. It
     * allows a parent class (or interface) to declare logical properties, then
     * a subclass to "undeclare" them. This is completely broken OO design,
     * but is required in a few rare cases by the JSF specification. 
     */
    boolean tagExcluded() default false;
    
    /**
     * A long description of the purpose of this attribute.
     * <p>
     * This is commonly shown as help in IDEs.
     */
    String longDesc() default "";
    
    /**
     * A short description of the purpose of this attribute.
     * <p>
     * This is commonly shown as a "tool tip" or popup-help in IDEs.
     */
    String desc() default "";
    
    /**
     * Indicate if this property is inherited from a parent tag class or not.
     * 
     * @since 1.0.6
     */
    boolean inheritedTag() default false;

    /**
     * Use saveAttachedXXX and restoreAttachedXXX to save and restore state
     * 
     * @since 1.0.6
     */
    boolean stateHolder() default false;
    
    /**
     * Indicate that the getter and setter does not evaluate EL or ValueBinding expressions.
     * 
     * @since 1.0.6
     */
    boolean literalOnly() default false;
}
