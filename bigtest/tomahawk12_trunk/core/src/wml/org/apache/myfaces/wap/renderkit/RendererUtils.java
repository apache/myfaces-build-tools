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
package org.apache.myfaces.wap.renderkit;

import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectMany;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */ 

public class RendererUtils {
    private static Log log = LogFactory.getLog(RendererUtils.class);

    public static String getCurrentUrl(FacesContext context){
        ViewHandler viewHandler = context.getApplication().getViewHandler();
        String viewId = context.getViewRoot().getViewId();
        String actionUrl = viewHandler.getActionURL(context, viewId);
        String urlLink = context.getExternalContext().encodeActionURL(actionUrl);
        return(urlLink);
    }

    /** write attribute */
    public static void writeAttribute(String attribute, Object value, ResponseWriter writer){
        log.debug("attribute " + attribute + ": " + value);

        try {
            if (value != null)
                writer.writeAttribute(attribute, value, null);
        } catch (java.io.IOException ex) {
            log.error("Error write attribute '" + attribute + " value: '" + value + "'", ex.getCause());
        }
    }

    /** Converts submitted value to Object (local component value) */
    public static Object convertToObject(FacesContext context, UIComponent component){
        if (!(component instanceof EditableValueHolder))
            throw new IllegalArgumentException("Expected component of type EditableValueHolder. Cannot convert submitted value.");

        EditableValueHolder holder = (EditableValueHolder)component;

        if (!(holder.getSubmittedValue() instanceof String))
            throw new ClassCastException("Submitted value must be a String value.");

        String submittedValue = (String)holder.getSubmittedValue();
        Converter conv = holder.getConverter();

        // no conversion needed
        if (conv == null) return(submittedValue);

        return(conv.getAsObject(context, component, submittedValue));
    }

    /** Converts value of compoent to String */
    public static String convertToString(FacesContext facesContext, UIComponent component) {
        if (!(component instanceof ValueHolder)) {
            throw new IllegalArgumentException("Component is not a ValueHolder");
        }

        if (component instanceof EditableValueHolder) {
            Object submittedValue = ((EditableValueHolder)component).getSubmittedValue();
            if (submittedValue != null) {
                if (submittedValue instanceof String) {
                    return (String)submittedValue;
                }
                else {
                    throw new IllegalArgumentException("Expected submitted value of type String");
                }
            }
        }

        Object value = ((ValueHolder)component).getValue();

        Converter converter = ((ValueHolder)component).getConverter();
        if (converter == null  && value != null) {
            if (value instanceof String) {
                return (String) value;
            }

            try {
                converter = facesContext.getApplication().createConverter(value.getClass());
            }
            catch (FacesException e) {
                log.error("No converter for class " + value.getClass().getName() + " found (component id=" + component.getId() + ").");
                // converter stays null
            }
        }

        if (converter == null) {
            if (value == null) {
                return "";
            }
            else {
                return value.toString();
            }
        }
        else {
            return converter.getAsString(facesContext, component, value);
        }
    }

    public static Object convertUISelectManyToObject(FacesContext context, UISelectMany component, Object value) {
        if (!(value instanceof String[]))
            throw new ClassCastException("Selected value must be a String[] type.");

        String[] submittedValue = (String[])value;

        ValueBinding vb = component.getValueBinding("value");
        Class valueType = null;
        Class arrayComponentType = null;
        if (vb != null) {
            valueType = vb.getType(context);
            if (valueType != null && valueType.isArray()) {
                arrayComponentType = valueType.getComponentType();
            }
        }

        Converter converter = component.getConverter();
        if (converter == null) {
            if (valueType == null) {
                // No converter, and no idea of expected type
                // --> return the submitted String array
                return submittedValue;
            }

            if (List.class.isAssignableFrom(valueType)) {
                // expected type is a List
                // --> according to javadoc of UISelectMany we assume that the element type
                //     is java.lang.String, and copy the String array to a new List
                int len = submittedValue.length;
                List lst = new ArrayList(len);
                for (int i = 0; i < len; i++) {
                    lst.add(submittedValue[i]);
                }
                return lst;
            }

            if (arrayComponentType == null) {
                throw new IllegalArgumentException("ValueBinding for UISelectMany must be of type List or Array");
            }

            if (String.class.equals(arrayComponentType)) return submittedValue; //No conversion needed for String type
            if (Object.class.equals(arrayComponentType)) return submittedValue; //No conversion for Object class

            try {
                converter = context.getApplication().createConverter(arrayComponentType);
            }
            catch (FacesException e) {
                log.error("No Converter for type " + arrayComponentType.getName() + " found");
                return submittedValue;
            }
        }

        // Now, we have a converter...
        if (valueType == null) {
            // ...but have no idea of expected type
            // --> so let's convert it to an Object array
            int len = submittedValue.length;
            Object[] convertedValues = new Object[len];
            for (int i = 0; i < len; i++) {
                convertedValues[i] = converter.getAsObject(context, component, submittedValue[i]);
            }
            return convertedValues;
        }

        if (List.class.isAssignableFrom(valueType)) {
            // Curious case: According to specs we should assume, that the element type
            // of this List is java.lang.String. But there is a Converter set for this
            // component. Because the user must know what he is doing, we will convert the values.
            int len = submittedValue.length;
            List lst = new ArrayList(len);
            for (int i = 0; i < len; i++) {
                lst.add(converter.getAsObject(context, component, submittedValue[i]));
            }
            return lst;
        }

        if (arrayComponentType == null) {
            throw new IllegalArgumentException("ValueBinding for UISelectMany must be of type List or Array");
        }

        if (arrayComponentType.isPrimitive()) {
            //primitive array
            int len = submittedValue.length;
            Object convertedValues = Array.newInstance(arrayComponentType, len);
            for (int i = 0; i < len; i++) {
                Array.set(convertedValues, i, converter.getAsObject(context, component, submittedValue[i]));
            }
            return convertedValues;
        }
        else {
            //Object array
            int len = submittedValue.length;
            Object[] convertedValues = new Object[len];
            for (int i = 0; i < len; i++) {
                convertedValues[i] = converter.getAsObject(context, component, submittedValue[i]);
            }
            return convertedValues;
        }
    }

    public static void renderChildren(FacesContext context, UIComponent component) throws java.io.IOException {
        if (component != null && component.isRendered()){
            Iterator iter = component.getChildren().iterator();
            while (iter.hasNext()){
                UIComponent child = (UIComponent)iter.next();
                renderChild(context, child);
            }
        }
    }

    public static void renderChild(FacesContext context, UIComponent child) throws java.io.IOException {
        if (child == null || !child.isRendered()) return;

        child.encodeBegin(context);
        if (child.getRendersChildren()) {
            child.encodeChildren(context);
        }
        else {
            renderChildren(context, child);
        }
        child.encodeEnd(context);

        if (!child.isRendered())
        {
            return;
        }
    }

    /** Insert parameter and its value to href. Insert correct separator '?' or '&amp;' */
    public static String insertGetParam(String href, String param, String value, String encoding) throws java.io.IOException {
        href += (href.indexOf('?') == -1) ? "?" : "&amp;";

        href += URLEncoder.encode(param, encoding);
        href += '=';
        href += URLEncoder.encode(value, encoding);

        return (href);
    }

    /** Returns all input components(input, select, option...) from the list of components(type UICommponents).
     * @param components the list of components.
     * @return Set of UIComponent - all input components from the list. If no input tag was found, return empty set.
     */
    public static Set getInputTags(List components){
        Set inputTags = new HashSet();

        Iterator iter = components.iterator();

        while (iter.hasNext()){
            UIComponent comp = (UIComponent)iter.next();
            String family = comp.getFamily();
            log.debug("processing component family:" + family);

            if (isInputComponent(family)){
                inputTags.add(comp);
            }
        }
        return(inputTags);
    }


    private static boolean isInputComponent(String name){
        String INPUT_COMPONENTS_FAMILY[] = {"UIInput", "UISelectItems", "UISelectBoolean", "UISelectOne", "UISelectMany"};

        for (int i = 0; i < INPUT_COMPONENTS_FAMILY.length; i++){
            if (name != null && name.equals(INPUT_COMPONENTS_FAMILY[i]))
                return (true);
        }
        return (false);
    }

    public static String getAttribute(UIComponent comp, String attribute){
        Map attrs = comp.getAttributes();

        Iterator iter = attrs.keySet().iterator();
        while (iter.hasNext())
            log.debug("attr:" + iter.next());

        String value = (String)attrs.get(attribute);
        if (value != null){
            log.debug("Contains name:" + value);
            return(value);
        }

        return(null);
    }
}
