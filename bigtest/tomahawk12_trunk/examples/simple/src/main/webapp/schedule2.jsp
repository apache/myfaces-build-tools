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
			<t:schedule value="#{scheduleHandler1.model}" id="schedule1"
				rendered="true" visibleEndHour="18" visibleStartHour="8"
				workingEndHour="17" workingStartHour="9" readonly="false"
				theme="evolution" tooltip="true" />
		</t:div>
		<!--  The column on the left, containing the calendar and other controls -->
		<t:div style="position: absolute; left: 5px; top: 5px; width: 210px; overflow: auto">
			<h:panelGrid columns="1">
				<t:inputCalendar id="scheduleNavigator"
					value="#{scheduleHandler1.model.selectedDate}" />
				<h:commandButton
					action="add_entry"
					value="add entry" />
				<h:commandButton
					actionListener="#{scheduleHandler1.deleteSelectedEntry}"
					value="delete selected entry"
					rendered="#{scheduleHandler1.model.entrySelected}"/>
			</h:panelGrid>
		    <%@include file="/inc/page_footer.jsp" %>
		    <jsp:include page="inc/mbean_source.jsp"/>
		</t:div>
	</h:form>
</f:view>
</body>
</html>
