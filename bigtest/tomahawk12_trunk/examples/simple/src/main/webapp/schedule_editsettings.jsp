<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

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
<%@include file="/inc/head.inc"%>
<body>
<f:view>
	<h:form>
		<t:htmlTag value="h3">Edit settings</t:htmlTag>
		<h:messages tooltip="true" layout="table" globalOnly="false" />
		<h:panelGrid columns="3">
			<h:outputLabel for="mode" value="Display mode:" />
			<h:selectOneRadio id="mode" value="#{scheduleSettings2.mode}">
				<f:selectItem itemValue="0" itemLabel="day"/>
				<f:selectItem itemValue="1" itemLabel="workweek"/>
				<f:selectItem itemValue="2" itemLabel="week" />
				<f:selectItem itemValue="3" itemLabel="month" />
			</h:selectOneRadio>
			<h:message for="mode" />
			<h:outputLabel for="visibleStartHour" value="Display from:" />
			<h:inputText id="visibleStartHour"
				value="#{scheduleSettings2.visibleStartHour}" required="true">
				<f:validateLongRange minimum="0" maximum="11" />
			</h:inputText>
			<h:message for="visibleStartHour" />
			<h:outputLabel for="visibleEndHour" value="Display until:" />
			<h:inputText id="visibleEndHour"
				value="#{scheduleSettings2.visibleEndHour}" required="true">
				<f:validateLongRange minimum="13" maximum="24" />
			</h:inputText>
			<h:message for="visibleEndHour" />
			<h:outputLabel for="workingStartHour" value="Working day starts at:" />
			<h:inputText id="workingStartHour"
				value="#{scheduleSettings2.workingStartHour}" required="true">
				<f:validateLongRange minimum="7" maximum="11" />
			</h:inputText>
			<h:message for="workingStartHour" />
			<h:outputLabel for="workingEndHour" value="Working day ends at:" />
			<h:inputText id="workingEndHour"
				value="#{scheduleSettings2.workingEndHour}" required="true">
				<f:validateLongRange minimum="15" maximum="22" />
			</h:inputText>
			<h:message for="workingEndHour" />
			<h:outputLabel for="readonly" value="Schedule is read only:" />
			<h:selectBooleanCheckbox id="readonly"
				value="#{scheduleSettings2.readonly}" required="true" />
			<h:message for="readonly" />
			<h:outputLabel for="tooltip" value="Show tooltips:" />
			<h:selectBooleanCheckbox id="tooltip"
				value="#{scheduleSettings2.tooltip}" required="true" />
			<h:message for="tooltip" />
			<h:outputLabel for="renderZeroLength" value="Render zero length entries" />
			<h:selectBooleanCheckbox id="renderZeroLength"
				value="#{scheduleSettings2.renderZeroLength}" required="true" />
			<h:message for="renderZeroLength" />
			<h:outputLabel for="expandToFitEntries" value="Expand to fit entries" />
			<h:selectBooleanCheckbox id="expandToFitEntries"
				value="#{scheduleSettings2.expandToFitEntries}" required="true" />
			<h:message for="expandToFitEntries" />
			<h:outputLabel for="theme" value="Theme:" />
			<h:selectOneRadio id="theme" value="#{scheduleSettings2.theme}"
				required="true">
				<f:selectItems value="#{scheduleThemeChoices}" />
			</h:selectOneRadio>
			<h:message for="theme" />
			<h:outputLabel for="headerDateFormat" value="Header date format:" />
			<h:inputText id="headerDateFormat"
				value="#{scheduleSettings2.headerDateFormat}" required="true" />
			<h:message for="headerDateFormat" />
			<h:outputLabel for="compactWeekRowHeight"
				value="Row height in week mode:" />
			<h:inputText id="compactWeekRowHeight"
				value="#{scheduleSettings2.compactWeekRowHeight}" required="true">
				<f:validateLongRange minimum="100" maximum="300" />
			</h:inputText>
			<h:message for="compactWeekRowHeight" />
			<h:outputLabel for="compactMonthRowHeight"
				value="Row height in month mode:" />
			<h:inputText id="compactMonthRowHeight"
				value="#{scheduleSettings2.compactMonthRowHeight}" required="true">
				<f:validateLongRange minimum="50" maximum="150" />
			</h:inputText>
			<h:message for="compactMonthRowHeight" />
			<h:outputLabel for="detailedRowHeight"
				value="Row height in detailed mode:" />
			<h:inputText id="detailedRowHeight"
				value="#{scheduleSettings2.detailedRowHeight}" required="true">
				<f:validateLongRange minimum="22" maximum="50" />
			</h:inputText>
			<h:message for="detailedRowHeight" />
		</h:panelGrid>
		<h:panelGrid columns="2">
			<h:commandButton action="#{scheduleSettings2.save}" value="save" />
		</h:panelGrid>
	</h:form>
	<%@include file="/inc/page_footer.jsp"%>

    <jsp:include page="inc/mbean_source.jsp"/>

</f:view>
</body>
</html>
