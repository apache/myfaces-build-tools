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

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectMany;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.wap.component.SelectMany;
import org.apache.myfaces.wap.renderkit.Attributes;
import org.apache.myfaces.wap.renderkit.RendererUtils;
import org.apache.myfaces.wap.renderkit.WmlRenderUtils;
import org.apache.myfaces.wap.renderkit.WmlRenderer;

/**
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SelectManyRenderer extends WmlRenderer {
    private static Log log = LogFactory.getLog(SelectManyRenderer.class);

    /** Creates a new instance of TextRenderer */
    public SelectManyRenderer() {
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

        SelectMany comp = (SelectMany)component;

        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(Attributes.SELECT, component);
        RendererUtils.writeAttribute(Attributes.ID, comp.getClientId(context), writer);
        RendererUtils.writeAttribute(Attributes.STYLE_CLASS, comp.getStyleClass(), writer);
        RendererUtils.writeAttribute(Attributes.XML_LANG, comp.getXmllang(), writer);
        /* TODO: attribute "required" */

        /* attribute name is not required. If is not set, name value equals component id */
        if (comp.getName() == null) {
            log.debug("getName is null");
            comp.setName(comp.getClientId(context));
        }
        RendererUtils.writeAttribute(Attributes.NAME, comp.getName(), writer);

        RendererUtils.writeAttribute(Attributes.TABINDEX, comp.getTabindex(), writer);
        RendererUtils.writeAttribute(Attributes.TITLE, comp.getTitle(), writer);

//        RenderUtils.writeAttribute(Attributes.VALUE, convertSelectedValuesToString(comp.getSelectedValues()), writer);
        RendererUtils.writeAttribute(Attributes.VALUE, convertSelectedValuesToString(comp.getSelectedValues()), writer);

        // multiple
        RendererUtils.writeAttribute(Attributes.MULTIPLE, "true", writer);

        List child = component.getChildren();
        for (int i = 0; i < child.size(); i++){
            if (child.get(i) instanceof UISelectItem){
                UISelectItem item = (UISelectItem)child.get(i);
                WmlRenderUtils.writeOption((SelectItem)item.getValue(), comp, writer);
            }
            if (child.get(i) instanceof UISelectItems){
                UISelectItems item = (UISelectItems)child.get(i);
                WmlRenderUtils.writeOptions(item.getValue(), comp, writer);
            }
        }

        writer.endElement(Attributes.SELECT);
    }

    public void decode(FacesContext context, UIComponent component) {
        log.debug("decode(" + component.getId() + ")");
        if (component == null || context == null) throw new NullPointerException();
        if (!(component instanceof SelectMany))
            log.error("Component " + component.getClass().getName() + " is no SelectMany component, cannot be converted!");

        SelectMany comp = (SelectMany)component;

        Map map = context.getExternalContext().getRequestParameterMap();

        // Set the submitted value of this UISelectOne component
        if (map.containsKey(comp.getName())){
            log.debug("Parameter:" + comp.getName() + " was found in the request. Value: " + (String)map.get(comp.getName()));

            String value = (String)map.get(comp.getName());
            comp.setSelectedValues(convertSubmittedValueToObject(value));
        }
    }

    /** Overrides method getConvertedValue */
    public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue) throws javax.faces.convert.ConverterException {
        if (!(component instanceof UISelectMany))
            throw new IllegalArgumentException("Expected component of type UISelectMany. Cannot convert selected value.");

        UISelectMany comp = (UISelectMany)component;

        return(RendererUtils.convertUISelectManyToObject(context, comp, submittedValue));
    }

    /** Returns WML String representating the selected options.
     * @param obj Object returned from getSelectedValue(). This object must be a String[] type.
     * @return String representing selected options for wml code. Is rendered as a value of "value" attribute in the select element.
     */
    private String convertSelectedValuesToString(Object obj){
        if (obj == null) return(null);

        if (!(obj instanceof String[]))
            throw new ClassCastException("Value of SelectMany component must be a String[] type.");

        StringBuffer buff = new StringBuffer();

        String[] selected = (String[])obj;

        for (int i = 0 ; i < selected.length; i++){
            String item = selected[i];

            buff.append(item);

            // insert separator
            if (i < selected.length - 1) buff.append(Attributes.SELECT_MANY_SEPARATOR);
        }
        return (buff.toString());
    }

    /** Tokenizes submitted value from request parameter to the String array . */
    private String[] convertSubmittedValueToObject(String value){
        StringTokenizer st = new StringTokenizer(value, Attributes.SELECT_MANY_SEPARATOR);

        String[] ret = new String[st.countTokens()];

        log.debug("Selected option:");
        for (int i = 0; st.hasMoreTokens(); i++){
            ret[i] = st.nextToken();
            log.debug(ret[i]);
        }
        return (ret);
    }
}

