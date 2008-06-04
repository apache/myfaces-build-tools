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

      <!-- Testing h:OutputLabel and h:InputText -->
      <h:form id="textInputForm">
        <h:outputText id="hOutputText" value="in textForm" />
        <h:outputLabel id="hOutputLabel" for="hInputText" value="Label" />
        <h:inputText id="hInputText" value="text value" />
      <!-- 
  Just testing attributes.  The value for each attribute are not ment
  to be valid.
-->
        <h:inputText id="hInputTextWithAttributes" accesskey="K"
          alt="altText" dir="ltr" lang="en" maxlength="100" onblur="onBlur" 
          onchange="onChange" onfocus="onFocus" onkeydown="onKeyDown"
          onkeyup="onKeyUp" onmousedown="onMouseDown" onmouseout="onMouseOut"
          onmouseover="onMouseOver" onmouseup="onMouseUp" onselect="onSelect"
          size="10" style="font-weight: bold;" styleClass="Class"
          tabindex="7" title="thisTitle" value="text value" />
        <h:inputText id="hReadOnlyInputText" readonly="true" value="readOnly" />
        <h:inputText id="hDisabledInputText" disabled="true" value="Disabled" />
      </h:form>

      <!-- Testing h:inputTextArea -->
      <h:form id="textAreaInputForm" >
        <h:inputTextarea id="hInputTextarea" value="Text Area" />
        <h:inputTextarea id="hInputTextareaWithAttributes" cols="5" 
          rows="10" value="Text Area with attributes" />
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
