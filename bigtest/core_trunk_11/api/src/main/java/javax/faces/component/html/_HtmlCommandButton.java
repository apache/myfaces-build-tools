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
package javax.faces.component.html;

import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * see Javadoc of <a href="http://java.sun.com/j2ee/javaserverfaces/1.1_01/docs/api/index.html">JSF Specification</a>
 *
 * @JSFComponent
 *   name = "h:commandButton"
 *   class = "javax.faces.component.html.HtmlCommandButton"
 *   tagClass = "org.apache.myfaces.taglib.html.HtmlCommandButtonTag"
 *   desc = "h:commandButton"
 *   
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
abstract class _HtmlCommandButton
        extends UICommand implements _Focus_BlurProperties, 
        _EventProperties, _StyleProperties, _UniversalProperties,
        _AccesskeyProperty, _TabindexProperty
{
    public static final String COMPONENT_TYPE = "javax.faces.HtmlCommandButton";
    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Button";
        
    /**
     * @JSFProperty
     */
    public abstract String getAlt();
            
    /**
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract boolean isDisabled();

    /**
     * @JSFProperty
     */
    public abstract String getImage();


    /**
     * @JSFProperty
     */
    public abstract String getOnchange();


    /**
     * @JSFProperty
     */
    public abstract String getOnselect();

    /**
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract boolean isReadonly();

    /**
     * @JSFProperty
     *   defaultValue = "submit"
     */
    public abstract String getType();

}
