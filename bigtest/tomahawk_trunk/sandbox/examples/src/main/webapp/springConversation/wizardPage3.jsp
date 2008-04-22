<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s"%>

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

<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=UTF-8" />
<title>MyFaces - the free JSF Implementation</title>
<link rel="stylesheet" type="text/css" href="css/basic.css" />
</head>
<body>
<f:view>

<s:ensureConversation name="springWizardData" action="#{springWizardController.ensureConversationAction}" />

<t:htmlTag value="h1">Registration Wizard</t:htmlTag>

<h:outputLink value="home.jsf"><h:outputText value="Menu" /></h:outputLink>
<s:separateConversationContext>
	<h:outputLink value="home.jsf"><h:outputText value="Menu (with new conversationContext)" /></h:outputLink>
</s:separateConversationContext>

<h:form>
<h:outputText value="Whatever the page might tell you, no data will ever be saved ;-)." />
<h:panelGrid columns="2">
	<f:facet name="header">
		<h:outputText value="Registration Wizard (page 3/3)" />
	</f:facet>
	<f:facet name="footer">
		<h:panelGroup>
			<h:commandButton value="<< Prev" action="springWizardPage2" immediate="true"/>
			<h:commandButton value="Finish" action="springWizardFinish" />
		</h:panelGroup>
	</f:facet>

    <h:outputText value="Info: " />
    <t:inputHtml
    		id="info"
    		value="#{springWizardData.info}"
    		allowEditSource="false"
    		allowExternalLinks="false"/>

</h:panelGrid>
<h:messages showDetail="true"/>
</h:form>
</f:view>
</body>
</html>
