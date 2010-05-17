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
 * Used to define a jsf facelet tag handler. 
 * <p>
 * The intention of this annotation is use its information to generate a
 * facelet taglib xml file(optional), and create a specific 
 * facelet documentation (generate alternate .tld files and use 
 * maven-taglib-plugin on these files).
 * In order to do that, this annotation has multiple use cases:
 * </p>
 * <ul>
 * <li>Define a component only available on facelets.</li>
 * <li>When a component has additional properties only available on facelets.
 * In this case, we set componentClass/converterClass/validatorClass/tagClass attribute.
 * The effect is that all properties and descriptions are flattened to this class. In other
 * words, when the documentation of the annotated facelet tag handler is generated, 
 * this information precedes the one pointed by the annotation. This only happens when
 * name attribute match.</li>
 * <li>When a component does not have an specific tag handler, so we can create
 * a dummy tag handler, annotate it and set configExcluded=true, so we can 
 * document it instead put this information on the base tld used to generate.
 * This is the case of h:outputScript or h:outputStylesheet</li>
 * </ul>
 * 
 * This annotation should only be used in jsf 2.0 libraries.
 * 
 * @since 1.0.4
 * @author Leonardo Uribe (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface JSFFaceletTag
{
    /**
     * Indicate if the element accept inner elements or not. (JSP or empty)
     * 
     * @since 1.0.4
     */
    String bodyContent() default "";
    
    /**
     * Short description
     * 
     * @since 1.0.4
     */
    String desc() default "";
    
    /**
     * Long description. By default, it takes what is inside comment area.
     * 
     * @since 1.0.4
     */
    String longDescription() default "";    
    
    /**
     * The name of the component in a page (ex: x:mycomp).
     * 
     * @since 1.0.4
     */
    String name() default "";
    
    /**
     * Indicate that this facelet tag handler should not be defined 
     * on facelets taglib xml file.
     * Note that for some libraries a xml file is not generated 
     * (like in core 2.0), instead a library class is defined, 
     * so in those cases set this value has no effect.
     * 
     * @since 1.0.4
     */
    boolean configExcluded() default false;
    
    /**
     * The name of the most near jsp tag class where all
     * attributes should be inherited from this class
     * 
     * @since 1.0.4
     */
    String tagClass() default "";
    
    /**
     * The name of the most near component class where all
     * attributes should be inherited from this class
     * 
     * @since 1.0.4
     */
    String componentClass() default "";
    
    /**
     * The name of the most near converter class where all
     * attributes should be inherited from this class
     * 
     * @since 1.0.4
     */
    String converterClass() default "";
    
    /**
     * The name of the most near validator class where all
     * attributes should be inherited from this class
     * 
     * @since 1.0.4
     */
    String validatorClass() default "";
    
    /**
     * The name of the most near behavior class where all
     * attributes should be inherited from this class
     * 
     * @since 1.0.5
     */
    String behaviorClass() default "";
}