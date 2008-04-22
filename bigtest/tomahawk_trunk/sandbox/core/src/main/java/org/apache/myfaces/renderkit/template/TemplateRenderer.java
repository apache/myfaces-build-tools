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
package org.apache.myfaces.renderkit.template;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class TemplateRenderer extends Renderer
{
    private static final Log log = LogFactory.getLog(TemplateRenderer.class);

    private static final String TEMPLATE_ENCODER = "org.apache.myfaces.tomahawk.TemplateEncoder";
    private static final String TEMPLATE_ENCODER_ENCODER_CLASS = "org.apache.myfaces.tomahawk.templateEncoder.ENCODER_CLASS";

    /**
     * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
     */
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException
    {
        if (!component.isRendered()) return;
        encodeTemplate(context, component, getTemplateName(context, component) + "_begin.ftl");
    }

    /**
     * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
     */
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException
    {
        if (!component.isRendered()) return;
        if (!getRendersChildren()) return;
        encodeTemplate(context, component, getTemplateName(context, component) + "_children.ftl");
    }

    /**
     * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
     */
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException
    {
        if (!component.isRendered()) return;
        encodeTemplate(context, component, getTemplateName(context, component) + "_end.ftl");
    }

    protected void encodeTemplate(FacesContext context, UIComponent component, String template) throws IOException {
        TemplateEncoder templateEncoder =
                (TemplateEncoder) context.getExternalContext().getApplicationMap().get(TEMPLATE_ENCODER);

        if(templateEncoder == null) {

            String className =
                    context.getExternalContext().getInitParameter(TEMPLATE_ENCODER_ENCODER_CLASS);

            if(className == null) {
                className = "org.apache.myfaces.renderkit.template.DefaultTemplateEncoder";
            }

            try {
                templateEncoder = (TemplateEncoder) Class.forName(className).newInstance();
                context.getExternalContext().getApplicationMap().put(TEMPLATE_ENCODER, templateEncoder);
            } catch (ClassNotFoundException e) {
                log.error("Template encoder class : "+className+" not found. Alternative classed defined in web-xml parameter : "+TEMPLATE_ENCODER_ENCODER_CLASS);
            } catch (IllegalAccessException e) {
                log.error("Constructor of template encoder class : " + className + " could not be accessed. Alternative classes may be defined in web-xml parameter : "+TEMPLATE_ENCODER_ENCODER_CLASS);
            } catch (InstantiationException e) {
                log.error("Instance of template encoder class : " + className + " could not be instantiated. Alternative classes may be defined in web-xml parameter : "+TEMPLATE_ENCODER_ENCODER_CLASS);
            }
        }

        templateEncoder.encodeTemplate(context, component, this, template, getDatamodel(context, component));
    }
    
    protected abstract Object getDatamodel(FacesContext context, UIComponent component);
    protected abstract String getTemplateName(FacesContext context, UIComponent component);
    
    
}
