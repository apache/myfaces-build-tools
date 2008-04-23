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

import javax.faces.component.UIMessage;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * Renders the first FacesMessage that is assigned to the component
 * referenced by the "for" attribute.
 * 
 * Unless otherwise specified, all attributes accept static values
 * or EL expressions.
 * 
 * see Javadoc of <a href="http://java.sun.com/j2ee/javaserverfaces/1.1_01/docs/api/index.html">JSF Specification</a>
 *
 * @JSFComponent
 *   name = "h:message"
 *   class = "javax.faces.component.html.HtmlMessage"
 *   tagClass = "org.apache.myfaces.taglib.html.HtmlMessageTag"
 *   desc = "h:message"
 *   
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
abstract class _HtmlMessage extends UIMessage implements _StyleProperties, 
    _MessageProperties
{

    public static final String COMPONENT_TYPE = "javax.faces.HtmlMessage";
    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Message";
    
}
