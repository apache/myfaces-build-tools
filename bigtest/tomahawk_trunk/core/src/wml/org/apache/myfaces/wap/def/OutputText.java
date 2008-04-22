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
 * If the "styleClass" attribute is present, render its value as the value of the "class" attribute. If the "escape" attribute is not present, or it is present and its value is "true" all angle brackets should be converted to the ampersand xx semicolon syntax when rendering the value of the "value" attribute as the value of the component. If the "escape" attribute is present and is "false" the value of the component should be rendered as text without escaping.
 *
 * @wapfaces.tag
 *       componentFamily="UIOutput"
 *       rendererType="OutputTextRenderer"
 *       tagName="outputText"
 *       tagBaseClass="org.apache.myfaces.wap.base.ValueHolderTagBase"
 *       bodyContent="JSP"
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */


public class OutputText extends javax.faces.component.UIOutput {

    /**
     * Flag indicating that characters that are sensitive in WML and XML markup must be escaped. This flag is set to "true" by default.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     *     initValue="true"
     */
    boolean escape;

    /**
     * CSS style(s) to be applied when this component is rendered.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    //java.lang.String style;

    /**
     * Space-separated list of style class(es) to be applied when this element is rendered. This value must be passed through as the "class" attribute on generated markup.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    //java.lang.String styleClass;


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
     * Converter instance registered with this component.
     *
     * @wapfaces.attribute
     *     abstract="true"
     *     inherit="true"
     */
    java.lang.String converter;

    /**
     * The current value of this component.
     *
     * @wapfaces.attribute
     *     inherit="true"
     *     abstract="true"
     */
    java.lang.Object value;
}
