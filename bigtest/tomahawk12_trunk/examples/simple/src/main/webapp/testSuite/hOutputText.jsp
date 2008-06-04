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
        <h:outputText value="h:outputText" />
      </t:htmlTag>
    </t:documentHead>
    <t:documentBody>
      <h:outputText value="h:outputText" />

      <h:outputText id="outputText" 
        style="background-color: gray;" title="title" styleClass="class"
        value="text" />
      <h:outputText value=" | " />
      <h:outputText id="renderedTrue" value="You should see this"
        style="color: green;" rendered="true" />
      <h:outputText value=" | " />
      <h:outputText id="renderedFalse" value="You should not see this"
        style="color: red;" rendered="false" />
      <h:outputText value=" | " />
      <h:outputText id="escape" escape="true" value="10 &gt; 5" />
      <h:outputText value=" | " />
      <h:outputText id="notEscape" escape="false" value="10 &gt; 5" />
      <h:outputText value=" | " />
      <h:outputText id="utf8charEscaped" value="δόφί" escape="true" /> 
      <h:outputText value=" | " />
      <h:outputText id="utf8charNotEscaped" value="δόφί" escape="false" /> 
      <h:outputText value=" | " />
      <h:outputText id="utf8char" value="δόφί" /> 
      <h:outputText value=" | " />
      <h:outputText id="utf8charInEscapedFormat" value="&#228;&#252;&#246;&#223;" escape="false" /> 


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
