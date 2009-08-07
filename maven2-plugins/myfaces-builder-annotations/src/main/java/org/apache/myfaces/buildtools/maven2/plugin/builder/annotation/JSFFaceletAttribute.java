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
 * 
 * Define a facelet tag attribute. This doclet should be used inside a @JSFFaceletTag class, 
 * to define individual tag classes used in JSF
 * 
 * This annotation should only be used in jsf 2.0 libraries.
 * 
 * @since 1.0.4
 * @author Leonardo Uribe (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
@Documented
@Target(value={
        ElementType.TYPE,
        ElementType.METHOD,
        ElementType.LOCAL_VARIABLE,
        ElementType.FIELD
        })
@Retention(RetentionPolicy.SOURCE)
public @interface JSFFaceletAttribute
{
    /**
     * The name of the attribute. This is the name on the tld
     * 
     * @since 1.0.4
     */
    String name() default "";
    
    /**
     * The class or type the component must refer on the tag class. 
     * On 1.1 is java.lang.String always and on 1.2 is 
     * javax.el.ValueExpression or javax.el.MethodExpression.
     * 
     * @since 1.0.4
     */
    String className() default "";
    
    /**
     * Long description. By default, it takes what is inside comment area.
     * 
     * @since 1.0.4
     */
    String longDescription() default "";
    
    /**
     * (true|false) Define if the property is required or not. Default:false
     * 
     * @since 1.0.4
     */
    boolean required() default false;
    
    /**
     * (true|false) This value is put on the tld when applies 
     * and className is not javax.el.ValueExpression or 
     * javax.el.MethodExpression.
     * 
     * @since 1.0.4
     */
    boolean rtexprvalue() default false;
    
    /**
     * Short description
     * 
     * @since 1.0.4
     */
    String desc() default "";

    /**
     * Indicate the type that values should be
     * cast on tld. It is supposed that the className is
     * javax.el.ValueExpression to apply it. 
     *
     * @since 1.0.4
     */
    String deferredValueType() default "";

    /**
     * Indicate the method signature that values should be
     * cast on tld. It is supposed that the className is
     * javax.el.MethodExpression to apply it. 
     *
     * @since 1.0.4
     */
    String deferredMethodSignature() default "";
    
    /**
     * Mark this property to not be included on the tld.
     * 
     * @since 1.0.4
     */
    boolean exclude() default false;
}
