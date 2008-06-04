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

    <h:form id="form1">
    		<s:effect id="effect1" fade="true">
			<h:outputText style="width: 400px; text-align: center;"
				value="state change notifier, triggers a popup if something has changed in the forms" />
		</s:effect>
    
    
        <s:stateChangedNotifier confirmationMessage="Values have changed. Did you know it?"
                                disabled="false"
                                excludedIds="excludedLink1,excludedLink2"/>

        <h:panelGrid columns="1">
            <h:inputText id="input1" value="" required="true"/>
            <h:message for="input1"/>

            <h:inputTextarea value=""/>
            <h:selectOneMenu value="item2">
                <f:selectItem itemValue="item1" itemLabel="Item 1"/>
                <f:selectItem itemValue="item2" itemLabel="Item 2"/>
                <f:selectItem itemValue="item3" itemLabel="Item 3"/>
            </h:selectOneMenu>

            <t:commandLink value="Submit Form (goes to the home page)" action="home"/>
            <t:commandLink id="excludedLink1" forceId="true"
                       value="Excluded link (goes to the home page without warning)" action="home"/>
            <t:commandButton
                       value="Submit Form (goes to the home page)" action="home"/>
            <t:commandButton id="excludedLink2"
                       value="Excluded button (goes to the home page without warning)" action="home"/>
        </h:panelGrid>


    </h:form>

    <h:form id="form2">
        <s:stateChangedNotifier confirmationMessage="Everything ok?" excludedIds="link3"/>

        <h:panelGrid columns="1">
            <h:inputText id="input1" value="" />
            <h:message for="input1"/>

            <h:inputTextarea value=""/>

            <t:commandLink value="Submit Form (goes to the home page)" action="home"/>
            <t:commandButton id="link3"
                       value="Excluded button (goes to the home page without warning)" action="home"/>
        </h:panelGrid>


    </h:form>
</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>
