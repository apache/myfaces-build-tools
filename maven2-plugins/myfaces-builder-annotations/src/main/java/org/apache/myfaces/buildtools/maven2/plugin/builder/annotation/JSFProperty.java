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
    boolean required();
    
    /**
     * Indicate if the property is not saved and restored its state.
     */
    boolean istransient();
    
    /**
     * Use saveAttachedXXX and restoreAttachedXXX to save and restore state
     */
    boolean stateHolder();
    
    /**
     * Indicate that the getter and setter does not evaluate EL or ValueBinding expressions.
     */
    boolean literalOnly();
    
    /**
     * Define if this tag is excluded from tld.
     */
    boolean tagExcluded();
    
    /**
     * 
     */
    boolean localMethod();
    
    /**
     * 
     */
    String localMethodScope();
    
    /**
     * 
     */
    boolean setMethod();
    
    /**
     * 
     */
    String setMethodScope();
    
    /**
     * 
     */
    String jspName();
    
    /**
     * (true|false) This value is put on the tld when applies.
     */
    boolean rtexprvalue();
    
    /**
     * Short description
     */
    String desc();
    
    /**
     * Indicate if this property is inherited from a parent tag class or not.
     * 
     * @return
     */
    boolean inheritTag();
    
    /**
     * The full name of the return type for MethodBinding or MethodExpression it uses
     */
    String returnSignature();

    /**
     * CSV full names of the types that are params for methods using this MethodBinding or MethodExpression param
     */
    String methodSignature();
    
    /**
     * The default value to set if this property is generated.
     */
    String defaultValue();

}
