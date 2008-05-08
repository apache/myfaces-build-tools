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
package org.apache.myfaces.custom.collapsiblepanel;

import org.apache.myfaces.renderkit.html.util.DummyFormUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;

import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Panel"
 *   type = "org.apache.myfaces.CollapsiblePanel"
 *   
 * @author Kalle Korhonen (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlCollapsiblePanelRenderer extends HtmlRenderer {
    //private static final Log log = LogFactory.getLog(HtmlCollapsiblePanel.class);
    private static final String LINK_ID = "ToggleCollapsed".intern();
    private static final String COLLAPSED_STATE_ID = "CollapsedState".intern();

    public boolean getRendersChildren() {
        return true;
    }

    public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        // RendererUtils.checkParamValidity(facesContext, uiComponent, HtmlCollapsiblePanel.class);
        ResponseWriter writer = facesContext.getResponseWriter();
        HtmlCollapsiblePanel collapsiblePanel = (HtmlCollapsiblePanel) uiComponent;

        UIComponent headerComp = collapsiblePanel.getFacet("header");
        UIComponent linkToReset = null;
        String resetId = null;

        if (headerComp != null) {
            linkToReset = RendererUtils.findComponent(headerComp, HtmlHeaderLink.class);

            if (linkToReset != null) {
                resetId = linkToReset.getId();
                linkToReset.setId(collapsiblePanel.getId() + LINK_ID);
            }
        }
        else {
            HtmlCommandLink link = getLink(facesContext, collapsiblePanel);
            collapsiblePanel.getChildren().add(link);

            headerComp = link;
        }

        //Render the current state - collapsed or not - of the panel.
        HtmlRendererUtils.renderHiddenInputField(writer, collapsiblePanel.getClientId(facesContext) +
            COLLAPSED_STATE_ID,
                                                 collapsiblePanel.getSubmittedValue() != null ?
                                                     collapsiblePanel.getSubmittedValue() : (collapsiblePanel.isCollapsed() + ""));

        // Always render the header - to be able toggle the collapsed state
        RendererUtils.renderChild(facesContext, headerComp);
        headerComp.setRendered(false);

        // conditionally render the rest of the children
        if (!collapsiblePanel.isCollapsed()) {
            HtmlRendererUtils.writePrettyLineSeparator(facesContext);
            // TODO apply styles from the parent element to this DIV
            writer.startElement(HTML.DIV_ELEM, uiComponent);
            RendererUtils.renderChildren(facesContext, uiComponent);
            writer.endElement(HTML.DIV_ELEM);
            HtmlRendererUtils.writePrettyLineSeparator(facesContext);
        }
        else {
            UIComponent component = collapsiblePanel.getFacet("closedContent");
            if (component != null) {
                writer.startElement(HTML.DIV_ELEM, uiComponent);
                RendererUtils.renderChild(facesContext, component);
                writer.endElement(HTML.DIV_ELEM);
                HtmlRendererUtils.writePrettyLineSeparator(facesContext);
            }
        }

        headerComp.setRendered(true);

        if (linkToReset != null) {
            linkToReset.setId(resetId);
        }
    }

    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        RendererUtils.checkParamValidity(facesContext, uiComponent, HtmlCollapsiblePanel.class);
        ResponseWriter writer = facesContext.getResponseWriter();

        HtmlRendererUtils.writePrettyLineSeparator(facesContext);
        writer.startElement(HTML.DIV_ELEM, uiComponent);

        ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
        String viewId = facesContext.getViewRoot().getViewId();
        viewHandler.getActionURL(facesContext, viewId);

        facesContext.getApplication();
    }


    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        //RendererUtils.checkParamValidity(facesContext, uiComponent, HtmlCollapsiblePanel.class);
        ResponseWriter writer = facesContext.getResponseWriter();
        writer.endElement(HTML.DIV_ELEM);
        HtmlRendererUtils.writePrettyLineSeparator(facesContext);
    }

    public void decode(FacesContext facesContext, UIComponent uiComponent) {
        RendererUtils.checkParamValidity(facesContext, uiComponent, HtmlCollapsiblePanel.class);
        HtmlCollapsiblePanel collapsiblePanel = (HtmlCollapsiblePanel) uiComponent;

        Map reqParams = facesContext.getExternalContext().getRequestParameterMap();

        String togglingIndicated = (String) reqParams.get(HtmlRendererUtils
                .getHiddenCommandLinkFieldName(
                DummyFormUtils.findNestingForm(collapsiblePanel, facesContext)));
        // if togglingIndicated is null this application could be running within the RI.
        // The RI denotes link activation by adding a hidden field with the name
        // and value of the link client ID.
        if (togglingIndicated == null
                && reqParams.containsKey(collapsiblePanel.getClientId(facesContext) + LINK_ID)) {
            togglingIndicated = collapsiblePanel.getClientId(facesContext) + LINK_ID;
        }

        String reqValue = (String) reqParams.get(
            collapsiblePanel.getClientId(facesContext) + COLLAPSED_STATE_ID);

        collapsiblePanel.setCurrentlyCollapsed(HtmlCollapsiblePanel.isCollapsed(reqValue));

        if ((collapsiblePanel.getClientId(facesContext) + LINK_ID).equals(togglingIndicated)) {
            if (reqValue != null)
                collapsiblePanel.setSubmittedValue("" + !collapsiblePanel.isCurrentlyCollapsed());
            else
                collapsiblePanel.setSubmittedValue("" + !collapsiblePanel.isCollapsed());
        }
        else {
            if (reqValue != null)
                collapsiblePanel.setSubmittedValue("" + collapsiblePanel.isCurrentlyCollapsed());
        }

    }

    protected HtmlCommandLink getLink(FacesContext facesContext, HtmlCollapsiblePanel collapsiblePanel)
        throws IOException {
        Application application = facesContext.getApplication();
        HtmlCommandLink link = (HtmlCommandLink) application.createComponent(HtmlCommandLink.COMPONENT_TYPE);
        link.setId(collapsiblePanel.getId() + LINK_ID);
        link.setTransient(true);
        link.setImmediate(true);
        //link.addActionListener(new ChangeCollapsedHandler());

        List children = link.getChildren();
        // Create the indicator. You could later make this conditional and render optional images instead
        HtmlOutputText uiText = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        uiText.setTransient(true);
        uiText.setValue(collapsiblePanel.isCollapsed() ? "&gt;" : "&#957;");
        uiText.setEscape(false);
        uiText.setStyleClass(collapsiblePanel.getStyleClass());
        uiText.setStyle(collapsiblePanel.getStyle());
        children.add(uiText);

        // Create the optional label
        String label = collapsiblePanel.getTitle();
        if (label != null) {
            uiText = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
            uiText.setTransient(true);
            uiText.setValue(" " + label);
            uiText.setStyleClass(collapsiblePanel.getStyleClass());
            uiText.setStyle(collapsiblePanel.getStyle());
            children.add(uiText);
        }
        return link;
    }

    public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue) throws ConverterException {
        if (submittedValue instanceof String) {
            return Boolean.valueOf((String) submittedValue);
        }

        return super.getConvertedValue(context, component, submittedValue);
    }

    // Couldn't get an ActionListner for a link to work properly. With each page submit, one more
    // event was fired. I assume it is because the link component was set to transparent, and I didn't
    // know how to get a reference to it back in a new encoding phase
    /*
    public static class ChangeCollapsedHandler implements ActionListener {
    	// Can't make this class anonymous, because it won't work with state saving
    	// refer to http://forum.java.sun.com/thread.jspa?messageID=2885214&#2885214

		  /* (non-Javadoc)
		  * @see javax.faces.event.ActionListener#processAction(javax.faces.event.ActionEvent)
		  */
    /*
      public void processAction(ActionEvent actionEvent) throws AbortProcessingException {
    		log.info("Got action event, processing " + actionEvent.getComponent().getId() + ", phase id " + actionEvent.getPhaseId() );
    		if (!(actionEvent.getComponent().getParent() instanceof HtmlCollapsiblePanel) )
    			throw new AbortProcessingException("The parent of the action source was of unexpected type, HtmlCollapsiblePanel was expected");
    		HtmlCollapsiblePanel collapsiblePanel = (HtmlCollapsiblePanel)actionEvent.getComponent().getParent();
    		collapsiblePanel.setFirstCollapsed(!collapsiblePanel.isFirstCollapsed() );
        // Note that we need to remove the listeners here, otherwise they will be fired again for old components,
        // don't quite understand why
        ActionListener[] listeners = collapsiblePanel.getLink().getActionListeners();
        for (int i= 0; i< listeners.length; i++) collapsiblePanel.getLink().removeActionListener(listeners[i]);
    	}
		}
    */
}
