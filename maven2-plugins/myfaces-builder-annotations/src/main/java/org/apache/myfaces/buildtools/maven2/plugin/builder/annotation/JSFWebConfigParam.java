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
 * Used to define a param configured in web.xml file.
 * 
 * @since 1.0.4
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 758513 $ $Date: 2009-03-25 23:02:16 -0500 (mié, 25 mar 2009) $
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface JSFWebConfigParam
{
    String name() default "";
    
    /**
     * Short description
     */
    String desc() default "";
    
    /**
     * 
     */
    String defaultValue() default "";
    
    /**
     * 
     */
    String expectedValues() default "";
    
    /**
     * 
     */
    String since() default "";

    /**
     * The group which the param belongs. 
     * 
     * <p>These are the groups used in myfaces core:</p>
     * <ul>
     * <li>state</li>
     * <li>resources</li>
     * <li>viewhandler</li>
     * <li>validation</li>
     * <li>render</li>
     * <li>EL</li>
     * </ul>
     * 
     * @return
     */
    String group() default "";

    /**
     * The tag(s) or classification(s), separated by commas
     * that this web config param can be classified.
     * 
     * <p>These are the tags used in myfaces core:</p>
     * <ul>
     * <li>tomahawk</li>
     * <li>performance</li>
     * </ul>
     * 
     * 
     * @return
     */
    String tags() default "";
    
    /**
     * Indicate if the param was deprecated or not.
     * 
     * @return
     */
    boolean deprecated() default false;

    /**
     * Indicate an alias or alternate name for this param. 
     * @return
     */
    String alias() default "";
    
    /**
     * Indicate if this param ignore upper or lower case when read params 
     * @return
     */
    boolean ignoreUpperLowerCase() default false;
    
    /**
     * The java type or class which this param is converted
     * 
     * @return
     */
    String classType() default "";
}
