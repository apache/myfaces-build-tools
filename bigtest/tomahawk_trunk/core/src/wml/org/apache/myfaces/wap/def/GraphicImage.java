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
package org.apache.myfaces.wap.def;

/**
 * Renders an WML "img" element. Render the value of the component as the value of the "src" attribute,
 * after passing it to the getResourceURL() method of the ViewHandler for this application, and passing the result
 * through the encodeResourceURL() method of theExternalContext.
 * Render the value of the alt attribute as the value of the "alt" attribute.
 * If the "styleClass" attribute is specified, render its value as the value of the "class" attribute.
 *
 * @wapfaces.tag
 *       componentFamily="UIGraphic"
 *       rendererType="ImageRenderer"
 *       tagName="graphicImage"
 *       tagBaseClass="org.apache.myfaces.wap.base.ComponentTagBase"
 *       bodyContent="empty"
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */


public class GraphicImage extends javax.faces.component.UIGraphic {

    /**
     * This attribute specifies an alternative textual representation for the image. This representation is used when the image can not be displayed using any other method (i.e., the user agent does not support images, or the image contents can not be found).
     *
     *@wapfaces.attribute
     *    valueBinding="true"
     *    required="true"
     */
    java.lang.String alt;

    /**
     * This attribute specifies the URI for the image. If the browser supports images, it downloads the image from the specified URI and renders it when the text is being displayed.
     *
     *@wapfaces.attribute
     *    valueBinding="true"
     *    required="true"
     */
    java.lang.String url;

    /**
     * This attribute specifies an alternative internal representation for the image. This representation is used if it exists; otherwise the image is downloaded from the URI specified in the src attribute, i.e., any localsrc parameter specified takes precedence over the image specified in the src parameter.
     *
     *@wapfaces.attribute
     *    valueBinding="true"
     */
    java.lang.String localsrc;

    /**
     * This attribute specify the amount of white space to be inserted to the above and below the image. The default value for this attribute is zero indicating that no white space should be inserted. If length is specified as a percentage value, the space inserted is based on the available horizontal or vertical space. This attribute is hints to the user agent and may be ignored.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String vspace;

    /**
     * This attribute specify the amount of white space to be inserted to the left and right the image. The default value for this attribute is zero indicating that no white space should be inserted. If length is specified as a percentage value, the space inserted is based on the available horizontal or vertical space. This attribute is hints to the user agent and may be ignored.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String hspace;

    /**
     * This attribute specifies image alignment within the text flow and with respect to the current insertion point. Align has three possible values: (top|middle|bottom)
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String align;

    /**
     * This attribute give user agents an idea of the size of an image or object so that they may reserve space for it and continue rendering the card while waiting for the image data. User agents may scale objects and images to match these values if appropriate. If length is specified as a percentage value, the resulting size is based on the available vertical space, not on the natural size of the image. This attribute is a hint to the user agent and may be ignored.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String height;

    /**
     * This attribute give user agents an idea of the size of an image or object so that they may reserve space for it and continue rendering the card while waiting for the image data. User agents may scale objects and images to match these values if appropriate. If length is specified as a percentage value, the resulting size is based on the available horizontal space, not on the natural size of the image. This attribute is a hint to the user agent and may be ignored.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String width;

    /**
     * The attribute styleClass affiliates an element with one or more classes. Multiple elements can be given the same styleClass name.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String styleClass;

    /**
     * The xml:lang attribute specifies the natural or formal language of an element or its attributes.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String xmllang;

    /**
     * The current value of this component.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     *     inherit="true"
     */
    java.lang.String value;

    // ============= ABSTARACT ATTRIBUTES ======================================
    /**
     * The component identifier for the associated component.
     *
     * @wapfaces.attribute
     *     abstract="true"
     *     inherit="true"
     */
    java.lang.String id;

    /**
     * Flag indicating whether or not this component should be rendered (during Render Response Phase), or processed on any subsequent form submit.
     *
     * @wapfaces.attribute
     *     abstract="true"
     *     inherit="true"
     */
    boolean rendered;

    /**
     * The value binding expression linking this component to a property in a backing bean.
     *
     * @wapfaces.attribute
     *     abstract="true"
     *     inherit="true"
     */
    java.lang.String binding;
}
