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
 * --
 * $Id$
 */
package org.apache.myfaces.custom.roundeddiv;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.html.util.ParameterResourceHandler;
import org.apache.myfaces.custom.htmlTag.HtmlTagRenderer;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.renderkit.html.util.ResourceLoader;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;

/**
 * Renderer for the {@link HtmlRoundedDiv} component.
 *
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Output"
 *   type = "org.apache.myfaces.HtmlRoundedDiv"
 * 
 * @author Andrew Robinson (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlRoundedDivRenderer extends HtmlTagRenderer implements
        ResourceLoader
{
    private final static Log log = LogFactory
            .getLog(HtmlRoundedDivRenderer.class);
    private static Map imageCache = Collections.synchronizedMap(new HashMap());
    private static List cacheQueue = Collections
            .synchronizedList(new ArrayList());
    private static Integer cacheSize;

    private final static int DEFAULT_CACHE_SIZE = 20;
    private final static String CACHE_SIZE_KEY = "org.apache.myfaces.ROUNDED_DIV_CACHE_SIZE";
    public final static String RENDERER_TYPE = "org.apache.myfaces.HtmlRoundedDiv";

    /**
     * @see org.apache.myfaces.renderkit.html.util.ResourceLoader#serveResource(
     * javax.servlet.ServletContext, javax.servlet.http.HttpServletRequest, 
     * javax.servlet.http.HttpServletResponse, java.lang.String)
     */
    public void serveResource(ServletContext context,
            HttpServletRequest request, HttpServletResponse response,
            String resourceUri) throws IOException
    {
        initCache(context);
        boolean useCache = cacheSize.intValue() > 0;

        String cacheKey = null;
        RoundedBorderGenerator borderGen = null;
        if (useCache)
        {
            cacheKey = getCacheKey(request.getQueryString());
            borderGen = (RoundedBorderGenerator) imageCache.get(cacheKey);
        }

        if (borderGen == null)
        {
            if (useCache)
            {
                borderGen = initCachedGenerator(request, cacheKey);
            }
            else
            {
                borderGen = buildGenerator(request);
            }
        }

        String area = request.getParameter("a");
        BufferedImage img;
        if (request.getParameter("s") != null)
        {
            img = borderGen.createImage();
        }
        else
        {
            img = borderGen.createImageSection(getAreaAsSection(area));
        }

        if (cacheSize.intValue() == 0)
        {
            borderGen.dispose();
        }

        response.setContentType("image/png");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", baos);
        byte[] data = baos.toByteArray();
        baos = null;

        response.setContentLength(data.length);
        OutputStream out = response.getOutputStream();
        try
        {
            out.write(data);
        }
        finally
        {
            out.close();
        }
    }

    /**
     * @param request
     * @param cacheKey
     * @return
     */
    protected RoundedBorderGenerator initCachedGenerator(
            HttpServletRequest request, String cacheKey)
    {
        RoundedBorderGenerator borderGen;

        synchronized (imageCache)
        {
            borderGen = buildGenerator(request);

            imageCache.put(cacheKey, borderGen);
            cacheQueue.add(cacheKey);

            while (cacheQueue.size() > cacheSize.intValue())
            {
                RoundedBorderGenerator gen = (RoundedBorderGenerator) imageCache
                        .remove(cacheQueue.remove(0));
                gen.dispose();
            }
        }
        return borderGen;
    }

    protected RoundedBorderGenerator buildGenerator(HttpServletRequest request)
    {
        RoundedBorderGenerator borderGen;
        String param;
        Color foregroundColor = Color.decode("#" + request.getParameter("c"));

        Dimension size = null;
        if ((param = request.getParameter("s")) != null)
        {
            int i = param.indexOf('x');
            size = new Dimension(Integer.parseInt(param.substring(0, i)),
                    Integer.parseInt(param.substring(i + 1)));
        }

        Color backgroundColor = null, borderColor = null;
        if ((param = request.getParameter("bgc")) != null)
        {
            backgroundColor = Color.decode("#" + param);
        }
        if ((param = request.getParameter("bc")) != null)
        {
            borderColor = Color.decode("#" + param);
        }

        int borderWidth = Integer.parseInt(request.getParameter("bw"));
        int radius = Integer.parseInt(request.getParameter("r"));

        boolean inverse = "t".equals(request.getParameter("i"));

        borderGen = new RoundedBorderGenerator(borderColor, borderWidth,
                radius, foregroundColor, backgroundColor, size, inverse);
        return borderGen;
    }

    /**
     * @see org.apache.myfaces.custom.htmlTag.HtmlTagRenderer#encodeBegin(
     * javax.faces.context.FacesContext, javax.faces.component.UIComponent)
     */
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException
    {
        super.encodeBegin(context, component);
        if (!component.isRendered())
            return;

        boolean ie = isIE(context);

        HtmlRoundedDiv div = (HtmlRoundedDiv) component;
        ResponseWriter writer = context.getResponseWriter();
        Set areas = null;

        if (div.getSize() == null)
        {
            areas = getAreasToRender(context, div);
        }

        if ("table".equals(div.getValue()))
        {
            encodeTable(context, ie, div, writer, areas);
        }
        else
        {
            encodeDivBegin(context, ie, div, writer, areas);
        }
    }

    protected void encodeDivBegin(FacesContext context, boolean ie,
            HtmlRoundedDiv div, ResponseWriter writer, Set areas)
            throws IOException
    {
        if (div.getSize() != null)
        {
            addImage(writer, context, div, null, null, ie);
        }
        else
        {
            for (Iterator iter = areas.iterator(); iter.hasNext();)
            {
                String area = (String) iter.next();
                addImage(writer, context, div, area, areas, ie);
            }
        }

        // create a DIV for the contents
        writer.startElement(HTML.DIV_ELEM, div);

        StringBuffer style = new StringBuffer("position: relative; z-index: 1;");
        addPadding(Math.max(div.getBorderWidth().intValue(), div.getRadius()
                .intValue()), style, areas);

        String val;
        if ((val = div.getContentStyle()) != null)
        {
            style.append(val);
        }

        writer.writeAttribute(HTML.STYLE_ATTR, style, null);
        if ((val = div.getContentStyleClass()) != null)
        {
            writer.writeAttribute(HTML.CLASS_ATTR, val, null);
        }
    }

    protected void encodeTable(FacesContext context, boolean ie,
            HtmlRoundedDiv div, ResponseWriter writer, Set areas)
            throws IOException
    {
        int padding = Math.max(div.getBorderWidth().intValue(), div.getRadius()
                .intValue());

        writer.writeAttribute(HTML.CELLPADDING_ATTR, "0", null);
        writer.writeAttribute(HTML.CELLSPACING_ATTR, "0", null);

        writer.startElement(HTML.TBODY_ELEM, div);

        writer.startElement(HTML.TR_ELEM, div);
        encodeImageColumn(writer, context, div, "topleft", areas, padding, ie);
        encodeImageColumn(writer, context, div, "top", areas, padding, ie);
        encodeImageColumn(writer, context, div, "topright", areas, padding, ie);
        writer.endElement(HTML.TR_ELEM);
        writer.startElement(HTML.TR_ELEM, div);
        encodeImageColumn(writer, context, div, "left", areas, padding, ie);
        encodeContentColumn(writer, context, div, areas, ie);
        encodeImageColumn(writer, context, div, "right", areas, padding, ie);
        writer.endElement(HTML.TR_ELEM);
        writer.startElement(HTML.TR_ELEM, div);
        encodeImageColumn(writer, context, div, "bottomleft", areas, padding,
                ie);
        encodeImageColumn(writer, context, div, "bottom", areas, padding, ie);
        encodeImageColumn(writer, context, div, "bottomright", areas, padding,
                ie);
        writer.endElement(HTML.TR_ELEM);

        writer.endElement(HTML.TBODY_ELEM);
    }

    /**
     * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
     */
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException
    {
        if (!component.isRendered())
        {
            return;
        }
        HtmlRoundedDiv div = (HtmlRoundedDiv) component;
        if ("div".equals(div.getValue()))
        {
            RendererUtils.renderChildren(context, component);
        }
    }

    /**
     * @see org.apache.myfaces.custom.htmlTag.HtmlTagRenderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
     */
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException
    {
        super.encodeEnd(context, component);
        if (!component.isRendered())
        {
            return;
        }
        HtmlRoundedDiv div = (HtmlRoundedDiv) component;
        if ("div".equals(div.getValue()))
        {
            context.getResponseWriter().endElement(HTML.DIV_ELEM);
        }
    }

    /**
     * @see javax.faces.render.Renderer#getRendersChildren()
     */
    public boolean getRendersChildren()
    {
        return true;
    }

    /**
     * Convert the string area to one of the integer constants from
     * {@link RoundedBorderGenerator}
     * 
     * @param area the area as a string
     * @return integer constant value
     */
    protected int getAreaAsSection(String area)
    {
        if ("top".equals(area))
        {
            return RoundedBorderGenerator.SECTION_TOP;
        }
        else if ("topright".equals(area))
        {
            return RoundedBorderGenerator.SECTION_TOPRIGHT;
        }
        else if ("right".equals(area))
        {
            return RoundedBorderGenerator.SECTION_RIGHT;
        }
        else if ("bottomright".equals(area))
        {
            return RoundedBorderGenerator.SECTION_BOTTOMRIGHT;
        }
        else if ("bottom".equals(area))
        {
            return RoundedBorderGenerator.SECTION_BOTTOM;
        }
        else if ("bottomleft".equals(area))
        {
            return RoundedBorderGenerator.SECTION_BOTTOMLEFT;
        }
        else if ("left".equals(area))
        {
            return RoundedBorderGenerator.SECTION_LEFT;
        }
        else if ("topleft".equals(area))
        {
            return RoundedBorderGenerator.SECTION_TOPLEFT;
        }
        else if ("center".equals(area))
        {
            return RoundedBorderGenerator.SECTION_CENTER;
        }
        else
        {
            throw new IllegalArgumentException("Invalid area: " + area);
        }
    }

    /**
     * Get the areas to render. This takes into account the corners
     * that the user has requested to be rendered.
     * 
     * @param context {@link FacesContext}
     * @param component The component
     * @return A set of strings containing the areas to render
     */
    protected Set getAreasToRender(FacesContext context,
            HtmlRoundedDiv component)
    {
        String corners = component.getCorners();
        Set areas = new HashSet();

        if (corners != null)
        {
            String[] cornersArr = corners.split("\\s*,\\s*");
            for (int i = 0; i < cornersArr.length; i++)
            {
                if ("topleft".equals(cornersArr[i]))
                {
                    areas.add("top");
                    areas.add("left");
                    areas.add("topleft");
                }
                else if ("topright".equals(cornersArr[i]))
                {
                    areas.add("top");
                    areas.add("right");
                    areas.add("topright");
                }
                else if ("bottomright".equals(cornersArr[i]))
                {
                    areas.add("right");
                    areas.add("bottom");
                    areas.add("bottomright");
                }
                else if ("bottomleft".equals(cornersArr[i]))
                {
                    areas.add("bottom");
                    areas.add("bottomleft");
                    areas.add("left");
                }
                else
                {
                    throw new IllegalArgumentException("Invalid corner: "
                            + cornersArr[i]);
                }
            }
            areas.add("center");
        }
        else
        {
            areas.add("top");
            areas.add("topright");
            areas.add("right");
            areas.add("bottomright");
            areas.add("bottom");
            areas.add("bottomleft");
            areas.add("left");
            areas.add("topleft");
            areas.add("center");
        }
        return areas;
    }

    /**
     * Adds an IMG tag to the response for an image
     *  
     * @param writer The response writer
     * @param context The faces context
     * @param component The component
     * @param area The area of the image
     * @param areas All the areas to be rendered
     * @param ie If the browser is IE
     * @throws IOException when writing to the writer
     */
    protected void addImage(ResponseWriter writer, FacesContext context,
            HtmlRoundedDiv component, String area, Set areas, boolean ie)
            throws IOException
    {
        StringBuffer style = new StringBuffer(
                "position: absolute;padding: 0px;margin: 0px;");
        String url = getImageSource(context, component, area);
        int padding = Math.max(component.getBorderWidth().intValue(), component
                .getRadius().intValue());

        if (ie)
        {
            // NOT CURRENTLY WORKING:
            //            // IE transparency fix
            //            if (component.getBackgroundColor() == null
            //                    && ("topright".equals(area) || "topleft".equals(area)
            //                            || "bottomright".equals(area) || "bottomleft"
            //                            .equals(area)) && isIE6(context))
            //            {
            //                setIESrcForCorner(context, component, url, style, padding);
            //                url = AddResourceFactory.getInstance(context).getResourceUri(
            //                        context, HtmlRoundedDiv.class, "blank.gif");
            //            }

            if (areas != null)
            {
                setIECssPosition(padding, style, area, areas);
            }
        }
        else if (areas != null)
        {
            setCssPosition(padding, style, area, areas);
        }

        writer.startElement(HTML.IMG_ELEM, component);
        writer.writeAttribute(HTML.SRC_ATTR, url, null);
        writer.writeAttribute(HTML.STYLE_ATTR, style, null);
        writer.endElement(HTML.IMG_ELEM);
    }

    /**
     * Set the CSS positioning attributes for IE6
     * 
     * @param padding The size of the image section
     * @param style The current style
     * @param area The area
     * @param areas All the areas to be rendered
     */
    protected void setIECssPosition(int padding, StringBuffer style,
            String area, Set areas)
    {
        if ("center".equals(area))
        {
            int p = 0;
            if (areas.contains("left"))
            {
                p += padding;
            }
            if (areas.contains("right"))
            {
                p += padding;
            }
            style.append("width: expression((offsetParent.clientWidth - ")
                    .append(p).append(") + 'px');");

            p = 0;
            if (areas.contains("top"))
            {
                p += padding;
            }
            if (areas.contains("bottom"))
            {
                p += padding;
            }
            style.append("height: expression((offsetParent.clientHeight - ")
                    .append(p).append(") + 'px');");

            style.append("top: ").append(areas.contains("top") ? padding : 0)
                    .append("px;");
            style.append("left: ").append(areas.contains("left") ? padding : 0)
                    .append("px;");
        }
        else if ("top".equals(area))
        {
            style.append("top: 0px;");
            style.append("left: ").append(areas.contains("left") ? padding : 0)
                    .append("px;");
            style.append("height: ").append(padding).append("px;");

            int p = 0;
            if (areas.contains("left"))
            {
                p += padding;
            }
            if (areas.contains("right"))
            {
                p += padding;
            }
            style.append("width: expression((offsetParent.clientWidth - ")
                    .append(p).append(") + 'px');");
        }
        else if ("topright".equals(area))
        {
            style.append("top: 0px;");
            style.append("right: 0px;");
        }
        else if ("right".equals(area))
        {
            style.append("top: ").append(areas.contains("top") ? padding : 0)
                    .append("px;");
            style.append("right: 0px;");
            style.append("width: ").append(padding).append("px;");

            int p = 0;
            if (areas.contains("top"))
            {
                p += padding;
            }
            if (areas.contains("bottom"))
            {
                p += padding;
            }
            style.append("height: expression((offsetParent.clientHeight - ")
                    .append(p).append(") + 'px');");
        }
        else if ("bottomright".equals(area))
        {
            style.append("right: 0px;");
            style.append("bottom: 0px;");
        }
        else if ("bottom".equals(area))
        {
            style.append("bottom: 0px;");
            style.append("left: ").append(areas.contains("left") ? padding : 0)
                    .append("px;");
            style.append("height: ").append(padding).append("px;");

            int p = 0;
            if (areas.contains("left"))
            {
                p += padding;
            }
            if (areas.contains("right"))
            {
                p += padding;
            }
            style.append("width: expression((offsetParent.clientWidth - ")
                    .append(p).append(") + 'px');");
        }
        else if ("bottomleft".equals(area))
        {
            style.append("bottom: 0px;");
            style.append("left: 0px;");
        }
        else if ("left".equals(area))
        {
            style.append("top: ").append(areas.contains("top") ? padding : 0)
                    .append("px;");
            style.append("left: 0px;");
            style.append("width: ").append(padding).append("px;");

            int p = 0;
            if (areas.contains("top"))
            {
                p += padding;
            }
            if (areas.contains("bottom"))
            {
                p += padding;
            }
            style.append("height: expression((offsetParent.clientHeight - ")
                    .append(p).append(") + 'px');");
        }
        else
        //if ("topleft".equals(area))
        {
            style.append("top: 0px;");
            style.append("left: 0px;");
        }
    }

    protected void encodeContentColumn(ResponseWriter writer,
            FacesContext context, HtmlRoundedDiv component, Set areas,
            boolean ie) throws IOException
    {
        writer.startElement(HTML.TD_ELEM, component);
        String val;
        StringBuffer style = new StringBuffer();

        if ((val = component.getContentStyle()) != null)
        {
            style.append(val).append(';');
        }
        style.append("background-image: url('").append(
                getImageSource(context, component, "center")).append("');");
        writer.writeAttribute(HTML.STYLE_ATTR, style, null);

        if ((val = component.getContentStyleClass()) != null)
        {
            writer.writeAttribute(HTML.CLASS_ATTR, val, null);
        }

        RendererUtils.renderChildren(context, component);

        writer.endElement(HTML.TD_ELEM);
    }

    protected void encodeImageColumn(ResponseWriter writer,
            FacesContext context, HtmlRoundedDiv component, String area,
            Set areas, int padding, boolean ie) throws IOException
    {
        writer.startElement(HTML.TD_ELEM, component);
        writer.writeAttribute(HTML.CLASS_ATTR, area, null);
        if (areas.contains(area))
        {
            StringBuffer style = new StringBuffer("font-size: 1px; background-image: url('")
                .append(getImageSource(context,
                    component, area)).append("');")
                .append("background-position: top left;");
            
            if ("left".equals(area) || "right".equals(area))
            {
                style.append("background-repeat: repeat-y; width: ")
                    .append(padding).append("px;");
            }
            else if ("top".equals(area) || "bottom".equals(area))
            {
                style.append("background-repeat: repeat-x; height: ")
                    .append(padding).append("px;");
            }
            
            writer.writeAttribute(HTML.STYLE_ATTR, style, null);

            // force the browser to allocate this column in rendering
            writer.write("&#160;");
        }
        writer.endElement(HTML.TD_ELEM);
    }

    // NOT CURRENTLY WORKING:
    //    /**
    //     * Set the CSS style for IE6 to work-around the IE6 PNG alpha-transparency
    //     * bug
    //     * 
    //     * @param context The faces context
    //     * @param component The component
    //     * @param url The URL of the image
    //     * @param style The current style
    //     */
    //    protected void setIESrcForCorner(FacesContext context,
    //            HtmlRoundedDiv component, String url, StringBuffer style,
    //            int imageSize)
    //    {
    //        style
    //                .append(
    //                        " filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='")
    //                .append(url).append("', sizingMethod='image', enabled=true);").append(
    //                        "height: ").append(imageSize).append("px; width: ")
    //                .append(imageSize).append("px;");
    //    }

    /**
     * Set the CSS position using CSS2 attributes
     * 
     * @param padding The size of the image section
     * @param style The current style
     * @param area The area
     * @param areas All the areas to be rendered
     */
    protected void setCssPosition(int padding, StringBuffer style, String area,
            Set areas)
    {
        if ("center".equals(area))
        {
            style.append("top: ").append(areas.contains("top") ? padding : 0)
                    .append("px;");
            style.append("right: ").append(
                    areas.contains("right") ? padding : 0).append("px;");
            style.append("bottom: ").append(
                    areas.contains("bottom") ? padding : 0).append("px;");
            style.append("left: ").append(areas.contains("left") ? padding : 0)
                    .append("px;");
        }
        else if ("top".equals(area))
        {
            style.append("top: 0px;");
            style.append("right: ").append(
                    areas.contains("right") ? padding : 0).append("px;");
            style.append("left: ").append(areas.contains("left") ? padding : 0)
                    .append("px;");
            style.append("height: ").append(padding).append("px;");
        }
        else if ("topright".equals(area))
        {
            style.append("top: 0px;");
            style.append("right: 0px;");
        }
        else if ("right".equals(area))
        {
            style.append("top: ").append(areas.contains("top") ? padding : 0)
                    .append("px;");
            style.append("right: 0px;");
            style.append("bottom: ").append(
                    areas.contains("bottom") ? padding : 0).append("px;");
            style.append("width: ").append(padding).append("px;");
        }
        else if ("bottomright".equals(area))
        {
            style.append("right: 0px;");
            style.append("bottom: 0px;");
        }
        else if ("bottom".equals(area))
        {
            style.append("right: ").append(
                    areas.contains("right") ? padding : 0).append("px;");
            style.append("bottom: 0px;");
            style.append("left: ").append(areas.contains("left") ? padding : 0)
                    .append("px;");
            style.append("height: ").append(padding).append("px;");
        }
        else if ("bottomleft".equals(area))
        {
            style.append("bottom: 0px;");
            style.append("left: 0px;");
        }
        else if ("left".equals(area))
        {
            style.append("top: ").append(areas.contains("top") ? padding : 0)
                    .append("px;");
            style.append("bottom: ").append(
                    areas.contains("bottom") ? padding : 0).append("px;");
            style.append("left: 0px;");
            style.append("width: ").append(padding).append("px;");
        }
        else
        //if ("topleft".equals(area))
        {
            style.append("top: 0px;");
            style.append("left: 0px;");
        }
    }

    /**
     * Get the URL to use to let this class work with the MyFaces resource handling
     * in order to generate the image
     * 
     * @param context The faces context
     * @param component The component
     * @param area The area
     * @return The URL of the image
     */
    protected String getImageSource(FacesContext context,
            HtmlRoundedDiv component, String area)
    {
        AddResource addResource = AddResourceFactory.getInstance(context);
        return addResource.getResourceUri(context,
                new ParameterResourceHandler(this.getClass(),
                        buildParameterMap(component, area)));
    }

    /**
     * Adds padding to the inner DIV so that the contents do not overlap
     * the rounded corner
     * 
     * @param padding The max of the border width and radius
     * @param style The style
     * @param areas The areas to render
     */
    protected void addPadding(int padding, StringBuffer style, Set areas)
    {
        int top = 0, right = 0, bottom = 0, left = 0;
        if (areas == null || areas.contains("top"))
        {
            top = padding;
        }
        if (areas == null || areas.contains("right"))
        {
            right = padding;
        }
        if (areas == null || areas.contains("bottom"))
        {
            bottom = padding;
        }
        if (areas == null || areas.contains("left"))
        {
            left = padding;
        }

        style.append(" padding: ").append(top).append("px ").append(right)
                .append("px ").append(bottom).append("px ").append(left)
                .append("px;");
    }

    protected Dimension _getSize(HtmlRoundedDiv component){
        String size = component.getSize();
        
        Matcher m = Pattern.compile("(\\d+)\\D+(\\d+)").matcher(size);
        if (!m.find())
        {
            throw new IllegalArgumentException("Invalid dimension");
        }
        return new Dimension(Integer.parseInt(m.group(1)), Integer
                .parseInt(m.group(2)));       
    }
    
    /**
     * Build a set of parameters as a map that are needed for the rendering
     * 
     * @param component The component
     * @param area The area
     * @return Map of parameters
     */
    protected Map buildParameterMap(HtmlRoundedDiv component, String area)
    {
        Map map = new HashMap(7);
        if (component.getColor() != null){
            map.put("c", colorToHtml(Color.decode(component.getColor())));
        }
        Color c;
        if (component.getBackgroundColor() != null)
        {
            c = Color.decode(component.getBackgroundColor());
            map.put("bgc", colorToHtml(c));
        }
        if ( component.getBorderColor() != null)
        {
            c = Color.decode(component.getBorderColor());
            map.put("bc", colorToHtml(c));
        }

        map.put("bw", component.getBorderWidth().toString());
        map.put("r", component.getRadius().toString());
        Dimension d;
        if ( component.getSize() != null)
        {
            d = _getSize(component);
            map.put("s", d.width + "x"
                    + d.height);
        }
        if (area != null)
        {
            map.put("a", area);
        }
        map.put("i", component.getInverse().booleanValue() ? "t" : "f");
        return map;
    }

    /**
     * Convert a color to an HTML style color (i.e. FFFFFF)
     * 
     * @param c The color
     * @return Color as hexidemal representation
     */
    protected String colorToHtml(Color c)
    {
        int rbg = c.getRGB();
        String[] strs = { Integer.toHexString((rbg >> 16) & 0xFF),
                Integer.toHexString((rbg >> 8) & 0xFF),
                Integer.toHexString(rbg & 0xFF) };

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strs.length; i++)
        {
            if (strs[i].length() == 1)
            {
                sb.append('0');
            }
            sb.append(strs[i]);
        }
        return sb.toString();
    }

    /**
     * Check if the user is using IE
     * 
     * @param context Faces context
     * @return True if IE
     */
    protected boolean isIE(FacesContext context)
    {
        try
        {
            String userAgent = context.getExternalContext()
                    .getRequestHeaderMap().get("User-Agent").toString();

            return userAgent.toUpperCase().indexOf("MSIE") >= 0;
        }
        catch (NullPointerException ex)
        {
            // should never happen, but just in case
            return false;
        }
    }

    private void initCache(ServletContext context)
    {
        if (cacheSize != null)
        {
            return;
        }
        synchronized (imageCache)
        {
            if (cacheSize != null)
            {
                return;
            }

            String param = context.getInitParameter(CACHE_SIZE_KEY);
            if (cacheSize == null)
            {
                cacheSize = new Integer(DEFAULT_CACHE_SIZE);
            }
            else
            {
                try
                {
                    cacheSize = new Integer(Integer.parseInt(param));
                }
                catch (NumberFormatException ex)
                {
                    log.error("Unabled to parse cache size, using default", ex);
                    cacheSize = new Integer(DEFAULT_CACHE_SIZE);
                }
            }
            log.debug("Using a cache of size: " + cacheSize);
        }
    }

    protected String getCacheKey(String queryString)
    {
        int from = queryString.indexOf("&a=") + 1;
        
        if (from == -1)
        {
            return queryString;
        }
        
        int to = queryString.indexOf('&', from + 1);
        
        return to == -1 ? queryString.substring(0, from) : queryString.substring(
                0, from) + queryString.substring(to);
    }

    //    /**
    //     * Check if the user is using IE6
    //     * 
    //     * @param context Faces context
    //     * @return True if IE
    //     */
    //    protected boolean isIE6(FacesContext context)
    //    {
    //        try
    //        {
    //            String userAgent = context.getExternalContext()
    //                    .getRequestHeaderMap().get("User-Agent").toString();
    //
    //            return userAgent.toUpperCase().indexOf("MSIE 6") >= 0;
    //        }
    //        catch (NullPointerException ex)
    //        {
    //            // should never happen, but just in case
    //            return false;
    //        }
    //    }
}
