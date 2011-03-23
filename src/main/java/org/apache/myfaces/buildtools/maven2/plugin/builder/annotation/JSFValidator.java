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
 * Used to define a jsf validator.
 * 
 * @author Leonardo Uribe (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface JSFValidator
{
    
    /**
     * Indicate the validatorId which identifies this class. If not defined, it
     * tries to get the value of the field VALIDATOR_ID.
     */
    String id() default "";
    
    /**
     * The name of the component in a page (ex: x:mycomp).
     */
    String name() default "";

    /**
     * The tag class used for this validator, if applies.
     */
    String tagClass() default "";
    
    /**
     * Tag super class that inherits the tag class
     */
    String tagSuperclass() default "";

    /**
     * Indicate that this component should not be defined on faces-config.xml.
     * Anyway, if this is true or false does not have any significative impact. 
     * 
     * @return
     */
    boolean configExcluded() default false;
        
    /**
     * Indicate if the element accept inner elements or not.
     */
    String bodyContent() default "";
    
    /**
     * Short description
     */
    String desc() default "";
    
    /**
     * Serial uid that should be added on generated tag class
     * 
     * @since 1.0.3
     */
    String serialuidtag() default "";
    
    /**
     * The fully-qualified-name of a concrete validator class.
     * <p>
     * This attribute is only relevant when "name" is also set, ie the
     * annotation is indicating that a validator is really being declared.
     * <p>
     * When this attribute is not defined then it is assumed that this
     * annotated class is the actual validator class.
     * <p>
     * When this attribute is set to something other than the name of the
     * annotated class then the specified class is the one that the JSF
     * validator registration in faces-config.xml will refer to. And if that
     * class does not exist in the classpath (which will normally be the
     * case) then code-generation will be triggered to create it.
     * <p>
     * This attribute is not inheritable.
     * <p>
     * The doclet-annotation equivalent of this attribute is named "class".
     * 
     * @since 1.0.3
     */
    String clazz() default "";
    
    /**
     * Indicate tag handler class used for this component on facelets.
     * 
     * @since 1.0.7
     */
    String tagHandler() default "";
    
    /**
     * Indicate that the EL Expressions should be stored using 
     * setValueExpression() method, instead evaluate them at build view
     * time. Later the EL Expressions will be evaluated according to
     * their needs
     * 
     * @since 1.0.7
     */
    boolean evaluateELOnExecution() default false;
}
