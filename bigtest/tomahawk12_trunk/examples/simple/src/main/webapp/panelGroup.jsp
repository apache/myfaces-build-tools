<%@ page import="org.apache.myfaces.custom.tree.DefaultMutableTreeNode,
                 org.apache.myfaces.custom.tree.model.DefaultTreeModel"%>
<%@ page session="true" contentType="text/html;charset=utf-8"%>
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

    <t:panelGroup id="span">
		<t:htmlTag value="p">
			<f:verbatim>A panel group (&lt;t:panelGroup&gt;) is rendered as an HTML &lt;span&gt; by default...</f:verbatim>
		</t:htmlTag>
	</t:panelGroup>
	
	<t:htmlTag value="br"/>
	
	<t:panelGroup id="div" layout="block">
		<t:htmlTag value="p">
			<f:verbatim>...or as a &lt;div&gt; with a value of 'block' for the layout attribute</f:verbatim>
		</t:htmlTag>
	</t:panelGroup>
	
</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>
