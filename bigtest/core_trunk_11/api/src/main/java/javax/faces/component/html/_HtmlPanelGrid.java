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

import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * see Javadoc of <a href="http://java.sun.com/j2ee/javaserverfaces/1.1_01/docs/api/index.html">JSF Specification</a>
 *
 * @JSFComponent
 *   name = "h:panelGrid"
 *   class = "javax.faces.component.html.HtmlPanelGrid"
 *   tagClass = "org.apache.myfaces.taglib.html.HtmlPanelGridTag"
 *   tagSuperclass = "javax.faces.webapp.UIComponentBodyTag"
 *   desc = "h:panelGrid"
 *
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
abstract class _HtmlPanelGrid extends UIPanel implements _EventProperties,
    _StyleProperties, _UniversalProperties
{
    public static final String COMPONENT_TYPE = "javax.faces.HtmlPanelGrid";
    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Grid";

    /**
     * @JSFProperty
     */
    public abstract String getBgcolor();

    /**
     * @JSFProperty
     *   defaultValue="Integer.MIN_VALUE"
     */
    public abstract int getBorder();

    /**
     * @JSFProperty
     */
    public abstract String getCellpadding();

    /**
     * @JSFProperty
     */
    public abstract String getCellspacing();

    /**
     * @JSFProperty
     */
    public abstract String getColumnClasses();

    /**
     * @JSFProperty
     *   defaultValue="1"
     */
    public abstract int getColumns();

    /**
     * @JSFProperty
     */
    public abstract String getFooterClass();

    /**
     * @JSFProperty
     */
    public abstract String getFrame();

    /**
     * @JSFProperty
     */
    public abstract String getHeaderClass();

    /**
     * @JSFProperty
     */
    public abstract String getRowClasses();

    /**
     * @JSFProperty
     */
    public abstract String getRules();

    /**
     * @JSFProperty
     */
    public abstract String getSummary();

    /**
     * @JSFProperty
     */
    public abstract String getWidth();
    
}
