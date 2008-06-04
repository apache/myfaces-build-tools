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
 * Render an WML "a" anchor element that acts like a form submit button when clicked.
 * This element must be a childern of UIForm component.
 *
 * Decode Behavior
 * Obtain the Map from the "requestParameterMap" property of the ExternalContext. If the value in theMap for
 * the value of the "clientId" property of the component is not null, get the value of the "type" attribute,
 * and convert it to lower case. If the result is equal to the String "reset" (without the quotes), return fromdecode().
 * Otherwise, create ajavax.faces.event.ActionEvent around the component, and pass it to the queueEvent() method
 * of the component, which must be an instance ofUICommand.
 *
 * Encode Behavior
 * Wml don't contain submit button tag. This element is rendered using "do" and "go" wml tags.
 *
 * @wapfaces.tag
 *       componentFamily="UICommand"
 *       rendererType="CommandLinkRenderer"
 *       tagName="commandLink"
 *       tagBaseClass="org.apache.myfaces.wap.base.ActionSourceTagBase"
 *       bodyContent="JSP"
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */ 


public class CommandLink extends javax.faces.component.UICommand {

    /**
     * Defines a text identifying the link.
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
     * MethodBinding representing the application action to invoke when this component is activated by the user. The expression must evaluate to a public method that takes no parameters, and returns a String (the logical outcome) which is passed to the NavigationHandler for this application.
     *
     * @wapfaces.attribute
     *     inherit="true"
     *     abstract="true"
     */
    java.lang.String action;

    /**
     * MethodBinding representing an action listener method that will be notified when this component is activated by the user. The expression must evaluate to a public method that takes an ActionEvent parameter, with a return type of void.
     *
     * @wapfaces.attribute
     *     inherit="true"
     *     abstract="true"
     */
    java.lang.String actionListener;

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
     * Flag indicating that this component's value must be converted and validated immediately (that is, during Apply Request Values phase), rather than waiting until Process Validations phase.
     *
     * @wapfaces.attribute
     *     inherit="true"
     *     abstract="true"
     */
    boolean immediate;

    /**
     * The current value of this component.
     * TODO: implement this attribute.
     *
     * @wapfaces.attribute
     *     inherit="true"
     *     abstract="true"
     */
    java.lang.Object value;

}
