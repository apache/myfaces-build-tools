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
 * Renders an WML table element. Required attribute is "column" what sets number of colums in the table. Render the "header" and a "footer" facet if present, as the first or last row of the table. Render the children based on the value of the "columns" attribute, creating a new row each time a "columns" worth of children have been rendered. Each child is rendered inside of a "td" element. If a child has "rendered==false" it is not rendered, and the column counter must not be incremented.
 *
 * @wapfaces.tag
 *       componentFamily="UIPanel"
 *       rendererType="GridRenderer"
 *       tagName="panelGrid"
 *       tagBaseClass="org.apache.myfaces.wap.base.ComponentTagBase"
 *       bodyContent="JSP"
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */


public class PanelGrid extends javax.faces.component.UIPanel {

    /**
     * Aligns the text in a column. Specify a list of the align values, one for each column. Posible are values: "C", "L" or "R". Example: aling="RRCL" - the first column is align to right, second right, third center and the fourth column to left. Default aling is left.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String align;

    /**
     * Sets the number of columns in the table.
     *
     * @wapfaces.attribute
     *     required="true"
     *     valueBinding="true"
     */
    java.lang.String columns;

    /**
     * The attribute styleClass affiliates an element with one or more classes. Multiple elements can be given the same styleClass name.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String styleClass;

    /**
     * Sets a title for the table.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String title;

    /**
     * The xml:lang attribute specifies the natural or formal language of an element or its attributes.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String xmllang;

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
