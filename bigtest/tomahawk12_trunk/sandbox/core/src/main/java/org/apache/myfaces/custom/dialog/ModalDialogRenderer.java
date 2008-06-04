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

package org.apache.myfaces.custom.dialog;

import org.apache.myfaces.custom.dojo.DojoUtils;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Panel"
 *   type = "org.apache.myfaces.ModalDialog"
 *
 */
public class ModalDialogRenderer extends HtmlRenderer {
    public static final String RENDERER_TYPE = "org.apache.myfaces.ModalDialog";

    public static final String DIV_ID_PREFIX = "_div";

    //@Override
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        String javascriptLocation = (String) component.getAttributes().get(JSFAttr.JAVASCRIPT_LOCATION);
        DojoUtils.addMainInclude(context, component, javascriptLocation,
                                 DojoUtils.getDjConfigInstance(context));
        DojoUtils.addRequire(context, component, "dojo.widget.Dialog");

        writeModalDialogBegin(context, (ModalDialog) component, context.getResponseWriter());
    }

    //@Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ModalDialog dlg = (ModalDialog) component;

		StringBuffer buf = new StringBuffer();

        buf.append("</div>");

		writeDialogLoader(context, dlg, buf);

		context.getResponseWriter().write(buf.toString());

		if (dlg.getViewId() != null)
		{
			RendererUtils.renderChildren(context, component);
        	HtmlRendererUtils.writePrettyLineSeparator(context);
		}
    }

    private void appendHiderIds(StringBuffer buf, ModalDialog dlg)
	{
		List hiders = new ArrayList();

		if (dlg.getHiderIds() != null)
		{
            hiders.addAll(Arrays.asList(dlg.getHiderIds().split(",")));
        }
		if (isRenderCloseButton(dlg) && dlg.getDialogTitle() != null)
		{
			hiders.add(dlg.getDialogVar() + "Closer");
		}

		for (int i = 0; i < hiders.size(); i++)
		{
            String varName = "btn" + i;
            buf.append("var ").append(varName).append(" = document.getElementById(\"")
            .append(((String) hiders.get(i)).trim()).append("\");")
            .append(dlg.getDialogVar()).append(".setCloseControl(").append(varName).append(");");
        }
    }

    private void appendDialogAttributes(StringBuffer buf, ModalDialog dlg) {
        if(dlg.getDialogAttr() == null)
            return;

        StringTokenizer it = new StringTokenizer(dlg.getDialogAttr(), " ");
        while(it.hasMoreElements())
        {
            String[] pair = it.nextToken().split("=");
            String attribute = pair[0];
            String value = pair[1].replaceAll("'", "");
            try {
                Double number = new Double(value);
            }
            catch(NumberFormatException e) {
                value = new StringBuffer("\"").append(value).append("\"").toString();
            }
            buf.append(", ").append(attribute).append(":").append(value);
        }
    }

    private void writeModalDialogBegin(FacesContext context, ModalDialog dlg, ResponseWriter writer) throws IOException {
        StringBuffer buf = new StringBuffer();

		// writeDialogLoader(context, dlg, buf);

		String dlgId = getDialogId(dlg);
        buf.append("<div id=\"").append(dlgId).append("\"");
        if(dlg.getStyle() != null)
            buf.append(" style=\"").append(dlg.getStyle()).append("\"");
        if(dlg.getStyleClass() != null)
            buf.append(" class=\"").append(dlg.getStyleClass()).append("\"");
        buf.append(">");

        writer.write(buf.toString());
    }

	private String getDialogId(ModalDialog dlg)
	{
		String dlgId = dlg.getId() != null ?
					   dlg.getId() :
					   new StringBuffer(dlg.getDialogId()).append(DIV_ID_PREFIX).toString();
		return dlgId;
	}

	private String writeDialogLoader(FacesContext context, ModalDialog dlg, StringBuffer buf)
	{
		String dlgId = getDialogId(dlg);
		buf.append("<script type=\"text/javascript\">")
        .append("var ").append(dlg.getDialogVar()).append(";")
        .append("function "+dlg.getDialogVar()+"_loader(e) {").append(dlg.getDialogVar())
        .append(" = dojo.widget.createWidget(\"dialog\", {id:")
        .append("\"").append(dlg.getDialogId()).append("\"");

		appendDialogAttributes(buf, dlg);

		buf.append("}, dojo.byId(\"").append(dlgId).append("\"));");

		appendHiderIds(buf, dlg);

		if (dlg.getViewId() != null)
		{
			appendShowHideView(context, buf, dlg);
		}

		buf.append("}")

		// do not user timout, else you'll break the submitOnEvent component
		// .append("setTimeout('"+dlg.getDialogVar()+"_loader();', 50);")
		// do not user direct loading, else you'll break IE sometimes (always?)
		// .append(dlg.getDialogVar()+"_loader();")
		// looks like this works best for now
		.append("dojo.addOnLoad(function() {"+dlg.getDialogVar()+"_loader();});")

		.append("</script>");
		return dlgId;
	}

	private void appendShowHideView(FacesContext context, StringBuffer buf, ModalDialog dlg)
	{
		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append(context.getExternalContext().getRequestContextPath());
		sbUrl.append("/");
		sbUrl.append(dlg.getViewId());
		String encodedUrl = context.getExternalContext().encodeActionURL(sbUrl.toString());

		 buf.append(dlg.getDialogVar()).append(".oldOnShow=").append(dlg.getDialogVar()).append(".onShow;")
                   .append(dlg.getDialogVar())
			.append(".onShow = function() {")
                        .append("this.oldOnShow();")
			.append("var content = document.getElementById(\"modalDialogContent")
			.append(dlg.getDialogVar())
			.append("\"); ")
			.append("window._myfaces_currentModal=")
			.append(dlg.getDialogVar())
			.append("; ")
			.append(dlg.getDialogVar())
			.append("._myfaces_ok=false; ")
			.append("content.contentWindow.location.replace('")
			.append(encodedUrl)
			.append("'); ")
			.append("}; ");

                buf.append(dlg.getDialogVar()).append(".oldOnHide=").append(dlg.getDialogVar()).append(".onHide;")
                   .append(dlg.getDialogVar())
			.append(".onHide = function() {")
                        .append("this.oldOnHide();")
			.append("window._myfaces_currentModal=null;")
			.append("var content = document.getElementById(\"modalDialogContent")
			.append(dlg.getDialogVar())
			.append("\"); ")
			.append("content.contentWindow.location.replace('javascript:false;'); ")
			.append("}; ");
	}

	public boolean getRendersChildren()
    {
        return true;
    }
    /*
     * (non-Javadoc)
     *
     * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
		ModalDialog dlg = (ModalDialog) uiComponent;
		ResponseWriter writer = facesContext.getResponseWriter();

		UIComponent titleFacet = dlg.getFacet("titleBar");
		if (titleFacet != null)
		{
			RendererUtils.renderChild(facesContext, titleFacet);
		}
		else if (dlg.getDialogTitle() != null)
		{
			AddResourceFactory.getInstance(facesContext).addStyleSheet(facesContext, AddResource.HEADER_BEGIN,  ModalDialog.class, "modalDialog.css");

			writer.startElement(HTML.TABLE_ELEM, dlg);
			writer.writeAttribute(HTML.CLASS_ATTR, "modalDialogDecoration " + getStyleName(dlg, "Decoration") , null);
			writer.writeAttribute(HTML.CELLPADDING_ATTR, "2", null);
			writer.writeAttribute(HTML.CELLSPACING_ATTR, "0", null);

			writer.startElement(HTML.TR_ELEM, dlg);
			writer.writeAttribute(HTML.CLASS_ATTR, "modalDialogTitle " + getStyleName(dlg, "Title"), null);

			writer.startElement(HTML.TD_ELEM, dlg);
			writer.writeAttribute(HTML.CLASS_ATTR, "modalDialogTitleLeft " + getStyleName(dlg, "TitleLeft"), null);
			writer.writeText(dlg.getDialogTitle(), null);
			writer.endElement(HTML.TD_ELEM);

			writer.startElement(HTML.TD_ELEM, dlg);
			writer.writeAttribute(HTML.CLASS_ATTR, "modalDialogTitleRight " + getStyleName(dlg, "TitleRight"), null);
			if (isRenderCloseButton(dlg))
			{
				String imageUri = AddResourceFactory.getInstance(facesContext).getResourceUri(facesContext, ModalDialog.class, "close.gif");
				writer.startElement(HTML.IMG_ELEM, dlg);
				writer.writeAttribute(HTML.ID_ATTR, dlg.getDialogVar() + "Closer", null);
				writer.writeAttribute(HTML.SRC_ATTR, imageUri, null);
				writer.writeAttribute(HTML.CLASS_ATTR, "modalDialogCloser " + getStyleName(dlg, "Closer"), null);
				writer.endElement(HTML.IMG_ELEM);
			}
			writer.endElement(HTML.TD_ELEM);

			writer.endElement(HTML.TR_ELEM);
			writer.endElement(HTML.TABLE_ELEM);
		}

		if (dlg.getViewId() != null)
		{
			renderDialogViewFrame(facesContext, dlg);
		}
		else
		{
			RendererUtils.renderChildren(facesContext, uiComponent);
        	HtmlRendererUtils.writePrettyLineSeparator(facesContext);
		}
	}

	protected boolean isRenderCloseButton(ModalDialog dlg)
	{
		return !Boolean.FALSE.equals(dlg.getCloseButton());
	}

	private String getStyleName(ModalDialog dlg, String suffix)
	{
		if (dlg.getStyleClass() != null)
		{
			return dlg.getStyleClass() + suffix;
		}

		return "";
	}

	private void renderDialogViewFrame(FacesContext facesContext, ModalDialog dlg) throws IOException
	{
		ResponseWriter writer = facesContext.getResponseWriter();

		writer.startElement(HTML.IFRAME_ELEM, dlg);
		writer.writeAttribute(HTML.ID_ATTR, "modalDialogContent" + dlg.getDialogVar(), null);
		writer.writeAttribute(HTML.CLASS_ATTR, "modalDialogContent " + getStyleName(dlg, "Content"), null);
		writer.writeAttribute(HTML.SCROLLING_ATTR, "auto", null);
		writer.writeAttribute(HTML.FRAMEBORDER_ATTR, "0", null);
		writer.endElement(HTML.IFRAME_ELEM);
	}
}
