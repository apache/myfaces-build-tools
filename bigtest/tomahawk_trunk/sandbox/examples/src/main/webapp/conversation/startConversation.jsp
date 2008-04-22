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

<h:outputLink value="home.jsf"><h:outputText value="Menu" /></h:outputLink>

<h:form>
<h:panelGrid columns="2">
    <h:outputText value="Enter something into this field: " />
    <h:inputText value="#{convData.input}" />
    
    <h:commandLink value="check value"/>

	<h:panelGrid columns="2">
		<h:commandLink value="start conversation">
			<s:startConversation name="pageDemand">
				<s:conversation value="#{convData}" />
			</s:startConversation>
		</h:commandLink>
		<h:commandLink value="end conversation">
			<s:endConversation name="pageDemand" />
		</h:commandLink>
	</h:panelGrid>
</h:panelGrid>
<h:panelGrid columns="1">
    <h:outputText value="Press 'check value' to issue a new request" />
	<h:outputText value="Press 'start conversation' to start the conversation (which will elevate the bean)" />
    <h:outputText value="Press 'end conversation' to simulate a server action AND END the conversation (then your value will be lost)" />
</h:panelGrid>
</h:form>
</f:view>
</body>
</html>
