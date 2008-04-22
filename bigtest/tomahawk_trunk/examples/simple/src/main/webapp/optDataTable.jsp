<%@ page
	session="false"
	contentType="text/html;charset=utf-8"%>
<%@ taglib
	uri="http://java.sun.com/jsf/html"
	prefix="h"%>
<%@ taglib
	uri="http://java.sun.com/jsf/core"
	prefix="f"%>
<%@ taglib
	uri="http://myfaces.apache.org/tomahawk"
	prefix="t"%>
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

<%@include file="inc/head.inc"%>

<body>

<f:view>

	<f:loadBundle
		basename="org.apache.myfaces.examples.resource.example_messages"
		var="example_messages" />

<h:form>
	<h:panelGroup id="body">

		<h:panelGrid columns="2">
			<f:facet name="footer">
					<h:commandButton value="Update Table" />
			</f:facet>
			
			<h:outputLabel for="renderHeader" value="Render Header"/>
			<h:selectBooleanCheckbox id="renderHeader" value="#{countryForm.renderHeader}" />
			
			<h:outputLabel for="renderFooter" value="Render Footer"/>
			<h:selectBooleanCheckbox id="renderFooter" value="#{countryForm.renderFooter}" />
		</h:panelGrid>
		
		<f:verbatim>
			<br>
		</f:verbatim>

		<t:dataTable
			id="data"
			styleClass="standardTable"
			headerClass="standardTable_Header"
			footerClass="standardTable_Header"
			rowClasses="standardTable_Row1,standardTable_Row2"
			columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
			var="currentCountry"
			value="#{countryList.countries}"
			preserveDataModel="true">
			<f:facet name="header">
				<h:outputText value="The Table Header" />
			</f:facet>
			<h:column>
				<f:facet name="header">
					<h:outputText
							value="#{example_messages['label_country_name']}"
							rendered="#{countryForm.renderHeader}"
							/>
				</f:facet>
				<f:facet name="footer">
					<h:outputText
							value="#{example_messages['label_country_name']}"
							rendered="#{countryForm.renderFooter}"/>
				</f:facet>

				<h:outputText value="#{currentCountry.name}" />
			</h:column>

			<h:column>
				<f:facet name="header">
					<h:outputText
							value="#{example_messages['label_country_iso']}"
							rendered="#{countryForm.renderHeader}"/>
				</f:facet>
				<f:facet name="footer">
					<h:outputText
							value="#{example_messages['label_country_iso']}"
							rendered="#{countryForm.renderFooter}"/>
				</f:facet>
				<h:outputText value="#{currentCountry.isoCode}" />
			</h:column>

			<f:facet name="footer">
				<h:outputText value="tHE tABLE fOOTER" />
			</f:facet>
		</t:dataTable>

		<f:verbatim>
			<br>
		</f:verbatim>

	</h:panelGroup>
</h:form>

<jsp:include page="inc/mbean_source.jsp"/>

</f:view>

<%@include file="inc/page_footer.jsp"%>

</body>

</html>
