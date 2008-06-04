<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s"%>

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
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=UTF-8" />
<title>MyFaces - the free JSF Implementation</title>
<link rel="stylesheet" type="text/css" href="css/basic.css" />
<style>

.dojoHtmlFisheyeListBar {
	margin: 0 auto;
	text-align: center;
}

.outerbar {
	background-color: #666;
	text-align: center;
    position: absolute;
	left: 0px;
	top: 0px;
	width: 100%;
}

body {
	font-family: Arial, Helvetica, sans-serif;
	padding: 0;
	margin: 0;
}

.page {
	padding: 60px 20px 20px 20px;
}

</style>
</head>
<body>
<f:view>
    <f:loadBundle basename="org.apache.myfaces.examples.fisheye.labels" var="label"/>
    <h:form>
	<t:div styleClass="outerbar">
        <s:fishEyeNavigationMenu itemWidth="50" itemHeight="50" itemMaxWidth="80"
            itemMaxHeight="80" orientation="horizontal" effectUnits="2" var="item" value="#{fisheye.items}"
            itemPadding="10" attachEdge="top" labelEdge="bottom" visibleWindow="3" >
                <s:fishEyeCommandLink caption="#{labels.labels[item.caption]}" iconSrc="#{item.iconSrc}" target="#{item.target}"
                                      actionListener="#{item.action}"/>
        </s:fishEyeNavigationMenu>
	</t:div>
	<t:div styleClass="page">
		<t:outputText value="#{fisheye.actionName}" />
		<%@include file="../inc/page_footer.jsp"%>
	</t:div>
	</h:form>
</f:view>
</body>
</html>