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
 * This annotation identifies a class as a full component class with properties,
 * that is inheritable from other components, so it is included on the model. 
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
     * The name of the component in a page (ex: x:mycomp).
     */
    String name() default "";

    /**
     * The class that implements this component. If not exists this is generated.
     * If doclet is used, this property is referred as "class".
     */
    String clazz() default "";
    
    /**
     * The parent class which inherits this class. If not set, the superclass of the current class is used
     */
    String parent() default "";
    
    /**
     * The superClass which inherits this class if it is generated.
     */
    String superClass() default "";
    
    /**
     * Family that belongs this component. If not defined, it try to get the value of the field COMPONENT_FAMILY.
     */
    String family() default "";
    
    /**
     * Type of component. If not defined, it try to get the value of the field COMPONENT_TYPE.
     */
    String type() default "";
    
    /**
     * Renderer type used to identify which renderer class use. If not defined, it try to get the value of the field DEFAULT_RENDERER_TYPE.
     */
    String defaultRendererType() default "";
    
    /**
     * Indicate if the component can have children
     */
    boolean canHaveChildren() default false;
    
    /**
     * Tag class that match this component
     */
    String tagClass() default "";
    
    /**
     * Tag super class that inherits the tag class
     */
    String tagSuperclass() default "";
    
    /**
     * Indicate the tag handler of the tag class.
     */
    String tagHandler() default "";
    
    /**
     * Indicate if the element accept inner elements (JSP) or not (empty).
     */
    String bodyContent() default "";
    
    /**
     * Short description.
     */
    String desc() default "";
    
    /**
     * Serial UID to be put on class when generated
     */
    String serialuid() default "";
    
    /**
     * Interfaces that should implement this component
     */
    String implementz() default "";
}
