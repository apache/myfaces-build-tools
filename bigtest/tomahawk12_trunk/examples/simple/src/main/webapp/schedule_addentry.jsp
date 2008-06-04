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
		<t:htmlTag value="h3">Add entry</t:htmlTag>
		<h:messages tooltip="true" layout="table" globalOnly="true" />
		<h:panelGrid columns="3">
			<h:outputLabel for="from" value="from:" />
			<t:inputDate id="from" value="#{addEntryHandler.from}" type="both"
				required="true" popupCalendar="true"/>
			<h:message for="from" />
			<h:outputLabel for="until" value="until:" />
			<t:inputDate id="until" value="#{addEntryHandler.until}" type="both"
				required="true" popupCalendar="true"/>
			<h:message for="until" />
			<h:outputLabel for="title" value="title:" />
			<h:inputText id="title" value="#{addEntryHandler.title}" required="true" />
			<h:message for="title" />
			<h:outputLabel for="location" value="location:" />
			<h:inputText id="location" value="#{addEntryHandler.location}" />
			<h:message for="location" />
			<h:outputLabel for="comments" value="comments:" />
			<h:inputTextarea id="comments" value="#{addEntryHandler.comments}" />
			<h:message for="comments" />
		</h:panelGrid>
		<h:panelGrid columns="2">
			<h:commandButton action="#{addEntryHandler.add}" value="add" />
			<h:commandButton action="cancel" value="cancel" immediate="true"/>
		</h:panelGrid>
	</h:form>
<%@include file="/inc/page_footer.jsp"%>
<jsp:include page="inc/mbean_source.jsp"/>

</f:view>
</body>
</html>
