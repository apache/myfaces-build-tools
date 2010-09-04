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
    <title>My JSF Components</title>
</head>

<body>

<f:view>

    <mycomp:sayHello firstName="John" lastName="Smith"/>

    <h:outputText value=" (probably not you, I know)"/>

    <f:verbatim><hr></f:verbatim>

    <h:form>
        <h:panelGrid columns="2">
            <h:outputLabel for="inputFirstName" value="First name" />
            <h:inputText id="inputFirstName" value="#{sayHelloBean.firstName}"/>

            <h:outputLabel for="inputLastName" value="Last name" />
            <h:inputText id="inputLastName" value="#{sayHelloBean.lastName}"/>
        </h:panelGrid>

        <h:panelGrid columns="1">

            <h:commandButton value="Say Hello!" actionListener="#{sayHelloBean.sayIt}"/>

            <mycomp:sayHello firstName="#{sayHelloBean.firstName}"
                             lastName="#{sayHelloBean.lastName}"
                             rendered="#{sayHelloBean.renderGreeting}"/>

            <h:commandLink value="[HOME]" action="go_home"/>

        </h:panelGrid>

    </h:form>

</f:view>


</body>

</html>
