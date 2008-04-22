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
package org.apache.myfaces.wap.renderkit.wml;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.wap.component.CommandButton;
import org.apache.myfaces.wap.renderkit.Attributes;
import org.apache.myfaces.wap.renderkit.RendererUtils;
import org.apache.myfaces.wap.renderkit.WmlRenderer;


/**
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CommandButtonRenderer extends WmlRenderer {
    private static Log log = LogFactory.getLog(CommandButtonRenderer.class);

    /** Creates a new instance of FormRenderer */
    public CommandButtonRenderer() {
        super();
        log.debug("created object " + this.getClass().getName());
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws java.io.IOException {
        log.debug("encodeBegin(" + component.getId() + ")");
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) return;

    }

    public void encodeChildren(FacesContext context, UIComponent component) throws java.io.IOException {
        log.debug("encodeChildren(" + component.getId() + ")");
        if (context == null || component == null) {
            throw new NullPointerException();
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws java.io.IOException {
        log.debug("encodeEnd(" + component.getId() + ")");
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) return;

        ResponseWriter writer = context.getResponseWriter();
        CommandButton comp = (CommandButton)component;

        // <do type='accept' label='submit' >
        writer.startElement(Attributes.DO, component);
        RendererUtils.writeAttribute(Attributes.ID, comp.getClientId(context), writer);
        //RenderUtils.writeAttribute(Attributes.STYLE_CLASS, comp.getStyleClass(), writer);
        //RenderUtils.writeAttribute(Attributes.XML_LANG, comp.getXmllang(), writer);
        //RenderUtils.writeAttribute(Attributes.NAME, comp.getName(), writer);
        RendererUtils.writeAttribute(Attributes.LABEL, comp.getValue(), writer);
        RendererUtils.writeAttribute(Attributes.TYPE, comp.getType(), writer);

        /* default value is false, write only if value of attribute emptyok is "true" */
        if (comp.isOptional()) RendererUtils.writeAttribute(Attributes.OPTIONAL, "true", writer);

        if (isReset(comp))
            renderReset(context, comp);
        else
            renderAction(context, comp);

        writer.endElement(Attributes.DO);
    }


    public void decode(FacesContext context, UIComponent component) {
        log.debug("decode(" + component.getId() + ")");
        if (component == null ) throw new NullPointerException();
        if (!component.isRendered()) return;

        Map map = context.getExternalContext().getRequestParameterMap();

        // was this link activated?
        if (map.containsKey(component.getClientId(context) + Attributes.POSTFIX_ACTIVATED) && !isReset((CommandButton)component)){
            log.debug("action queued");
            component.queueEvent(new ActionEvent(component));
        }
    }

    private void renderAction(FacesContext context, CommandButton comp) throws java.io.IOException {
        ResponseWriter writer = context.getResponseWriter();

        // post form data
        UIForm parentForm = getParentForm(context, comp);
        String href = RendererUtils.getCurrentUrl(context);

        // <go href='"+ href  +"' method='post' >
        writer.startElement(Attributes.GO, comp);
        writer.writeAttribute(Attributes.HREF, href, null);
        writer.writeAttribute(Attributes.METHOD, Attributes.POST, null);

        Set inputTags = RendererUtils.getInputTags(parentForm.getChildren());
        Iterator iter = inputTags.iterator();

        log.debug("Form has " + inputTags.size() + " input tags.");

        while(iter.hasNext()){
            UIComponent component = (UIComponent)iter.next();
            String name = RendererUtils.getAttribute(component, Attributes.NAME);

            // <postfield name='" + name + "' value='$(" + name + ")' />
            writer.startElement(Attributes.POSTFIELD, component);
            writer.writeAttribute(Attributes.NAME, name, null);
            writer.writeAttribute(Attributes.VALUE, "$(" + name + ")", null);
            writer.endElement(Attributes.POSTFIELD);
        }

        // write hidden input to determine "submited" value on decode parent UIForm component
        writer.startElement(Attributes.POSTFIELD, comp);
        writer.writeAttribute(Attributes.NAME, parentForm.getClientId(context) + Attributes.POSTFIX_SUBMITED, null);
        writer.writeAttribute(Attributes.VALUE, "true", null);
        writer.endElement(Attributes.POSTFIELD);

        // write hidden input to determine "activated" value on decode commandLink
        writer.startElement(Attributes.POSTFIELD, comp);
        writer.writeAttribute(Attributes.NAME, comp.getClientId(context) + Attributes.POSTFIX_ACTIVATED, null);
        writer.writeAttribute(Attributes.VALUE, "true", null);
        writer.endElement(Attributes.POSTFIELD);

        writer.endElement(Attributes.GO);

    }

    private void renderReset(FacesContext context, CommandButton comp) throws java.io.IOException {
        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(Attributes.REFRESH, comp);

        UIForm parentForm = getParentForm(context, comp);
        Set inputTags = RendererUtils.getInputTags(parentForm.getChildren());
        Iterator iter = inputTags.iterator();

        while(iter.hasNext()){
            UIComponent component = (UIComponent)iter.next();

            // <setvar name='" + name + "' value='$(" + name + ")' />
            writer.startElement(Attributes.SETVAR, comp);
            String name = RendererUtils.getAttribute(component, Attributes.NAME);
            writer.writeAttribute(Attributes.NAME, name, null);

            String value = RendererUtils.getAttribute(component, Attributes.VALUE);
            writer.writeAttribute(Attributes.VALUE, value, null);
            writer.endElement(Attributes.SETVAR);
        }

        writer.endElement(Attributes.REFRESH);
    }

    private boolean isReset(CommandButton comp){
        return ("reset".compareToIgnoreCase(comp.getType()) == 0);
    }


    private UIForm getParentForm(FacesContext context, UIComponent component){
        // find UIForm parent
        UIComponent parent = component.getParent();
        while(parent != null && !(parent instanceof UIForm)){
            parent = parent.getParent();
        }

        String formId;
        if (parent == null){ //parent not found
            //TODO: create dummy form
            throw new FacesException("FacesException - tag commandButton must be nested in a UIForm tag");
        } else {
            return((UIForm)parent);
        }
    }
}

