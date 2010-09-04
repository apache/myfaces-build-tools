<%--
    Licensed to the Apache Software Foundation (ASF) under one
    or more contributor license agreements.  See the NOTICE file
    distributed with this work for additional information
    regarding copyright ownership.  The ASF licenses this file
    to you under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.
--%>

<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://www.myorganitzation.org/mycomponents" prefix="mycomp"%>

<html>

<head>
    <title>My JSF Components Examples</title>
</head>

<body>

<f:view>
    <h:form>

        <f:loadBundle basename="org.myorganization.component.example.build" var="buildInfo"/>

        <h:panelGrid columns="2">
            <h:outputText style="font-weight: bold" value="My JSF Components Library"/>
            <h:outputText value=" (Version #{buildInfo['mycomponents_version']}, using #{buildInfo ['jsf_implementation']})"/>
        </h:panelGrid>

        <h:panelGrid>

            <h:outputText value="My component examples" />
            <h:panelGrid style="padding-left:25px">
                <h:outputLink value="sayhello.jsf">
                    <f:verbatim>sayHello - demo component</f:verbatim>
                </h:outputLink>
                <h:outputLink value="validatorDemo.jsf">
                    <f:verbatim>sayHello - demo validator</f:verbatim>
                </h:outputLink>
                <h:outputLink value="converterDemo.jsf">
                    <f:verbatim>sayHello - demo converter</f:verbatim>
                </h:outputLink>
            </h:panelGrid>

        </h:panelGrid>

    </h:form>
</f:view>
</body>
</html>
