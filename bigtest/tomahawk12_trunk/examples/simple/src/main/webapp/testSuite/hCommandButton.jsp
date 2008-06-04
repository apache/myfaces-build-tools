<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<!--
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
//-->

<f:view>
  <t:document>
    <t:documentHead>
      <t:htmlTag value="title">
        <h:outputText value="#{view.viewId}" />
      </t:htmlTag>
    </t:documentHead>
    <t:documentBody>
      <h:outputText value="Running test #{view.viewId}" />
      <t:htmlTag value="br" />
      <t:htmlTag value="br" />

      <!-- Begin test -->

      <!-- Testing h:outputLabel -->
      <h:form id="commandButtonForm">
        <h:commandButton id="commandButton" value="Default"/>
        <h:commandButton id="commandButtonImage" image="/images/logo.jpg" alt="Alt"/>
        <h:commandButton id="commandButtonTitle" value="Title"  title="Popup Title"/>
        <h:commandButton id="commandButtonReadOnly" value="Read Only" readonly="true"/>
        <h:commandButton id="commandButtonNotReadOnly" value="not Read Only" readonly="false"/>
        <h:commandButton id="commandButtonReset" value="Reset" type="reset"/>
        <h:commandButton id="commandButtonSubmit" value="Submit" type="submit"/>
        <h:commandButton id="commandButtonTabIndex" value="TabIndex" tabindex="4"/>
      </h:form>

      <!-- End test -->

      <t:htmlTag value="br" />
      <h:form id="footerLinks">
        <h:commandLink id="homeLink" action="home" value="Home" />
        <h:outputText value=" " />
        <h:outputLink id="sourceLink" target="sourceWindow"
          value="#{facesContext.externalContext.requestContextPath}#{view.viewId}.source">
          <h:outputText value="View source in popup window" />
        </h:outputLink>
      </h:form>
    </t:documentBody>
  </t:document>
</f:view>
