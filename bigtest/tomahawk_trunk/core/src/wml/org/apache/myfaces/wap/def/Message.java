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
 * Render a single message for a specific component.
 *
 * Set-up for Rendering
 * Obtain the "summary" and "detail" properties fromUIMessage component. If not present, keep the empty string as the value, respectively. Obtain the firstFacesMessage to render from the component, using the "for" property of the UIMessage. This will be the only message we render.
 *
 * Rendering
 * For the message renderer, we only render one row, for the first message. For the messages renderer, we render as many rows as we have messages.
 *
 * @wapfaces.tag
 *       componentFamily="UIMessage"
 *       rendererType="MessageRenderer"
 *       tagName="message"
 *       tagBaseClass="org.apache.myfaces.wap.base.MessageTagBase"
 *       bodyContent="JSP"
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */


public class Message extends javax.faces.component.UIMessage {

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

    /**
     * Client identifier of the component for which to display messages.
     *
     * @wapfaces.attribute
     *     required="true"
     *     abstract="true"
     *     inherit="true"
     *     replaceWith="for"
     */
    java.lang.String forComponent;

     /**
     * Flag indicating whether the summary portion of displayed messages should be included. Default value is "true".
     *
     * @wapfaces.attribute
     *     initValue="true"
     *     abstract="true"
     *     inherit="true"
     */
    boolean showDetail;

     /**
     * Flag indicating whether the summary portion of displayed messages should be included. Default value is "false".
     *
     * @wapfaces.attribute
     *     initValue="false"
     *     abstract="true"
     *     inherit="true"
     */
    boolean showSummary;

}
