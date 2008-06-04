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
		<s:effect id="effect1" fade="true">
			<h:outputText
				value="Hello We hope you enjoy Apache MyFaces and the effects by DOJO and script.aculo.us" />
		</s:effect>
		<s:effect id="effect2" fade="true" duration="2000" fadeColor="green">
			<h:outputText
				value="Same effect different color, different duration" />
		</s:effect>

		<s:effect id="booga" puff="true">
			<t:outputText value="[Puff me by Clicking]" />
		</s:effect>
		<s:effect id="booga2" squish="true">
			<t:outputText value="[Squish me by clicking]" />
		</s:effect>
		<s:effect id="boogae2" pulsate="true">
			<t:outputText value="[Pulsate me by clicking]" />
		</s:effect>
	</h:form>

</f:view>

<%@include file="inc/page_footer.jsp"%>

</body>

</html>

