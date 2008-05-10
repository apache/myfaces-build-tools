<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
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

<f:view>
	<t:stylesheet path="/css/table.css" filtered="true" />

	<f:loadBundle basename="org.apache.myfaces.examples.resource.example_messages" var="example_messages"/>

    <p>
        This example demonstrates a filtered stylesheet.
	</p>
    <p>
		You should see a table with an background image configured. The background image is configured in
		the stylesheet as<br />

		<pre>
.tableBack
{
	background-image: url(&#35;{environmentInfo.requestContextPath}/images/logo.jpg)
}
		</pre>
	</p>

	<h:panelGrid columns="3" styleClass="tableBack">
		<h:outputText value="col1.1" />
		<h:outputText value="col1.2" />
		<h:outputText value="col1.3" />
		<h:outputText value="col2.1" />
		<h:outputText value="col2.2" />
		<h:outputText value="col2.3" />
		<h:outputText value="col3.1" />
		<h:outputText value="col3.2" />
		<h:outputText value="col3.3" />
	</h:panelGrid>

</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>
