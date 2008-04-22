<%@ page session="false" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s" %>

<html>

<%@ include file="inc/head.inc" %>

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

    <h1>Sandbox DateTimeConverter</h1>
    <h:form>
		<t:messages styleClass="error" />

        <h:panelGrid columns="3">
            <h:outputText value="Sandbox DateTimeConverter:"/>
            <h:inputText value="#{dateTimeConverterBean.date1}">
                <s:convertDateTime pattern="yyyy-MM-dd HH:mm:ss"></s:convertDateTime>
            </h:inputText>
            <h:outputText value="#{dateTimeConverterBean.date1}">
                <s:convertDateTime pattern="yyyy-MM-dd HH:mm:ss z"></s:convertDateTime>
            </h:outputText>

            <h:outputText value="Spec DateTimeConverter:"/>
            <h:inputText value="#{dateTimeConverterBean.date2}">
                <f:convertDateTime pattern="yyyy-MM-dd HH:mm:ss"/>
            </h:inputText>
            <h:outputText value="#{dateTimeConverterBean.date2}">
                <f:convertDateTime pattern="yyyy-MM-dd HH:mm:ss z"/>
            </h:outputText>
        </h:panelGrid>
        <h:commandButton action="#{dateTimeConverterBean.submit}" value="Submit"/>
    </h:form>


</f:view>

<%@ include file="inc/page_footer.jsp" %>

</body>

</html>

