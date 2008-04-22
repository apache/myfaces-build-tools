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

	<h:form id="form1">
   		<h:panelGrid columns="3">
	
			<h:outputLabel for="baseInput" value="#{example_messages['validate_base']}" />
			<h:inputText id="baseInput" value="#{validateForm.equal}" required="true"/>
			<t:message id="baseError" for="baseInput" styleClass="error" />
			
			<h:outputLabel for="eqInput" value="#{example_messages['validate_equal']}" />
			<h:inputText id="eqInput" required="true">
				<s:validateCompareTo for="baseInput" operator="eq" />
			</h:inputText>
			<t:message id="eqError" for="eqInput" styleClass="error" />
			
			<h:outputLabel for="neInput" value="#{example_messages['validate_notequal']}" />
			<h:inputText id="neInput" required="true">
				<s:validateCompareTo for="baseInput" operator="ne" />
			</h:inputText>
			<t:message id="neError" for="neInput" styleClass="error" />
			
			<h:outputLabel for="gtInput" value="#{example_messages['validate_greaterthan']}" />
			<h:inputText id="gtInput" required="true">
				<s:validateCompareTo for="baseInput" operator="gt" />
			</h:inputText>
			<t:message id="gtError" for="gtInput" styleClass="error" />
			
			<h:outputLabel for="geInput" value="#{example_messages['validate_greaterthanequal']}" />
			<h:inputText id="geInput" required="true">
				<s:validateCompareTo for="baseInput" operator="ge" />
			</h:inputText>
			<t:message id="geError" for="geInput" styleClass="error" />
			
			<h:outputLabel for="ltInput" value="#{example_messages['validate_lessthan']}" />
			<h:inputText id="ltInput" required="true">
				<s:validateCompareTo for="baseInput" operator="lt" />
			</h:inputText>
			<t:message id="ltError" for="ltInput" styleClass="error" />
			
			<h:outputLabel for="leInput" value="#{example_messages['validate_lessthanequal']}" />
			<h:inputText id="leInput" required="true">
				<s:validateCompareTo for="baseInput" operator="le" />
			</h:inputText>
			<t:message id="leError" for="leInput" styleClass="error" />
			
			<h:panelGroup/>
			<h:commandButton id="validateButton" value="#{example_messages['button_submit']}" action="#{validateForm.submit}"/>
			<h:panelGroup/>
		
		</h:panelGrid>
	</h:form>

    </h:panelGroup>

</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>
