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

        <h:panelGrid columns="3">


            <f:facet name="footer">
                <h:panelGrid columns="4">
                    <h:commandButton id="trapButton" value="trap button" action="#{submitOnEvent.trapAction}"/>
                    <h:commandButton id="submitButton" value="submit button" action="#{submitOnEvent.buttonAction}"/>
                    <h:commandLink id="submitLink" value="submit link" action="#{submitOnEvent.linkAction}"/>

                    <h:outputText value="last action was: #{submitOnEvent.lastAction}" />

                </h:panelGrid>
            </f:facet>

            <h:outputText value="description" />
            <h:outputText value="control" />
            <h:outputText value="output" />


            <h:outputLabel for="text1" value="submit via link on enter (global event)"/>
            <h:inputText id="text1" value="#{submitOnEvent.strings.text1}"/>
            <h:outputText value="#{submitOnEvent.strings.text1}"/>


        </h:panelGrid>

        <s:submitOnEvent for="submitLink" />
    </h:form>
</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>
