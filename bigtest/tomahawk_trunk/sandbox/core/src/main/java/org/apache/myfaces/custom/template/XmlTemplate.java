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

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;
import java.net.URI;

/**
 * Used to transform XML (from either a String or URL) using either XSLT or
 * Velocity.
 *
 * @JSFComponent
 *   name = "s:xmlTemplate"
 *   tagClass = "org.apache.myfaces.custom.template.XmlTemplateTag"
 *   
 * @author Sean Schofield
 */
public class XmlTemplate extends UIComponentBase
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.tomahawk.XmlTemplate";
    public static final String COMPONENT_FAMILY = "org.apache.myfaces.tomahawk.Template";

    private String content;
    private String contentLocation;
    private Object contentStream;
    private String stylesheet;
    private String stylesheetLocation;
    private Object styleStream;

    // see superclass for documentation
    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public void encodeBegin(FacesContext context)
            throws IOException
    {
        InputStream xmlStream = (InputStream)getContentStream();
        String xml = getContent();
        String xmlLocation = getContentLocation();

        InputStream xslStream = (InputStream)getStyleStream();
        String xsl = getStylesheet();
        String xslLocation = getStylesheetLocation();

        if (context == null) throw new NullPointerException("context");
        if (!isRendered()) return;

        if (getContent() == null && getContentLocation() == null && getContentStream() == null)
            throw new NullPointerException("content/contentLocation/contentStream cannot all be null");

        //TODO - handle all cases
        if (xmlLocation != null)
        {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            if (loader == null)
            {
                loader = XmlTemplate.class.getClassLoader();
            }

            URL url = loader.getResource(xmlLocation);
            xmlStream = new FileInputStream(new File(URI.create(url.toString())));
        }

        if (xslLocation != null)
        {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            if (loader == null)
            {
                loader = XmlTemplate.class.getClassLoader();
            }

            URL url = loader.getResource(xslLocation);
            xslStream = new FileInputStream(new File(URI.create(url.toString())));
        }

        if (xml != null)
        {
            xmlStream = new ByteArrayInputStream(xml.getBytes());
        }

        if (xsl != null)
        {
            xslStream = new ByteArrayInputStream(xsl.getBytes());
        }

        if (xmlStream != null && xslStream != null)
        {
            transformContent(xmlStream, xslStream);
        }
    }

    /**
     * Transforms an XML string using the stylesheet string provided.
     *
     * @param content The XML to transform
     * @param stylesheet The stylesheet to use in the transformation
     * @throws IOException
     */
    private void transformContent(InputStream content, InputStream stylesheet)
        throws IOException
    {
        try
        {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(new StreamSource(stylesheet));

            Writer responseWriter = FacesContext.getCurrentInstance().getResponseWriter();
            transformer.transform(new StreamSource(content), new StreamResult(responseWriter));
        }
        catch (TransformerException te)
        {
            throw new IOException("Error while transforming XML: " + te.getMessage());
        }
    }

    // component does not need to manage its own children (its not allowed to have any)
    public void encodeChildren(FacesContext context)
            throws IOException
    {}

    // nothing special to do here
    public void encodeEnd(FacesContext context)
            throws IOException
    {}

    //  see superclass for documentation
    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[5];

        values[0] = super.saveState(context);
        values[1] = content;
        values[2] = contentLocation;
        values[3] = stylesheet;
        values[4] = stylesheetLocation;

        /**
         * NOTE: If setContentStream is called directly (instead of through value binding) it will
         * not be saved in the state since it does not make sense to serialize a stream.
         */

        return values;
    }

    // see superclass for documentation
    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);

        content = (String)values[1];
        contentLocation = (String)values[2];
        stylesheet = (String)values[3];
        stylesheetLocation = (String)values[4];
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        if (content != null) return content;

        ValueBinding vb = getValueBinding("content");
        return (vb != null) ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setContentLocation(String contentLocation)
    {
        this.contentLocation = contentLocation;
    }

    public String getContentLocation()
    {
        if (contentLocation != null) return contentLocation;

        ValueBinding vb = getValueBinding("contentLocation");
        return (vb != null) ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setStylesheet(String stylesheet)
    {
        this.stylesheet = stylesheet;
    }

    public String getStylesheet()
    {
        if (stylesheet != null) return stylesheet;

        ValueBinding vb = getValueBinding("stylesheet");
        return (vb != null) ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setContentStream(Object contentStream)
    {
        this.contentStream = contentStream;
    }

    public Object getContentStream()
    {
        if (contentStream != null) return contentStream;

        ValueBinding vb = getValueBinding("contentStream");
        return (vb != null) ? vb.getValue(getFacesContext()) : null;
    }

    public void setStylesheetLocation(String stylesheetLocation)
    {
        this.stylesheetLocation = stylesheetLocation;
    }

    public String getStylesheetLocation()
    {
        if (stylesheetLocation != null) return stylesheetLocation;

        ValueBinding vb = getValueBinding("stylesheetLocation");
        return (vb != null) ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setStyleStream(Object styleStream)
    {
        this.styleStream = styleStream;
    }

    public Object getStyleStream()
    {
        if (styleStream != null) return styleStream;

        ValueBinding vb = getValueBinding("styleStream");
        return (vb != null) ? vb.getValue(getFacesContext()) : null;
    }
}
