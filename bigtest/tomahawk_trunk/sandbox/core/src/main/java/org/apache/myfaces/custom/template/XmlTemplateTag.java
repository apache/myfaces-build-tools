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
package org.apache.myfaces.custom.template;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;
import javax.faces.el.ValueBinding;

/**
 * JspTag for XmlTemplate Component
 * @author Sean Schofield
 */
public class XmlTemplateTag extends UIComponentTag
{
    private String content;
    private String contentLocation;
    private String contentStream;
    private String stylesheet;
    private String stylesheetLocation;
    private String styleStream;

    public String getComponentType()
    {
        return XmlTemplate.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        /**
         * There is no renderer for this component.  Component just transforms XML and writes directly to the
         * response stream.
         */
        return null;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public void setContentLocation(String contentLocation)
    {
        this.contentLocation = contentLocation;
    }

    public void setContentStream(String contentStream)
    {
        this.contentStream = contentStream;
    }

    public void setStylesheet(String stylesheet)
    {
        this.stylesheet = stylesheet;
    }

    public void setStylesheetLocation(String stylesheetLocation)
    {
        this.stylesheetLocation = stylesheetLocation;
    }

    public void setStyleStream(String styleStream)
    {
        this.styleStream = styleStream;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        XmlTemplate template = (XmlTemplate)component;

        if (content != null && isValueReference(content))
        {
            ValueBinding vb = getFacesContext().getApplication().createValueBinding(content);
            component.setValueBinding("content", vb);
        }
        else
        {
            template.setContent(content);
        }

        if (contentLocation != null && isValueReference(contentLocation))
        {
            ValueBinding vb = getFacesContext().getApplication().createValueBinding(contentLocation);
            component.setValueBinding("contentLocation", vb);
        }
        else
        {
            template.setContentLocation(contentLocation);
        }

        if (contentStream != null && isValueReference(contentStream))
        {
            ValueBinding vb = getFacesContext().getApplication().createValueBinding(contentStream);
            component.setValueBinding("contentStream", vb);
        }

        if (stylesheet != null && isValueReference(stylesheet))
        {
            ValueBinding vb = getFacesContext().getApplication().createValueBinding(stylesheet);
            component.setValueBinding("stylesheet", vb);
        }
        else
        {
            template.setStylesheet(stylesheet);
        }

        if (stylesheetLocation != null && isValueReference(stylesheetLocation))
        {
            ValueBinding vb = getFacesContext().getApplication().createValueBinding(stylesheetLocation);
            component.setValueBinding("stylesheetLocation", vb);
        }
        else
        {
            template.setStylesheetLocation(stylesheetLocation);
        }

        if (styleStream != null && isValueReference(styleStream))
        {
            ValueBinding vb = getFacesContext().getApplication().createValueBinding(styleStream);
            component.setValueBinding("styleStream", vb);
        }
    }
}
