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
import org.apache.myfaces.wap.component.CommandLink;
import org.apache.myfaces.wap.renderkit.Attributes;
import org.apache.myfaces.wap.renderkit.RendererUtils;
import org.apache.myfaces.wap.renderkit.WmlRenderer;

/**
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CommandLinkRenderer extends WmlRenderer {
    private static Log log = LogFactory.getLog(CommandLinkRenderer.class);

    /** Creates a new instance of TextRenderer */
    public CommandLinkRenderer() {
        super();
        log.debug("created object " + this.getClass().getName());
    }

    public boolean getRendersChildren(){
        return(false);
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws java.io.IOException {
        log.debug("encodeBegin(" + component.getId() + ")");
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) return;

        CommandLink comp = (CommandLink)component;
        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(Attributes.ANCHOR, component);
        RendererUtils.writeAttribute(Attributes.TITLE, comp.getTitle(), writer);
        writer.flush();
    }

    public void encodeChildren(FacesContext context, UIComponent component) throws java.io.IOException {
        log.debug("encodeChildren(" + component.getId() + ")");
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) return;

        RendererUtils.renderChildren(context, component);
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws java.io.IOException {
        log.debug("encodeEnd(" + component.getId() + ")");
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) return;

        CommandLink comp = (CommandLink)component;
        UIForm parentForm = getParentForm(context, component);

        ResponseWriter writer = context.getResponseWriter();

        String href = RendererUtils.getCurrentUrl(context);

        // <go href='"+ href  +"' method='post' >
        writer.startElement(Attributes.GO, component);
        writer.writeAttribute(Attributes.HREF, href, null);
        writer.writeAttribute(Attributes.METHOD, Attributes.POST, null);

        Set inputTags = RendererUtils.getInputTags(parentForm.getChildren());
        Iterator iter = inputTags.iterator();

        log.debug("Form has " + inputTags.size() + " input tags.");

        while(iter.hasNext()){
            UIComponent inputComp = (UIComponent)iter.next();
            String name = RendererUtils.getAttribute(inputComp, Attributes.NAME);

            // <postfield name='" + name + "' value='$(" + name + ")' />
            writer.startElement(Attributes.POSTFIELD, component);
            writer.writeAttribute(Attributes.NAME, name, null);
            writer.writeAttribute(Attributes.VALUE, "$(" + name + ")", null);
            writer.endElement(Attributes.POSTFIELD);
        }

        // write hidden input to determine "submited" value on decode parent UIForm component
        writer.startElement(Attributes.POSTFIELD, component);
        writer.writeAttribute(Attributes.NAME, parentForm.getClientId(context) + Attributes.POSTFIX_SUBMITED, null);
        writer.writeAttribute(Attributes.VALUE, "true", null);
        writer.endElement(Attributes.POSTFIELD);

        // write hidden input to determine "activated" value on decode commandLink
        writer.startElement(Attributes.POSTFIELD, component);
        writer.writeAttribute(Attributes.NAME, component.getClientId(context) + Attributes.POSTFIX_ACTIVATED, null);
        writer.writeAttribute(Attributes.VALUE, "true", null);
        writer.endElement(Attributes.POSTFIELD);

        writer.endElement(Attributes.GO);
        writer.endElement(Attributes.ANCHOR);
    }

    public void decode(FacesContext context, UIComponent component) {
        log.debug("decode(" + component.getId() + ")");
        if (component == null || context == null) throw new NullPointerException();
        if (!component.isRendered()) return;

        Map map = context.getExternalContext().getRequestParameterMap();

        // was this link activated?
        if (map.containsKey(component.getClientId(context) + Attributes.POSTFIX_ACTIVATED))
            component.queueEvent(new ActionEvent(component));
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
            throw new FacesException("FacesException - tag commandLink must be nested in a UIForm tag");
        } else {
           return((UIForm)parent);
        }
    }

}

