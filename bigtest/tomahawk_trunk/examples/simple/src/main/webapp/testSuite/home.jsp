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
        <h:outputText value="Testing Home" />
      </t:htmlTag>
    </t:documentHead>
    <t:documentBody>
      <h:form>
        <t:panelNavigation2 layout="list">
          <t:navigationMenuItem action="hCommandButton" itemLabel="h:commandButton" />
          <t:navigationMenuItem action="hForm" itemLabel="h:Form" />
          <t:navigationMenuItem action="hFormWithInputs" itemLabel="h:Form and h:input..." />
          <t:navigationMenuItem action="hFormWithSelects" itemLabel="h:Form and h:select..." />
          <t:navigationMenuItem action="hGraphicImage" itemLabel="h:graphicImage" />
          <t:navigationMenuItem action="hOutputLink" itemLabel="h:outputLink" />
          <t:navigationMenuItem action="hOutputText" itemLabel="h:outputText" />
          <t:navigationMenuItem action="hPanelGrid" itemLabel="h:panelGrid" />
          <t:navigationMenuItem action="tPanelNavigation" itemLabel="t:commandNavigation" />
          <t:navigationMenuItem action="tPanelNavigation2" itemLabel="t:commandNavigation2" />
          <t:navigationMenuItem action="tPanelNavigation" itemLabel="t:navigationMenuItem" />
          <t:navigationMenuItem action="tPanelNavigation2" itemLabel="t:navigationMenuItem #2" />
          <t:navigationMenuItem action="tPanelNavigation" itemLabel="t:panelNavigation" />
          <t:navigationMenuItem action="tPanelNavigation2" itemLabel="t:panelNavigation2" />
          <t:navigationMenuItem action="tOutputText" itemLabel="t:outputText" />
        </t:panelNavigation2>
      </h:form>
    </t:documentBody>
  </t:document>
</f:view>
