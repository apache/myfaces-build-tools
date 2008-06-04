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
package org.apache.myfaces.custom.suggest;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.ValueHolder;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.apache.myfaces.component.html.ext.HtmlInputHidden;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;

/**
 * Basic HTML Renderer for the inputSuggest component.
 *
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Input"
 *   type = "org.apache.myfaces.InputSuggest"
 * 
 * @author Sean Schofield
 * @author Matt Blum
 * @version $Revision: $ $Date: $
 */
public class InputSuggestRenderer
    extends Renderer
{
    private String NEW_TEXT_KEY = "-1";

    public boolean getRendersChildren()
    {
        // must return "true" so that the UISelectItem child has been added before encode() is called
        return true;
    }

    public void decode(FacesContext context, UIComponent component)
    {

        if (isDisabledOrReadOnly(component))
        {
            return;
        }

        Map params = context.getExternalContext().getRequestParameterMap();
        String text = (String) params.get(getTextId(component, context));
        String choice = (String) params.get(getChoiceId(component, context));
        if (choice != null)
        {
            ( (EditableValueHolder) component).setSubmittedValue(choice);

            if (choice.equals(NEW_TEXT_KEY))
            {
                Map choices = getChoices(component);
                choices.put(NEW_TEXT_KEY, text);
            }
        }
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws
                                                                         IOException
    {

        if (!component.isRendered())
        {
            return;
        }

        // Get the current value
        String value = (String) ( (EditableValueHolder) component).
            getSubmittedValue();
        if (value == null)
        {
            value = (String) ( (ValueHolder) component).getValue();
        }

        String text = null;
        Map choices = getChoices(component);
        if (value != null && choices != null)
        {
            text = (String) choices.get(value);
        }

        ResponseWriter out = context.getResponseWriter();
        renderInputField(out, text, getTextId(component, context), component);

        // render hidden input field containing the user's choice
        HtmlInputHidden hiddenChoice = new HtmlInputHidden();
        hiddenChoice.setId(getChoiceId(component, context));
        hiddenChoice.setValue(value);
        hiddenChoice.getAttributes().put(JSFAttr.FORCE_ID_ATTR, Boolean.TRUE);
        /** @todo use new encode recursive helper method once available */
        hiddenChoice.encodeBegin(context);
        hiddenChoice.encodeEnd(context);

        encodeSuggestions(context, out, choices,
                          getSuggestionsId(component, context), component);
        encodeStyles(component, context);
        encodeJavascript(component, context);
    }

    private void renderInputField(ResponseWriter out, String text,
                                  String clientId, UIComponent component) throws
                                                                          IOException
    {

        out.startElement("input", component);
        out.writeAttribute("name", clientId, null);
        out.writeAttribute("id", clientId, null);

        if (text != null)
        {
            out.writeAttribute("value", text, "value");
        }
        else
        {
            out.writeAttribute("value", "", "value");
        }

        component.getAttributes().put("autocomplete","off");

        HtmlRendererUtils.renderHTMLAttributes(out,
                                               component, HTML.INPUT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);

        if((component instanceof HtmlInputText) && ((HtmlInputText) component).isDisabled())
        {
            out.writeAttribute(HTML.DISABLED_ATTR, Boolean.TRUE, null);
        }

        out.endElement("input");
    }

    private void encodeSuggestions(FacesContext context, ResponseWriter out,
                                   Map choices, String clientId,
                                   UIComponent component) throws IOException
    {
        //String choiceId = getChoiceId(component, context);
        /** @todo have the div class be the one specified by user or default of ACDiv */

        out.startElement(HTML.DIV_ELEM, component);
        out.writeAttribute(HTML.ID_ATTR, clientId, null);

        Iterator i = choices.keySet().iterator();
        while (i.hasNext())
        {
            String choice = (String) i.next();
            if(choice.compareTo("-1")==0) continue;
            String text = (String) choices.get(choice);
            out.startElement(HTML.DIV_ELEM, null);
            out.writeAttribute(HTML.ID_ATTR,
                               component.getClientId(context) + "_choice" +
                               choice, null);
            out.writeAttribute(HTML.CLASS_ATTR, "ACdiv", null);
            out.writeText(text, null);
            out.endElement(HTML.DIV_ELEM);
        }

        out.endElement(HTML.DIV_ELEM);
    }

    /**
     * Returns true if one or both of the HTML attributes "disabled"
     * or "readonly" are set to true.
     */
    private boolean isDisabledOrReadOnly(UIComponent component)
    {
        boolean disabled = false;
        boolean readOnly = false;

        Object disabledAttr = component.getAttributes().get("disabled");
        if (disabledAttr != null)
        {
            disabled = disabledAttr.equals(Boolean.TRUE);
        }
        Object readOnlyAttr = component.getAttributes().get("readonly");
        if (readOnlyAttr != null)
        {
            readOnly = readOnlyAttr.equals(Boolean.TRUE);
        }
        return disabled || readOnly;
    }

    private Map getChoices(UIComponent component)
    {
        // Get the choices
        Object choices = null;
        Iterator i = component.getChildren().iterator();
        while (i.hasNext())
        {
            UIComponent kid = (UIComponent) i.next();
            // Should handle UISelectItem as well
            if (kid instanceof UISelectItems)
            {
                choices = ( (UISelectItems) kid).getValue();
            }
        }

        /** @todo selectItems may not necessarily be a map */

//        if (choices instanceof Map)
//        {
            return (Map) choices;
//        }
//        else if (choices instanceof Collection)
//        {
//
//        }
//        else if (choices instanceof Object[])
//        {
//
//        }
//        else if (choices instanceof SelectItem)
//        {
//
//        }
//
//        throw new IllegalArgumentException(
//            "Unsupported object type used for choices.");
    }


//  (this is not called from anywhere)
//    /**
//     * Helper method for getting the boolean value of an attribute.  If the attribute is not specified,
//     * then return the default value.
//     *
//     * @param component The component for which the attributes are to be checked.
//     * @param attributeName The name of the boolean attribute.
//     * @param defaultValue The default value of the attribute (to be returned if no value found).
//     * @return boolean
//     */
//    private boolean getBoolean(UIComponent component, String attributeName,
//                               boolean defaultValue)
//    {
//        Boolean booleanAttr = (Boolean) component.getAttributes().get(
//            attributeName);
//
//        if (booleanAttr == null)
//        {
//            return defaultValue;
//        }
//        else
//        {
//            return booleanAttr.booleanValue();
//        }
//    }

    /**
     * Encodes necessary style information.
     *
     * @param component UIComponent
     * @param context FacesContext
     * @throws IOException
     */
    private void encodeStyles(UIComponent component, FacesContext context) throws
                                                                           IOException
    {
        AddResource addResource = AddResourceFactory.getInstance(context);
        String styleLocation = (String) component.getAttributes().get(JSFAttr.
            STYLE_LOCATION);
        if (styleLocation == null)
        {
            addResource.addStyleSheet(context, AddResource.HEADER_BEGIN, InputSuggestRenderer.class,
                                      "css/suggest.css");
        }
        else
        {
            addResource.addStyleSheet(context, AddResource.HEADER_BEGIN, styleLocation + "/suggest.css");
        }
    }

    /**
     * Encodes necessary javascript functions.
     *
     * @param component UIComponent
     * @param context FacesContext
     * @throws IOException
     */
    private void encodeJavascript(UIComponent component, FacesContext context) throws
                                                                               IOException
    {
        ResponseWriter out = context.getResponseWriter();

        AddResource addResource = AddResourceFactory.getInstance(context);
        String javascriptLocation = (String) component.getAttributes().get(
            JSFAttr.JAVASCRIPT_LOCATION);
        if (javascriptLocation == null)
        {
            addResource.addJavaScriptHere(context, InputSuggestRenderer.class,
                                          "javascript/suggest.js");
        }
        else
        {
            addResource.addJavaScriptHere(context, javascriptLocation + "/suggest.js");
        }

        // now add javascript that depends on the component and cannot be part of javascript file
        out.startElement(HTML.SCRIPT_ELEM, null);
        out.writeAttribute(HTML.TYPE_ATTR, "text/javascript", null);

        String textId = getTextId(component, context);
        String choiceId = getChoiceId(component, context);
        String suggestionsId = getSuggestionsId(component, context);

        // can't use ':', '|' or '.' chars in javascript variable name
        String modifiedTextId = textId.replace(':', '_');
        modifiedTextId = modifiedTextId.replace('|', '_');
        modifiedTextId = modifiedTextId.replace('.', '_');

        /** @todo make these values dependent on component attributes */
        out.writeText("\nvar " + modifiedTextId +
                      "Row = -1; // this should always be initialized to -1\n", null);
        out.writeText("var " + modifiedTextId +
                      "RowDiv = null; // this should always be initialized to null\n", null);
        out.writeText("var " + modifiedTextId +
                      "MinRow = 0; // this should always be initialized to 0\n", null);
        out.writeText("var ACrowHeight = 15;\n", null);
        out.writeText("var ACfield = document.getElementById('" + textId +
                      "');\n", null);
        out.writeText("var " + modifiedTextId + "Scroll = true;\n", null);
        out.writeText("var " + modifiedTextId + "CaseSensitive = false;\n", null);
        out.writeText("var " + modifiedTextId + "DisplayRows = 5;\n", null);
        out.writeText("var " + modifiedTextId +
                      "Div = document.getElementById('" + suggestionsId +
                      "');\n", null);
        out.writeText("var " + modifiedTextId + "HiddenFldId = '" + choiceId +
                      "';\n", null);
        out.writeText("var " + modifiedTextId + "NormalClass = 'ACdiv';\n", null);
        out.writeText("var " + modifiedTextId +
                      "HighlightClass = 'AChighlighted';\n", null);
        out.writeText("ACfield.onfocus = new Function('" + modifiedTextId +
                      "Div.style.visibility = \"visible\"');\n", null);
        out.writeText("ACfield.onblur = new Function('blurACfld(this)');\n", null);
        out.writeText("ACfield.onkeyup = new Function(\"event\", \"return handleACkeyUp(this, event)\");\n", null);
        out.writeText("ACfield.onkeydown = new Function(\"event\", \"return handleACkeyDown(this, event)\");\n", null);
        out.writeText("var " + modifiedTextId + "Options = " + modifiedTextId +
                      "Div.getElementsByTagName(\"DIV\");\n", null);
        out.writeText(modifiedTextId +
                      "Div.onscroll = new Function(\"setACfieldFocus('" +
                      textId + "')\");\n", null);

        out.writeText("var optLen = " + modifiedTextId + "Options.length;\n", null);
        out.writeText("for (var ii=0; ii<optLen; ii++) {\n", null);
        out.writeText(modifiedTextId +
                      "Options[ii].style.height = ACrowHeight + 'px';\n", null);
        out.writeText(modifiedTextId +
                      "Options[ii].onmouseover = new Function(\"highlightACDiv(this, '" +
                      textId +
                      "', \" + ii + \")\");\n", null);
        out.writeText(modifiedTextId +
                      "Options[ii].onmouseout = new Function(\"unHighlightACDiv(this, '" +
                      textId + "')\");\n", null);
        out.writeText(modifiedTextId +
                      "Options[ii].onmousedown = new Function(\"selectACDiv('" +
                      textId + "')\");\n", null);
        out.writeText("}\n", null);

        out.writeText("if (navigator.appVersion.toLowerCase().indexOf('msie') != -1 && " +
                      "navigator.userAgent.toLowerCase().indexOf('opera') == -1)\n", null);
        out.writeText("document.writeln('<iframe id=\"" + modifiedTextId + "Shim\" src=\"javascript:false;\" " +
                      "scrolling=\"no\" frameborder=\"0\" style=\"position:absolute; top:0px; left:0px;\">" +
                      "</iframe>');\n", null);

//       out.writeText("var backingBean_inputFieldScroll = true;\n", null);
//       out.writeText("var backingBean_inputFieldCaseSensitive = false;\n", null);
//       out.writeText("var backingBean_inputFieldDisplayRows = 5;\n", null);
//       out.writeText("var backingBean_inputFieldDiv = document.getElementById('" + suggestionsId + "');\n", null);
//       out.writeText("var backingBean_inputFieldNormalClass = 'ACdiv';\n", null);
//       out.writeText("var backingBean_inputFieldHighlightClass = 'AChighlighted';\n", null);
//       out.writeText("ACfield.onfocus = new Function('backingBean_inputFieldDiv.style.visibility = \"visible\"');\n", null);
//       out.writeText("ACfield.onblur = new Function('backingBean_inputFieldDiv.style.visibility = \"hidden\"');\n", null);
//       out.writeText("ACfield.onkeyup = new Function(\"event\", \"return handleACkeyUp(this, event)\");\n", null);
//       out.writeText("ACfield.onkeydown = new Function(\"event\", \"return handleACkeyDown(this, event)\");\n", null);
//       out.writeText("var backingBean_inputFieldOptions = backingBean_inputFieldDiv.getElementsByTagName(\"DIV\");\n", null);
//       out.writeText("backingBean_inputFieldDiv.onscroll = new Function(\"setACfieldFocus('" + textId + "')\");\n", null);
//
//       out.writeText("var optLen = backingBean_inputFieldOptions.length;\n", null);
//       out.writeText("for (var ii=0; ii<optLen; ii++) {\n", null);
//       out.writeText("backingBean_inputFieldOptions[ii].style.height = ACrowHeight + 'px';\n", null);
//       out.writeText("backingBean_inputFieldOptions[ii].onmouseover = new Function(\"highlightACDiv(this, '" + textId +
//                     "', \" + ii + \")\");\n", null);
//       out.writeText("backingBean_inputFieldOptions[ii].onmouseout = new Function(\"unHighlightACDiv(this, '" + textId + "')\");\n", null);
//       out.writeText("backingBean_inputFieldOptions[ii].onmousedown = new Function(\"selectACDiv('" + textId + "')\");\n", null);
//       out.writeText("}\n", null);

        out.endElement(HTML.SCRIPT_ELEM);

    }

    private String getTextId(UIComponent component, FacesContext context)
    {
        return (component.getId() + "_text");
    }

    private String getChoiceId(UIComponent component, FacesContext context)
    {
        return (component.getId() + "_choice");
    }

    private String getSuggestionsId(UIComponent component, FacesContext context)
    {
        return ("AC" + getTextId(component, context));
    }
}
