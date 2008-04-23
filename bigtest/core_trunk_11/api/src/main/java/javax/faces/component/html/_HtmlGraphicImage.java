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
package javax.faces.component.html;

import javax.faces.component.UIGraphic;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * Renders an HTML img element.
 * 
 * Unless otherwise specified, all attributes accept static values
 * or EL expressions.
 * 
 * see Javadoc of <a href="http://java.sun.com/j2ee/javaserverfaces/1.1_01/docs/api/index.html">JSF Specification</a>
 *
 * @JSFComponent
 *   name = "h:graphicImage"
 *   class = "javax.faces.component.html.HtmlGraphicImage"
 *   tagClass = "org.apache.myfaces.taglib.html.HtmlGraphicImageTag"
 *   desc = "h:graphicImage"
 *   
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
abstract class _HtmlGraphicImage extends UIGraphic implements _EventProperties,
    _StyleProperties, _UniversalProperties, _AltProperty
{

    public static final String COMPONENT_TYPE = "javax.faces.HtmlGraphicImage";
    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Image";

    /**
     * HTML: Overrides the natural height of this image, by specifying height in pixels.
     * 
     * @JSFProperty
     */
    public abstract String getHeight();

    /**
     * HTML: Specifies server-side image map handling for this image.
     * 
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract boolean isIsmap();
    
    /**
     * HTML: A link to a long description of the image.
     * 
     * @JSFProperty
     */
    public abstract String getLongdesc();
    
    /**
     * HTML: Specifies an image map to use with this image.
     * 
     * @JSFProperty
     */
    public abstract String getUsemap();
    
    /**
     * HTML: Overrides the natural width of this image, by specifying width in pixels.
     * 
     * @JSFProperty
     */
    public abstract String getWidth();
    
}
