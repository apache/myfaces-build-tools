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
 * The inputText tag represents an input field (a text field where the user can enter some text). Default value sets the attribute "value".
 * If the "styleClass" attribute is present, render its value as the value of the "class" attribute.
 *
 * @wapfaces.tag
 *       componentFamily="UIInput"
 *       rendererType="InputTextRenderer"
 *       tagName="inputText"
 *       tagBaseClass="org.apache.myfaces.wap.base.EditableValueHolderTagBase"
 *       bodyContent="JSP"
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */


public class InputText extends javax.faces.component.UIInput {

    /**
     * The name of the variable that is set with the result of the user's input.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String name;

    /**
     * Sets whether the user can leave the input field blank or not. Default is "false"
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    boolean emptyok;

    /**
     * Sets the data format for the input field. Default is "*M".
     * A = uppercase alphabetic or punctuation characters
     * a  = lowercase alphabetic or punctuation characters
     * N = numeric characters
     * X = uppercase characters
     * x  = lowercase characters
     * M = all characters
     * m  = all characters
     * f = Any number of characters. Replace the f with one of the letters above to specify what characters the user can enter
     * nf  = Replace the n with a number from 1 to 9 to specify the number of characters the user can enter. Replace the f with one of the letters above to specify what characters the user can enter
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String format;

    /**
     * Sets the maximum number of characters the user can enter in the field
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String maxlength;

    /**
     * Sets the width of the input field
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String size;

    /**
     * Sets the tabbing position for the input field
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String tabindex;

    /**
     * Sets a title for the input field
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

    /**
     * Space-separated list of style class(es) to be applied when this element is rendered. This value must be passed through as the "class" attribute on generated markup.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String styleClass;


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
     * Flag indicating that this component's value must be converted and validated immediately (that is, during Apply Request Values phase), rather than waiting until Process Validations phase.
     *
     * @wapfaces.attribute
     *     inherit="true"
     *     abstract="true"
     */
    boolean immediate;

    // ============= ABSTARACT ATTRIBUTES ======================================
    /**
     * The current value of this component.
     *
     * @wapfaces.attribute
     *     inherit="true"
     *     abstract="true"
     */
    java.lang.Object value;

    /**
     * Flag indicating that the user is required to provide a submitted value for this input component.
     *
     * @wapfaces.attribute
     *     abstract="true"
     *     inherit="true"
     */
    boolean required;

    /**
     * MethodBinding representing a validator method that will be called during Process Validations to perform correctness checks on the value of this component. The expression must evaluate to a public method that takes FacesContext, UIComponent, and Object parameters, with a return type of void.
     *
     * @wapfaces.attribute
     *     abstract="true"
     *     inherit="true"
     */
    java.lang.String validator;

    /**
     * MethodBinding representing a value change listener method that will be notified when a new value has been set for this input component. The expression must evaluate to a public method that takes a ValueChangeEvent parameter, with a return type of void.
     *
     * @wapfaces.attribute
     *     abstract="true"
     *     inherit="true"
     */
    java.lang.String valueChangeListener;
}
