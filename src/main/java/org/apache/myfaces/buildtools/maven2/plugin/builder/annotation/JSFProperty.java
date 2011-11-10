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
 * Define that this getter or setter method define a property.
 * 
 * @author Leonardo Uribe (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface JSFProperty
{

    /**
     * (true|false) Define if the property is required or not. Default:false
     */
    boolean required() default false;
    
    /**
     * Indicate if the property is not saved and restored its state.
     */
    boolean istransient() default false;
    
    /**
     * Use saveAttachedXXX and restoreAttachedXXX to save and restore state
     */
    boolean stateHolder() default false;
    
    /**
     * Indicate that the getter and setter does not evaluate EL or ValueBinding expressions.
     */
    boolean literalOnly() default false;
    
    /**
     * Define if this tag is excluded from tld.
     */
    boolean tagExcluded() default false;
    
    /**
     * Indicate if it should be generated a method like
     * getLocalXXX to retrieve the local value of the property
     * directly (without evaluate ValueBinding or ValueExpression).
     * 
     * If there is no generation of component class this property
     * has any effect.
     * 
     */
    boolean localMethod() default false;
    
    /**
     * Define the scope to be used when generating the method getLocalXXX.
     * 
     * The default to be applied is "protected".
     * 
     */
    String localMethodScope() default "";
    
    /**
     * Indicate if it should be generated a method like
     * isSetXXX, used when it is necessary to check if a boolean type
     * property was set programatically (using setXXX method) or not. 
     * 
     * If there is no generation of component class this property
     * has any effect.
     */
    boolean setMethod() default false;
    
    /**
     * Define the scope to be used when generating the method isSetXXX.
     * 
     * The default to be applied is "protected".
     * 
     */
    String setMethodScope() default "";
    
    /**
     * The name used in jsp pages to make reference to this
     * property (used on tld).
     */
    String jspName() default "";
    
    /**
     * (true|false) This value is put on the tld when applies.
     */
    boolean rtexprvalue() default false;
    
    /**
     * Short description
     */
    String desc() default "";
    
    /**
     * Indicate if this property is inherited from a parent tag class or not.
     * 
     * @return
     */
    boolean inheritedTag() default false;
    
    /**
     * The full name of the return type for MethodBinding or MethodExpression it uses
     */
    String returnSignature() default "";

    /**
     * CSV full names of the types that are params for methods using this MethodBinding or MethodExpression param
     */
    String methodSignature() default "";
    
    /**
     * The default value to set if this property is generated.
     */
    String defaultValue() default "";

    /**
     * Indicate the type that values should be cast on tld. 
     * This param only applies on jsf 1.2 (it is supposed that 
     * the className is javax.el.ValueExpression to apply it),
     * because in jsf 1.1, values on tag class are considered 
     * to be String. 
     *
     * @since 1.0.3
     */
    String deferredValueType() default "";
    
    /**
     * Indicate if this property is a client event that should
     * be returned by ClientBehaviorHolder.getEventNames(). This
     * property is by default "", it is JSF 2.0 specific, and 
     * the component holding it must implement 
     * javax.faces.component.behavior.ClientBehaviorHolder interface.
     * 
     * @since 1.0.4
     */
    String clientEvent() default "";
    
    /**
     * 
     * @since 1.0.5
     */
    boolean partialStateHolder() default false;
    
    /**
     * 
     * @since 1.0.5
     */
    boolean faceletsOnly() default false;
}
