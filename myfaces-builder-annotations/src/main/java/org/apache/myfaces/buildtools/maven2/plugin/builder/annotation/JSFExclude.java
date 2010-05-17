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
 * Indicate that this code must be excluded from copying when generation using a 
 * template package scoped class is used. 
 * 
 * THIS ONLY SHOULD BE USED IN VERY SPECIAL CASES 
 * 
 * (Use abstract pattern to add custom code on component class instead).
 * 
 * @author Leonardo Uribe (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
@Documented
@Target(value={
        ElementType.TYPE,
        ElementType.METHOD,
        ElementType.LOCAL_VARIABLE,
        ElementType.FIELD,
        ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.SOURCE)
public @interface JSFExclude
{

}
