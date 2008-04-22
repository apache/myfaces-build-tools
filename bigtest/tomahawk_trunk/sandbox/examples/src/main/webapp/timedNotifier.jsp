<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s"%>

<html>

	<%@include file="inc/head.inc"%>

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
		<h:form>
			<h:outputText value="Timed notifier please wait a few seconds for the first dialog"></h:outputText>
			<s:timedNotifier disabled="false" showDelay="5000" hideDelay="3000" confirmationMessage="5 minutes til timeout, press ok to click me away early" />
			<s:timedNotifier showDelay="7000" hideDelay="2000">
				<f:facet name="content">
					<h:panelGroup>
						<f:verbatim>this confirm could be </f:verbatim>
						<h:outputFormat id="xxa" value="having an action" />
					</h:panelGroup>
				</f:facet>
				<f:facet name="confirm">
					<h:panelGrid columns="1">
						<h:commandButton id="myConfirm" value="custom confirm" />
					</h:panelGrid>
				</f:facet>
			</s:timedNotifier>
		</h:form>
		</f:view>

		<%@include file="inc/page_footer.jsp"%>

	</body>

</html>

