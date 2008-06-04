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

  <h:form id="carForm" onsubmit="return tomahawk.executeClientLifeCycle();">

		<t:messages forceSpan="true" layout="list" showDetail="true" showSummary="false"/>

		<h:panelGrid columns="3">
			<h:outputText id="lbl_id" value="Id *"></h:outputText>
			<h:inputText id="txt_id" value="#{customerCreateBean.customer.id}" required="true"></h:inputText>
			<t:message for="txt_id" forceSpan="true" />
		
			<h:outputText id="lbl_name" value="Name *"></h:outputText>
			<h:inputText id="txt_name" value="#{customerCreateBean.customer.name}" required="true"></h:inputText>
			<t:message for="txt_name" forceSpan="true" />
			
			<h:outputText id="lbl_surname" value="Surname *"></h:outputText>
			<h:inputText id="txt_surname" value="#{customerCreateBean.customer.surname}" required="true"></h:inputText>
			<t:message for="txt_surname" forceSpan="true" />

			<h:outputText id="lbl_age" value="Age"></h:outputText>
			<h:inputText id="txt_age" value="#{customerCreateBean.customer.age}">
				<f:validateLongRange minimum="18" maximum="50" />
			</h:inputText>
			<t:message for="txt_age" forceSpan="true" />
			
			<h:outputText id="lbl_address" value="Adress"></h:outputText>
			<h:inputText id="txt_address" value="#{customerCreateBean.customer.address}">
				<f:validateLength minimum="2" maximum="5"/>
			</h:inputText>
			<t:message for="txt_address" forceSpan="true" />
			
			<h:outputText id="lbl_salary" value="Salary"></h:outputText>
			<h:inputText id="txt_salary" value="#{customerCreateBean.customer.salary}">
				<f:validateDoubleRange minimum="1000" maximum="5000" />
			</h:inputText>
			<t:message for="txt_salary" forceSpan="true" />
			
			<h:outputText id="lbl_salaryBonus" value="Salary Bonus"></h:outputText>
			<h:inputText id="txt_salaryBonus" value="#{customerCreateBean.customer.salaryBonus}">
			</h:inputText>
			<t:message for="txt_salaryBonus" forceSpan="true" />
		</h:panelGrid>

		<h:commandButton id="btn_save" value="Create" />
		
		<%--
			Not necessary when h:form or s:form is used, only added just to give an example
			
			<s:validationScript></s:validationScript> 
		--%>

	</h:form>
	
</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>
