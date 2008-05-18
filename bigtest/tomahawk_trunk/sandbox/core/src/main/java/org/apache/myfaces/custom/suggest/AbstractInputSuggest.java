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
package org.apache.myfaces.custom.suggest;

import javax.faces.component.html.HtmlInputText;

import org.apache.myfaces.component.ForceIdAware;
import org.apache.myfaces.component.LocationAware;

/**
 * 
 * @JSFComponent
 *   name = "s:inputSuggest"
 *   class = "org.apache.myfaces.custom.suggest.InputSuggest"
 *   superClass = "org.apache.myfaces.custom.suggest.AbstractInputSuggest"
 *   tagClass = "org.apache.myfaces.custom.suggest.InputSuggestTag"
 */
public abstract class AbstractInputSuggest extends HtmlInputText
  implements ForceIdAware, LocationAware
{

    static public final String COMPONENT_FAMILY = "javax.faces.Input";
    static public final String COMPONENT_TYPE = "org.apache.myfaces.InputSuggest";
    static public final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.InputSuggest";


    /**
     * Gets If true, this component will force the use of the specified id when rendering.
     * 
     * @JSFProperty
     *   literalOnly = "true"
     *   defaultValue = "false"
     * @return  the new forceId value
     */
    public abstract Boolean getForceId();
    
    public abstract void setForceId(Boolean forceId);

    /**
     * Gets If false, this component will not append a '[n]' suffix (where 'n' is the row index) to components
     *                 that are contained within a "list."  This value will be true by default and the value will be ignored if
     *                 the value of forceId is false (or not specified.)
     *
     * @JSFProperty 
     *   literalOnly = "true"
     *   defaultValue = "true"
     * @return  the new forceIdIndex value
     */
    public abstract Boolean getForceIdIndex();

    /**
     * Gets An alternate location to find javascript resources. If no values is specified, javascript will be loaded from the resources directory using AddResource and ExtensionsFilter.
     *
     * @JSFProperty
     * @return  the new javascriptLocation value
     */
    public abstract String getJavascriptLocation();

    /**
     * Gets An alternate location to find image resources. If no values is specified, images will be loaded from the resources directory using AddResource and ExtensionsFilter.
     *
     * @JSFProperty
     * @return  the new imageLocation value
     */
    public abstract String getImageLocation();
    
    /**
     * Gets An alternate location to find stylesheet resources. If no values is specified, stylesheets will be loaded from the resources directory using AddResource and ExtensionsFilter.
     * @JSFProperty
     * @return  the new styleLocation value
     */
    public abstract String getStyleLocation();
    
}
