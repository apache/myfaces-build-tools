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

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.wap.component.Message;
import org.apache.myfaces.wap.renderkit.WmlRenderer;

/**
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MessageRenderer extends WmlRenderer {
    private static Log log = LogFactory.getLog(MessageRenderer.class);

    /** Creates a new instance of TextRenderer */
    public MessageRenderer() {
        super();
        log.debug("created object " + this.getClass().getName());
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws java.io.IOException {
        log.debug("encodeBegin(" + component.getId() + ")");
        if (context == null || component == null) {
            throw new NullPointerException();
        }
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

        Message comp = (Message)component;
        ResponseWriter writer = context.getResponseWriter();

        String _for = comp.getFor();
        if (_for == null) log.error("Attribute 'for' of UIMessage must not be null.");

        UIComponent forComponent = component.findComponent(_for);
        if (forComponent == null) log.error("Unable to find component '" + _for + "'. Can not render UIMessage component.");

        Iterator iter = context.getMessages(forComponent.getClientId(context));
        if (iter.hasNext()) {
            FacesMessage message = (FacesMessage)iter.next();
            if (comp.isShowSummary())
                writer.write(message.getSummary());
            if (comp.isShowDetail())
                writer.write(message.getDetail());
        }
    }

    public void decode(FacesContext context, UIComponent component) {
        if (component == null ) throw new NullPointerException();
    }
}

