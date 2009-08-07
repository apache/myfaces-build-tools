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
 * Define a set of tags that uses the tag handler class annotated.
 * <p>
 * In jsp, each tag (read it as component/converter/validator/tag)
 * has one specific tag file that only works for. But in facelets,
 * one tag handler could take care of multiple tags. 
 * </p>
 * <p>
 * This tag is used to group several JSFFaceletTag definitions and
 * indicate that the tag handler annotated by this annotations handle
 * the selected tags.
 * </p>
 * <p>
 * This annotation should only be used in jsf 2.0 libraries.
 * </p>
 * 
 * @since 1.0.4
 * @author Leonardo Uribe (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface JSFFaceletTags
{

    /**
     * Array of JSFFaceletTags to be defined.
     * 
     * @since 1.0.4
     */
    JSFFaceletTag [] tags();
    
}
