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
		
			<h:outputLabel for="email" value="#{example_messages['email_comma']}" />
			<h:inputText id="email" value="#{validateForm.email}" required="true">
				<s:validateCSV subvalidatorId="org.apache.myfaces.validator.Email" />
			</h:inputText>
			<t:message id="emailError" for="email" styleClass="error" />
			
			<h:outputLabel for="creditCardNumber" value="#{example_messages['credit_dot']}" />
			<h:inputText id="creditCardNumber" value="#{validateForm.creditCardNumber}" required="true">
				<s:validateCSV subvalidatorId="org.apache.myfaces.validator.CreditCard" separator="\\." />
			</h:inputText>
			<t:message id="creditCardNumberError" for="creditCardNumber" styleClass="error" />
			
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
