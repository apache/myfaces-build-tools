<%--
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
  --%>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<f:loadBundle basename="org.apache.myfaces.examples.resource.build" var="buildInfo"/>
<f:loadBundle basename="org.apache.myfaces.examples.resource.example_messages" var="example_messages"/>

<h:panelGrid id="header_group1" columns="2" styleClass="pageHeader"  >
    <h:graphicImage id="header_logo" url="/images/logo_mini.jpg" alt="#{example_messages['alt_logo']}" />
    <f:verbatim>
        <h:outputText style="font-size:20px;color:#FFFFFF;" escape="false" value="MyFaces - The free JavaServer&#8482; Faces Implementation"/>
        <h:outputText style="font-size:10px;color:#FFFFFF" value=" (Sandbox15 - Tomahawk Version #{buildInfo['tomahawk_version']}, using #{buildInfo ['jsf_implementation']})"/>
    </f:verbatim>
</h:panelGrid>

<h:panelGrid id="header_group2" columns="1" styleClass="pageHeader2" columnClasses="pageHeader2col1"  >
    <t:jscookMenu layout="hbr" theme="ThemeOffice" >
        <t:navigationMenuItem id="nav_1" itemLabel="#{example_messages['nav_Home']}" action="go_home" />
        <t:navigationMenuItem id="nav_2" itemLabel="#{example_messages['nav_Examples']}" >
            <t:navigationMenuItem id="nav_2_4" itemLabel="#{example_messages['nav_Components']}" icon="/images/component.gif" split="true" >
	            <t:navigationMenuItem id="nav_2_4_10" itemLabel="#{example_messages['nav_dynaForm']}" action="go_dynaForm" icon="/images/myfaces.gif" />
            </t:navigationMenuItem>
        </t:navigationMenuItem>
    </t:jscookMenu>
</h:panelGrid>

