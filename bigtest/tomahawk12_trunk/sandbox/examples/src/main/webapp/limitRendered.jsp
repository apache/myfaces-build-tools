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
        <h:panelGrid columns="1">
            <h:outputText value="Set the rendered flags:" />
            <h:panelGroup>
                <h:selectBooleanCheckbox
                    value="#{limitRendered.ARendered}" />
                <h:outputText value="A" />
            </h:panelGroup>
            <h:panelGroup>
                <h:selectBooleanCheckbox
                    value="#{limitRendered.BRendered}" />
                <h:outputText value="B" />
            </h:panelGroup>
            <h:panelGroup>
                <h:selectBooleanCheckbox
                    value="#{limitRendered.CRendered}" />
                <h:outputText value="C" />
            </h:panelGroup>
            <h:panelGroup>
                <h:selectBooleanCheckbox
                    value="#{limitRendered.DRendered}" />
                <h:outputText value="D" />
            </h:panelGroup>
            <f:facet name="footer">
              <h:commandButton value="Update" />
            </f:facet>
        </h:panelGrid>
        
        <h:outputText value="Output:" />
        <h:panelGrid columns="2">
            <h:outputText value="First:" />
            <s:limitRendered>
                <h:outputText value="A" rendered="#{limitRendered.ARendered}" />
                <h:outputText value="B" rendered="#{limitRendered.BRendered}" />
                <h:outputText value="C" rendered="#{limitRendered.CRendered}" />
                <h:outputText value="D" rendered="#{limitRendered.DRendered}" />
            </s:limitRendered>
            <h:outputText value="First two:" />
            <s:limitRendered value="2">
                <h:outputText value="A" rendered="#{limitRendered.ARendered}" />
                <h:outputText value="B" rendered="#{limitRendered.BRendered}" />
                <h:outputText value="C" rendered="#{limitRendered.CRendered}" />
                <h:outputText value="D" rendered="#{limitRendered.DRendered}" />
            </s:limitRendered>
            <h:outputText value="First three (by index):" />
            <s:limitRendered type="index" value="0,1,2">
                <h:outputText value="A" rendered="#{limitRendered.ARendered}" />
                <h:outputText value="B" rendered="#{limitRendered.BRendered}" />
                <h:outputText value="C" rendered="#{limitRendered.CRendered}" />
                <h:outputText value="D" rendered="#{limitRendered.DRendered}" />
            </s:limitRendered>
            <h:outputText value="First and last (by index):" />
            <s:limitRendered type="index" value="#{limitRendered.indexes}">
                <h:outputText value="A" rendered="#{limitRendered.ARendered}" />
                <h:outputText value="B" rendered="#{limitRendered.BRendered}" />
                <h:outputText value="C" rendered="#{limitRendered.CRendered}" />
                <h:outputText value="D" rendered="#{limitRendered.DRendered}" />
            </s:limitRendered>
            <h:outputText value="Middle two (by index):" />
            <s:limitRendered type="index" value="#{limitRendered.indexCollection}">
                <h:outputText value="A" rendered="#{limitRendered.ARendered}" />
                <h:outputText value="B" rendered="#{limitRendered.BRendered}" />
                <h:outputText value="C" rendered="#{limitRendered.CRendered}" />
                <h:outputText value="D" rendered="#{limitRendered.DRendered}" />
            </s:limitRendered>
        </h:panelGrid>
    </h:form>
</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>
</html>
