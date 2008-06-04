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
		<!--  The schedule itself -->
		<t:div style="position: absolute; left: 220px; top: 5px; right: 5px;">
			<t:schedule value="#{bindingScheduleHandler.model}" id="schedule1"
			    binding="#{bindingScheduleHandler.schedule}"
				rendered="true" visibleEndHour="#{scheduleSettings2.visibleEndHour}"
				visibleStartHour="#{scheduleSettings2.visibleStartHour}"
				workingEndHour="#{scheduleSettings2.workingEndHour}"
				workingStartHour="#{scheduleSettings2.workingStartHour}"
				readonly="#{scheduleSettings2.readonly}"
				theme="#{scheduleSettings2.theme}"
				tooltip="#{scheduleSettings2.tooltip}"
				headerDateFormat="#{scheduleSettings2.headerDateFormat}"
				compactWeekRowHeight="#{scheduleSettings2.compactWeekRowHeight}"
				compactMonthRowHeight="#{scheduleSettings2.compactMonthRowHeight}"
				detailedRowHeight="#{scheduleSettings2.detailedRowHeight}"
				submitOnClick="true"
				mouseListener="#{bindingScheduleHandler.scheduleClicked}"
				action="#{bindingScheduleHandler.scheduleAction}"
				/>
			<h:outputText value="#{bindingScheduleHandler.mouseActionText}"></h:outputText>
		</t:div>
		<!--  The column on the left, containing the calendar and other controls -->
		<t:div style="position: absolute; left: 5px; top: 5px; width: 210px; overflow: auto">
			<h:panelGrid columns="1">
				<t:inputCalendar id="scheduleNavigator"
					value="#{bindingScheduleHandler.model.selectedDate}" />
				<h:commandButton
					actionListener="#{bindingScheduleHandler.addSampleEntries}"
					value="add sample entries" />
				<h:commandButton
					actionListener="#{bindingScheduleHandler.addSampleHoliday}"
					value="add sample holiday" />
			</h:panelGrid>
		    <%@include file="/inc/page_footer.jsp" %>
		    <jsp:include page="inc/mbean_source.jsp"/>
		</t:div>
	</h:form>
</f:view>
</body>
</html>
