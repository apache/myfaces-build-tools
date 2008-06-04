<%@ page import="java.util.Random"%>
<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
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

    <f:loadBundle basename="org.apache.myfaces.examples.resource.example_messages" var="example_messages"/>

    <h:panelGroup id="body">

        <h:messages id="messageList" showSummary="true" showDetail="true" />

        <h:form>
			<t:div id="setTypeHeader1" rendered="#{true}"  >
				<tr>
			    	<td  >
			      	<h:outputLabel value="# {true}" />
					</td>
					<td width="5%" />
			  	</tr>
			</t:div>
			<t:div id="setTypeHeader2" rendered="true"  >
				<tr>
			    	<td  >
			      	<h:outputLabel value="true" />
					</td>
					<td width="5%" />
			  	</tr>
			</t:div>
			<t:div id="setTypeHeader3" rendered="#{false}"  >
				<tr>
			    	<td  >
			      	<h:outputLabel value="# {false}" />
					</td>
					<td width="5%" />
			  	</tr>
			</t:div>
			<t:div id="setTypeHeader4" rendered="false"  >
				<tr>
			    	<td  >
			      	<h:outputLabel value="false" />
					</td>
					<td width="5%" />
			  	</tr>
			</t:div>
        </h:form>

    </h:panelGroup>

    <jsp:include page="inc/mbean_source.jsp"/>

</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>
