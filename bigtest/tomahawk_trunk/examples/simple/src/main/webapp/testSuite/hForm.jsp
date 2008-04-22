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
        <h:outputText value="Test #{view.viewId}" />
      </t:htmlTag>
    </t:documentHead>
    <t:documentBody>
      <h:outputText value="Running test #{view.viewId}" />
      <t:htmlTag value="br" />
      <t:htmlTag value="br" />

      <!-- Begin test -->

      <h:form id="basicForm">
        <h:outputText id="hOutputText" value="in basicForm" />
      </h:form>

      <!-- Verify form is not rendered -->
      <h:form id="nonRenderedForm" rendered="false">
        <h:outputText id="hOutputText" value="in nonrenderedForm" />
      </h:form>

      <!-- 
  Just testing attributes.  The value for each attribute are not ment
  to be valid.
-->
      <h:form id="formWithAttributes" target="formFrame"
        accept="contentType" acceptcharset="charSet"
        enctype="encodeType" onreset="Alert('onReset');"
        onsubmit="Alert('onSubmit');">
        <h:outputText id="hOutputText" value="in formWithAttributes" />
      </h:form>

      <!-- End test -->

      <t:htmlTag value="br" />
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
