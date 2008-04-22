<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s"%>

<html>

<%@include file="inc/head.inc" %>

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

<body>

<f:view>
    <h:form>
        <h:panelGrid>
            <t:messages showDetail="true" showSummary="false"/>
            <h:outputText value="Example for partial validation"/>
            <s:subForm id="personForm">
                <h:outputText value="Person" style="font-weight:bold;"/>
                <h:panelGrid columns="2">
                    <h:outputLabel for="firstName" value="First name:"/>
                    <h:inputText id="firstName" value="#{subFormBean.firstName}" required="true"/>
                    <h:outputLabel for="lastName" value="Last name:"/>
                    <h:inputText id="lastName" value="#{subFormBean.lastName}" required="true"/>
                    <h:outputText value="Partial condition"/>
                    <h:selectBooleanCheckbox value="false"                                              
                                             onchange="personForm_submit();"/>                   
                </h:panelGrid>
                <h:commandButton value="Show current values of Person"/>
            </s:subForm>
            
            <s:subForm id="vehicleForm">
                <h:outputText value="Vehicle" style="font-weight:bold;"/>
                <h:panelGrid columns="2">
                    <h:outputLabel for="firstName" value="Type:"/>
                    <h:inputText id="firstName" value="#{subFormBean.type}" required="true"/>
                    <h:outputLabel for="lastName" value="Make:"/>
                    <h:inputText id="lastName" value="#{subFormBean.make}" required="true"/>
                    <h:outputText value="Partial condition"/>
                    <h:selectBooleanCheckbox value="false"                                              
                                             onchange="vehicleForm_submit();"/>  
                </h:panelGrid>
                <h:commandButton value="Show current values of Vehicle"/>
            </s:subForm>
                       
            <t:commandButton value="Show current values of Person" actionFor="personForm"/>
            <t:commandButton value="Show current values of Vehicle" actionFor="vehicleForm"/>
            <t:commandButton value="Show current values of Person and Vehicle (actionFor for both forms set)" actionFor="personForm,vehicleForm"/>
            <h:commandButton value="Show current values of Person and Vehicle (no actionFor set), standard button"/>
            <t:commandButton value="Show current values of Person and Vehicle (no actionFor set), extended button"/>

            <h:panelGrid columns="2">
                <h:outputText value="First name of Person:"/>
                <h:outputText value="#{subFormBean.firstName}"/>
                <h:outputText value="Last name of Person:"/>
                <h:outputText value="#{subFormBean.lastName}"/>
                <h:outputText value="Type of vehicle:"/>
                <h:outputText value="#{subFormBean.type}"/>
                <h:outputText value="Make of vehicle:"/>
                <h:outputText value="#{subFormBean.make}"/>
            </h:panelGrid>
        </h:panelGrid>

    </h:form>
</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>
