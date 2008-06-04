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
package org.apache.myfaces.test.utils;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class TestUtils
{
    /** Default Logger */
    private static final Log log = LogFactory.getLog(TestUtils.class);

    /** utility class, do not instantiate */
    private TestUtils()
    {
        // utility class, disable instantiation
    }

    /**
     * Add all of Tomahawk's renderers.  Currently this is not tied to 
     * faces-config.xml, so all change to the file MUST also be made here.
     * 
     * @param facesContext
     * @todo Do not add renderer if renderer is already added.
     */
    public static void addDefaultRenderers(FacesContext facesContext)
    {
        // Standard HTML Renderers
        addRenderer(facesContext, "javax.faces.Command", "javax.faces.Button",
                "org.apache.myfaces.renderkit.html.HtmlButtonRenderer");

        addRenderer(facesContext, "javax.faces.SelectBoolean",
                "javax.faces.Checkbox",
                "org.apache.myfaces.renderkit.html.HtmlCheckboxRenderer");

        addRenderer(facesContext, "javax.faces.SelectMany",
                "javax.faces.Checkbox",
                "org.apache.myfaces.renderkit.html.HtmlCheckboxRenderer");

        addRenderer(facesContext, "javax.faces.Form", "javax.faces.Form",
                "org.apache.myfaces.renderkit.html.HtmlFormRenderer");

        addRenderer(facesContext, "javax.faces.Panel", "javax.faces.Grid",
                "org.apache.myfaces.renderkit.html.HtmlGridRenderer");

        addRenderer(facesContext, "javax.faces.Panel", "javax.faces.Group",
                "org.apache.myfaces.renderkit.html.HtmlGroupRenderer");

        addRenderer(facesContext, "javax.faces.Input", "javax.faces.Hidden",
                "org.apache.myfaces.renderkit.html.HtmlHiddenRenderer");

        addRenderer(facesContext, "javax.faces.Graphic", "javax.faces.Image",
                "org.apache.myfaces.renderkit.html.HtmlImageRenderer");

        addRenderer(facesContext, "javax.faces.Output", "javax.faces.Label",
                "org.apache.myfaces.renderkit.html.HtmlLabelRenderer");

        addRenderer(facesContext, "javax.faces.Output", "javax.faces.Link",
                "org.apache.myfaces.renderkit.html.HtmlLinkRenderer");

        addRenderer(facesContext, "javax.faces.Command", "javax.faces.Link",
                "org.apache.myfaces.renderkit.html.HtmlLinkRenderer");

        addRenderer(facesContext, "javax.faces.SelectOne",
                "javax.faces.Listbox",
                "org.apache.myfaces.renderkit.html.HtmlListboxRenderer");

        addRenderer(facesContext, "javax.faces.SelectMany",
                "javax.faces.Listbox",
                "org.apache.myfaces.renderkit.html.HtmlListboxRenderer");

        addRenderer(facesContext, "javax.faces.SelectOne", "javax.faces.Menu",
                "org.apache.myfaces.renderkit.html.HtmlMenuRenderer");

        addRenderer(facesContext, "javax.faces.SelectMany", "javax.faces.Menu",
                "org.apache.myfaces.renderkit.html.HtmlMenuRenderer");

        addRenderer(facesContext, "javax.faces.Message", "javax.faces.Message",
                "org.apache.myfaces.renderkit.html.HtmlMessageRenderer");

        addRenderer(facesContext, "javax.faces.Output", "javax.faces.Format",
                "org.apache.myfaces.renderkit.html.HtmlFormatRenderer");

        addRenderer(facesContext, "javax.faces.Messages",
                "javax.faces.Messages",
                "org.apache.myfaces.renderkit.html.HtmlMessagesRenderer");

        addRenderer(facesContext, "javax.faces.SelectOne", "javax.faces.Radio",
                "org.apache.myfaces.renderkit.html.HtmlRadioRenderer");

        addRenderer(facesContext, "javax.faces.Input", "javax.faces.Secret",
                "org.apache.myfaces.renderkit.html.HtmlSecretRenderer");

        addRenderer(facesContext, "javax.faces.Data", "javax.faces.Table",
                "org.apache.myfaces.renderkit.html.HtmlTableRenderer");

        addRenderer(facesContext, "javax.faces.Input", "javax.faces.Textarea",
                "org.apache.myfaces.renderkit.html.HtmlTextareaRenderer");

        addRenderer(facesContext, "javax.faces.Input", "javax.faces.Text",
                "org.apache.myfaces.renderkit.html.HtmlTextRenderer");

        addRenderer(facesContext, "javax.faces.Output", "javax.faces.Text",
                "org.apache.myfaces.renderkit.html.HtmlTextRenderer");

        // extended standard renderers

        addRenderer(facesContext, "javax.faces.Command",
                "org.apache.myfaces.Button",
                "org.apache.myfaces.renderkit.html.ext.HtmlButtonRenderer");

        addRenderer(facesContext, "javax.faces.Command",
                "org.apache.myfaces.Link",
                "org.apache.myfaces.renderkit.html.ext.HtmlLinkRenderer");

        addRenderer(facesContext, "javax.faces.Data",
                "org.apache.myfaces.Table",
                "org.apache.myfaces.renderkit.html.ext.HtmlTableRenderer");

        addRenderer(facesContext, "javax.faces.Panel",
                "org.apache.myfaces.Grid",
                "org.apache.myfaces.renderkit.html.ext.HtmlGridRenderer");

        addRenderer(facesContext, "javax.faces.Panel",
                "org.apache.myfaces.Group",
                "org.apache.myfaces.renderkit.html.ext.HtmlGroupRenderer");

        addRenderer(facesContext, "javax.faces.Input",
                "org.apache.myfaces.Secret",
                "org.apache.myfaces.renderkit.html.ext.HtmlSecretRenderer");

        addRenderer(facesContext, "javax.faces.Graphic",
                "org.apache.myfaces.Image",
                "org.apache.myfaces.renderkit.html.ext.HtmlImageRenderer");

        addRenderer(facesContext, "javax.faces.Input",
                "org.apache.myfaces.Text",
                "org.apache.myfaces.renderkit.html.ext.HtmlTextRenderer");

        addRenderer(facesContext, "javax.faces.Input",
                "org.apache.myfaces.TextHelp",
                "org.apache.myfaces.custom.inputTextHelp.HtmlTextHelpRenderer");

        addRenderer(facesContext, "javax.faces.Input",
                "org.apache.myfaces.Textarea",
                "org.apache.myfaces.renderkit.html.ext.HtmlTextareaRenderer");

        addRenderer(facesContext, "javax.faces.Message",
                "org.apache.myfaces.Message",
                "org.apache.myfaces.renderkit.html.ext.HtmlMessageRenderer");

        addRenderer(facesContext, "javax.faces.Messages",
                "org.apache.myfaces.Messages",
                "org.apache.myfaces.renderkit.html.ext.HtmlMessagesRenderer");

        addRenderer(facesContext, "javax.faces.Output",
                "org.apache.myfaces.Text",
                "org.apache.myfaces.renderkit.html.ext.HtmlTextRenderer");

        addRenderer(facesContext, "javax.faces.SelectBoolean",
                "org.apache.myfaces.Checkbox",
                "org.apache.myfaces.renderkit.html.ext.HtmlCheckboxRenderer");

        addRenderer(facesContext, "javax.faces.SelectMany",
                "org.apache.myfaces.Checkbox",
                "org.apache.myfaces.renderkit.html.ext.HtmlCheckboxRenderer");

        addRenderer(facesContext, "javax.faces.SelectOne",
                "org.apache.myfaces.Menu",
                "org.apache.myfaces.renderkit.html.ext.HtmlMenuRenderer");

        addRenderer(facesContext, "javax.faces.SelectMany",
                "org.apache.myfaces.Menu",
                "org.apache.myfaces.renderkit.html.ext.HtmlMenuRenderer");

        addRenderer(facesContext, "javax.faces.SelectOne",
                "org.apache.myfaces.Listbox",
                "org.apache.myfaces.renderkit.html.ext.HtmlListboxRenderer");

        addRenderer(facesContext, "javax.faces.SelectMany",
                "org.apache.myfaces.Listbox",
                "org.apache.myfaces.renderkit.html.ext.HtmlListboxRenderer");

        addRenderer(facesContext, "javax.faces.SelectOne",
                "org.apache.myfaces.Radio",
                "org.apache.myfaces.renderkit.html.ext.HtmlRadioRenderer");

        //  custom component renderers

        addRenderer(facesContext, "javax.faces.Data",
                "org.apache.myfaces.Buffer",
                "org.apache.myfaces.custom.buffer.BufferRenderer");

        addRenderer(facesContext, "javax.faces.Data",
                "org.apache.myfaces.Document",
                "org.apache.myfaces.custom.document.DocumentRenderer");

        addRenderer(facesContext, "javax.faces.Data",
                "org.apache.myfaces.DocumentHead",
                "org.apache.myfaces.custom.document.DocumentHeadRenderer");

        addRenderer(facesContext, "javax.faces.Data",
                "org.apache.myfaces.DocumentBody",
                "org.apache.myfaces.custom.document.DocumentBodyRenderer");

        addRenderer(facesContext, "org.apache.myfaces.Checkbox",
                "org.apache.myfaces.Checkbox",
                "org.apache.myfaces.renderkit.html.ext.HtmlCheckboxRenderer");

        addRenderer(facesContext, "javax.faces.Panel",
                "org.apache.myfaces.DataScroller",
                "org.apache.myfaces.custom.datascroller.HtmlDataScrollerRenderer");

        addRenderer(facesContext, "javax.faces.Input",
                "org.apache.myfaces.Calendar",
                "org.apache.myfaces.custom.calendar.HtmlCalendarRenderer");

        addRenderer(facesContext, "javax.faces.Input",
                "org.apache.myfaces.Date",
                "org.apache.myfaces.custom.date.HtmlDateRenderer");

        addRenderer(facesContext, "javax.faces.Input",
                "org.apache.myfaces.FileUpload",
                "org.apache.myfaces.custom.fileupload.HtmlFileUploadRenderer");

        addRenderer(facesContext, "javax.faces.Input",
                "org.apache.myfaces.InputHtml",
                "org.apache.myfaces.custom.inputHtml.InputHtmlRenderer");

        addRenderer(facesContext, "javax.faces.Panel",
                "org.apache.myfaces.Popup",
                "org.apache.myfaces.custom.popup.HtmlPopupRenderer");

        addRenderer(facesContext, "javax.faces.Panel",
                "org.apache.myfaces.Layout",
                "org.apache.myfaces.custom.layout.HtmlLayoutRenderer");

        addRenderer(facesContext, "javax.faces.Panel",
                "org.apache.myfaces.Navigation",
                "org.apache.myfaces.custom.navigation.HtmlNavigationRenderer");

        addRenderer(facesContext, "javax.faces.Panel",
                "org.apache.myfaces.NavigationMenu",
                "org.apache.myfaces.custom.navmenu.htmlnavmenu.HtmlNavigationMenuRenderer");

        addRenderer(facesContext, "javax.faces.Command",
                "org.apache.myfaces.Navigation",
                "org.apache.myfaces.custom.navigation.HtmlNavigationRenderer");

        addRenderer(facesContext, "javax.faces.Command",
                "org.apache.myfaces.NavigationMenu",
                "org.apache.myfaces.custom.navmenu.htmlnavmenu.HtmlNavigationMenuRenderer");

        addRenderer(facesContext, "javax.faces.Command",
                "org.apache.myfaces.JSCookMenu",
                "org.apache.myfaces.custom.navmenu.jscookmenu.HtmlJSCookMenuRenderer");

        addRenderer(facesContext, "javax.faces.Command",
                "org.apache.myfaces.SortHeader",
                "org.apache.myfaces.custom.sortheader.HtmlSortHeaderRenderer");

        addRenderer(facesContext, "javax.faces.Panel",
                "org.apache.myfaces.TabbedPane",
                "org.apache.myfaces.custom.tabbedpane.HtmlTabbedPaneRenderer");

        addRenderer(facesContext, "javax.faces.Panel",
                "org.apache.myfaces.CollapsiblePanel",
                "org.apache.myfaces.custom.collapsiblepanel.HtmlCollapsiblePanelRenderer");

        addRenderer(facesContext, "javax.faces.Data",
                "org.apache.myfaces.List",
                "org.apache.myfaces.custom.datalist.HtmlListRenderer");

        addRenderer(facesContext, "javax.faces.Data",
                "org.apache.myfaces.HtmlNewspaperTable",
                "org.apache.myfaces.custom.newspaper.HtmlNewspaperTableRenderer");

        addRenderer(facesContext, "javax.faces.Output",
                "org.apache.myfaces.JsValueSet",
                "org.apache.myfaces.custom.jsvalueset.HtmlJsValueSetRenderer");

        addRenderer(facesContext, "javax.faces.Output",
                "org.apache.myfaces.JsValueChangeListener",
                "org.apache.myfaces.custom.jslistener.JsValueChangeListenerRenderer");

        addRenderer(facesContext, "org.apache.myfaces.Radio",
                "org.apache.myfaces.Radio",
                "org.apache.myfaces.renderkit.html.ext.HtmlRadioRenderer");

        addRenderer(facesContext, "javax.faces.SelectOne",
                "org.apache.myfaces.SelectOneCountryRenderer",
                "org.apache.myfaces.custom.selectOneCountry.SelectOneCountryRenderer");

        addRenderer(facesContext, "javax.faces.SelectOne",
                "org.apache.myfaces.SelectOneLanguageRenderer",
                "org.apache.myfaces.custom.selectOneLanguage.SelectOneLanguageRenderer");

        addRenderer(facesContext, "javax.faces.Output",
                "org.apache.myfaces.Stylesheet",
                "org.apache.myfaces.custom.stylesheet.StylesheetRenderer");

        addRenderer(facesContext, "javax.faces.Output",
                "org.apache.myfaces.HtmlTagRenderer",
                "org.apache.myfaces.custom.htmlTag.HtmlTagRenderer");

        addRenderer(facesContext, "javax.faces.Output",
                "org.apache.myfaces.DojoInitializerRenderer",
                "org.apache.myfaces.custom.dojo.DojoInitializerRenderer");

        addRenderer(facesContext, "org.apache.myfaces.HtmlTree2",
                "org.apache.myfaces.HtmlTree2",
                "org.apache.myfaces.custom.tree2.HtmlTreeRenderer");

        addRenderer(facesContext, "org.apache.myfaces.HtmlTree",
                "org.apache.myfaces.HtmlTree",
                "org.apache.myfaces.custom.tree.renderkit.html.HtmlTreeRenderer");

        addRenderer(
                facesContext,
                "org.apache.myfaces.HtmlTree",
                "org.apache.myfaces.HtmlTreeImageCommandLink",
                "org.apache.myfaces.custom.tree.renderkit.html.HtmlTreeImageCommandLinkRenderer");

        addRenderer(facesContext, "org.apache.myfaces.HtmlTreeCheckbox",
                "org.apache.myfaces.HtmlTreeCheckbox",
                "org.apache.myfaces.custom.tree.renderkit.html.HtmlTreeCheckboxRenderer");

        addRenderer(facesContext, "javax.faces.Command",
                "org.apache.myfaces.HtmlTreeNode",
                "org.apache.myfaces.custom.tree.renderkit.html.HtmlTreeNodeRenderer");

        addRenderer(facesContext, "javax.faces.Panel",
                "org.apache.myfaces.PanelStack",
                "org.apache.myfaces.custom.panelstack.HtmlPanelStackRenderer");

        addRenderer(facesContext, "javax.faces.Graphic",
                "org.apache.myfaces.SwapImage",
                "org.apache.myfaces.custom.swapimage.HtmlSwapImageRenderer");

        addRenderer(facesContext, "javax.faces.Panel",
                "org.apache.myfaces.Schedule",
                "org.apache.myfaces.custom.schedule.ScheduleDelegatingRenderer");

        addRenderer(facesContext, "javax.faces.Data",
                "org.apache.myfaces.Table",
                "org.apache.myfaces.renderkit.html.ext.HtmlTableRenderer");
    }

    /**
     * Add a renderer to the FacesContext.
     * 
     * @param facesContext Faces Context
     * @param family Componenet Family
     * @param rendererType Component Type
     * @param renderClassName Class Name of Renderer
     */
    public static void addRenderer(FacesContext facesContext, String family,
            String rendererType, String renderClassName)
    {
        Renderer renderer = (javax.faces.render.Renderer) newInstance(renderClassName);
        RenderKit kit = facesContext.getRenderKit();
        kit.addRenderer(family, rendererType, renderer);
    }

    /**
     * Tries a Class.loadClass with the context class loader of the current thread first and
     * automatically falls back to the ClassUtils class loader (i.e. the loader of the
     * myfaces.jar lib) if necessary.
     * 
     * Note: This was copied from org.apache.myfaces.shared.util.ClassUtils
     *
     * @param type fully qualified name of a non-primitive non-array class
     * @return the corresponding Class
     * @throws NullPointerException if type is null
     * @throws ClassNotFoundException
     */
    private static Class classForName(String type)
            throws ClassNotFoundException
    {
        if (type == null)
            throw new NullPointerException("type");
        try
        {
            // Try WebApp ClassLoader first
            return Class.forName(type, false, // do not initialize for faster startup
                    Thread.currentThread().getContextClassLoader());
        }
        catch (ClassNotFoundException ignore)
        {
            // fallback: Try ClassLoader for ClassUtils (i.e. the myfaces.jar lib)
            return Class.forName(type, false, // do not initialize for faster startup
                    TestUtils.class.getClassLoader());
        }
    }

    /**
     * Same as {@link #classForName(String)}, but throws a RuntimeException
     * (FacesException) instead of a ClassNotFoundException.
     *
     * Note: This was copied from org.apache.myfaces.shared.util.ClassUtils
     *
     * @return the corresponding Class
     * @throws NullPointerException if type is null
     * @throws FacesException if class not found
     */
    private static Class simpleClassForName(String type)
    {
        try
        {
            return classForName(type);
        }
        catch (ClassNotFoundException e)
        {
            log.error("Class " + type + " not found", e);
            throw new FacesException(e);
        }
    }

    /**
     * Create an instance of the class with the type of <code>type</code>.
     * 
     * Note: This was copied from org.apache.myfaces.shared.util.ClassUtils
     *
     * @param type Type of new class.
     * @return Instance of the class <code>type</code>
     * @throws FacesException
     */
    private static Object newInstance(String type) throws FacesException
    {
        if (type == null)
            return null;
        return newInstance(simpleClassForName(type));
    }

    /**
     * Create an instance of the class <code>clazz</code>.
     * 
     * Note: This was copied from org.apache.myfaces.shared.util.ClassUtils
     *
     * @param clazz Class to create an instance of.
     * @return Instance of the class <code>clazz</code>
     * @throws FacesException
     */
    private static Object newInstance(Class clazz) throws FacesException
    {
        try
        {
            return clazz.newInstance();
        }
        catch (NoClassDefFoundError e)
        {
            log.error("Class : " + clazz.getName() + " not found.", e);
            throw new FacesException(e);
        }
        catch (InstantiationException e)
        {
            log.error(e.getMessage(), e);
            throw new FacesException(e);
        }
        catch (IllegalAccessException e)
        {
            log.error(e.getMessage(), e);
            throw new FacesException(e);
        }
    }

    /**
     * Renderered a component, including it's children, then complete the reponse.
     * 
     * @param context Faces Context
     * @param component Component to be rendered.
     * @throws IOException Thrown while rendering.
     */
    public static void renderComponent(FacesContext context,
            UIComponent component) throws IOException
    {
        Renderer renderer = context.getRenderKit().getRenderer(
                component.getFamily(), component.getRendererType());
        renderer.encodeBegin(context, component);
        renderer.encodeChildren(context, component);
        renderer.encodeEnd(context, component);
        context.responseComplete();
        context.renderResponse();
    }
}
