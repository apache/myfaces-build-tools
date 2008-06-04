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
        <h:outputText value="PanelGrid" />
      </t:htmlTag>
    </t:documentHead>
    <t:documentBody>
      <t:outputText value="Panel Grid" />
      <h:panelGrid id="hPanelGrid" columns="4"
        columnClasses="oddColumnClass,evenColumnClass"
        headerClass="tableHeaderClass" footerClass="tableFooterClass"
        styleClass="tableClass" style="background-color: silver;"
        lang="en" rowClasses="oddRowClass,evenRowClass"
        summary="h:panelGrid Summary" title="h:panelGrid Title">
        <f:facet name="header" >
          <h:outputText value="Header for h:panelGrid" />
        </f:facet>
        <h:outputText value="1.1" />
        <h:outputText value="1.2" />
        <h:outputText value="1.3" />
        <h:outputText value="1.4" />
        <h:outputText value="2.1" />
        <h:outputText value="2.2" />
        <h:outputText value="2.3" />
        <h:outputText value="2.4" />
        <h:outputText value="3.1" />
        <h:outputText value="3.2" />
        <h:outputText value="3.3" />
        <h:outputText value="3.4" />
        <h:outputText value="4.1" />
        <h:outputText value="4.2" />
        <h:outputText value="4.3" />
        <h:outputText value="4.4" />
        <f:facet name="footer">
          <h:outputText value="Footer for h:panelGrid" />
        </f:facet>
      </h:panelGrid>
      <h:form id="footerLinks">
        <h:commandLink id="homeLink" action="home" value="Home" />
        <h:outputText value=" " />
        <h:outputLink target="sourceWindow"
          value="#{facesContext.externalContext.requestContextPath}#{view.viewId}.source">
          <h:outputText value="View source in popup window" />
        </h:outputLink>
      </h:form>
    </t:documentBody>
  </t:document>
</f:view>
