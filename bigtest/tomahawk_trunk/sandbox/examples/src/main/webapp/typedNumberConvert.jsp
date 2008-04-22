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

<!--
managed beans used:
    validateForm
-->

<f:view>

    <f:loadBundle basename="org.apache.myfaces.examples.resource.example_messages" var="example_messages"/>

    <h:panelGroup id="body">

	<f:verbatim>Enter numbers with or without fraction and see how we convert them to the type required for bean</f:verbatim>
	
	<h:form id="form1">
		<h:panelGrid columns="4">
		
			<h:outputLabel for="shortNumber" value="Enter Number (autoconvert to short)" />
			<h:inputText id="shortNumber" value="#{numberHolder.shortNumber}">
				<s:convertNumber />
			</h:inputText>
			<h:outputText value="current value: #{numberHolder.shortNumber}" />
			<t:message for="shortNumber" styleClass="error" />
			
			<h:outputLabel for="intNumber" value="Enter Number (autoconvert to int)" />
			<h:inputText id="intNumber" value="#{numberHolder.intNumber}">
				<s:convertNumber />
			</h:inputText>
			<h:outputText value="current value: #{numberHolder.intNumber}" />
			<t:message for="intNumber" styleClass="error" />

			<h:outputLabel for="longNumber" value="Enter Number (autoconvert to long)" />
			<h:inputText id="longNumber" value="#{numberHolder.longNumber}">
				<s:convertNumber />
			</h:inputText>
			<h:outputText value="current value: #{numberHolder.longNumber}" />
			<t:message for="longNumber" styleClass="error" />

			<h:outputLabel for="doubleNumber" value="Enter Number (autoconvert to double)" />
			<h:inputText id="doubleNumber" value="#{numberHolder.doubleNumber}">
				<s:convertNumber />
			</h:inputText>
			<h:outputText value="current value: #{numberHolder.doubleNumber}" />
			<t:message for="doubleNumber" styleClass="error" />
			
			<h:outputLabel for="bigDecimalNumber" value="Enter Number (autoconvert to bigDecimal)" />
			<h:inputText id="bigDecimalNumber" value="#{numberHolder.bigDecimalNumber}">
				<s:convertNumber />
			</h:inputText>
			<h:outputText value="current value: #{numberHolder.bigDecimalNumber}" />
			<t:message for="bigDecimalNumber" styleClass="error" />

			<h:outputLabel for="doubleNumberManual" value="Enter Number (convert to double using destType attribute)" />
			<h:inputText id="doubleNumberManual" value="#{numberHolder.doubleNumberManual}">
				<s:convertNumber destType="java.lang.Double" />
			</h:inputText>
			<h:outputText value="current value: #{numberHolder.doubleNumberManual}" />
			<t:message for="doubleNumberManual" styleClass="error" />
			
			<h:panelGroup/>
				<h:commandButton />
			<h:panelGroup/>
		
		</h:panelGrid>
	</h:form>

    </h:panelGroup>

</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>
