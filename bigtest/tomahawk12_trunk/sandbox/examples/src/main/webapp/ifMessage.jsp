<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
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
	<h:form>

        <s:ifMessage for="item,item2">
			<h:outputText value="Error!!!!"/>
		</s:ifMessage>

        <br/>
		<br/>

        <h:message for="item"/>
		<h:inputText id="item" required="true"/>

        <br/>
		<br/>

        <h:message for="item2"/>
		<h:inputText id="item2" required="true"/>

        <br/>
		<br/>

        <h:message for="item3"/>
		<h:inputText id="item3" required="true"/>

        <br/>
		<br/>

        <h:commandButton value="Submit Me"/>
	</h:form>
</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>