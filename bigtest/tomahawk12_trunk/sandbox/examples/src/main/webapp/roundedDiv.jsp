<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s"%>
<html>

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

<%@include file="inc/head.inc" %>

<body>
<f:view>
    <h:form>
        <h:panelGrid columns="2">
            <h:panelGrid columns="2" style="border: 1px double black;">
                <f:facet name="header">
                  <h:outputText value="Attributes" />
                </f:facet>
                <h:outputText value="Color:" />
                <h:panelGroup>
                    <h:inputText id="Color"
                        value="#{roundedDiv.color}"
                        required="true" />
                    <t:message for="Color" errorStyle="color: red;" 
                        fatalStyle="color: red;" replaceIdWithLabel="true"
                        tooltip="true" warnStyle="color: orange;" />
                </h:panelGroup>
                <h:outputText value="Background color:" />
                <h:panelGroup>
                    <h:inputText id="BackgroundColor"
                        value="#{roundedDiv.backgroundColor}" />
                    <t:message for="BackgroundColor" errorStyle="color: red;" 
                        fatalStyle="color: red;" replaceIdWithLabel="true"
                        tooltip="true" warnStyle="color: orange;" />
                </h:panelGroup>
                <h:outputText value="Border color:" />
                <h:panelGroup>
                    <h:inputText id="BorderColor"
                        value="#{roundedDiv.borderColor}" />
                    <t:message for="BorderColor" errorStyle="color: red;" 
                        fatalStyle="color: red;" replaceIdWithLabel="true"
                        tooltip="true" warnStyle="color: orange;" />
                </h:panelGroup>
                <h:outputText value="Border width:" />
                <h:panelGroup>
                    <h:inputText id="BorderWidth"
                        value="#{roundedDiv.borderWidth}" />
                    <t:message for="BorderWidth" errorStyle="color: red;" 
                        fatalStyle="color: red;" replaceIdWithLabel="true"
                        tooltip="true" warnStyle="color: orange;" />
                </h:panelGroup>
                <h:outputText value="Radius:" />
                <h:panelGroup>
                    <h:inputText id="Radius"
                        value="#{roundedDiv.radius}" />
                    <t:message for="Radius" errorStyle="color: red;" 
                        fatalStyle="color: red;" replaceIdWithLabel="true"
                        tooltip="true" warnStyle="color: orange;" />
                </h:panelGroup>
                <h:outputText value="Corners:" />
                <h:panelGroup>
                    <h:inputText id="Corners"
                        value="#{roundedDiv.corners}" />
                    <t:message for="Corners" errorStyle="color: red;" 
                        fatalStyle="color: red;" replaceIdWithLabel="true"
                        tooltip="true" warnStyle="color: orange;" />
                </h:panelGroup>
                <h:outputText value="Size:" />
                <h:panelGroup>
                    <h:inputText id="Size"
                        value="#{roundedDiv.size}" />
                    <t:message for="Size" errorStyle="color: red;" 
                        fatalStyle="color: red;" replaceIdWithLabel="true"
                        tooltip="true" warnStyle="color: orange;" />
                </h:panelGroup>
                <h:outputText value="Style:" />
                <h:panelGroup>
                    <h:inputText id="Style"
                        value="#{roundedDiv.style}" />
                    <t:message for="Style" errorStyle="color: red;" 
                        fatalStyle="color: red;" replaceIdWithLabel="true"
                        tooltip="true" warnStyle="color: orange;" />
                </h:panelGroup>
                <h:outputText value="Style class:" />
                <h:panelGroup>
                    <h:inputText id="StyleClass"
                        value="#{roundedDiv.styleClass}" />
                    <t:message for="StyleClass" errorStyle="color: red;" 
                        fatalStyle="color: red;" replaceIdWithLabel="true"
                        tooltip="true" warnStyle="color: orange;" />
                </h:panelGroup>
                <h:outputText value="Content style:" />
                <h:panelGroup>
                    <h:inputText id="ContentStyle"
                        value="#{roundedDiv.contentStyle}" />
                    <t:message for="ContentStyle" errorStyle="color: red;" 
                        fatalStyle="color: red;" replaceIdWithLabel="true"
                        tooltip="true" warnStyle="color: orange;" />
                </h:panelGroup>
                <h:outputText value="Content style class:" />
                <h:panelGroup>
                    <h:inputText id="ContentStyleClass"
                        value="#{roundedDiv.contentStyleClass}" />
                    <t:message for="ContentStyleClass" errorStyle="color: red;" 
                        fatalStyle="color: red;" replaceIdWithLabel="true"
                        tooltip="true" warnStyle="color: orange;" />
                </h:panelGroup>
                <h:outputText value="Layout:" />
                <h:panelGroup>
                    <h:inputText id="Layout"
                        value="#{roundedDiv.layout}" />
                    <t:message for="Layout" errorStyle="color: red;" 
                        fatalStyle="color: red;" replaceIdWithLabel="true"
                        tooltip="true" warnStyle="color: orange;" />
                </h:panelGroup>
                <f:facet name="footer">
                    <h:panelGroup>
                        <h:commandButton value="Update" />
                        <h:commandButton value="Reset" actionListener="#{roundedDiv.reset}" />
                    </h:panelGroup>
                </f:facet>
            </h:panelGrid>
            <s:roundedDiv
                backgroundColor="#{roundedDiv.backgroundColor}"
                borderColor="#{roundedDiv.borderColor}"
                borderWidth="#{roundedDiv.borderWidth}"
                color="#{roundedDiv.color}"
                corners="#{roundedDiv.corners}"
                radius="#{roundedDiv.radius}"
                size="#{roundedDiv.size}"
                style="#{roundedDiv.style}"
                styleClass="#{roundedDiv.styleClass}"
                contentStyle="#{roundedDiv.contentStyle}"
                contentStyleClass="#{roundedDiv.contentStyleClass}"
                layout="#{roundedDiv.layout}">
                <h:outputText value="Contents" />
            </s:roundedDiv>
        </h:panelGrid>
    </h:form>
</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>
</html>
