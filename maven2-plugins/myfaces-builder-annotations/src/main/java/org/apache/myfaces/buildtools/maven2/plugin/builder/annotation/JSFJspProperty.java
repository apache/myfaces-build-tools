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
 * Define a property that belongs to this component but does not have getter defined.
 * 
 *  here exists two main scenarios: 
 *  
 *  1. Add a property that exists on tld but not on component(binding on 1.1). 
 *  2. Exclude an already defined property from the tld. 
 *  
 *  THIS ONLY SHOULD BE USED IN VERY SPECIAL CASES (Use @JSFProperty instead).
 * 
 * @author Leonardo Uribe (latest modification by $Author$)
 * @version $Revision$ $Date$
 *
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface JSFJspProperty
{

    /**
     * The name that identifies this property. (ex:border, id, value)
     */
    String name() default "";
    
    /**
     * The full type or primitive that this property has defined on tag class.
     */
    String returnType() default "";
    
    /**
     * (true|false) Define if the property is required or not. Default:false
     */
    boolean required() default false;
    
    /**
     * Define if this tag is excluded from tld.
     */
    boolean tagExcluded() default false;
    
    /**
     * Long description
     */
    String longDesc() default "";
    
    /**
     * Short description
     */
    String desc() default "";
}
