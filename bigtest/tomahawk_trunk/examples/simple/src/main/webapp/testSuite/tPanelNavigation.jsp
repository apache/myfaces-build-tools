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
        <h:outputText value="tpanelNavigation2" />
      </t:htmlTag>
    </t:documentHead>
    <t:documentBody>
      <t:outputText value="t:panelNavigation with t:navigationMenuItem" />
      <h:form id="navListForm">
        <t:panelNavigation id="pn2_list"
          activeItemClass="activeItemClass" itemClass="itemClass"
          openItemClass="openItemClass" separatorClass="seperatorClass"
          styleClass="styleClass">
          <t:navigationMenuItem id="nmi_p1" action="page_1"
            itemLabel="Page 1" icon="/testSuite/images/icon1.jpg" />
          <t:navigationMenuItem id="nmi_p2" action="page_2"
            itemLabel="Page 2" />
          <t:navigationMenuItem id="nmi_p3" action="page_3"
            icon="/images/icon3.jpg" />
          <t:navigationMenuItem id="nmi_p4" value="Page 4">
            <t:navigationMenuItem id="nmi_p4_1" action="page_4"
              itemLabel="Page 4.1" />
          </t:navigationMenuItem>
        </t:panelNavigation>
      </h:form>
      <t:outputText value="t:panelNavigation with t:commandNavigation" />
      <h:form id="nav2Form">
        <t:panelNavigation id="pn2_list"
          activeItemClass="activeItemClass" itemClass="itemClass"
          openItemClass="openItemClass" separatorClass="seperatorClass"
          styleClass="styleClass">
          <t:commandNavigation id="cn2_p1" action="page_1"
            value="Page 1" />
          <t:commandNavigation id="cn2_p2" action="page_2">
            <h:graphicImage url="/testSuite/images/icon2.jpg" />
            <h:outputText value="Page 2" />
          </t:commandNavigation>
          <t:commandNavigation id="cn2_p3" action="page_3">
            <h:outputText value="Page 3" />
          </t:commandNavigation>
          <t:commandNavigation id="cn2_4">
            <h:outputText value="Page 4" />
            <t:commandNavigation id="cn2_4_1" action="page_4">
              <h:outputText value="Page 4.1" />
            </t:commandNavigation>
          </t:commandNavigation>
        </t:panelNavigation>
      </h:form>


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
