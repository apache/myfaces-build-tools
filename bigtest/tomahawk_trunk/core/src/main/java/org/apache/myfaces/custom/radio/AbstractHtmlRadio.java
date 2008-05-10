/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.custom.radio;

import javax.faces.component.UIComponentBase;

import org.apache.myfaces.component.UserRoleAware;

/**
 * @JSFComponent
 *   name = "t:radio"
 *   class = "org.apache.myfaces.custom.radio.HtmlRadio"
 *   superClass = "org.apache.myfaces.custom.radio.AbstractHtmlRadio"
 *   tagClass = "org.apache.myfaces.custom.radio.HtmlRadioTag"
 * 
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlRadio
    extends UIComponentBase implements UserRoleAware
{
    //private static final Log log = LogFactory.getLog(HtmlRadio.class);

    public static final String FOR_ATTR = "for".intern();
    public static final String INDEX_ATTR = "index".intern();


    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlRadio";
    public static final String COMPONENT_FAMILY = "org.apache.myfaces.Radio";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Radio";

    /**
     * @JSFProperty
     *   required="true"
     */
    public abstract String getFor();
    
    /**
     * @JSFProperty
     *   defaultValue = "Integer.MIN_VALUE"
     *   required="true"
     */
    public abstract int getIndex();

}
